package com.release3.tf.plsql.crud.model;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Control;

import com.release3.r3coreplsqlgen.R3CorePlSql;
import com.release3.tf.plsql.process.PlSqlTextDialog;

public class CRUDEditingSupport extends EditingSupport {
	private CellEditor editor;
	private int column;
	private String[] yesNoList = { "N", "Y" };

	public CRUDEditingSupport(ColumnViewer viewer, int column) {
		super(viewer);
		switch (column) {
		case 3:
			// editor = new TextCellEditor(((TableViewer) viewer).getTable());
			editor = new DialogCellEditor(((TableViewer) viewer).getTable()) {

				@Override
				protected Object openDialogBox(Control cellEditorWindow) {
					String value = (String) getValue();
					PlSqlTextDialog ftDialog = new PlSqlTextDialog(
							cellEditorWindow.getShell(), value);

					if (value != null) {
						int result = ftDialog.open();
						value = ftDialog.getText();
					}
					return value;
				}
			};
			break;
		case 4:
			editor = new CheckboxCellEditor(((TableViewer) viewer).getTable());
			break;
		case 5:
			editor = new CheckboxCellEditor(((TableViewer) viewer).getTable());
			break;
		}
		this.column = column;
	}

	@Override
	protected CellEditor getCellEditor(Object element) {

		return editor;
	}

	@Override
	protected boolean canEdit(Object element) {
		return true;
	}

	@Override
	protected Object getValue(Object element) {
		R3CorePlSql crud = (R3CorePlSql) element;
		System.out.println("getValue" + element);
		switch (this.column) {
		case 3:
			String plsqlText = crud.getPlSqlText();
			return plsqlText;
		case 4:
			return crud.isToBeMigrate();
		case 5:
			return crud.isMoveToDB();
		default:
			return null;
		}
	}

	@Override
	protected void setValue(Object element, Object value) {
		R3CorePlSql crud = (R3CorePlSql) element;
		switch (this.column) {
		case 3:
			crud.setPlSqlText((String) value);
			break;
		case 4:
			Boolean boolVal = (Boolean) value;
			crud.setToBeMigrate(boolVal);

			break;
		case 5:

			crud.setMoveToDB((Boolean) value);

			break;
		default:
			break;
		}

		getViewer().update(element, null);

	}

}
