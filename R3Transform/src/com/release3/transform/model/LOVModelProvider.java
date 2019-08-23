package com.release3.transform.model;

import java.util.ArrayList;
import java.util.List;

import com.release3.transform.lov.LOVRecordGroupModelList;

public class LOVModelProvider {
	private List<LOVRecordGroupModelList.LOVRecordGroupModel> lovRgList;
	private static LOVModelProvider content;

	private LOVModelProvider() {
		lovRgList = new ArrayList<LOVRecordGroupModelList.LOVRecordGroupModel>();
	}

	public static LOVModelProvider getInstance() {
		if (content != null) {
			return content;
		} else {
			content = new LOVModelProvider();
			return content;
		}
	}

	public List<LOVRecordGroupModelList.LOVRecordGroupModel> getLovRgList() {
		return lovRgList;
	}

	public void setLovRgList(List<LOVRecordGroupModelList.LOVRecordGroupModel> lovRgList) {
		if (lovRgList != null) {
			if (this.lovRgList.size() > 0) {
				this.lovRgList.clear();
			}
			this.lovRgList = lovRgList;
		} else
			this.lovRgList = new ArrayList<LOVRecordGroupModelList.LOVRecordGroupModel>();

	}

}
