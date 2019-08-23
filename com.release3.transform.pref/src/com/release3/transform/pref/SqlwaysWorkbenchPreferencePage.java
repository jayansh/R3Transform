package com.release3.transform.pref;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class SqlwaysWorkbenchPreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {
	
	private DirectoryFieldEditor sqlwaysHomeFieldEditor;
	private BooleanFieldEditor overwriteFuncMapXmlFieldEditor;
	private BooleanFieldEditor overwriteItemVariableXmlFieldEditor;

	/**
	 * Create the preference page.
	 */
	public SqlwaysWorkbenchPreferencePage() {
		super(FLAT);
		// Set the preference store for the preference page.
		IPreferenceStore store = PreferencePlugin.getDefault()
				.getPreferenceStore();
		setPreferenceStore(store);

	}

	@Override
	public void init(IWorkbench workbench) {
		initializeDefaultPreferences();

	}

	@Override
	protected void createFieldEditors() {
		
		sqlwaysHomeFieldEditor = new DirectoryFieldEditor(
				PreferenceConstants.APPLICATION_SQLWAYS_HOME,
				"Ispirer's SQLWays Home\n(The root directory where Ispirer's SQLWays is installed):",
				getFieldEditorParent());
		addField(sqlwaysHomeFieldEditor);
		overwriteFuncMapXmlFieldEditor = new BooleanFieldEditor(
				PreferenceConstants.APPLICATION_SQLWAYS_OVERWRITE_FUNCTION_MAP_XML,
				"Overwrite existing function_maps.xml inside SourceForms folder.",
				getFieldEditorParent());
		addField(overwriteFuncMapXmlFieldEditor);
		overwriteItemVariableXmlFieldEditor = new BooleanFieldEditor(
				PreferenceConstants.APPLICATION_SQLWAYS_OVERWRITE_ITEM_VARIABLE_XML,
				"Overwrite existing item_variable.xml inside SourceForms folder.",
				getFieldEditorParent());
		addField(overwriteItemVariableXmlFieldEditor);
	}

	public void initializeDefaultPreferences() {
		IPreferenceStore store = PreferencePlugin.getDefault()
				.getPreferenceStore();
		
		store.setDefault(PreferenceConstants.APPLICATION_SQLWAYS_HOME,
				"C:\\Release3\\TransformToolkit\\Ispirer\\SQLWays6");
		store.setDefault(
				PreferenceConstants.APPLICATION_SQLWAYS_OVERWRITE_FUNCTION_MAP_XML,
				false);
		store.setDefault(
				PreferenceConstants.APPLICATION_SQLWAYS_OVERWRITE_ITEM_VARIABLE_XML,
				false);
		setPreferenceStore(store);

	}

}
