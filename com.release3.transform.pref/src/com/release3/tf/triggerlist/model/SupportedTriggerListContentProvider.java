package com.release3.tf.triggerlist.model;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.release3.triggerlist.SupportedTriggerType;

public class SupportedTriggerListContentProvider implements IStructuredContentProvider {

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
		@SuppressWarnings("unchecked")
		List<SupportedTriggerType> triggerTypeList = (List<SupportedTriggerType>) inputElement;
		return triggerTypeList.toArray();
	}

}
