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
package com.release3.cleanupwizard;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import com.converter.premigration.FileFilterExt;
import com.release3.tf.core.form.Settings;
import com.release3.transform.util.Utils;

/**
 * 
 * @author Jayansh This class load convert form to XML load xml using DOM parser
 */
@Deprecated
/**
 * Moved to com.release3.tf.core.form.FormtoXML
 */
public class FormtoXML {

	// private List<Module> moduleList;
	private List<File> formXmlList;

	public FormtoXML() {
		// moduleList = new ArrayList<Module>();
		formXmlList = new ArrayList<File>();
	}

	/**
	 * Converting multiple forms inside a directory to respective xml file.
	 * 
	 * @param srcDir
	 *            - source directory containing all the forms.
	 */
	public void formsList2xmlList(File srcDir) {

		String[] str = { "fmb" };
		FileFilter filter = new FileFilterExt("*.fmb", str);
		File[] fileArr = srcDir.listFiles(filter);
		String devSuitePath = Settings.getSettings().getOracleDeveloperSuite();

		for (int i = 0; i < fileArr.length; i++) {

			String command = devSuitePath + File.separator + "bin"
					+ File.separator + "frmf2xml.bat OVERWRITE=YES "
					+ fileArr[i];

			try {
				Process p = Runtime.getRuntime().exec("cmd.exe /C " + command);

				BufferedReader in = new BufferedReader(new InputStreamReader(p
						.getInputStream()));
				String line = in.readLine();
				while (line != null) {
					line = in.readLine();
					System.out.println(line);
				}
				in.close();
				p.waitFor();
				String xmlFilePath = fileArr[i].getParent() + File.separator
						+ fileArr[i].getName().replaceFirst(".fmb", "_fmb")
						+ ".xml";
				formXmlList.add(new File(xmlFilePath));

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * 
	 * @param srcFormFile
	 *            - Single form can converted to xml file using this.
	 */
	public void form2xml(File srcFormFile, File destFormXml) {

		String devSuitePath = Settings.getSettings().getOracleDeveloperSuite();

		String command = devSuitePath + File.separator + "bin" + File.separator
				+ "frmf2xml.bat OVERWRITE=YES " + srcFormFile;
		try {
			Process p = Runtime.getRuntime().exec("cmd.exe /C " + command);
			BufferedReader in = new BufferedReader(new InputStreamReader(p
					.getInputStream()));
			String line = in.readLine();
			while (line != null) {
				line = in.readLine();
				System.out.println(line);
			}
			in.close();
			p.waitFor();
			String xmlFilePath = srcFormFile.getParent() + File.separator
					+ srcFormFile.getName().replaceFirst(".fmb", "_fmb")
					+ ".xml";
			File srcFile = new File(xmlFilePath);
			if (!destFormXml.equals(srcFile)) {
				Utils.copyFile(srcFile, destFormXml);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<File> getFormXmlList() {
		return formXmlList;
	}

	/*
	 * public Document loadDocument(String url) throws
	 * FormToXMLConversionException {
	 * 
	 * DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
	 * domFactory.setNamespaceAware(true);
	 * 
	 * DocumentBuilder builder = null; try { builder =
	 * domFactory.newDocumentBuilder(); } catch (ParserConfigurationException e)
	 * { throw new FormToXMLConversionException(e); }
	 * 
	 * if (builder == null) { return null; }
	 * 
	 * 
	 * try { doc = builder.parse(url); } catch (SAXException e) { throw new
	 * FormToXMLConversionException("Unable to load document", e); } catch
	 * (IOException e) { throw new
	 * FormToXMLConversionException("Unable to load document", e); }
	 * 
	 * return doc; }
	 */
	public void saveDocument(Document doc, String url) throws Exception {

		Source source = new DOMSource(doc);

		File file = new File(url);
		Result result = new StreamResult(file);

		try {
			Transformer xformer = TransformerFactory.newInstance()
					.newTransformer();
			xformer.transform(source, result);
		} catch (TransformerConfigurationException e) {
			throw new Exception("Unable to save document", e);
		} catch (TransformerException e) {
			throw new Exception("Unable to save document", e);
		}
	}

}
