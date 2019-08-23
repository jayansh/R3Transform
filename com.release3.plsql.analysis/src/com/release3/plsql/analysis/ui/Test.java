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
package com.release3.plsql.analysis.ui;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String text = "if(SET_ITEMEMP.getJOB()== null || SET_ITEMEMP.getJOB() == &quot;SALESMAN&quot; || SET_ITEMEMP.getJOB().equalsIgnoreCase(&quot;SALESMAN&quot;)){"
				+ "return"
				+ " true;"
				+ "set_item_property(&quot;comm&quot;,visible,property_true);"
				+ "set_item_property(&quot;comm&quot;,enabled,property_false);"
				+ "}else{"
				+ " return"
				+ " false;"
				+ "set_item_property(&quot;comm&quot;,visible,property_false);"
				+ "}";
		Test test = new Test();
		test.migrateItemProperty(text);
	}

	public String migrateItemProperty(String text) {
		String compiledText = "";
		if (text != null) {
			text = text.toLowerCase();
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
						compiledText = compiledText
								+ newText.substring(0, endIndex +2) + "*/";
						newText = newText.substring(endIndex+2);
					} else {
						compiledText = compiledText + "*/";
					}
					System.out.println("Compiled Text::: "+compiledText);
					if(newText.length() > 0){
						compiledText = compiledText + migrateItemProperty(newText);
					}
					
				}
			}else
				if(text.length() > 0){
					compiledText = compiledText +text;
				}
		}
		
		System.out.println("Compiled Text::: "+compiledText);
		return compiledText;
	}

}
