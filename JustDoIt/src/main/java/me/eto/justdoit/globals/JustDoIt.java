package me.eto.justdoit.globals;

import android.app.Application;
import android.os.AsyncTask;

import me.eto.justdoit.R;

/**
 * Created by eto on 10/19/13.
 */
public class JustDoIt extends Application {

    private static String mString;
    private static JustDoIt sSelf;

    @Override
    public void onCreate() {
        super.onCreate();
        initAsyncTask();
        sSelf = this;
        mString = getString(R.string.string_config);

    }

    private void initAsyncTask(){
        try{
            // bug di async task
            // usa handler su thread sbagliato
            // se usiamo questa riga é garantito
            // che sia stato inizializzato sul thread giusto
            Class.forName(AsyncTask.class.getName(),true,
                    JustDoIt.class.getClassLoader());
        } catch (ClassNotFoundException e) {

        }
    }
    public static JustDoIt get(){
        return sSelf;
    }

    public String getString(){
        return mString; // non puó essere null
    }
}
