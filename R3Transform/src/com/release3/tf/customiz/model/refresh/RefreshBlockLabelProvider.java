package com.release3.tf.customiz.model.refresh;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.converter.toolkit.ui.custom.Refresh;
import com.oracle.xmlns.forms.Block;

public class RefreshBlockLabelProvider extends LabelProvider implements
ITableLabelProvider {

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		Refresh refresh = (Refresh) element;
		switch (columnIndex) {
		case 0:
			return refresh.getBlock();
		default:
			throw new RuntimeException("Should not happen");
		}
		
		
	}

}
