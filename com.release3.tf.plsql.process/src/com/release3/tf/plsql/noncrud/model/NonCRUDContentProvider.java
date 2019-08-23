package com.release3.tf.plsql.noncrud.model;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.release3.javasql.JavaPlSqlType;
import com.release3.r3coreplsqlgen.R3CorePlSql;

public class NonCRUDContentProvider implements IStructuredContentProvider {

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
		List<JavaPlSqlType> nonCrudList = (List<JavaPlSqlType>) inputElement;
		return nonCrudList.toArray();
	}

}
