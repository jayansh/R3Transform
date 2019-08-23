package com.release3.transform.model;

import java.util.Properties;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.oracle.xmlns.forms.Item;
import com.release3.transform.constants.PremigrationConstants;
import com.release3.transform.pref.PropertiesReadWrite;
import com.release3.transform.util.SpecialCharSearcher;

public class SpecialCharsLabelProvider extends LabelProvider implements
		ITableLabelProvider {

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		SpecialCharsItemModel scItem = (SpecialCharsItemModel) element;
		Item item = scItem.getItem();
		Properties properties = PropertiesReadWrite.getPropertiesReadWrite()
				.getProperties();
		switch (columnIndex) {
		case 0:
			return item.getName();
		case 1:
			if (scItem.isLabelContainsSC()) {
				if (PremigrationConstants.UsePreviousCleanupFormPref) {
					if (properties.containsKey("SpecialCharsItemModel."
							+ item.getName() + ".Label")) {
						item.setLabel(properties
								.getProperty("SpecialCharsItemModel."
										+ item.getName() + ".Label"));
					}
				}
				if (SpecialCharSearcher.findSpecialChar(item.getLabel())) {
					scItem.setLabelContainsSC(true);
					scItem.setReplacement(null);
				} else {
					scItem.setLabelContainsSC(false);
					scItem.setReplacement(item.getLabel());
				}

				return item.getLabel();
			} else if (scItem.isPromptContainsSC()) {
				if (PremigrationConstants.UsePreviousCleanupFormPref) {
					if (properties.containsKey("SpecialCharsItemModel."
							+ item.getName() + ".Prompt")) {
						item.setPrompt(properties
								.getProperty("SpecialCharsItemModel."
										+ item.getName() + ".Prompt"));
					}
				}
				if (SpecialCharSearcher.findSpecialChar(item.getPrompt())) {
					scItem.setPromptContainsSC(true);
					scItem.setReplacement(null);
				} else {
					scItem.setPromptContainsSC(false);
					scItem.setReplacement(item.getPrompt());
				}

				return item.getPrompt();
			} else if (scItem.isHintContainsSC()) {
				if (PremigrationConstants.UsePreviousCleanupFormPref) {
					if (properties.containsKey("SpecialCharsItemModel."
							+ item.getName() + ".Hint")) {
						item.setHint(properties
								.getProperty("SpecialCharsItemModel."
										+ item.getName() + ".Hint"));
					}
				}
				if (SpecialCharSearcher.findSpecialChar(item.getHint())) {
					scItem.setHintContainsSC(true);
					scItem.setReplacement(null);
				} else {
					scItem.setHintContainsSC(false);
					scItem.setReplacement(item.getHint());
				}

				return item.getHint();
			} else if (scItem.isCommentContainsSC()) {
				if (PremigrationConstants.UsePreviousCleanupFormPref) {
					if (properties.containsKey("SpecialCharsItemModel."
							+ item.getName() + ".Comment")) {
						item.setHint(properties
								.getProperty("SpecialCharsItemModel."
										+ item.getName() + ".Comment"));
					}
				}
				if (SpecialCharSearcher.findSpecialChar(item.getComment())) {
					scItem.setCommentContainsSC(true);
					scItem.setReplacement(null);
				} else {
					scItem.setCommentContainsSC(false);
					scItem.setReplacement(item.getComment());
				}

				return item.getComment();
			} else if (scItem.isFormatMaskContainsSC()) {
				if (PremigrationConstants.UsePreviousCleanupFormPref) {
					if (properties.containsKey("SpecialCharsItemModel."
							+ item.getName() + ".FormatMask")) {
						item.setFormatMask(properties
								.getProperty("SpecialCharsItemModel."
										+ item.getName() + ".FormatMask"));
					}
				}
				if (SpecialCharSearcher.findSpecialChar(item.getFormatMask())) {
					scItem.setFormatMaskContainsSC(true);
					scItem.setReplacement(null);
				} else {
					scItem.setFormatMaskContainsSC(false);
					scItem.setReplacement(item.getComment());
				}

				return item.getFormatMask();
			} 
			else {
				return scItem.getReplacement();
			}

			// case 2:
			// return scItem.getReplacement();

		default:
			throw new RuntimeException("Should not happen");
		}
	}

}
