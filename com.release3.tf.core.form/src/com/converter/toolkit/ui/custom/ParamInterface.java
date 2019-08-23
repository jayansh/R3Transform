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

public interface ParamInterface {

	public void addParameter(Parameter param);

	public List getParameter();

	public void setParameters(Vector<Parameter> param);

	public void addCanvasSwitchParameter(CanvasSwitchParameter param);

	public List getCanvasSwitchParameters();

	public void setCanvasSwitchParameters(Vector<CanvasSwitchParameter> param);

}
