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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBException;

import org.eclipse.core.runtime.Platform;

import com.converter.toolkit.ui.JAXBFMBReader;
import com.oracle.xmlns.forms.Module;

/**
 * 
 * @author Jayansh Shinde
 * 
 */

public class CleanupForm extends AbstractForm {
	
	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
			this);
	/**
	 * This file contains the form triggers and program units.
	 */
	

	public CleanupForm(){
		super();
	}
	public CleanupForm(File form, String status) {
		super(form, status,form.getName().substring(0,form.getName().indexOf('.')) );
	}

	public void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

	

	public void save(boolean isFinish) {
		try {
			jaxbFMBReader.save(formXml.getPath());
			if (isFinish) {
				XMLToForm xml2form = new XMLToForm();
				xml2form.xml2form(formXml);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
	
}
