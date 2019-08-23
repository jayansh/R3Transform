package com.release3.tf.customiz.model.param.canvas;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.converter.toolkit.ui.custom.CanvasSwitchParameter;

public class CanvasSwitchParameterContentProvider implements
		IStructuredContentProvider {

	@Override
	public Object[] getElements(Object inputElement) {
		@SuppressWarnings("unchecked")
		List<CanvasSwitchParameter> forms = (List<CanvasSwitchParameter>) inputElement;
		return forms.toArray();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub

	}

}
