package com.converter.premigration;

import java.io.File;

import org.eclipse.core.runtime.Platform;


public class UserInfoSettings {
	
	
	
	public static final String USERINFO_CONFIG =  "userinfo.properties";
	
	public static final String USERINFO_DB_URL = "UserInfo.DBURL";
	public static final String USERINFO_DB_DRIVER = "UserInfo.DRIVER";
	public static final String USERINFO_DB_USERNAME = "UserInfo.USERNAME";
	public static final String USERINFO_DB_PASSWORD = "UserInfo.PASSWORD";
	
	public static final String USERINFO_COMMONNAME = "UserInfo.CommanName";
	public static final String USERINFO_ORGANIZATION = "UserInfo.Organization";
	public static final String USERINFO_ORGANIZATION_UNIT = "UserInfo.OrganizationUnit";
	public static final String USERINFO_ADDRESS1 = "UserInfo.Address1";
	public static final String USERINFO_ADDRESS2 = "UserInfo.Address2";
	
	public static final String USERINFO_LOCATION = "UserInfo.Location";
	public static final String USERINFO_STATE = "UserInfo.State";
	public static final String USERINFO_COUNTRY = "UserInfo.Country";
	public static final String USERINFO_EMAIL = "UserInfo.Email";
	public static final String USERINFO_PHONE = "UserInfo.Phone";
	
	public static final String USERINFO_ZIPCODE = "UserInfo.Zipcode";
	public static final String USERINFO_STATUS = "UserInfo.Status";
	public static final File PROPERTIES_FILE = new File(Platform.getLocation() + File.separator
			+ UserInfoSettings.USERINFO_CONFIG);
	
}
