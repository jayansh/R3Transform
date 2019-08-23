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
package com.release3.tf.plsql.process;

import java.util.List;

import com.converter.toolkit.ui.FormsObject;
import com.oracle.xmlns.forms.Block;
import com.oracle.xmlns.forms.FormModule;
import com.oracle.xmlns.forms.Item;

import com.release3.tf.core.form.XmlGen;
import com.release3.tf.migration.model.MigrationForm;

public class JavaEventValidation {
	private FormModule formModule;
	private MigrationForm migrationForm;

	public JavaEventValidation(MigrationForm migrationForm) {
		this.migrationForm = migrationForm;
		String xmlFilePath = migrationForm.getForm().getAbsolutePath()
				.replace(".fmb", "_fmb")
				+ ".xml";
		if (xmlFilePath.indexOf(".FMB") > 0) {
			xmlFilePath = xmlFilePath.replace(".FMB", "_FMB");
		}
		/* xml generation */
		XmlGen.makeXml(migrationForm.getForm().getAbsolutePath());
		migrationForm.setFmbXmlPath(xmlFilePath);
		this.formModule = migrationForm.getModule().getFormModule();
	}

	public boolean buttonItemValidation(String blockName, String itemName) {
		List<FormsObject> formsObjectList = this.formModule.getChildren();
		for (FormsObject formsObject : formsObjectList) {
			if (formsObject instanceof Block) {
				Block block = (Block) formsObject;
				if (block.getName().equalsIgnoreCase(blockName)
						|| block.getName() == blockName) {
					List<FormsObject> blockChildren = block.getChildren();
					for (FormsObject blockChild : blockChildren) {
						if (blockChild instanceof Item) {
							Item item = (Item) blockChild;
							if (item.getName().equalsIgnoreCase(itemName)
									|| item.getName() == itemName) {
								if (item.getItemType() != null
										&& (item.getItemType()
												.equalsIgnoreCase("Push Button") || item
												.getItemType() == "Push Button")) {
									return true;
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

}
