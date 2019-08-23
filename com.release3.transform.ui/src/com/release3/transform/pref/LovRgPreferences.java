package com.release3.transform.pref;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LovRgPreferences {
	private int rowId;
	private ArrayList<String> values;
	private Map<String,String> properties;

	public LovRgPreferences(){
		properties = new HashMap<String, String>();
	}
	
	public ArrayList<String> getValues() {
		return values;
	}

	public void setValues(ArrayList<String> values) {
		this.values = values;
	}

	
	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

	public int getRowId() {
		return rowId;
	}

	public void setRowId(int rowId) {
		this.rowId = rowId;
	}

	

}
