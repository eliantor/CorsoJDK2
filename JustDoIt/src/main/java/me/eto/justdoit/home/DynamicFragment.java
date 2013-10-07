package me.eto.justdoit.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.eto.justdoit.R;

/**
 * Created by eto on 10/5/13.
 */
public class DynamicFragment extends Fragment {

    private final static String COLOR_ARG = "COLOR";

    public static DynamicFragment create(int color) {
        DynamicFragment df = new DynamicFragment();
        Bundle args = new Bundle();
        args.putInt(COLOR_ARG, color);
        df.setArguments(args);
        return df;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dynamic_fragment, container, false);
        View colored = v.findViewById(R.id.colored_view);
        Bundle args = getArguments();
        int color = args.getInt(COLOR_ARG);

        colored.setBackgroundColor(color);
        return v;
    }
}
