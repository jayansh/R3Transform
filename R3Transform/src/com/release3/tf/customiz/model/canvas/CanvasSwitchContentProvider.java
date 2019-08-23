package com.release3.tf.customiz.model.canvas;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.converter.toolkit.ui.custom.Parameter;

public class CanvasSwitchContentProvider implements IStructuredContentProvider {

	@Override
	public Object[] getElements(Object inputElement) {
		List<Parameter> forms = (List<Parameter>) inputElement;
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
