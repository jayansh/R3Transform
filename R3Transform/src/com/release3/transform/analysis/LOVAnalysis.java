package com.release3.transform.analysis;

import java.util.ArrayList;
import java.util.List;

import com.oracle.xmlns.forms.LOV;

public class LOVAnalysis {
	private List<LOV> lovList;
	
	
	public LOVAnalysis() {
		this.lovList = new ArrayList<LOV>();
		
	}

	public void preAnalysis(LOV lov) {
		lovList.add(lov);
	}
	
	
	public void setLovList(List<LOV> lovList) {
		if (lovList != null) {
			this.lovList = lovList;
		} else {
			this.lovList = new ArrayList<LOV>();
		}
	}

	public List<LOV> getLovList() {
		return lovList;
	}

	
	
	

}
