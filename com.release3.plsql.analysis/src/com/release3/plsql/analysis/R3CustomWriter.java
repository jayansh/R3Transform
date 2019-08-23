package com.release3.plsql.analysis;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Vector;

import javax.xml.bind.JAXBException;

import com.converter.toolkit.sqlparsing.CodeProcessor;
import com.converter.toolkit.sqlparsing.ProcessedCode;
import com.converter.toolkit.sqlparsing.Variable;
import com.converter.toolkit.ui.FormsObject;
import com.converter.toolkit.ui.JAXBXMLWriter;
import com.converter.toolkit.ui.custom.Parameter;
import com.oracle.xmlns.forms.Block;
import com.oracle.xmlns.forms.Item;
import com.oracle.xmlns.forms.Module;
import com.oracle.xmlns.forms.ProgramUnit;
import com.oracle.xmlns.forms.Trigger;
import com.release3.customization.R3;
import com.release3.customization.parameter.ParameterType;
import com.release3.plsqlgen.R3PlSql;
import com.release3.sqlparsing.VariableSort;
import com.release3.tf.core.form.Settings;
import com.release3.transform.constants.UIConstants;
import com.release3.variablemap.Variables;

/**
 * 
 * @author Jayansh
 * 
 *         This class using jaxb to write a text pls/sql file and an xml file
 *         used to generate customization file.
 * 
 *         Trigger types = 0 - Form Level Trigger 1 - Block Level Trigger 2 -
 *         Item Level Trigger
 * 
 */
public class R3CustomWriter {

	private static R3CustomWriter plSqlTextWriter;
	private Module originalModule;
	private PrintWriter bodyOut;
	private PrintWriter defOut;
	private R3PlSql plsqlModule;
	private R3 r3PlSqlModule;

	// public static String[] TRIGGER_PLSQL = { "POST-QUERY", "PRE-QUERY",
	// "PRE-INSERT", "PRE-UPDATE", "PRE-DELETE", "WHEN-VALIDATE-ITEM",
	// "WHEN-NEW-FORM-INSTANCE" };

	private R3CustomWriter() {

	}

	public static R3CustomWriter getPlSqlTextWriter() {
		if (plSqlTextWriter == null) {
			plSqlTextWriter = new R3CustomWriter();

		}
		return plSqlTextWriter;
	}

	public Module getOriginalModule() {
		return originalModule;
	}

	public void setOriginalModule(Module originalModule) {
		this.originalModule = originalModule;
	}

	public void writetoText(File parentFilePath, R3PlSql plsqlModule)
			throws IOException {
		try {
			this.plsqlModule = plsqlModule;
			r3PlSqlModule = new R3();
			r3PlSqlModule.setName(plsqlModule.getFormName());
			FileWriter bodyOutFile = new FileWriter(parentFilePath
					+ File.separator + plsqlModule.getFormName()
					+ "_body_before.sql");
			FileWriter defOutFile = new FileWriter(parentFilePath
					+ File.separator + plsqlModule.getFormName()
					+ "_def_before.sql");
			bodyOut = new PrintWriter(bodyOutFile);
			defOut = new PrintWriter(defOutFile);
			writePackage(plsqlModule);
			writeToR3Module(
					parentFilePath + File.separator + plsqlModule.getFormName()
							+ "_R3Module.xml", r3PlSqlModule);

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			defOut.flush();
			defOut.close();
			if (bodyOut != null) {
				bodyOut.flush();
				bodyOut.close();
			}
		}
	}

	// private void write(FormsObject formModule) {
	//
	// List<FormsObject> formsObjectList = formModule.getChildren();
	// for (FormsObject formsObject : formsObjectList) {
	// if (formsObject instanceof Trigger) {
	// writeTrigger(formsObject, null);
	// } else if (formsObject instanceof ProgramUnit) {
	// writeProgramUnit(formsObject);
	// } else if (formsObject instanceof Block) {
	// Block block = (Block) formsObject;
	// String blockName = block.getName();
	//
	// } else {
	//
	// if (formsObject.getChildren().size() > 0) {
	//
	// write(formsObject);
	// }
	// }
	// }
	// }

	public void writePackage(R3PlSql plsqlModule) {

		defOut.write("CREATE or REPLACE PACKAGE " + plsqlModule.getFormName()
				+ " AS\n");
		bodyOut.write("CREATE or REPLACE PACKAGE BODY "
				+ plsqlModule.getFormName() + " AS\n");

		List<R3PlSql.R3Trigger> r3TriggerList = plsqlModule.getR3Trigger();
		for (R3PlSql.R3Trigger r3Trigger : r3TriggerList) {

			String blockName = r3Trigger.getBlockName();
			String itemName = r3Trigger.getItemName();
			if (r3Trigger.getBlockName() != null
					&& r3Trigger.getBlockName().length() > 0) {
				blockName = r3Trigger.getBlockName();
			}
			if (r3Trigger.getItemName() != null
					&& r3Trigger.getItemName().length() > 0) {
				itemName = r3Trigger.getItemName();
			}
			Trigger trigger = r3Trigger.getTrigger();
			// Trigger writer call

			writeTrigger(trigger, blockName, itemName);

		}
		/* package ends here */
		defOut.write("\nEND " + plsqlModule.getFormName() + ";");
		bodyOut.write("\nEND " + plsqlModule.getFormName() + ";");
	}

