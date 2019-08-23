package com.release3.plsql.analysis.property.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FormPropertiesFile extends File {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7542081079967560343L;

	public FormPropertiesFile(String pathname) {
		super(pathname);

	}

	public FormPropertiesFile(File file) {
		super(file.getAbsolutePath());
	}

	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {

		this.text = text;
		try {
			writeTextToPhysicalFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void writeTextToPhysicalFile() throws IOException {
		if (text != null) {
			FileWriter fileW = new FileWriter(this);
			fileW.write(text);
			fileW.close();
		}
	}
}
