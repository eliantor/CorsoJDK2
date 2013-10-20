package me.eto.justdoit.network;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 * Created by eto on 10/19/13.
 */
public class GCMService extends IntentService {


    public GCMService() {
        super("GCM");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

        String message = gcm.getMessageType(intent);
        if (!extras.isEmpty()) {
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(message)) {
                //error
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(message)) {
                //no more on server
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(message)) {
                // do something
                extras.toString();
            }
        }

//        GCMReceiver.completeWakefulIntent(intent);
    }
}
