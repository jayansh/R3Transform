package com.release3.transform;

import java.io.File;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.converter.toolkit.ui.FormsObject;
import com.converter.toolkit.ui.control.BlockCustsControl;
import com.converter.toolkit.ui.control.CanvasSwitchParametersControl;
import com.converter.toolkit.ui.control.ParametersControl;
import com.converter.toolkit.ui.control.RecordGroupsControl;
import com.converter.toolkit.ui.control.SequenceControl;
import com.converter.toolkit.ui.control.TreesControl;
import com.converter.toolkit.ui.control.TriggersControl;
import com.converter.toolkit.ui.custom.BlockCust;
import com.converter.toolkit.ui.custom.RecordGroup;
import com.converter.toolkit.ui.custom.Trigger;
import com.oracle.xmlns.forms.Block;
import com.release3.tf.core.form.BlockTrigger;
import com.release3.tf.core.form.ItemTrigger;
import com.release3.tf.core.form.Settings;
import com.release3.tf.core.form.TreeObject;
import com.release3.tf.core.form.UISettings;
import com.release3.tf.customiz.model.param.ParameterModelProvider;
import com.release3.tf.customiz.model.param.canvas.CanvasSwitchParameterModelProvider;
import com.release3.tf.customiz.model.refresh.RefreshBlockModelProvider;
import com.release3.tf.customiz.model.sequence.SequenceAnalysis;
import com.release3.tf.customiz.model.sequence.SequenceModelProvider;
import com.release3.transform.constants.UIConstants;
import com.release3.transform.ui.BlockCustomization;
import com.release3.transform.ui.BlockTriggerUI;
import com.release3.transform.ui.ItemTriggerUI;
import com.release3.transform.ui.RecordGroupCustomization;
import com.release3.transform.ui.TriggerCustomization;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.core.databinding.beans.PojoObservables;
import org.eclipse.swt.widgets.Button;

public class Helper extends Dialog {

	private TreesControl treeControl;
	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	// Module module;
	// FormModule formModule;
	private DefaultTreeModel defaultTreeModel;
	private BlockTriggerUI blkTrigger;
	private ItemTriggerUI itemTrigger;
	private ScrolledComposite scrolledComposite;
	public static TreeItem currentItem;
	private File formFile;
	private Group group;
	private Tree tree;

