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

import javax.swing.tree.DefaultMutableTreeNode;

import com.converter.toolkit.ui.custom.BlockCust;
import com.converter.toolkit.ui.custom.CustomizationPreferences;
import com.converter.toolkit.ui.custom.Trigger;
import com.release3.tf.core.form.TreeObject;
import com.release3.tf.core.form.UISettings;

public class BlockCustsControl {

	private String filter;
	private String order;
	private List<Trigger> pls;
	private int currentRow;

	private BlockCust currenObject = null;
	private Object masterObject = null;


	public BlockCustsControl(DefaultMutableTreeNode node) {

		currentRow = 0;
		String type = ((TreeObject) node.getUserObject()).getFrmObj().getType();
		String name = ((TreeObject) node.getUserObject())
		.getFrmObj().getName();
		currenObject = findObject(name);

	}

	private List makeIterator() {
		return null;
	}

	public List getIterator() {
		return null;
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

	// public void rowSelectiion(ValueChangeEvent event) {
	// String jsfevent = (String)event.getNewValue();
	// DefaultMutableTreeNode node =
	// (DefaultMutableTreeNode)dc.getCurrentRow();
	// String type = ((TreeObject)node.getUserObject()).getFrmObj().getType();
	// currenObject =
	// findObject(((TreeObject)node.getUserObject()).getFrmObj().getName());
	// }

	public int getCurrentIndex() {
		return currentRow;
	}

	public Object getCurrentRow(DefaultMutableTreeNode node) {

		currenObject = findObject(((TreeObject) node.getUserObject())
				.getFrmObj().getName());

		return currenObject;
	}


	public boolean getNextDisabled() {
		return false;
	}


	public boolean getPrevDisabled() {
		return false;
	}

	public void save() throws Exception {
		UISettings.getUISettingsBean().getTreeControl().getCustomizationModel()
				.addBlockCust(currenObject);
		UISettings.getUISettingsBean().getTreeControl().getCustomizationModel()
				.saveDocument();
		//
		// ((Trees_Control)(bf.getTree())).getCustomizationModel().addBlockCust(currenObject);
		// ((Trees_Control)(bf.getTree())).getCustomizationModel().saveDocument();
	}

	public void create() throws Exception {
	}

	public Object getObject() {
		return currenObject;
	}

	public void refresh() {
	}

	private BlockCust findObject(String name) {

		CustomizationPreferences modelFormsModel = UISettings.getUISettingsBean().getTreeControl().getCustomizationModel();
		if (modelFormsModel.getBlockCust() == null)
			return new BlockCust(name);
		Iterator itr = modelFormsModel.getBlockCust().iterator();
		while (itr.hasNext()) {
			BlockCust rg = (BlockCust) itr.next();
			if (rg.getName().equals(name)) {
				return rg;
			}
		}
		return new BlockCust(name);
	}

	public void removeObject(Object obj, int level) {
	}
}
