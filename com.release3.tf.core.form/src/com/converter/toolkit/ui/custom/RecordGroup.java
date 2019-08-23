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
package com.converter.toolkit.ui.custom;

import java.util.List;
import java.util.Vector;

public class RecordGroup implements ParamInterface {
	private String table;
	private String name;
	private String schema;
	private Boolean componentOrderBy;
	public Vector<Parameter> parameter = new Vector<Parameter>();

	public RecordGroup() {
	}

	public RecordGroup(String name) {
		this.name = name;
	}

	public void setParameters(Vector param) {
		this.parameter = param;
	}

	public void addParameter(Parameter param) {
		parameter.add((Parameter) param);
	}

	public List getParameter() {
		return parameter;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getTable() {
		return table;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getSchema() {
		return schema;
	}

	public void setComponentOrderBy(Boolean componentOrderBy) {
		this.componentOrderBy = componentOrderBy;
	}

	public Boolean getComponentOrderBy() {
		return componentOrderBy;
	}

	@Override
	public List getCanvasSwitchParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCanvasSwitchParameters(Vector<CanvasSwitchParameter> param) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addCanvasSwitchParameter(CanvasSwitchParameter param) {
		// TODO Auto-generated method stub
		
	}
}
