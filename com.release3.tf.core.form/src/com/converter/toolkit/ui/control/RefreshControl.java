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
package com.converter.toolkit.ui.control;

import java.util.List;
import java.util.Vector;

import com.converter.toolkit.ui.custom.ParamInterface;
import com.converter.toolkit.ui.custom.Refresh;
import com.converter.toolkit.ui.custom.Trigger;

@Deprecated
public class RefreshControl {

	private String filter;
	private String order;
	private List<Refresh> pls;
	private int currentRow;

	private Trigger masterObject = null;

	public RefreshControl(Trigger masterObject) {
		this.masterObject =  masterObject;
		
	}

	
	private List<Refresh> makeIterator() {
		pls = masterObject.getRefresh();
		return pls;
	}

	public List<Refresh> getIterator() {
		return makeIterator();
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public String getFilter() {
		return filter;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getOrder() {
		return order;
	}

	public void rowSelection(int rowIndex) {
		currentRow = rowIndex;
	}

	public int getCurrentIndex() {
		return currentRow;
	}

	public Object getCurrentRow() {
		return pls.get(currentRow);
	}

//	public void save() throws Exception {
//		masterObject.setRefresh((Vector<Refresh>) pls);
//	}

	public void create() throws Exception {
		pls.add(new Refresh());
	}

	public Object getObject() {
		return null;
	}

}
