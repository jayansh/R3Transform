package com.release3.transform.model;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import com.release3.tf.core.form.CleanupForm;

public class CleanupFormLabelProvider extends LabelProvider implements
		ITableLabelProvider {

//	private static final Image CHECKED = Activator.getImageDescriptor(
//	"icons/checked.gif").createImage();
//	private static final Image UNCHECKED = Activator.getImageDescriptor(
//			"icons/unchecked.gif").createImage();


	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		// In case you don't like image just return null here
//		if (columnIndex == 1) {
//			if (((CleanupForm) element).isStatus()) {
//				return CHECKED;
//			} else {
//				return UNCHECKED;
//			}
//		}
		return null;

	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		CleanupForm form = (CleanupForm) element;
		switch (columnIndex) {
		case 0:
			return form.getFormName();
		case 1:
//			return String.valueOf( form.isStatus());
			return form.getStatus();
		default:
			throw new RuntimeException("Should not happen");
		}

	}

}