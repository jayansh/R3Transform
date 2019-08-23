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
package com.release3.tf.javagen;

import java.util.List;

import com.converter.toolkit.ui.FormsObject;
import com.oracle.xmlns.forms.Block;
import com.oracle.xmlns.forms.FormModule;
import com.oracle.xmlns.forms.Item;
import com.oracle.xmlns.forms.Module;

/**
 * 
 * @author Jayansh Shinde Converting form plsql to java code
 *         Select/Insert/Update/Delete calls will be called through
 *         EntityManager.
 * 
 */
/**
 * Deprecated - Now using Ispirer SQLWays instead.
 */
@Deprecated
public class PlSqlParser {
	@Deprecated
	public String migrateToJava(String text, String formName, Module module) {
		if (text != null) {
			text = text.toLowerCase();
			text = text.replace("end if;", "}");
			text = ifThenReplacer(text, formName, module);

			// text = text.replace("if", "if(");
			// text = text.replace("then", "){ \nreturn\ntrue; ");
			 text = text.replace("else", "} else { ");
			text = text.replace("then", "");
			text = text.replace("&amp;#10;", "\n");
			text = text.replace("&#10;", "\n");
			text = text.replace("'", "\"");
			text = text.replace("/*", "--");
			text = text.replace("*/", "");
			// text = text.replace("-", "_");

			// }
			System.out.println("Text::: " + text);
		}
		//comment generated code...
//		text = "/*\n "+text +" */";
		
		return text;
	}

