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
package com.release3.transform.pref;

public class PreferenceConstants {
	private static final String ID = PreferencePlugin.PLUGIN_ID
			+ ".preferences.plugin";

	public static String HOST = ID + ".db_host";
	public static String DATABASE = ID + ".db_database";
	public static String PORT = ID + ".db_port";
	public static String SID_SERVICE = ID + ".db_sid_servicename";
	public static String DB_USERNAME = ID + ".db_username";
	public static String DB_PASSWORD = ID + ".db_password";
	public static String APPLICATION_CLIENT_ID = ID + ".app_client_id";
	public static String APPLICATION_CLIENT_KEY = ID + ".app_client_key";

	public static String APPLICATION_CLIENT_COMMANNAME = ID
			+ ".UserInfo.CommanName";// CN
	public static String APPLICATION_CLIENT_ADDRESS1 = ID
			+ ".UserInfo.Address1";
	public static String APPLICATION_CLIENT_ADDRESS2 = ID
			+ ".UserInfo.Address2";
	public static String APPLICATION_CLIENT_ORGANIZATION = ID
			+ ".UserInfo.Organization";// O
	public static String APPLICATION_CLIENT_ORGANIZATIONUNIT = ID
			+ ".UserInfo.OrganizationUnit";// OU
	public static String APPLICATION_CLIENT_LOCATION = ID
			+ ".UserInfo.Location";// L
	public static String APPLICATION_CLIENT_STATE = ID + ".UserInfo.State";// ST
	public static String APPLICATION_CLIENT_COUNTRY = ID + ".UserInfo.Country"; // C
	public static String APPLICATION_CLIENT_EMAIL = ID + ".UserInfo.Email";
	public static String APPLICATION_CLIENT_PHONE = ID + ".UserInfo.Phone";
	public static String APPLICATION_CLIENT_ISSUEDATE = ID
			+ ".UserInfo.IssueDate";

	public static String APPLICATION_CLIENT_ZIPCODE = ID + ".UserInfo.ZipCode";
	public static String APPLICATION_CLIENT_STATUS = ID + ".UserInfo.Status";
	public static String APPLICATION_MIGRATE_CRUD_TO_JAVA = ID
			+ ".migrate.crudtojava";
	public static String APPLICATION_SQLWAYS_HOME = ID + ".sqlways.home";
	public static String APPLICATION_SQLWAYS_USE_ISPIRER_SQLWAYS = ID + ".sqlways.useIspirerSqlWays";
	public static String APPLICATION_SQLWAYS_OVERWRITE_FUNCTION_MAP_XML = ID + ".sqlways.overwriteFunctionMapXml";
	public static String APPLICATION_SQLWAYS_OVERWRITE_ITEM_VARIABLE_XML = ID + ".sqlways.overwriteItemVariableXml";
	// public static final String APPLICATION_MIGRATE_PU_TO_JAVA = ID
	// + ".migrate.programunittojava";
}
