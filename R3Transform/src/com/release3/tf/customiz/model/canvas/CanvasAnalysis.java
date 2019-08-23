package com.release3.tf.customiz.model.canvas;

import java.util.Collection;
import java.util.List;

import com.release3.tf.core.form.UISettings;

public class CanvasAnalysis {

	public static String[] COLUMN_HEADERS = { "Forms View Ports", "View Port",
			"Forms list", "Canvas" };

	public static String[] getFormViewPortList() {
		List<String> formsList = UISettings.getUISettingsBean()
				.getConvertedFormsControl().getIterator();

		String[] formsArray = new String[formsList.size()];
		for (int i = 0; i < formsList.size(); i++) {
			String formName = formsList.get(i);
			formsArray[i] = formName;

		}
		return formsArray.clone();
	}

	public static String[] getViewPortList() {
		List< String> viewPortTable = UISettings
				.getUISettingsBean().getViewPortControl().getIterator();
		String[] viewPortValuesArray = new String[viewPortTable.size()];
		Collection<?> collection = viewPortTable;
		viewPortValuesArray = collection.toArray(new String[0]);
		return viewPortValuesArray;
	}

	public static String[] getFormsArray() {
		List<String> formsList = UISettings.getUISettingsBean()
				.getConvertedFormsControl().getIterator();

		String[] formsArray = new String[formsList.size()];
		for (int i = 0; i < formsList.size(); i++) {
			String formName = formsList.get(i);
			formsArray[i] = formName;

		}
		return formsArray.clone();

	}

	public static String[] getCanvasArray() {

		List<String> viewPortList = UISettings.getUISettingsBean()
				.getCanvases().getIterator();

		String[] viewPortArray = new String[viewPortList.size()];
		for (int i = 0; i < viewPortList.size(); i++) {
			String viewPortName = viewPortList.get(i);
			viewPortArray[i] = viewPortName;

		}
		return viewPortArray;
	}
}
