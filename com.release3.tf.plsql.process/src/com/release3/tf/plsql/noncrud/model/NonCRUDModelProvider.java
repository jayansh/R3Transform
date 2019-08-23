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
package com.release3.tf.plsql.noncrud.model;

import java.util.ArrayList;
import java.util.List;

import com.release3.javasql.JavaPlSqlType;
import com.release3.tf.plsql.process.NonCRUDAnalysis;

public class NonCRUDModelProvider {
	private List<JavaPlSqlType> crudList;
	private static NonCRUDModelProvider content;
	private NonCRUDAnalysis nonCRUDAnalysis;

	private NonCRUDModelProvider() {
		crudList = new ArrayList<JavaPlSqlType>();
	}

	public static NonCRUDModelProvider getInstance() {
		if (content != null) {
			return content;
		} else {
			content = new NonCRUDModelProvider();
			return content;
		}
	}

	public void setNonCRUDAnalysis(NonCRUDAnalysis nonCRUDAnalysis) {
		this.nonCRUDAnalysis = nonCRUDAnalysis;
	}

	public NonCRUDAnalysis getNonCRUDAnalysis() {
		return nonCRUDAnalysis;
	}

	public List<JavaPlSqlType> getNonCRUDList() {
		if (nonCRUDAnalysis != null) {
			return nonCRUDAnalysis.getNonCrudList();
		}
		return crudList;
	}
}
