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

import java.util.List;

import com.oracle.xmlns.forms.Module;
import com.release3.tf.migration.model.MigrationForm;

public class PropertiesModelProvider {
	private static PropertiesModelProvider content;
	private List<FormProperties> formPropertiesList;
	private Module currentModule;
	private MigrationForm currentForm;

	private PropertiesModelProvider() {

	}

	public static PropertiesModelProvider getInstance() {
		if (content == null) {
			content = new PropertiesModelProvider();
		}
		return content;

	}

	public void setFormPropertiesList(List<FormProperties> formPropertiesList) {
		this.formPropertiesList = formPropertiesList;
	}

	public List<FormProperties> getFormPropertiesList() {
		return formPropertiesList;
	}

	private String currentTriggerText;

	public String getCurrentTriggerText() {
		return currentTriggerText;
	}

	public void setCurrentTriggerText(String currentTriggerText) {
		this.currentTriggerText = currentTriggerText;
	}

	public Module getCurrentModule() {
		return currentModule;
	}

	public void setCurrentModule(Module currentModule) {
		this.currentModule = currentModule;
	}

	public MigrationForm getCurrentForm() {
		return currentForm;
	}

	public void setCurrentForm(MigrationForm currentForm) {
		this.currentForm = currentForm;
	}

	
}
