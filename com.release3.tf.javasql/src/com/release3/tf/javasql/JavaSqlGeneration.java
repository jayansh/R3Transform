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
package com.release3.tf.javasql;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.xml.bind.JAXBException;

import org.eclipse.jface.preference.IPreferenceStore;

import com.converter.toolkit.ui.FormsObject;
import com.converter.toolkit.ui.JAXBXMLLoader;
import com.converter.toolkit.ui.JAXBXMLWriter;
import com.oracle.xmlns.forms.Block;
import com.oracle.xmlns.forms.FormModule;
import com.oracle.xmlns.forms.Item;
import com.oracle.xmlns.forms.Module;
import com.oracle.xmlns.forms.Trigger;
import com.release3.javasql.JavaPlSqlType;
import com.release3.javasql.R3JavaSqlGen;
import com.release3.javasql.R3JavaSqlGenFactory;
import com.release3.customization.builtin.BuiltinType;
import com.release3.plsqlgen.R3PlSql;
import com.release3.plsqlgen.R3PlSql.R3Trigger;
import com.release3.tf.javagen.PlSqlParser;
import com.release3.transform.constants.UIConstants;
import com.release3.transform.pref.PreferenceConstants;
import com.release3.transform.pref.PreferencePlugin;

public class JavaSqlGeneration {
	PlSqlParser parser = new PlSqlParser();

	Properties props = new Properties();
	private boolean migrateCrudToJava;
	private Module originalModule;

	public JavaSqlGeneration(Module originalModule) {
		this.originalModule = originalModule;
		IPreferenceStore store = PreferencePlugin.getDefault()
				.getPreferenceStore();
		migrateCrudToJava = store
				.getBoolean(PreferenceConstants.APPLICATION_MIGRATE_CRUD_TO_JAVA);
	}

