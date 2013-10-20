package me.eto.justdoit.network;

import android.accounts.Account;
import android.app.Service;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.Intent;
import android.content.SyncResult;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by eto on 10/12/13.
 */
public class Sync extends Service {

    private SyncAdapter mSyncAdapter;

    @Override
    public void onCreate() {
        super.onCreate();
        
    }

    @Override
    public IBinder onBind(Intent intent) {
        if (mSyncAdapter==null){
            mSyncAdapter=new SyncAdapter(this.getApplicationContext(),true);
        }
        return mSyncAdapter.getSyncAdapterBinder();
    }


    private static class SyncAdapter extends AbstractThreadedSyncAdapter{

        public SyncAdapter(Context context, boolean autoInitialize) {
            super(context, autoInitialize);
        }

        @Override
        public void onPerformSync(Account account,
                                  Bundle extras, String authority,
                                  ContentProviderClient provider,
                                  SyncResult syncResult) {

        }
    }
}