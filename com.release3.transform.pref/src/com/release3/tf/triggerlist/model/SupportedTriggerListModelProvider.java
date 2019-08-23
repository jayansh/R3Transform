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
package com.release3.tf.triggerlist.model;

import java.util.ArrayList;
import java.util.List;

import com.converter.toolkit.ui.JAXBXMLLoader;
import com.release3.triggerlist.SupportedTriggerType;

public class SupportedTriggerListModelProvider {
	private List<SupportedTriggerType> triggerList;
	private static SupportedTriggerListModelProvider content;

	private SupportedTriggerListModelProvider() {
		triggerList = new ArrayList<SupportedTriggerType>();
	}

	public static SupportedTriggerListModelProvider getInstance() {
		if (content != null) {
			return content;
		} else {
			content = new SupportedTriggerListModelProvider();
			return content;
		}
	}


	public List<SupportedTriggerType> getSupportedTriggerList() {
		triggerList = JAXBXMLLoader.getInstance().getTriggerList().getSupportedTrigger();
		return triggerList;
	}
}
