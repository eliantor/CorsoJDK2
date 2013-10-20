package me.eto.justdoit.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by eto on 10/19/13.
 */
public class NetworkStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        ConnectivityManager netManager =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        final NetworkInfo current = netManager.getActiveNetworkInfo();
        if(current==null) Log.d("BROADCAST","No network");

        String state =current.getState().name();
        String type =current.getTypeName();

        Log.d("BROADCAST",String.format("%s state: %s",type,state));

    }
}
