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
package com.release3.tf.customiz.model.canvas;

import java.util.List;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;

import com.converter.toolkit.ui.custom.Parameter;
import com.release3.tf.core.form.UISettings;

public class CanvasSwitchEditingSupport extends EditingSupport {
	private CellEditor editor;
	private int column;
	String viewPortForms[] = CanvasAnalysis.getFormViewPortList();
	String viewPorts[] = CanvasAnalysis.getViewPortList();
	String formsList[] = CanvasAnalysis.getFormsArray();
	String canvasList[] = CanvasAnalysis.getCanvasArray();

	public CanvasSwitchEditingSupport(ColumnViewer viewer, int column) {
		super(viewer);
		switch (column) {
		case 0:
			editor = new ComboBoxCellEditor(((TableViewer) viewer).getTable(),
					viewPortForms);
			break;
		case 1:
			editor = new ComboBoxCellEditor(((TableViewer) viewer).getTable(),
					viewPorts);
			break;
		case 2:
			editor = new ComboBoxCellEditor(((TableViewer) viewer).getTable(),
					formsList);
			break;
		case 3:
			editor = new ComboBoxCellEditor(((TableViewer) viewer).getTable(),
					canvasList);
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
		Parameter param = (Parameter) element;
		List<String> viewPortTable = UISettings
				.getUISettingsBean().getViewPortControl().getIterator();

		switch (this.column) {
		case 0:
			for (int i = 0; i < viewPortForms.length; i++) {
				if (param.getViewPortForm() != null
						&& param.getViewPortForm().equals(viewPortForms[i])) {
					return i;
				}
			}
			return 0;
		case 1:
			for (int i = 0; i < viewPorts.length; i++) {
				String viewPort = param.getViewPort();
//				String viewPortVal = viewPortTable.get(viewPort);
//				// System.out.println("viewPortVal:: " + viewPortVal);

				if (viewPort == viewPorts[i]
						&& viewPort.equals(viewPorts[i])) {
					return i;
				}
			}
			return 0;
		case 2:
			for (int i = 0; i < formsList.length; i++) {

				if (param.getFormname() != null
						&& param.getFormname().equals(formsList[i])) {
					return i;
				}
			}
			return 0;
		case 3:
			for (int i = 0; i < canvasList.length; i++) {

				if (param.getCanvas() != null
						&& param.getCanvas().equals(canvasList[i])) {
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
		Parameter param = (Parameter) element;
		switch (this.column) {
		case 0:
			param.setViewPortForm(viewPortForms[(Integer) value]);
			break;
		case 1:
			String name = (String) viewPorts[(Integer) value];
			name = name.substring(name.indexOf('-') + 1);
			param.setViewPort(name);
			break;
		case 2:
			param.setFormname(formsList[(Integer) value]);
			break;
		case 3:
			param.setCanvas(canvasList[(Integer) value]);
			break;
		default:
			break;
		}

		getViewer().update(element, null);

	}

}
