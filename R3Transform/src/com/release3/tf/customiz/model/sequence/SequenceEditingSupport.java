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
package com.release3.tf.customiz.model.sequence;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;

import com.converter.toolkit.ui.custom.Parameter;
import com.converter.toolkit.ui.custom.Sequence;
import com.release3.transform.constants.UIConstants;

public class SequenceEditingSupport extends EditingSupport {
	private CellEditor editor;
	private int column;
	String itemArray[] = SequenceAnalysis.getSequenceAnalysis().getItemArray();

	public SequenceEditingSupport(ColumnViewer viewer, int column) {

		super(viewer);

		// Create the correct editor based on the column index
		switch (column) {
		case 0:

			editor = new ComboBoxCellEditor(((TableViewer) viewer).getTable(),
					itemArray);
			break;

		default:
			editor = new TextCellEditor(((TableViewer) viewer).getTable());
		}
		this.column = column;

	}

	@Override
	protected boolean canEdit(Object element) {
		return true;
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		return editor;
	}

	@Override
	protected Object getValue(Object element) {
		Sequence seq = (Sequence) element;

		switch (this.column) {
		case 0:

			for (int i = 0; i < itemArray.length; i++) {

				if (seq.getItem() != null && seq.getItem().equals(itemArray[i])) {
					return i;
				}
			}
			return 0;
		case 1:
			return seq.getSeq();
		default:
			return null;
		}

	}

	@Override
	protected void setValue(Object element, Object value) {
		Sequence seq = (Sequence) element;
		System.out.println(value);
		switch (this.column) {
		case 0:

			seq.setItem(itemArray[((Integer) value)]);
			break;
		case 1:

			seq.setSeq((String) value);
			break;
		default:
			break;
		}

		getViewer().update(element, null);

	}

}
