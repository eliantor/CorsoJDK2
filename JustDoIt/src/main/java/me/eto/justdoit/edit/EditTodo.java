package me.eto.justdoit.edit;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import me.eto.justdoit.R;

/**
 * Created by eto on 10/5/13.
 */
public class EditTodo extends ActionBarActivity /*estende a sua volta FragmentActivity*/
        implements EditTodoWorker.OnTodoAdded {

    private final static String WORKER_TAG = "worker";
    //    private TodoContentHandler mHandler;
    private EditTodoWorker mWorker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_todo_activity);
//        mHandler = new TodoContentHandler(getContentResolver());
        FragmentManager manager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            manager.beginTransaction()
                    .add(new EditTodoWorker(), WORKER_TAG)
                    .commit();
            manager.executePendingTransactions();
        }
        EditTodoFragment f = (EditTodoFragment)
                manager.findFragmentById(R.id.EditTodoFragment);
        f.setOnTodoItemCreatedListener(fCallbacks);
        mWorker = (EditTodoWorker) manager.findFragmentByTag(WORKER_TAG);
    }

    public EditTodoFragment.OnTodoItemCreatedListener
            fCallbacks = new EditTodoFragment.OnTodoItemCreatedListener() {
        @Override
        public void onTodoItemCreated(String title, String note) {
              mWorker.insertTodo(title, note);
        }
    };

    @Override
    public void onTodoAdded(Uri uri) {
        Log.d("RESPONSE", uri.toString());
    }
}
