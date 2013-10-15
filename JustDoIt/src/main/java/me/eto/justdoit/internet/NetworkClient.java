package me.eto.justdoit.internet;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by eto on 10/12/13.
 */
public class NetworkClient extends IntentService {

    public static void download(Context context, String url) {
        context.startService(new Intent(context, NetworkClient.class)
                .setData(Uri.parse(url)
                ));
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
        try {
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
}
