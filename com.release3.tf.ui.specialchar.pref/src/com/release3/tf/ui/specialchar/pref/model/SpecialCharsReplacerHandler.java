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
package com.release3.tf.ui.specialchar.pref.model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.eclipse.core.runtime.Platform;

import com.converter.toolkit.ui.JAXBXMLReader;
import com.release3.specialchars.SpecialCharsReplacer;
import com.release3.specialchars.SpecialCharsReplacerModel;
import com.release3.tf.ui.specialchar.pref.SpecialCharPrefPlugin;
import com.release3.transform.util.SpecialCharConstants;

public class SpecialCharsReplacerHandler {

	private List<SpecialCharsReplacer> specialCharReplacerList;
	private SpecialCharsReplacerModel specialCharsReplacerModel;

	public SpecialCharsReplacerHandler() {
		try {
			loadSpecialCharsList();
			if (specialCharReplacerList == null
					|| specialCharReplacerList.size() == 0) {
				loadDefaultSpecialCharList();
			}
		} catch (Exception e) {
			e.printStackTrace();
			loadDefaultSpecialCharList();
		}
	}

	/**
	 * load the existing special chars list or create new.
	 * 
	 * @throws IOException
	 */
	public void loadSpecialCharsList() throws IOException {
		JAXBXMLReader reader = new JAXBXMLReader();
		specialCharsReplacerModel = (SpecialCharsReplacerModel) reader.init(
				readXmlFile(), "http://www.release3.com/SpecialChars",
				com.release3.specialchars.ObjectFactory.class);
		specialCharReplacerList = specialCharsReplacerModel
				.getSpecialCharsReplacer();
	}

	private InputStream readXmlFile() throws IOException {
		URL url = getSpecialCharPrefXmlURL();
		InputStream inputStream = url.openStream();
		return inputStream;
	}

	public URL getSpecialCharPrefXmlURL() {
		URL url = Platform.getBundle(SpecialCharPrefPlugin.PLUGIN_ID).getEntry(
				"schema/SpecialCharPref.xml");
		return url;
	}

	public void saveSpecialCharPrefXmlFile() throws JAXBException, IOException {
		saveSpecialCharPrefXmlFile(specialCharsReplacerModel);
	}

	private void saveSpecialCharPrefXmlFile(Object obj) throws JAXBException,
			IOException {
		JAXBContext context = JAXBContext.newInstance(obj.getClass());
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.setProperty("jaxb.encoding", "US-ASCII");
		URL fileUrl = org.eclipse.core.runtime.FileLocator
				.toFileURL(getSpecialCharPrefXmlURL());
		marshaller.marshal(obj, new FileWriter(fileUrl.getPath()));
	}

	public void addSpecialChar(SpecialCharsReplacer specialCharsReplacer) {
		specialCharsReplacerModel.getSpecialCharsReplacer().add(
				specialCharsReplacer);
	}

	public void removeSpecialChars(List elements) {
		specialCharsReplacerModel.getSpecialCharsReplacer().removeAll(elements);

	}

	public List<SpecialCharsReplacer> getSpecialCharReplacerList() {
		return specialCharReplacerList;
	}

	public void loadDefaultSpecialCharList() {

		specialCharReplacerList = defaultSpecialCharReplacerList();
		com.release3.specialchars.ObjectFactory objFact = new com.release3.specialchars.ObjectFactory();
		specialCharsReplacerModel = objFact.createSpecialCharsReplacerModel();
		specialCharsReplacerModel.getSpecialCharsReplacer().addAll(
				specialCharReplacerList);
		try {

			saveSpecialCharPrefXmlFile(specialCharsReplacerModel);
		} catch (JAXBException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public List<SpecialCharsReplacer> defaultSpecialCharReplacerList() {
		List<SpecialCharsReplacer> replacerList = new ArrayList<SpecialCharsReplacer>();
		for (int i = 0; i < SpecialCharConstants.SPECIAL_CHARS.length; i++) {
			char character = SpecialCharConstants.SPECIAL_CHARS[i];
			String newChar = "";
			SpecialCharsReplacer specialCharsReplacer = new SpecialCharsReplacer();
			specialCharsReplacer.setSpecialChar(character + "");
			switch (character) {
			case '<':
				newChar = SpecialCharConstants.LESS_THAN;
				break;

			case '>':
				newChar = SpecialCharConstants.GREATER_THAN;
				break;
			case '&':
				newChar = SpecialCharConstants.AND;
				break;
			case '"':
				newChar = SpecialCharConstants.DOUBL_QUOTE_REPLACER;
				break;
			case '\'':
				newChar = SpecialCharConstants.BACK_SLASH_REPLACER;
				break;
			case '�':
				newChar = SpecialCharConstants.SPANISH_A_REPLACER;
				break;
			case '�':
				newChar = SpecialCharConstants.SPANISH_E_REPLACER;
				break;
			case '�':
				newChar = SpecialCharConstants.SPANISH_I_REPLACER;
				break;
			case '�':
				newChar = SpecialCharConstants.SPANISH_O_REPLACER;
				break;
			case '�':
				newChar = SpecialCharConstants.SPANISH_U_REPLACER;
				break;
			case '�':
				newChar = SpecialCharConstants.SPANISH_CAP_A_REPLACER;
				break;
			case '�':
				newChar = SpecialCharConstants.SPANISH_CAP_E_REPLACER;
				break;
			case '�':
				newChar = SpecialCharConstants.SPANISH_CAP_I_REPLACER;
				break;
			case '�':
				newChar = SpecialCharConstants.SPANISH_CAP_O_REPLACER;
				break;
			case '�':
				newChar = SpecialCharConstants.SPANISH_CAP_U_REPLACER;
				break;
			case '�':
				newChar = SpecialCharConstants.SPANISH_N_REPLACER;
				break;
			case '�':
				newChar = SpecialCharConstants.SPANISH_CAP_N_REPLACER;
				break;
			case '�':
				newChar = SpecialCharConstants.SPANISH_U_REPLACER;
				break;

			}
			specialCharsReplacer.setReplacement(newChar);
			replacerList.add(specialCharsReplacer);
		}
		return replacerList;
	}

}
