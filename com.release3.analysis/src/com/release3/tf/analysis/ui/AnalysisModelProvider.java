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
