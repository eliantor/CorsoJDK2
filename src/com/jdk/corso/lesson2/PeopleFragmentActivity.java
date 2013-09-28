package com.jdk.corso.lesson2;

import java.util.ArrayList;
import java.util.List;

import com.jdk.corso.R;
import com.jdk.corso.lesson2.PeopleInputFragment.OnNewNameListener;
import com.jdk.corso.lesson2.PeopleListFragment.PeopleProvider;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

public class PeopleFragmentActivity extends FragmentActivity
	implements PeopleProvider{
	private final static String SAVED_DATA="saved_data";
	
	private PeopleListFragment mListFragment;
	private PeopleInputFragment mInput;
	private ArrayList<String> mPeople;
	
	@Override
	protected void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);

		Log.d(Constants.LOG, "activityCreateBegin");
		setContentView(R.layout.people_fragment_activity);
		

		if(savedInstance == null){
			mPeople = StaticData.getInitialData();
		} else {
			mPeople = savedInstance.getStringArrayList(SAVED_DATA);
		}
		Log.d(Constants.LOG, "people init");
		FragmentManager fm = getSupportFragmentManager();
		
		mListFragment = (PeopleListFragment)
				fm.findFragmentById(R.id.PeopleList);
		
		mInput = (PeopleInputFragment)
				fm.findFragmentById(R.id.InputFragment);
		
		mInput.setOnNewNameListener(fListener);

		Log.d(Constants.LOG, "activityCreateEnd");
	}
	
	private void checkIfFragmentAvailable(){
		FragmentManager manager = getSupportFragmentManager();
		Fragment optional = manager.findFragmentById(R.id.PeopleList);
		if(optional != null){
			
		} else{
			
		}
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putStringArrayList(SAVED_DATA, mPeople);
	}
	
	private final OnNewNameListener fListener = new OnNewNameListener() {
		
		@Override
		public void onNewName(String name) {
			addName(name);
		}
	};
	
	void addName(String name){
		mListFragment.addElement(name);
	}

	@Override
	public List<String> supplyPeopleList() {
		return mPeople;
	}
}
