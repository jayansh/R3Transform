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

import com.oracle.xmlns.forms.Block;
import com.release3.customization.blockcust.BlockCustType;
import com.release3.customization.parameter.ParameterType;

/**
 * @since 1.0 This class is responsible for getting dbblock or non-dbblock from
 *        form and analyze for any wrong combination.
 * @version 1.1 BlockCust analysis introduced(whereClauseAnalysis).
 * 
 * @author jayansh
 */
public class BlockAnalysis {
	private List<Block> blockList;

	// private List<BlockCustType> blockCustomizationList;

	public BlockAnalysis() {
		this.blockList = new ArrayList<Block>();
		// this.blockCustomizationList = new ArrayList<BlockCustType>();
	}

	public void analysis(Block block) {

		if (block.isDatabaseBlock() == null) {
			block.setDatabaseBlock(true);
		}
		if (block.getQueryDataSourceType() == null) {
			block.setQueryDataSourceType("Table");
		}
		if (block.getQueryDataSourceName() == null) {
			block.setQueryDataSourceName("");
		}
		whereClauseAnalysis(block);
		this.blockList.add(block);
	}

	public List<Block> getBlockList() {
		return blockList;
	}

	public void setBlockList(List<Block> blockList) {
		if (blockList != null) {
			this.blockList = blockList;
		} else {
			this.blockList = new ArrayList<Block>();
			// this.blockCustomizationList = new ArrayList<BlockCustType>();
		}
	}

	public void whereClauseAnalysis(Block block) {
		List<ParameterType> paramList = new ArrayList<ParameterType>();
		String whereClause = block.getWhereClause();
		if (whereClause != null && whereClause.indexOf(':') > 0) {
			String qualifiedItem = null;
			// String[] itemArray = whereClause.split("\\=");
			// if (itemArray.length > 1) {
			// qualifiedItem = itemArray[1];
			// } else {
			qualifiedItem = whereClause.substring(whereClause.indexOf(':'));
			// }
			String itemBlkArr[] = qualifiedItem.split("\\.");
			if (itemBlkArr.length > 1) {
				String blockName = itemBlkArr[0].replace(":", "");
				String itemName = itemBlkArr[1];
				itemName = replaceUnderScore(itemName);
				ParameterType param = new ParameterType();
				param.setBlock(trim(blockName));
				param.setItem(trim(itemName));
				param.setName(trim(qualifiedItem));
				param.setType("in");
				paramList.add(param);
			} else {
				ParameterType param = new ParameterType();
				param.setBlock(trim(block.getName()));
				qualifiedItem = trim(qualifiedItem);
				String itemName = replaceUnderScore(qualifiedItem);
				param.setItem(itemName);
				param.setName(qualifiedItem);
				param.setType("in");
				paramList.add(param);
			}
		}
		if (paramList.size() > 0) {
			BlockCustType blockCust = new BlockCustType();
			blockCust.setName(trim(block.getName()));
			blockCust.setImmediate(false);
			blockCust.getParameter().addAll(paramList);
			// this.blockCustomizationList.add(blockCust);
		}

	}

	// public List<BlockCustType> getBlockCustomizationList() {
	// return blockCustomizationList;
	// }

	public String trim(String str) {
		if (str != null) {
			return str.trim();
		}
		return str;
	}

	public String replaceUnderScore(String itemName) {

		if (itemName != null && itemName.length() > 1
				&& itemName.charAt(1) == '_') {
			if (itemName.charAt(0) != '_') {
				itemName = itemName.replaceFirst("_", "");
			}
		}
		return itemName;
	}
}
