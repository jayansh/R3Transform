package com.release3.transform.pref;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

public class R3TransformPreferenceInitializer extends
		AbstractPreferenceInitializer {

	@Override
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
		
//		store.setDefault(PreferenceConstants.APPLICATION_CLIENT_ID, "");
//		store.setDefault(PreferenceConstants.APPLICATION_CLIENT_KEY , "");
		
	}

}
