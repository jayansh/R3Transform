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
