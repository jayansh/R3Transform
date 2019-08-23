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
package com.release3.tf.analysis.ui;

import com.release3.tf.analysis.AnalysisResult;

public class AnalysisResultModelProvider {
	private String resultSummary;
	private AnalysisResult result;
	private static AnalysisResultModelProvider content;

	private AnalysisResultModelProvider() {
		resultSummary = "";
	}

	public static AnalysisResultModelProvider getInstance() {
		if (content != null) {
			return content;
		} else {
			content = new AnalysisResultModelProvider();
			return content;
		}
	}

	public String getResultSummary() {
		return resultSummary;
	}

	public void setResultSummary(String result) {
		this.resultSummary = result;
	}

	public AnalysisResult getResult() {
		return result;
	}

	public void setResult(AnalysisResult result) {
		this.result = result;
	}

	
}
