package me.eto.justdoit.home;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import me.eto.justdoit.Contract;
import me.eto.justdoit.R;
import me.eto.justdoit.utils.IdGen;

/**
 * Created by eto on 10/5/13.
 */
public class ListTodosFragment extends Fragment {

    /**
     * Usare un id preso dalle risorse garantisce l'unicitá dell'id stesso
     * per tutta l'applicazione.
     *
     */
    private final static int      TODO_LOADER_ID = R.id.loader_id;
    private final static Uri      TODO_URI       = Contract.Todos.CONTENT_URI;
    private final static String[] PROJECTION     = Contract.Todos.Fields.ALL_COLUMNS;
    private final static String   SORTING        = Contract.Todos.Fields.DATE;

    public static interface OnTodoItemActionListener {

        public void onTodoItemSelected(Uri todoItemUri);
    }

    private ListView     mList;
    private TodosAdapter mAdapter;
    private final Callbacks fCallbacks = new Callbacks();
    private OnTodoItemActionListener mOnTodoItemActionListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_main, container, false);
        mList = (ListView) v.findViewById(R.id.lv_todos);
        mAdapter = new TodosAdapter(getActivity());
        mList.setAdapter(mAdapter);
        return v;
    }

    public void setOnTodoItemActionListener(OnTodoItemActionListener listener) {
        mOnTodoItemActionListener = listener;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final LoaderManager loaderManager = getLoaderManager();

        /*
         Init loader crea il loader solo una volta
         le chiamate successive recuperano la stessa istanza
         quindi se volete modifcare la query non va bene
         va invece usato il metodo seguente
        loaderManager.restartLoader(TODO_LOADER_ID,null,fCallbacks);
         che lo distrugge e ne crea uno nuovo come sostituto.

         */
        loaderManager.initLoader(
                /* an identifier unique for the whole activity*/ TODO_LOADER_ID,
                /* an optional bundle of args*/ null,
                /* callbacks invoked during the life of the loader*/ fCallbacks);

    }

    private class Callbacks implements
            LoaderManager.LoaderCallbacks<Cursor>,
            AdapterView.OnItemClickListener {

        /**
         * When the loader does not yet exists this
         * callback is invoked to create one
         *
         * @param loaderId the identifier
         * @param bundle   the arguments passed to initLoader
         * @return the new loader
         */
        @Override
        public Loader<Cursor> onCreateLoader(int loaderId, Bundle bundle) {

            if (loaderId == TODO_LOADER_ID) {

                return new CursorLoader(getActivity(), TODO_URI,
                        PROJECTION, null, null, SORTING);
            }
            throw new AssertionError("Unhandled loader " + loaderId);
        }

        /**
         * at this point the loader has completed it's work,
         * in this case we are loading a cursor
         *
         * @param loader the loader
         * @param cursor the loaded cursor
         */
        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
            final int id = loader.getId(); // which loader completed?
            if (id == TODO_LOADER_ID) {
                //so we have a cursor
                //we refresh the adapter
                //swap cursor take care of closing
                //the old cursor setting the new one
                //and notify the adapter of the changes
                mAdapter.swapCursor(cursor);
            }
        }

        /**
         * When this is called, the loader is going
         * to be detroyed data is not valid anymore
         * we cannot hold on previous delivered results
         *
         * @param loader the loader
         */
        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            final int id = loader.getId();
            if (id == TODO_LOADER_ID) {
                // swapping with null is allowed
                // to erase old data
                mAdapter.swapCursor(null);
            }
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // given we have used _id as primary key
            // the adapter is able to identify an unique id for each row
            // we can build the uri we wold like to view
            Uri uri = Contract.Todos.singleTodo(id);
            if (mOnTodoItemActionListener != null) {
                mOnTodoItemActionListener.onTodoItemSelected(uri);
            }
        }
    }
}
