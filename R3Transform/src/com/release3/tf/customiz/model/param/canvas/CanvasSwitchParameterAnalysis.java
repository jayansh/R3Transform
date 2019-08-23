/*******************************************************************************
 * Copyright (c) 2009, 2013 ObanSoft Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Jayansh Shinde
 *******************************************************************************/
package com.release3.tf.customiz.model.param.canvas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.converter.toolkit.ui.FormsObject;
import com.oracle.xmlns.forms.Block;
import com.oracle.xmlns.forms.Item;
import com.release3.tf.core.form.UISettings;

public class CanvasSwitchParameterAnalysis {

	public static String[] COLUMN_HEADERS = { "Source Form", "Source Block",
			"Source Item", "Destination Form", "Destination Block",
			"Destination Item" };

	public static HashMap<String, List<String>> blockItemListMap = new HashMap<String, List<String>>();
	private static String formsArray[];
	public static String formName;
	private static String blockArray[];

	// public static String currentBlockName;

	public static String[] getFormsArray() {

		formsArray = UISettings
				.getUISettingsBean()
				.getViewPortControl()
				.getFormBlockMap()
				.keySet()
				.toArray(
						new String[UISettings.getUISettingsBean()
								.getViewPortControl().getFormBlockMap()
								.keySet().size()]);
		return formsArray;
	}

	public static String[] getBlockArray(String formName) {
		if (formName == null) {
			formName = formsArray[0];
		}
		initBlockItemMap(formName);
		return blockArray;
	}

	public static void initBlockItemMap(String formName) {
		// List<Block> blockList = UISettings.getUISettingsBean()
		// .getBlocksConrol().getIterator();
		List<Block> blockList = UISettings.getUISettingsBean()
				.getViewPortControl().getFormBlockMap().get(formName);

		blockArray = new String[blockList.size()];
		for (int i = 0; i < blockList.size(); i++) {
			List<String> itemList = new ArrayList<String>();
			Block block = blockList.get(i);
			String blockName = blockList.get(i).getName();
			blockArray[i] = blockName;
			List<FormsObject> formsObjectList = block.getChildren();

			for (int j = 0; j < formsObjectList.size(); j++) {
				Object object = formsObjectList.get(j);
				if (object instanceof Item) {
					String itemName = ((Item) object).getName();
					itemList.add(itemName);
				}
			}
			blockItemListMap.put(blockName, itemList);
		}
		// return blockItemListMap;
	}

	public static String[] getItemArray(String blockName) {
		if (blockName == null) {
			List<String> itemList = blockItemListMap.get(blockArray[0]);
			return (String[]) itemList.toArray(new String[0]);
		}
		List<String> itemList = blockItemListMap.get(blockName);
		return (String[]) itemList.toArray(new String[0]);
	}

	public static String[] getItemArray() {
		return getItemArray(null);
	}

	public static String[] getBlockArray() {
		return getBlockArray(null);
	}
}
