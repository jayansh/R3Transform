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
package com.release3.transform.analysis;

import java.util.ArrayList;
import java.util.List;

import com.oracle.xmlns.forms.Item;
import com.release3.transform.model.SpecialCharsItemModel;
import com.release3.transform.util.SpecialCharSearcher;

/**
 * @since 1.1
 * @author jayansh
 * @version 1.1 Get the items containing special characters.
 */
public class SpecialCharAnalysis {
	private List<SpecialCharsItemModel> itemsList;

	public SpecialCharAnalysis() {
		itemsList = new ArrayList<SpecialCharsItemModel>();
	}

	// public void analysis(Item blockItem) {
	// boolean promptHasSC = findSpecialChar(((Item) blockItem).getPrompt());
	// boolean labelHasSC = findSpecialChar(((Item) blockItem).getLabel());
	// boolean hintHasSC = findSpecialChar(((Item) blockItem).getHint());
	// boolean commentHasSC = findSpecialChar(((Item) blockItem).getComment());
	// if (promptHasSC || labelHasSC || hintHasSC || commentHasSC) {
	// SpecialCharsItemModel scItemModel = new SpecialCharsItemModel(
	// (Item) blockItem, labelHasSC, promptHasSC, hintHasSC,
	// commentHasSC);
	// itemsList.add(scItemModel);
	// }
	// }

	/**
	 * the same item object will be added 4 time in a list if contains special
	 * characters in label,prompt,hint or comment to differentiate each of
	 * condition.
	 */
	/**
	 * Finding special char in FormatMask, added on 21/04/2011.  
	 */
	public void analysis(Item blockItem) {

		if (SpecialCharSearcher.findSpecialChar(((Item) blockItem).getLabel())) {

			SpecialCharsItemModel scItemModel = new SpecialCharsItemModel(
					(Item) blockItem, true, false, false, false);
			itemsList.add(scItemModel);
		}
		if (SpecialCharSearcher.findSpecialChar(((Item) blockItem).getPrompt())) {
			SpecialCharsItemModel scItemModel = new SpecialCharsItemModel(
					(Item) blockItem, false, true, false, false);
			itemsList.add(scItemModel);
		}

		if (SpecialCharSearcher.findSpecialChar(((Item) blockItem).getHint())) {
			SpecialCharsItemModel scItemModel = new SpecialCharsItemModel(
					(Item) blockItem, false, false, true, false);
			itemsList.add(scItemModel);
		}
		if (SpecialCharSearcher.findSpecialChar(((Item) blockItem).getComment())) {
			SpecialCharsItemModel scItemModel = new SpecialCharsItemModel(
					(Item) blockItem, false, false, false, true);
			itemsList.add(scItemModel);
		}
		if (SpecialCharSearcher.findSpecialChar(((Item) blockItem).getFormatMask())) {
			SpecialCharsItemModel scItemModel = new SpecialCharsItemModel(
					(Item) blockItem, false, false, false, false);
			scItemModel.setFormatMaskContainsSC(true);
			itemsList.add(scItemModel);
		}
	}

	public List<SpecialCharsItemModel> getItemsWithSpecialCharList() {
		return itemsList;
	}

	public void setItemsList(List<SpecialCharsItemModel> itemsList) {
		if (itemsList != null) {
			this.itemsList = itemsList;
		} else {
			this.itemsList = new ArrayList<SpecialCharsItemModel>();
		}
	}

	
}
