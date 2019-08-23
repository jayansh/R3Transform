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
