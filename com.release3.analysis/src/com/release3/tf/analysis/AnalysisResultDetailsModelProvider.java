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
