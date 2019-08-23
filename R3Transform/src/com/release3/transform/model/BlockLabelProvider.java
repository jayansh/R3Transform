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

import com.oracle.xmlns.forms.Block;
import com.release3.transform.constants.PremigrationConstants;
import com.release3.transform.pref.PropertiesReadWrite;

public class BlockLabelProvider extends LabelProvider implements
		ITableLabelProvider {

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		Block block = (Block) element;
		Properties properties = PropertiesReadWrite.getPropertiesReadWrite()
				.getProperties();
		switch (columnIndex) {
		case 0:
			return block.getName();
		case 1:
			if (PremigrationConstants.UsePreviousCleanupFormPref  && !PremigrationConstants.ApplyRules) {
				if (properties.containsKey("BlockType." + block.getName()
						+ ".DatabaseBlock")) {
					block.setDatabaseBlock(Boolean.valueOf(properties
							.getProperty(
									"BlockType." + block.getName()
											+ ".DatabaseBlock").trim()));

				}
			}
			return String.valueOf(block.isDatabaseBlock());
		case 2:
			if (PremigrationConstants.UsePreviousCleanupFormPref) {
				if (properties.containsKey("BlockType." + block.getName()
						+ ".QueryDataSourceType")) {
					block.setQueryDataSourceType(properties.getProperty(
							"BlockType." + block.getName()
									+ ".QueryDataSourceType").trim());

				}
			}
			return block.getQueryDataSourceType();
		case 3:
			if (PremigrationConstants.UsePreviousCleanupFormPref ) {
				if (properties.containsKey("BlockType." + block.getName()
						+ ".QueryDataSourceName" + "."
						+ PremigrationConstants.BLOCKTYPE_TITLES[columnIndex])) {
					block.setQueryDataSourceName(properties.getProperty(
							"BlockType." + block.getName()
									+ ".QueryDataSourceName").trim());

				}
			}
			return block.getQueryDataSourceName();

		default:
			throw new RuntimeException("Should not happen");
		}
	}

}
