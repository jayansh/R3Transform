package com.release3.tf.core.form;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;
import com.release3.transform.constants.Environment;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;

import com.release3.transform.constants.UIConstants;

public class Settings {

	private String fmbFile = "";
	private String applicationName = "applicationname";
	// project directory
	private String baseDir = "";
	private String oracleDeveloperSuite = "";
	private String dbSrcUrl = "jdbc:oracle:thin:@localhost:1521:ORCL";
	private String dbSrcDriver = "oracle.jdbc.driver.OracleDriver";
	private String dbSrcUser = "HR";
	private String dbSrcPassword = "HR";
	private String dbDestUrl = "jdbc:edb://localhost:5444/edb?searchpath=hr";
	private String dbDestDriver = "com.edb.Driver";
	private String dbDestUser = "enterprisedb";
	private String dbDestPassword = "enterprisedb";
	private String workspaceSettings = UIConstants.WORKSPACE_SETTINGS_OPTIONS[0];
	private Boolean jbossPortalSupport = false;
	// Transform Toolkit home path. The path where toolkit is installed.
	private String transformHome;
	private String databaseType="Oracle";

	public String getDatabaseType() {
		return databaseType;
	}

	public void setDatabaseType(String databaseType) {
		this.databaseType = databaseType;
	}

	private String dbFormConnectionSID = "ORCL";// @ORCL or @KMA or @XE

	// form directory
	private String fmbRootDir = "";

	private File propFile;

	private Settings() {

		propFile = new File(Platform.getLocation() + File.separator
				+ Environment.SAVED_CONFIG);
		load();
	}

	private static Settings settings;

	public static Settings getSettings() {
		if (settings != null) {
			return settings;
		} else {
			settings = new Settings();
			return settings;
		}
	}

	public String getTransformHome() {
//		this.transformHome = System.getProperty("eclipse.home.location");
		this.transformHome = Platform.getInstallLocation().getURL().getPath();
//		this.transformHome = "C:\\Release3\\R3Transform_10June2013";
		System.out.println("Transform Home :"+this.transformHome );
		return transformHome;
	}

	public void setFmbFile(String fmbFile) {
		this.fmbFile = fmbFile;
	}

