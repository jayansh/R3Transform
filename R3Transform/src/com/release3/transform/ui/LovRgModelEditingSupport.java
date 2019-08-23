package com.release3.transform.ui;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;

import com.release3.transform.constants.PremigrationConstants;
import com.release3.transform.lov.LOVRecordGroupModelList.LOVRecordGroupModel;
import com.release3.transform.pref.PropertiesReadWrite;

public class LovRgModelEditingSupport extends EditingSupport {

	private CellEditor editor;
	private int column;

	public LovRgModelEditingSupport(ColumnViewer viewer, int column) {
		super(viewer);
		// Create the correct editor based on the column index
		switch (column) {
		// case 1:
		// editor = new ComboBoxCellEditor(((TableViewer) viewer).getTable(),
		// blockTypes);
		// break;
		// case 2:
		// editor = new ComboBoxCellEditor(((TableViewer) viewer).getTable(),
		// queryDataSourceTypes);
		// break;

		default:
			editor = new TextCellEditor(((TableViewer) viewer).getTable());
		}
		this.column = column;

	}

	@Override
	protected boolean canEdit(Object element) {
		return true;
	}

	@Override
	protected CellEditor getCellEditor(Object element) {

		return editor;
	}

	@Override
	protected Object getValue(Object element) {
		LOVRecordGroupModel lovRgModel = (LOVRecordGroupModel) element;

		switch (this.column) {
		case 0:
			return lovRgModel.getLOV().getName();
		case 1:
			return lovRgModel.getTableName();
		case 2:
			return lovRgModel.getRecordGroup().getRecordGroupQuery();

		default:
			break;
		}
		return null;

	}

	@Override
	protected void setValue(Object element, Object value) {
		LOVRecordGroupModel lovRgModel = (LOVRecordGroupModel) element;

		switch (this.column) {

		case 1:
			lovRgModel.setTableName((String) value);
			PropertiesReadWrite
					.getPropertiesReadWrite()
					.getProperties()
					.setProperty(
							"LOVRecordGroupModel."
									+ lovRgModel.getLOV().getName()
									+ "."
									+ PremigrationConstants.LOV_RG_TITLES[column],
							(String) value);
			break;
		case 2:
			lovRgModel.getRecordGroup().setRecordGroupQuery((String) value);
			PropertiesReadWrite
					.getPropertiesReadWrite()
					.getProperties()
					.setProperty(
							"LOVRecordGroupModel."
									+ lovRgModel.getLOV().getName()
									+ "."
									+ PremigrationConstants.LOV_RG_TITLES[column],
							(String) value);
			break;

		}

	}

}
