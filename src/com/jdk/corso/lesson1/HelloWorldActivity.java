package com.jdk.corso.lesson1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jdk.corso.R;

public class HelloWorldActivity  extends FragmentActivity implements View.OnClickListener{
	
	/**
	 * Request code serve a riconoscere la risposta
	 * di startActivityForResult
	 */
	private final static int REQUEST_CODE = 1;

	/**
	 * Chiavi usate per comunicare con ResponderActivity
	 */
	public final static String REQUEST_PARAMETER = "param";
	public final static String RETURN_VALUE= "upperCase";
	
	/**
	 * Key usata nel bundle
	 */
	private final static String STATE_KEY = "state_key";
	
	private TextView mOutput;
	private EditText mClearMessage;
	private String mState;
	
	@Override
	protected void onCreate(Bundle savedState) {
		super.onCreate(savedState);
		// associamo layout all'activity
		setContentView(R.layout.hello_world_activity);
		
		//recuperare una vista tramite id (associato nel layout)
		mOutput =(TextView) findViewById(R.id.tv_time_output);
		mClearMessage = (EditText)findViewById(R.id.edt_clear_message);
		View button = findViewById(R.id.btn_refresh);
		View clearButton = findViewById(R.id.btn_clear);
		View importButton = findViewById(R.id.btn_import);
		
		// associamo comportamenti ai bottoni
		button.setOnClickListener(this);
		clearButton.setOnClickListener(this);
		importButton.setOnClickListener(this);
		
		// inizializiamo lo stato
		if(savedState == null){
			// parte per iniziativa dell'utente
			updateTime();
		} else{
			// ricreata dal sistema:
			// e' presente STATE_KEY nel Bundle
			mState = savedState.getString(STATE_KEY);
			mOutput.setText(mState);
		}
		
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		// salviamo l'ultimo stato dell'activity
		outState.putString(STATE_KEY, mState);
	}
	
	
	private void clear(){
		String inputFromUser = mClearMessage.getText().toString();
		updateText(inputFromUser);
		mClearMessage.setText("");
	}
	
	private void updateText(String msg){
		mState = msg; //!!!!! salviamo stato volatile
		mOutput.setText(mState);
	}
	
	private void updateTime(){
		long time=System.currentTimeMillis();
		updateText(String.format("Last refresh: %d", time));
	}
	
	private void importResult(){
		Intent intent = new Intent(this,ResponderActivity.class);
		intent.putExtra(REQUEST_PARAMETER, mClearMessage.getText().toString());
		startActivityForResult(intent, REQUEST_CODE);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if ( requestCode == REQUEST_CODE) {
			if ( resultCode == RESULT_OK && data != null){
				String text = data.getStringExtra(RETURN_VALUE);
				if(text != null){
					updateText(text);
				}
			} else{
				Toast.makeText(this, "User cancelled!", Toast.LENGTH_LONG).show();
			}
		} else {
			super.onActivityResult(requestCode, resultCode, data);
		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_clear:
			clear();
			break;
		case R.id.btn_refresh:
			updateTime();
			break;
		case R.id.btn_import:
			importResult();
			break;
		default:
			break;
		}
	}
}
