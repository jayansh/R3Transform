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
package com.converter.premigration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

public class UserInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5950583429260094982L;
	/**
	 * 
	 */
	private String dbURL;
	private String dbDriver;
	private String dbUser;
	private String dbPassword;

	private int clientId;
	private String commanName = "";// CN
	private String address1 = "";
	private String address2 = "";
	private String organization = "";// O
	private String organizationUnit = "";// OU
	private String location = "";// L
	private String state = "";// ST
	private String country = ""; // C
	private String email = "";
	private String phone = "";
	private String issueDate = "";
	private String expiryDate = "";
	private int validity = 45;

	private String zipcode = "";
	private int status = 2;

	
	private Properties props = new Properties();

	public UserInfo() {

		if (load()) {
			this.commanName = props
					.getProperty(UserInfoSettings.USERINFO_COMMONNAME);
			this.organization = props
					.getProperty(UserInfoSettings.USERINFO_ORGANIZATION);
			this.organizationUnit = props
					.getProperty(UserInfoSettings.USERINFO_ORGANIZATION_UNIT);
			this.address1 = props
					.getProperty(UserInfoSettings.USERINFO_ADDRESS1);
			this.address2 = props
					.getProperty(UserInfoSettings.USERINFO_ADDRESS2);
			this.location = props
					.getProperty(UserInfoSettings.USERINFO_LOCATION);
			this.state = props.getProperty(UserInfoSettings.USERINFO_STATE);
			this.country = props.getProperty(UserInfoSettings.USERINFO_COUNTRY);
			this.email = props.getProperty(UserInfoSettings.USERINFO_EMAIL);
			this.phone = props.getProperty(UserInfoSettings.USERINFO_PHONE);
			this.zipcode = props.getProperty(UserInfoSettings.USERINFO_ZIPCODE);
			this.status = Integer.parseInt(props
					.getProperty(UserInfoSettings.USERINFO_STATUS));
			Calendar cal = Calendar.getInstance();
			// this.issueDate = (new Date()).toString();
			this.issueDate = cal.getTime().toString();
			cal.add(Calendar.DATE, validity);
			this.expiryDate = cal.getTime().toString();

		}
	}

	public UserInfo(String commanName, String organization,
			String organizationUnit, String location, String state,
			String country, String email, String phone, String issueDate,
			String expiryDate,int validity) {
		this.commanName = commanName;
		this.organization = organization;
		this.organizationUnit = organizationUnit;
		this.location = location;
		this.state = state;
		this.country = country;
		this.email = email;
		this.phone = phone;
		this.issueDate = issueDate;
		this.expiryDate = expiryDate;
		this.validity = validity;
		
	}

	public boolean load() {
		try {
			// InputStream propFileInputStream = getClass().getResourceAsStream(
			// "/com/converter/premigration/userinfo.properties");
			// props.load(propFileInputStream);

			FileInputStream io = new FileInputStream(
					UserInfoSettings.PROPERTIES_FILE);
			props.load(io);
			return true;

		} catch (FileNotFoundException fNFEx) {
			if (!UserInfoSettings.PROPERTIES_FILE.getParentFile().exists()) {
				UserInfoSettings.PROPERTIES_FILE.getParentFile().mkdir();
			}
			try {
				UserInfoSettings.PROPERTIES_FILE.createNewFile();
				save();
				return false;
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return false;
	}

	public String getDbURL() {
		return dbURL;
	}

	public void setDbURL(String dbURL) {
		this.dbURL = dbURL;
	}

	public String getDbDriver() {
		return dbDriver;
	}

	public void setDbDriver(String dbDriver) {
		this.dbDriver = dbDriver;
	}

	public String getDbUser() {
		return dbUser;
	}

	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public void setCommanName(String commanName) {
		this.commanName = commanName;
	}

	public String getCommanName() {
		return commanName;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganizationUnit(String organizationUnit) {
		this.organizationUnit = organizationUnit;
	}

	public String getOrganizationUnit() {
		return organizationUnit;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLocation() {
		return location;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getState() {
		return state;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountry() {
		return country;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone() {
		return phone;
	}

	public void setIssueDate(String currentDate) {
		this.issueDate = currentDate;
	}

	public String getIssueDate() {
		return issueDate;
	}

	
	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	
	

	public int getValidity() {
		return validity;
	}

	public void setValidity(int validity) {
		this.validity = validity;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Properties load(String propsName) throws Exception {
		Properties props = new Properties();
		URL url = ClassLoader.getSystemResource(propsName);
		props.load(url.openStream());
		return props;
	}

	public void save() {
		try {

			FileOutputStream out = new FileOutputStream(
					UserInfoSettings.PROPERTIES_FILE);
			Properties prop = new Properties();
			prop.setProperty(UserInfoSettings.USERINFO_COMMONNAME, this
					.getCommanName());
			prop.setProperty(UserInfoSettings.USERINFO_ORGANIZATION, this
					.getOrganization());
			prop.setProperty(UserInfoSettings.USERINFO_ORGANIZATION_UNIT, this
					.getOrganizationUnit());
			prop.setProperty(UserInfoSettings.USERINFO_ADDRESS1, this
					.getAddress1());
			prop.setProperty(UserInfoSettings.USERINFO_ADDRESS2, this
					.getAddress2());
			prop.setProperty(UserInfoSettings.USERINFO_LOCATION, this
					.getLocation());
			prop.setProperty(UserInfoSettings.USERINFO_STATE, this.getState());
			prop.setProperty(UserInfoSettings.USERINFO_COUNTRY, this
					.getCountry());
			prop.setProperty(UserInfoSettings.USERINFO_EMAIL, this.getEmail());
			prop.setProperty(UserInfoSettings.USERINFO_PHONE, this.getPhone());
			prop.setProperty(UserInfoSettings.USERINFO_ZIPCODE, this
					.getZipcode());

			prop.setProperty(UserInfoSettings.USERINFO_STATUS, this.getStatus()
					+ "");

			prop.store(out, "UserInfo properties");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		UserInfo userinfo = new UserInfo();
	}

	public List<String> toList() {
		List<String> info = new ArrayList<String>();

		info.add("CommanName = " + commanName);
		info.add("Organization = " + organization);
		info.add("OrganizationUnit = " + organizationUnit);
		info.add("Location = " + location);
		info.add("State = " + state);
		info.add("Country = " + country);
		info.add("Email = " + email);
		info.add("Phone = " + phone);
		info.add("IssueDate = " + issueDate);
//		info.add("ExpiryDate = " + expiryDate);
//		info.add("Validity = " + validity);
		return info;
	}

}
