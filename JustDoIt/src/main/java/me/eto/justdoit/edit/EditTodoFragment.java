package me.eto.justdoit.edit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import me.eto.justdoit.R;

/**
 * Created by eto on 10/5/13.
 */
public class EditTodoFragment extends Fragment {

    private EditText mTitle;
    private EditText mNote;

    static interface OnTodoItemCreatedListener {

        public void onTodoItemCreated(String title, String note);
    }

    private OnTodoItemCreatedListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_add_todo, container, false);
        mTitle = (EditText) v.findViewById(R.id.in_title);
        mNote = (EditText) v.findViewById(R.id.in_note);
        return v;
    }

    public void setOnTodoItemCreatedListener(OnTodoItemCreatedListener listener) {
        mListener = listener;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.done, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mListener != null && item.getItemId() == R.id.action_done) {
            todoCreated();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void todoCreated() {
        String title = mTitle.getText().toString();
        String note = mNote.getText().toString();
        mListener.onTodoItemCreated(title, note);
    }
}
