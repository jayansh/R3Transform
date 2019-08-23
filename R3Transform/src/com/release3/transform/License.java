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
package com.release3.transform;

import java.util.List;

import com.converter.premigration.UserInfo;
/**
 * Deprecated - use com.release3.tf.license.License instead from plugin com.release3.tf.License
 */
@Deprecated
public class License {
	private String licenseeName;
	private String productName;
	// features, in our case would be oracle form
	private List<OracleForm> forms;
	private UserInfo userInfo;

	public License(String licenseeName, String productName,
			List<OracleForm> forms,UserInfo userInfo) {
		this.licenseeName = licenseeName;
		this.productName = productName;
		this.forms = forms;
		this.userInfo=userInfo;
	}

	public String getLicenseeName() {
		return licenseeName;
	}

	public void setLicenseeName(String licenseeName) {
		this.licenseeName = licenseeName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public List<OracleForm> getForms() {
		return forms;
	}

	public void setForms(List<OracleForm> forms) {
		this.forms = forms;
	}
	
	public void putForm(OracleForm form){
		forms.add(form);
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

}
