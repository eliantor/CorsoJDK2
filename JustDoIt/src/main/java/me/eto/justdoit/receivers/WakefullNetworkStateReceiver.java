package me.eto.justdoit.receivers;

import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Created by eto on 10/19/13.
 */
public class WakefullNetworkStateReceiver extends WakefulBroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(null);
        /*

        PowerManager m = (PowerManager)context.getSystemService(Context.POWER_SERVICE);
        final PowerManager.WakeLock myWakelock = m.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "myWakelock");

       */
        /*

        WakefulBroadcastReceiver.completeWakefulIntent(null);
        startWakefulService(context, null);
         */

    }
}
