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

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.converter.toolkit.ui.FormsObject;
import com.oracle.xmlns.forms.Block;
import com.oracle.xmlns.forms.Item;
import com.oracle.xmlns.forms.Module;

public class ItemsControl {

	private Module modelFormsModel;
	private String filter;
	private String order;
	private int currentRow;

	public ItemsControl(Module model) {

		currentRow = 0;
		modelFormsModel = model;

	}

	public List<Item> getIterator() {
		Vector<Item> pls = new Vector<Item>();

		Iterator itr = modelFormsModel.getFormModule().getChildren().iterator();
		while (itr.hasNext()) {
			FormsObject obj = (FormsObject) itr.next();
			if ((obj instanceof Block)) {
				Iterator itmItr = obj.getChildren().iterator();
				while (itmItr.hasNext()) {
					FormsObject o = (FormsObject) itmItr.next();
					if (o instanceof Item) {
						pls.add((Item) o);
					}
				}
			}
		}
		return pls;
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
		return null;
	}

	public boolean getNextDisabled() {
		return false;
	}

	public boolean getPrevDisabled() {
		return false;
	}

	public Object getObject() {
		return null;
	}

	public void refresh() {
	}

	public void registerObject(Object obj, int level) {
	}

	public Object getCurrentRowNoProxy() {
		return null;
	}

	public void removeObject(Object obj, int level) {
	}
}
