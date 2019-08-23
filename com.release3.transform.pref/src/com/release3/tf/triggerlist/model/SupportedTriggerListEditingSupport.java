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
package com.release3.tf.triggerlist.model;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;

import com.release3.triggerlist.SupportedTriggerType;

public class SupportedTriggerListEditingSupport extends EditingSupport {
	private CellEditor editor;
	private int column;
	private ColumnViewer viewer;

	public SupportedTriggerListEditingSupport(ColumnViewer viewer, int column) {
		super(viewer);
		switch (column) {
		case 0:
			editor = new TextCellEditor(((TableViewer) viewer).getTable());
			break;
		case 1:
			editor = new CheckboxCellEditor(((TableViewer) viewer).getTable());
			break;

		}
		this.viewer = viewer;
		this.column = column;

	}

	@Override
	protected CellEditor getCellEditor(Object element) {

		return editor;
	}

	@Override
	protected boolean canEdit(Object element) {
		return true;
	}

	@Override
	protected Object getValue(Object element) {
		SupportedTriggerType nonCrud = (SupportedTriggerType) element;
		System.out.println("getValue" + element);
		switch (this.column) {
		 case 0:
		 String triggerName = nonCrud.getName();
		 return triggerName;
		case 1:
			if (nonCrud.isMigrate()== null) {
				return false;
			} else {
				return nonCrud.isMigrate();

			}

		default:
			return null;
		}
	}

	@Override
	protected void setValue(Object element, Object value) {
		SupportedTriggerType triggerType = (SupportedTriggerType) element;
		switch (this.column) {
		 case 0:
			 triggerType.setName((String) value);
		 break;
		 case 1:
			Boolean boolVal = (Boolean) value;
			triggerType.setMigrate(boolVal);

			break;

		default:
			break;
		}
		getViewer().update(element, null);

	}

}
