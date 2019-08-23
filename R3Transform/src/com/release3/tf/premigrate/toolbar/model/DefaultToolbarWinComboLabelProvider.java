package com.release3.tf.premigrate.toolbar.model;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.oracle.xmlns.forms.Window;

public class DefaultToolbarWinComboLabelProvider  extends LabelProvider {
	public Image getImage(Object element) {
		return super.getImage(element);
	}

	public String getText(Object element) {
		Window window = (Window)element;
		return window.getName();
	}
}
