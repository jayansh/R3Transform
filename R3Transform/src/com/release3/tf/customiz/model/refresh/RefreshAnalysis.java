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

import java.util.List;

import com.oracle.xmlns.forms.Block;
import com.release3.tf.core.form.UISettings;

public class RefreshAnalysis {

	public static String[] getRefreshArray() {
		List<Block> blockList = UISettings.getUISettingsBean()
				.getBlocksConrol().getIterator();
		String[] refresh = new String[blockList.size() ];
		for (int i = 0; i < blockList.size(); i++) {
			String blockName = blockList.get(i).getName();
			refresh[i] = blockName;
		}
		return refresh;

	}
}
