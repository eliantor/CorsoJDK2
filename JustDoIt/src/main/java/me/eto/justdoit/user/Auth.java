package me.eto.justdoit.user;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.appcompat.R;

import com.google.android.gms.internal.co;

/**
 * Created by eto on 10/12/13.
 */
public class Auth extends Service {
    public final static String SYNC_ACCOUNT = "default_symc_account";
    private final static String SYNC_ACCOUNT_TYPE = "me.eto.justdoit.SYNC_ACCOUNT";
    private Authenticator mAuthenticator;

    public static boolean createSyncAccount(Context context){
        Account account = new Account(SYNC_ACCOUNT,SYNC_ACCOUNT_TYPE);
        AccountManager manager = AccountManager.get(context);
        return manager.addAccountExplicitly(account,null,null);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAuthenticator = new Authenticator(this.getApplicationContext());
    }


    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }

    private static class Authenticator extends AbstractAccountAuthenticator{

        public Authenticator(Context context) {
            super(context);
        }

        @Override
        public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options) throws NetworkErrorException {
            return null;
        }

        @Override
        public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options) throws NetworkErrorException {
            return null;
        }

        @Override
        public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
            throw new UnsupportedOperationException();
        }

        @Override
        public String getAuthTokenLabel(String authTokenType) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
            throw new UnsupportedOperationException();
        }

        @Override
        public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features) throws NetworkErrorException {
            throw new UnsupportedOperationException();
        }
    }

}
