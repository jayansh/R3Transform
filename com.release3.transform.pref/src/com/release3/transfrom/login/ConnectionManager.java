package com.release3.transfrom.login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.release3.dbconnect.DBCommand;
import com.release3.dbconnect.DBConnector;
import com.release3.transform.pref.PreferenceConstants;
import com.release3.transform.pref.PreferencePlugin;

public class ConnectionManager {

	private String dbHost;
	private String dbPort;
	private String dbServiceORsid;

	private String dbDriver;

	private String dbUsername;
	private String dbPassword;

	private static ConnectionManager connectionManager;
	private Connection conn;

	private ConnectionManager() {
		dbHost = PreferencePlugin.getDefault().getPreferenceStore()
				.getString(PreferenceConstants.HOST);
		dbServiceORsid = PreferencePlugin.getDefault().getPreferenceStore()
				.getString(PreferenceConstants.SID_SERVICE);

		dbPort = PreferencePlugin.getDefault().getPreferenceStore()
				.getString(PreferenceConstants.PORT);
		dbUsername = PreferencePlugin.getDefault().getPreferenceStore()
				.getString(PreferenceConstants.DB_USERNAME);
		dbPassword = PreferencePlugin.getDefault().getPreferenceStore()
				.getString(PreferenceConstants.DB_PASSWORD);
		dbDriver = PreferencePlugin.getDefault().getPreferenceStore()
				.getString(PreferenceConstants.DATABASE);
	}

	public static ConnectionManager getConnectionManager() {
		if (connectionManager != null) {
			return connectionManager;
		} else {
			connectionManager = new ConnectionManager();
			return connectionManager;
		}

	}

	public String getJDBCUrl(String host, int port, String database) {
		final String JDBC_PATH = "jdbc:oracle:thin:@";

		if (database.startsWith("(")) {
			return JDBC_PATH + database;
		} else if (database.startsWith("/")) {
			return JDBC_PATH + "//" + host + ":" + port + database;
		} else {
			return JDBC_PATH + host + ":" + port + ":" + database;
		}
	}

	public Connection getConnection() throws SQLException {
		if (conn != null) {
			return conn;
		} else {
//			 DriverManager.registerDriver(new
//			 oracle.jdbc.driver.OracleDriver());
			 // @machineName:port:SID, userid, password
//			 conn = DriverManager.getConnection(getJDBCUrl(dbHost, Integer
//			 .parseInt(dbPort), dbServiceORsid), dbUsername, dbPassword);
//			 conn.setAutoCommit(false);
			DBConnector connector = new DBConnector(dbDriver,getJDBCUrl(dbHost, Integer
					 .parseInt(dbPort), dbServiceORsid), dbUsername, dbPassword);
			try {
				conn =  connector.getConnection();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return conn;
		}
	}

}
