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
