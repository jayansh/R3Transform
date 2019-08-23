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
import org.eclipse.swt.widgets.Group;

public class PreMigrationProcess {

	protected Shell shell;
	private Text txtFileSrc;
	private Text text;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			PreMigrationProcess window = new PreMigrationProcess();
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
		shell = new Shell();
		shell.setLocation(-14, -201);
		shell.setSize(549, 412);
		shell.setText("SWT Application");
		
		Label lblSourceFolder = new Label(shell, SWT.NONE);
		lblSourceFolder.setBounds(10, 10, 86, 18);
		lblSourceFolder.setText("Source Folder");
		
		txtFileSrc = new Text(shell, SWT.BORDER);
		txtFileSrc.setBounds(118, 9, 262, 19);
		
		Button btnBrowseSrc = new Button(shell, SWT.NONE);
		btnBrowseSrc.setBounds(403, 10, 68, 23);
		btnBrowseSrc.setText("Browse");
		
		text = new Text(shell, SWT.BORDER);
		text.setBounds(118, 45, 262, 19);
		
		Label lblDestinationFolder = new Label(shell, SWT.NONE);
		lblDestinationFolder.setBounds(10, 45, 102, 19);
		lblDestinationFolder.setText("Destination Folder");
		
		Button btnBrowse = new Button(shell, SWT.NONE);
		btnBrowse.setBounds(403, 45, 68, 23);
		btnBrowse.setText("Browse");
		
		Group grpCleanUpProcess = new Group(shell, SWT.NONE);
		grpCleanUpProcess.setText("Clean Up Process");
		grpCleanUpProcess.setBounds(10, 97, 502, 98);
		
		Button btnProcess = new Button(grpCleanUpProcess, SWT.NONE);
		btnProcess.setBounds(10, 28, 67, 23);
		btnProcess.setText("Process");
		
		Label lblProcess = new Label(grpCleanUpProcess, SWT.NONE);
		lblProcess.setBounds(94, 20, 398, 60);
		lblProcess.setText("During the Clean-Up process, a) all dependencies from referenced object\r\nreferences are removed b) The value attributes are copied back to the FMB\r\nmodule, and the c) The referenced objects are detached");
		
		Group grpDefinationFileGeneration = new Group(shell, SWT.NONE);
		grpDefinationFileGeneration.setText("Defination File Generation");
		grpDefinationFileGeneration.setBounds(10, 201, 502, 96);
		
		Button btnGenerate = new Button(grpDefinationFileGeneration, SWT.NONE);
		btnGenerate.setBounds(10, 27, 68, 23);
		btnGenerate.setText("Generate");
		
		Label lblGenerate = new Label(grpDefinationFileGeneration, SWT.NONE);
		lblGenerate.setBounds(95, 26, 397, 60);
		lblGenerate.setText("Once the clean-Up is performed, Generate the Definition File. Send this file to\r\nRelease3 at licensing@release3.com. A license file will be issued on the basis of\r\nthis definition file.");

	}
}
