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
package com.release3.transform.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.TreeEditor;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.oracle.xmlns.forms.Block;
import com.oracle.xmlns.forms.Graphics;
import com.oracle.xmlns.forms.Item;
import com.release3.tf.core.form.CleanupForm;
import com.release3.transform.constants.PremigrationConstants;
import com.release3.transform.model.CanvasModel;
import com.release3.transform.model.PromptItemModel;
import com.release3.transform.pref.PropertiesReadWrite;

public class PromptHandlingWizardPage extends WizardPage {

	Combo comboCanvas;
	Tree itemsTree;
	Tree treeGraphics;
	// List of treeitem to which prompt is added.
	HashMap<String, TreeItem> itemsTreeMap;
	HashMap<String, TreeItem> graphicsTreeMap;

	// HashMap<Graphics, Canvas> graphicsToRemove;

	/**
	 * Create the wizard.
	 */
	public PromptHandlingWizardPage() {
		super("wizardPage");
		setTitle("Prompt Handling");
		setDescription("Prompt Handling");
		itemsTreeMap = new HashMap<String, TreeItem>();
		graphicsTreeMap = new HashMap<String, TreeItem>();
		// graphicsToRemove = new HashMap<Graphics, Canvas>();
	}

	/**
	 * Create contents of the wizard.
	 * 
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);

		Group mainGroup = new Group(container, SWT.NONE);
		mainGroup.setLocation(0, 0);
		mainGroup.setSize(772, 490);

		final ScrolledComposite scrolledCompositeLeft = new ScrolledComposite(
				mainGroup, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledCompositeLeft.setLocation(10, 50);
		scrolledCompositeLeft.setSize(375, 380);
		scrolledCompositeLeft.setExpandHorizontal(true);
		scrolledCompositeLeft.setExpandVertical(true);

		itemsTree = new Tree(scrolledCompositeLeft, SWT.BORDER);
		scrolledCompositeLeft.setContent(itemsTree);
		scrolledCompositeLeft.setMinSize(itemsTree.computeSize(SWT.DEFAULT,
				SWT.DEFAULT));
		TreeEditor itemsTreeEditor = new TreeEditor(itemsTree);

		itemsTreeEditor.horizontalAlignment = SWT.LEFT;
		itemsTreeEditor.grabHorizontal = true;

		ScrolledComposite scrolledCompositeRight = new ScrolledComposite(
				mainGroup, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledCompositeRight.setLocation(387, 50);
		scrolledCompositeRight.setSize(375, 380);
		scrolledCompositeRight.setExpandHorizontal(true);
		scrolledCompositeRight.setExpandVertical(true);

		treeGraphics = new Tree(scrolledCompositeRight, SWT.BORDER);
		scrolledCompositeRight.setContent(treeGraphics);
		scrolledCompositeRight.setMinSize(treeGraphics.computeSize(SWT.DEFAULT,
				SWT.DEFAULT));
		TreeEditor graphicsTreeEditor = new TreeEditor(treeGraphics);

		graphicsTreeEditor.horizontalAlignment = SWT.LEFT;
		graphicsTreeEditor.grabHorizontal = true;

		Transfer[] types = new Transfer[] { TextTransfer.getInstance() };
		int operations = DND.DROP_MOVE | DND.DROP_COPY;
		DragSource sourceGraphicsTree = new DragSource(treeGraphics, operations);
		sourceGraphicsTree.setTransfer(types);
		final TreeItem[] dragSourceItem = new TreeItem[1];
		sourceGraphicsTree.addDragListener(new DragSourceListener() {
			@Override
			public void dragStart(DragSourceEvent event) {
				TreeItem[] selection = treeGraphics.getSelection();
				if (selection.length > 0 && selection[0].getItemCount() == 0) {
					event.doit = true;
					dragSourceItem[0] = selection[0];
				} else {
					event.doit = false;
				}
			}

			@Override
			public void dragFinished(DragSourceEvent event) {
				if (event.detail == DND.DROP_MOVE) {
					graphicsTreeMap.put(dragSourceItem[0].getText(),
							dragSourceItem[0]);
					dragSourceItem[0].setText("");
				}

			}

			@Override
			public void dragSetData(DragSourceEvent event) {
				// Provide the data of the requested type.
				if (TextTransfer.getInstance().isSupportedType(event.dataType)) {
					event.data = dragSourceItem[0].getText();
				}
			}
		});

		DropTarget targetItemsTree = new DropTarget(itemsTree, operations);
		targetItemsTree.setTransfer(types);
		final Display display = mainGroup.getShell().getDisplay();

		targetItemsTree.addDropListener(new DropTargetAdapter() {
			public void dragOver(DropTargetEvent event) {
				System.out.println(display.readAndDispatch());
				System.out.println(event.dataTypes[0].getClass());
				event.feedback = DND.FEEDBACK_EXPAND | DND.FEEDBACK_SCROLL;
				if (event.item != null) {
					TreeItem item = (TreeItem) event.item;
					if (item.getParentItem() != null) {
						if (item.getParentItem().getParentItem() == null) {
							event.feedback |= DND.FEEDBACK_NONE;
						} else {
							event.feedback |= DND.FEEDBACK_SELECT;
						}
					} else {
						event.feedback |= DND.FEEDBACK_NONE;
					}
				}
			}

			public void drop(DropTargetEvent event) {

				if (event.data == null) {
					event.detail = DND.DROP_NONE;
					return;
				}
				String text = (String) event.data;
				if (event.item == null) {

					TreeItem item = new TreeItem(itemsTree, SWT.NONE);
					item.setText(text);
					itemsTreeMap.put(text, item);
				} else {
					TreeItem item = (TreeItem) event.item;
					TreeItem parent = item.getParentItem();
					if (parent != null) {
						if (parent.getParentItem() != null) {
							item.setText(text);
							itemsTreeMap.put(text, item);
						} else {
							event.detail = DND.DROP_NONE;
							return;
						}
					} else {
						event.detail = DND.DROP_NONE;
						return;
					}
				}
			}
		});

		DragSource sourceItemsTree = new DragSource(itemsTree, operations);
		sourceItemsTree.setTransfer(types);

		sourceItemsTree.addDragListener(new DragSourceListener() {
			@Override
			public void dragStart(DragSourceEvent event) {
				TreeItem[] selection = itemsTree.getSelection();
				if (selection.length > 0 && selection[0].getItemCount() == 0) {
					if (selection[0].getParentItem() != null) {
						event.doit = true;
						dragSourceItem[0] = selection[0];

					} else {
						event.doit = false;
					}
				} else {
					event.doit = false;
				}
			}

			@Override
			public void dragFinished(DragSourceEvent event) {
				if (event.detail == DND.DROP_MOVE) {
					dragSourceItem[0].setText("");
				}

			}

			@Override
			public void dragSetData(DragSourceEvent event) {
				// Provide the data of the requested type.
				if (TextTransfer.getInstance().isSupportedType(event.dataType)) {
					event.data = dragSourceItem[0].getText();
				}
			}
		});

		DropTarget targetGraphicsTree = new DropTarget(treeGraphics, operations);
		targetGraphicsTree.setTransfer(types);

		targetGraphicsTree.addDropListener(new DropTargetAdapter() {
			public void dragOver(DropTargetEvent event) {
				System.out.println(display.readAndDispatch());
				System.out.println(event.dataTypes[0].getClass());
				event.feedback = DND.FEEDBACK_EXPAND | DND.FEEDBACK_SCROLL;
				if (event.item != null) {
					TreeItem item = (TreeItem) event.item;
					if (item.getParentItem() == null) {
						event.feedback |= DND.FEEDBACK_NONE;
					} else {
						event.feedback |= DND.FEEDBACK_SELECT;
					}
				}
			}

			public void drop(DropTargetEvent event) {

				if (event.data == null) {
					event.detail = DND.DROP_NONE;
					return;
				}
				String text = (String) event.data;

				TreeItem item = (TreeItem) event.item;
				item.setText(text);
				itemsTreeMap.remove(text);

			}
		});

		comboCanvas = new Combo(mainGroup, SWT.NONE);
		comboCanvas.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				treeGraphics.removeAll();
				itemsTree.removeAll();
				setErrorMessage(null);
				String canvas = comboCanvas.getItem(comboCanvas
						.getSelectionIndex());
				List<CanvasModel> canvasModelList = ((FormChooserWizardPage) getPreviousPage()
						.getPreviousPage()).getGraphicsAnalysis()
						.getCanvasModelList();

				for (CanvasModel canvasModel : canvasModelList) {

					fillGraphicsTree(canvasModel, canvas);

				}

				if (treeGraphics.getItems().length == 0) {
					setErrorMessage("No associated prompt items in right panel.");
				}

				List<PromptItemModel> itemModelList = ((FormChooserWizardPage) getPreviousPage()
						.getPreviousPage()).getItemAnalysis()
						.getItemModelList();

				for (PromptItemModel itemModel : itemModelList) {
					fillItemsTree(itemModel, canvas);

				}

			}
		});
		comboCanvas.setBounds(10, 23, 158, 21);

		Button btnApply = new Button(mainGroup, SWT.NONE);
		btnApply.setBounds(694, 455, 68, 23);
		btnApply.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				for (Map.Entry<String, TreeItem> entry : itemsTreeMap
						.entrySet()) {
					TreeItem treeItem = entry.getValue();
					Item item = (Item) treeItem.getParentItem().getData();
					item.setPrompt(treeItem.getText());
					PropertiesReadWrite
							.getPropertiesReadWrite()
							.getProperties()
							.setProperty(
									"PromptHandling." + item.getName()
											+ ".Prompt", treeItem.getText());
					TreeItem graphicsTreeItem = graphicsTreeMap.get(treeItem
							.getText());
					Graphics graphics = (Graphics) graphicsTreeItem.getData();
					item.setPromptAttachmentOffset(graphics
							.getFrameTitleOffset());
					item.setPromptFontName(graphics.getGraphicsFontName());
					item.setPromptFontSize(graphics.getGraphicsFontSize());
					item.setPromptFontSpacing(graphics.getGraphicsFontSpacing());

					item.setPromptFontWeight(graphics.getGraphicsFontWeight());

				}
				itemsTreeMap.clear();
				Table table = ((FormChooserWizardPage) getPreviousPage()
						.getPreviousPage()).getTable();
				try {
					((CleanupForm) (table.getItem(table.getSelectionIndex())
							.getData())).save(false);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				PropertiesReadWrite.getPropertiesReadWrite().write();
			}
		});
		btnApply.setText("Apply");
		setPageComplete(true);
	}

	@Override
	public IWizardPage getNextPage() {

		treeGraphics.removeAll();
		itemsTree.removeAll();
		setErrorMessage(null);

		List<CanvasModel> canvasModelList = ((FormChooserWizardPage) getPreviousPage()
				.getPreviousPage().getPreviousPage()).getGraphicsAnalysis()
				.getCanvasModelList();
		comboCanvas.removeAll();
		comboCanvas.add("All");
		comboCanvas.select(0);
		for (CanvasModel canvasModel : canvasModelList) {
			comboCanvas.add(canvasModel.getCanvas().getName());
		}
		for (CanvasModel canvasModel : canvasModelList) {
			fillGraphicsTree(canvasModel,
					comboCanvas.getItem(comboCanvas.getSelectionIndex()));
		}

		List<PromptItemModel> itemModelList = ((FormChooserWizardPage) getPreviousPage()
				.getPreviousPage().getPreviousPage()).getItemAnalysis()
				.getItemModelList();

		for (PromptItemModel itemModel : itemModelList) {

			fillItemsTree(itemModel,
					comboCanvas.getItem(comboCanvas.getSelectionIndex()));
		}
		if (treeGraphics.getItems().length == 0) {
			setErrorMessage("No associated prompt items in right panel.");
		}

		/**
		 * Preparing next page.
		 */

