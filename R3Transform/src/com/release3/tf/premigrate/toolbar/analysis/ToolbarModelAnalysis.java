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
