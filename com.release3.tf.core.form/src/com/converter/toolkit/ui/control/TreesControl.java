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

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;

import com.converter.toolkit.ui.FormsObject;
import com.converter.toolkit.ui.custom.CustomizationPreferences;
import com.converter.toolkit.ui.custom.Trigger;
import com.oracle.xmlns.forms.Block;
import com.oracle.xmlns.forms.Item;
import com.oracle.xmlns.forms.Module;
import com.oracle.xmlns.forms.RecordGroup;
import com.release3.tf.core.form.BlockTrigger;
import com.release3.tf.core.form.InputFileData;
import com.release3.tf.core.form.ItemTrigger;
import com.release3.tf.core.form.Settings;
import com.release3.tf.core.form.TreeObject;
import com.release3.transform.constants.UIConstants;

public class TreesControl {
	private CustomizationPreferences modelCustomization;
	private DefaultMutableTreeNode rootNode;
	private String filter;
	private String order;
	private List<Trigger> pls;
	private InputFileData inputForm;
	private int currentRow;
	private DefaultMutableTreeNode currenObject = null;
	private JAXBElement<?> poElement;
	private Unmarshaller u;
	private String jsf;
	private int idCount;
	private Module modelFormsModel;
	private Module globalModel;

	public TreesControl(File formFile) {
		inputForm = new InputFileData(formFile.getAbsolutePath());
	}

	public CustomizationPreferences getCustomizationModel() {
		return modelCustomization;
	}

	public Unmarshaller getU() {
		return u;
	}

	public Module getModel() {
		return modelFormsModel;
	}

	private List makeIterator() {
		return null;
	}

	public List getIterator() {
		return modelFormsModel.getFormModule().getChildren();
	}

	public DefaultTreeModel getTree() {

		// if ((masterObject == null) ||
		// (!masterObject.equals(dc.getCurrentRow())) ||
		// (refresh != ((InputFileData)(dc.getCurrentRow())).getRefresh())) {
		// currentRow = 0;
		// masterObject = (InputFileData)dc.getCurrentRow();
		// refresh = masterObject.getRefresh();
		// init();
		// rootNode = addNode(modelFormsModel.getFormModule());
		// currenObject = rootNode;
		// }

		return new DefaultTreeModel(rootNode);
	}

	public void setFilter(String filter) {
	}

	public String getFilter() {
		return filter;
	}

	public void setOrder(String order) {
	}

	public String getOrder() {
		return order;
	}

	public void rowSelection(int rowIndex) {
		currentRow = rowIndex;
	}

	// public void rowSelectiion(ActionEvent event) {
	// if (event.getPhaseId() != PhaseId.INVOKE_APPLICATION) {
	// event.setPhaseId(PhaseId.INVOKE_APPLICATION);
	// event.queue();
	// } else {
	// String name = getRequestParameter("objectName");
	// String type = getRequestParameter("objectType");
	// String parentName = getRequestParameter("parentName");
	// String id = getRequestParameter("id");
	// currenObject = findObject(id);
	// }
	//
	// }

	public int getCurrentIndex() {
		return currentRow;
	}

	public Object getCurrentRow() {
		this.getTree();
		return currenObject;
	}

	// public void next(ActionEvent event) {
	// if (pls == null) {
	// return;
	// }
	//
	// if ((currentRow + 1) < pls.size()) {
	// currentRow++;
	// }
	// }

	public boolean getNextDisabled() {
		return false;
	}

	// public void prev(ActionEvent event) {
	// }

	public boolean getPrevDisabled() {
		return false;
	}

	// public void save(ActionEvent event) throws Exception {
	// }

	public void create() throws Exception {
		FormsObject obj = ((TreeObject) (this.currenObject.getUserObject()))
				.getFrmObj();
		if ((obj instanceof BlockTrigger) || (obj instanceof ItemTrigger)) {
			com.oracle.xmlns.forms.Trigger t = new com.oracle.xmlns.forms.Trigger();
			t.setName(this.getJsf());
			DefaultMutableTreeNode n = makeNode(t,
					(DefaultMutableTreeNode) this.getCurrentRow());
		}

	}

	public Object getObject() {
		return null;
	}

	public void refresh() {
	}

