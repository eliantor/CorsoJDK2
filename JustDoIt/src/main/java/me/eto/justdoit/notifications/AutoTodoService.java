package me.eto.justdoit.notifications;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import me.eto.justdoit.R;
import me.eto.justdoit.home.MainActivity;

/**
 * Created by eto on 10/12/13.
 */
public class AutoTodoService extends Service {

    private final static String TITLE_PARAM = "title";

    private NotificationManager mNotify;

    public static void start(Context context, String text, long when) {
        Intent startMe = new Intent(context, AutoTodoService.class);
        startMe.putExtra(TITLE_PARAM, text);
        if (when == -1) {
            context.startService(startMe);
        } else {
            startDelayed(context, startMe, when);
        }

    }

    private static void startDelayed(Context context, Intent intent, long when) {
        AlarmManager am = (AlarmManager) context.getSystemService(
                Context.ALARM_SERVICE);

        PendingIntent pi =
                PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        am.set(AlarmManager.RTC_WAKEUP, when, pi);

    }

    @Override
    public void onCreate() {
        mNotify = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("LOG", "Start service");
        NotificationCompat.Builder b = new NotificationCompat.Builder(this);
        b.setContentTitle("Ciao!!!!");
        b.setContentText("WOW");
        b.setSmallIcon(R.drawable.ic_launcher);
        PendingIntent action = PendingIntent.getActivity(this, 0, new Intent(this,
                MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        b.setContentIntent(action);
        b.setAutoCancel(true);
        Notification notification = b.build();
        mNotify.notify(1, notification);

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
