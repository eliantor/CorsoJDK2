package me.eto.justdoit.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import me.eto.justdoit.R;

/**
 * Created by eto on 10/5/13.
 */
public class ListTodosFragment extends Fragment {

    private ListView mList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_main, container, false);
        mList = (ListView) v.findViewById(R.id.lv_todos);

        return v;
    }
}
