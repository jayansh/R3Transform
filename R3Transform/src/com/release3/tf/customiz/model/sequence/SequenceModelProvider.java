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
package com.release3.tf.customiz.model.sequence;

import java.util.ArrayList;
import java.util.List;

import com.converter.toolkit.ui.control.ParametersControl;
import com.converter.toolkit.ui.control.SequenceControl;
import com.converter.toolkit.ui.custom.Parameter;
import com.converter.toolkit.ui.custom.Refresh;
import com.converter.toolkit.ui.custom.Sequence;
import com.release3.tf.core.form.UISettings;

public class SequenceModelProvider {
	private List<Sequence> sequenceList;
	private static SequenceModelProvider content;

	private SequenceModelProvider() {
		SequenceControl paramControl = UISettings.getUISettingsBean()
				.getSequenceControl();

		sequenceList = paramControl.getIterator();
		if (sequenceList == null) {
			this.sequenceList = new ArrayList<Sequence>();
		}
	}

	public static SequenceModelProvider getInstance() {
		if (content != null) {
			return content;
		} else {
			content = new SequenceModelProvider();
			return content;
		}
	}

	public void setSequenceList(List<Sequence> sequenceList) {
		if (sequenceList != null) {
			this.sequenceList = sequenceList;
		} else {
			this.sequenceList = new ArrayList<Sequence>();
		}
	}

	public List<Sequence> getSequenceList() {
		return sequenceList;
	}

	public void upIntheList(int index) {
		if (index > 0) {
			Sequence currentItem = sequenceList.get(index);
			Sequence previousItem = sequenceList.get(index - 1);
			sequenceList.set(index, previousItem);
			sequenceList.set(index - 1, currentItem);
		}
	}

	public void downIntheList(int index) {
		if (index < sequenceList.size() - 1) {
			Sequence currentItem = sequenceList.get(index);
			Sequence nextItem = sequenceList.get(index + 1);
			sequenceList.set(index, nextItem);
			sequenceList.set(index + 1, currentItem);
		}
	}

	public void delete(int index) {
		if (index >= 0) {
			sequenceList.remove(index);
		}
	}
}
