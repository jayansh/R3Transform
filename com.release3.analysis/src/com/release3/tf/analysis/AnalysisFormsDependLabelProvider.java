package com.release3.tf.analysis;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class AnalysisFormsDependLabelProvider extends LabelProvider implements
		ITableLabelProvider {

	@Override
	public Image getColumnImage(Object element, int columnIndex) {

		return null;

	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		FormDependency dependency = (FormDependency) element;
		switch (columnIndex) {
		case 0:
			return dependency.getDependencyName();
		case 1:
			return dependency.getDependencyType();

		default:
			throw new RuntimeException("Should not happen");
		}

	}
}
