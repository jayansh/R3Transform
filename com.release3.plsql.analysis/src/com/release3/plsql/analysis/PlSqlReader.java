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
package com.release3.plsql.analysis;

import java.io.FileReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;

import org.xml.sax.InputSource;

import com.converter.toolkit.ui.JAXBFMBReader;
import com.oracle.xmlns.forms.Module;

public class PlSqlReader {
	private JAXBFMBReader jaxbFMBReader = new JAXBFMBReader();
	private Module module;
//	private JAXBContext jc;
//	private JAXBElement<?> poElement;
	public Module readXml(String xmlPath) {

		module = jaxbFMBReader.init(xmlPath);
		return module;
	}
	
//	public Module read(String xmlPath){
//		try {
//			// create a JAXBContext capable of handling classes generated into
//			// the primer.po package
//			jc = JAXBContext.newInstance("com.oracle.xmlns.forms");
//
//			// create an Unmarshaller
//			Unmarshaller u = jc.createUnmarshaller();
//
//			FileReader reader = new FileReader(xmlPath);
//			InputSource is = new InputSource(reader);
//
//			poElement = (JAXBElement<?>) u.unmarshal(is);
//			return (Module) poElement.getValue();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
}
