package com.release3.tf.premigrate.item.model;

import java.util.Properties;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.oracle.xmlns.forms.Block;
import com.oracle.xmlns.forms.Item;
import com.release3.tf.plsql.process.PlSqlProcessPlugin;
import com.release3.transform.constants.PremigrationConstants;
import com.release3.transform.pref.PropertiesReadWrite;

public class ItemLabelProvider extends LabelProvider implements
		ITableLabelProvider {
	private static final Image CHECKED = PlSqlProcessPlugin.getImageDescriptor(
			"icons/checked.gif").createImage();
	private static final Image UNCHECKED = PlSqlProcessPlugin
			.getImageDescriptor("icons/unchecked.gif").createImage();

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		Item item = (Item) element;
		Properties properties = PropertiesReadWrite.getPropertiesReadWrite()
				.getProperties();
		switch (columnIndex) {
		case 1:
			
			if (PremigrationConstants.UsePreviousCleanupFormPref
					&& !PremigrationConstants.ApplyRules) {
				if (properties.containsKey("Item." + item.getName()
						+ ".PrimaryKey")) {
					item.setPrimaryKey(Boolean.valueOf(properties.getProperty(
							"Item." + item.getName() + ".PrimaryKey").trim()));

				}
			}
			Boolean primaryKey = item.isPrimaryKey();
			System.out.println("migrateYN:: " + primaryKey);
			if (primaryKey == null) {
				return UNCHECKED;
			} else if (primaryKey) {
				return CHECKED;
			} else {
				return UNCHECKED;
			}
			
		}
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		Item item = (Item) element;
		Properties properties = PropertiesReadWrite.getPropertiesReadWrite()
				.getProperties();
		switch (columnIndex) {
		case 0:
			return item.getName();

		}
		return null;
	}

}
