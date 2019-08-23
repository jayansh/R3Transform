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
package com.release3.plsql.analysis;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import javax.xml.bind.JAXBException;

import com.converter.toolkit.ui.FormsObject;
import com.converter.toolkit.ui.JAXBXMLWriter;
import com.oracle.xmlns.forms.Block;
import com.oracle.xmlns.forms.FormModule;
import com.oracle.xmlns.forms.Item;
import com.oracle.xmlns.forms.Module;
import com.release3.plsql.analysis.builtins.model.BuiltinProperties;
import com.release3.plsql.analysis.builtins.model.BuiltinProperties.Builtin;
import com.release3.plsql.analysis.builtins.model.ObjectFactory;
import com.release3.plsql.analysis.property.model.PropertiesModelProvider;
import com.release3.tf.core.form.Settings;

public class BuiltinProcessor {

	BuiltinProperties builtinProperties = new BuiltinProperties();
	Properties props = new Properties();

	// public void process(String text) {
	// if (text != null) {
	//
	// if (text.contains("/* TODO set_item_property")) {
	// int index = text.indexOf("set_item_property(");
	// if (index > 0) {
	// text = text.substring(index);
	// int endText = text.indexOf(");*/");
	// String setItemPropertyText = text.substring(18, endText);
	// String[] propertyArray = setItemPropertyText.split(",");
	// if (propertyArray.length > 2) {
	// String itemName = propertyArray[0];
	// String propertyName = propertyArray[1];
	// String propertyValue = propertyArray[2];
	// BuiltinProperties properties = new BuiltinProperties();
	// properties.setBuiltinName(itemName);
	// properties.setPropertyName(propertyName);
	// properties.setPropertyValue(propertyValue);
	// System.out.println("itemName::: " + itemName
	// + "\npropertyName:::" + propertyName
	// + "\npropertyValue" + propertyValue);
	// }
	// text = text.substring(endText + 4);
	// process(text);
	// }
	//
	// }
	// }
	//
	// }

	public BuiltinProcessor() {
		loadMapper();
	}

	public String removePropertyFromElse(String text) {
		String compiledText = "";
		if (text != null) {
			// text = text.toLowerCase();
			if (text.contains("}else{")) {
				int elseIndex = text.indexOf("}else{");
				elseIndex = elseIndex + 6;
				compiledText = text.substring(elseIndex);
				int endElseIndex = compiledText.indexOf("}");

				if (endElseIndex > -1) {
					compiledText = compiledText.substring(0, endElseIndex);
					if (compiledText.contains("set_item_property")) {
						int index = compiledText.indexOf("set_item_property");
						int endIndex = compiledText.indexOf(");");
						if (index > -1 && endIndex > index) {
							StringBuffer strBuffer = new StringBuffer(text);
							strBuffer.replace(elseIndex, elseIndex
									+ endElseIndex, "return\nfalse;");
							text = strBuffer.toString();
							text = removePropertyFromElse(text);
						}
					}

				}

			}

		}
		return text;
	}

	public String migrateItemProperty(String text, String containingMethod) {
		String compiledText = "";
		if (text != null) {
			// text = text.toLowerCase();
			if (text.contains("set_item_property")) {
				int todoIndex = text.indexOf("/* TODO set_item_property");
				int index = text.indexOf("set_item_property");

				if (todoIndex == -1 && index > 0) {
					compiledText = text.substring(0, index);
					String newText = text.substring(index);
					newText = newText.replaceFirst("set_item_property",
							"/* TODO set_item_property");
					int endIndex = newText.indexOf(");");
					if (endIndex > -1) {
						newText = changeToQualifiedItemName(newText,
								containingMethod);
						compiledText = compiledText
								+ newText.substring(0, endIndex + 2) + "*/";
						newText = newText.substring(endIndex + 2);
					} else {
						compiledText = compiledText + "*/";
					}
					System.out.println("Compiled Text::: " + compiledText);
					if (newText.length() > 0) {
						compiledText = compiledText
								+ migrateItemProperty(newText, containingMethod);
					}

				}
			} else if (text.length() > 0) {
				compiledText = compiledText + text;
			}
		}

		System.out.println("Compiled Text::: " + compiledText);
		return compiledText;
	}

