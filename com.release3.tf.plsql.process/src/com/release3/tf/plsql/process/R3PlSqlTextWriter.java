package com.release3.tf.plsql.process;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.converter.toolkit.sqlparsing.CodeProcessor;
import com.converter.toolkit.sqlparsing.ProcessedCode;
import com.converter.toolkit.sqlparsing.Variable;
import com.converter.toolkit.ui.FormsObject;
import com.oracle.xmlns.forms.Module;
import com.oracle.xmlns.forms.ProgramUnit;
import com.release3.customization.parameter.ParameterType;
import com.release3.formsmap.R3FormsMap.Rec;
import com.release3.javasql.JavaPlSqlType;
import com.release3.javasql.R3JavaSqlGen;
import com.release3.programunitgen.R3ProgramUnit;
import com.release3.programunitgen.R3ProgramUnitGen;
import com.release3.r3coreplsqlgen.R3CorePlSql;
import com.release3.r3coreplsqlgen.R3CorePlSqlGen;
import com.release3.sqlparsing.VariableSort;
import com.release3.tf.core.Utils;

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
public class R3PlSqlTextWriter {

	private static R3PlSqlTextWriter plSqlTextWriter;
	private Module originalModule;
	private PrintWriter bodyOut;
	private PrintWriter defOut;
	private R3CorePlSqlGen plsqlModule;

	private R3PlSqlTextWriter() {

	}

	public static R3PlSqlTextWriter getPlSqlTextWriter() {
		if (plSqlTextWriter == null) {
			plSqlTextWriter = new R3PlSqlTextWriter();

		}
		return plSqlTextWriter;
	}

	public Module getOriginalModule() {
		return originalModule;
	}

	public void setOriginalModule(Module originalModule) {
		this.originalModule = originalModule;
	}