	public void generateJavaSql(String destXmlPath, R3PlSql plsqlModule)
			throws IOException {
		List<R3Trigger> r3TriggerList = plsqlModule.getR3Trigger();
		R3JavaSqlGenFactory javaSqlGenfactory = new R3JavaSqlGenFactory();
		R3JavaSqlGen javaSqlGen = javaSqlGenfactory.createR3JavaSqlGen();
		javaSqlGen.setName(plsqlModule.getFormName());
		for (R3Trigger r3Trigger : r3TriggerList) {
			Trigger trigger = r3Trigger.getTrigger();
			if (isListedTrigger(trigger.getName())) {
				JavaPlSqlType javaPlSql = javaSqlGenfactory.createJavaPlSqlType();
				javaPlSql.setJavaPlSql(r3Trigger.getName());
				javaPlSql.setBlockName(r3Trigger.getBlockName());
				javaPlSql.setItemName(r3Trigger.getItemName());
				javaPlSql.setToBeMigrate(true);
				String text = trigger.getTriggerText();
				text = text.replace("&#10;", "\n");
				if (text != null) {
					if (contains(text, "SET_ITEM_PROPERTY")) {
						BuiltinType builtin = new BuiltinType();
						String newText = migrateItemProperty(text, builtin);
						javaPlSql.setJavaPlSqlText(newText);
						// trigger.setTriggerText(newText);
						// int index = text.indexOf("set_item_property(");
						// if (index > 0) {
						// text = text.substring(index);
						// int endText = text.indexOf(");");
						// String setItemPropertyText = text
						// .substring(18, endText);
						// String[] propertyArray =
						// setItemPropertyText.split(",");
						// if (propertyArray.length > 2) {
						// String itemName = propertyArray[0];
						// String propertyName = propertyArray[1];
						// String propertyValue = propertyArray[2];
						// }
						// }

						// builtin.setBlockName(r3Trigger.getBlockName());
						// builtin.setBuiltinName("set_item_property");
						// builtin.setFormName(plsqlModule.getFormName());
						// builtin.setItemName(r3Trigger.getItemName());
						// builtin.setPropertyName("Disabled");
						// builtin.setPropertyValue("true");
						javaPlSql.getBuiltin().add(builtin);

					} else {
						javaPlSql.setJavaPlSqlText(text);

					}
					// String plsqlText = javaPlSql.getJavaPlSqlText();
					// plsqlText = parser.migrateToJava(text,
					// javaSqlGen.getName(), null);
					// javaPlSql.setJavaPlSqlText(plsqlText);
					javaSqlGen.getJavaSql().add(javaPlSql);
				}
			}
		}
		try {
			JAXBXMLWriter.writetoXML(destXmlPath, javaSqlGen);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// try {
		// JavaSqlModelGen javaGen = new JavaSqlModelGen();
		// javaGen.generateJavaClass(javaSqlGen);
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

	}

	public String migrateItemProperty(String text, BuiltinType builtin) {
		String compiledText = "";
		if (text != null) {
			// text = text.toLowerCase();
			if (contains(text, "SET_ITEM_PROPERTY")) {
				int todoIndex = indexOfIfIgnoreCase(text,
						"/* TODO set_item_property");
				int index = indexOfIfIgnoreCase(text, "set_item_property");

				if (todoIndex == -1 && index > 0) {
					compiledText = text.substring(0, index);
					String newText = text.substring(index);
					newText = newText.replaceFirst("set_item_property",
							"/* TODO set_item_property");
					int endIndex = newText.indexOf(");");
					if (endIndex > -1) {
						newText = changeToQualifiedItemName(newText, builtin);
						compiledText = compiledText
								+ newText.substring(0, endIndex + 2) + "*/";
						newText = newText.substring(endIndex + 2);
					} else {
						compiledText = compiledText + "*/";
					}
					System.out.println("Compiled Text::: " + compiledText);

				}
			} else if (text.length() > 0) {
				compiledText = compiledText + text;
			}
		}

		System.out.println("Compiled Text::: " + compiledText);
		return compiledText;
	}

	public String changeToQualifiedItemName(String text, BuiltinType builtin) {
		int index = indexOfIfIgnoreCase(text, "set_item_property(");
		if (index > 0) {
			String setItemPropertyText = text.substring(index + 18,
					text.indexOf(")"));
			String[] propertyArray = setItemPropertyText.split(",");
			if (propertyArray.length > 2) {
				String itemName = propertyArray[0];
				String propertyName = props.getProperty(propertyArray[1]
						.toUpperCase());

				String propertyValue = props.getProperty(propertyArray[2]
						.toUpperCase());
				if (propertyName == "disabled") {
					if (propertyValue == "false") {
						propertyValue = "true";
					} else if (propertyValue == "true") {
						propertyValue = "false";
					}
				}
				builtin.setPropertyName(propertyName);
				builtin.setPropertyValue(propertyValue);
				String blockName = null;
				System.out.println("itemName::: " + itemName
						+ "\npropertyName:::" + propertyName
						+ "\npropertyValue" + propertyValue);
				if (!(itemName.indexOf(".") > -1)) {
					String newItemName = itemName.replace('"', ' ')
							.toUpperCase().trim();
					blockName = findBlock(newItemName);
					if (blockName != null && blockName.length() > 0) {
						newItemName = blockName + "." + newItemName;
						itemName = newItemName;
					}
					builtin.setBlockName(blockName);
					builtin.setItemName(itemName);
				}

			}

		}

		return text;
	}

	private String findBlock(String itemName) {

		FormModule formModule = originalModule.getFormModule();
		List<FormsObject> children = formModule.getChildren();
		for (FormsObject formsObject : children) {
			if (formsObject instanceof Block) {
				Block block = (Block) formsObject;
				List<FormsObject> blockChildren = block.getChildren();
				for (FormsObject blockObject : blockChildren) {
					if (blockObject instanceof Item) {
						Item item = (Item) blockObject;
						System.out.println("ItemName::: " + item.getName());
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

	public boolean isListedTrigger(String triggerName) {

		if (migrateCrudToJava) {
			return false;
		}
		// for (String trigName : UIConstants.TRIGGER_PLSQL) {
		// if (trigName == triggerName
		// || trigName.equalsIgnoreCase(triggerName)) {
		// return true;
		// }
		// }
		for (com.release3.triggerlist.SupportedTriggerType triggerType : JAXBXMLLoader
				.getInstance().getTriggerList().getSupportedTrigger()) {
			String trigName = triggerType.getName();
			if (trigName == triggerName
					|| trigName.equalsIgnoreCase(triggerName)) {
				return triggerType.isMigrate();
			}
		}
		return false;
	}

	public boolean contains(String text, String charSq) {
		if (text == null) {
			return false;
		}
		if (text.toUpperCase().contains(charSq.toUpperCase())) {
			return true;
		}
		return false;

	}

	public int indexOfIfIgnoreCase(String text, String charSq) {
		if (text == null) {
			return -1;
		}

		return text.toUpperCase().indexOf(charSq.toUpperCase());

	}
}