	public String changeToQualifiedItemName(String text, String containingMethod) {
		int index = text.indexOf("set_item_property(");
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
				}
				ObjectFactory buitinFactory = new ObjectFactory();
				Builtin properties = buitinFactory
						.createBuiltinPropertiesBuiltin();
				properties.setBuiltinName(itemName);
				properties.setPropertyName(propertyName);
				properties.setFormName(PropertiesModelProvider
						.getInstance()
						.getCurrentForm()
						.getFormName()
						.substring(
								0,
								PropertiesModelProvider.getInstance()
										.getCurrentForm().getFormName()
										.indexOf(".")));
				properties.setPropertyValue(propertyValue);
				properties.setMethod(containingMethod);
				properties.setBuiltinExpression(propertyValue + "==Bindings"
						+ properties.getFormName() + "." + containingMethod
						);
				addBuiltinToList(properties);
			}

		}

		return text;
	}

	private void addBuiltinToList(Builtin inputBuiltin) {
		if (!builtinLookup(inputBuiltin)) {
			builtinProperties.getBuiltin().add(inputBuiltin);
		}

	}

	private boolean builtinLookup(Builtin inputBuiltin) {
		if (inputBuiltin == null) {
			return false;
		}
		for (Builtin builtin : builtinProperties.getBuiltin()) {
			if (inputBuiltin.getBuiltinName() == null
					|| inputBuiltin.getPropertyName() == null
					|| builtin.getBuiltinName() == null
					|| builtin.getPropertyName() == null) {
				return false;
			}
			if (inputBuiltin.getBuiltinName() == builtin.getBuiltinName()
					|| inputBuiltin.getBuiltinName().equals(
							builtin.getBuiltinName())) {
				if (inputBuiltin.getPropertyName() == builtin.getPropertyName()
						|| inputBuiltin.getPropertyName().equals(
								builtin.getPropertyName())) {
					builtin.setPropertyValue(null);
					builtin.setBuiltinExpression(builtin.getBuiltinExpression()
							+ " or " + inputBuiltin.getBuiltinExpression());
					return true;
				}
			}
		}
		return false;

	}

	// public void migrate(String text) {
	// if (text != null) {
	// text = text.toLowerCase();
	// if (text.contains("set_item_property")) {
	// int index = text.indexOf("set_item_property(");
	// if (index > 0) {
	// String setItemPropertyText = text.substring(index + 18,
	// text.indexOf(")"));
	// String[] propertyArray = setItemPropertyText.split(",");
	// if (propertyArray.length > 2) {
	// String itemName = propertyArray[0];
	// String blockName = null;
	// if (itemName.indexOf(".") > -1) {
	// String[] strArray = itemName.split(".");
	// blockName = strArray[0];
	// itemName = strArray[1];
	//
	// } else {
	// blockName = findBlock(itemName);
	// }
	// String propertyName = propertyArray[1];
	// String propertyValue = propertyArray[2];
	// System.out.println("itemName::: " + itemName
	// + "\npropertyName:::" + propertyName
	// + "\npropertyValue" + propertyValue);
	// }
	//
	// }
	// }
	// }
	//
	// }

	public String migrateToJava(String text, String containingMethod) {
		// StringBuffer migratedJavaCode = new StringBuffer("");
		if (text != null) {
			text = text.toLowerCase();

			// if (text.contains("if") && text.contains("then")) {

			text = text.replace("end if;", "}");
			text = text.replace("if", "if(");
			text = text.replace("then", "){ \nreturn\ntrue; ");
			text = text.replace("else", "} else { \nreturn\nfalse; ");
			text = text.replace("&amp;#10", "\n");
			text = text.replace("&#10;", "\n");
			text = text.replace("'", "\"");
			text = text.replace(" ", "");
			text = text.replace("-", "_");
			int ifIndex = text.indexOf("if(");
			int thenIndex = text.indexOf(")");
			if (ifIndex > -1 && thenIndex > -1) {
				String condition = text.substring(ifIndex + 3, thenIndex);

				String javaCondition = condition.toUpperCase();
				String[] conditionArr = javaCondition.split("=");
				String conditionStr1 = conditionArr[0].replace(":", "");
				String formName = PropertiesModelProvider.getInstance()
						.getCurrentForm().getFormName().toUpperCase();
				formName = formName.replace(".FMB", "");
				String blockName = null;
				String itemName = null;
				if (conditionStr1.indexOf(".") > -1) {
					String[] strArray = conditionStr1.split(".");
					blockName = strArray[0];
					itemName = strArray[1];

				} else {
					itemName = conditionStr1;
					blockName = findBlock(itemName);
				}
				// String conditionPrefix = formName + "_" + blockName +
				// "Intfs "
				// + formName + blockName + " = (" + formName + "_"
				// + blockName + "Intfs) get" + formName + "_" + blockName
				// + "().getCurrentRow();";
				javaCondition = formName + blockName + ".get" + itemName
						+ "()== null || " + formName + blockName + ".get"
						+ itemName + "() == " + conditionArr[1] + " || "
						+ formName + blockName + ".get" + itemName
						+ "().equalsIgnoreCase(" + conditionArr[1] + ")";

				// javaCondition = javaCondition.replace(":", "get");
				// javaCondition = javaCondition.replace("=", "==");
				text = text.replace(condition, javaCondition);
				text = removePropertyFromElse(text);
				text = migrateItemProperty(text, containingMethod);
				// text = conditionPrefix + "\n" + text;

			}

			// }
			System.out.println("Text::: " + text);
		}
		save();
		return text;
	}

	public void save() {
		saveBuiltinTriggers();
		saveBuiltinProperties();
	}

	public void saveBuiltinTriggers() {
		String xmlPath = PropertiesModelProvider.getInstance().getCurrentForm()
				.getPlsqlAnalysisFile().getAbsolutePath()
				.replace("fmb_plsql.xml", "builtinTriggers.xml");
		Module module = PropertiesModelProvider.getInstance()
				.getCurrentModule();
		try {
			JAXBXMLWriter.writetoXML(xmlPath, module);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void saveBuiltinProperties() {
		String xmlPath = PropertiesModelProvider.getInstance().getCurrentForm()
				.getPlsqlAnalysisFile().getAbsolutePath()
				.replace("fmb_plsql.xml", "builtinProperties.xml");
		;
		try {
			JAXBXMLWriter.writetoXML(xmlPath, builtinProperties);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String findBlock(String itemName) {
		if (PropertiesModelProvider.getInstance().getCurrentForm()
				.getFmbXmlPath() == null
				|| PropertiesModelProvider.getInstance().getCurrentForm()
						.getFmbXmlPath().length() == 0) {
			PropertiesModelProvider.getInstance().getCurrentForm()
					.setFmbXmlPath(getFmbXmlFile());
		}
		Module module = PropertiesModelProvider.getInstance().getCurrentForm()
				.getModule();
		FormModule formModule = module.getFormModule();
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

	private String getFmbXmlFile() {
		String dataControlOutputDir = Settings.getSettings().getBaseDir()
				+ File.separator + "ViewController" + File.separator + "src"
				+ File.separator + Settings.getSettings().getApplicationName();
		String formName = PropertiesModelProvider.getInstance()
				.getCurrentForm().getFormName();
		formName = formName.substring(0, formName.indexOf("."));
		String fmbXmlPath = dataControlOutputDir + File.separator + formName
				+ File.separator + formName + ".xml";
		return fmbXmlPath;
	}

	private void loadMapper() {
		InputStream stream = getClass().getResourceAsStream(
				"builtinMapper.properties");
		try {
			props.load(stream);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		BuiltinProcessor processor = new BuiltinProcessor();
		String text = "if(SET_ITEMEMP.getJOB()== null || SET_ITEMEMP.getJOB() == &quot;SALESMAN&quot; || SET_ITEMEMP.getJOB().equalsIgnoreCase(&quot;SALESMAN&quot;)){		return		true;		/* TODO set_item_property(&quot;comm&quot;,visible,property_true);*/		/* TODO set_item_property(&quot;comm&quot;,enabled,property_false);*/		}else{		return		false;		/* TODO set_item_property(&quot;comm&quot;,visible,property_false);*/		}";
		System.out.println("Text Before:::: " + text);
		text = processor.removePropertyFromElse(text);
		System.out.println("Text after:::: " + text);
		// processor.process(text);
	}
}
