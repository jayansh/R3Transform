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

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class AnalysisResultDetailsLabelProvider extends LabelProvider implements
		ITableLabelProvider {

//	private static final Image CHECKED = Activator.getImageDescriptor(
//	"icons/checked.gif").createImage();
//	private static final Image UNCHECKED = Activator.getImageDescriptor(
//			"icons/unchecked.gif").createImage();


	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		// In case you don't like image just return null here
//		if (columnIndex == 1) {
//			if (((CleanupForm) element).isStatus()) {
//				return CHECKED;
//			} else {
//				return UNCHECKED;
//			}
//		}
		return null;

	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		AnalysisResultDetail detail = (AnalysisResultDetail) element;
		switch (columnIndex) {
		case 0:
			return detail.getElement();
		case 1:
			return detail.getIssue();
		case 2:
			return detail.getSuggestion();
		default:
			throw new RuntimeException("Should not happen");
		}

	}

}
