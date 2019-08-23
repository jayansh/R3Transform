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
package com.release3.dbconnect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.release3.dbconnect.DBConnector;
import com.release3.tf.core.form.Settings;

public class DBCommand {
	private static Connection conn;
	private static DBCommand dBCommand;
	private static DBConnector dbConnector = new DBConnector();

	private DBCommand() {

	}

	public static DBCommand getDBCommand() {
		if (dBCommand == null) {
			dBCommand = new DBCommand();
		}

		return dBCommand;
	}

	/**
	 * Create a view and returns its name
	 * 
	 * @param query
	 * @param tables
	 * @return view name
	 */
	public String createView(String query, String tables) {
		tables = tables.replace(" ", "");
		String viewName = tables.replace(",", "_") + "_VW";
		conn = getConnection();
		try {
			Statement stmt = conn.createStatement();
			String queryView = "CREATE OR REPLACE VIEW " + viewName + " AS ("
					+ query + " )";
			System.out.println(queryView);
			stmt.executeUpdate(queryView);
			stmt.close();
			// commit the transaction
			System.out.println("  COMMIT");
			conn.commit();
			return viewName;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * This method will return destination connection for Oracle and source
	 * otherwise.
	 * 
	 * @return a Connection object
	 */
	public Connection getTargetConnection() {
		Connection conn = null;
		String dbType = Settings.getSettings().getDatabaseType();
		if ("Oracle".equals(dbType)) {
			conn = getDestConnection();
		}else{
			conn = getConnection();
		}
		return conn;

	}

	public static Connection getConnection() {
		boolean settingModified = false;
		dbConnector.setDbDriver(Settings.getSettings().getDbSrcDriver());
		if (dbConnector.getDbURL() != null) {
			if (!dbConnector.getDbURL().equalsIgnoreCase(
					Settings.getSettings().getDbSrcUrl())
					&& dbConnector.getDbURL() != Settings.getSettings()
							.getDbSrcUrl()) {
				dbConnector.setDbURL(Settings.getSettings().getDbSrcUrl());
				settingModified = true;
			}
		}
		if (dbConnector.getUserName() != null) {
			if (!dbConnector.getUserName().equalsIgnoreCase(
					Settings.getSettings().getDbSrcUser())
					&& dbConnector.getUserName() != Settings.getSettings()
							.getDbSrcUser()) {
				dbConnector.setUserName(Settings.getSettings().getDbSrcUser());
				settingModified = true;
			}
		}
		if (dbConnector.getUserName() != null) {
			if (!dbConnector.getPassword().equalsIgnoreCase(
					Settings.getSettings().getDbSrcPassword())
					&& dbConnector.getPassword() != Settings.getSettings()
							.getDbSrcPassword()) {
				dbConnector.setPassword(Settings.getSettings()
						.getDbSrcPassword());
				settingModified = true;
			}
		}

		try {
			if (conn == null || conn.isClosed() || settingModified) {
				dbConnector.setDbURL(Settings.getSettings().getDbSrcUrl());
				dbConnector.setUserName(Settings.getSettings().getDbSrcUser());
				dbConnector.setPassword(Settings.getSettings()
						.getDbSrcPassword());
				conn = dbConnector.getConnection();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// dbConnector.setDbURL(Settings.getSettings().getDbSrcUrl());
		// dbConnector.setUserName(Settings.getSettings().getDbSrcUser());
		// dbConnector.setPassword(Settings.getSettings().getDbSrcPassword());
		// conn = dbConnector.getConnection();
		return conn;
	}

	public static Connection getDestConnection() {
		boolean settingModified = false;
		dbConnector.setDbDriver(Settings.getSettings().getDbDestDriver());
		if (dbConnector.getDbURL() != null) {
			if (!dbConnector.getDbURL().equalsIgnoreCase(
					Settings.getSettings().getDbDestUrl())
					&& dbConnector.getDbURL() != Settings.getSettings()
							.getDbDestUrl()) {
				dbConnector.setDbURL(Settings.getSettings().getDbDestUrl());
				settingModified = true;
			}
		}
		if (dbConnector.getUserName() != null) {
			if (!dbConnector.getUserName().equalsIgnoreCase(
					Settings.getSettings().getDbDestUser())
					&& dbConnector.getUserName() != Settings.getSettings()
							.getDbDestUser()) {
				dbConnector.setUserName(Settings.getSettings().getDbDestUser());
				settingModified = true;
			}
		}
		if (dbConnector.getUserName() != null) {
			if (!dbConnector.getPassword().equalsIgnoreCase(
					Settings.getSettings().getDbDestPassword())
					&& dbConnector.getPassword() != Settings.getSettings()
							.getDbDestPassword()) {
				dbConnector.setPassword(Settings.getSettings()
						.getDbDestPassword());
				settingModified = true;
			}
		}

		try {
			if (conn == null || conn.isClosed() || settingModified) {
				dbConnector.setDbURL(Settings.getSettings().getDbDestUrl());
				dbConnector.setUserName(Settings.getSettings().getDbDestUser());
				dbConnector.setPassword(Settings.getSettings()
						.getDbDestPassword());
				conn = dbConnector.getConnection();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// dbConnector.setDbURL(Settings.getSettings().getDbSrcUrl());
		// dbConnector.setUserName(Settings.getSettings().getDbSrcUser());
		// dbConnector.setPassword(Settings.getSettings().getDbSrcPassword());
		// conn = dbConnector.getConnection();
		return conn;
	}

	public String getQualifiedColumnName(String tables, String columnName) {
		String[] tableArray = tables.split(",");
		for (String tableName : tableArray) {
			tableName = tableName.trim();
			String[] array = tableName.split(" ");
			String alias = "";
			if (array.length > 0) {
				tableName = array[0];

			}
			if (array.length > 1) {
				alias = array[1];
			}

			String query = "SELECT COLUMN_NAME FROM USER_TAB_COLUMNS WHERE TABLE_NAME = '"
					+ tableName.toUpperCase()
					+ "' and column_name='"
					+ columnName.toUpperCase() + "'";
			conn = getConnection();
			try {
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				if (rs.next()) {
					if (alias.length() > -1) {
						columnName = alias + "." + columnName;
					}
					return columnName;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return columnName;
	}

	public void createNonDBBlockTable() {
		int noOfFields = 90;
		String fieldDataType = "VARCHAR2(1024)";
		String fields = null;
		for (int i = 1; i <= noOfFields; i++) {
			if (fields == null) {
				fields = "FIELD" + i + " " + fieldDataType + ",\n";
			} else {
				fields = fields + "FIELD" + i + " " + fieldDataType + ",\n";
			}
		}

		String dropNonDB = "DROP TABLE NONDBBLOCK";

		String queryNonDB = "CREATE TABLE NONDBBLOCK \n (   REC_ID     NUMBER(15),\n"
				+ "SESSION_ID VARCHAR2(1024),\n"
				+ "BLOCK_ID VARCHAR2(1024),\n"
				+ "FORM_ID VARCHAR2(1024),\n"
				+ "INDEX_ID NUMBER(15),\n"
				+ fields
				+ "CONSTRAINT \"PK_REC_IDT\" PRIMARY KEY (\"REC_ID\") ENABLE)";
		conn = getConnection();
		try {
			Statement stmt = conn.createStatement();
			System.out.println("Creating Table NonDBBlock in source database.");
			stmt.executeUpdate(queryNonDB);
			System.out.println("NonDBBlock Table Created.");
			stmt.close();
			// commit the transaction
			System.out.println("  COMMIT");
			conn.commit();
		} catch (SQLException e) {
			if (e.getErrorCode() == 955) {
				System.out.println("NonDBBlock Table already exist.");
			}
			System.out.println(e.getMessage());
		}
	}

	public void createRecIdSequence() {
		String querySeq = "CREATE SEQUENCE REC_ID_SEQ INCREMENT BY 1";
		try {
			conn = getConnection();
			Statement stmt = conn.createStatement();
			System.out
					.println("Creating Sequence REC_ID_SEQ in source database.");
			stmt.executeUpdate(querySeq);
			System.out.println("REC_ID_SEQ Sequence Created.");
			stmt.close();
			// commit the transaction
			System.out.println("  COMMIT");
			conn.commit();
		} catch (SQLException e) {
			if (e.getErrorCode() == 955) {
				System.out.println("REC_ID_SEQ Sequence already exist.");
			}
			System.out.println(e.getMessage());
		}
	}

	public List<String> getColumnList(String tableName) {
		String query = "SELECT COLUMN_NAME FROM USER_CONS_COLUMNS UCC WHERE   "
				+ "UCC.CONSTRAINT_NAME=(            "
				+ "SELECT CONSTRAINT_NAME FROM USER_CONSTRAINTS UC WHERE "
				+ "UPPER(UC.TABLE_NAME)=UPPER('" + tableName
				+ "') AND UC.CONSTRAINT_TYPE='P'    )";
		System.out.println(query);
		List<String> columnList = new ArrayList<String>();
		try {
			conn = getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				String columnName = (String) rs.getObject("COLUMN_NAME");
				columnList.add(columnName);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return columnList;
	}

	/**
	 * create table/sequence/package/function/procedure
	 * 
	 * 
	 * @throws SQLException
	 */
	public void runCreateCommand(String command) throws SQLException {
		System.out.println(command);
		conn = getTargetConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(command);
		stmt.close();
		conn.commit();

	}

	public String getSourceDatabaseVersion() {
		String query = "select * from v$version where banner like 'Oracle%'";
		try {
			conn = getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			if (rs.next()) {
				String dbVersion = rs.getString("BANNER");
				return dbVersion;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return null;
	}
}
