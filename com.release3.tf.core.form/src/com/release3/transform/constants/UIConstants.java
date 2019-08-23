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
package com.release3.transform.constants;

public class UIConstants {
	public static String BLOCK_TRIGGER_COMBO_OPTIONS[] = { "Init", "postQuery",
			"postInsert", "postUpdate", "postDelete", "preQuery", "preInsert",
			"preUpdate", "preDelete" };
	public static String ITEM_TRIGGER_COMBO_OPTIONS[] = { "actionListener",
			"valueChangeListener",
//			"preForm",
//			"validator", 
			"whenValidateItem",			"postChange" };
	public static String TRIGGER_CUSTMIZATION_COMBO_OPTIONS[] = { "plsqlCall",
			"javaCall", "forceQuery", "objectSetter", "nextRecord",
			"prevRecord", "newRecord", "saveRecord", "Remove", "searchRecord",
			"queryRecord", "canvasSwitcher", //"itemValidator",
			"clearBlock","none" };
	public static String TRIGGER_PARAMETER_TYPE_LABELS[] = { "inject",
			"depend", "output", "outLocal", "inLocal" };
	public static String TRIGGER_PARAMETER_TYPE_VALUES[] = { "in", "depend",
			"out", "outLocal", "inLocal", };
	public static String WORKSPACE_SETTINGS_OPTIONS[] = {
			"JBoss Studio+JBoss Server", "MyEclipse+IBM Websphere",
			"Eclipse+Oracle WebLogic" };
	/** "WHEN-VALIDATE-ITEM", removed */
	public static String[] TRIGGER_PLSQL = { "POST-QUERY", "POST-INSERT",
			"POST-UPDATE", "POST-DELETE", "PRE-QUERY", "PRE-INSERT",
			"PRE-UPDATE", "PRE-DELETE", "WHEN-NEW-FORM-INSTANCE" };

	/**
	 * Receive a triggerName which may be any one listed in TRIGGER_PLSQL.
	 * return index no if exist in array TRIGGER_PLSQL, otherwise return -1.
	 * 
	 * @param triggerName
	 * @return the index of triggerName
	 */
	public static int getPlSqlTriggerIndex(String triggerName) {
		for (int i = 0; i < TRIGGER_PLSQL.length; i++) {

			String name = TRIGGER_PLSQL[i];
			if (name == triggerName || name.equalsIgnoreCase(triggerName)) {
				return i;
			}
		}
		return -1;
	}
}
