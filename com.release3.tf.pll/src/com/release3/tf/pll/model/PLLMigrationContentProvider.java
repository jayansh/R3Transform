package com.release3.tf.pll.model;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;


public class PLLMigrationContentProvider implements IStructuredContentProvider {

	@SuppressWarnings("unchecked")
	@Override
	public Object[] getElements(Object inputElement) {
		List<MigrationPLL> pll = (List<MigrationPLL>) inputElement;
		return pll.toArray();
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		System.out.println("MigrationContentProvider selected");
		// TODO Auto-generated method stub

	}

}
