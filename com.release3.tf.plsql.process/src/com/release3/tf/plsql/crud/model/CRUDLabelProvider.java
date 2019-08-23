package com.release3.tf.plsql.crud.model;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.release3.r3coreplsqlgen.R3CorePlSql;
import com.release3.tf.plsql.process.PlSqlProcessPlugin;

public class CRUDLabelProvider extends LabelProvider implements
		ITableLabelProvider {
	private static final Image CHECKED = PlSqlProcessPlugin.getImageDescriptor(
			"icons/checked.gif").createImage();
	private static final Image UNCHECKED = PlSqlProcessPlugin
			.getImageDescriptor("icons/unchecked.gif").createImage();

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		R3CorePlSql crud = (R3CorePlSql) element;
		switch (columnIndex) {
		case 4:
			Boolean migrateYN = crud.isToBeMigrate();
			if (migrateYN == null) {
				return UNCHECKED;
			} else if (migrateYN) {
				return CHECKED;
			} else {
				return UNCHECKED;
			}

		case 5:
			Boolean moveToDB = crud.isMoveToDB();
			if (moveToDB) {
				return CHECKED;
			} else {
				return UNCHECKED;
			}
		}
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		R3CorePlSql crud = (R3CorePlSql) element;
		switch (columnIndex) {
		case 0:
			String blockName = crud.getBlockName();
			return blockName;
		case 1:
			String itemName = crud.getItemName();
			return itemName;
		case 2:
			String triggerName = crud.getPlSqlName();
			return triggerName;
		case 3:
			String plsqlText = crud.getPlSqlText();
			return plsqlText;

		}
		return null;

	}

}
