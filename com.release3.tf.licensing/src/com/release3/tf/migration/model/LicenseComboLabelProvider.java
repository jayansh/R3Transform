package com.release3.tf.migration.model;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.release3.tf.core.License;

public class LicenseComboLabelProvider extends LabelProvider {
	public Image getImage(Object element) {
		return super.getImage(element);
	}

	public String getText(Object element) {
		License license = (License)element;
		return license.getLicenseeName();
	}
}
