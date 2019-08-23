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
package com.release3.tf.plsql.process;

import java.util.prefs.Preferences;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolTip;

public class FormLogicPreferencesDialog extends TitleAreaDialog {

	private static final String _StrMsg = "Use either a previously saved form processed logic or generate a new one.";

	// this are our preferences we will be using as the IPreferenceStore is not
	// available yet
	public static Preferences preferences = Preferences
			.userNodeForPackage(FormLogicPreferencesDialog.class);

	public static final String REMEMBER_LAST_SELECTED_OPTION = "FormLogicPreferencesDialog.RememberLastSelectedOption";
	private Button btnGeneratedNew;
	private Button btnSavedPreferences;
	private Label lblGenerateNew;
	private Label lblUsePrevGen;
	/**
	 * if this useDefaultFlag = true implies that use default previously saved
	 * files else use autogenerated files
	 */
	private boolean useDefaultFlag = true;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public FormLogicPreferencesDialog(Shell parentShell) {
		super(parentShell);

	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		setTitle("Use Preferences from previously processed form logic.");
		setMessage(_StrMsg);
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		btnGeneratedNew = new Button(container, SWT.RADIO);
		// btnGeneratedNew.addSelectionListener(new SelectionAdapter() {
		// @Override
		// public void widgetSelected(SelectionEvent e) {
		// useDefaultFlag = !btnSavedPreferences
		// .getSelection();
		// }
		// });
		final ToolTip btnGeneratedNewToolTip = new ToolTip(
				container.getShell(), SWT.NONE);
		btnGeneratedNewToolTip
				.setText("If this option is selected,\n "
						+ "a new form logic preferences file will be created in workspace location.");
		btnGeneratedNewToolTip.setAutoHide(true);

		btnGeneratedNew.setBounds(10, 34, 23, 16);
		// btnAlwaysUseAutogenerated.setSelection(true);

		btnSavedPreferences = new Button(container, SWT.RADIO);
		btnSavedPreferences.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				useDefaultFlag = btnSavedPreferences.getSelection();

				preferences.putBoolean(REMEMBER_LAST_SELECTED_OPTION,
						useDefaultFlag);
			}
		});
		final ToolTip btnSavedPreferencesToolTip = new ToolTip(
				container.getShell(), SWT.NONE);
		btnSavedPreferencesToolTip
				.setText("If this option is selected,\n "
						+ "the previously saved form logic preference file from the workspace folder will be used.");
		btnSavedPreferencesToolTip.setAutoHide(true);

		btnSavedPreferences.setBounds(10, 77, 23, 16);
		btnSavedPreferences.setSelection(preferences.getBoolean(
				REMEMBER_LAST_SELECTED_OPTION, false));

		Label lblOr = new Label(container, SWT.NONE);
		lblOr.setBounds(110, 56, 23, 15);
		lblOr.setText("OR");

		lblGenerateNew = new Label(container, SWT.NONE);
		lblGenerateNew.setBounds(40, 35, 201, 15);
		lblGenerateNew.setText("Process form logic (new).");
		lblGenerateNew.addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent e) {
				btnGeneratedNewToolTip.setVisible(true);
			}
		});
		lblUsePrevGen = new Label(container, SWT.NONE);
		lblUsePrevGen.setBounds(40, 78, 226, 15);
		lblUsePrevGen.setText("Use previously generated form logic.");
		lblUsePrevGen.addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent e) {
				btnSavedPreferencesToolTip.setVisible(true);
			}
		});
		return area;
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
		return new Point(657, 282);
	}
}
