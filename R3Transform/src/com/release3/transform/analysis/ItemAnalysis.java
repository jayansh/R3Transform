package com.release3.transform.analysis;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.converter.toolkit.ui.FormsObject;
import com.oracle.xmlns.forms.Block;
import com.oracle.xmlns.forms.Item;
import com.release3.transform.model.PromptItemModel;

/**
 * @since 1.1 earlier know as PromptAnalysis.java In 1.1 replaced by
 *        ItemAnalysis.java ItemModel.java is replaced by PromptItemModel.java
 *        ItemAnalysis named as this deal with not only empty prompt but also
 *        with item having special characters.
 * @version 1.1
 * @author jayansh
 */
public class ItemAnalysis {

	private List<PromptItemModel> itemModelList;
	private SpecialCharAnalysis specialCharAnalysis;

	public ItemAnalysis() {
		itemModelList = new ArrayList<PromptItemModel>();
		specialCharAnalysis = new SpecialCharAnalysis();
	}

	public List<PromptItemModel> getItemModelList() {
		return itemModelList;
	}

	public void setItemModelList(List<PromptItemModel> itemModelList) {
		if (itemModelList != null) {
			this.itemModelList = itemModelList;
		} else {
			this.itemModelList = new ArrayList<PromptItemModel>();
		}
	}

	public void analysis(Block block) {
		PromptItemModel itemModel = new PromptItemModel();
		List<FormsObject> itemsList = block.getChildren();
		Boolean blockContainsPKColumn = false;
		boolean hasItems = false;
		for (FormsObject blockItem : itemsList) {
			if (blockItem instanceof Item) {

				String itemName = blockItem.getName();
				Boolean primaryKeyItem = ((Item) blockItem).isPrimaryKey();
				System.out.println("Analysing Item ::: " + itemName);
				if (itemModel.getBlock() == null) {
					itemModel.setBlock(block);
				}
				Item item = (Item) blockItem;
				if (item.getCanvasName() != null
						&& item.getCanvasName().length() > 0) {
					if (item.getPrompt() == null
							|| item.getPrompt().length() == 0) {
						itemModel.addItem(item);
					}
				} else {
					// Set item's visible property to false if item's canvas
					// property is null and visible is true.
					if (item.isVisible() != null && item.isVisible()) {
						item.setVisible(false);
					} else if (item.isVisible() == null) {
						item.setVisible(false);
					}

				}
				// for sungard Only...
				if (item.getHeight() == null
						|| item.getHeight().intValue() < 10) {
					item.setHeight(new BigInteger("10"));

				}
				if (item.getWidth() == null || item.getWidth().intValue() < 10) {
					item.setWidth(new BigInteger("10"));
				}
				specialCharAnalysis.analysis((Item) blockItem);
			}
			hasItems = true;
		}
		if (hasItems) {
			itemModelList.add(itemModel);
		}
	}

	public SpecialCharAnalysis getSpecialCharAnalysis() {
		return specialCharAnalysis;
	}

}
