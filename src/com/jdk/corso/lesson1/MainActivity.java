package com.jdk.corso.lesson1;

import com.jdk.corso.R;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;

public class MainActivity  extends FragmentActivity{
	private final Callbacks fCallbacks = new Callbacks();
	
	private final static String TAG = MainActivity.class.getSimpleName();
	
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		View v =findViewById(R.id.btn_start_hello);
		v.setOnClickListener(fCallbacks);
		v.setOnLongClickListener(fCallbacks);
	}

	private class Callbacks implements OnClickListener, OnLongClickListener {
		
		@Override
		public void onClick(View v) {
			Intent startHello = new Intent(MainActivity.this,HelloWorldActivity.class);
			startActivity(startHello);
		}
		
		@Override
		public boolean onLongClick(View v) {
			Log.d(TAG, "Long pressed!");
			Log.wtf(TAG, "Really????");
			return true;
		}
	}
	
}
