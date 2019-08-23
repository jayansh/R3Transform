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
package com.release3.tf.core;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.StringTokenizer;

import com.cinnabarsystems.clm.LicenseManagerException;
import com.cinnabarsystems.clm.LicenseUtility;
import com.converter.premigration.ProductInfo;
import com.converter.premigration.UserInfo;

public class Utils {

	private Hashtable<String, List> licenseTable = new Hashtable<String, List>();

	public static synchronized long copyFile(File in, File out)
			throws IOException {
		long noOfBytes = 0;
		if (!out.getParentFile().exists()) {
			out.getParentFile().mkdirs();
		}
		if (in.length() > 80000000000L) {
			System.out.println("New IO");
			noOfBytes = useNewIO(in, out);
		} else {
			System.out.println("Normal IO");
			noOfBytes = useNormalIO(in, out);
		}

		System.out.println(noOfBytes + " No. of bytes copied");
		return noOfBytes;
	}

	private static synchronized long useNewIO(File in, File out)
			throws IOException {
		FileChannel inChannel = new FileInputStream(in).getChannel();
		FileChannel outChannel = new FileOutputStream(out).getChannel();
		try {
			long noOfBytes = inChannel.transferTo(0, inChannel.size(),
					outChannel);
			System.out.println(noOfBytes + " No. of bytes copied");
			return noOfBytes;
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		} finally {
			if (inChannel != null)
				inChannel.close();
			if (outChannel != null)
				outChannel.close();

		}
	}

	private static synchronized long useNormalIO(File in, File out)
			throws IOException {
		// long time1 = System.currentTimeMillis();
		InputStream is = new FileInputStream(in);

		if (!out.exists()) {
			out.createNewFile();
		}

		FileOutputStream fos = new FileOutputStream(out);
		byte[] buf = new byte[8 * 1024];
		long countByte = 0;
		int len = 0;
		while ((len = is.read(buf)) != -1) {
			fos.write(buf, 0, len);
			countByte = countByte + len;
		}
		fos.flush();
		fos.close();
		is.close();
		// long time2 = System.currentTimeMillis();
		// System.out.println("Time taken: " + (time2 - time1) + " ms");
		return countByte;
	}

