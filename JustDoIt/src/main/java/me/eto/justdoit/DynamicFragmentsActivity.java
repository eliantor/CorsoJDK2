package me.eto.justdoit;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.RadioGroup;

import me.eto.justdoit.home.DynamicFragment;

/**
 * Created by eto on 10/5/13.
 */
public class DynamicFragmentsActivity extends FragmentActivity {

    private final static int FRAGMENT_HOST = R.id.host;

    private final static String FIRST  = "FIRST";
    private final static String SECOND = "SECOND";
    private final static String THIRD  = "THIRD";
    private FragmentManager mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dynamic_activity);
        RadioGroup rg = (RadioGroup) findViewById(R.id.rg_choose_fragment);
        rg.setOnCheckedChangeListener(fCheckedChanged);
        mManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            mManager.beginTransaction()
                    .add(FRAGMENT_HOST,
                            DynamicFragment.create(Color.BLACK),
                            "BLACK")
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void swapColoredFragment(int color, String tag) {
        DynamicFragment f = DynamicFragment.create(color);

        mManager.beginTransaction()
                .replace(FRAGMENT_HOST, f, tag)
                .commit();
    }

    private RadioGroup.OnCheckedChangeListener fCheckedChanged =
            new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.first:
                            swapColoredFragment(Color.BLUE, FIRST);
                            break;
                        case R.id.second:
                            swapColoredFragment(Color.GREEN, SECOND);
                            break;
                        case R.id.third:
                            swapColoredFragment(Color.RED, THIRD);
                            break;
                    }
                }
            };
}