	public void writeTrigger(FormsObject formsObject, String containingBlock,
			String containingItem) {

		R3.Trigger customTrigger = new R3.Trigger();

		Trigger trigger = (Trigger) formsObject;

		String triggerName = trigger.getName();
		String triggerText = trigger.getTriggerText();
		int triggerIndex = UIConstants.getPlSqlTriggerIndex(triggerName);
		if (triggerIndex > -1) {
			customTrigger.setJsfattr(getJavaName(triggerName));
			customTrigger.setMethodType(getMethodType(triggerIndex));

			CodeProcessor sqlparser = new CodeProcessor();
			ProcessedCode code = sqlparser.process(triggerText);
			Vector<Variable> variables = code.getVariables();

			if (containingBlock != null && containingBlock.length() > 0) {
				customTrigger.setBlock(containingBlock);
				triggerName = triggerName + "_" + containingBlock;
			}
			if (containingItem != null && triggerName.length() > 0) {
				customTrigger.setItem(containingItem);
				triggerName = triggerName + "_" + containingItem;
			}
			StringBuffer starter = new StringBuffer();

			starter.append("-----------------------------------\n");
			starter.append("--" + trigger.getName() + "--\n");
			starter.append("-----------------------------------\n");

			defOut.write(starter.toString());
			bodyOut.write(starter.toString());

			StringBuffer newTriggerTextDef = new StringBuffer();
			StringBuffer newTriggerTextBody = new StringBuffer();
			triggerName = getJavaName(triggerName);
			newTriggerTextDef.append("PROCEDURE " + triggerName
					+ "(sessionId varchar2 ");
			customTrigger.setPlsql(triggerName);
			VariableSort.sort(variables);
			addMessageVariables(variables);
			for (Variable variable : variables) {
				ParameterType param = new ParameterType();

				String blockName = variable.getBlock();
				if (blockName != null) {
					blockName = blockName.toUpperCase();
				}
				String itemName = variable.getItem();
				if (itemName != null) {
					itemName = itemName.toUpperCase();
				}
				param.setBlock(blockName);
				param.setItem(itemName);

				param.setName(variable.getReplacedName());

				String paramType = variable.getType().toLowerCase();
				if (containingBlock != null
						&& containingBlock.equals(blockName)) {
					paramType = paramType + "Local";
				}
				param.setType(paramType);
				
				customTrigger.getParameter().add(param);
			}
			ParameterSort.sort(customTrigger.getParameter());
			for (ParameterType param : customTrigger.getParameter()) {
				newTriggerTextDef.append(",");
				String dataType = findDataType(param);
				if (dataType.equalsIgnoreCase("datetime")) {
					dataType = "DATE";
				}
				String paramType = param.getType().replace("Local", "");

				newTriggerTextDef.append(param.getName() + " " + paramType
						+ " " + dataType);
			}
			newTriggerTextBody.append(newTriggerTextDef);
			newTriggerTextDef.append(") ;\n");
			defOut.write(newTriggerTextDef.toString());
			newTriggerTextBody.append(") IS \n");
			newTriggerTextBody.append("\tBEGIN\n");
			newTriggerTextBody.append(code.getCode());
			newTriggerTextBody.append("\n");
			newTriggerTextBody.append("\t END;\n");
			trigger.setTriggerText(newTriggerTextBody.toString());
			bodyOut.write(newTriggerTextBody.toString());
			r3PlSqlModule.getTrigger().add(customTrigger);
		}
		/* This code will not appear here, will be gone to builtinXmlWriter */
		// else {
		// if (triggerText.contains("set_item_property")) {
		// BuiltinType builtin = new BuiltinType();
		// builtin.setBuiltinName("set_item_property");
		// switch (level) {
		// case 1:
		// builtin.setBlockName(suffix);
		// break;
		// case 2:
		// builtin.setBlockName(suffix.substring(0, suffix.indexOf('_')));
		// builtin.setItemName(suffix.substring(suffix.indexOf('_') + 1));
		//
		// break;
		// }
		// builtin.setFormName(formModule.getName());
		// builtin.setPropertyName(Dis)
		// }
		// }

	}

	public void writeProgramUnit(FormsObject formsObject) {
		ProgramUnit programUnit = (ProgramUnit) formsObject;
		String programUnitText = programUnit.getProgramUnitText().replace(
				"&#10;", "\n");
		CodeProcessor sqlparser = new CodeProcessor();
		ProcessedCode code = sqlparser.process(programUnitText);
		bodyOut.write("-----------------------------------\n");
		bodyOut.write("--" + programUnit.getName() + "--\n");
		bodyOut.write("-----------------------------------\n");
		bodyOut.write(code.getCode());
		bodyOut.write("\n");
	}

