package com.release3.tf.customiz.model.canvas;

import java.util.List;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.converter.toolkit.ui.custom.Parameter;
import com.release3.tf.core.form.UISettings;

public class CanvasSwitchLabelProvider extends LabelProvider implements
		ITableLabelProvider {

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		Parameter param = (Parameter) element;
		
		switch (columnIndex) {
		case 0:
			return param.getViewPortForm();
		case 1:
			List<String> viewPortTable = UISettings
			.getUISettingsBean().getViewPortControl().getIterator();
			String viewPort = param.getViewPort();
//			String viewPortVal = viewPortTable.get(name);
			return viewPort;
		case 2:
			return param.getFormname();
		case 3:
			return param.getCanvas();
		default:
			throw new RuntimeException("Should not happen");
		}
	}

}