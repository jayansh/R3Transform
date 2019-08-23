package com.release3.tf.analysis;

import java.util.ArrayList;
import java.util.List;

public class AnalysisResultDetailsModelProvider {
	private List<AnalysisResultDetail> analysisResultDetailList;
	public List<AnalysisResultDetail> getAnalysisResultDetailList() {
		return analysisResultDetailList;
	}

	public void setAnalysisResultDetailList(
			List<AnalysisResultDetail> analysisResultDetailList) {
		this.analysisResultDetailList = analysisResultDetailList;
	}

	private static AnalysisResultDetailsModelProvider content;

	private AnalysisResultDetailsModelProvider() {
		analysisResultDetailList = new ArrayList<AnalysisResultDetail>();
	}

	public static AnalysisResultDetailsModelProvider getInstance() {
		if (content != null) {
			return content;
		} else {
			content = new AnalysisResultDetailsModelProvider();
			return content;
		}
	}

	

}
