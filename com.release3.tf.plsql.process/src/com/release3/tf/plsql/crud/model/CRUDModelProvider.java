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
package com.release3.tf.plsql.crud.model;

import java.util.ArrayList;
import java.util.List;

import com.release3.r3coreplsqlgen.R3CorePlSql;

public class CRUDModelProvider {
	private List<R3CorePlSql> crudList;
	private static CRUDModelProvider content;

	private CRUDModelProvider() {
		crudList = new ArrayList<R3CorePlSql>();
	}

	public static CRUDModelProvider getInstance() {
		if (content != null) {
			return content;
		} else {
			content = new CRUDModelProvider();
			return content;
		}
	}

	public void setCRUDList(List<R3CorePlSql> crudList) {
		if (this.crudList == null) {
			this.crudList = new ArrayList<R3CorePlSql>();
		} else if (this.crudList.size() > 0) {
			this.crudList.addAll(crudList);
		} else {
			this.crudList = crudList;
		}

	}

	public List<R3CorePlSql> getCRUDList() {
		return crudList;
	}
}
