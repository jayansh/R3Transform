package com.release3.tf.premigrate.item.model;

import java.util.ArrayList;
import java.util.List;

import com.converter.toolkit.ui.FormsObject;
import com.oracle.xmlns.forms.Block;
import com.oracle.xmlns.forms.Item;

public class ItemModelProvider {
	private List<Item> itemList;
	private static ItemModelProvider content;

	private ItemModelProvider() {
		itemList = new ArrayList<Item>();
	}

	public static ItemModelProvider getInstance() {
		if (content != null) {
			return content;
		} else {
			content = new ItemModelProvider();
			return content;
		}
	}

	

	public List<Item> getItemList(Block block) {
		this.itemList = new ArrayList<Item>();
		List<FormsObject> blockChildren = block.getChildren();
		for (FormsObject formsObject : blockChildren) {
			if(formsObject instanceof Item){
				itemList.add((Item) formsObject);
			}
		}
		return itemList;
	}
}