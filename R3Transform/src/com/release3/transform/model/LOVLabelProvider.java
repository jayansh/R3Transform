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
package com.release3.transform.model;

import java.util.Properties;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.release3.transform.constants.PremigrationConstants;
import com.release3.transform.lov.LOVRecordGroupModelList;
import com.release3.transform.pref.PropertiesReadWrite;

public class LOVLabelProvider extends LabelProvider implements
		ITableLabelProvider {

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		LOVRecordGroupModelList.LOVRecordGroupModel lovModel = (LOVRecordGroupModelList.LOVRecordGroupModel) element;
		Properties properties = PropertiesReadWrite.getPropertiesReadWrite()
				.getProperties();

		switch (columnIndex) {
		case 0:

			return lovModel.getLOV().getName();
		case 1:
			if (PremigrationConstants.UsePreviousCleanupFormPref) {
				if (properties.containsKey("LOVRecordGroupModel."
						+ lovModel.getLOV().getName() + "."
						+ PremigrationConstants.LOV_RG_TITLES[columnIndex])) {
					lovModel.setTableName(properties
							.getProperty("LOVRecordGroupModel."
									+ lovModel.getLOV().getName()
									+ "."
									+ PremigrationConstants.LOV_RG_TITLES[columnIndex]));

				}
			}
			if (lovModel.getTableName().indexOf(",") > -1) {
				lovModel.setContainsMultipleTable(true);
			} else if (lovModel.isContainsMultipleTable()) {
				lovModel.setContainsMultipleTable(false);
			}
			return lovModel.getTableName();

		case 2:
			return lovModel.getRecordGroup().getRecordGroupQuery();

		default:
			throw new RuntimeException("Should not happen");
		}
	}

}
