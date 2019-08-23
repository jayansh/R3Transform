package com.release3.transform.model;

import java.util.ArrayList;
import java.util.List;

import com.oracle.xmlns.forms.Block;
import com.oracle.xmlns.forms.Item;


/**
 * @since 1.0
 * @author jayansh
 * @deprecated
 * 
 */
public class ItemModel {
	private Item currentItem;
	private List<Item> itemList;
	private Block block;

	public ItemModel() {
		itemList = new ArrayList<Item>();
	}
	
	public void addItem(Item item){
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
