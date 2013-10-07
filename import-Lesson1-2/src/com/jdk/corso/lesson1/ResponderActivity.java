package com.jdk.corso.lesson1;

import com.jdk.corso.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class ResponderActivity extends FragmentActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.responder_activity);
		findViewById(R.id.btn_upper).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				doUpper();
			}
		});
	}
	
	private void doUpper(){
		Intent startIntent = getIntent();
		String text = startIntent.getStringExtra(HelloWorldActivity.REQUEST_PARAMETER);
		if (text != null) {
			Intent response = new Intent();
			response.putExtra(HelloWorldActivity.RETURN_VALUE, text.toUpperCase());
			setResult(RESULT_OK,response);
		}
		Log.d("RESPONSE","responding");
		finish();
	}

}
