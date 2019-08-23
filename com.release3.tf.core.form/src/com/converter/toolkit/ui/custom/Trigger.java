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

public class Trigger implements ParamInterface {
	private String block;
	private String item;
	private String jsfattr;
	private String plsql;
	private String methodType;
	private String javaMethod;
	private Vector<Parameter> parameterList = new Vector<Parameter>();
	private Vector<CanvasSwitchParameter> canvasSwitchParameters = new Vector<CanvasSwitchParameter>();
	private Vector<Refresh> refresh = new Vector<Refresh>();

	public Trigger(String block, String item, String jsfattr) {
		this.block = block;
		this.item = item;
		this.jsfattr = jsfattr;
	}

	public Trigger() {
	}

	public void setParameters(Vector<Parameter> paramVector) {
		this.parameterList = paramVector;
	}

	public void addRefresh(Refresh rf) {
		refresh.add(rf);
	}

	public List<Refresh> getRefresh() {
		return refresh;
	}

	public void setRefresh(Vector<Refresh> refresh) {
		this.refresh = refresh;
	}

	public void addParameter(Parameter param) {
		parameterList.add((Parameter) param);
	}

	public List getParameter() {
		return parameterList;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public String getBlock() {
		return block;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getItem() {
		return item;
	}

	public void setJsfattr(String jsfattr) {
		this.jsfattr = jsfattr;
	}

	public String getJsfattr() {
		return jsfattr;
	}

	public void setPlsql(String plsql) {
		this.plsql = plsql;
	}

	public String getPlsql() {
		return plsql;
	}

	public void setMethodType(String methodType) {
		this.methodType = methodType;
	}

	public String getMethodType() {
		return methodType;
	}

	public void addCanvasSwitchParameter(
			CanvasSwitchParameter canvasSwitchParameter) {
		canvasSwitchParameters
				.add((CanvasSwitchParameter) canvasSwitchParameter);
	}

	public Vector<CanvasSwitchParameter> getCanvasSwitchParameters() {
		return canvasSwitchParameters;
	}

	public void setCanvasSwitchParameters(
			Vector<CanvasSwitchParameter> canvasSwitchParameterList) {
		this.canvasSwitchParameters = canvasSwitchParameterList;
	}

	public String getJavaMethod() {
		return javaMethod;
	}

	public void setJavaMethod(String javaMethod) {
		this.javaMethod = javaMethod;
	}

}
