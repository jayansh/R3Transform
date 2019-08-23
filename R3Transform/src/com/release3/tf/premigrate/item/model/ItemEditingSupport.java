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
package com.release3.tf.premigrate.item.model;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;

import com.oracle.xmlns.forms.Block;
import com.oracle.xmlns.forms.Item;
import com.release3.transform.constants.PremigrationConstants;
import com.release3.transform.pref.PropertiesReadWrite;

public class ItemEditingSupport extends EditingSupport {

	private CellEditor editor;
	private int column;
	

	public ItemEditingSupport(ColumnViewer viewer, int column) {
		super(viewer);

		// Create the correct editor based on the column index
		switch (column) {
		case 1:
			editor = new CheckboxCellEditor(((TableViewer) viewer).getTable());
			break;

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
		Item item = (Item) element;

		switch (this.column) {
		case 0:
			return item.getName();
		case 1:
			if (item.isPrimaryKey() == null) {
				return false;
			} else {
			return item.isPrimaryKey();
			}

		default:
			break;
		}
		return null;

	}

	@Override
	protected void setValue(Object element, Object value) {
		Item blks = (Item) element;
		System.out.println(value);
		switch (this.column) {
		case 1:
			blks.setPrimaryKey((Boolean) value);
			PropertiesReadWrite
					.getPropertiesReadWrite()
					.getProperties()
					.setProperty(
							"Item." + blks.getName() + ".PrimaryKey",
							 value.toString());
			break;

		default:
			break;
		}

		getViewer().update(element, null);

	}

}
