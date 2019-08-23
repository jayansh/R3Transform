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
package com.release3.transfrom.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.converter.premigration.UserInfo;
import com.release3.transform.pref.PreferenceConstants;
import com.release3.transform.pref.PreferencePlugin;

public class PCLogin {
	public static String clientId;

	public static void clientIdLookup() {
		clientId = PreferencePlugin.getDefault().getPreferenceStore()
				.getString(PreferenceConstants.APPLICATION_CLIENT_ID);
	}

	public static boolean isUserExist(String clientId, String clientKey) {
		try {
			Connection conn = ConnectionManager.getConnectionManager()
					.getConnection();
			// Statement object for issuing SQL statements
			Statement stmt = conn.createStatement();
			String loginQuery = "SELECT client_id, client_key FROM users WHERE UPPER (client_id)='"
					+ clientId.toUpperCase()
					+ "' and client_key = '"
					+ clientKey + "'";

			ResultSet loginDetails = stmt.executeQuery(loginQuery);
			if (loginDetails.next()) {

				// Activator.getDefault().getPreferenceStore().setValue(Constants.USER_PREF,
				// clientId);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;

		}
		return false;
	}

	public static void insert(UserInfo userinfo) throws SQLException {

		String selectQuery = "select count(*) from CLIENTS";

		Connection conn = ConnectionManager.getConnectionManager()
				.getConnection();
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery(selectQuery);
		int clientId = 0;
		if (rs.next()) {
			clientId = rs.getInt(1);
		}

		String insertQuery = "insert into CLIENTS(CLIENT_ID,CLIENT_NAME, ADDRESS1,ADDRESS2,"
				+ " CITY,STATE,COUNTRY,PHONE_NO,EMAIL,ZIPCODE,STATUS ) values ("
				+ clientId
				+ ",'"
				+ userinfo.getOrganization()
				+ "','"
				+ userinfo.getAddress1()
				+ "','"
				+ userinfo.getAddress2()
				+ "','"
				+ userinfo.getLocation()
				+ "','"
				+ userinfo.getState()
				+ "','"
				+ userinfo.getCountry()
				+ "','"
				+ userinfo.getPhone()
				+ "','"
				+ userinfo.getEmail()
				+ "','"
				+ userinfo.getZipcode()
				+ "'," + userinfo.getStatus() + " )";
		System.out.println(insertQuery);

		stat.executeUpdate(insertQuery);
		conn.commit();

		// organization,organizationUnit,location,state,country,email,phone,issueDate
	}

	public static void updateClientName(UserInfo userinfo) throws SQLException {

		Connection conn = ConnectionManager.getConnectionManager()
				.getConnection();

		String updateQuery = "update CLIENTS SET CLIENT_NAME = ? WHERE CLIENT_ID = ?";
		PreparedStatement updateStat = conn.prepareStatement(updateQuery);
		updateStat.setString(1, userinfo.getCommanName());
		updateStat.setInt(2, userinfo.getClientId());

		System.out.println(updateQuery);

		updateStat.executeUpdate(updateQuery);
		conn.commit();

	}

	public static void updateAddress1(UserInfo userinfo) throws SQLException {

		Connection conn = ConnectionManager.getConnectionManager()
				.getConnection();

		String updateQuery = "update CLIENTS SET Address1 = ? WHERE CLIENT_ID = ?";
		PreparedStatement updateStat = conn.prepareStatement(updateQuery);
		updateStat.setString(1, userinfo.getLocation());
		updateStat.setInt(2, userinfo.getClientId());

		System.out.println(updateQuery);

		updateStat.executeUpdate(updateQuery);
		conn.commit();

	}

	public static void updateAddress2(UserInfo userinfo) throws SQLException {

		Connection conn = ConnectionManager.getConnectionManager()
				.getConnection();

		String updateQuery = "update CLIENTS SET Address2 = ? WHERE CLIENT_ID = ?";
		PreparedStatement updateStat = conn.prepareStatement(updateQuery);
		updateStat.setString(1, userinfo.getAddress2());
		updateStat.setInt(2, userinfo.getClientId());

		System.out.println(updateQuery);

		updateStat.executeUpdate(updateQuery);
		conn.commit();

	}

	public static void updateLocation(UserInfo userinfo) throws SQLException {

		Connection conn = ConnectionManager.getConnectionManager()
				.getConnection();

		String updateQuery = "update CLIENTS SET CITY = ? WHERE CLIENT_ID = ?";
		PreparedStatement updateStat = conn.prepareStatement(updateQuery);
		updateStat.setString(1, userinfo.getLocation());
		updateStat.setInt(2, userinfo.getClientId());

		System.out.println(updateQuery);

		updateStat.executeUpdate(updateQuery);
		conn.commit();

	}

	public static void updateState(UserInfo userinfo) throws SQLException {

		Connection conn = ConnectionManager.getConnectionManager()
				.getConnection();

		String updateQuery = "update CLIENTS SET state = ? WHERE CLIENT_ID = ?";
		PreparedStatement updateStat = conn.prepareStatement(updateQuery);
		updateStat.setString(1, userinfo.getState());
		updateStat.setInt(2, userinfo.getClientId());

		System.out.println(updateQuery);

		updateStat.executeUpdate(updateQuery);
		conn.commit();

	}

	public static void updateCountry(UserInfo userinfo) throws SQLException {

		Connection conn = ConnectionManager.getConnectionManager()
				.getConnection();

		String updateQuery = "update CLIENTS SET country = ? WHERE CLIENT_ID = ?";
		PreparedStatement updateStat = conn.prepareStatement(updateQuery);
		updateStat.setString(1, userinfo.getCountry());
		updateStat.setInt(2, userinfo.getClientId());

		System.out.println(updateQuery);

		updateStat.executeUpdate(updateQuery);
		conn.commit();

	}

	public static void updatePhone(UserInfo userinfo) throws SQLException {

		Connection conn = ConnectionManager.getConnectionManager()
				.getConnection();

		String updateQuery = "update CLIENTS SET phone = ? WHERE CLIENT_ID = ?";
		PreparedStatement updateStat = conn.prepareStatement(updateQuery);
		updateStat.setString(1, userinfo.getPhone());
		updateStat.setInt(2, userinfo.getClientId());

		System.out.println(updateQuery);

		updateStat.executeUpdate(updateQuery);
		conn.commit();

	}

	public static void updateEmail(UserInfo userinfo) throws SQLException {

		Connection conn = ConnectionManager.getConnectionManager()
				.getConnection();

		String updateQuery = "update CLIENTS SET email = ? WHERE CLIENT_ID = ?";
		PreparedStatement updateStat = conn.prepareStatement(updateQuery);
		updateStat.setString(1, userinfo.getEmail());
		updateStat.setInt(2, userinfo.getClientId());

		System.out.println(updateQuery);

		updateStat.executeUpdate(updateQuery);
		conn.commit();

	}

	public static void updateZipcode(UserInfo userinfo) throws SQLException {

		Connection conn = ConnectionManager.getConnectionManager()
				.getConnection();

		String updateQuery = "update CLIENTS SET zipcode = ? WHERE CLIENT_ID = ?";
		PreparedStatement updateStat = conn.prepareStatement(updateQuery);
		updateStat.setString(1, userinfo.getZipcode());
		updateStat.setInt(2, userinfo.getClientId());

		System.out.println(updateQuery);

		updateStat.executeUpdate(updateQuery);
		conn.commit();

	}

	public static void updateStatus(UserInfo userinfo) throws SQLException {

		Connection conn = ConnectionManager.getConnectionManager()
				.getConnection();

		String updateQuery = "update CLIENTS SET status = ? WHERE CLIENT_ID = ?";
		PreparedStatement updateStat = conn.prepareStatement(updateQuery);
		updateStat.setInt(1, userinfo.getStatus());
		updateStat.setInt(2, userinfo.getClientId());

		System.out.println(updateQuery);

		updateStat.executeUpdate(updateQuery);
		conn.commit();

	}

	public static void updateOrganizationName(UserInfo userinfo)
			throws SQLException {

		Connection conn = ConnectionManager.getConnectionManager()
				.getConnection();

		String updateQuery = "update CLIENTS SET CLIENT_NAME = ? WHERE CLIENT_ID = ?";
		PreparedStatement updateStat = conn.prepareStatement(updateQuery);
		updateStat.setString(1, userinfo.getCommanName());
		updateStat.setInt(2, userinfo.getClientId());

		System.out.println(updateQuery);

		updateStat.executeUpdate(updateQuery);
		conn.commit();

	}

}
