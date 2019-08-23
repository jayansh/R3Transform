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
package com.converter.toolkit.sqlparsing;

import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Scans the input code for bind variables, stores them in a vector and and
 * converts them into parameter variables.
 * 
 * Uses a regular expression to identify the bind variables.
 * 
 * @author www.expressjava.net
 * 
 */
public class CodeProcessor {

	/**
	 * Regular expression to identify bind variables (explained in the
	 * accompanying documentation).
	 */
	// public final String DEFAULT_REGEXP =
	// "[^:]*(:([a-zA-Z0-9_\\.]+))(?:\\s)*((:=)?)";
	public final String DEFAULT_REGEXP = "((INTO)?)[\\s]*[:]*(:([a-zA-Z0-9_\\.]+))(?:\\s)*((:=)?)";

	/**
	 * Regular expression in use.
	 */
	private String regexp = DEFAULT_REGEXP;

	/**
	 * Default no-arg constructor, uses default regular expression.
	 */
	public CodeProcessor() {
	}

	/**
	 * Constructor that allows using an alternative regular expression.
	 */
	public CodeProcessor(String regexp) {
		this.regexp = regexp;
	}

	/**
	 * Parses and processes the input code.
	 * 
	 * @param code
	 *            Input code
	 * @return an instance of <code>ProcessedCode</code> with the processing
	 *         result.
	 */
	public ProcessedCode process(String code) {
		Pattern p = Pattern.compile(regexp, Pattern.CASE_INSENSITIVE);

		Matcher m = p.matcher(code);

		Vector<Variable> variables = new Vector<Variable>();

		StringBuffer builder = new StringBuffer();

		int start, end;

		Variable variable;

		String name;

		int off = 0;

		// Scanning loop
		while (m.find()) {

			// The variable's name is stored in group 3.
			start = m.start(3);

			end = m.end(3);

			name = code.substring(start, end);

			// String mGrp = m.group(3);
			// System.out.println(mGrp);
			// If the variable was the left operand in an assignment statement,
			// then group 5 must be not null ( as it must hold the :=
			// characters).
			// If the variable starts with INTO then group 2 must have value
			// INTO.
			boolean outOrIn = (m.group(1).length() == 0)
					&& (m.group(5).length() == 0);
			variable = new Variable(name, outOrIn ? Variable.TYPE_IN
					: Variable.TYPE_OUT, newName(name));
			// variable = new Variable(name,
			// m.group(3).length() == 0 ? Variable.TYPE_IN
			// : Variable.TYPE_OUT, newName(name));

			/*
			 * // Adds the variable instance into the vector unless there's
			 * already // one with the same name. if
			 * (!variables.contains(variable)) variables.add(variable);
			 */
			/*
			 * replaced by jay with below code
			 */
			replaceOrAdd(variables, variable);
			// Appends the code before the current variable and the previous one
			// (or the beginning of the code).
			builder.append(code.substring(off, start));
			// Appends new variable name
			builder.append(variable.getReplacedName());

			off = end;
		}

		// Appends the code after the last variable detected.
		builder.append(code.substring(off));

		return new ProcessedCode(variables, builder.toString());

	}

	/**
	 * replace the variable instance into the vector if it's already exist as
	 * TYPE_IN.
	 * 
	 * @param variables
	 * @param variable
	 * @return true if replace successful else false
	 */
	public Boolean replaceOrAdd(Vector<Variable> variables, Variable variable) {
		if (contains(variables,variable)) {
			if ((variable.getType() == Variable.TYPE_OUT || variable.getType()
					.equalsIgnoreCase(Variable.TYPE_OUT))) {
				for (Variable existingVariable : variables) {
					if (existingVariable.getType() == Variable.TYPE_IN
							|| existingVariable.getType().equalsIgnoreCase(
									Variable.TYPE_IN)) {
						// if (existingVariable.getName().equalsIgnoreCase(
						// variable.getName())
						// || existingVariable.getName() == variable
						// .getName())
						if (existingVariable.equals(variable)) {
							variables.remove(existingVariable);
							variables.add(variable);
							return true;
						}
					}
				}
			}
		} else {
			variables.add(variable);
		}
		return false;
	}

	public boolean contains(Vector<Variable> variables, Variable variable) {
		for (Variable existingVariable : variables) {
			if (existingVariable.getName().equalsIgnoreCase(
							variable.getName())) {
				return true;
			}
		}
		return false;

	}

	public String replace(String str) {
		return str.replaceAll("&#10;", "\n");
	}

	/**
	 * Creates the replacement name by prepending <code>p_</code> to the
	 * variable's original name after stripping the colon and replacing all
	 * instances of character <code>.</code> with character <code>_</code>.
	 * 
	 * @param name
	 *            Variable's original name
	 * @return variable's new replacement name
	 */
	private String newName(String name) {
		StringBuilder builder = new StringBuilder("p_");

		// Character at index 0 is the colon, skip it.
		// Replace all dots with underscores.
		builder.append(name.substring(1).replaceAll("\\.", "_"));

		return builder.toString();
	}

