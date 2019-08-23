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

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

public class CanvasSwitchParamDialog extends TitleAreaDialog {
	private Table table;
	private TableViewer tableViewer;
	private CanvasSwitchParamCustGroup groupParam;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public CanvasSwitchParamDialog(Shell parentShell) {
		super(parentShell);
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		groupParam = new CanvasSwitchParamCustGroup(container, SWT.NONE);
		// tableViewer = new TableViewer(container, SWT.BORDER
		// | SWT.FULL_SELECTION);
		// table = tableViewer.getTable();
		// table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1,
		// 1));
		loadTables();
		return container;
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(697, 416);
	}

	public void loadTables() {
		// Load Parameter Table
		groupParam.loadTable();
	}
	// public void loadTable() {
	// ParametersControl paramControl = UISettings.getUISettingsBean()
	// .getParametersControl();
	// List<Parameter> params = paramControl.getIterator();
	// for (Parameter parameter : params) {
	// // fillTable(parameter);
	// }
	// tableViewer.setContentProvider(new ParameterContentProvider());
	// ParameterLabelProvider labelProvider = new ParameterLabelProvider();
	// tableViewer.setLabelProvider(labelProvider);
	// parameterModelProvider = ParameterModelProvider.getInstance();
	// tableViewer.setInput(parameterModelProvider.getParameterList());
	// tableViewer.refresh();
	// }
}
