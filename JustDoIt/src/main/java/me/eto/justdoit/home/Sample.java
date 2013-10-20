package me.eto.justdoit.home;

import android.app.IntentService;
import android.content.Intent;
import android.os.ResultReceiver;

/**
 * Created by eto on 10/19/13.
 */
public class Sample extends IntentService{
    private final static String RECEIVER = "RECEIVER";
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public Sample(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ResultReceiver r = intent.getParcelableExtra(RECEIVER);
        r.send(10,null);

    }
}