		return super.getNextPage();
	}

	private void fillGraphicsTree(CanvasModel canvasModel, String canvas) {

		if (canvas.equals("All")
				|| (canvasModel.getCanvas().getName() == canvas || canvasModel
						.getCanvas().getName().equalsIgnoreCase(canvas))) {
			List<Graphics> graphicsList = canvasModel.getGraphicsList();
			for (Graphics graphics : graphicsList) {
				if (graphics.getGraphicsText() != null
						&& graphics.getGraphicsText().length() > 0) {
					TreeItem treeItem = new TreeItem(treeGraphics, SWT.NONE);
					treeItem.setData(graphics);
					if (PremigrationConstants.UsePreviousCleanupFormPref) {
						if (!PropertiesReadWrite.getPropertiesReadWrite()
								.getProperties()
								.contains(graphics.getGraphicsText())) {
							treeItem.setText(graphics.getGraphicsText());
						}
					}

					// graphicsToRemove.put(graphics, canvasModel.getCanvas());
				}

			}
		}
	}

	private void fillItemsTree(PromptItemModel itemModel, String canvas) {

		Block block = itemModel.getBlock();

		TreeItem treeItem = new TreeItem(itemsTree, SWT.NONE);
		treeItem.setData(block);
		treeItem.setText(block.getName());
		List<Item> itemList = itemModel.getItemList();
		for (Item item : itemList) {

			// if (item.getPrompt() == null || item.getPrompt().length() == 0) {
			if (canvas.equals("All")
					|| (item.getCanvasName() == canvas || item.getCanvasName()
							.equalsIgnoreCase(canvas))) {

				TreeItem treeSubItem = new TreeItem(treeItem, SWT.NONE);
				treeSubItem.setData(item);
				treeSubItem.setText(item.getName());

				TreeItem childItem = new TreeItem(treeSubItem, SWT.NONE);
				if (PremigrationConstants.UsePreviousCleanupFormPref) {
					if (PropertiesReadWrite
							.getPropertiesReadWrite()
							.getProperties()
							.containsKey(
									"PromptHandling." + item.getName()
											+ ".Prompt")) {
						childItem.setText(PropertiesReadWrite
								.getPropertiesReadWrite()
								.getProperties()
								.getProperty(
										"PromptHandling." + item.getName()
												+ ".Prompt"));
					}
				}
			}

		}
	}

}
