package com.release3.tf.analysis;

import java.util.ArrayList;
import java.util.List;

public class AnalysisResult {
	private String formName;
	private int totalBlocks;
	private int totalLOVs;
	private int totalCanvases;
	private int totalWindows;
	private int totalFormBasedTriggers;
	private int totalBlockBasedTriggers;
	private int totalItemBasedTriggers;
	private long formSize;
	private long pllSize;
	private long olbSize;

	private ArrayList<String> olbList;
	private ArrayList<String> formList;
	private ArrayList<String> missingLibs;
	private ArrayList<FormDependency> dependenciesList;

	private String complexity;
	private List<AnalysisResultDetail> detailList;

	public AnalysisResult() {
		detailList = new ArrayList<AnalysisResultDetail>();
		olbList = new ArrayList<String>();
		formList = new ArrayList<String>();
		missingLibs = new ArrayList<String>();
		dependenciesList = new ArrayList<FormDependency>();
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public int getTotalBlocks() {
		return totalBlocks;
	}

	public void setTotalBlocks(int totalBlocks) {
		this.totalBlocks = totalBlocks;
	}

	public int getTotalLOVs() {
		return totalLOVs;
	}

	public void setTotalLOVs(int totalLOVs) {
		this.totalLOVs = totalLOVs;
	}

	public int getTotalCanvases() {
		return totalCanvases;
	}

	public void setTotalCanvases(int totalCanvases) {
		this.totalCanvases = totalCanvases;
	}

	public int getTotalWindows() {
		return totalWindows;
	}

	public void setTotalWindows(int totalWindows) {
		this.totalWindows = totalWindows;
	}

	public int getTotalFormBasedTriggers() {
		return totalFormBasedTriggers;
	}

	public void setTotalFormBasedTriggers(int totalFormBasedTriggers) {
		this.totalFormBasedTriggers = totalFormBasedTriggers;
	}

	public int getTotalBlockBasedTriggers() {
		return totalBlockBasedTriggers;
	}

	public void setTotalBlockBasedTriggers(int totalBlockBasedTriggers) {
		this.totalBlockBasedTriggers = totalBlockBasedTriggers;
	}

	public int getTotalItemBasedTriggers() {
		return totalItemBasedTriggers;
	}

	public void setTotalItemBasedTriggers(int totalItemBasedTriggers) {
		this.totalItemBasedTriggers = totalItemBasedTriggers;
	}

	public long getFormSize() {
		return formSize;
	}

	public void setFormSize(long formSize) {
		this.formSize = formSize;
	}

	public long getPllSize() {
		return pllSize;
	}

	public void setPllSize(long pllSize) {
		this.pllSize = pllSize;
	}

	public long getOlbSize() {
		return olbSize;
	}

	public void setOlbSize(long olbSize) {
		this.olbSize = olbSize;
	}

	public String getComplexity() {
		return complexity;
	}

	public void setComplexity(String complexity) {
		this.complexity = complexity;
	}

	public List<AnalysisResultDetail> getDetailList() {
		return detailList;
	}

	public void addDetail(AnalysisResultDetail detail) {
		this.detailList.add(detail);
	}

	public ArrayList<String> getOlbList() {
		return olbList;
	}

	public ArrayList<String> getFormList() {
		return formList;
	}

	public ArrayList<String> getMissingLibs() {
		return missingLibs;
	}

	public ArrayList<FormDependency> getDependenciesList() {

		return dependenciesList;
	}

	public void setDependenciesList(ArrayList<FormDependency> dependenciesList) {
		this.dependenciesList = dependenciesList;
	}

	public void setMissingLibs(ArrayList<String> missingLibs) {
		this.missingLibs = missingLibs;
	}

}
