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
package com.release3.tf.migration.model;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import com.release3.tf.core.form.Settings;

public class MigrationModelProvider {
	private List<MigrationForm> formsList;
	private static MigrationModelProvider content;
	private List<MigrationForm> selectedForms;

	private MigrationModelProvider() {
		formsList = new ArrayList<MigrationForm>();
		selectedForms = new ArrayList<MigrationForm>();
		loadForms();
	}

	public static MigrationModelProvider getInstance() {
		if (content != null) {
			return content;
		} else {
			content = new MigrationModelProvider();
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
						return name.endsWith("fmb") || name.endsWith("FMB");
					}

				};
				File forms[] = currentFormsDir.listFiles(formFilter);
				for (File file : forms) {

					MigrationForm migrationForm = new MigrationForm(file, "?");
					if (!migrationForm.isCustomizationExist()) {
						migrationForm
								.setStatus("Customization file not found.");
					}
					formsList.add(migrationForm);
				}
			}
		}
	}

	public List<MigrationForm> getFormList() {
		return formsList;
	}

	public List<MigrationForm> getSelectedForms() {
		return selectedForms;
	}

	
}