	private String findDataType(ParameterType paramType) {
		String blockName = paramType.getBlock();
		String itemName = paramType.getItem();
		
		if (blockName == null && itemName != null) {
			List<FormsObject> children = originalModule.getFormModule()
					.getChildren();
			for (FormsObject formsObject : children) {
				if (formsObject instanceof Block) {
					Block block = (Block) formsObject;
					List<FormsObject> blockChildren = block.getChildren();
					for (FormsObject blockObject : blockChildren) {
						if (blockObject instanceof Item) {
							Item item = (Item) blockObject;
							if (item.getName() == itemName
									|| item.getName()
											.equalsIgnoreCase(itemName)) {
								paramType.setBlock(block.getName());
								if (item.getDataType() != null) {
									return item.getDataType();
								} else {
									return "VARCHAR2";
								}
							}
						}
					}
				}
			}
		}
		if (blockName != null && itemName != null) {
			if(blockName.equals("VIRTUAL_BLOCK") && itemName.equals(originalModule.getFormModule().getName()+"_ERROR_FLAG")){
				return "number";
			}
			List<FormsObject> children = originalModule.getFormModule()
					.getChildren();
			for (FormsObject formsObject : children) {
				if (formsObject instanceof Block) {
					Block block = (Block) formsObject;
					if (block.getName() == blockName
							|| block.getName().equalsIgnoreCase(blockName)) {
						List<FormsObject> blockChildren = block.getChildren();
						for (FormsObject blockObject : blockChildren) {
							if (blockObject instanceof Item) {
								Item item = (Item) blockObject;
								if (item.getName() == itemName
										|| item.getName().equalsIgnoreCase(
												itemName)) {
									if (item.getDataType() != null) {
										return item.getDataType();
									} else {
										return "VARCHAR2";
									}
								}
							}
						}
					}
				}
			}
		}
		return "VARCHAR2";
	}

	public void writeToR3Module(String destXmlPath, Object module)
			throws IOException {
		try {
			JAXBXMLWriter.writetoXML(destXmlPath, module);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Only for strings contains in TRIGGER_PLSQL like POST-QUERY,PRE-QUERY.
	 * (containing char '-')
	 * 
	 * @param plsqlName
	 */
	public String getJavaName(String plsqlName) {
		int index = plsqlName.indexOf('-');

		StringBuilder javaName = new StringBuilder();
		if (index > 0) {
			String[] javaStrArr = plsqlName.split("-");

			for (int i = 0; i < javaStrArr.length; i++) {
				String javaSubStr = javaStrArr[i];
				if (i == 0) {
					javaName.append(javaSubStr.toLowerCase());
				} else {
					javaName.append(
							Character.toUpperCase(javaStrArr[i].charAt(0)))
							.append(javaStrArr[i].substring(1).toLowerCase());

				}
			}

		}
		if (javaName.length() == 0) {
			return plsqlName;
		} else {
			return javaName.toString();
		}
	}

	/**
	 * 
	 * @param triggerIndex
	 * @return methodType corresponding to TRIGGER_PLSQL
	 */
	public String getMethodType(int triggerIndex) {
		switch (triggerIndex) {
		// "POST-QUERY"
		case 0:
			return "plsqlCall";

			// PRE-QUERY
		case 1:
			return "objectSetter";

			// PRE-INSERT
		case 2:
			break;

		// PRE-UPDATE
		case 3:
			break;

		// PRE-DELETE
		case 4:
			break;

		// WHEN-VALIDATE-ITEM
		case 5:
			break;
		case 6:
			return "init";
		}
		return null;
	}

	public void addMessageVariables(List<Variable> variables) {
		int index = 0;
		for (int i = 0; i < variables.size(); i++) {
			index = i;
			if (index > 0) {
				Variable iVar = variables.get(index);
				Variable iMin1Var = variables.get(index - 1);
				if (iVar.getType().equalsIgnoreCase(Variable.TYPE_OUT)
						&& iMin1Var.getType()
								.equalsIgnoreCase(Variable.TYPE_IN)) {
					break;
				}
			}
			if (i + 1 == variables.size()) {
				index = i + 1;
			}
		}
		Variable message = new Variable("MESSAGE", "OUT", "MESSAGE");
		message.setBlock("VIRTUAL_BLOCK");
		message.setItem(plsqlModule.getFormName() + "_MESSAGE");
		variables.add(index, message);
		Variable messageType = new Variable("MESSAGE_TYPE", "OUT",
				"MESSAGE_TYPE");
		messageType.setBlock("VIRTUAL_BLOCK");
		messageType.setItem(plsqlModule.getFormName() + "_MESSAGE_TYPE");
		variables.add(index + 1, messageType);
		Variable errorFlag = new Variable("ERROR_FLAG", "OUT", "ERROR_FLAG");
		errorFlag.setBlock("VIRTUAL_BLOCK");
		errorFlag.setItem(plsqlModule.getFormName() + "_ERROR_FLAG");
		errorFlag.setDataType("number");
		variables.add(index + 2, errorFlag);
	}
}