package com.release3.transform.model;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.release3.transform.lov.LOVRecordGroupModelList.LOVRecordGroupModel;

public class LOVContentProvider  implements IStructuredContentProvider{

	@Override
	public Object[] getElements(Object inputElement) {
		@SuppressWarnings("unchecked")
		List<LOVRecordGroupModel> items = (List<LOVRecordGroupModel>) inputElement;
		return items.toArray();
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
