package com.jdk.corso.lesson2;

import java.util.List;

import com.jdk.corso.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class PeopleListFragment extends Fragment{
	private List<String> mPeople;
	private ListView mListView;
	private PeopleAdapter mAdapter;
	
	public static interface PeopleProvider{
		public List<String> supplyPeopleList();
	}
	private PeopleProvider mProvider;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		Log.d(Constants.LOG, "onAttachBegin");
		try{
			mProvider = (PeopleProvider)activity;

			Log.d(Constants.LOG, "onAttachOk");
		}catch (ClassCastException e) {
			throw new ClassCastException("Activity must support PeopleProvider");
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.people_list, container,false);

		Log.d(Constants.LOG, "fragmentCreateBegin");
		
		mListView = (ListView)v.findViewById(R.id.lv_people);
		

		Log.d(Constants.LOG, "fragmentCreateEnd");
		return v;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		Log.d(Constants.LOG, "activity ready!!!");
		mPeople = mProvider.supplyPeopleList();
		mAdapter = new PeopleAdapter(getActivity(), mPeople);
		mListView.setAdapter(mAdapter);
	
	}
	
	public void addElement(String text){
		if (!TextUtils.isEmpty(text)) {
			mPeople.add(text);
			mAdapter.notifyDataSetChanged();
		}
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
		mProvider = null;
	}
	
}
