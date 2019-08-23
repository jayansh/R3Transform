package com.release3.transform.ui;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;

import com.oracle.xmlns.forms.Block;
import com.release3.transform.constants.PremigrationConstants;
import com.release3.transform.pref.PropertiesReadWrite;

public class BlockEditingSupport extends EditingSupport {

	private CellEditor editor;
	private int column;
	String blockTypes[] = { String.valueOf(true), String.valueOf(false) };
	String queryDataSourceTypes[] = { "None", "Table", "Procedure",
			"Transactional Triggers", "FROM clause query" };

	public BlockEditingSupport(ColumnViewer viewer, int column) {
		super(viewer);

		// Create the correct editor based on the column index
		switch (column) {
		case 1:
			editor = new ComboBoxCellEditor(((TableViewer) viewer).getTable(),
					blockTypes);
			break;
		case 2:
			editor = new ComboBoxCellEditor(((TableViewer) viewer).getTable(),
					queryDataSourceTypes);
			break;

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
		Block block = (Block) element;

		switch (this.column) {
		case 0:
			return block.getName();
		case 1:
			if (block.isDatabaseBlock()) {
				return 0;
			} else {
				return 1;
			}
		case 2:
			for (int i = 0; i < queryDataSourceTypes.length; i++) {
				if (block.getQueryDataSourceType().equals(
						queryDataSourceTypes[i])
						|| block.getQueryDataSourceType() == queryDataSourceTypes[i]) {
					return i;
				}
			}
			return 0;

		case 3:
			return block.getQueryDataSourceName();
		default:
			break;
		}
		return null;

	}

	@Override
	protected void setValue(Object element, Object value) {
		Block blks = (Block) element;
		System.out.println(value);
		switch (this.column) {
		case 1:
			blks.setDatabaseBlock(Boolean
					.valueOf(blockTypes[((Integer) value)]));
			PropertiesReadWrite
					.getPropertiesReadWrite()
					.getProperties()
					.setProperty(
							"BlockType."
									+ blks.getName()
									+ ".DatabaseBlock"
									,
							blockTypes[((Integer) value)]);
			break;
		case 2:
			blks
					.setQueryDataSourceType(queryDataSourceTypes[((Integer) value)]);
			PropertiesReadWrite
					.getPropertiesReadWrite()
					.getProperties()
					.setProperty(
							"BlockType."
									+ blks.getName()
									+ ".QueryDataSourceType"
									,
							queryDataSourceTypes[((Integer) value)]);
			break;
		case 3:
			blks.setQueryDataSourceName((String) value);
			PropertiesReadWrite
					.getPropertiesReadWrite()
					.getProperties()
					.setProperty(
							"BlockType."
									+ blks.getName()
									+ ".QueryDataSourceName"
									,
							(String) value);
			break;
		default:
			break;
		}

		getViewer().update(element, null);

	}

}
