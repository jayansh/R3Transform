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
package com.release3.plsql.analysis.property.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FormPropertiesFile extends File {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7542081079967560343L;

	public FormPropertiesFile(String pathname) {
		super(pathname);

	}

	public FormPropertiesFile(File file) {
		super(file.getAbsolutePath());
	}

	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {

		this.text = text;
		try {
			writeTextToPhysicalFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void writeTextToPhysicalFile() throws IOException {
		if (text != null) {
			FileWriter fileW = new FileWriter(this);
			fileW.write(text);
			fileW.close();
		}
	}
}
