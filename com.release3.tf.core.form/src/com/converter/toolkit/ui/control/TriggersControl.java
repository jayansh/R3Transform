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

import javax.swing.tree.DefaultMutableTreeNode;

import com.converter.toolkit.ui.FormsObject;
import com.converter.toolkit.ui.custom.CustomizationPreferences;
import com.converter.toolkit.ui.custom.Trigger;
import com.oracle.xmlns.forms.Block;
import com.oracle.xmlns.forms.Item;
import com.release3.tf.core.form.TreeObject;
import com.release3.tf.core.form.UISettings;

public class TriggersControl {

	private String filter;
	private String order;

	private int currentRow;

	private Trigger currenObject = null;

	private String jsf;

	public TriggersControl(DefaultMutableTreeNode node) {

		try {
			FormsObject frmObj1 = ((TreeObject) ((DefaultMutableTreeNode) ((DefaultMutableTreeNode) ((DefaultMutableTreeNode) node
					.getParent()).getParent()).getParent()).getUserObject())
					.getFrmObj();
			FormsObject frmObj2 = ((TreeObject) ((DefaultMutableTreeNode) ((DefaultMutableTreeNode) node
					.getParent()).getParent()).getUserObject()).getFrmObj();
			FormsObject frmObj3 = ((TreeObject) (node.getUserObject()))
					.getFrmObj();

//			if(frmObj3 instanceof ItemTrigger){
//				if(frmObj2 instanceof Block){
//					FormsObject frmObj4 = ((TreeObject) ((DefaultMutableTreeNode) ((DefaultMutableTreeNode) node
//							.getParent())).getUserObject()).getFrmObj();
//					currenObject = findObject((Item) frmObj4, (Block) frmObj2,
//							frmObj3.getName());
//				}
//			}
			if (frmObj2 instanceof Block) {
				currenObject = findObject((Block) frmObj2, frmObj3.getName());
			}
			if (frmObj2 instanceof Item) {
				Block block = (Block) frmObj1;
				currenObject = findObject((Item) frmObj2,block ,
						frmObj3.getName());
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public String getJsf() {
		return jsf;
	}

	public void setJsf(String jsf) {
		this.jsf = jsf;
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
	// if (event.getPhaseId() != PhaseId.INVOKE_APPLICATION) {
	// event.setPhaseId(PhaseId.INVOKE_APPLICATION);
	// event.queue();
	// } else {
	// eventChanged = true;
	// jsf = (String)event.getNewValue();
	// }
	// }

	public int getCurrentIndex() {
		return currentRow;
	}

	public Object getCurrentRow(DefaultMutableTreeNode node) {

		FormsObject frmObj1 = ((TreeObject) ((DefaultMutableTreeNode) ((DefaultMutableTreeNode) ((DefaultMutableTreeNode) node
				.getParent()).getParent()).getParent()).getUserObject())
				.getFrmObj();
		FormsObject frmObj2 = ((TreeObject) ((DefaultMutableTreeNode) ((DefaultMutableTreeNode) node
				.getParent()).getParent()).getUserObject()).getFrmObj();
		FormsObject frmObj3 = ((TreeObject) (node.getUserObject())).getFrmObj();

		if (frmObj2 instanceof Block) {
			currenObject = findObject((Block) frmObj2, frmObj3.getName());
		}
		if (frmObj2 instanceof Item) {
			currenObject = findObject((Item) frmObj2, (Block) frmObj1, frmObj3
					.getName());
		}

		return currenObject;
	}

	public void save() throws Exception {
		UISettings.getUISettingsBean().getTreeControl().getCustomizationModel()
				.addTrigger(currenObject);
		UISettings.getUISettingsBean().getTreeControl().getCustomizationModel()
				.saveDocument();
	}

	public Object getObject() {
		return currenObject;
	}

	private Trigger findObject(Item item, Block block, String jsfevent) {

		// CustomizationPreferences modelFormsModel=
		// ((Trees_Control)(bf.getTree())).getCustomizationModel();
		CustomizationPreferences modelFormsModel = UISettings
				.getUISettingsBean().getTreeControl().getCustomizationModel();

		if (modelFormsModel.getTrigger() == null)
			return new Trigger(block.getName(), item.getName(), jsfevent);
		Iterator<Trigger> itr = modelFormsModel.getTrigger().iterator();
		while (itr.hasNext()) {
			Trigger trg = (Trigger) itr.next();

			if (trg!=null && trg.getBlock().equals(block.getName())
					&& trg.getItem()!=null && trg.getItem().equals(item.getName())) {
				if ((jsfevent != null) && (trg.getJsfattr().equals(jsfevent)))
					return trg;
				if (jsfevent == null)
					return trg;

			}
		}

		return new Trigger(block.getName(), item.getName(), jsfevent);
	}

	private Trigger findObject(Block block, String jsfevent) {

		// CustomizationPreferences modelFormsModel=
		// ((Trees_Control)(bf.getTree())).getCustomizationModel();
		CustomizationPreferences modelFormsModel = UISettings
				.getUISettingsBean().getTreeControl().getCustomizationModel();

		if (modelFormsModel.getTrigger() == null)
			return new Trigger(block.getName(), null, jsfevent);
		Iterator<Trigger> itr = modelFormsModel.getTrigger().iterator();
		while (itr.hasNext()) {
			Trigger trg = itr.next();

			if ((trg.getBlock() == block.getName())
					|| trg.getBlock().equals(block.getName())) {
				if ((jsfevent != null) && (trg.getJsfattr().equals(jsfevent)))
					return trg;
				if (jsfevent == null)
					return trg;

			}
		}

		return new Trigger(block.getName(), null, jsfevent);
	}

	public void registerObject(Object obj, int level) {
	}

	public Object getCurrentRowNoProxy() {
		return null;
	}

	public void removeObject(Object obj, int level) {
	}
}
