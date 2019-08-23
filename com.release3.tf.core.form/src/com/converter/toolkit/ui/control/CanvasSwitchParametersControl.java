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

import com.converter.toolkit.ui.custom.CanvasSwitchParameter;
import com.converter.toolkit.ui.custom.ParamInterface;

public class CanvasSwitchParametersControl {
	
	private String filter;
	private String order;
	private List<CanvasSwitchParameter> pls;
	private int currentRow;
	private Object masterObject = null;

	

	public CanvasSwitchParametersControl(Object masterObject) {
		this.masterObject =  masterObject;
		currentRow = 0;
        pls = ((ParamInterface)masterObject).getCanvasSwitchParameters();
	}

	private void makeIterator() {
    }
	
	public List getIterator() {
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
		return pls.get(currentRow);
	}

	public boolean getNextDisabled() {
		return false;
	}

	public boolean getPrevDisabled() {
		return false;
	}

	public void save() throws Exception {
		// ((ParamInterface) dc.getCurrentRow())
		// .setParameters((Vector<Parameter>) pls);
//		((ParamInterface) UISettings.getUISettingsBean().getTriggersControl()
//				.getCurrentRow()).setParameters((Vector<Parameter>) pls);
//		 UISettings.getUISettingsBean().getTriggersControl().save();

	}

	public void create() throws Exception {
		currentRow = pls.size();
		pls.add(currentRow, new CanvasSwitchParameter());
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