	private DefaultMutableTreeNode findObject(String id) {

		Enumeration nodes = this.rootNode.depthFirstEnumeration();
		while (nodes.hasMoreElements()) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) nodes
					.nextElement();
			int idObject = ((TreeObject) node.getUserObject()).getId();

			if ((new Integer(id)).intValue() == idObject)
				return node;
		}
		return null;
	}

	public DefaultMutableTreeNode addNode() {
		FormsObject model = modelFormsModel.getFormModule();
		rootNode = new DefaultMutableTreeNode();
		TreeObject treeObject = new TreeObject(rootNode);
		rootNode.setUserObject(treeObject);
		treeObject.setFrmObj(model);
		treeObject.setText(model.getName());

		DefaultMutableTreeNode blockNode = new DefaultMutableTreeNode();
		treeObject = new TreeObject(blockNode);
		blockNode.setUserObject(treeObject);
		FormsObject formObject = new FormsObject();
		formObject.setName("Blocks");
		treeObject.setFrmObj(formObject);
		treeObject.setText(formObject.getName());
		treeObject.setId(idCount);
		idCount++;

		rootNode.add(blockNode);

		addNodeByType(blockNode, model, 1);

		DefaultMutableTreeNode rcdGrpNode = new DefaultMutableTreeNode();
		treeObject = new TreeObject(rcdGrpNode);
		rcdGrpNode.setUserObject(treeObject);
		formObject = new FormsObject();
		formObject.setName("Record Groups");
		treeObject.setFrmObj(formObject);
		treeObject.setText(formObject.getName());
		treeObject.setId(idCount);
		idCount++;

		rootNode.add(rcdGrpNode);

		addNodeByType(rcdGrpNode, model, 2);

		return rootNode;
	}

	public DefaultMutableTreeNode makeNode(FormsObject formObject,
			DefaultMutableTreeNode parent) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode();

		TreeObject treeObject = new TreeObject(node);
		node.setUserObject(treeObject);
		treeObject.setFrmObj(formObject);
		treeObject.setText(formObject.getName());
		treeObject.setId(idCount);
		idCount++;

		// non-employee node or branch
		// if (formObject instanceof Item) {
		// node.setAllowsChildren(false);
		// }
		// // employee node
		// else {
		node.setAllowsChildren(true);
		// }
		// finally add the node to the parent.
		parent.add(node);
		return node;
	}

	public DefaultMutableTreeNode addNodeByType(DefaultMutableTreeNode parent,
			FormsObject formObject, int objFilter) {
		DefaultMutableTreeNode node = null;

		List ls = formObject.getChildren();
		// List ls = getIterator();
		if (ls == null)
			return null;
		Iterator itr = ls.iterator();
		while (itr.hasNext()) {
			Object obj = itr.next();
			switch (objFilter) {
			case 1:
				if ((obj instanceof Block) || (obj instanceof Item)) {
					if (obj instanceof Block) {
						if (((Block) obj).getName().equals("VIRTUAL_BLOCK"))
							break;
						if (((Block) obj).getName().equals("GLOBAL"))
							break;
					}
					node = makeNode((FormsObject) obj, parent);
					addTriggers(node, (FormsObject) obj);
					addNodeByType(node, (FormsObject) obj, objFilter);
				}
				break;
			case 2:
				if (obj instanceof RecordGroup) {
					node = makeNode((FormsObject) obj, parent);
					addNodeByType(node, (FormsObject) obj, objFilter);
				}

			}

		}
		return node;
	}

	private void addTriggers(DefaultMutableTreeNode parent,
			FormsObject formObject) {

		FormsObject trg = null;
		//
		if (formObject instanceof Item) {
			trg = new ItemTrigger();

		}

		if (formObject instanceof Block) {
			trg = new BlockTrigger();
		}

		trg.setName("Triggers");
		DefaultMutableTreeNode trgNode = makeNode(trg, parent);
		Iterator itr = this.getCustomizationModel().getTrigger().iterator();
		while (itr.hasNext()) {
			Trigger trigger = (Trigger) itr.next();
			if (formObject instanceof Block) {
				if (trigger.getBlock().equals(formObject.getName())) {
					// if ((trigger.getJsfattr().equals("preQuery"))
					// || (trigger.getJsfattr().equals("postQuery"))
					// || (trigger.getJsfattr().equals("preInsert"))
					// || (trigger.getJsfattr().equals("preUpdate"))
					// || (trigger.getJsfattr().equals("preRemove"))
					// || (trigger.getJsfattr().equals("Init")))
					if (isCrudTrigger(trigger.getJsfattr())) {
						com.oracle.xmlns.forms.Trigger t = new com.oracle.xmlns.forms.Trigger();
						t.setName(trigger.getJsfattr());
						DefaultMutableTreeNode n = makeNode(t, trgNode);
					}
				}
			} else if (formObject instanceof Item) {

				FormsObject parentFormObject = ((TreeObject) (((DefaultMutableTreeNode) (parent
						.getParent())).getUserObject())).getFrmObj();
				if (trigger.getItem() != null) {
					if ((trigger.getItem().equals(formObject.getName()))
							&& (trigger.getBlock().equals((parentFormObject
									.getName())))) {

						if ((trigger.getJsfattr().equals("actionListener"))
//								|| (trigger.getJsfattr().equals("preForm"))
								|| (trigger.getJsfattr()
										.equals("valueChangeListener"))
								|| (trigger.getJsfattr().equals("validator"))
								|| (trigger.getJsfattr()
										.equals("whenValidateItem"))
								|| (trigger.getJsfattr().equals("postChange"))) {
							com.oracle.xmlns.forms.Trigger t = new com.oracle.xmlns.forms.Trigger();
							t.setName(trigger.getJsfattr());
							DefaultMutableTreeNode n = makeNode(t, trgNode);
						}
					}
				}
			}
		}

	}

	public void init() {
		String physicalPath = null;
		String fileName = null;
		String applicationName = null;

		try {

			idCount = 0;

			physicalPath = inputForm.getAbsolutePath();
			fileName = inputForm.getName();

			applicationName = Settings.getSettings().getApplicationName();
			fileName = fileName.substring(0, fileName.indexOf("."));
			physicalPath = physicalPath.substring(0,
					physicalPath.indexOf(fileName) - 1);

			modelCustomization = new CustomizationPreferences();
			modelCustomization.setDirPath(physicalPath);
			modelCustomization.setFormName(fileName);
			modelCustomization = (CustomizationPreferences) modelCustomization
					.loadDocument();
			modelCustomization.setDirPath(physicalPath);
			modelCustomization.setFormName(fileName);
		} catch (Exception e) {
		}
		try {

			// ValueBinding binding = app.createValueBinding("#{popup}");
			// Settings set = (Settings)binding.getValue(context);
			Settings settings = Settings.getSettings();

			File f = new File(settings.getBaseDir() + "\\ViewController\\src\\"
					+ applicationName + "\\" + fileName + "\\" + fileName
					+ "_2.xml");

			// create a JAXBContext capable of handling classes generated into
			// the primer.po package
			JAXBContext jc = JAXBContext.newInstance("com.oracle.xmlns.forms");

			// create an Unmarshaller
			u = jc.createUnmarshaller();

			// unmarshal a po instance document into a tree of Java content
			// objects composed of classes from the primer.po package.
			// InputStream io = this.getClass().getResourceAsStream(resource);

			InputStream io = new FileInputStream(f);

			poElement = (JAXBElement<?>) u.unmarshal(io);
			modelFormsModel = (Module) poElement.getValue();

			f = new File(settings.getBaseDir() + "\\ViewController\\src\\"
					+ applicationName + "\\GLOBAL\\GLOBAL.xml");
			if (f.exists()) {
				io = new FileInputStream(f);

				poElement = (JAXBElement<?>) u.unmarshal(io);
				globalModel = (Module) poElement.getValue();

				Iterator<FormsObject> itr = globalModel.getFormModule()
						.getChildren().iterator();
				while (itr.hasNext()) {
					FormsObject obj = itr.next();
					if (obj instanceof Block) {
						modelFormsModel.getFormModule().getChildren().add(obj);
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isCrudTrigger(String jsfAttr) {
		for (String triggerName : UIConstants.BLOCK_TRIGGER_COMBO_OPTIONS) {
			if (triggerName.equals(jsfAttr)) {
				return true;
			}
		}
		return false;
	}

	public void registerObject(Object obj, int level) {
	}

	public Object getCurrentRowNoProxy() {
		return null;
	}


	public void removeObject(Object obj, int level) {
	}

	public String getJsf() {
		return jsf;
	}

	public void setJsf(String jsf) {
		this.jsf = jsf;
	}
}
