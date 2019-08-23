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
package com.release3.transform;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class DBPreferences {

	protected Shell shell;
	private Text txtAppName;
	private Text txtDataBaseString;
	private Text text;
	private Text txtDataBasePwd;
	private Text txtOutputDir;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			DBPreferences window = new DBPreferences();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBounds(10, 10, 422, 246);
		
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("SWT Application");
		
		Label lblApplicationName = new Label(shell, SWT.NONE);
		lblApplicationName.setBounds(10, 23, 105, 13);
		lblApplicationName.setText("Application Name:");
		
		txtAppName = new Text(shell, SWT.BORDER);
		txtAppName.setBounds(162, 20, 254, 19);
		
		Label lblDataBaseString = new Label(shell, SWT.NONE);
		lblDataBaseString.setBounds(10, 66, 146, 13);
		lblDataBaseString.setText("Data Base String Connection:");
		
		txtDataBaseString = new Text(shell, SWT.BORDER);
		txtDataBaseString.setBounds(162, 63, 254, 19);
		
		Label lblDatabaseUser = new Label(shell, SWT.NONE);
		lblDatabaseUser.setBounds(10, 104, 85, 13);
		lblDatabaseUser.setText("DataBase User:");
		
		text = new Text(shell, SWT.BORDER);
		text.setBounds(162, 98, 254, 19);
		
		txtDataBasePwd = new Text(shell, SWT.BORDER | SWT.PASSWORD);
		txtDataBasePwd.setBounds(162, 138, 254, 19);
		
		Label lblDataBasePassword = new Label(shell, SWT.NONE);
		lblDataBasePassword.setBounds(10, 141, 105, 13);
		lblDataBasePassword.setText("DataBase Password:");
		
		txtOutputDir = new Text(shell, SWT.BORDER);
		txtOutputDir.setBounds(162, 181, 254, 19);
		
		Label lblOutputDirectory = new Label(shell, SWT.NONE);
		lblOutputDirectory.setBounds(10, 187, 105, 13);
		lblOutputDirectory.setText("Output Directory:");
		
		Button btnApply = new Button(shell, SWT.NONE);
		btnApply.setBounds(348, 216, 68, 23);
		btnApply.setText("Apply");
		
		

	}
}