	public String getFmbFile() {
		return fmbFile;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setBaseDir(String baseDir) {
		this.baseDir = baseDir;
	}

	public String getBaseDir() {
		return baseDir;
	}

	// public String getOracleDeveloperSuite() {
	// return oracleDeveloperSuite;
	// }

	public void setDbSrcUrl(String dbSrcUrl) {
		this.dbSrcUrl = dbSrcUrl;
	}

	public String getDbSrcUrl() {
		return dbSrcUrl;
	}

	public void setDbSrcDriver(String dbSrcDriver) {
		this.dbSrcDriver = dbSrcDriver;
	}

	public String getDbSrcDriver() {
		return dbSrcDriver;
	}

	public void setDbSrcUser(String dbUser) {
		this.dbSrcUser = dbUser;
	}

	public String getDbSrcUser() {
		return dbSrcUser;
	}

	public void setDbSrcPassword(String dbPassword) {
		this.dbSrcPassword = dbPassword;
	}

	public String getDbSrcPassword() {
		return dbSrcPassword;
	}

	public String getDbDestUrl() {
		return dbDestUrl;
	}

	public void setDbDestUrl(String dbDestUrl) {
		this.dbDestUrl = dbDestUrl;
	}

	public String getDbDestDriver() {
		return dbDestDriver;
	}

	public void setDbDestDriver(String dbDestDriver) {
		this.dbDestDriver = dbDestDriver;
	}

	public String getDbDestUser() {
		return dbDestUser;
	}

	public void setDbDestUser(String dbDestUser) {
		this.dbDestUser = dbDestUser;
	}

	public String getDbDestPassword() {
		return dbDestPassword;
	}

	public void setDbDestPassword(String dbDestPassword) {
		this.dbDestPassword = dbDestPassword;
	}

	public String getWorkspaceSettings() {
		return workspaceSettings;
	}

	public void setWorkspaceSettings(String workspaceSettings) {
		this.workspaceSettings = workspaceSettings;
	}

	public void write(String dir) {
		try {
			File buildProp = new File(dir + File.separator
					+ Environment.TOOL_PROPERTY_FILE);
			PrintStream ps = new PrintStream(buildProp);
			ps.println(Environment.PROPERTY_TAG_FMBFILE + "="
					+ this.getFmbFile());
			ps.println(Environment.PROPERTY_TAG_APPLICATIONNAME + "="
					+ this.getApplicationName());
			ps.println(Environment.PROPERTY_TAG_FMBROOTDIR + "="
					+ this.getFmbRootDir().replace("\\", "\\\\"));
			ps.println(Environment.PROPERTY_TAG_BASEDIR + "="
					+ this.getBaseDir().replace("\\", "\\\\"));
			ps.println(Environment.PROPERTY_TAG_ORACLEDEVELOPERSUITE + "="
					+ this.getOracleDeveloperSuite().replace("\\", "\\\\"));
			ps.println(Environment.PROPERTY_TAG_DBURL + "="
					+ this.getDbSrcUrl());
			ps.println(Environment.PROPERTY_TAG_DBDRIVER + "="
					+ this.dbSrcDriver);
			ps.println(Environment.PROPERTY_TAG_DBUSER + "="
					+ this.getDbSrcUser());
			ps.println(Environment.PROPERTY_TAG_DBPASSWORD + "="
					+ this.getDbSrcPassword());

			ps.println(Environment.PROPERTY_TAG_DEST_DBURL + "="
					+ this.getDbDestUrl());
			ps.println(Environment.PROPERTY_TAG_DEST_DBDRIVER + "="
					+ this.dbDestDriver);
			ps.println(Environment.PROPERTY_TAG_DEST_DBUSER + "="
					+ this.getDbDestUser());
			ps.println(Environment.PROPERTY_TAG_DEST_DBPASSWORD + "="
					+ this.getDbDestPassword());
			ps.println(Environment.PROPERTY_TAG_WORKSPACE_SETTINGS + "="
					+ this.getWorkspaceSettings());
			ps.println(Environment.PROPERTY_TAG_JBOSS_PORTAL_SUPPORT + "="
					+ this.isJbossPortalSupport().toString());

			ps.flush();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void load() {
		try {

			FileInputStream io = new FileInputStream(propFile);
			Properties prop = new Properties();
			prop.load(io);
			this.setApplicationName(prop.getProperty(
					Environment.PROPERTY_TAG_APPLICATIONNAME, applicationName));
			this.setBaseDir(prop.getProperty(Environment.PROPERTY_TAG_BASEDIR,
					baseDir));
			this.setFmbRootDir(prop.getProperty(
					Environment.PROPERTY_TAG_FMBROOTDIR, fmbRootDir));
			this.setOracleDeveloperSuite(prop.getProperty(
					Environment.PROPERTY_TAG_ORACLEDEVELOPERSUITE,
					oracleDeveloperSuite));

			this.setDbSrcDriver(prop.getProperty(
					Environment.PROPERTY_TAG_DBDRIVER, dbSrcDriver));
			this.setDbSrcUrl(prop.getProperty(Environment.PROPERTY_TAG_DBURL,
					dbSrcUrl));
			this.setDbSrcUser(prop.getProperty(Environment.PROPERTY_TAG_DBUSER,
					dbSrcUser));
			this.setDbSrcPassword(prop.getProperty(
					Environment.PROPERTY_TAG_DBPASSWORD, dbSrcPassword));

			this.setDbDestDriver(prop.getProperty(
					Environment.PROPERTY_TAG_DEST_DBDRIVER, dbDestDriver));
			this.setDbDestUrl(prop.getProperty(
					Environment.PROPERTY_TAG_DEST_DBURL, dbDestUrl));
			this.setDbDestUser(prop.getProperty(
					Environment.PROPERTY_TAG_DEST_DBUSER, dbDestUser));
			this.setDbDestPassword(prop.getProperty(
					Environment.PROPERTY_TAG_DEST_DBPASSWORD, dbDestPassword));
			this.setWorkspaceSettings(prop.getProperty(
					Environment.PROPERTY_TAG_WORKSPACE_SETTINGS,
					workspaceSettings));

			this.setDbFormConnectionSID(prop.getProperty(
					Environment.PROPERTY_TAG_SID_OR_SERVICE_NAME,
					dbFormConnectionSID));

			if (prop.getProperty(Environment.PROPERTY_TAG_JBOSS_PORTAL_SUPPORT) != null) {
				this.setJbossPortalSupport(new Boolean(
						prop.getProperty(Environment.PROPERTY_TAG_JBOSS_PORTAL_SUPPORT)));
			}
			// if (prop.getProperty(Environment.PROPERTY_TAG_TRANSFORM_HOME) !=
			// null) {
			// this.transformHome = prop
			// .getProperty(Environment.PROPERTY_TAG_TRANSFORM_HOME);
			// } else {
			// this.transformHome = System
			// .getProperty("eclipse.home.location");
			// }

		} catch (FileNotFoundException e) {
			if (!propFile.getParentFile().exists()) {
				propFile.getParentFile().mkdir();
			}
			try {
				propFile.createNewFile();
				save();

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void save() {
		try {

			FileOutputStream out = new FileOutputStream(propFile);
			Properties prop = new Properties();
			prop.setProperty(Environment.PROPERTY_TAG_APPLICATIONNAME,
					this.getApplicationName());

			prop.setProperty(Environment.PROPERTY_TAG_BASEDIR,
					this.getBaseDir());
			prop.setProperty(Environment.PROPERTY_TAG_FMBROOTDIR,
					this.getFmbRootDir());
			prop.setProperty(Environment.PROPERTY_TAG_ORACLEDEVELOPERSUITE,
					this.getOracleDeveloperSuite());

			prop.setProperty(Environment.PROPERTY_TAG_DBURL, this.getDbSrcUrl());
			prop.setProperty(Environment.PROPERTY_TAG_DBDRIVER,
					this.getDbSrcDriver());
			prop.setProperty(Environment.PROPERTY_TAG_DBUSER,
					this.getDbSrcUser());
			prop.setProperty(Environment.PROPERTY_TAG_DBPASSWORD,
					this.getDbSrcPassword());

			prop.setProperty(Environment.PROPERTY_TAG_DEST_DBURL,
					this.getDbDestUrl());
			prop.setProperty(Environment.PROPERTY_TAG_DEST_DBDRIVER,
					this.getDbDestDriver());
			prop.setProperty(Environment.PROPERTY_TAG_DEST_DBUSER,
					this.getDbDestUser());
			prop.setProperty(Environment.PROPERTY_TAG_DEST_DBPASSWORD,
					this.getDbDestPassword());
			prop.setProperty(Environment.PROPERTY_TAG_WORKSPACE_SETTINGS,
					this.getWorkspaceSettings());
			prop.setProperty(Environment.PROPERTY_TAG_SID_OR_SERVICE_NAME,
					this.getDbFormConnectionSID());
			prop.setProperty(Environment.PROPERTY_TAG_JBOSS_PORTAL_SUPPORT,
					this.isJbossPortalSupport().toString());
			prop.setProperty(Environment.PROPERTY_TAG_TRANSFORM_HOME,
					this.getTransformHome());
			prop.store(out, "User properties");
			// System.out.println(this.getFMBRootDir().length());
			if (this.getFmbRootDir() != null
					&& this.getFmbRootDir().length() != 0) {
				write(this.getFmbRootDir());
			} else {
				this.setFmbRootDir("C:\\transformtoolkit\\SampleForms");
				write(this.getFmbRootDir());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setFmbRootDir(String fMBFileDir) {
		this.fmbRootDir = fMBFileDir;
	}

	public String getFmbRootDir() {
		return fmbRootDir;
	}

	public String getOracleDeveloperSuite() {
		return oracleDeveloperSuite;
	}

	public void setOracleDeveloperSuite(String oracleDeveloperSuite) {
		this.oracleDeveloperSuite = oracleDeveloperSuite;
	}

	public String getDbFormConnectionSID() {
		return dbFormConnectionSID;
	}

	public void setDbFormConnectionSID(String dbFormConnectionSID) {
		this.dbFormConnectionSID = dbFormConnectionSID;
	}

	public Boolean isJbossPortalSupport() {
		return jbossPortalSupport;
	}

	public void setJbossPortalSupport(Boolean jbossPortalSupport) {
		this.jbossPortalSupport = jbossPortalSupport;
	}

	public int indexOf(String[] strArray, String strValue) {
		int index = 0;
		for (String string : strArray) {
			if (string.equals(strValue) || string == strValue) {
				return index;
			}
			index++;
		}
		return -1;
	}

}
