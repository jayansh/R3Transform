package com.release3.plsql.analysis;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.xml.bind.JAXBException;

import org.eclipse.jface.preference.IPreferenceStore;

import com.converter.toolkit.sqlparsing.CodeProcessor;
import com.converter.toolkit.ui.FormsObject;
import com.converter.toolkit.ui.JAXBXMLReader;
import com.converter.toolkit.ui.JAXBXMLWriter;
import com.oracle.xmlns.forms.Block;
import com.oracle.xmlns.forms.FormModule;
import com.oracle.xmlns.forms.Item;
import com.oracle.xmlns.forms.Module;
import com.oracle.xmlns.forms.Trigger;
import com.release3.customization.R3;
import com.release3.customization.builtin.BuiltinType;
import com.release3.customization.parameter.ParameterType;
import com.release3.plsqlgen.R3PlSql;
import com.release3.plsqlgen.R3PlSql.R3Trigger;
import com.release3.r3coreplsqlgen.R3CorePlSql;
import com.release3.r3coreplsqlgen.R3CorePlSqlGen;
import com.release3.r3coreplsqlgen.R3CorePlSqlGenFactory;
import com.release3.transform.constants.UIConstants;
import com.release3.transform.pref.PreferenceConstants;
import com.release3.transform.pref.PreferencePlugin;

public class R3CorePlSqlXmlGeneration {
	private boolean migrateCrudToJava = false;
	Properties props = new Properties();
	private Module originalModule;
	private CodeProcessor sqlparser;
	private String[] BUILTINS_ARRAY_START = { "set_item_property",
			"show_alert", "execute_query" };
	private String[] BUILTINS_ARRAY_END = { ");", ");", ";" };
	private String[] messageArray = { "MESSAGE", "MESSAGE_TYPE", "ERROR_FLAG" };
	private JAXBXMLReader jaxbXmlReader = new JAXBXMLReader();
	private File destParentFile;
	private R3PlSql plsqlModule;
	private R3 preCustomization;

	public R3CorePlSqlXmlGeneration(Module originalModule, File destParentFile,
			R3PlSql plsqlModule, R3 preCustomization) {
		this.originalModule = originalModule;
		this.sqlparser = new CodeProcessor();
		this.destParentFile = destParentFile;
		this.plsqlModule = plsqlModule;
		this.preCustomization = preCustomization;
		IPreferenceStore store = PreferencePlugin.getDefault()
				.getPreferenceStore();
		migrateCrudToJava = store
				.getBoolean(PreferenceConstants.APPLICATION_MIGRATE_CRUD_TO_JAVA);

	}

