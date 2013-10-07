package com.jdk.corso.lesson2;

import java.util.ArrayList;
import java.util.List;

public class StaticData {

	private static final List<String> DATA = new ArrayList<String>();
	static{
		DATA.add("Franco Pellegrino");
		DATA.add("Marco Ciampini");
		DATA.add("Piergiorgio Cantagallo");
		DATA.add("Mariano Concilio");
		DATA.add("Daniel Di Gennaro");
		DATA.add("Adam Ebrahim");
		DATA.add("Luca Zicari");
		DATA.add("Massimiliano Finzi");
	}
	
	
	public static ArrayList<String> getInitialData(){
		ArrayList<String> ret = new ArrayList<String>();
		ret.addAll(DATA);
		return ret;
	}
			
}
