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
package com.release3.transform.pref;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReadWrite {

	private Properties properties;
	private File propertyFile;
	private static PropertiesReadWrite propertiesReadWrite;

	// private PropertiesReadWrite(File propertyFile) {
	// properties = new Properties();
	// this.propertyFile = propertyFile;
	// }

	public static PropertiesReadWrite getPropertiesReadWrite() {
		if (propertiesReadWrite == null) {
			propertiesReadWrite = new PropertiesReadWrite();
		}
		return propertiesReadWrite;
	}

	private PropertiesReadWrite() {
		properties = new Properties();

	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public File getPropertyFile() {
		return propertyFile;
	}

	public void setPropertyFile(File propertyFile) {
		this.propertyFile = propertyFile;
	}

	/**
	 * Read properties file.
	 * 
	 */
	public void read() {
		try {
			properties.load(new FileInputStream(propertyFile));
		}catch (FileNotFoundException e) {
			try {
				propertyFile.createNewFile();
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
//			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Write properties file.
	 * 
	 */
	public void write() {
		try {
			properties.store(new FileOutputStream(propertyFile), null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
