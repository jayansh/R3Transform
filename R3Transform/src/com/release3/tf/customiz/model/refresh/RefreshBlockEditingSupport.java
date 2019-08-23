package com.release3.tf.customiz.model.refresh;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;

import com.converter.toolkit.ui.custom.Refresh;

public class RefreshBlockEditingSupport extends EditingSupport {

	private CellEditor editor;
	private int column;
	String refreshblocks[];

	public RefreshBlockEditingSupport(ColumnViewer viewer, int column) {

		super(viewer);
		refreshblocks = RefreshAnalysis.getRefreshArray();
		// Create the correct editor based on the column index
		switch (column) {
		case 0:
			editor = new ComboBoxCellEditor(((TableViewer) viewer).getTable(),
					refreshblocks);
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
		Refresh refreshBlock = (Refresh) element;

		switch (this.column) {
		case 0:
			for (int i = 0; i < refreshblocks.length; i++) {
				if (refreshBlock.getBlock().equals(refreshblocks[i])) {
					return i;
				}
			}
			return 0;

		default:
			return null;
		}

	}

	@Override
	protected void setValue(Object element, Object value) {
		Refresh refreshBlock = (Refresh) element;
		System.out.println(value);
		switch (this.column) {
		case 0:
			refreshBlock.setBlock(refreshblocks[((Integer) value)]);
			break;

		default:
			break;
		}

		getViewer().update(element, null);

	}

}
