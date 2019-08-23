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
package com.release3.transform.ui;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;

import com.oracle.xmlns.forms.Item;
import com.release3.transform.model.SpecialCharsItemModel;
import com.release3.transform.pref.PropertiesReadWrite;
import com.release3.transform.util.SpecialCharSearcher;

public class SpecialCharsEditingSupport extends EditingSupport {
	private CellEditor editor;
	private int column;

	public SpecialCharsEditingSupport(ColumnViewer viewer, int column) {
		super(viewer);

		// Create the correct editor based on the column index
		switch (column) {
		case 1:
			editor = new TextCellEditor(((TableViewer) viewer).getTable());
			break;

		default:
			editor = new TextCellEditor(((TableViewer) viewer).getTable());
		}
		this.column = column;

	}

	@Override
	protected boolean canEdit(Object element) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		// TODO Auto-generated method stub
		return editor;
	}

	@Override
	protected Object getValue(Object element) {
		SpecialCharsItemModel scItem = (SpecialCharsItemModel) element;
		Item item = scItem.getItem();
		switch (this.column) {
		case 0:
			return item.getName();
		case 1:
			if (scItem.isLabelContainsSC()) {
				return item.getLabel();
			} else if (scItem.isPromptContainsSC()) {
				return item.getPrompt();
			} else if (scItem.isHintContainsSC()) {
				return item.getHint();
			}else if (scItem.isFormatMaskContainsSC()) {
				return item.getFormatMask();
			} 
			else {
				return item.getComment();
			}

		case 2:
			return scItem.getReplacement();

		default:
			break;
		}
		return null;

	}

	@Override
	protected void setValue(Object element, Object value) {
		SpecialCharsItemModel scItemModel = (SpecialCharsItemModel) element;

		switch (this.column) {

		case 1:

			if (scItemModel.isLabelContainsSC()) {

				scItemModel.getItem().setLabel((String) value);
				if (SpecialCharSearcher.findSpecialChar(scItemModel.getItem()
						.getLabel())) {
					scItemModel.setLabelContainsSC(true);
					scItemModel.setReplacement(null);
				} else {
					scItemModel.setLabelContainsSC(false);
					scItemModel.setReplacement((String) value);
				}
				PropertiesReadWrite
						.getPropertiesReadWrite()
						.getProperties()
						.setProperty(
								"SpecialCharsItemModel."
										+ scItemModel.getItem().getName()
										+ ".Label"
										,
								(String) value);

			} else if (scItemModel.isPromptContainsSC()) {
				scItemModel.getItem().setPrompt((String) value);
				if (SpecialCharSearcher.findSpecialChar(scItemModel.getItem()
						.getPrompt())) {
					scItemModel.setPromptContainsSC(true);
					scItemModel.setReplacement(null);
				} else {
					scItemModel.setPromptContainsSC(false);
					scItemModel.setReplacement((String) value);
				}
				PropertiesReadWrite
				.getPropertiesReadWrite()
				.getProperties()
				.setProperty(
						"SpecialCharsItemModel."
								+ scItemModel.getItem().getName()
								+ ".Prompt"
								,
						(String) value);
			} else if (scItemModel.isHintContainsSC()) {
				scItemModel.getItem().setHint((String) value);
				if (SpecialCharSearcher.findSpecialChar(scItemModel.getItem()
						.getHint())) {
					scItemModel.setHintContainsSC(true);
					scItemModel.setReplacement(null);
				} else {
					scItemModel.setHintContainsSC(false);
					scItemModel.setReplacement((String) value);
				}
				PropertiesReadWrite
				.getPropertiesReadWrite()
				.getProperties()
				.setProperty(
						"SpecialCharsItemModel."
								+ scItemModel.getItem().getName()
								+ ".Hint"
								,
						(String) value);
			} else if (scItemModel.isFormatMaskContainsSC()) {
				scItemModel.getItem().setFormatMask((String) value);
				if (SpecialCharSearcher.findSpecialChar(scItemModel.getItem()
						.getFormatMask())) {
					scItemModel.setFormatMaskContainsSC(true);
					scItemModel.setReplacement(null);
				} else {
					scItemModel.setFormatMaskContainsSC(false);
					scItemModel.setReplacement((String) value);
				}
				PropertiesReadWrite
				.getPropertiesReadWrite()
				.getProperties()
				.setProperty(
						"SpecialCharsItemModel."
								+ scItemModel.getItem().getName()
								+ ".FormatMask"
								,
						(String) value);
			}  else {
				scItemModel.getItem().setComment((String) value);
				if (SpecialCharSearcher.findSpecialChar(scItemModel.getItem()
						.getComment())) {
					scItemModel.setCommentContainsSC(true);
					scItemModel.setReplacement(null);
				} else {
					scItemModel.setCommentContainsSC(false);
					scItemModel.setReplacement((String) value);
				}
				PropertiesReadWrite
				.getPropertiesReadWrite()
				.getProperties()
				.setProperty(
						"SpecialCharsItemModel."
								+ scItemModel.getItem().getName()
								+ ".Comment"
								,
						(String) value);
			}

			break;

		default:
			break;
		}

		getViewer().update(element, null);
	}

}
