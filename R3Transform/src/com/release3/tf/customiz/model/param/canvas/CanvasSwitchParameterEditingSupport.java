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
package com.release3.tf.customiz.model.param.canvas;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;

import com.converter.toolkit.ui.custom.CanvasSwitchParameter;
import com.release3.tf.core.form.UISettings;
import com.release3.transform.constants.UIConstants;

public class CanvasSwitchParameterEditingSupport extends EditingSupport {
	private CellEditor editor;
	private int column;
	private ColumnViewer viewer;
	String formsArray[] = CanvasSwitchParameterAnalysis.getFormsArray();
	String blocksArray[] = CanvasSwitchParameterAnalysis.getBlockArray();
	String destBlocksArray[] = CanvasSwitchParameterAnalysis.getBlockArray();
	String currentBlockName = null;
	String currentFormName = null;
	String currentDestinationBlockName = null;
	String currentDestinationFormName = null;
	String itemArray[] = CanvasSwitchParameterAnalysis.getItemArray();
	String destItemArray[] = CanvasSwitchParameterAnalysis.getItemArray();

	public CanvasSwitchParameterEditingSupport(ColumnViewer viewer, int column) {

		super(viewer);

		// Create the correct editor based on the column index
		switch (column) {
		case 0:
			editor = new ComboBoxCellEditor(((TableViewer) viewer).getTable(),
					formsArray);
			break;
		case 1:
			editor = new ComboBoxCellEditor(((TableViewer) viewer).getTable(),
					blocksArray);
			break;
		case 2:
			editor = new ComboBoxCellEditor(((TableViewer) viewer).getTable(),
					itemArray);
			break;
		case 3:
			editor = new ComboBoxCellEditor(((TableViewer) viewer).getTable(),
					formsArray);
			break;
		case 4:
			editor = new ComboBoxCellEditor(((TableViewer) viewer).getTable(),
					destBlocksArray);
			break;
		case 5:
			editor = new ComboBoxCellEditor(((TableViewer) viewer).getTable(),
					destItemArray);
			break;
		default:
			editor = new TextCellEditor(((TableViewer) viewer).getTable());
		}
		this.column = column;
		this.viewer = viewer;

	}

	@Override
	protected boolean canEdit(Object element) {
		return true;
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		switch (this.column) {
		case 1:
			currentFormName = ((CanvasSwitchParameter) element).getSourceForm();
			blocksArray = CanvasSwitchParameterAnalysis
					.getBlockArray(currentFormName);
			// System.out.println("element " + element);
			((ComboBoxCellEditor) editor).setItems(blocksArray);
			break;
		case 2:
			currentBlockName = ((CanvasSwitchParameter) element)
					.getSourceBlock();
			itemArray = CanvasSwitchParameterAnalysis
					.getItemArray(currentBlockName);
			// System.out.println("element " + element);
			((ComboBoxCellEditor) editor).setItems(itemArray);
			break;
		case 4:
			currentDestinationFormName = ((CanvasSwitchParameter) element)
					.getDestinationForm();
			destBlocksArray = CanvasSwitchParameterAnalysis
					.getBlockArray(currentDestinationFormName);
			// System.out.println("element " + element);
			((ComboBoxCellEditor) editor).setItems(destBlocksArray);
			break;
		case 5:
			currentDestinationBlockName = ((CanvasSwitchParameter) element)
					.getDestinationBlock();
			destItemArray = CanvasSwitchParameterAnalysis
					.getItemArray(currentDestinationBlockName);
			// System.out.println("element " + element);
			((ComboBoxCellEditor) editor).setItems(destItemArray);
			break;
		}

		return editor;
	}

	@Override
	protected Object getValue(Object element) {
		CanvasSwitchParameter param = (CanvasSwitchParameter) element;
		currentBlockName = param.getSourceBlock();
		currentDestinationBlockName = param.getDestinationBlock();
		switch (this.column) {
		case 0:
			for (int i = 0; i < formsArray.length; i++) {
				if (currentFormName != null
						&& currentFormName.equals(formsArray[i])) {
					return i;
				}
			}
			return 0;
		case 1:
			for (int i = 0; i < blocksArray.length; i++) {
				if (currentBlockName != null
						&& currentBlockName.equals(blocksArray[i])) {
					return i;
				}
			}
			return 0;
		case 2:
			itemArray = CanvasSwitchParameterAnalysis
					.getItemArray(currentBlockName);
			for (int i = 0; i < itemArray.length; i++) {

				if (param.getSourceItem() != null
						&& param.getSourceItem().equals(itemArray[i])) {
					return i;
				}
			}
			return 0;
		case 3:
			for (int i = 0; i < formsArray.length; i++) {
				if (currentDestinationFormName != null
						&& currentDestinationFormName.equals(formsArray[i])) {
					return i;
				}
			}
			return 0;
		case 4:
			for (int i = 0; i < destBlocksArray.length; i++) {
				if (currentDestinationBlockName != null
						&& currentDestinationBlockName
								.equals(destBlocksArray[i])) {
					return i;
				}
			}
			return 0;
		case 5:
			destItemArray = CanvasSwitchParameterAnalysis
					.getItemArray(currentDestinationBlockName);
			for (int i = 0; i < destItemArray.length; i++) {

				if (param.getSourceItem() != null
						&& param.getSourceItem().equals(destItemArray[i])) {
					return i;
				}
			}
			return 0;
		default:
			return null;
		}

	}

	@Override
	protected void setValue(Object element, Object value) {
		CanvasSwitchParameter param = (CanvasSwitchParameter) element;
		// System.out.println(value);

		switch (this.column) {
		case 0:
			currentFormName = formsArray[((Integer) value)];
			param.setSourceForm(currentFormName);
			break;
		case 1:
			currentBlockName = blocksArray[((Integer) value)];
			param.setSourceBlock(currentBlockName);
			break;
		case 2:
			String itemArray[] = CanvasSwitchParameterAnalysis
					.getItemArray(currentBlockName);
			param.setSourceItem(itemArray[((Integer) value)]);
			break;
		case 3:
			currentDestinationFormName = formsArray[((Integer) value)];
			param.setDestinationForm(currentDestinationFormName);
			break;
		case 4:
			currentDestinationBlockName = destBlocksArray[((Integer) value)];
			param.setDestinationBlock(currentDestinationBlockName);
			break;
		case 5:
			String destItemArray[] = CanvasSwitchParameterAnalysis
					.getItemArray(currentDestinationBlockName);
			param.setDestinationItem(destItemArray[((Integer) value)]);
			break;
		default:
			break;
		}

		getViewer().update(element, null);

	}
}
