package com.release3.cleanup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.tools.ant.Task;

public class CleanupAmpTask extends Task {

	private static final char CHAR_SEMICOLON = ';';

	private static final String REGEX = "&\\w+";

	private String srcFilePath;

	private String destFilePath;
	private static Logger logger = Logger.getLogger(CleanupAmpTask.class);

	public CleanupAmpTask() {

	}

	public CleanupAmpTask(String srcfilePath, String destFilePath) {
		this.srcFilePath = srcfilePath;
		this.destFilePath = destFilePath;
	}

	public void setSrcFilePath(String srcfilePath) {
		this.srcFilePath = srcfilePath;
	}

	public void setDestFilePath(String destFilePath) {
		this.destFilePath = destFilePath;
	}

	public static void main(String[] args) {
		CleanupAmpTask task = new CleanupAmpTask(
				"C:\\Cleanup\\specialChar\\old\\demo.xml",
				"C:\\Cleanup\\specialChar\\new\\demo1.xml");

		// task.setFileUrl("D:/workspace/regex/FACILITY_FORM.xml");
		// task.setFileUrl("C:/MyPrj/Forms2ICE/Forms2ICE/CleanupTasks/XML_Forms/MYFORM_1.xml");
		// task.setFileUrl("C:/transformtoolkit/TFWorkspace/ViewController/src/applicationname/MYFORM/MYFORM.xml");
		// task.setFileUrl("C:/transformtoolkit/TFWorkspace/ViewController/src/applicationname/DEPT_EMP/DEPT_EMP.xml");
		// task.setFileUrl("");
//		task.setSrcFilePath("");
		task.execute();
	}

	public void execute() {

		File srcFile = new File(srcFilePath);
		File destFile = new File(destFilePath);

		char[] data = getFileData(srcFile);
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
			if (fileString.charAt(matcher.end()) != CHAR_SEMICOLON) {
				startIndex = matcher.start() - countMods;
				stringBuilder.deleteCharAt(startIndex);
				countMods++;
			}
		}

		data = stringBuilder.toString().toCharArray();
		writeToFile(destFile, data);
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
