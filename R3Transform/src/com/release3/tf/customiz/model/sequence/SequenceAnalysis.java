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
package com.release3.tf.customiz.model.sequence;

import java.util.ArrayList;
import java.util.List;

import com.converter.toolkit.ui.FormsObject;
import com.oracle.xmlns.forms.Block;
import com.release3.tf.core.form.UISettings;

public class SequenceAnalysis {

	private static SequenceAnalysis sequenceAnalysis;

	private SequenceAnalysis() {

	}

	public static SequenceAnalysis getSequenceAnalysis() {
		if (sequenceAnalysis == null) {
			sequenceAnalysis = new SequenceAnalysis();
		}
		return sequenceAnalysis;
	}

	public static String[] COLUMN_HEADERS = { "Item", "Seq" };

	public String[] getBlockArray() {
		List<Block> blockList = UISettings.getUISettingsBean()
				.getBlocksConrol().getIterator();
		String[] blockArray = new String[blockList.size()];
		for (int i = 0; i < blockList.size(); i++) {
			String blockName = blockList.get(i).getName();
			blockArray[i] = blockName;
		}
		return blockArray;

	}

	private String selectedBlock;

	public String[] getItemArray() {
		// String[] itemArray = null;
		List<Block> blockList = UISettings.getUISettingsBean()
				.getBlocksConrol().getIterator();
		// List<Item> itemList =
		// UISettings.getUISettingsBean().getItemsControl()
		// .getIterator();
		List<String> itemList = new ArrayList<String>();
		// int k = 0;
		// itemArray = new String[itemList.size()];
		if (selectedBlock != null) {
			for (int i = 0; i < blockList.size(); i++) {
				Block block = blockList.get(i);
				if (block.getName() == selectedBlock
						|| block.getName().equalsIgnoreCase(selectedBlock)) {
					List<FormsObject> formsObjectList = block.getChildren();

					for (int j = 0; j < formsObjectList.size(); j++) {
						String itemName = formsObjectList.get(j).getName();
						itemList.add(itemName);
						// itemArray[k] = block.getName() + "-" + itemName;

					}
				}
			}
		}
		return (String[]) itemList.toArray(new String[0]);
		// return itemArray;
	}

	public String getSelectedBlock() {
		return selectedBlock;
	}

	public void setSelectedBlock(String selectedBlock) {
		this.selectedBlock = selectedBlock;
	}
	
	
}
