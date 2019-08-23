package com.release3.tf.pll.model;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.release3.tf.core.form.Settings;

public class PLLMigrationModelProvider {
	private Vector<MigrationPLL> formsList;
	private static PLLMigrationModelProvider content;
	private Vector<MigrationPLL> selectedPLLs;

	private PLLMigrationModelProvider() {
		formsList = new Vector<MigrationPLL>();
		selectedPLLs = new Vector<MigrationPLL>();
		loadForms();
	}

	public static PLLMigrationModelProvider getInstance() {
		if (content != null) {
			return content;
		} else {
			content = new PLLMigrationModelProvider();
			return content;
		}
	}

	public void loadForms() {
		if (formsList.size() > 0) {
			formsList.clear();
		}
		String fmbRootDir = Settings.getSettings().getFmbRootDir();

		if (fmbRootDir == null) {
			fmbRootDir = "";
		}
		if (fmbRootDir != null) {
			File currentFormsDir = new File(fmbRootDir);
			if (currentFormsDir.exists()) {
				FileFilter formFilter = new FileFilter() {
					public boolean accept(File file) {
						String name = file.getName().toLowerCase();
						return name.endsWith("pll") || name.endsWith("PLL");
					}

				};
				File forms[] = currentFormsDir.listFiles(formFilter);
				for (File file : forms) {

					MigrationPLL migrationForm = new MigrationPLL(file, "?",
							file.getName());

					formsList.add(migrationForm);
				}
			}
		}
	}

	public Vector<MigrationPLL> getPLLList() {
		return formsList;
	}

	public Vector<MigrationPLL> getSelectedPLLs() {
		return selectedPLLs;
	}

}
