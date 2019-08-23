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
package com.release3.tf.customiz.model.param;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.converter.toolkit.ui.custom.Parameter;
import com.converter.toolkit.ui.custom.Refresh;

public class ParameterLabelProvider extends LabelProvider implements
ITableLabelProvider {

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		Parameter param = (Parameter) element;
		switch (columnIndex) {
		case 0:
			return param.getBlock();
		case 1:
			return param.getItem();
		case 2:
			return param.getType();
		case 3:
			return param.getName();
		default:
			throw new RuntimeException("Should not happen");
		}
	}

}
