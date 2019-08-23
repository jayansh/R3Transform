package com.release3.tf.premigrate.toolbar.model;

import java.util.ArrayList;
import java.util.List;

import com.oracle.xmlns.forms.Block;
import com.oracle.xmlns.forms.Window;

public class DefaultToolbarModelProvider {
	private List<Window> windowList;
	private List<Block> blockList;
	private static DefaultToolbarModelProvider content;

	private DefaultToolbarModelProvider() {
		windowList = new ArrayList<Window>();
		blockList = new ArrayList<Block>();
	}

	public static DefaultToolbarModelProvider getInstance() {
		if (content != null) {
			return content;
		} else {
			content = new DefaultToolbarModelProvider();
			return content;
		}
	}

	public List<Window> getWindowList() {
		return windowList;
	}

	public void setWindowList(List<Window> windowList) {
		this.windowList = windowList;
	}

	public List<Block> getBlockList() {
		return blockList;
	}

	public void setBlockList(List<Block> blockList) {
		this.blockList = blockList;
	}
	
	
}
