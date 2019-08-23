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

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.release3.transform.lov.LOVRecordGroupModelList;
import com.release3.transform.lov.LOVRecordGroupModelList.LOVRecordGroupModel;

public class LovXmlReaderWriter {

	private LOVRecordGroupModelList lovRgModelList;
	private Document doc;
	private DocumentBuilderFactory factory;
	private DocumentBuilder builder;

	private JAXBContext jc;

	public void loadLovRgModelList(File xmlFile) {
		try {
			// create a JAXBContext capable of handling classes generated into
			// the primer.po package
			jc = JAXBContext.newInstance("com.release3.transform.lov");

			// create an Unmarshaller
			Unmarshaller u = jc.createUnmarshaller();

			FileReader reader = new FileReader(xmlFile);
			InputSource is = new InputSource(reader);

			lovRgModelList = (LOVRecordGroupModelList) u.unmarshal(is);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void setLovRgModelList(LOVRecordGroupModelList lovRgModelList) {
		this.lovRgModelList = lovRgModelList;
	}

	public void generateCustomization(File customizationFile)
			throws ParserConfigurationException, SAXException, IOException {
		factory = DocumentBuilderFactory.newInstance();
		builder = factory.newDocumentBuilder();
		if (customizationFile.exists() && customizationFile.length() != 0) {
			System.out.println();
			doc = builder.parse(customizationFile);
			Element rootElement = doc.getDocumentElement();
			if (rootElement == null) {
				rootElement = doc.createElement("R3");
			}
			NodeList nodeList = doc.getElementsByTagName("RecordGroup");
			for (int j = 0; j < lovRgModelList.getLOVRecordGroupModel().size(); j++) {
				LOVRecordGroupModel model = lovRgModelList
						.getLOVRecordGroupModel().get(j);
				boolean nodeExist = false;
				if (nodeList.getLength() > 0) {
					for (int i = 0; i < lovRgModelList.getLOVRecordGroupModel()
							.size(); i++) {

						Element rgElement = (Element) nodeList.item(i);
						if (rgElement.getAttribute("Name").equals(
								model.getRecordGroup().getName())) {
							if (rgElement.getAttribute("Table") == null
									&& rgElement.getAttribute("Table").length() == 0) {
								rgElement.setAttribute("Table", model
										.getTableName());

							}
							nodeExist = true;
						}
					}
				}
				if (!nodeExist) {
					Element rgElement = doc.createElement("RecordGroup");
					rgElement.setAttribute("Name", model.getRecordGroup()
							.getName());
					rgElement.setAttribute("Table", model.getTableName());
					rootElement.appendChild(rgElement);
				}
			}

		} else {
			// customizationFile.createNewFile();
			doc = builder.newDocument();
			Element rootElement = doc.createElement("R3");
			doc.appendChild(rootElement);
			for (int i = 0; i < lovRgModelList.getLOVRecordGroupModel().size(); i++) {
				LOVRecordGroupModel model = lovRgModelList
						.getLOVRecordGroupModel().get(i);

				Element rgElement = doc.createElement("RecordGroup");
				rgElement
						.setAttribute("Name", model.getRecordGroup().getName());
				rgElement.setAttribute("Table", model.getTableName());
				rootElement.appendChild(rgElement);
			}

		}

	}

	private String getTableName(String recordGroupName) {

		for (int i = 0; i < lovRgModelList.getLOVRecordGroupModel().size(); i++) {
			LOVRecordGroupModel model = lovRgModelList.getLOVRecordGroupModel()
					.get(i);

			if (model.getRecordGroup().getName().equalsIgnoreCase(
					recordGroupName)
					|| model.getRecordGroup().getName() == recordGroupName) {
				return model.getTableName();
			}
		}
		return null;
	}

	public String getTableName(String xmlFilePath, String rgName) {
		File xmlFile = new File(xmlFilePath);
		if (xmlFile.exists()) {
			loadLovRgModelList(xmlFile);
			return getTableName(rgName);
		} else {
			return null;
		}
	}

	public void writeToFile(File xmlFile)
			throws TransformerConfigurationException,
			TransformerFactoryConfigurationError {
		Transformer transformer = TransformerFactory.newInstance()
				.newTransformer();
		// initialize StreamResult with File object to save to file
		Result result = new StreamResult(xmlFile);
		// StreamResult result = new StreamResult(System.out);

		DOMSource source = new DOMSource(doc);
		try {
			transformer.transform(source, result);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {

		String file = new String(
				"C:\\transformtoolkit\\SampleForms\\DEPT_EMP_fmb_LOV.xml");

		// File custFile = new File(
		// "C:\\transformtoolkit\\SampleForms\\ADMGNRNVO_customization.xml");
		LovXmlReaderWriter lovXml = new LovXmlReaderWriter();

		System.out.println(lovXml.getTableName(file, "DEPARTMENTS"));
		// try {
		// lovXml.generateCustomization(custFile);
		// try {
		// lovXml.writeToFile(custFile);
		// } catch (TransformerConfigurationException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (TransformerFactoryConfigurationError e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// } catch (ParserConfigurationException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (SAXException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// lovXml.getRecordGroupName();

	}

}
