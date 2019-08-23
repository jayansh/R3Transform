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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.Platform;

import com.release3.tf.core.License;
import com.release3.tf.core.Utils;

public class LicenseComboModelProvider {
	private List<License> licensesList;
	private static LicenseComboModelProvider content;

	private LicenseComboModelProvider() {
		licensesList = new ArrayList<License>();
		try {
			loadLicense();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static LicenseComboModelProvider getInstance() {
		if (content != null) {
			return content;
		} else {
			content = new LicenseComboModelProvider();
			return content;
		}
	}

	public void loadLicense() throws IOException {
		if (licensesList.size() > 0) {
			licensesList.clear();
		}

		// String fmbRootDir = Settings.getSettings().getFMBRootDir();
		String licensePath = Platform.getLocation().toFile().getPath()
				+ File.separator + "licenses";
		File licenseRootDir = new File(licensePath);

		if (licensePath != null) {
			if (!licenseRootDir.exists()) {
				licenseRootDir.mkdirs();
			}
			File currentFormsDir = new File(licensePath);
			FileFilter formFilter = new FileFilter() {
				public boolean accept(File file) {
					String name = file.getName().toLowerCase();
					return name.endsWith("lic") || name.endsWith("LIC");
				}

			};
			File licenses[] = currentFormsDir.listFiles(formFilter);
			for (File licenseFile : licenses) {
				if (licenseFile.length() > 0L) {
					License license = Utils.readLicenseFS(licenseFile);
					licensesList.add(license);
				}
			}
		}
	}

	public List<License> getLicenseList() {
		return licensesList;
	}
}
