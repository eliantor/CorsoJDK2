package me.eto.justdoit.internet;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.internal.ac;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import me.eto.justdoit.Contract;

/**
 * Created by eto on 10/12/13.
 */
public class NetworkClient extends IntentService {
    private final static String REPLY_TO = "reply_to";

    public static void download(Context context, String url,String action) {
        context.startService(new Intent(context, NetworkClient.class)
                .setData(Uri.parse(url))
                .putExtra(REPLY_TO, action));
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public NetworkClient() {
        super("NETWORK CLIENT");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        Uri connectTo = intent.getData();
        HttpURLConnection connection = null;
        InputStream in = null;
        final String replyChannel = intent.getStringExtra(REPLY_TO);
        try {
//            sendBroadcast(new Intent(replyChannel).putExtra("state","starting..."));
            LocalBroadcastManager.getInstance(this)
                    .sendBroadcast(new Intent(replyChannel).putExtra("state","starting..."));

            URL url = new URL(connectTo.toString());
            connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("GET");
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "*/*");

            connection.connect();
            int status = connection.getResponseCode();
            if (status / 100 == 2) {
                // response
                //read response
                in = connection.getInputStream();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(in, "UTF-8"));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append('\n');
                }
                String data = sb.toString();
                saveData(data);
                Log.d("RECEIVED", sb.toString());

            } else {
                Log.d("ERROR", "Wrong: " + status);
            }
        } catch (MalformedURLException e) {

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private void saveData(String data){
        ContentResolver cr = getContentResolver();
        ContentValues v  = new ContentValues();
        v.put(Contract.Todos.Fields.TITLE,data);
        cr.insert(Contract.Todos.CONTENT_URI,v);
    }
}
