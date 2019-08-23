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

import com.oracle.xmlns.forms.Item;

public class SpecialCharsItemModel {
	public SpecialCharsItemModel(Item item, boolean labelContainsSC,
			boolean promptContainsSC, boolean hintContainsSC,
			boolean commentContainsSC) {
		this.item = item;
		this.labelContainsSC = labelContainsSC;
		this.promptContainsSC = promptContainsSC;
		this.hintContainsSC = hintContainsSC;
		this.commentContainsSC = commentContainsSC;
	}

	/*
	 * is label contains special characters.
	 */
	private boolean labelContainsSC;
	/*
	 * is prompt contains special characters.
	 */
	private boolean promptContainsSC;
	/*
	 * is hint contains special characters.
	 */
	private boolean hintContainsSC;
	/*
	 * is comment contains special characters.
	 */
	private boolean commentContainsSC;

	/*
	 * is FormatMask contains special characters.
	 */
	private boolean formatMaskContainsSC;
	
	private Item item;
	private String replacement;

	public boolean isLabelContainsSC() {
		
		return labelContainsSC;
	}

	public void setLabelContainsSC(boolean labelContainsSC) {
		this.labelContainsSC = labelContainsSC;
	}

	public boolean isPromptContainsSC() {
		return promptContainsSC;
	}

	public void setPromptContainsSC(boolean promptContainsSC) {
		this.promptContainsSC = promptContainsSC;
	}

	public boolean isHintContainsSC() {
		return hintContainsSC;
	}

	public void setHintContainsSC(boolean hintContainsSC) {
		this.hintContainsSC = hintContainsSC;
	}

	public boolean isCommentContainsSC() {
		return commentContainsSC;
	}

	public void setCommentContainsSC(boolean commentContainsSC) {
		this.commentContainsSC = commentContainsSC;
	}

	
	public boolean isFormatMaskContainsSC() {
		return formatMaskContainsSC;
	}

	public void setFormatMaskContainsSC(boolean formatmaskContainsSC) {
		this.formatMaskContainsSC = formatmaskContainsSC;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public String getReplacement() {
		return replacement;
	}

	public void setReplacement(String replacement) {
		this.replacement = replacement;
	}
	
	
	
}
