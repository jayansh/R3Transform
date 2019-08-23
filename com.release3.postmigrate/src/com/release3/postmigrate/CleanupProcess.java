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
package com.release3.postmigrate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CleanupProcess {

	private static final char CHAR_SEMICOLON = ';';

	//private static final String REGEX = "&\\w+";
		private static final String REGEX = "&amp;\\w";
	private String fileUrl;

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public void execute() {

		File file = new File(fileUrl);
		char[] data = getFileData(file);
		if (data == null) {
			return;
		}
		String fileString = new String(data);
		
		StringBuilder stringBuilder = new StringBuilder(fileString);

		Pattern pattern = Pattern.compile(REGEX);
		Matcher matcher = pattern.matcher(fileString);

		int startIndex = -1;
		int countMods = 0;
		while (matcher.find()) {
//			if (fileString.charAt(matcher.end()) != CHAR_SEMICOLON) {
				startIndex = matcher.start() - countMods;
				stringBuilder.deleteCharAt(startIndex);
				countMods++;
//			}
		}

		data = stringBuilder.toString().toCharArray();
		File newfile = new File(fileUrl.replaceAll(".xml","1.xml"));
		writeToFile(newfile, data);
	}

	private char[] getFileData(File file) {

		FileReader fr = null;
		char[] data = null;
		try {
			fr = new FileReader(file);

			data = new char[(int) file.length()];
			fr.read(data, 0, data.length);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fr != null) {
				try {
					fr.close();
				} catch (IOException ignore) {
				}
			}
		}
		return data;
	}

	private void writeToFile(File file, char[] data) {

		FileWriter fw = null;
		try {
			fw = new FileWriter(file);
			fw.write(data);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException ignore) {
				}
			}
		}
	}

}
