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
package com.release3.tf.customiz.model.refresh;

import java.util.ArrayList;
import java.util.List;

import com.converter.toolkit.ui.custom.Parameter;
import com.converter.toolkit.ui.custom.Refresh;

public class RefreshBlockModelProvider {
	private List<Refresh> refreshList;
	private static RefreshBlockModelProvider content;

	private RefreshBlockModelProvider() {
		refreshList = new ArrayList<Refresh>();
	}

	public static RefreshBlockModelProvider getInstance() {
		if (content != null) {
			return content;
		} else {
			content = new RefreshBlockModelProvider();
			return content;
		}
	}

	public void setRefreshList(List<Refresh> refreshList) {
		if (refreshList != null) {
			this.refreshList = refreshList;
		} else
			this.refreshList = new ArrayList<Refresh>();
	}

	public List<Refresh> getRefreshList() {
		return refreshList;
	}

	public void upIntheList(int index) {
		if (index > 0) {
			Refresh currentItem = refreshList.get(index);
			Refresh previousItem = refreshList.get(index - 1);
			refreshList.set(index, previousItem);
			refreshList.set(index - 1, currentItem);
		}
	}

	public void downIntheList(int index) {
		if (index < refreshList.size() - 1) {
			Refresh currentItem = refreshList.get(index);
			Refresh nextItem = refreshList.get(index + 1);
			refreshList.set(index, nextItem);
			refreshList.set(index + 1, currentItem);
		}
	}

	public void delete(int index) {
		if (index >= 0) {
			refreshList.remove(index);
		}
	}
}
