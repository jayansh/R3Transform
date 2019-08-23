package com.release3.tf.plsql.pu;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Control;

import com.release3.programunitgen.R3ProgramUnit;
import com.release3.tf.plsql.noncrud.model.NonCRUDModelProvider;
import com.release3.tf.plsql.process.JavaEventValidation;
import com.release3.tf.plsql.process.PlSqlTextDialog;

public class ProgramUnitEditingSupport extends EditingSupport {
	private CellEditor editor;
	private int column;

	// private String[] yesNoList = { "N", "Y" };
	private String[] javaMethodArray = { "Plain Java Method", "onblur",
			"actionListener" };

	public ProgramUnitEditingSupport(ColumnViewer viewer, int column) {
		super(viewer);
		switch (column) {
		case 1:
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
		case 2:
			// editor = new ComboBoxCellEditor(((TableViewer)
			// viewer).getTable(),
			// yesNoList);
			editor = new CheckboxCellEditor(((TableViewer) viewer).getTable());
			break;
		case 3:
			// editor = new ComboBoxCellEditor(((TableViewer)
			// viewer).getTable(),
			// yesNoList);
			editor = new CheckboxCellEditor(((TableViewer) viewer).getTable());
			break;
		case 4:
			editor = new ComboBoxCellEditor(((TableViewer) viewer).getTable(),
					javaMethodArray);
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
		R3ProgramUnit pu = (R3ProgramUnit) element;
		// System.out.println("getValue" + element);
		switch (this.column) {
		case 1:
			String plsqlText = pu.getR3ProgramUnit().getProgramUnitText().replace("&#10;", "\n");
			return plsqlText;
		case 2:
			if (pu.isToBeMigrate() == null) {
				return false;
			} else {
				return pu.isToBeMigrate();

			}
		case 3:
			if (pu.isMoveToDB() == null) {
				return false;
			} else {
				return pu.isMoveToDB();
			}
		case 4:
			if (pu.isToBeMigrate() != null && pu.isToBeMigrate()
					&& (pu.isMoveToDB() == null || !pu.isMoveToDB())) {
				String javaMethod = pu.getJavaMethod();
				int index = match(javaMethod);
				if (index > -1) {
					return index;
				}
			}
			return 0;
		default:
			return null;
		}
	}

	@Override
	protected void setValue(Object element, Object value) {
		R3ProgramUnit r3PU = (R3ProgramUnit) element;
		// System.out.println("setValue " + value.toString());
		switch (this.column) {
		case 1:
			r3PU.getR3ProgramUnit().setProgramUnitText(value.toString());
			break;
		case 2:
			Boolean boolVal = (Boolean) value;
			if (boolVal != null && !boolVal) {
				r3PU.setMoveToDB(boolVal);
			}
			r3PU.setToBeMigrate(boolVal);

			break;
		case 3:
			Boolean toBeMigrate = (Boolean) value;
			r3PU.setMoveToDB(toBeMigrate);
			if (toBeMigrate != null && toBeMigrate) {
				r3PU.setToBeMigrate(toBeMigrate);
			}
			break;
		case 4:
			if (r3PU.isToBeMigrate() != null && r3PU.isToBeMigrate()
					&& (r3PU.isMoveToDB() == null || !r3PU.isMoveToDB())) {
				Integer javaMethodIndex = (Integer) value;

				r3PU.setJavaMethod(javaMethodArray[javaMethodIndex]);

			}
			break;
		default:
			break;
		}

		getViewer().update(element, null);

	}

	private int match(String javaMethod) {
		for (int i = 0; i < javaMethodArray.length; i++) {
			if (javaMethodArray[i] == javaMethod
					|| javaMethodArray[i].equalsIgnoreCase(javaMethod)) {
				return i;
			}
		}
		return -1;
	}
}
