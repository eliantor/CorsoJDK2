package me.eto.justdoit.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

/**
 * Created by eto on 10/19/13.
 */
public class SimpleReceiver extends ResultReceiver {

    public interface Receiver{
        public void onReceive(int code,Bundle data);
    }

    private Receiver mReceiver;

    public void setReceiver(Receiver receiver){
        mReceiver =receiver;
    }

    /**
     * Create a new ResultReceive to receive results.  Your
     * {@link #onReceiveResult} method will be called from the thread running
     * <var>handler</var> if given, or from an arbitrary thread if null.
     */
    public SimpleReceiver(Handler handler) {
        super(handler);
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if(mReceiver!=null){
            mReceiver.onReceive(resultCode,resultData);
        }
    }




}
