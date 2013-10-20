package me.eto.justdoit.globals;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by eto on 10/19/13.
 */
public class JustDoItPrefs {
    private final static String MY_PREFS = "PREFS";
    private final static String IS_FIRST_LAUNCH ="IS_FIRST_LAUNCH";

    private static volatile JustDoItPrefs sObject;
    private static final Object LOCK = new Object();

    public static JustDoItPrefs get(Context context){
        if(sObject ==null){
            synchronized (LOCK){
                if(sObject ==null){
                    sObject = new JustDoItPrefs(context);
                }
            }
        }
        return sObject;

    }

    private final Context fContext;
    private JustDoItPrefs(Context context){
        fContext = context.getApplicationContext();
    }

    private SharedPreferences getPrefs(){
        return fContext.getSharedPreferences(MY_PREFS,Context.MODE_PRIVATE);

    }

    public boolean isFirstRun(){
        SharedPreferences prefs = getPrefs();
        if(prefs.getBoolean(IS_FIRST_LAUNCH,true)){
            prefs.edit().putBoolean(IS_FIRST_LAUNCH,false).commit();
            return true;
        }
        return false;
    }
}
