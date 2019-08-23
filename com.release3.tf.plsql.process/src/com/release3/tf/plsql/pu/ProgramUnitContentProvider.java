package com.release3.tf.plsql.pu;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.release3.programunitgen.R3ProgramUnit;
import com.release3.r3coreplsqlgen.R3CorePlSql;

public class ProgramUnitContentProvider implements IStructuredContentProvider {

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
		List<R3ProgramUnit> puList = (List<R3ProgramUnit>) inputElement;
		return puList.toArray();
	}

}