	@Deprecated
	public static License readLicense(File licenseFile) throws IOException {
		License license = null;

		InputStream myLicenseStream = new FileInputStream(licenseFile);
		String tmpDir = System.getProperty("java.io.tmpdir");
		File tmpLicFile = new File(tmpDir + File.separator + "templic.txt");
		if (!tmpLicFile.exists()) {
			tmpLicFile.createNewFile();
		}
		PrintStream ps = new PrintStream(tmpLicFile);
		try {
			LicenseUtility.dumpLicense(myLicenseStream, ps);
		} catch (LicenseManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FileInputStream fis = new FileInputStream(tmpLicFile);
		DataInputStream in = new DataInputStream(fis);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine = null;
		int lineNo = 0;

		String productName = "";
		String licenseeName = "";
		String formName = "";
		long formSize = 0;
		String commonName = null;
		String organization = null;
		String organizationUnit = null;
		String location = null;
		String state = null;
		String country = null;
		String email = null;
		String phone = null;
		String issueDate = null;
		String expiryDate = null;

		HashMap<String, String> blockTable = new HashMap<String, String>();

		while ((strLine = br.readLine()) != null) {
			// Print the content on the console
			System.out.println(strLine);
			lineNo++;

			List<String> tokens = getTokens(strLine, ",");
			if (tokens.size() > 1) {
				List<String> subTokens = getTokens(tokens.get(1), "=");
				if (subTokens.size() > 1) {
					if (subTokens.get(0).indexOf("product name") > -1) {
						productName = subTokens.get(1);
					}
				}
				if (tokens.get(0).indexOf("licensee") > -1) {
					licenseeName = getTokens(tokens.get(1), "=").get(1);
				}
				if (tokens.get(0).indexOf("feature") > -1) {
					if (subTokens.get(0).indexOf("name") > -1) {
						formName = subTokens.get(1);
					}

				}
				if (tokens.get(0).indexOf("attribute") > -1) {

					if (tokens.size() > 2) {
						List<String> subTokens2 = getTokens(tokens.get(2), "=");
						String name = subTokens.get(1);
						String value = subTokens2.get(1);
						if (name == "size" || name.equals("size")) {
							formSize = Long.parseLong(value);
							OracleForm form = new OracleForm(formName,
									blockTable, formSize);
							UserInfo userInfo = new UserInfo(commonName,
									organization, organizationUnit, location,
									state, country, email, phone, issueDate,
									expiryDate, 45);
							if (license == null) {
								List<OracleForm> forms = new ArrayList<OracleForm>();
								forms.add(form);
								license = new License(licenseeName,
										productName, forms, userInfo);
								formName = "";
							} else {
								license.putForm(form);
								formName = "";
							}
							license.setLicensePath(licenseFile.getPath());
							license.setLicenseeName(licenseFile.getName());
							blockTable.clear();

						} else if (name == "CommonName"
								|| name.equals("CommonName")) {
							commonName = value;
						} else if (name == "OrganizationUnit"
								|| name.equals("OrganizationUnit")) {
							organizationUnit = value;
						} else if (name == "Organization"
								|| name.equals("Organization")) {
							organization = value;
						} else if (name == "Location"
								|| name.equals("Location")) {
							location = value;
						} else if (name == "State" || name.equals("State")) {
							state = value;
						} else if (name == "Country" || name.equals("Country")) {
							country = value;
						} else if (name == "IssueDate"
								|| name.equals("IssueDate")) {
							issueDate = value;
						} else if (name == "ExpiryDate"
								|| name.equals("ExpiryDate")) {
							expiryDate = value;
						} else if (name == "Phone" || name.equals("Phone")) {
							phone = value;
						} else if (name == "Email" || name.equals("Email")) {
							email = value;
						}

						else {
							if (name.indexOf("block") > -1) {
								blockTable.put(name, value);
							}

						}
					}
				}
			}

		}

		// Close the input stream
		in.close();

		return license;
	}

	public static License readLicenseFS(File licenseFile) throws IOException {
		License license = null;

		InputStream myLicenseStream = new FileInputStream(licenseFile);
		String tmpDir = System.getProperty("java.io.tmpdir");
		File tmpLicFile = new File(tmpDir + File.separator + "templic.txt");
		if (!tmpLicFile.exists()) {
			tmpLicFile.createNewFile();
		}
		PrintStream ps = new PrintStream(tmpLicFile);
		try {
			LicenseUtility.dumpLicense(myLicenseStream, ps);
		} catch (LicenseManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FileInputStream fis = new FileInputStream(tmpLicFile);
		DataInputStream in = new DataInputStream(fis);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine = null;
		int lineNo = 0;

		String productName = "";
		ProductInfo productInfo = new ProductInfo();
		String licenseeName = "";
		String commonName = null;
		String organization = null;
		String organizationUnit = null;
		String location = null;
		String state = null;
		String country = null;
		String email = null;
		String phone = null;
		String issueDate = null;
		String expiryDate = null;
		String feature = null;

		HashMap<String, String> blockTable = new HashMap<String, String>();

		while ((strLine = br.readLine()) != null) {
			// Print the content on the console
			System.out.println(strLine);
			lineNo++;

			List<String> tokens = getTokens(strLine, ",");
			if (tokens.size() > 1) {
				List<String> subTokens = getTokens(tokens.get(1), "=");
				if (subTokens.size() > 1) {
					if (subTokens.get(0).indexOf("product name") > -1) {
						productName = subTokens.get(1);
						productInfo.setProductName(productName);
					}
				}
				if (tokens.get(0).indexOf("licensee") > -1) {
					licenseeName = getTokens(tokens.get(1), "=").get(1);
					if (license != null) {
						license.setLicenseeName(licenseeName);
					}
				}
				if (tokens.get(0).indexOf("feature") > -1) {
					if (subTokens.get(0).indexOf("name") > -1) {
						feature = subTokens.get(1);
					}
					if (feature.equals("r3transform")) {
						UserInfo userInfo = new UserInfo(commonName,
								organization, organizationUnit, location,
								state, country, email, phone, issueDate,
								expiryDate, 45);
						if (license == null) {
							license = new License(licenseeName, productInfo,
									userInfo);
							license.setProductName(productName);
						}
						license.setLicensePath(licenseFile.getPath());

						blockTable.clear();
					}

				}
				if (tokens.get(0).indexOf("attribute") > -1) {

					if (tokens.size() > 2) {
						List<String> subTokens2 = getTokens(tokens.get(2), "=");
						String name = subTokens.get(1);
						String value = subTokens2.get(1);
						if (name == "CommonName" || name.equals("CommonName")) {
							commonName = value;
						} else if (name == "OrganizationUnit"
								|| name.equals("OrganizationUnit")) {
							organizationUnit = value;
						} else if (name == "Organization"
								|| name.equals("Organization")) {
							organization = value;
						} else if (name == "Location"
								|| name.equals("Location")) {
							location = value;
						} else if (name == "State" || name.equals("State")) {
							state = value;
						} else if (name == "Country" || name.equals("Country")) {
							country = value;
						} else if (name == "IssueDate"
								|| name.equals("IssueDate")) {
							issueDate = value;
						} else if (name == "ExpiryDate"
								|| name.equals("ExpiryDate")) {
							expiryDate = value;
						} else if (name == "Phone" || name.equals("Phone")) {
							phone = value;
						} else if (name == "Email" || name.equals("Email")) {
							email = value;
						} else if (name == "version" || name.equals("version")) {
							productInfo.setProductVersion(value);
						} else {
							if (name.indexOf("block") > -1) {
								blockTable.put(name, value);
							}

						}
					}
				}
			}

		}

		// Close the input stream
		in.close();

		return license;
	}

	public static List<String> getTokens(String strLine, String delim) {
		List<String> tokens = new ArrayList<String>();
		StringTokenizer strTokenizer = new StringTokenizer(strLine, delim);
		while (strTokenizer.hasMoreTokens()) {
			String token = strTokenizer.nextToken().trim();
			tokens.add(token);
		}
		return tokens;
	}

	public static boolean makeDirs(File dir) {
		if (!dir.exists()) {
			return dir.mkdirs();
		}
		return false;
	}

	public static boolean makeParentDirs(File file) {
		File dir = file.getParentFile();
		return makeDirs(dir);
	}

	public static void copy(InputStream inputStream,
			FileOutputStream fileOutputStream) throws IOException {
		// TODO Auto-generated method stub
		byte[] buf = new byte[8192];
		while (true) {
			int length = inputStream.read(buf);
			if (length < 0)
				break;
			fileOutputStream.write(buf, 0, length);
		}

		try {
			inputStream.close();
		} catch (IOException ignore) {
		}
		try {
			fileOutputStream.close();
		} catch (IOException ignore) {
		}
	}

	/**
	 * Only for strings contains in Non-Crud Triggers like WHEN-BUTTON-PRESSED,
	 * ON-CLEAR-DETAILS. (containing char '-') The char next to '-' will be
	 * appear in upper case, char '-' will be removed. (containing char '.') The
	 * char next to '.' will be appear in upper case, char '.' will be removed.
	 * 
	 * @param name
	 */
	public static String getJavaName(String name) {
		char[] splitters = { '-', '.' };
		String javaName = getJavaName(name, splitters);
		return javaName;
	}

	public static String getJavaName(String name, char[] splitters) {
		for (char splitter : splitters) {
			int index = name.indexOf(splitter);
			if (index > 0) {
				String oldSplitterStr = String.valueOf(splitter);
				String newSplitterStr = String.valueOf('-');
				name = name.replace(oldSplitterStr, newSplitterStr);

			}
		}
		int index = name.indexOf('-');

		StringBuilder javaName = new StringBuilder();
		if (index > 0) {
			String splitterStr = "\\" + String.valueOf('-');
			String[] javaStrArr = name.split(splitterStr);

			for (int i = 0; i < javaStrArr.length; i++) {
				String javaSubStr = javaStrArr[i];
				if (i == 0) {
					javaName.append(javaSubStr.toLowerCase());
				} else {
					javaName.append(
							Character.toUpperCase(javaStrArr[i].charAt(0)))
							.append(javaStrArr[i].substring(1).toLowerCase());

				}
			}

		}
		if (javaName.length() == 0) {
			return name;
		} else {
			return javaName.toString();
		}
	}

	public static String getPlSqlName(String name) {
		char[] splitters = { '-', '.' };
		String plsqlName = getPlSqlName(name, splitters);
		return plsqlName;
	}

	public static String getPlSqlName(String name, char[] splitters) {
		for (char splitter : splitters) {
			int index = name.indexOf(splitter);
			if (index > 0) {
				String oldSplitterStr = String.valueOf(splitter);
				String newSplitterStr = String.valueOf('-');
				name = name.replace(oldSplitterStr, newSplitterStr);

			}
		}
		int index = name.indexOf('-');

		String plsqlName = new String();
		if (index > 0) {
			plsqlName = name.replace('-', '_');

		}
		if (plsqlName.length() == 0) {
			return name;
		} else {
			return plsqlName.toString();
		}
	}
}
