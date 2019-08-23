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
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;

public class DBConnector {
	private String dbDriver;
	private String dbURL;
	private String userName;
	private String password;

	public DBConnector() {
		Locale.setDefault(Locale.ENGLISH);
	}

	public DBConnector(String dbDriver, String dbURL, String userName,
			String password) {
		Locale.setDefault(Locale.ENGLISH);
		this.dbDriver = dbDriver;
		this.dbURL = dbURL;
		this.userName = userName;
		this.password = password;
	}

	public String getDbDriver() {
		return dbDriver;
	}

	public void setDbDriver(String dbDriver) {
		this.dbDriver = dbDriver;
	}

	public String getDbURL() {
		return dbURL;
	}

	public void setDbURL(String dbURL) {
		this.dbURL = dbURL;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean testConnection() throws ClassNotFoundException, SQLException {
		Class.forName(dbDriver);
		Connection conn = DriverManager
				.getConnection(dbURL, userName, password);
		if (conn != null) {
			conn.close();
			conn = null;
			return true;
		}

		return false;

	}

	public Connection getConnection() throws ClassNotFoundException,
			SQLException {
		Class.forName(dbDriver);
		Connection conn = DriverManager
				.getConnection(dbURL, userName, password);
		return conn;
	}
}
