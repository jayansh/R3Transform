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

import java.io.File;

import org.eclipse.core.runtime.Platform;

import com.converter.toolkit.ui.JAXBFMBReader;
import com.oracle.xmlns.forms.Module;

public abstract class AbstractForm {
	protected String formName;
	/**
	 * XML File for Form (which converted to xml).
	 */
	protected File formXml;
	/**
	 * Form file.
	 */
	protected File form;

	/**
	 * status - cleanup done or not
	 */
	protected String status;
	protected Module module;
	protected String fmbXmlPath;
	/**
	 * This file contains the form triggers and program units.
	 */
	protected File plSqlAnalysisFile;
	protected JAXBFMBReader jaxbFMBReader = new JAXBFMBReader();

	protected AbstractForm() {

	}

	protected AbstractForm(File form, String status, String formName) {
		this.form = form;
		this.formName = formName;
		this.status = status;
	}

	public String getFormName() {
		// System.out.println(formName);
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public File getForm() {
		return form;
	}

	public void setForm(File form) {
		this.form = form;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFmbXmlPath() {
		return fmbXmlPath;
	}

	public void setFmbXmlPath(String fmbXmlPath) {
		this.fmbXmlPath = fmbXmlPath;
	}

	public File getFormXml() {
		return formXml;
	}

	public void setFormXml(File formXml) {
		this.formXml = formXml;
	}

	public File getPlsqlAnalysisFile() {
		String xmlParentFilePath = Platform.getInstanceLocation().getURL()
				.getFile()
				+ File.separator
				+ "xmlFiles"
				+ File.separator
				+ this.getForm().getName()
						.substring(0, this.getForm().getName().indexOf("."));

		File xmlParentFile = new File(xmlParentFilePath);
		if (!xmlParentFile.exists()) {
			xmlParentFile.mkdirs();
		}
		String xmlPath = xmlParentFilePath + File.separator
				+ this.getForm().getName().replace(".", "_") + "_plsql.xml";
		plSqlAnalysisFile = new File(xmlPath);
		return plSqlAnalysisFile;
	}

	public File getR3CorePlSqlXmlFile() {
		String xmlParentFilePath = Platform.getInstanceLocation().getURL()
				.getFile()
				+ File.separator
				+ "xmlFiles"
				+ File.separator
				+ this.getForm().getName()
						.substring(0, this.getForm().getName().indexOf("."));

		File xmlParentFile = new File(xmlParentFilePath);
		if (!xmlParentFile.exists()) {
			xmlParentFile.mkdirs();
		}
		String xmlPath = xmlParentFilePath
				+ File.separator
				+ this.getForm().getName().replace(".fmb", "")
						.replace(".FMB", "") + "_R3CorePlSql.xml";
		return new File(xmlPath);

	}

	public File getR3JavaSqlXmlFile() {
		String xmlParentFilePath = Platform.getInstanceLocation().getURL()
				.getFile()
				+ File.separator
				+ "xmlFiles"
				+ File.separator
				+ this.getForm().getName()
						.substring(0, this.getForm().getName().indexOf("."));

		File xmlParentFile = new File(xmlParentFilePath);
		if (!xmlParentFile.exists()) {
			xmlParentFile.mkdirs();
		}
		String xmlPath = xmlParentFilePath
				+ File.separator
				+ this.getForm().getName().replace(".fmb", "")
						.replace(".FMB", "") + "_R3JavaSql.xml";
		return new File(xmlPath);

	}

	public File getR3NonCRUDXmlFile() {
		String xmlParentFilePath = Platform.getInstanceLocation().getURL()
				.getFile()
				+ File.separator
				+ "xmlFiles"
				+ File.separator
				+ this.getForm().getName()
						.substring(0, this.getForm().getName().indexOf("."));

		File xmlParentFile = new File(xmlParentFilePath);
		if (!xmlParentFile.exists()) {
			xmlParentFile.mkdirs();
		}
		String xmlPath = xmlParentFilePath
				+ File.separator
				+ this.getForm().getName().replace(".fmb", "")
						.replace(".FMB", "") + "_NONCRUD.xml";
		return new File(xmlPath);

	}

	public File getR3CRUDXmlFile() {
		String xmlParentFilePath = Platform.getInstanceLocation().getURL()
				.getFile()
				+ File.separator
				+ "xmlFiles"
				+ File.separator
				+ this.getForm().getName()
						.substring(0, this.getForm().getName().indexOf("."));

		File xmlParentFile = new File(xmlParentFilePath);
		if (!xmlParentFile.exists()) {
			xmlParentFile.mkdirs();
		}
		String xmlPath = xmlParentFilePath
				+ File.separator
				+ this.getForm().getName().replace(".fmb", "")
						.replace(".FMB", "") + "_CRUD.xml";
		return new File(xmlPath);

	}

	public File getR3ProgramUnitAfterXmlFile() {
		String xmlParentFilePath = Platform.getInstanceLocation().getURL()
				.getFile()
				+ File.separator
				+ "xmlFiles"
				+ File.separator
				+ this.getForm().getName()
						.substring(0, this.getForm().getName().indexOf("."));

		File xmlParentFile = new File(xmlParentFilePath);
		if (!xmlParentFile.exists()) {
			xmlParentFile.mkdirs();
		}
		String xmlPath = xmlParentFilePath
				+ File.separator
				+ this.getForm().getName().replace(".fmb", "")
						.replace(".FMB", "") + "_R3PU_After.xml";
		return new File(xmlPath);

	}

	public File getPUXmlFile() {
		String xmlParentFilePath = Platform.getInstanceLocation().getURL()
				.getFile()
				+ File.separator
				+ "xmlFiles"
				+ File.separator
				+ this.getForm().getName()
						.substring(0, this.getForm().getName().indexOf("."));

		File xmlParentFile = new File(xmlParentFilePath);
		if (!xmlParentFile.exists()) {
			xmlParentFile.mkdirs();
		}
		String xmlPath = xmlParentFilePath
				+ File.separator
				+ this.getForm().getName().replace(".fmb", "")
						.replace(".FMB", "") + "_R3PU.xml";
		return new File(xmlPath);

	}

	public String getFormsMapFilePath() {
		String xmlParentFilePath = Platform.getInstanceLocation().getURL()
				.getFile()
				+ File.separator
				+ "xmlFiles"
				+ File.separator
				+ this.getForm().getName()
						.substring(0, this.getForm().getName().indexOf("."));

		File xmlParentFile = new File(xmlParentFilePath);
		if (!xmlParentFile.exists()) {
			xmlParentFile.mkdirs();
		}
		String xmlPath = xmlParentFilePath + File.separator
				+ this.getForm().getName().replace(".", "_") + "_FormsMap.xml";
		return xmlPath;
	}

	public String getProcFuncCallMapFilePath() {
		String xmlParentFilePath = Platform.getInstanceLocation().getURL()
				.getFile()
				+ File.separator
				+ "xmlFiles"
				+ File.separator
				+ this.getForm().getName()
						.substring(0, this.getForm().getName().indexOf("."));

		File xmlParentFile = new File(xmlParentFilePath);
		if (!xmlParentFile.exists()) {
			xmlParentFile.mkdirs();
		}
		String xmlPath = xmlParentFilePath + File.separator
				+ this.getFormName() + "_ProcFuncCallMap.xml";
		return xmlPath;
	}

	public String getDBPlSqlFilePath() {
		String xmlParentFilePath = Platform.getInstanceLocation().getURL()
				.getFile()
				+ File.separator
				+ "xmlFiles"
				+ File.separator
				+ this.getForm().getName()
						.substring(0, this.getForm().getName().indexOf("."));

		File xmlParentFile = new File(xmlParentFilePath);
		if (!xmlParentFile.exists()) {
			xmlParentFile.mkdirs();
		}
		String xmlPath = xmlParentFilePath + File.separator
				+ this.getForm().getName().replace(".", "_") + "_DBPlSql.xml";
		return xmlPath;
	}

	public File getFormWorkspaceLocation() {
		String formWorkspacePath = Platform.getInstanceLocation().getURL()
				.getFile()
				+ File.separator
				+ "xmlFiles"
				+ File.separator
				+ this.getForm().getName()
						.substring(0, this.getForm().getName().indexOf("."));
		File formWorkspaceLocation = new File(formWorkspacePath);
		return formWorkspaceLocation;
	}

	public Module getModule() {
		if (module == null) {
			if (fmbXmlPath != null) {
				module = jaxbFMBReader.init(fmbXmlPath);
			} else {
				fmbXmlPath = form.getAbsolutePath().replace(".", "_")+".xml";
				formXml = new File(fmbXmlPath);
				form2xml();
			}

		}
		return module;
	}

	public File getXmlFolderPath() {
		String xmlParentFilePath = Platform.getInstanceLocation().getURL()
				.getFile()
				+ File.separator
				+ "xmlFiles"
				+ File.separator
				+ this.getForm().getName()
						.substring(0, this.getForm().getName().indexOf("."));

		File xmlParentFile = new File(xmlParentFilePath);
		if (!xmlParentFile.exists()) {
			xmlParentFile.mkdirs();
		}
		return xmlParentFile;
	}

	public void form2xml() {
		FormtoXML formtoxml = new FormtoXML();
		formtoxml.form2xml(form, formXml);
		module = jaxbFMBReader.init(formXml.getPath());
		status = "True";
	}
}
