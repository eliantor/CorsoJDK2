package me.eto.justdoit.globals;

import android.content.Context;

import java.io.File;

import me.eto.justdoit.R;

/**
 * Created by eto on 10/19/13.
 */
public class GlobalSettings {

    private static volatile GlobalSettings sObject;
    private static final Object LOCK = new Object();

    private final Context mContext;

    private GlobalSettings(Context context) {
        mContext = context.getApplicationContext();
    }

    public static GlobalSettings get(Context context){
        if(sObject ==null){
            synchronized (LOCK){
                if(sObject ==null){
                    sObject = new GlobalSettings(context);
                }
            }
        }
        return sObject;

    }

    public File createMyDirectory(){
        File f =mContext.getFilesDir();
        File myDir = new File(f,getString());
        myDir.mkdirs();
        return myDir;
    }

    public String getString(){
        return mContext.getString(R.string.string_config);
    }

    public int getInteger(){
        return mContext.getResources().getInteger(R.integer.integer_config);
    }
}
