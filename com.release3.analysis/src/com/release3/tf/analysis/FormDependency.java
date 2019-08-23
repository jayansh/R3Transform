package com.release3.tf.analysis;

public class FormDependency {
	private String dependencyType;
	private String dependencyName;

	public FormDependency() {

	}

	public FormDependency(String dependencyName, String dependencyType) {
		this.dependencyName = dependencyName;
		this.dependencyType = dependencyType;
	}

	public String getDependencyType() {
		return dependencyType;
	}

	public void setDependencyType(String dependencyType) {
		this.dependencyType = dependencyType;
	}

	public String getDependencyName() {
		return dependencyName;
	}

	public void setDependencyName(String dependencyName) {
		this.dependencyName = dependencyName;
	}

}
