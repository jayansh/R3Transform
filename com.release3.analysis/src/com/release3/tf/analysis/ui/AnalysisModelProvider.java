package com.release3.tf.analysis.ui;

import java.util.ArrayList;
import java.util.List;

import com.release3.tf.migration.model.MigrationForm;

public class AnalysisModelProvider {
	private List<MigrationForm> formsList;
	private static AnalysisModelProvider content;

	private AnalysisModelProvider() {
		formsList = new ArrayList<MigrationForm>();
	}

	public static AnalysisModelProvider getInstance() {
		if (content != null) {
			return content;
		} else {
			content = new AnalysisModelProvider();
			return content;
		}
	}

	

	public List<MigrationForm> getFormList() {
		return formsList;
	}
}
