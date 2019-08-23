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
package com.release3.transform.model;

import java.util.ArrayList;
import java.util.List;

import com.oracle.xmlns.forms.Block;
import com.oracle.xmlns.forms.Item;

/**
 * @since 1.1
 * @author jayansh 
 * @version 1.1
 * This contains all the item having null or empty prompt
 *         property values.
 * earlier know as ItemModel.java
 * In 1.1 replaced PromptItemModel.java
 */
public class PromptItemModel {
	private Item currentItem;
	private List<Item> itemList;
	private Block block;

	public PromptItemModel() {
		itemList = new ArrayList<Item>();
	}

	public void addItem(Item item) {
		itemList.add(item);
	}

	public Item getCurrentItem() {
		return currentItem;
	}

	public void setCurrentItem(Item currentItem) {
		this.currentItem = currentItem;
	}

	public List<Item> getItemList() {
		return itemList;
	}

	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}

	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

}
