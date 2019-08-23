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
package com.release3.tf.customiz.model.param;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ICellEditorListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;

import com.converter.toolkit.ui.custom.Parameter;
import com.release3.transform.constants.UIConstants;

public class ParameterEditingSupport extends EditingSupport {
	private CellEditor editor;
	private int column;
	private ColumnViewer viewer;
	String blocksArray[] = ParameterAnalysis.getBlockArray();
	String currentBlockName = null;
	String itemArray[] = ParameterAnalysis.getItemArray();

	public ParameterEditingSupport(ColumnViewer viewer, int column) {

		super(viewer);

		// Create the correct editor based on the column index
		switch (column) {
		case 0:
			editor = new ComboBoxCellEditor(((TableViewer) viewer).getTable(),
					blocksArray);
			// editor.addListener(new ICellEditorListener() {
			//
			// @Override
			// public void editorValueChanged(boolean oldValidState,
			// boolean newValidState) {
			// System.out.println("oldValidState " + oldValidState);
			// System.out.println("newValidState " + newValidState);
			// // TODO Auto-generated method stub
			//
			// }
			//
			// @Override
			// public void cancelEditor() {
			// // TODO Auto-generated method stub
			//
			// }
			//
			// @Override
			// public void applyEditorValue() {
			// Integer index = (Integer) editor.getValue();
			// currentBlockName = (String) blocksArray[index];
			// itemArray = ParameterAnalysis
			// .getItemArray(currentBlockName);
			// System.out.println("currentBlockName " + currentBlockName);
			// }
			// });

			break;
		case 1:
			editor = new ComboBoxCellEditor(((TableViewer) viewer).getTable(),
					itemArray);
			break;
		case 2:
			editor = new ComboBoxCellEditor(((TableViewer) viewer).getTable(),
					UIConstants.TRIGGER_PARAMETER_TYPE_VALUES);
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
			currentBlockName = ((Parameter) element).getBlock();
			itemArray = ParameterAnalysis.getItemArray(currentBlockName);
//			System.out.println("element " + element);
			((ComboBoxCellEditor) editor).setItems(itemArray);
			// editor = new ComboBoxCellEditor(((TableViewer)
			// viewer).getTable(),
			// itemArray);
			break;
		}
		return editor;
	}

	@Override
	protected Object getValue(Object element) {
		Parameter param = (Parameter) element;
		currentBlockName = param.getBlock();
		switch (this.column) {
		case 0:
			for (int i = 0; i < blocksArray.length; i++) {
				if (currentBlockName != null
						&& currentBlockName.equals(blocksArray[i])) {
					return i;
				}
			}
			return 0;
		case 1:
			itemArray = ParameterAnalysis.getItemArray(currentBlockName);
			for (int i = 0; i < itemArray.length; i++) {

				if (param.getItem() != null
						&& param.getItem().equals(itemArray[i])) {
					return i;
				}
			}
			return 0;
		case 2:
			for (int i = 0; i < UIConstants.TRIGGER_PARAMETER_TYPE_VALUES.length; i++) {

				if (param.getItem() != null
						&& param.getItem().equals(
								UIConstants.TRIGGER_PARAMETER_TYPE_VALUES[i])) {
					return i;
				}
			}
			return 0;
		case 3:
			return param.getName();
		default:
			return null;
		}

	}

	@Override
	protected void setValue(Object element, Object value) {
		Parameter param = (Parameter) element;
//		System.out.println(value);

		switch (this.column) {
		case 0:
			currentBlockName = blocksArray[((Integer) value)];
			param.setBlock(currentBlockName);
			break;
		case 1:
			String itemArray[] = ParameterAnalysis
					.getItemArray(currentBlockName);
			param.setItem(itemArray[((Integer) value)]);
			break;
		case 2:
			param.setType(UIConstants.TRIGGER_PARAMETER_TYPE_VALUES[(Integer) value]);
			break;
		case 3:
			param.setName((String) value);
			break;
		default:
			break;
		}

		getViewer().update(element, null);

	}
}
