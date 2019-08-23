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

import com.oracle.xmlns.forms.Block;

public class BlockModelProvider {
	private List<Block> blockList;
	private static BlockModelProvider content;

	private BlockModelProvider() {
		blockList = new ArrayList<Block>();
	}

	public static BlockModelProvider getInstance() {
		if (content != null) {
			return content;
		} else {
			content = new BlockModelProvider();
			return content;
		}
	}

	public void setBlocks(List<Block> blockList) {
		if (blockList != null) {
			if (this.blockList.size() > 0) {
				this.blockList.clear();
			}
			this.blockList=blockList;
		}
		else
			this.blockList = new ArrayList<Block>();
			
			
		// setting new list of blocks *not adding.
		
		
		
	}

	public List<Block> getBlockList() {
		return blockList;
	}
}
