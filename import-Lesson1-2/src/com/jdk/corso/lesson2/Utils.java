
package com.jdk.corso.lesson2;

import com.jdk.corso.R;

import android.content.Context;

public class Utils {
	private static Boolean mTablet;
	
	
	public static boolean isTablet(Context context){
		if(mTablet==null){
			mTablet=context.getResources().getBoolean(R.bool.is_tablet);
		}
		return mTablet;
	}
}
