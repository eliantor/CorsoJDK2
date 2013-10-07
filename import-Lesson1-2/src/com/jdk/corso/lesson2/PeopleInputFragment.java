package com.jdk.corso.lesson2;

import com.jdk.corso.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class PeopleInputFragment extends Fragment{
	
	public static interface OnNewNameListener{
		public void onNewName(String name);
	}
	
	private EditText mInput;
	private OnNewNameListener mOnNewNameListener;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		Log.d(Constants.LOG, "fragment2Begin");
		View v = inflater.inflate(R.layout.input_layout, container,false);
		mInput = (EditText)v.findViewById(R.id.edt_name);
		v.findViewById(R.id.btn_add).setOnClickListener(fListener);

		Log.d(Constants.LOG, "fragment2End");
		return v;
	}
	
	private View.OnClickListener fListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			String text = mInput.getText().toString();
			mInput.setText(null);
			Log.d(Constants.LOG, "Clicked text: "+text);
			
			PeopleFragmentActivity owner = (PeopleFragmentActivity)getActivity();
			owner.addName(text);
			
			if (mOnNewNameListener!=null) {
				mOnNewNameListener.onNewName(text);
			}
		}
	};

	public void setOnNewNameListener(OnNewNameListener listener) {
		mOnNewNameListener = listener;
	}
}
