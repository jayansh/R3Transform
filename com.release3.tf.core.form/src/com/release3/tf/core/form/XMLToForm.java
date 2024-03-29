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
package com.release3.tf.core.form;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import com.release3.tf.core.form.Settings;

public class XMLToForm {
	public void xml2form(File srcfile) {

		String devSuitePath = Settings.getSettings().getOracleDeveloperSuite();
		String dbConnectionString = "USERID="
				+ Settings.getSettings().getDbSrcUser() + "/"
				+ Settings.getSettings().getDbSrcPassword() + "@"
				+ Settings.getSettings().getDbFormConnectionSID() + " ";
		String xml2FrmPath = devSuitePath + File.separator + "bin"
				+ File.separator + "frmxml2f.bat";
		// String command = OVERWRITE=YES " + dbConnectionString
		// // + File.separator + "frmxml2f.bat "
		// + srcfile;
		try {
			File frm2Xml = new File(xml2FrmPath);
			if (frm2Xml.exists()) {
				String batchCommand = xml2FrmPath + " OVERWRITE=YES " + dbConnectionString+ srcfile;
				xml2Frm(batchCommand);

			} else {
				// if 11g developer suite
				xml2FrmPath = Settings.getSettings().getOracleDeveloperSuite()
						+ File.separator + "forms" + File.separator
						+ "templates" + File.separator + "scripts"
						+ File.separator + "frmxml2f.bat";
				frm2Xml = new File(xml2FrmPath);
				if (frm2Xml.exists()) {
					String batchCommand = xml2FrmPath + " OVERWRITE=YES " + dbConnectionString
							+ srcfile;
					xml2Frm(batchCommand);
				} else {
					throw new Exception(
							"frmxml2f.bat not found. Oracle Forms Developer may not be configured properly.");
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// }
		}
	}

	private static void xml2Frm(String batchCommand) throws Exception {
		System.out.println(batchCommand);
		Process p = Runtime.getRuntime().exec("cmd.exe /C " + batchCommand);
		BufferedReader in = new BufferedReader(new InputStreamReader(
				p.getInputStream()));
		String line = in.readLine();
		while (line != null) {
			line = in.readLine();
			System.out.println(line);
		}
		in.close();
		p.waitFor();

	}
}
