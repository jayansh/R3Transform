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

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class AnalysisResultDetailsContentProvider implements IStructuredContentProvider {

	@Override
	public Object[] getElements(Object inputElement) {
		@SuppressWarnings("unchecked")
		List<AnalysisResultDetail> detail = (List<AnalysisResultDetail>) inputElement;
		return detail.toArray();

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub

	}

}
