package com.release3.tf.ui.specialchar.pref.model;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.release3.specialchars.SpecialCharsReplacer;

public class SpecialCharsReplacerLabelProvider extends LabelProvider implements
		ITableLabelProvider {

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		SpecialCharsReplacer scReplacerModel = (SpecialCharsReplacer) element;
		switch (columnIndex) {
		case 0:
			return scReplacerModel.getSpecialChar();
		case 1:
			return scReplacerModel.getReplacement();
		default:
			throw new RuntimeException("Should not happen");
		}
	}

}
