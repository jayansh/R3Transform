package com.release3.tf.premigrate.toolbar.model;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.oracle.xmlns.forms.Window;

public class DefaultToolbarWinContentProvider implements IStructuredContentProvider {
	@SuppressWarnings("unchecked")
	public Object[] getElements(Object inputElement) {
		List<Window> windowList = (List<Window>) inputElement;
		return windowList.toArray();
	}
	public void dispose() {
	}
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}
}
