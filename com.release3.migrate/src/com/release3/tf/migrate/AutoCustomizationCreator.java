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
package com.release3.tf.migrate;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.List;
import java.util.Vector;

import com.converter.toolkit.sqlparsing.CodeProcessor;
import com.converter.toolkit.sqlparsing.ProcessedCode;
import com.converter.toolkit.sqlparsing.Variable;
import com.converter.toolkit.ui.FormsObject;
import com.converter.toolkit.ui.custom.CustomizationPreferences;

import com.oracle.xmlns.forms.Block;
import com.oracle.xmlns.forms.FormModule;
import com.oracle.xmlns.forms.Item;
import com.oracle.xmlns.forms.Module;
import com.oracle.xmlns.forms.RecordGroup;
import com.oracle.xmlns.forms.Trigger;
import com.release3.tf.core.form.Settings;
import com.release3.tf.migration.model.MigrationForm;
import com.release3.transform.LovXmlReaderWriter;

public class AutoCustomizationCreator {
	private CodeProcessor sqlparser;
	private File cutomizationFile;

	private MigrationForm form;

	public AutoCustomizationCreator(MigrationForm form) {
		this.form = form;
		this.sqlparser = new CodeProcessor();
	}

	public void create() {

		String applicationName = Settings.getSettings().getApplicationName();
		try {

			// ValueBinding binding = app.createValueBinding("#{popup}");
			// Settings set = (Settings)binding.getValue(context);
			Settings settings = Settings.getSettings();

			File customFile1 = new File(settings.getBaseDir()
					+ File.separator
					+ "ViewController"
					+ File.separator
					+ "src"
					+ File.separator
					+ applicationName
					+ File.separator
					+ form.getFormName().substring(0,
							form.getFormName().indexOf('.'))
					+ File.separator
					+ form.getFormName().substring(0,
							form.getFormName().indexOf('.'))
					+ "_customization.xml");
			if (customFile1.exists() || form.isCustomizationExist()) {
				return;
			} else {
				Module module = form.getModule();
				FormModule formModule = module.getFormModule();
				List<FormsObject> children = formModule.getChildren();
				StringBuffer xmlOutput = new StringBuffer();
				xmlOutput.append("<R3>");
				xmlOutput.append("\n");
				for (FormsObject formsObject : children) {
					if (formsObject instanceof Block) {
						Block block = (Block) formsObject;
						List<FormsObject> blockChildren = block.getChildren();
						for (FormsObject blockObject : blockChildren) {
							if (blockObject instanceof Trigger) {
								Trigger trigger = (Trigger) blockObject;
								System.out
										.println("trigger.getParentModule():::: "
												+ trigger.getParentModule());
								String triggerOutput = getTriggerTag(trigger,
										block.getName(), null);
								if (triggerOutput != null
										&& triggerOutput.length() > 0) {
									xmlOutput.append(triggerOutput);
								}

							} else if (blockObject instanceof Item) {
								Item item = (Item) blockObject;
								System.out
										.println("trigger.getParentModule():::: "
												+ item.getParentModule());
								List<FormsObject> itemChildren = item
										.getChildren();
								for (FormsObject itemObject : itemChildren) {
									if (itemObject instanceof Trigger) {
										Trigger trigger = (Trigger) itemObject;
										System.out
												.println("trigger.getParentModule():::: "
														+ trigger
																.getParentModule());
										String triggerOutput = getTriggerTag(
												trigger, block.getName(),
												item.getName());
										if (triggerOutput != null
												&& triggerOutput.length() > 0) {
											xmlOutput.append(triggerOutput);
										}
									}
								}

							}
						}
					}
					if (formsObject instanceof Trigger) {
						Trigger trigger = (Trigger) formsObject;
						System.out.println("trigger.getParentModule():::: "
								+ trigger.getParentModule());
						String triggerOutput = getTriggerTag(trigger, null,
								null);
						if (triggerOutput != null && triggerOutput.length() > 0) {
							xmlOutput.append(triggerOutput);
						}

					} else if (formsObject instanceof RecordGroup) {
						RecordGroup rg = (RecordGroup) formsObject;
						LovXmlReaderWriter lovXmlRW = new LovXmlReaderWriter();
						String lovXmlFilePath = Settings.getSettings()
								.getFmbRootDir()
								+ File.separator
								+ form.getFormName().replace(".", "_")
								+ "_LOV.xml";
						String rgName = rg.getName();
						String tableName = lovXmlRW.getTableName(
								lovXmlFilePath, rgName);
						if (tableName == null) {
							tableName = "na";
						}
						boolean recOrderBy = false;
						String rgStartTag = "<RecordGroup Name=\"" + rgName
								+ "\" Table=\"" + tableName
								+ "\"  ComponentOrderBy=\"" + recOrderBy
								+ "\">";
						xmlOutput.append(rgStartTag);
						xmlOutput.append("\n");
						String rgQuery = rg.getRecordGroupQuery();

						if (rgQuery != null && rgQuery.length() > 0) {
							ProcessedCode code = sqlparser.process(rgQuery);
							Vector<Variable> variables = code.getVariables();
							for (Variable variable : variables) {
								String blockName = variable.getBlock();
								String itemName = variable.getItem();
								if (blockName == null || itemName != null) {
									blockName = findBlock(itemName);
								}
								String paramterTag = "<Parameter Block=\""
										+ blockName + "\" Item=\"" + itemName
										+ "\" Type=\"" + variable.getType()
										+ "\" Name=\""
										+ variable.getReplacedName() + "\" />";
								xmlOutput.append(paramterTag);
								xmlOutput.append("\n");
								if (blockName != null
										&& !blockName
												.equalsIgnoreCase("GLOBAL")) {
									String paramterTag2 = "<Parameter Block=\""
											+ blockName + "\" Item=\""
											+ itemName + "\" Type=\"depend\"/>";
									xmlOutput.append(paramterTag2);
									xmlOutput.append("\n");
								}
							}
						}
						String rgEndTag = "</RecordGroup>";
						xmlOutput.append(rgEndTag);
						xmlOutput.append("\n");
					}
				}
				xmlOutput.append("</R3>");
				try {
					// Create file
					customFile1.createNewFile();
					FileWriter fstream = new FileWriter(customFile1);
					BufferedWriter out = new BufferedWriter(fstream);
					out.write(xmlOutput.toString());
					// Close the output stream
					out.close();
				} catch (Exception e) {// Catch exception if any
					System.err.println("Error: " + e.getMessage());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getTriggerTag(Trigger trigger, String parentBlockName,
			String parentItemName) {
		StringBuffer triggerOutput = new StringBuffer();
		String triggerName = trigger.getName();
		String triggerText = trigger.getTriggerText();
		if (parentBlockName == null) {
			parentBlockName = "na";
		}
		if (parentItemName == null) {
			parentItemName = "na";
		}
		if (triggerName == "POST-QUERY"
				|| triggerName.equalsIgnoreCase("POST-QUERY")) {

			String triggerTag = "<Trigger Block=\""
					+ parentBlockName
					+ "\" Jsfattr=\"postQuery\" MethodType=\"plsqlCall\" plsql=\"postQuery_"
					+ parentBlockName + "\">";
			triggerOutput.append(triggerTag);
			triggerOutput.append("\n");
			triggerOutput.append(getParameterTag(triggerText));
			triggerOutput.append("</Trigger>");
			triggerOutput.append("\n");
		} else if (triggerName == "PRE-INSERT"
				|| triggerName.equalsIgnoreCase("PRE-INSERT")) {
			String triggerTag = "<Trigger Block=\""
					+ parentBlockName
					+ "\" Jsfattr=\"preInsert\" MethodType=\"objectSetter\" plsql=\"preInsert_"
					+ parentBlockName + "\">";
			triggerOutput.append(triggerTag);
			triggerOutput.append("\n");
			triggerOutput.append(getParameterTag(triggerText));
			triggerOutput.append("</Trigger>");
			triggerOutput.append("\n");

		} else if (triggerName == "PRE-UPDATE"
				|| triggerName.equalsIgnoreCase("PRE-UPDATE")) {
			String triggerTag = "<Trigger Block=\""
					+ parentBlockName
					+ "\" Jsfattr=\"preUpdate\" MethodType=\"objectSetter\" plsql=\"preUpdate_"
					+ parentBlockName + "\">";
			triggerOutput.append(triggerTag);
			triggerOutput.append("\n");
			triggerOutput.append(getParameterTag(triggerText));
			triggerOutput.append("</Trigger>");
			triggerOutput.append("\n");

		}
		return triggerOutput.toString();
	}

	private String getParameterTag(String triggerText) {
		StringBuffer triggerOutput = new StringBuffer("");
		if (triggerText != null && triggerText.length() > 0) {
			if (triggerText.contains("PROCEDURE")) {
				triggerText = triggerText.substring(
						triggerText.indexOf("PROCEDURE"),
						triggerText.indexOf(") IS"));
			}
			ProcessedCode code = sqlparser.process(triggerText);
			Vector<Variable> variables = code.getVariables();
			for (Variable variable : variables) {
				String blockName = variable.getBlock();
				String itemName = variable.getItem();
				if (blockName == null || itemName != null) {
					blockName = findBlock(itemName);
				}
				String paramterTag = "<Parameter Block=\"" + blockName
						+ "\" Item=\"" + itemName + "\" Type=\""
						+ variable.getType() + "\" Name=\""
						+ variable.getReplacedName() + "\" />";
				triggerOutput.append(paramterTag);
				triggerOutput.append("\n");

			}
		}

		return triggerOutput.toString();
	}

	private String findBlock(String itemName) {
		Module module = form.getModule();
		FormModule formModule = module.getFormModule();
		List<FormsObject> children = formModule.getChildren();
		for (FormsObject formsObject : children) {
			if (formsObject instanceof Block) {
				Block block = (Block) formsObject;
				List<FormsObject> blockChildren = block.getChildren();
				for (FormsObject blockObject : blockChildren) {
					if (blockObject instanceof Item) {
						Item item = (Item) blockObject;
						if (item.getName() == itemName
								|| item.getName().equalsIgnoreCase(itemName)) {
							return block.getName();
						}
					}
				}
			}
		}
		return null;
	}
}
