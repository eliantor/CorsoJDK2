package me.eto.justdoit.loaders;

import android.content.Context;
import android.database.ContentObservable;
import android.support.v4.content.AsyncTaskLoader;

/**
 * Created by eto on 10/12/13.
 */
@SuppressWarnings("unused")
public abstract class SimpleCustomLoader<T extends ContentObservable> extends AsyncTaskLoader<T> {


    private T mPayload;

    /*
    public final class ForceLoadContentObserver extends ContentObserver {
        public ForceLoadContentObserver() {
               super(new Handler());
        }

         @Override
         public boolean More ...deliverSelfNotifications() {
             return true;
         }

         @Override
         public void More ...onChange(boolean selfChange) {
             onContentChanged();
         }
     }
     */

    private ForceLoadContentObserver mObserver;

    public SimpleCustomLoader(Context context) {
        super(context);
        mObserver = new ForceLoadContentObserver();
        // do not store the context
        // it's saved by the base class

    }

    @Override
    public T loadInBackground() {
        T payload = onLoad();
        if (payload != null) {
            payload.registerObserver(mObserver);
        }
        return payload;
    }


    protected abstract T onLoad();

    protected abstract void onDispose(T data);


    private void dispose(T data) {

        if (data != null) {
            //if we have data deregister
            data.unregisterObserver(mObserver);
        }
        onDispose(data);
    }


    /**
     * This runs on the ui thread
     * when the payload is loaded
     *
     * @param data
     */
    @Override
    public void deliverResult(T data) {
        // the loader can be in one of 3 states
        // reset: about to terminate
        // stopped: not loading data
        // started: loading has been requested

        if (isReset()) {
            // the loader finished loading but has been reset
            // we need to dispose release the received data
            if (data != null) {
                dispose(data);
            }
            return;
        }

        //we swap oldData
        T oldData = mPayload;
        mPayload = data;

        if (isStarted()) {
            //loader is active so we want
            //to deliver new payload
            super.deliverResult(mPayload);
        }

        if (oldData != mPayload && oldData != null) {
            //if we ve not delivered the same
            //payload and we had a previous one
            //we need to dispose it
            dispose(data);
        }
    }

    @Override
    protected void onStartLoading() {
        //we ve started the loader
        //through initLoader or restartLoader
        if (mPayload != null) {
            //we have ready a result so just deliver it
            deliverResult(mPayload);
        }
        if (takeContentChanged() || mPayload != null) {
            //we didn't have a result or content has changed
            //so we load it
            forceLoad();
        }
    }


    @Override
    protected void onStopLoading() {
        //we are going to pause. stop loading
        cancelLoad();
    }

    @Override
    public void onCanceled(T data) {
        //loading has been cancelled
        //if we have data release it
        if (mPayload != null) {
            dispose(mPayload);
        }
    }

    @Override
    protected void onReset() {
        super.onReset();
        //loader has been reset
        //ensure no current loading
        onStopLoading();
        //and dispose data
        if (mPayload != null) {
            dispose(mPayload);
        }
    }
}
