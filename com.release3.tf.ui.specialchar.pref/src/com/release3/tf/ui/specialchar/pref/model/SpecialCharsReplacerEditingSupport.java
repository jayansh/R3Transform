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
package com.release3.tf.ui.specialchar.pref.model;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;

import com.release3.specialchars.SpecialCharsReplacer;

public class SpecialCharsReplacerEditingSupport extends EditingSupport {
	private CellEditor editor;
	private int column;

	public SpecialCharsReplacerEditingSupport(ColumnViewer viewer, int column) {
		super(viewer);

		// Create the correct editor based on the column index
		switch (column) {
		case 1:
			editor = new TextCellEditor(((TableViewer) viewer).getTable());
			break;

		default:
			editor = new TextCellEditor(((TableViewer) viewer).getTable());
		}
		this.column = column;

	}

	@Override
	protected boolean canEdit(Object element) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		// TODO Auto-generated method stub
		return editor;
	}

	@Override
	protected Object getValue(Object element) {
		SpecialCharsReplacer scReplacer = (SpecialCharsReplacer) element;
		switch (this.column) {
		case 0:
			return scReplacer.getSpecialChar();
		case 1:

			return scReplacer.getReplacement();

		default:
			break;
		}
		return null;

	}

	@Override
	protected void setValue(Object element, Object value) {
		SpecialCharsReplacer scItemModel = (SpecialCharsReplacer) element;

		switch (this.column) {
		case 0:
			scItemModel.setSpecialChar(value.toString());
		case 1:

			scItemModel.setReplacement(value.toString());

			break;

		default:
			break;
		}

		getViewer().update(element, null);
	}

}
