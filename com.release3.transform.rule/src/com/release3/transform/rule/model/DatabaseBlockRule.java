package com.release3.transform.rule.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatabaseBlockRule")
@Deprecated
public class DatabaseBlockRule extends Rules {
  
	private boolean isDBBlockType;
  
	private String queryDataSourceType;

	private boolean hasQueryDataSourceName;
	
	
	private String result;
	
	@XmlElement()
	public boolean isDBBlockType() {
		return isDBBlockType;
	}

	public void setDBBlockType(boolean isDBBlockType) {
		this.isDBBlockType = isDBBlockType;
	}

	@XmlElement()
	public String getQueryDataSourceType() {
		return queryDataSourceType;
	}

	public void setQueryDataSourceType(String queryDataSourceType) {
		this.queryDataSourceType = queryDataSourceType;
	}

	@XmlElement()
	public boolean isHasQueryDataSourceName() {
		return hasQueryDataSourceName;
	}

	public void setHasQueryDataSourceName(boolean hasQueryDataSourceName) {
		this.hasQueryDataSourceName = hasQueryDataSourceName;
	}

	@XmlElement()
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
