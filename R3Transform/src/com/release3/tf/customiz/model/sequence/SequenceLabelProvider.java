package com.release3.tf.customiz.model.sequence;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.converter.toolkit.ui.custom.Parameter;
import com.converter.toolkit.ui.custom.Refresh;
import com.converter.toolkit.ui.custom.Sequence;

public class SequenceLabelProvider extends LabelProvider implements
		ITableLabelProvider {

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		Sequence seq = (Sequence) element;
		switch (columnIndex) {
		case 0:

			return seq.getItem();
		case 1:
			return seq.getSeq();

		default:
			throw new RuntimeException("Should not happen");
		}
	}

}
