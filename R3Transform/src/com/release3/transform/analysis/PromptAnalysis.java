package com.release3.transform.analysis;

import java.util.ArrayList;
import java.util.List;

import com.converter.toolkit.ui.FormsObject;
import com.oracle.xmlns.forms.Block;
import com.oracle.xmlns.forms.Item;
import com.release3.transform.model.ItemModel;
/**
 * @since 1.0
 * @author jayansh
 * @deprecated
 */
public class PromptAnalysis {

	private List<ItemModel> itemModelList;

	public PromptAnalysis() {
		itemModelList = new ArrayList<ItemModel>();
	}

	public List<ItemModel> getItemModelList() {
		return itemModelList;
	}

	public void setItemModelList(List<ItemModel> itemModelList) {
		if (itemModelList != null) {
			this.itemModelList = itemModelList;
		} else {
			this.itemModelList = new ArrayList<ItemModel>();
		}
	}

	
	public void analysis(Block block) {
		ItemModel itemModel = new ItemModel();
		List<FormsObject> itemsList = block.getChildren();
		for (FormsObject blockItem : itemsList) {
			if (blockItem instanceof Item) {

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
					if (item.isVisible()!=null && item.isVisible()) {
						item.setVisible(false);
					}
				}
			}
		}
		itemModelList.add(itemModel);
	}
}
