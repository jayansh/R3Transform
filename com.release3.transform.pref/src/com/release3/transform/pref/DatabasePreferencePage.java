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
package com.release3.transform.pref;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class DatabasePreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	private StringFieldEditor hostFieldEditor;
	private IntegerFieldEditor portFieldEditor;
	private StringFieldEditor dbDriverFieldEditor;
	private StringFieldEditor serviceFieldEditor;
	private StringFieldEditor userFieldEditor;
	private StringFieldEditor passwordFieldEditor;

	/**
	 * Create the preference page.
	 */
	public DatabasePreferencePage() {
		super(FLAT);
		// Set the preference store for the preference page.
		IPreferenceStore store =
			PreferencePlugin.getDefault().getPreferenceStore();
		setPreferenceStore(store);

	}

	/**
	 * Create contents of the preference page.
	 */
	@Override
	protected void createFieldEditors() {
		// Create the field editors
		hostFieldEditor = new StringFieldEditor(PreferenceConstants.HOST,
				"Host:", -1, StringFieldEditor.VALIDATE_ON_KEY_STROKE,
				getFieldEditorParent());
		addField(hostFieldEditor);

		portFieldEditor = new IntegerFieldEditor(PreferenceConstants.PORT,
				"Port:", getFieldEditorParent());
		addField(portFieldEditor);

		dbDriverFieldEditor = new StringFieldEditor(
				PreferenceConstants.DATABASE, "Database Driver:", -1,
				StringFieldEditor.VALIDATE_ON_KEY_STROKE,
				getFieldEditorParent());
		addField(dbDriverFieldEditor);

		serviceFieldEditor = new StringFieldEditor(
				PreferenceConstants.SID_SERVICE, "SID/Service name:", -1,
				StringFieldEditor.VALIDATE_ON_KEY_STROKE,
				getFieldEditorParent());
		addField(serviceFieldEditor);

		userFieldEditor = new StringFieldEditor(
				PreferenceConstants.DB_USERNAME, "Username", -1,
				StringFieldEditor.VALIDATE_ON_KEY_STROKE,
				getFieldEditorParent());
		addField(userFieldEditor);

		passwordFieldEditor = new StringFieldEditor(
				PreferenceConstants.DB_PASSWORD, "Password:", -1,
				StringFieldEditor.VALIDATE_ON_KEY_STROKE,
				getFieldEditorParent());
		addField(passwordFieldEditor);

//		hostFieldEditor.setPreferenceStore(PreferencePlugin.getDefault()
//				.getPreferenceStore());
//		hostFieldEditor.load();
//
//		portFieldEditor.setPreferenceStore(PreferencePlugin.getDefault()
//				.getPreferenceStore());
//		portFieldEditor.load();
//
//		dbDriverFieldEditor.setPreferenceStore(PreferencePlugin.getDefault()
//				.getPreferenceStore());
//		dbDriverFieldEditor.load();
//
//		serviceFieldEditor.setPreferenceStore(PreferencePlugin.getDefault()
//				.getPreferenceStore());
//		serviceFieldEditor.load();
//
//		userFieldEditor.setPreferenceStore(PreferencePlugin.getDefault()
//				.getPreferenceStore());
//		userFieldEditor.load();
//
//		passwordFieldEditor.setPreferenceStore(PreferencePlugin.getDefault()
//				.getPreferenceStore());
//		passwordFieldEditor.load();

	}

	/**
	 * Initialize the preference page.
	 */
	@Override
	public void init(IWorkbench workbench) {
		initializeDefaultPreferences();
	}

	public void initializeDefaultPreferences() {
		IPreferenceStore store = PreferencePlugin.getDefault()
				.getPreferenceStore();
		store.setDefault(PreferenceConstants.HOST, "5.210.82.177");
		store.setDefault(PreferenceConstants.PORT, "1521");
		store.setDefault(PreferenceConstants.DATABASE,
				"oracle.jdbc.driver.OracleDriver");
		store.setDefault(PreferenceConstants.SID_SERVICE, "orcl");
		store.setDefault(PreferenceConstants.DB_USERNAME, "hr");
		store.setDefault(PreferenceConstants.DB_PASSWORD, "hr");
		setPreferenceStore(store);

	}

	@Override
	protected void performDefaults() {
		hostFieldEditor.loadDefault();
		portFieldEditor.loadDefault();
		dbDriverFieldEditor.loadDefault();
		serviceFieldEditor.loadDefault();
		userFieldEditor.loadDefault();
		passwordFieldEditor.loadDefault();
		super.performDefaults();
	}

	@Override
	protected void performApply() {
		hostFieldEditor.store();
		portFieldEditor.store();
		dbDriverFieldEditor.store();
		serviceFieldEditor.store();
		userFieldEditor.store();
		passwordFieldEditor.store();
		super.performApply();
	}

}
