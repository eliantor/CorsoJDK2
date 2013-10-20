package me.eto.justdoit.home;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import me.eto.justdoit.R;
import me.eto.justdoit.edit.EditTodo;
import me.eto.justdoit.globals.JustDoIt;
import me.eto.justdoit.internet.NetworkClient;
import me.eto.justdoit.notifications.AutoTodoService;

public class MainActivity extends ActionBarActivity {

    private final static String       REPLY_CHANNEL = MainActivity.class.getName() + ".reply";
    private final static IntentFilter FILTER        = new IntentFilter(REPLY_CHANNEL);

    private class Receiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }

    private final static int CREATE_TODO_REQUEST = 1;
    private final static int EDIT_TODO_REQUEST   = 2;


    private Receiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        /* talk with my application*/
        Application app = getApplication();
        JustDoIt myApp = (JustDoIt) app;
        JustDoIt myAppAlt = JustDoIt.get();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mReceiver =new Receiver();

        final Intent lastEvent = this.registerReceiver(mReceiver, FILTER);
        //,"pemission", new Handler());
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver,FILTER);

    }


    @Override
    protected void onPause() {
        super.onPause();
//        unregisterReceiver(mReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
        mReceiver = null;
    }

    /**
     * We can use this over fragment on attach
     * to let the activity decide how to handle
     * fragments callbacks
     *
     * @param fragment
     */
    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof ListTodosFragment) {
            // when the listTodoFragment is attached
            ListTodosFragment targetFragment = (ListTodosFragment) fragment;
            // we set the listener
            targetFragment.setOnTodoItemActionListener(fTodoActionListener);
        }
    }

    private final ListTodosFragment.OnTodoItemActionListener fTodoActionListener
            = new ListTodosFragment.OnTodoItemActionListener() {
        @Override
        public void onTodoItemSelected(Uri todoItemUri) {

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            // todo add action
            startActivityForResult(new Intent(this, EditTodo.class), CREATE_TODO_REQUEST);
        } else if (item.getItemId() == R.id.action_start_service) {
            AutoTodoService.start(this, "Palla", 1000);
            NetworkClient.download(this, "http://adroid-eliantor.rhcloud.com/todos",REPLY_CHANNEL);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