	public void writetoText(String parentFilePath, R3CorePlSqlGen plsqlModule,
			R3JavaSqlGen javaPlSqlGen, R3ProgramUnitGen programUnitGen)
			throws IOException {
		try {

			FileWriter bodyOutFile = new FileWriter(parentFilePath
					+ File.separator + plsqlModule.getName()
					+ "_body_after.sql");
			FileWriter defOutFile = new FileWriter(parentFilePath
					+ File.separator + plsqlModule.getName() + "_def_after.sql");
			bodyOut = new PrintWriter(bodyOutFile);
			defOut = new PrintWriter(defOutFile);
			writePackage(plsqlModule, javaPlSqlGen, programUnitGen);

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

	private void writePackage(R3CorePlSqlGen plsqlModule2,
			R3JavaSqlGen javaPlSqlGen, R3ProgramUnitGen programUnitGen) {

		defOut.write("CREATE or REPLACE PACKAGE " + plsqlModule2.getName()
				+ " AS\n");
		bodyOut.write("CREATE or REPLACE PACKAGE BODY "
				+ plsqlModule2.getName() + " AS\n");

		List<R3CorePlSql> r3TriggerList = plsqlModule2.getR3CorePlSql();
		for (R3CorePlSql r3Trigger : r3TriggerList) {
			if (r3Trigger.isToBeMigrate()) {
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

				// Trigger writer call

				writeTrigger(r3Trigger, blockName, itemName);
			}
		}
		List<R3ProgramUnit> r3PUList = programUnitGen.getR3ProgramUnit();
		for (R3ProgramUnit r3ProgramUnit : r3PUList) {
			if (r3ProgramUnit.isMoveToDB() != null
					&& r3ProgramUnit.isMoveToDB()) {
				writeProgramUnit(r3ProgramUnit.getR3ProgramUnit());
			}
		}
		List<JavaPlSqlType> javaPlSqlList = javaPlSqlGen.getJavaSql();
		for (JavaPlSqlType javaPlSql : javaPlSqlList) {
			if ( javaPlSql.isToBeMigrate()
					&& (javaPlSql.isMoveToDB() != null && javaPlSql
							.isMoveToDB())) {
				String blockName = javaPlSql.getBlockName();
				String itemName = javaPlSql.getItemName();
				if (javaPlSql.getBlockName() != null
						&& javaPlSql.getBlockName().length() > 0) {
					blockName = javaPlSql.getBlockName();
				}
				if (javaPlSql.getItemName() != null
						&& javaPlSql.getItemName().length() > 0) {
					itemName = javaPlSql.getItemName();
				}

				// Trigger writer call

				writeTrigger(javaPlSql, blockName, itemName);
			}
		}
		/* package ends here */
		defOut.write("\nEND " + plsqlModule2.getName() + ";");
		bodyOut.write("\nEND " + plsqlModule2.getName() + ";");
	}

	private void writeTrigger(R3CorePlSql trigger, String containingBlock,
			String containingItem) {

		String triggerName = trigger.getPlSqlName();
		String triggerText = process(trigger.getPlSqlText());
		// int triggerIndex = getPlSqlTriggerIndex(triggerName);
		// if (triggerIndex > -1) {
		// customTrigger.setJsfattr(getJavaName(triggerName));
		// customTrigger.setMethodType(getMethodType(triggerIndex));

		// CodeProcessor sqlparser = new CodeProcessor();
		// ProcessedCode code = sqlparser.process(triggerText);
		// Vector<Variable> variables = code.getVariables();
		//
		// if (containingBlock != null && containingBlock.length() > 0) {
		// // customTrigger.setBlock(containingBlock);
		// triggerName = triggerName + "_" + containingBlock;
		// }
		// if (containingItem != null && triggerName.length() > 0) {
		// // customTrigger.setItem(containingItem);
		// triggerName = triggerName.substring(triggerName.indexOf('.')) + "_" +
		// containingItem;
		// }
		StringBuffer starter = new StringBuffer();

		starter.append("-----------------------------------\n");
		starter.append("--" + trigger.getPlSqlName() + "--\n");
		starter.append("-----------------------------------\n");

		defOut.write(starter.toString());
		bodyOut.write(starter.toString());

		// StringBuffer newTriggerTextDef = new StringBuffer();
		// StringBuffer newTriggerTextBody = new StringBuffer();
		// triggerName = getJavaName(triggerName);
		// newTriggerTextDef.append("PROCEDURE " + triggerName
		// + "(sessionId varchar2 ");
		// customTrigger.setPlsql(triggerName);

		// for (Variable variable : variables) {
		// ParameterType param = new ParameterType();
		//
		// String blockName = variable.getBlock();
		// String itemName = variable.getItem();
		// param.setBlock(blockName);
		// param.setItem(itemName);
		// String dataType = findDataType(param);
		//
		// newTriggerTextDef.append(",");
		//
		// newTriggerTextDef.append(variable.getReplacedName() + " "
		// + variable.getType() + " " + dataType);
		// param.setType(variable.getType().toLowerCase());
		// // customTrigger.getParameter().add(param);
		// }
		// newTriggerTextBody.append(newTriggerTextDef);
		// newTriggerTextDef.append(") ;\n");
		defOut.write(trigger.getPlSqlDefinition());
		// newTriggerTextBody.append(") IS \n");
		// newTriggerTextBody.append("\tBEGIN\n");
		// newTriggerTextBody.append(code.getCode());
		// newTriggerTextBody.append("\n");
		// newTriggerTextBody.append("\t END\n");
		// trigger.setTriggerText(newTriggerTextBody.toString());
		bodyOut.write(triggerText);
		// r3PlSqlModule.getTrigger().add(customTrigger);
		// }
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

	private void writeTrigger(JavaPlSqlType trigger, String containingBlock,
			String containingItem) {

		String triggerName = trigger.getJavaPlSql();
		String triggerText = trigger.getJavaPlSqlText();
		CodeProcessor sqlparser = new CodeProcessor();
		ProcessedCode code = sqlparser.process(triggerText);
		Vector<Variable> variables = code.getVariables();

		StringBuffer newTriggerTextDef = new StringBuffer();
		StringBuffer newTriggerTextBody = new StringBuffer();

		newTriggerTextDef.append("PROCEDURE " + Utils.getPlSqlName(triggerName)
				+ "(sessionId varchar2 ");

		VariableSort.sort(variables);
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
			String dataType = "VARCHAR2";

			newTriggerTextDef.append(",");

			newTriggerTextDef.append(variable.getReplacedName() + " "
					+ variable.getType() + " " + dataType);
			param.setType(variable.getType().toLowerCase());

		}
		newTriggerTextBody.append(newTriggerTextDef);
		newTriggerTextDef.append(") ;\n");
		defOut.write(newTriggerTextDef.toString());
		newTriggerTextBody.append(") IS \n");
		newTriggerTextBody.append("\tBEGIN\n");
		newTriggerTextBody.append(code.getCode());
		newTriggerTextBody.append("\n");
		newTriggerTextBody.append("\t END;\n");
		trigger.setJavaPlSqlText(newTriggerTextBody.toString());
		bodyOut.write(newTriggerTextBody.toString());

		// StringBuffer starter = new StringBuffer();
		//
		// starter.append("-----------------------------------\n");
		// starter.append("--" + triggerName + "--\n");
		// starter.append("-----------------------------------\n");
		//
		// defOut.write(starter.toString());
		// bodyOut.write(starter.toString());
		//
		//
		// // defOut.write(trigger.getJavaPlSqlText());
		//
		// bodyOut.write(triggerText);

	}

	public void writeProgramUnit(ProgramUnit programUnit) {

		String programUnitText = programUnit.getProgramUnitText().replace("&#10;", "\n");
		CodeProcessor sqlparser = new CodeProcessor();
		ProcessedCode code = sqlparser.process(programUnitText);
		List<Variable> variableList = code.getVariables();
		programUnitText = code.getCode();
		StringBuffer newTriggerTextDef = new StringBuffer();
		// newTriggerTextDef.append(programUnit.getProgramUnitType() + " "
		// + programUnit.getName() + " " + "(sessionId varchar2 ");
		newTriggerTextDef.append(programUnit.getProgramUnitType() + " "
				+ programUnit.getName() + " " + "( ");
		boolean first = true;
		VariableSort.sort(variableList);
		for (Variable variable : variableList) {
			String dataType = "VARCHAR2";
			if (variable.getDataType() != null) {
				dataType = variable.getDataType();
			}
			String tmpStr = "";
			if (first) {
				tmpStr = variable.getReplacedName() + " " + variable.getType()
						+ " " + dataType;
				first = false;
			} else {
				tmpStr = ", " + variable.getReplacedName() + " "
						+ variable.getType() + " " + dataType;
			}
			newTriggerTextDef.append(tmpStr);
		}

		int beginIndex = programUnitText.indexOf('(');
		int endIndex = programUnitText.indexOf(')');
		int isIndex = programUnitText.indexOf("IS");
		if (isIndex == -1) {
			isIndex = programUnitText.indexOf("is");
		}
		String variableString = null;
		if (beginIndex < endIndex && isIndex > endIndex) {
			variableString = programUnitText
					.substring(beginIndex + 1, endIndex);
			if (first) {
				newTriggerTextDef.append(variableString);
				first = false;
			} else {
				newTriggerTextDef.append(", " + variableString);
			}
			// String[] variableTokens = variableString.split(",");
			// for (String variableToken : variableTokens) {
			// variableToken=variableToken.replaceAll("^ +| +$|( )+",
			// "$1").trim();
			// String[] tokenDataType = variableToken.split(" ");
			// if (tokenDataType.length > 2) {
			// Variable variable = new Variable(tokenDataType[0],
			// tokenDataType[1], tokenDataType[0]);
			// variable.setDataType(tokenDataType[2]);
			// variableList.add(variable);
			// } else if (tokenDataType.length > 1) {
			// Variable variable = new Variable(tokenDataType[0], "IN",
			// tokenDataType[0]);
			// variable.setDataType(tokenDataType[1]);
			// variableList.add(variable);
			// } else if (tokenDataType.length > 0) {
			// Variable variable = new Variable(tokenDataType[0], "IN",
			// tokenDataType[0]);
			// variable.setDataType("VARCHAR2");
			// variableList.add(variable);
			// }
			//
			// }
			programUnitText = process(programUnitText.substring(endIndex + 1));

		} else {
			String[] tokens = programUnitText.split(" ");
			StringBuffer newProgramUnitTextBuffer = new StringBuffer();
			for (String token : tokens) {

				if (!((token.toUpperCase().indexOf(
						programUnit.getName().toUpperCase()) > -1) || token
						.equalsIgnoreCase(programUnit.getProgramUnitType()))) {
					newProgramUnitTextBuffer.append(" " + token);
				}

			}
			programUnitText = process(newProgramUnitTextBuffer.toString());
			System.out.println(newProgramUnitTextBuffer);

		}
		newTriggerTextDef.append(")");
		programUnitText = programUnitText.replace("&#10;", "\n");

		defOut.write("-----------------------------------\n");
		defOut.write(newTriggerTextDef + " ;");
		defOut.write("-----------------------------------\n");
		bodyOut.write("-----------------------------------\n");
		bodyOut.write("--" + programUnit.getName() + "--\n");
		bodyOut.write("-----------------------------------\n");
		bodyOut.write(newTriggerTextDef + "\n");
		bodyOut.write(programUnitText);
		bodyOut.write("\n");
	}

	public String process(String code) {
		// String regexp = "(\\s)*((message)[\\s]*(\\())*";
		String regexp = "((\\s)*((message)[\\s]*(\\()))|((RAISE)[\\s]*(form_trigger_failure))";
		Pattern p = Pattern.compile(regexp, Pattern.CASE_INSENSITIVE);
		Matcher match = p.matcher(code);
		StringBuffer builder = new StringBuffer();
		String name;
		int start, end;
		int off = 0;
		while (match.find()) {

			start = match.start(4);

			end = match.end(5);
			if (start != -1 && end != -1) {

				name = code.substring(start, end);
				// System.out.println("name: "+name);
				String mGrp = match.group(4);
				String mGrp1 = match.group(5);

				if ((mGrp != null && mGrp.equalsIgnoreCase("message"))
						&& (mGrp1 != null && mGrp1.equals("("))) {
					builder.append(code.substring(off, start));
					builder.append("----Added by TransForm----\n");
					builder.append("MESSAGE_TYPE := 'INFO';\n");
					builder.append("ERROR_FLAG := 0;\n");
					builder.append("----If this message is followed by a RAISE form_trigger_failure please change ERROR_FLAG to 1 and MESSAGE_TYPE to 'ERROR'.----\n");
					builder.append(mGrp + " := " + mGrp1);

					off = end;
				}
			}
			start = match.start(7);

			end = match.end(8);
			if (start == -1 || end == -1) {
				continue;
			}
			name = code.substring(start, end);
			String mGrp7 = match.group(7);
			String mGrp8 = match.group(8);
			if ((mGrp7 != null && mGrp7.equalsIgnoreCase("RAISE"))
					&& (mGrp8 != null && mGrp8.equals("form_trigger_failure"))) {
				builder.append(code.substring(off, start));
				builder.append("--" + name);
				off = end;
			}

		}
		builder.append(code.substring(off));
		return builder.toString();
	}

	public static void main(String args[]) {
		ProgramUnit pu = new ProgramUnit();

		String text = "PROCEDURE Query_Master_Details(rel_id Relation,detail VARCHAR2) IS &amp;#10;  oldmsg VARCHAR2(2);  -- Old Message Level Setting &amp;#10;  reldef VARCHAR2(5);  -- Relation Deferred Setting &amp;#10;BEGIN &amp;#10;  -- &amp;#10;  -- Initialize Local Variable(s) &amp;#10;  -- &amp;#10;  reldef := Get_Relation_Property(rel_id, DEFERRED_COORDINATION); &amp;#10;  oldmsg := :System.Message_Level; &amp;#10;  -- &amp;#10;  -- If NOT Deferred, Goto detail and execute the query. &amp;#10;  -- &amp;#10;  IF reldef = 'FALSE' THEN &amp;#10;    Go_Block(detail); &amp;#10;    Check_Package_Failure; &amp;#10;    :System.Message_Level := '10'; &amp;#10;    Execute_Query; &amp;#10;    :System.Message_Level := oldmsg; &amp;#10;  ELSE &amp;#10;    -- &amp;#10;    -- Relation is deferred, mark the detail block as un-coordinated &amp;#10;    -- &amp;#10;    Set_Block_Property(detail, COORDINATION_STATUS, NON_COORDINATED); &amp;#10;  END IF; &amp;#10; &amp;#10;EXCEPTION &amp;#10;    WHEN Form_Trigger_Failure THEN &amp;#10;      :System.Message_Level := oldmsg; &amp;#10;      RAISE; &amp;#10;END Query_Master_Details; &amp;#10;";
		pu.setProgramUnitText(text);
		pu.setProgramUnitType("Procedure");
		pu.setName("QUERY_MASTER_DETAILS");
		R3PlSqlTextWriter writer = new R3PlSqlTextWriter();
		List<Variable> variableList = new ArrayList<Variable>();
		// writer.processProgramUnit(pu, variableList);
	}
}