	public Helper(Shell parentShell, File formFile) {
		super(parentShell);
		setShellStyle(SWT.CLOSE | SWT.MIN | SWT.MAX | SWT.RESIZE);
		this.formFile = formFile;
		UISettings.getUISettingsBean().clearAll();
		treeControl = new TreesControl(formFile);
		treeControl.init();
		treeControl.addNode();
		defaultTreeModel = treeControl.getTree();
		UISettings.getUISettingsBean().setTreesControl(treeControl);
		Settings.getSettings().setFmbFile(formFile.getName());
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {

		Composite container = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = (GridLayout) container.getLayout();
		gridLayout.numColumns = 2;

		group = new Group(container, SWT.NONE);
		GridData gd_group = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_group.heightHint = 557;
		group.setLayoutData(gd_group);

		// TreeViewer treeViewer = new TreeViewer(group, SWT.BORDER |
		// SWT.V_SCROLL
		// | SWT.H_SCROLL);
		// Tree tree = treeViewer.getTree();

		tree = new Tree(group, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		tree.setBounds(0, 0, 257, 585);
		// initDataBindings();

		TreeItem treeItemForm = new TreeItem(tree, SWT.NONE);
		// treeItemForm.setText("FormName");
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) defaultTreeModel
				.getRoot();
		treeItemForm.setText(root.toString());
		addTreeItem(treeItemForm, root);
		scrolledComposite = new ScrolledComposite(container, SWT.BORDER
				| SWT.H_SCROLL | SWT.V_SCROLL);
		GridData gd_scrolledComposite = new GridData(SWT.FILL, SWT.FILL, true,
				true, 1, 1);
		gd_scrolledComposite.heightHint = 600;
		gd_scrolledComposite.widthHint = 655;
		scrolledComposite.setLayoutData(gd_scrolledComposite);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);

		// TreeItem treeItemBlocks = new TreeItem(treeItemForm, SWT.NONE);
		// treeItemBlocks.setText("Blocks");
		//
		// TreeItem treeItemBlkCounty = new TreeItem(treeItemBlocks, SWT.NONE);
		// treeItemBlkCounty.setText("BLK_COUNTY");
		//
		// TreeItem treeItemSlbStdCtrl = new TreeItem(treeItemBlocks, SWT.NONE);
		// treeItemSlbStdCtrl.setText("SLB_STD_CTRL");
		//
		// treeItemBlkCounty.setExpanded(true);
		// treeItemBlocks.setExpanded(true);
		// treeItemForm.setExpanded(true);

		tree.addListener(SWT.Selection, new Listener() {

			public void handleEvent(Event event) {
				if (event.detail == SWT.CHECK) {
					System.out.println(event.item + " was checked.");
				} else {
					System.out.println(event.item + " was selected");
					TreeItem treeItem = (TreeItem) event.item;
					parentHierarchy(treeItem);
				}

			}
		});

		return container;
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		Button button = createButton(parent, IDialogConstants.OK_ID,
				IDialogConstants.OK_LABEL, true);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				UISettings.getUISettingsBean().save();
			}
		});
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(973, 710);
	}

	private void addTreeItem(TreeItem treeItem,
			DefaultMutableTreeNode defaultTreeNode) {
		for (int i = 0; i < defaultTreeNode.getChildCount(); i++) {
			TreeItem childTreeItem = new TreeItem(treeItem, SWT.NONE);
			DefaultMutableTreeNode child = (DefaultMutableTreeNode) defaultTreeNode
					.getChildAt(i);
			childTreeItem.setText(child.toString());
			addTreeItem(childTreeItem, child);
		}

	}

	DefaultMutableTreeNode blockTrgNode = null;

	private DefaultMutableTreeNode searchNodeByName(String name,
			DefaultMutableTreeNode rootTreeNode) {
		int iteration = 0;
		System.out.println(" child count " + rootTreeNode.getChildCount());
		for (int i = 0; i < rootTreeNode.getChildCount(); i++) {
			DefaultMutableTreeNode child = (DefaultMutableTreeNode) rootTreeNode
					.getChildAt(i);
			System.out.println("i::: " + i);
			System.out.println("iteration::: " + iteration);
			if (child.toString() == name || child.toString().equals(name)) {
				if (rootTreeNode.toString() == "Record Groups"
						|| rootTreeNode.toString().equals("Record Groups")) {
					return child;
				} else {
					return (DefaultMutableTreeNode) child.getFirstChild();
				}
			} else {
				if (blockTrgNode == null) {
					blockTrgNode = searchNodeByName(name, child);
				}
			}

		}
		if (blockTrgNode != null) {
			return (DefaultMutableTreeNode) blockTrgNode.getFirstChild();
		} else {
			return null;
		}

	}

	DefaultMutableTreeNode node = null;

	private DefaultMutableTreeNode searchNodeByName(String name,
			String blockName, DefaultMutableTreeNode rootTreeNode) {

		int iteration = 0;
		System.out.println(" child count " + rootTreeNode.getChildCount());
		for (int i = 0; i < rootTreeNode.getChildCount(); i++) {
			DefaultMutableTreeNode child = (DefaultMutableTreeNode) rootTreeNode
					.getChildAt(i);
			DefaultMutableTreeNode parent = (DefaultMutableTreeNode) child
					.getParent();
			System.out.println("i::: " + i);
			System.out.println("iteration::: " + iteration);
			if ((child.toString() == name || child.toString().equals(name))
					&& (parent.toString() == blockName || parent.toString()
							.equals(blockName))) {
				// node = child;
				return child;
			} else {
				if (node == null) {
					node = searchNodeByName(name, blockName, child);
				}

			}

		}
		return node;
	}

	private DefaultMutableTreeNode searchNodeByName(String name,
			String blockName, String itemName,
			DefaultMutableTreeNode rootTreeNode) {

		int iteration = 0;
		System.out.println(" child count " + rootTreeNode.getChildCount());
		for (int i = 0; i < rootTreeNode.getChildCount(); i++) {
			DefaultMutableTreeNode child = (DefaultMutableTreeNode) rootTreeNode
					.getChildAt(i);
			DefaultMutableTreeNode parent = (DefaultMutableTreeNode) child
					.getParent();
			System.out.println("i::: " + i);
			System.out.println("iteration::: " + iteration);
			if ((child.toString() == name || child.toString().equals(name))
					&& (parent.toString() == itemName || parent.toString()
							.equals(itemName))
					&& (parent.getParent().toString() == blockName || parent
							.getParent().toString().equals(blockName))) {
				// node = child;
				return child;
			} else {
				if (node == null) {
					node = searchNodeByName(name, blockName, itemName, child);
				}

			}

		}
		return node;
	}

	private void parentHierarchy(TreeItem treeItem) {
		// System.out.println("----" + treeItem.getParentItem());
		if (treeItem.getParentItem() != null) {

			TreeItem treeParentItem = treeItem.getParentItem();
			System.out.println(treeParentItem.getText());
			currentItem = treeItem;
			if (treeParentItem.getText().equals("Blocks")
					|| treeParentItem.getText() == "Blocks") {

				FormsObject blockCustObject = new Block();
				drawBlockCustomization(treeItem, blockCustObject);

			} else if (treeItem.getText().equals("Triggers")
					|| treeItem.getText() == "Triggers") {
				if (treeParentItem.getParentItem().getText().equals("Blocks")
						|| treeParentItem.getParentItem().getText() == "Blocks") {
					drawBlockTrigger();

					// TreePath treePath = new TreePath(singlePath);

				} else {
					drawItemTrigger();
				}

			} else if (treeParentItem.getText() == "Record Groups"
					|| treeParentItem.getText().equals("Record Groups")) {
				FormsObject recordGroupObject = new FormsObject();
				drawRecordGroupCustomization(treeItem, recordGroupObject);
			} else if (contains(UIConstants.BLOCK_TRIGGER_COMBO_OPTIONS,
					treeItem.getText())) {
				FormsObject blockTrigger = new BlockTrigger();
				drawTriggerCustomization(treeItem, blockTrigger);
			} else if (contains(UIConstants.ITEM_TRIGGER_COMBO_OPTIONS,
					treeItem.getText())) {
				FormsObject itemTrigger = new ItemTrigger();
				drawTriggerCustomization(treeItem, itemTrigger);
			} else {
				disposeControls();
				System.out.println(UIConstants.BLOCK_TRIGGER_COMBO_OPTIONS
						.equals(treeItem.getText()));
			}
		}
	}

	// private BlockCustomization createBlockCustomization(Composite parent){
	// BlockCustomization blkCustomization = new BlockCustomization(parent,
	// SWT.NONE);
	//
	// return blkCustomization;
	// }

	public void drawBlockCustomization(TreeItem treeItem, FormsObject formObject) {

		Composite composite = new Composite(scrolledComposite, SWT.NONE);
		composite.setLayout(new GridLayout());
		BlockCustomization blockCust = new BlockCustomization(composite,
				SWT.NONE);
		scrolledComposite.setContent(composite);

		DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) (UISettings
				.getUISettingsBean().getTreeControl().getTree().getRoot());
		// DefaultMutableTreeNode currentParentNode = searchNodeByName(treeItem
		// .getParentItem().getText(), rootNode);
		DefaultMutableTreeNode blockNode = (DefaultMutableTreeNode) rootNode
				.getChildAt(0);
		DefaultMutableTreeNode currentNewNode = searchNodeByName(
				treeItem.getText(), blockNode);

		TreeObject treeObject = new TreeObject(currentNewNode);
		currentNewNode.setUserObject(treeObject);

		formObject.setName(treeItem.getText());
		System.out.println(formObject.getType());
		treeObject.setFrmObj(formObject);
		treeObject.setText(treeItem.getText());

		BlockCustsControl blockCustControl = new BlockCustsControl(
				currentNewNode);
		ParametersControl paramControl = new ParametersControl(
				blockCustControl.getObject());
		SequenceControl sequenceControl = new SequenceControl(
				blockCustControl.getObject());
		SequenceAnalysis.getSequenceAnalysis().setSelectedBlock(
				((BlockCust) blockCustControl.getObject()).getName());

		UISettings.getUISettingsBean().setBlockCustsControl(blockCustControl);
		UISettings.getUISettingsBean().setParametersControl(paramControl);
		ParameterModelProvider.getInstance().setParameterList(
				paramControl.getIterator());
		UISettings.getUISettingsBean().setSequenceControl(sequenceControl);
		SequenceModelProvider.getInstance().setSequenceList(
				sequenceControl.getIterator());

		blockCust.loadTables();

	}

	public void drawRecordGroupCustomization(TreeItem treeItem,
			FormsObject formObject) {

		Composite composite = new Composite(scrolledComposite, SWT.NONE);
		composite.setLayout(new GridLayout());
		RecordGroupCustomization recordGroupCust = new RecordGroupCustomization(
				composite, SWT.NONE);
		scrolledComposite.setContent(composite);

		DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) (UISettings
				.getUISettingsBean().getTreeControl().getTree().getRoot());
		DefaultMutableTreeNode recordGroupNode = (DefaultMutableTreeNode) rootNode
				.getChildAt(1);
		DefaultMutableTreeNode currentNewNode = searchNodeByName(
				treeItem.getText(), recordGroupNode);
		//
		TreeObject treeObject = new TreeObject(currentNewNode);
		currentNewNode.setUserObject(treeObject);

		formObject.setName(treeItem.getText());
		System.out.println(formObject.getType());
		treeObject.setFrmObj(formObject);
		treeObject.setText(treeItem.getText());

		RecordGroupsControl recordGrpControl = new RecordGroupsControl(
				currentNewNode);
		ParametersControl paramControl = new ParametersControl(
				recordGrpControl.getObject());

		UISettings.getUISettingsBean().setRecordGroupsControl(recordGrpControl);
		UISettings.getUISettingsBean().setParametersControl(paramControl);
		ParameterModelProvider.getInstance().setParameterList(
				paramControl.getIterator());

		recordGroupCust.loadParamTable();
		if (((RecordGroup) recordGrpControl.getObject()).getTable() != null) {
			recordGroupCust.txtTableName
					.setText(((RecordGroup) recordGrpControl.getObject())
							.getTable());
		}
		if (((RecordGroup) recordGrpControl.getObject()).getSchema() != null) {
			recordGroupCust.txtSchemaName
					.setText(((RecordGroup) recordGrpControl.getObject())
							.getSchema());
		}
		if (((RecordGroup) recordGrpControl.getObject()).getComponentOrderBy() != null) {
			recordGroupCust.btnComponentOrderBy
					.setSelection(((RecordGroup) recordGrpControl.getObject())
							.getComponentOrderBy());
		}

	}

	public void drawTriggerCustomization(TreeItem treeItem,
			FormsObject formObject) {

		Composite composite = new Composite(scrolledComposite, SWT.NONE);
		composite.setLayout(new GridLayout());
		TriggerCustomization trigCust = new TriggerCustomization(composite,
				SWT.NONE);
		scrolledComposite.setContent(composite);

		DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) (UISettings
				.getUISettingsBean().getTreeControl().getTree().getRoot());
		DefaultMutableTreeNode blockGroupNode = (DefaultMutableTreeNode) rootNode
				.getChildAt(0);

		DefaultMutableTreeNode currentParentNode = null;

		if (formObject instanceof ItemTrigger) {
			node = null;
			currentParentNode = searchNodeByName(treeItem.getParentItem()
					.getText(), treeItem.getParentItem().getParentItem()
					.getParentItem().getText(), treeItem.getParentItem()
					.getParentItem().getText(), blockGroupNode);
			System.out.println("currentParentNode:: " + currentParentNode);
		} else {
			blockTrgNode = null;
			currentParentNode = searchNodeByName(treeItem.getParentItem()
					.getParentItem().getText(), blockGroupNode);
		}

		DefaultMutableTreeNode currentNewNode = new DefaultMutableTreeNode();

		TreeObject treeObject = new TreeObject(currentNewNode);
		currentNewNode.setUserObject(treeObject);

		formObject.setName(treeItem.getText());
		System.out.println(formObject.getType());
		treeObject.setFrmObj(formObject);
		treeObject.setText(treeItem.getText());

		currentParentNode.add(currentNewNode);

		TriggersControl triggersControl = new TriggersControl(currentNewNode);
		ParametersControl paramControl = new ParametersControl(
				triggersControl.getObject());
		CanvasSwitchParametersControl canvasSwitchParamControl = new CanvasSwitchParametersControl(
				triggersControl.getObject());
		UISettings.getUISettingsBean().setTriggersControl(triggersControl);
		UISettings.getUISettingsBean().setParametersControl(paramControl);
		UISettings.getUISettingsBean().setCanvasSwitchParametersControl(
				canvasSwitchParamControl);
		Object obj = triggersControl.getObject();

		if (obj instanceof Trigger) {
			Trigger currentTrigger = (Trigger) obj;
			// RefreshControl refreshControl = new
			// RefreshControl(currentTrigger);
			// by default PLSQL Procedure text will be visible

			if (currentTrigger.getMethodType() != null) {
				// if equals to plsqlCall
				if (currentTrigger.getMethodType().equals(
						UIConstants.TRIGGER_CUSTMIZATION_COMBO_OPTIONS[1])) {
					trigCust.lblJavaMethod.setVisible(true);
					trigCust.txtJavaMethod.setVisible(true);
					trigCust.lblPlsql.setVisible(false);
					trigCust.txtPLSQLProcedure.setVisible(false);

				} else {
					trigCust.lblPlsql.setVisible(true);
					trigCust.txtPLSQLProcedure.setVisible(true);
					trigCust.lblJavaMethod.setVisible(false);
					trigCust.txtJavaMethod.setVisible(false);
				}
				if (currentTrigger.getPlsql() != null) {
					trigCust.txtPLSQLProcedure.setText(currentTrigger
							.getPlsql());
				} else if (currentTrigger.getJavaMethod() != null) {
					trigCust.txtPLSQLProcedure.setVisible(false);
					trigCust.txtJavaMethod.setVisible(true);
					trigCust.txtJavaMethod.setText(currentTrigger
							.getJavaMethod());
				}
				for (int i = 0; i < UIConstants.TRIGGER_CUSTMIZATION_COMBO_OPTIONS.length; i++) {
					if (UIConstants.TRIGGER_CUSTMIZATION_COMBO_OPTIONS[i] == currentTrigger
							.getMethodType()
							|| UIConstants.TRIGGER_CUSTMIZATION_COMBO_OPTIONS[i]
									.equals(currentTrigger.getMethodType())) {
						trigCust.cmbCallType.select(i);
					}
				}
			}
			ParameterModelProvider.getInstance().setParameterList(
					paramControl.getIterator());
			CanvasSwitchParameterModelProvider.getInstance().setParameterList(
					canvasSwitchParamControl.getIterator());
			// CanvasSwitchModelProvider.getInstance().setParameterList(paramControl.getIterator());
			// UISettings.getUISettingsBean().setRefreshControl(refreshControl);
			RefreshBlockModelProvider.getInstance().setRefreshList(
					currentTrigger.getRefresh());

		}

		// String blockName = treeItem.getParentItem().getParentItem()
		// .getParentItem().getText();
		// String itemName = null;
		// if (blockName == "Blocks" || blockName.equals("Blocks")) {
		// blockName = treeItem.getParentItem().getParentItem().getText();
		// } else {
		// itemName = treeItem.getParentItem().getParentItem().getText();
		//
		// }
		// ((Trigger) triggersControl.getObject()).setItem(itemName);
		// ((Trigger) triggersControl.getObject()).setBlock(blockName);
		trigCust.loadParamTable();
	}

	public void drawBlockTrigger() {

		Composite composite = new Composite(scrolledComposite, SWT.NONE);
		composite.setLayout(new GridLayout());
		blkTrigger = new BlockTriggerUI(composite, SWT.NONE);
		blkTrigger.getSubmitButton().addSelectionListener(
				new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						String selected = UIConstants.BLOCK_TRIGGER_COMBO_OPTIONS[blkTrigger
								.getCombo().getSelectionIndex()];
						createTrigger(selected);
					}
				});
		scrolledComposite.setContent(composite);

	}

	public void drawItemTrigger() {

		Composite composite = new Composite(scrolledComposite, SWT.NONE);
		composite.setLayout(new GridLayout());
		itemTrigger = new ItemTriggerUI(composite, SWT.NONE);
		itemTrigger.getSubmitButton().addSelectionListener(
				new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						String selected = UIConstants.ITEM_TRIGGER_COMBO_OPTIONS[itemTrigger
								.getCombo().getSelectionIndex()];
						createTrigger(selected);
					}
				});
		scrolledComposite.setContent(composite);

	}

	public void disposeControls() {
		blkTrigger = null;
		itemTrigger = null;
		Composite composite = new Composite(scrolledComposite, SWT.NONE);
		scrolledComposite.setContent(composite);
	}

	public void createTrigger(String name) {
		boolean triggerTypeExist = isTriggerTypeExist(name);
		System.out.println(triggerTypeExist);
		if (!triggerTypeExist) {
			TreeItem newItem = new TreeItem(currentItem, SWT.NONE);
			newItem.setText(name);

			// drawTriggerCustomization();
		}

	}

	public boolean contains(String[] strArray, String strValue) {
		for (String string : strArray) {
			if (string.equals(strValue) || string == strValue) {
				return true;
			}
		}
		return false;
	}

	public boolean isTriggerTypeExist(String selectedTriggerType) {
		TreeItem[] children = currentItem.getItems();
		for (TreeItem child : children) {
			System.out.println(child.getText());
			if (child.getText() == selectedTriggerType
					|| child.getText().equals(selectedTriggerType)) {

				return true;
			}

		}
		return false;

	}

	public void saveTrigger(TreeItem treeItem) {
		DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) (UISettings
				.getUISettingsBean().getTreeControl().getTree().getRoot());
		DefaultMutableTreeNode currentParentNode = searchNodeByName(treeItem
				.getParentItem().getText(), rootNode);
		DefaultMutableTreeNode currentNewNode = new DefaultMutableTreeNode();

		TreeObject treeObject = new TreeObject(currentNewNode);
		currentNewNode.setUserObject(treeObject);
		FormsObject formObject = new FormsObject();
		formObject.setName(treeItem.getText());
		treeObject.setFrmObj(formObject);
		treeObject.setText(treeItem.getText());

		currentParentNode.add(currentNewNode);
		TriggersControl triggersControl = new TriggersControl(currentNewNode);
		UISettings.getUISettingsBean().setTriggersControl(triggersControl);
	}

	// protected DataBindingContext initDataBindings() {
	// DataBindingContext bindingContext = new DataBindingContext();
	// IObservableValue treeObserveBoundsObserveWidget = SWTObservables
	// .observeBounds(tree);
	// System.out.println(treeObserveBoundsObserveWidget.getValue());
	// IObservableValue groupBoundsObserveValue = PojoObservables
	// .observeValue(group, "bounds");
	// bindingContext.bindValue(treeObserveBoundsObserveWidget,
	// groupBoundsObserveValue, null, null);
	// return bindingContext;
	// }
}
