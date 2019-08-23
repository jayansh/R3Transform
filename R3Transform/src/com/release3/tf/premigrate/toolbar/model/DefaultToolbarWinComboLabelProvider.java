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
package com.release3.tf.premigrate.toolbar.model;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.oracle.xmlns.forms.Window;

public class DefaultToolbarWinComboLabelProvider  extends LabelProvider {
	public Image getImage(Object element) {
		return super.getImage(element);
	}

	public String getText(Object element) {
		Window window = (Window)element;
		return window.getName();
	}
}
