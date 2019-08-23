package com.release3.tf.premigrate.item.model;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.oracle.xmlns.forms.Block;
import com.release3.tf.core.License;


public class BlockComboContentProvider implements IStructuredContentProvider {
	@SuppressWarnings("unchecked")
	public Object[] getElements(Object inputElement) {
		List<Block> blockList = (List<Block>) inputElement;
		return blockList.toArray();
	}
	public void dispose() {
	}
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}
}
