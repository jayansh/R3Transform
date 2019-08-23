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
package com.release3.tf.triggerlist.model;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.release3.transform.pref.PreferencePlugin;
import com.release3.triggerlist.SupportedTriggerType;

public class SupportedTriggerListLabelProvider extends LabelProvider implements
		ITableLabelProvider {
	// We use icons
	private static final Image CHECKED = PreferencePlugin.getImageDescriptor(
			"icons/checked.gif").createImage();
	private static final Image UNCHECKED = PreferencePlugin.getImageDescriptor(
			"icons/unchecked.gif").createImage();

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		SupportedTriggerType triggerType = (SupportedTriggerType) element;
		switch (columnIndex) {
		case 1:
			Boolean migrateYN = triggerType.isMigrate();
			if (migrateYN == null) {
				return UNCHECKED;
			} else if (migrateYN) {
				return CHECKED;
			} else {
				return UNCHECKED;
			}

		}
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		SupportedTriggerType triggerType = (SupportedTriggerType) element;
		switch (columnIndex) {
		case 0:
			String triggerName = triggerType.getName();
			return triggerName;
		case 1:
			Boolean migrateYN = triggerType.isMigrate();
			System.out.println("migrateYN:: " + migrateYN);
			if (migrateYN == null) {
				return "false";
			} else {
				return migrateYN.toString();
			}

			// default:
			// throw new RuntimeException("Should not happen");
		}
		return null;
	}

}