	public String ifThenReplacer(String text, String formName, Module module) {
		// StringBuffer migratedJavaCode = new StringBuffer("");

		int ifIndex = text.indexOf("if :");
		int thenIndex = text.indexOf("then");
		String originalText = text;
		if (ifIndex > -1 && thenIndex > ifIndex) {
			String condition = text.substring(ifIndex + 3, thenIndex + 4);
			text = text.substring(thenIndex + 5);
			String[] conditionArr = {};
			String javaCondition = "";
			if (condition.contains("=")) {
				conditionArr = condition.split("=");
				javaCondition = getJavaIfCondition(formName, conditionArr);
			} else if (condition.contains(" in ")) {
				conditionArr = condition.split(" in ");
				javaCondition = getJavaIfCondWhenIn(formName, conditionArr);
			} else if (condition.contains("is")) {
				conditionArr = condition.split("is");
				if (conditionArr[1].contains("not")) {
					javaCondition = getJavaIsNotNullCondition(formName,
							conditionArr);
				}

			} else {
				javaCondition = condition;
			}
			text = "if (" + javaCondition + ") { "
					+ ifThenReplacer(text, formName, module);
		}

		return text;
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

	private String findBlock(String itemName, Module module) {

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

	private String getJavaIfCondWhenIn(String formName, String[] conditionArr) {
		String conditionStr1 = conditionArr[0].replace(":", "").trim();
		String conditionStr2 = conditionArr[1].substring(
				conditionArr[1].indexOf('(') + 1, conditionArr[1].indexOf(')'));
		String inArray[] = conditionStr2.split(",");

		formName = formName.replace(".fmb", "");
		String blockName = null;
		String itemName = null;
		if (conditionStr1.indexOf(".") > -1) {
			String[] strArray = conditionStr1.split("\\.");
			blockName = strArray[0].toUpperCase();
			itemName = strArray[1].toUpperCase();

		} else {
			itemName = conditionStr1.toUpperCase();
			// blockName = findBlock(itemName, module);
		}
		String javaCondition = null;
		for (int i = 0; i < inArray.length; i++) {
			if (javaCondition == null) {
				javaCondition = formName + blockName + ".get" + itemName
						+ "()== null && (" + formName + blockName + ".get"
						+ itemName + "() == " + inArray[i] + " || " + formName
						+ blockName + ".get" + itemName
						+ "().equalsIgnoreCase(" + inArray[i] + "))";
			} else {
				javaCondition = javaCondition + " || (" + formName + blockName
						+ ".get" + itemName + "() == " + inArray[i] + " || "
						+ formName + blockName + ".get" + itemName
						+ "().equalsIgnoreCase(" + inArray[i] + "))";
			}
		}
		if (javaCondition == null) {
			return "";
		}

		return javaCondition;

	}

	private String getJavaIfCondition(String formName, String[] conditionArr) {
		String conditionStr1 = conditionArr[0].replace(":", "").trim();
		formName = formName.replace(".fmb", "");
		String blockName = null;
		String itemName = null;
		if (conditionStr1.indexOf(".") > -1) {
			String[] strArray = conditionStr1.split("\\.");
			blockName = strArray[0].toUpperCase();
			itemName = strArray[1].toUpperCase();

		} else {
			itemName = conditionStr1.toUpperCase();
			// blockName = findBlock(itemName, module);
		}

		String javaCondition = formName + blockName + ".get" + itemName
				+ "()== null || " + formName + blockName + ".get" + itemName
				+ "() == " + conditionArr[1] + " || " + formName + blockName
				+ ".get" + itemName + "().equalsIgnoreCase(" + conditionArr[1]
				+ ")";
		return javaCondition;

	}

	private String getJavaIsNotNullCondition(String formName,
			String[] conditionArr) {
		String conditionStr1 = conditionArr[0].replace(":", "").trim();
		formName = formName.replace(".fmb", "");
		String blockName = null;
		String itemName = null;
		if (conditionStr1.indexOf(".") > -1) {
			String[] strArray = conditionStr1.split("\\.");
			blockName = strArray[0].toUpperCase();
			itemName = strArray[1].toUpperCase();

		} else {
			itemName = conditionStr1.toUpperCase();
			// blockName = findBlock(itemName, module);
		}

		String javaCondition = formName + blockName + ".get" + itemName
				+ "()!= null ";
		return javaCondition;

	}

	public static void main(String args[]) {
		String plsql = "if :save_note.save_note  in ('Y','N') then &amp;#10;" +
				"   if :save_note.save_note = 'Y' then &amp;#10;      " +
				"get_user_name(:company.m_user,:case_note.m_create_name); &amp;#10;      " +
				"-- if coming from patient history set :global.last_rvw_no &amp;#10;      " +
				"if :global.calling_form = 'hcas0186v' then &amp;#10;         " +
				":global.last_rvw_no := :global.143_last_rvw_no; &amp;#10;      end if; &amp;#10;      " +
				":case_note.case_note_rvw_no := :global.last_rvw_no; &amp;#10;      " +
				"-- if creating note for inq won't be a review # &amp;#10;      " +
				"if :case_note.case_note_rvw_no is null then &amp;#10;         " +
				":case_note.case_note_rvw_no := 1; &amp;#10;      " +
				"end if; &amp;#10;      :case_note.create_date := sysdate; &amp;#10;      " +
				":case_note.created_by := :company.m_user; &amp;#10;      " +
				"if :global.table_name is not null then &amp;#10;         " +
				":system.message_level := 20; &amp;#10;      end if; &amp;#10;      commit; &amp;#10;      :system.message_level := 0; &amp;#10;      " +
				":hcx_case.m_note_count := :hcx_case.m_note_count + 1; &amp;#10;      " +
				"if :global.table_name is not null then &amp;#10;         exit_case_notes; &amp;#10;      else &amp;#10;         " +
				"go_field('save_note.m_refer_ind'); &amp;#10;      end if; &amp;#10;   else &amp;#10;      " +
				"-- not saving note, clear and exit if no case notes in case &amp;#10;      " +
				"clear_block(no_validate); &amp;#10;      if :global.table_name is not null then &amp;#10;         exit_case_notes; &amp;#10;      elsif :global.case_note_count &gt; 0 then &amp;#10;         go_block('case_note'); &amp;#10;         clear_block(no_validate); &amp;#10;         go_block('case_note_text'); &amp;#10;         clear_block(no_validate); &amp;#10;         go_block('case_note'); &amp;#10;         do_key('execute_query'); &amp;#10;         get_record; &amp;#10;      else &amp;#10;         exit_case_notes; &amp;#10;      end if; &amp;#10;   end if; &amp;#10;else &amp;#10;   bell; &amp;#10;   message('Enter &quot;Y&quot; or &quot;N&quot; '); &amp;#10;   raise form_trigger_failure; &amp;#10;end if;";
		// plsql = plsql.replace("&amp;#10", "\n");
		// plsql = plsql.replace("&#10;", "\n");
		// System.out.println(plsql);
		// String conditionStr1 = "save_note.save_note";
		// String[] strArray = conditionStr1.split("\\.");
		// System.out.println(strArray[0] + " ::: " + strArray[1]);
		PlSqlParser parser = new PlSqlParser();
		String text = parser.migrateToJava(plsql, "HCAS0054", null);
		System.out.println(text);

	}

}
