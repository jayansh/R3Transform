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
package com.release3.tf.plsql.noncrud.model;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.release3.javasql.JavaPlSqlType;
import com.release3.r3coreplsqlgen.R3CorePlSql;
import com.release3.tf.plsql.process.PlSqlProcessPlugin;

public class NonCRUDLabelProvider extends LabelProvider implements
		ITableLabelProvider {
	// We use icons
	private static final Image CHECKED = PlSqlProcessPlugin.getImageDescriptor(
			"icons/checked.gif").createImage();
	private static final Image UNCHECKED = PlSqlProcessPlugin
			.getImageDescriptor("icons/unchecked.gif").createImage();

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		JavaPlSqlType noncrud = (JavaPlSqlType) element;
		switch (columnIndex) {
		case 4:
			Boolean migrateYN = noncrud.isToBeMigrate();
			if (migrateYN == null) {
				return UNCHECKED;
			} else if (migrateYN) {
				return CHECKED;
			} else {
				return UNCHECKED;
			}
		case 5:
			Boolean moveToDB = noncrud.isMoveToDB();
			if (moveToDB == null) {
				return UNCHECKED;
			} else if (moveToDB) {
				return CHECKED;
			} else {
				return UNCHECKED;
			}
		}
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		JavaPlSqlType noncrud = (JavaPlSqlType) element;
		switch (columnIndex) {
		case 0:
			String blockName = noncrud.getBlockName();
			return blockName;
		case 1:
			String itemName = noncrud.getItemName();
			return itemName;
		case 2:
			String plSqlName = noncrud.getJavaPlSql();
			return plSqlName;
		case 3:
			String plsqlText = noncrud.getJavaPlSqlText();
			return plsqlText;
		case 6:
			String javaMethod = noncrud.getJavaMethod();
			return javaMethod;

			// case 4:
			// Boolean migrateYN = noncrud.isToBeMigrate();
			// System.out.println("migrateYN:: " + migrateYN);
			// if (migrateYN == null) {
			// return "false";
			// } else {
			// return migrateYN.toString();
			// }

			// default:
			// throw new RuntimeException("Should not happen");
		}
		return null;
	}

}
