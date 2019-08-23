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
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;


public class UserLoginPreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {

	private StringFieldEditor clientIDFieldEditor;
	private StringFieldEditor clientKeyFieldEditor;
	
	public UserLoginPreferencePage(){
		super(FLAT);
		// Set the preference store for the preference page.
		IPreferenceStore store =
			PreferencePlugin.getDefault().getPreferenceStore();
		setPreferenceStore(store);
	}

	@Override
	protected void createFieldEditors() {
		clientIDFieldEditor = new StringFieldEditor(
				PreferenceConstants.APPLICATION_CLIENT_ID, "Client ID:", -1,
				StringFieldEditor.VALIDATE_ON_KEY_STROKE,
				getFieldEditorParent());
		addField(clientIDFieldEditor);

		clientKeyFieldEditor = new StringFieldEditor(
				PreferenceConstants.APPLICATION_CLIENT_KEY, "Client key:", -1,
				StringFieldEditor.VALIDATE_ON_KEY_STROKE,
				getFieldEditorParent());
		addField(clientKeyFieldEditor);

		
	}

	@Override
	public void init(IWorkbench workbench) {
//		initializeDefaultPreferences();

	}
//
//	public void initializeDefaultPreferences() {
//		IPreferenceStore store = Activator.getDefault()
//				.getPreferenceStore();
//		store.setDefault(PreferenceConstants.APPLICATION_CLIENT_ID, "");
//		store.setDefault(PreferenceConstants.APPLICATION_CLIENT_KEY, "");
//		setPreferenceStore(store);
//
//	}

	@Override
	protected void performDefaults() {

		clientIDFieldEditor.loadDefault();
		clientKeyFieldEditor.loadDefault();
		super.performDefaults();
	}

	@Override
	protected void performApply() {

		clientIDFieldEditor.store();
		clientKeyFieldEditor.store();
		super.performApply();
	}
}
