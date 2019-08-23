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
package com.release3.tf.premigrate.toolbar.analysis;

import java.util.ArrayList;
import java.util.List;

import com.oracle.xmlns.forms.Block;
import com.oracle.xmlns.forms.Window;

/**
 * 
 * @author jayansh
 * 
 */
public class ToolbarModelAnalysis {
	private List<Window> windowList;
	private List<Block> blockList;

	public ToolbarModelAnalysis() {
		windowList = new ArrayList<Window>();
		blockList = new ArrayList<Block>();
	}

	public List<Window> getWindowList() {
		return windowList;
	}

	public void setWindowList(List<Window> windowList) {
		if (windowList != null) {
			this.windowList = windowList;
		} else {
			this.windowList = new ArrayList<Window>();
		}

	}

	public List<Block> getBlockList() {
		return blockList;
	}

	public void setBlockList(List<Block> blockList) {
		if (blockList != null) {
			this.blockList = blockList;
		} else {
			this.blockList = new ArrayList<Block>();
		}
	}

}
