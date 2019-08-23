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
package com.converter.toolkit.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.sax.SAXSource;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class JAXBXMLReader {
	private Object poElement;
	private Unmarshaller u;
	private Marshaller m;
	private JAXBContext jc;

	public JAXBXMLReader() {
	}

	public Object init(File xmlFile, Class className) {
		try {
			// create a JAXBContext capable of handling classes generated into
			// the primer.po package
			jc = JAXBContext.newInstance(className);

			// create an Unmarshaller
			u = jc.createUnmarshaller();

			// Create an XMLReader to use with our filter
			XMLReader xmlReader = XMLReaderFactory.createXMLReader();

			// unmarshal a po instance document into a tree of Java content
			// objects composed of classes from the primer.po package.
			// InputStream io = this.getClass().getResourceAsStream(resource);

			InputStream inStream = new FileInputStream(xmlFile);
			InputStreamReader inReader = new InputStreamReader(inStream,
					"UTF-8");

			Reader reader = new InvalidXMLCharacterFilterReader(inReader);

			// Prepare the input, in this case a java.io.File (output)
			InputSource is = new InputSource(reader);

			// Do unmarshalling
			poElement = u.unmarshal(is);
			return poElement;
		} catch (Exception e) {
			return null;
		}

	}

	public Object init(InputStream inStream, String nameSpaceUrl,
			Class className) {
		try {
			// create a JAXBContext capable of handling classes generated into
			// the primer.po package
			jc = JAXBContext.newInstance(className);

			// create an Unmarshaller
			u = jc.createUnmarshaller();

			// Create an XMLReader to use with our filter
			XMLReader xmlReader = XMLReaderFactory.createXMLReader();

			// Create the filter (to add namespace) and set the xmlReader as its
			// parent.
			NamespaceFilter inFilter = new NamespaceFilter(nameSpaceUrl, true);
			try {
				inFilter.startPrefixMapping("", nameSpaceUrl);
			} catch (SAXException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			inFilter.setParent(xmlReader);

			// unmarshal a po instance document into a tree of Java content
			// objects composed of classes from the primer.po package.
			// InputStream io = this.getClass().getResourceAsStream(resource);

			InputStreamReader inReader = new InputStreamReader(inStream,
					"UTF-8");

			Reader reader = new InvalidXMLCharacterFilterReader(inReader);

			// Prepare the input, in this case a java.io.File (output)
			InputSource is = new InputSource(reader);

			// Create a SAXSource specifying the filter
			SAXSource source = new SAXSource(inFilter, is);

			// Do unmarshalling
			poElement = u.unmarshal(is);
			return poElement;
		} catch (Exception e) {
			return null;
		}

	}

	// public Object readXml(File xmlFile, Class className) {
	// try {
	// // create a JAXBContext capable of handling classes generated into
	// // the primer.po package
	// jc = JAXBContext.newInstance(className);
	//
	// // create an Unmarshaller
	// u = jc.createUnmarshaller();
	//
	//
	//
	// // unmarshal a po instance document into a tree of Java content
	// // objects composed of classes from the primer.po package.
	// // InputStream io = this.getClass().getResourceAsStream(resource);
	//
	// InputStream inStream = new FileInputStream(xmlFile);
	// InputStreamReader inReader = new InputStreamReader(inStream);
	//
	//
	// // Prepare the input, in this case a java.io.File (output)
	// InputSource is = new InputSource(inReader);
	//
	// // Do unmarshalling
	// poElement = u.unmarshal(xmlFile);
	// return poElement;
	// } catch (Exception e) {
	// return null;
	// }
	//
	// }
}
