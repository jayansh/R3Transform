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
package com.release3.transform.pref;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;

import com.release3.tf.triggerlist.model.SupportedTriggerListContentProvider;
import com.release3.tf.triggerlist.model.SupportedTriggerListEditingSupport;
import com.release3.tf.triggerlist.model.SupportedTriggerListLabelProvider;
import com.release3.tf.triggerlist.model.SupportedTriggerListModelProvider;

public class TriggerListPrefComposite extends Composite {
	private Group mainGroup;
	private Table table;
	private TableViewer tableViewer;
	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public TriggerListPrefComposite(Composite parent, int style) {
		super(parent, style);

		mainGroup = new Group(this, SWT.NONE);
		mainGroup.setBounds(10, 10, 551, 437);
		tableViewer = new TableViewer(mainGroup, SWT.BORDER
				| SWT.FULL_SELECTION);
		createColumns(tableViewer);
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	private void createColumns(TableViewer viewer) {

		int[] bounds = { 180, 90 };

		for (int i = 0; i < PreferencePlugin.TRIGGERLIST_PAGE_TITLE.length; i++) {
			final TableViewerColumn column = new TableViewerColumn(viewer,
					SWT.NONE);
			column.getColumn().setText(
					PreferencePlugin.TRIGGERLIST_PAGE_TITLE[i]);
			column.getColumn().setWidth(bounds[i]);
			column.getColumn().setResizable(true);
			column.getColumn().setMoveable(true);
			column.setEditingSupport(new SupportedTriggerListEditingSupport(
					viewer, i));

		}
		table = viewer.getTable();
		table.removeAll();
		table.setBounds(0, 10, 541, 386);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		tableViewer
				.setContentProvider(new SupportedTriggerListContentProvider());
		SupportedTriggerListLabelProvider labelProvider = new SupportedTriggerListLabelProvider();
		tableViewer.setLabelProvider(labelProvider);
		tableViewer.setInput(SupportedTriggerListModelProvider.getInstance()
				.getSupportedTriggerList());
	}
}
