package com.release3.plsql.analysis.property.model;

import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class PropertiesContentProvider implements ITreeContentProvider {
	private Object[] EMPTY_ARRAY = new Object[0];

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof List) {
			List<FormProperties> properties = (List<FormProperties>) inputElement;

			FormProperties[] formPropertiesArray = new FormProperties[properties
					.size()];

			formPropertiesArray = properties.toArray(formPropertiesArray);

			return formPropertiesArray;
		}else if (inputElement instanceof FormProperties) {
			FormProperties property = (FormProperties) inputElement;

			FormProperties propertyArray[] = { property };

			return propertyArray;
		}
		return EMPTY_ARRAY;
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getParent(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		// Get the children
		Object[] obj = getChildren(element);

		// Return whether the parent has children
		return obj == null ? false : obj.length > 0;
	}

}
