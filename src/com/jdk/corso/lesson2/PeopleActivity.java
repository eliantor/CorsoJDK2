package com.jdk.corso.lesson2;

import java.util.ArrayList;
import java.util.List;

import com.jdk.corso.R;
import com.jdk.corso.lesson2.PeopleListFragment.PeopleProvider;

import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class PeopleActivity extends FragmentActivity
	implements PeopleProvider{
	private final static String SAVED_DATA="data";
	
	private ListView mListView;
	private PeopleAdapter mAdapter;
	private ArrayList<String> mPeople;
	private EditText mInput;
	
	@Override
	protected void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
		setContentView(R.layout.people_actvity);
		mListView = (ListView)findViewById(R.id.lv_people);
		mInput = (EditText)findViewById(R.id.edt_name);
		findViewById(R.id.btn_add).setOnClickListener(fClick);
		// otteniamo dati in un qualsiasi modo dipendente dall'applicazione
		if(savedInstance == null){
			mPeople = StaticData.getInitialData();
		} else {
			mPeople = savedInstance.getStringArrayList(SAVED_DATA);
		}
		
		// creiamo l'adapter
		mAdapter = new PeopleAdapter(this, mPeople);
		// lo settiamo all'adapterview
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(fItemClick);
		
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putStringArrayList(SAVED_DATA, mPeople);
	}
	
	private final View.OnClickListener fClick = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			mPeople.add(mInput.getText().toString());
			mAdapter.notifyDataSetChanged();
		}
	};
	
	private final OnItemClickListener fItemClick = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> list, View riga, int position, long id) {
			String item = mAdapter.getItem(position);
			Toast.makeText(PeopleActivity.this, item, Toast.LENGTH_SHORT).show();
		};
	};

	@Override
	public List<String> supplyPeopleList() {
		// TODO Auto-generated method stub
		return null;
	}

}
