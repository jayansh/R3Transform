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