	public static void main(String[] args) {
		CodeProcessor codeProcessor = new CodeProcessor();
		// "	    declare"+
		// "	    n  number;"+
		// "	    begin"+
		// "	    if :from_site is null then"+
		// "	        go_item('FROM_SITE');"+
		// "	        msg_alert('You must provide the site that originally contains the components to be transferred !!!','W',TRUE);"+
		// "	    else"+
		// "	       if  :component_nbr1 is null"+
		// "	       and :component_nbr2 is null"+
		// "	       and :component_nbr3 is null"+
		// "	       and :component_nbr4 is null"+
		// "	       and :component_nbr5 is null"+
		// "	       and :component_nbr6 is null"+
		// "	       and :component_nbr7 is null"+
		// "	       and :component_nbr8 is null"+
		// "	       then"+
		// "	           go_item('COMPONENT_NBR1');   "+
		// "	           msg_alert('You must provide at least one component ID !!!','W',TRUE);"+
		// "	       else"+
		// "	          if :to_site is null or :location_id is null then"+
		// "	             go_item('TO_SITE');   "+
		// "	             msg_alert('You must provide both Destination Site and Location ID !!!','W',TRUE);"+
		// "	          else"+
		//
		// "	             n := SHOW_ALERT('CONFIRM_XFER');"+
		// "	             if n = 88 then    /* if operator selected YES (alert_button1) then */"+
		//
		// "	                MOVE_COMPONENTS( NVL(:component_nbr1,0),"+
		// "	                                 NVL(:component_nbr2,0),"+
		// "	                                 NVL(:component_nbr3,0),"+
		// "	                                 NVL(:component_nbr4,0),"+
		// "	                                 NVL(:component_nbr5,0),"+
		// "	                                 NVL(:component_nbr6,0),"+
		// "	                                 NVL(:component_nbr7,0),"+
		// "	                                 NVL(:component_nbr8,0),"+
		// "	                                 :from_username,"+
		// "	                                 :to_username,"+
		// "	                                 :location_id );"+
		// "	                :XFR_BLOCK.COMPONENT_NBR1 := NULL;"+
		// "	                :XFR_BLOCK.COMPONENT_NBR2 := NULL;"+
		// "                       :XFR_BLOCK.component_nbr2 := NULL;"+
		// "	                :XFR_BLOCK.COMPONENT_NBR3 := NULL;"+
		// "	                :XFR_BLOCK.COMPONENT_NBR4 := NULL;"+
		// "	                :XFR_BLOCK.COMPONENT_NBR5 := NULL;"+
		// "	                :XFR_BLOCK.COMPONENT_NBR6 := NULL;"+
		// "	                :XFR_BLOCK.COMPONENT_NBR7 := NULL;"+
		// "	                :XFR_BLOCK.COMPONENT_NBR8 := NULL;"+
		// "	                go_item('COMPONENT_NBR1');"+
		// "	                msg_alert('All The Components Have Successfully Been Transferred !!!','I',FALSE); "+
		//
		// "	             else"+
		//
		// "	                go_item('COMPONENT_NBR1');"+
		//
		// "	             end if;"+
		//
		// "	          end if;"+
		// "	       end if;"+
		// "	    end if;"+
		// "	    end;"
		// String
		// strCode="SELECT IT_toUNIT UNIT, un_desc,&#10;I.COMP_CODE COMP_CODE,&#10;I.IT_CODE IT_CODE,&#10;I.IT_RUNO IT_RUNO&#10;&#10;FROM NIS.ITMASTUN I, nis.unmast u&#10;WHERE I.COMP_CODE = :GLOBAL.COMP&#10;AND IT_CODE =  :prdmast.txd_item&#10;and (i.comp_code = u.comp_code and i.it_tounit = u.un_code)  &#10;union&#10;SELECT IT_UNIT UNIT, un_desc,&#10;I.COMP_CODE COMP_CODE,&#10;I.IT_CODE IT_CODE,&#10;I.IT_RUNO IT_RUNO&#10;FROM NIS.ITMASTUN I, nis.unmast u&#10;WHERE I.COMP_CODE = :GLOBAL.COMP&#10;AND IT_CODE =  :prdmast.txd_item&#10;and (i.comp_code = u.comp_code and i.it_unit = u.un_code)";

		String strCode = "IF :parameter.p_user_id IS NOT NULL THEN\r\n"
				+ "\r\n"
				+ " IF :parameter.p_type_user = 'USER' THEN\r\n"
				+ "  :mso_user.user_id := USER;\r\n"
				+ "  IF :parameter.p_user_id != USER THEN\r\n"
				+ "    mso_error('(pre-query) You may only view your personal account information.');\r\n"
				+ "  END IF;\r\n"
				+ " ELSIF :parameter.p_type_user = 'SUPV' THEN\r\n"
				+ "    :mso_user.user_id := :parameter.p_user_id;\r\n"
				+ " ELSIF :parameter.p_type_user = 'DDM' THEN\r\n"
				+ "    :mso_user.user_id := :parameter.p_user_id;\r\n"
				+ " ELSE\r\n"
				+ "    :mso_user.user_id := :parameter.p_user_id;\r\n"
				+ " END IF;\r\n" + "\r\n" + "END IF;";
		ProcessedCode code = codeProcessor.process(strCode);

		System.out.println(code.getCode());
		int i = 1;

		for (Variable variable : code.getVariables()) {
			if (i < code.getVariables().size()) {
				System.out.println(variable.getReplacedName() + " "
						+ variable.getType() + " VARCHAR2,");
			} else {
				System.out.println(variable.getReplacedName() + " "
						+ variable.getType() + " VARCHAR2");
			}
			i++;
		}
	}
}
