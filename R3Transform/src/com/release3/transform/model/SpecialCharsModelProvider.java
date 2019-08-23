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

import com.ibm.icu.util.IslamicCalendar;

public class SpecialCharsModelProvider {
	private List<SpecialCharsItemModel> itemsList;
	private static SpecialCharsModelProvider content;
	private int indexItemHasSC = -1;
	private boolean updated = true;

	private SpecialCharsModelProvider() {
		indexItemHasSC = -1;
		itemsList = new ArrayList<SpecialCharsItemModel>();
	}

	public static SpecialCharsModelProvider getInstance() {
		if (content != null) {
			return content;
		} else {
			content = new SpecialCharsModelProvider();
			return content;
		}
	}

	public void setItems(List<SpecialCharsItemModel> itemsList) {
		if (itemsList != null) {
			if (this.itemsList.size() > 0) {
				this.itemsList.clear();
			}
			this.itemsList = itemsList;
		} else{
			this.itemsList = new ArrayList<SpecialCharsItemModel>();
			indexItemHasSC = -1;
		}
	}

	public List<SpecialCharsItemModel> getItemsList() {
		return itemsList;
	}

	public boolean isUpdated() {
		return updated;
	}

	public void setUpdated(boolean updated) {
		this.updated = updated;
	}

	public int getItemHasSC() {
		if (updated) {
			for (int i = 0; i < itemsList.size(); i++) {
				SpecialCharsItemModel item = itemsList.get(i);
				if (item.isPromptContainsSC() || item.isHintContainsSC()
						|| item.isLabelContainsSC()
						|| item.isCommentContainsSC()||item.isFormatMaskContainsSC()) {
					indexItemHasSC = i;
					return i;
				} else {
					indexItemHasSC = -1;

				}
			}

		}
		return indexItemHasSC;
	}

	public int getIndexItemHasSC() {
		return indexItemHasSC;
	}

	public void setIndexItemHasSC(int indexItemHasSC) {
		this.indexItemHasSC = indexItemHasSC;
	}

	
}
