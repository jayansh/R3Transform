package com.release3.tf.premigrate.toolbar.model;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.oracle.xmlns.forms.Block;

public class DefaultToolbarBlockComboLabelProvider  extends LabelProvider {
	public Image getImage(Object element) {
		return super.getImage(element);
	}

	public String getText(Object element) {
		Block block = (Block)element;
		return block.getName();
	}
}