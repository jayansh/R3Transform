package com.release3.tf.pll.model;

import java.io.File;

public class MigrationPLL {
	private String name;
	private String status;
	private File pllFile;

	protected MigrationPLL(File pllFile, String status, String pllName) {
		this.pllFile = pllFile;
		this.name = pllName;
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public File getPllFile() {
		return pllFile;
	}

	public void setPllFile(File pllFile) {
		this.pllFile = pllFile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