	public void generatePlSql() throws IOException {

		String r3CorePlSqlXmlPath = destParentFile + File.separator
				+ plsqlModule.getFormName() + "_R3CorePlSql.xml";
		String r3PreCustomizationXmlPath = destParentFile + File.separator
				+ plsqlModule.getFormName() + "_R3PreCustomization.xml";
		List<R3Trigger> r3TriggerList = plsqlModule.getR3Trigger();
		R3CorePlSqlGenFactory plSqlGenfactory = new R3CorePlSqlGenFactory();
		R3CorePlSqlGen plSqlGen = plSqlGenfactory.createR3CorePlSqlGen();

		plSqlGen.setName(plsqlModule.getFormName());
		for (R3Trigger r3Trigger : r3TriggerList) {
			Trigger trigger = r3Trigger.getTrigger();
			if (isCrudTrigger(trigger.getName())) {
				// String text = trigger.getTriggerText().toLowerCase();
				String text = trigger.getTriggerText();
				if (text != null) {
					R3CorePlSql corePlSql = plSqlGenfactory.createR3CorePlSql();
					corePlSql.setPlSqlName(r3Trigger.getName());

					corePlSql.setBlockName(r3Trigger.getBlockName());
					corePlSql.setItemName(r3Trigger.getItemName());
					R3.Trigger customTrigger = getCustomizationTriggerTag(
							trigger, r3Trigger.getBlockName(),
							r3Trigger.getItemName());
					if (isContainsBuiltin(text)) {

						String newText = migrateItemProperty(text, corePlSql,
								customTrigger);
						// trigger.setTriggerText(newText);
						corePlSql.setPlSqlText(newText);

						// plSqlGen.getR3CorePlSql().add(corePlSql);
					} else {
						String definitionText = text.substring(
								indexOfIfIgnoreCase(text, "procedure"),
								text.indexOf(")"));
						List<ParameterType> outMessageParamList = new ArrayList<ParameterType>();
						// String newDefinitionText =
						// getDefinitionWithMessageOut(
						// definitionText, outMessageParamList);
						// text = text.replace(definitionText,
						// newDefinitionText);
						// customTrigger.getParameter()
						// .addAll(outMessageParamList);
						// corePlSql.setPlSqlDefinition(newDefinitionText +
						// ");");
						corePlSql.setPlSqlDefinition(definitionText + ");");
						corePlSql.setPlSqlText(text);
					}
					plSqlGen.getR3CorePlSql().add(corePlSql);
					if (!(customTrigger.getPlsql() == null
							&& customTrigger.getJsfattr() == null
							&& customTrigger.getMethodType() == null
							&& customTrigger.getBlock() == null && customTrigger
							.getItem() == null)) {
						preCustomization.getTrigger().add(customTrigger);
					}
				}
			}
		}
		try {
			JAXBXMLWriter.writetoXML(r3CorePlSqlXmlPath, plSqlGen);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			JAXBXMLWriter.writetoXML(r3PreCustomizationXmlPath,
					preCustomization);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String migrateItemProperty(String text, R3CorePlSql corePlSql,
			R3.Trigger customTrigger) {
		HashMap<String, BuiltinType> builtinsMap = new HashMap<String, BuiltinType>();
		int setItemCount = 0;
		List<String> outParameters = new ArrayList<String>();
		List<ParameterType> outParamTag = new ArrayList<ParameterType>();
		String compiledText = "";
		for (int i = 0; i < BUILTINS_ARRAY_START.length; i++) {
			if (i > 0) {
				text = compiledText;
				compiledText = "";
			}
			if (text != null) {

				// text = text.toLowerCase();
				while (contains(text, BUILTINS_ARRAY_START[i])) {
					int todoIndex = indexOfIfIgnoreCase(text, "/* TODO "
							+ BUILTINS_ARRAY_START[i]);
					int index = indexOfIfIgnoreCase(text,
							BUILTINS_ARRAY_START[i]);

					if (todoIndex == -1 && index > 0) {
						if (compiledText.length() == 0) {
							compiledText = text.substring(0, index);
						} else {
							compiledText = compiledText
									+ text.substring(0, index);
						}

						String newText = text.substring(index);
						if (i == 1) {
							newText = newText.replaceFirst(
									BUILTINS_ARRAY_START[i], "null; /* TODO "
											+ BUILTINS_ARRAY_START[i]);
						} else {
							newText = newText.replaceFirst(
									BUILTINS_ARRAY_START[i], "/* TODO "
											+ BUILTINS_ARRAY_START[i]);
						}
						int endIndex = indexOfIfIgnoreCase(newText,
								BUILTINS_ARRAY_END[i]);
						if (endIndex > -1) {
							String setItemVariable = "";
							// only for set_item_property Trigger
							if (i == 0) {
								// ParameterType paramType = new
								// ParameterType();
								BuiltinType builtinType = new BuiltinType();
								// setBuiltinProperties(newText, builtinType,
								// paramType);
								setBuiltinProperties(newText, builtinType);
								builtinsMap.put(builtinType.getBuiltinName(),
										builtinType);
								setItemVariable = newText.substring(8,
										endIndex + 2);
								setItemVariable = setItemVariable.replace("'",
										"");
								String outParam = "setItemPropertyVar"
										+ setItemCount;
								setItemVariable = outParam + " := '"
										+ setItemVariable + "';\n";
								outParameters.add(outParam);
								builtinType.setType("set_item_property");
								// paramType.setName(outParam);

								customTrigger.getBuiltin().add(builtinType);
								// customTrigger.getParameter().add(paramType);
								setItemCount++;

							}
							compiledText = compiledText + setItemVariable
									+ newText.substring(0, endIndex + 2)
									+ "*/ /*Commented by TF5*/\n";
							text = newText.substring(endIndex + 2);
						} else {
							compiledText = compiledText
									+ "*/ /*Commented by TF5*/\n";
						}
						System.out.println("Compiled Text::: " + compiledText);

					}
				}
				// if (text.length() > 0) {
				compiledText = compiledText + text;
				// }
			}

			corePlSql.getBuiltin().addAll(builtinsMap.values());

		}

		String definitionText = "";
		String oldDefText = "";
		if (contains(compiledText, "procedure")) {
			definitionText = compiledText.substring(
					indexOfIfIgnoreCase(compiledText, "procedure"),
					compiledText.indexOf(")"));
			oldDefText = definitionText;
			for (String string : outParameters) {
				definitionText = definitionText + ", " + string
						+ " out varchar2";
			}
			// definitionText = getDefinitionWithMessageOut(definitionText,
			// outParamTag);
			compiledText = compiledText.replace(oldDefText, definitionText);

		}
		customTrigger.getParameter().addAll(outParamTag);
		corePlSql.setPlSqlDefinition(definitionText + ");");
		// System.out.println("Compiled Text::: " + compiledText);
		return compiledText;
	}

	// public void setBuiltinProperties(String text,
	// BuiltinType builtin, ParameterType paramType) {
	public void setBuiltinProperties(String text, BuiltinType builtin) {
		int index = indexOfIfIgnoreCase(text, "set_item_property(");
		if (index > 0) {
			System.out.println("index:: " + index);
			System.out.println("text:: " + text);
			if (text.indexOf(")") > 18 + index) {
				String setItemPropertyText = text.substring(index + 18,
						text.indexOf(")"));
				String[] propertyArray = setItemPropertyText.split(",");
				if (propertyArray.length > 2) {
					String itemName = propertyArray[0].replace("'", "");
					// String propertyName = props.getProperty(propertyArray[1]
					// .toUpperCase());
					//
					// String propertyValue = props.getProperty(propertyArray[2]
					// .toUpperCase());
					String propertyName = propertyArray[1].toUpperCase()
							.replace("'", "");

					String propertyValue = propertyArray[2].toUpperCase()
							.replace("'", "");

					// if (propertyName == "disabled") {
					// if (propertyValue == "false") {
					// propertyValue = "true";
					// } else if (propertyValue == "true") {
					// propertyValue = "false";
					// }
					// }

					builtin.setPropertyName(propertyName);
					builtin.setPropertyValue(propertyValue);
					String blockName = null;
					System.out.println("itemName::: " + itemName
							+ "\npropertyName:::" + propertyName
							+ "\npropertyValue" + propertyValue);
					// paramType.setType("SetItemProperty_"+propertyName);
					if (!(itemName.indexOf(".") > -1)) {
						String newItemName = itemName.replace('"', ' ')
								.toUpperCase().trim();
						blockName = findBlock(newItemName).toUpperCase();
						if (blockName != null && blockName.length() > 0) {
							newItemName = blockName + "." + newItemName;
							itemName = newItemName.toUpperCase();
						}
						builtin.setBlockName(blockName);
						builtin.setItemName(itemName);
						// paramType.setBlock(blockName);
						// paramType.setItem(itemName);

					} else {
						blockName = itemName
								.substring(0, itemName.indexOf('.'))
								.toUpperCase();
						itemName = itemName
								.substring(itemName.indexOf('.') + 1)
								.toUpperCase();
						builtin.setBlockName(blockName);
						builtin.setItemName(itemName);
						// paramType.setBlock(blockName);
						// paramType.setItem(itemName);
					}
					String builtinName = "setItemProperty" + blockName + "."
							+ itemName + "." + propertyName + "."
							+ propertyValue;
					builtin.setBuiltinName(builtinName);
					// builtinsMap.put(builtinName, builtin);
				}
			}
		}

	}

	public String getJavaName(String name) {
		name = name.toLowerCase();
		name = name.replace(String.valueOf(name.charAt(0)),
				String.valueOf(name.charAt(0)).toUpperCase());
		return name;
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

	public boolean isCrudTrigger(String triggerName) {
		if (migrateCrudToJava) {
			// crud level triggers are disabled.
			return false;
		}
		for (String trigName : UIConstants.TRIGGER_PLSQL) {
			if (trigName == triggerName
					|| trigName.equalsIgnoreCase(triggerName)) {
				return true;
			}
		}
		return false;
	}

	public boolean isContainsBuiltin(String triggerText) {
		for (String builtin : BUILTINS_ARRAY_START) {
			if (contains(triggerText, builtin)) {
				return true;
			}
		}

		return false;

	}

	public R3.Trigger getCustomizationTriggerTag(Trigger trigger,
			String parentBlockName, String parentItemName) {

		String triggerName = trigger.getName();
		String triggerText = trigger.getTriggerText();
		R3.Trigger customTrigger = new R3.Trigger();
		if (parentBlockName == null) {
			parentBlockName = "na";
		} else {
			parentBlockName = parentBlockName.toUpperCase();
		}
		if (parentItemName == null) {
			parentItemName = "na";
		} else {
			parentItemName = parentItemName.toUpperCase();
		}
		/**
		 * POST-QUERY, PRE-QUERY triggers as ordered in
		 * UIConstants.TRIGGER_PLSQL
		 */
		switch (UIConstants.getPlSqlTriggerIndex(triggerName)) {
		/* POST-QUERY */
		case 0:
			customTrigger.setBlock(parentBlockName);
			customTrigger.setJsfattr("postQuery");
			customTrigger.setPlsql("postQuery_" + parentBlockName);
			// customTrigger.setMethodType("objectSetter");
			customTrigger.getParameter().addAll(
					getParameterTagList("postQuery_" + parentBlockName));
			setMethodType(customTrigger);
			break;
		/* POST-INSERT */
		case 1:
			customTrigger.setBlock(parentBlockName);
			customTrigger.setJsfattr("postInsert");
			customTrigger.setPlsql("postInsert_" + parentBlockName);
			// customTrigger.setMethodType("objectSetter");
			customTrigger.getParameter().addAll(
					getParameterTagList("postInsert_" + parentBlockName));
			setMethodType(customTrigger);
			break;
		/* POST-UPDATE */
		case 2:
			customTrigger.setBlock(parentBlockName);
			customTrigger.setJsfattr("postUpdate");
			customTrigger.setPlsql("postUpdate_" + parentBlockName);
			customTrigger.getParameter().addAll(
					getParameterTagList("postUpdate_" + parentBlockName));
			setMethodType(customTrigger);
			break;
		/* POST-DELETE */
		case 3:
			customTrigger.setBlock(parentBlockName);
			customTrigger.setJsfattr("postDelete");
			customTrigger.setPlsql("postDelete_" + parentBlockName);
			customTrigger.getParameter().addAll(
					getParameterTagList("postDelete_" + parentBlockName));
			setMethodType(customTrigger);
			break;
		/* PRE-QUERY */
		case 4:
			customTrigger.setBlock(parentBlockName);
			customTrigger.setJsfattr("preQuery");
			customTrigger.setPlsql("preQuery_" + parentBlockName);
			// customTrigger.setMethodType("objectSetter");
			customTrigger.getParameter().addAll(
					getParameterTagList("preQuery_" + parentBlockName));
			setMethodType(customTrigger);
			break;
		/* PRE-INSERT */
		case 5:
			customTrigger.setBlock(parentBlockName);
			customTrigger.setJsfattr("preInsert");
			customTrigger.setPlsql("preInsert_" + parentBlockName);
			// customTrigger.setMethodType("objectSetter");
			customTrigger.getParameter().addAll(
					getParameterTagList("preInsert_" + parentBlockName));
			setMethodType(customTrigger);
			break;
		/* PRE-UPDATE */
		case 6:
			customTrigger.setBlock(parentBlockName);
			customTrigger.setJsfattr("preUpdate");
			customTrigger.setPlsql("preUpdate_" + parentBlockName);
			// customTrigger.setMethodType("objectSetter");
			customTrigger.getParameter().addAll(
					getParameterTagList("preUpdate_" + parentBlockName));
			setMethodType(customTrigger);
			break;
		/* PRE-DELETE */
		case 7:
			customTrigger.setBlock(parentBlockName);
			customTrigger.setJsfattr("preDelete");
			customTrigger.setPlsql("preDelete_" + parentBlockName);
			// customTrigger.setMethodType("objectSetter");
			customTrigger.getParameter().addAll(
					getParameterTagList("preDelete_" + parentBlockName));
			setMethodType(customTrigger);
			break;
		/* WHEN-NEW-FORM-INSTANCE */
		case 8:
			break;
		/* Any thing else */
		default:
			/* do nothing */
			break;
		}
		// if (triggerName == "POST-QUERY"
		// || triggerName.equalsIgnoreCase("POST-QUERY")) {
		//
		// customTrigger.setBlock(parentBlockName);
		// customTrigger.setJsfattr("postQuery");
		// customTrigger.setPlsql("postQuery_" + parentBlockName);
		// customTrigger.setMethodType("plsqlCall");
		// customTrigger.getParameter().addAll(
		// getParameterTagList("postQuery_" + parentBlockName));
		//
		// } else if (triggerName == "PRE-INSERT"
		// || triggerName.equalsIgnoreCase("PRE-INSERT")) {
		// customTrigger.setBlock(parentBlockName);
		// customTrigger.setJsfattr("preInsert");
		// customTrigger.setPlsql("preInsert_" + parentBlockName);
		// customTrigger.setMethodType("objectSetter");
		// customTrigger.getParameter().addAll(
		// getParameterTagList("preInsert_" + parentBlockName));
		//
		// } else if (triggerName == "PRE-UPDATE"
		// || triggerName.equalsIgnoreCase("PRE-UPDATE")) {
		//
		// customTrigger.setBlock(parentBlockName);
		// customTrigger.setJsfattr("preUpdate");
		// customTrigger.setPlsql("preUpdate_" + parentBlockName);
		// customTrigger.setMethodType("objectSetter");
		// customTrigger.getParameter().addAll(
		// getParameterTagList("preUpdate_" + parentBlockName));
		//
		// }
		return customTrigger;
	}

	private List<ParameterType> getParameterTagList(String plsqlName) {
		List<ParameterType> paramList = new ArrayList<ParameterType>();
		try {
			File r3PreCustomModuleFile = new File(destParentFile
					+ File.separator + plsqlModule.getFormName()
					+ "_R3Module.xml");
			R3 r3Module = (R3) jaxbXmlReader.init(r3PreCustomModuleFile,
					R3.class);
			List<R3.Trigger> triggerList = r3Module.getTrigger();
			for (R3.Trigger trigger : triggerList) {
				if (trigger.getPlsql() != null
						&& (trigger.getPlsql() == plsqlName || trigger
								.getPlsql().equalsIgnoreCase(plsqlName))) {
					paramList = trigger.getParameter();
					// ParameterSort.sort(paramList);
					return paramList;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return paramList;
		}
		// ParameterSort.sort(paramList);
		return paramList;

	}

	private String getDefinitionWithMessageOut(String definitionText,
			List<ParameterType> outParamTag) {
		for (int i = 0; i < messageArray.length; i++) {
			String messageStr = messageArray[i];
			messageStr = preCustomization.getName() + "_" + messageStr;
			String dataType = "varchar2";
			if (i == 2) {
				// error flag is number type.
				dataType = "number";
			}
			definitionText = definitionText + ", " + messageStr + " out "
					+ dataType;
			ParameterType param = new ParameterType();
			param.setBlock("VIRTUAL_BLOCK");
			param.setItem(messageStr);
			param.setType("out");
			outParamTag.add(param);
		}
		return definitionText;
	}

	/**
	 * Set the methodType for customTrigger, Default methodType is objectSetter
	 * 
	 * @param customTrigger
	 */
	private void setMethodType(R3.Trigger customTrigger) {
		String methodType = "objectSetter";
		String parentBlock = customTrigger.getBlock();
		List<ParameterType> paramList = customTrigger.getParameter();
		boolean isParentBlockInParameters = false;
		for (ParameterType parameterType : paramList) {
			String parameterBlock = parameterType.getBlock();
			if (parameterBlock != null
					&& (parameterBlock == parentBlock || parameterBlock
							.equalsIgnoreCase(parentBlock))) {
				isParentBlockInParameters = true;
				if (parameterType.getType() == "in"
						|| parameterType.getType() == "out"
						|| parameterType.getType().equalsIgnoreCase("in")
						|| parameterType.getType().equalsIgnoreCase("out"))
					parameterType.setType(parameterType.getType() + "Local");
			}
		}
		if (!isParentBlockInParameters) {
			methodType = "plsqlCall";
		}
		customTrigger.setMethodType(methodType);
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
