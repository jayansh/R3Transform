package com.release3.transform.pref;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class TransformWorkbenchPreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {
	private BooleanFieldEditor useIspirerSqlWaysFieldEditor;
	private BooleanFieldEditor crudAsNonCrudFieldEditor;
	private BooleanFieldEditor programUnitAsNonCrudFieldEditor;

	/**
	 * Create the preference page.
	 */
	public TransformWorkbenchPreferencePage() {
		super(FLAT);
		// Set the preference store for the preference page.
		IPreferenceStore store = PreferencePlugin.getDefault()
				.getPreferenceStore();
		setPreferenceStore(store);

	}

	@Override
	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void createFieldEditors() {
		useIspirerSqlWaysFieldEditor =  new BooleanFieldEditor(
				PreferenceConstants.APPLICATION_SQLWAYS_USE_ISPIRER_SQLWAYS,
				"Use Ispirer's SQLWays Toolkit to convert pl/sql to java, uncheck this otherwise.",
				getFieldEditorParent());
		addField(useIspirerSqlWaysFieldEditor);
		crudAsNonCrudFieldEditor = new BooleanFieldEditor(
				PreferenceConstants.APPLICATION_MIGRATE_CRUD_TO_JAVA,
				"Migrate crud level triggers(POST-QUERY, PRE-UPDATE etc.) to java.",
				getFieldEditorParent());

		addField(crudAsNonCrudFieldEditor);
		// programUnitAsNonCrudFieldEditor = new BooleanFieldEditor(
		// PreferenceConstants.APPLICATION_MIGRATE_PU_TO_JAVA,
		// "Migrate program units triggers to java.",
		// getFieldEditorParent());

		// addField(programUnitAsNonCrudFieldEditor);

	}

	public void initializeDefaultPreferences() {
		IPreferenceStore store = PreferencePlugin.getDefault()
				.getPreferenceStore();
		store.setDefault(
				PreferenceConstants.APPLICATION_SQLWAYS_USE_ISPIRER_SQLWAYS,
				true);
		store.setDefault(PreferenceConstants.APPLICATION_MIGRATE_CRUD_TO_JAVA,
				true);
		// store.setDefault(PreferenceConstants.APPLICATION_MIGRATE_PU_TO_JAVA,
		// true);
		setPreferenceStore(store);

	}

	// @Override
	// protected void performDefaults() {
	// crudAsNonCrudFieldEditor.loadDefault();
	// super.performDefaults();
	// }
	//
	// @Override
	// protected void performApply() {
	// crudAsNonCrudFieldEditor.store();
	// super.performApply();
	// }
}
