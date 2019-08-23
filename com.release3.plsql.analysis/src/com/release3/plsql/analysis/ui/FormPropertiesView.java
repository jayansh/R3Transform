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
package com.release3.plsql.analysis.ui;

import java.io.File;
import java.util.List;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.eclipse.ui.part.ViewPart;

import com.release3.plsql.analysis.BuiltinProcessor;
import com.release3.plsql.analysis.property.model.FormProperties;
import com.release3.plsql.analysis.property.model.FormPropertiesFile;
import com.release3.plsql.analysis.property.model.PropertiesContentProvider;
import com.release3.plsql.analysis.property.model.PropertiesLabelProvider;
import com.release3.plsql.analysis.property.model.PropertiesModelProvider;

public class FormPropertiesView extends ViewPart {

	public static final String ID = "com.release3.plsql.analysis.FormPropertiesView";
	BuiltinProcessor processor = new BuiltinProcessor();

	public FormPropertiesView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		try {

			ScrolledComposite scrolledCompositeTopLeft = new ScrolledComposite(
					parent, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
			scrolledCompositeTopLeft.setSize(863, 571);
			scrolledCompositeTopLeft.setExpandHorizontal(true);
			scrolledCompositeTopLeft.setExpandVertical(true);
			final TreeViewer treeViewer = new TreeViewer(
					scrolledCompositeTopLeft, SWT.BORDER);
			final Tree tree = treeViewer.getTree();

			Transfer[] types = new Transfer[] { TextTransfer.getInstance() };
			DragSource dragSource = new DragSource(tree, DND.DROP_MOVE
					| DND.DROP_COPY);
			dragSource.setTransfer(types);
			final TreeItem[] dragSourceItem = new TreeItem[1];
			dragSource.addDragListener(new DragSourceListener() {
				@Override
				public void dragStart(DragSourceEvent event) {
					TreeItem[] selection = tree.getSelection();
					if (selection.length > 0
							&& selection[0].getItemCount() == 0) {
						event.doit = true;
						dragSourceItem[0] = selection[0];
					} else {
						event.doit = false;
					}
				}

				@Override
				public void dragFinished(DragSourceEvent event) {
					if (event.detail == DND.DROP_MOVE) {
						// graphicsTreeMap.put(dragSourceItem[0].getText(),
						// dragSourceItem[0]);
						String text = dragSourceItem[0].getText();
						FormProperties properties = (FormProperties) dragSourceItem[0]
								.getData();
						String methodName =  properties.getTrigger().getName()
										.replace("-", "_") + "_"
								+ properties.getJavaMethodSuffix();
						properties.getTrigger().setTriggerText(

						processor.migrateToJava(text, methodName));
						processor.save();
						dragSourceItem[0].setText("");
					}

				}

				@Override
				public void dragSetData(DragSourceEvent event) {
					// Provide the data of the requested type.
					if (TextTransfer.getInstance().isSupportedType(
							event.dataType)) {
						event.data = dragSourceItem[0].getText();
					}
				}
			});

			treeViewer
					.addSelectionChangedListener(new ISelectionChangedListener() {
						public void selectionChanged(SelectionChangedEvent event) {
							TreeItem item = treeViewer.getTree().getSelection()[0];
							if (item.getData() instanceof FormProperties) {
								PropertiesModelProvider.getInstance()
										.setCurrentTriggerText(
												(String) item.getText());
								refreshOtherView();

							}
						}
					});

			scrolledCompositeTopLeft.setContent(tree);
			scrolledCompositeTopLeft.setMinSize(tree.computeSize(SWT.DEFAULT,
					SWT.DEFAULT));
			treeViewer.setContentProvider(new PropertiesContentProvider());
			treeViewer.setLabelProvider(new PropertiesLabelProvider());
			treeViewer.setInput(getInput());
			treeViewer.expandAll();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	private Object getInput() {
		List<FormProperties> properties = PropertiesModelProvider.getInstance()
				.getFormPropertiesList();

		return properties;
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	private void refreshOtherView() {
		IWorkbenchPage page = this.getSite().getPage();
		File tmpFile = new File(Platform.getLocation().toFile().getPath()
				+ File.separator + "Untitled.txt");
		FormPropertiesFile propertiesFile = new FormPropertiesFile(tmpFile);
		propertiesFile.setText(PropertiesModelProvider.getInstance()
				.getCurrentTriggerText());

		try {
			IFileStore fileStore = EFS.getStore(propertiesFile.toURI());
			FileStoreEditorInput input = new FileStoreEditorInput(fileStore);
			IEditorPart editor = page.findEditor(input);
			if (editor == null) {
				page.openEditor(input, EditorsUI.DEFAULT_TEXT_EDITOR_ID);
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}

		// PlSqlDBView view = (PlSqlDBView) page.findView(PlSqlDBView.ID);
		// view.refresh();
	}

}
