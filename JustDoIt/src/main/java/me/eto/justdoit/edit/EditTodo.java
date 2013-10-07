package me.eto.justdoit.edit;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;

import me.eto.justdoit.R;

/**
 * Created by eto on 10/5/13.
 */
public class EditTodo extends ActionBarActivity /*estende a sua volta FragmentActivity*/ {

    private TodoContentHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_todo_activity);
        mHandler = new TodoContentHandler(getContentResolver());

        FragmentManager manager = getSupportFragmentManager();
        EditTodoFragment f = (EditTodoFragment)
                manager.findFragmentById(R.id.EditTodoFragment);
        f.setOnTodoItemCreatedListener(fCallbacks);
    }

    public EditTodoFragment.OnTodoItemCreatedListener
            fCallbacks = new EditTodoFragment.OnTodoItemCreatedListener() {
        @Override
        public void onTodoItemCreated(String title, String note) {
            mHandler.startInsertTodo(EditTodo.this, title, note);
        }
    };

}
