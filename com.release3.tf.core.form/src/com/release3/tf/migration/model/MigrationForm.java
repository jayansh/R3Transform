package com.release3.tf.migration.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;

import com.converter.toolkit.ui.JAXBFMBReader;
import com.oracle.xmlns.forms.Module;
import com.release3.tf.core.form.AbstractForm;
import com.release3.tf.core.form.Settings;

public class MigrationForm extends AbstractForm{
//	private String formName;
	
	private Module module;
	/**
	 * Form file.
	 */
//	private File form;
	
	public boolean isPlSqlAnalysisFile() {
		if (getPlsqlAnalysisFile().exists()) {
			return true;
		}
		return false;
	}

	/**
	 * status - cleanup done or not
	 */
//	private String status;
	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
			this);
//	private String fmbXmlPath;

	public MigrationForm(File form, String status) {
		super(form,status,form.getName().substring(0,form.getName().indexOf('.')));
	}

	public void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

//	public String getFormName() {
//		// System.out.println(formName);
//		return formName;
//	}
//
//	public void setFormName(String formName) {
//		this.formName = formName;
//	}
//
//	public File getForm() {
//		return form;
//	}
//
//	public void setForm(File form) {
//		this.form = form;
//	}
//
//	public String getStatus() {
//		return status;
//	}
//
//	public void setStatus(String status) {
//		this.status = status;
//	}
//
//	public String getFmbXmlPath() {
//		return fmbXmlPath;
//	}
//
//	public void setFmbXmlPath(String fmbXmlPath) {
//		this.fmbXmlPath = fmbXmlPath;
//	}

	public boolean isCustomizationExist() {
		File customizationFile = new File(Settings.getSettings().getFmbRootDir()
				+ File.separator + formName
				+ "_customization.xml");
		return customizationFile.exists();
	}

	public void refresh() {
		isCustomizationExist();
	}

	

	
	

}
