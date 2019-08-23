package com.release3.tf.analysis;

import java.util.ArrayList;
import java.util.List;

public class AnalysisFormsDependModelProvider {
	private List<FormDependency> analysisDependenciesList;
	public List<FormDependency> getFormDependenciesList() {
		return analysisDependenciesList;
	}

	public void setFormDependenciesList(
			List<FormDependency> analysisDependenciesList) {
		this.analysisDependenciesList = analysisDependenciesList;
	}

	private static AnalysisFormsDependModelProvider content;

	private AnalysisFormsDependModelProvider() {
		analysisDependenciesList = new ArrayList<FormDependency>();
	}

	public static AnalysisFormsDependModelProvider getInstance() {
		if (content != null) {
			return content;
		} else {
			content = new AnalysisFormsDependModelProvider();
			return content;
		}
	}
}
