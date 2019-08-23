/*******************************************************************************
 * Copyright (c) 2009, 2013 ObanSoft Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Jayansh Shinde
 *******************************************************************************/
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
