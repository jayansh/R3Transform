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
package com.release3.tf.ui.specialchar.pref.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.release3.specialchars.SpecialCharsReplacer;
import com.release3.transform.util.SpecialCharConstants;

public class SpecialCharsReplacerModelProvider {
	// private List<SpecialCharsReplacer> specialCharsList;
	private static SpecialCharsReplacerModelProvider content;
	private boolean updated = true;
	private SpecialCharsReplacerHandler specialCharsReplacerHandler;

	private SpecialCharsReplacerModelProvider() {
		specialCharsReplacerHandler = new SpecialCharsReplacerHandler();
		// specialCharsList =
		// specialCharsReplacerHandler.getSpecialCharReplacerList();
	}

	public static SpecialCharsReplacerModelProvider getInstance() {
		if (content != null) {
			return content;
		} else {
			content = new SpecialCharsReplacerModelProvider();
			return content;
		}
	}

	// public void setSpecialChars(List<SpecialCharsReplacer> itemsList) {
	// if (itemsList != null) {
	// if (this.specialCharsList.size() > 0) {
	// this.specialCharsList.clear();
	// }
	// this.specialCharsList = itemsList;
	// } else{
	// this.specialCharsList = new ArrayList<SpecialCharsReplacer>();
	// }
	// }

	public List<SpecialCharsReplacer> getSpecialChars() {
		return specialCharsReplacerHandler.getSpecialCharReplacerList();
	}

	public boolean isUpdated() {
		return updated;
	}

	public void setUpdated(boolean updated) {
		this.updated = updated;
	}

	public void addSpecialChar(SpecialCharsReplacer specialCharsReplacer) {
		specialCharsReplacerHandler.addSpecialChar(specialCharsReplacer);
	}

	public void removeSpecialChars(List elements) {
		specialCharsReplacerHandler.removeSpecialChars(elements);
	}

	public void saveSpecialCharsReplacerModel() {
		try {
			specialCharsReplacerHandler.saveSpecialCharPrefXmlFile();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
