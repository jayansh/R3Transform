package com.release3.transform.analysis;

import java.util.ArrayList;
import java.util.List;

import com.oracle.xmlns.forms.RecordGroup;

public class RecordGroupAnalysis {
	private List<RecordGroup> recordGroupList;

	public RecordGroupAnalysis() {
		this.recordGroupList = new ArrayList<RecordGroup>();
	}

	public void preAnalysis(RecordGroup recordGroup) {
		recordGroupList.add(recordGroup);
	}
	
	public void setRecordGroupList(List<RecordGroup> recordGroupList) {
		if (recordGroupList != null) {
			this.recordGroupList = recordGroupList;
		} else {
			this.recordGroupList = new ArrayList<RecordGroup>();
		}
	}

	public List<RecordGroup> getRecordGroupList() {
		return recordGroupList;
	}
	
	
}
