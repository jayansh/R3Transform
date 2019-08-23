package com.release3.tf.plsql.pu;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.release3.javasql.JavaPlSqlType;
import com.release3.programunitgen.R3ProgramUnit;
import com.release3.tf.plsql.process.PlSqlProcessPlugin;

public class ProgramUnitLabelProvider extends LabelProvider implements
		ITableLabelProvider {
	private static final Image CHECKED = PlSqlProcessPlugin.getImageDescriptor(
			"icons/checked.gif").createImage();
	private static final Image UNCHECKED = PlSqlProcessPlugin
			.getImageDescriptor("icons/unchecked.gif").createImage();

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		R3ProgramUnit pu = (R3ProgramUnit) element;
		switch (columnIndex) {
		case 2:
			Boolean migrateYN = pu.isToBeMigrate();
			if (migrateYN == null) {
				return UNCHECKED;
			} else if (migrateYN) {
				return CHECKED;
			} else {
				return UNCHECKED;
			}
		case 3:
			Boolean migrateTODB = pu.isMoveToDB();
			if (migrateTODB == null) {
				return UNCHECKED;
			} else if (migrateTODB) {
				return CHECKED;
			} else {
				return UNCHECKED;
			}

		}
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		R3ProgramUnit pu = (R3ProgramUnit) element;
		switch (columnIndex) {
		case 0:
			return pu.getR3ProgramUnit().getName();
		case 1:
			return pu.getR3ProgramUnit().getProgramUnitText().replace("&#10;", "\n");
		case 4:
			return pu.getJavaMethod();
			// case 2:
			// Boolean migrateYN = pu.isToBeMigrate();
			// System.out.println("migrateYN:: " + migrateYN);
			// if (migrateYN == null) {
			// return "N";
			// } else if (migrateYN) {
			// return "Y";
			// } else {
			// return "N";
			// }

		}
		return null;
	}

}
