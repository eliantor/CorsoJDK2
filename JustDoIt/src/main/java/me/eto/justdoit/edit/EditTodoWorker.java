package me.eto.justdoit.edit;

import android.app.Activity;
import android.content.AsyncQueryHandler;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.lang.ref.WeakReference;

import me.eto.justdoit.Contract;

/**
 * Created by eto on 10/5/13.
 */
public class EditTodoWorker extends Fragment {

    private final static int INSERT_TODO = 1;

    private static abstract class BaseQueryHandler extends AsyncQueryHandler {

        public BaseQueryHandler(EditTodoWorker w) {
            super(w.getActivity().getContentResolver());
        }


        public abstract void detach();

    }

    /**
     * Non essendo statico non possiamo decidere quale policy
     * utilizzare per la terminazione, perché non abbiamo un
     * riferimento esplicto al fragment. Per cui possiamo solo
     * annullare.
     */
    private class NonStaticQueryHandler extends BaseQueryHandler {

        NonStaticQueryHandler(EditTodoWorker w) {
            super(w);
        }

        @Override
        protected void onInsertComplete(int token, Object cookie, Uri uri) {
            handleQueryCompletion(uri);
        }

        @Override
        public void detach() {

        }
    }


    /**
     * Lasciamo il controllo del detachment dal fragment
     * al sistema. Ovvero l'operazione termina ma non ci
     * viene segnalato, se il sistema ritiene il fragment
     * contenitore non serve piú
     */
    @SuppressWarnings("unused")
    private static class StaticWeakQueryHandler extends BaseQueryHandler {

        private final WeakReference<EditTodoWorker> mOwnerRef;

        StaticWeakQueryHandler(EditTodoWorker w) {
            super(w);
            mOwnerRef = new WeakReference<EditTodoWorker>(w);
        }

        @Override
        public void detach() {

        }

        @Override
        protected void onInsertComplete(int token, Object cookie, Uri uri) {
            final EditTodoWorker w = mOwnerRef.get();
            if (w != null) {
                w.handleQueryCompletion(uri);
            }
        }
    }

    /**
     * Come il caso precedente, ma siamo noi che esplicitamente
     * dobbiamo staccare il fragment dal thread tramite detach
     * quando lo riteniamo piú opportuno.
     */
    @SuppressWarnings("unused")
    private static class StaticQueryHandler extends BaseQueryHandler {

        private EditTodoWorker mOwner;

        StaticQueryHandler(EditTodoWorker w) {
            super(w);
            mOwner = w;
        }

        @Override
        protected void onInsertComplete(int token, Object cookie, Uri uri) {
            if (mOwner != null) mOwner.handleQueryCompletion(uri);
        }

        @Override
        public void detach() {
            mOwner = null;
        }
    }

    /**
     * This is non static, but we can't leak the
     * reference because the fragment is retained
     */
//    private static class InternalQueryHandler extends AsyncQueryHandler {
//
//        private EditTodoWorker mOwner;
////        private WeakReference<EditTodoWorker> mOwnerRef;
//
//        InternalQueryHandler(EditTodoWorker owner) {
//            super(owner.getActivity().getContentResolver());
////            mOwnerRef =new WeakReference<EditTodoWorker>(owner);
//            mOwner =owner;
//        }
//
//        public void detach(){
//            mOwner = null;
//        }
//
//        @Override
//        protected void onInsertComplete(int token, Object cookie, Uri uri) {
////            final EditTodoWorker w=mOwnerRef.get();
////
////            if (w != null)w.handleQueryCompletion(uri);
//
//              if (mOwner!=null)mOwner.handleQueryCompletion(uri);
////           .handleQueryCompletion(uri);
//        }
//    }

    private BaseQueryHandler mHandler;

    /**
     * A callback set by the activity
     * to handle completion of async operations
     */
    public static interface OnTodoAdded {

        public void onTodoAdded(Uri uri);
    }

    private OnTodoAdded mListener;
    /**
     * We need to cache the last result
     * when the listener is not available
     * and we cannot deliver it immediately.
     * The simplest possible strategy is to
     * keep a single value but is possible
     * to have a queue of pending result
     */
    private Uri         mLastResult;

    /**
     * A flag used to indicate the readyness
     * of the listener. We need this when
     * the valid lifecycle scope for result delivery
     * is narrower than the setting/unsetting of the listener
     */
    private boolean mActive;

    /**
     * This is called whenever the fragment is reattached
     * to the activity being retained or not
     *
     * @param activity to attach to
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (OnTodoAdded) activity;
    }

    /**
     * Given the fragment is retained
     * and we have the activity instance as
     * callback we need to release the reference
     * to avoid leaks.
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * OnResume marks the beginning
     * of the valid delivery scope
     */
    @Override
    public void onResume() {
        super.onResume();
        // we set the flag
        mActive = true;
        // and deliver pending results
        deliverPendingResults();
    }

    private void deliverPendingResults() {
        // if we have a lastResult
        // than the async work completed
        // while the listener was not
        // active
        if (mLastResult != null) {
            // we clear the cache
            Uri last = mLastResult;
            mLastResult = null;
            // and deliver the result
            // with this kind of implementation
            // the listener is unaware of
            // the fact that the result was realized
            // while it was not available
            // We could add a second parameter
            // to distinguish the two cases
            // but being opaque has the advantage
            // that external components can react
            // always in the same way
            mListener.onTodoAdded(last);
        }
    }

    /**
     * OnPause marks the end of
     * the active delivery scope
     */
    @Override
    public void onPause() {
        super.onPause();
        mActive = false;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // we set the fragment retained state
        setRetainInstance(true);
        // this method won't be called again
        // during restarts and config changes,
        // thus is a true once only initialization
        mHandler = new NonStaticQueryHandler(this);
    }

    /**
     * We handle the completion of
     * the query taking into account
     * the possibility of a missing listener
     *
     * @param uri
     */
    private void handleQueryCompletion(Uri uri) {
        if (mActive) {
            // if we are in the delimited active delivery
            // scope we push the result immediately to
            // the listener
            mListener.onTodoAdded(uri);
        } else {
            //otherwise we cache the result
            //for future delivery
            mLastResult = uri;
        }
    }


    /**
     * public api that wraps the asyncqueryhandler
     *
     * @param title
     * @param description
     */
    public void insertTodo(String title, String description) {
        ContentValues v = new ContentValues();
        v.put(Contract.Todos.Fields.TITLE, title);
        v.put(Contract.Todos.Fields.DESCRIPTION, description);
        mHandler.startInsert(INSERT_TODO, null,
                Contract.Todos.CONTENT_URI,
                v);
    }


    /**
     * When we reach this point
     * the activity has been finished
     * and not terminated due to low system
     * resource neither restarts or config
     * changes.
     * At this point the activity won't be
     * restarted again.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        //if we don't cancel the operation
        //we may temporarily leak this fragment
        //it's not as serious as leaking the whole
        //activity, but still.
        //We can avoid serious leaks cancelling
        //pending operations, in thi case, all ops
        // are identified by the same token.
        mHandler.cancelOperation(INSERT_TODO);
        mHandler.detach();
        mHandler = null;

    }
}
