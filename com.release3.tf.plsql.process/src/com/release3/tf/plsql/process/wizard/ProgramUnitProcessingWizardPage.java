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
package com.release3.tf.plsql.process.wizard;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;

import com.release3.programunitgen.R3ProgramUnit;
import com.release3.tf.plsql.process.PlSqlProcessPlugin;
import com.release3.tf.plsql.pu.ProgramUnitContentProvider;
import com.release3.tf.plsql.pu.ProgramUnitEditingSupport;
import com.release3.tf.plsql.pu.ProgramUnitLabelProvider;
import com.release3.tf.plsql.pu.ProgramUnitModelProvider;

public class ProgramUnitProcessingWizardPage extends WizardPage {
	private Table table;
	private TableViewer tableViewer;
	private ProgramUnitModelProvider puModelProvider;
	private static final Image CHECKED = PlSqlProcessPlugin.getImageDescriptor(
			"icons/checked.gif").createImage();
	private static final Image UNCHECKED = PlSqlProcessPlugin
			.getImageDescriptor("icons/unchecked.gif").createImage();
	private static boolean checkAllMoveToDB = false;
	private static boolean checkAllToBeMigrate = false;

	/**
	 * Create the wizard.
	 */
	public ProgramUnitProcessingWizardPage() {
		super("Program Unit Processing Wizard Page");
		setTitle("Program Unit Processing Wizard Page");
		setDescription("Program Unit Processing Wizard Page");
	}

	/**
	 * Create contents of the wizard.
	 * 
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);

		Group mainGroup = new Group(container, SWT.NONE);
		mainGroup.setBounds(10, 0, 1054, 411);
		tableViewer = new TableViewer(mainGroup, SWT.BORDER
				| SWT.FULL_SELECTION);
		createColumns(tableViewer);

	}

	private void createColumns(TableViewer viewer) {
		for (int i = 0; i < PlSqlProcessPlugin.PU_PROCESSING_PAGE_TITLE.length; i++) {
			final TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);
			if (i == 2) {
				if (checkAllToBeMigrate) {
					column.getColumn().setImage(CHECKED);
				} else {
					column.getColumn().setImage(UNCHECKED);
				}
				column.getColumn().setText(
						PlSqlProcessPlugin.PU_PROCESSING_PAGE_TITLE[i]);
				column.getColumn().addSelectionListener(
						new SelectionListener() {

							@Override
							public void widgetSelected(SelectionEvent e) {
								if (!checkAllToBeMigrate) {
									column.getColumn().setImage(CHECKED);
									checkAllToBeMigrate = true;
								} else {
									column.getColumn().setImage(UNCHECKED);
									checkAllToBeMigrate = false;
								}
								for (R3ProgramUnit r3ProgramUnit : ProgramUnitModelProvider
										.getInstance().getPUList()) {
									r3ProgramUnit.setToBeMigrate(checkAllToBeMigrate);
								}
								tableViewer.refresh();
							}

							@Override
							public void widgetDefaultSelected(SelectionEvent e) {
								// TODO Auto-generated method stub
								
							}

							

						});
			} else if (i == 3) {
				if (checkAllMoveToDB) {
					column.getColumn().setImage(CHECKED);
				} else {
					column.getColumn().setImage(UNCHECKED);
				}
				column.getColumn().setText(
						PlSqlProcessPlugin.PU_PROCESSING_PAGE_TITLE[i]);
				column.getColumn().addSelectionListener(
						new SelectionListener() {

							@Override
							public void widgetSelected(SelectionEvent e) {
								if (!checkAllMoveToDB) {
									column.getColumn().setImage(CHECKED);
									checkAllMoveToDB = true;
								} else {
									column.getColumn().setImage(UNCHECKED);
									checkAllMoveToDB = false;
								}
								for (R3ProgramUnit r3ProgramUnit : ProgramUnitModelProvider
										.getInstance().getPUList()) {
									r3ProgramUnit.setMoveToDB(checkAllMoveToDB);
								}
								tableViewer.refresh();
							}

							@Override
							public void widgetDefaultSelected(SelectionEvent e) {
								// TODO Auto-generated method stub
								
							}

							

						});
			} else {
				column.getColumn().setText(
						PlSqlProcessPlugin.PU_PROCESSING_PAGE_TITLE[i]);
			}
			column.getColumn().setWidth(180);
			column.getColumn().setResizable(true);
			column.getColumn().setMoveable(true);
			column.setEditingSupport(new ProgramUnitEditingSupport(viewer, i));
		}
		table = viewer.getTable();
		table.removeAll();

		table.setBounds(0, 10, 1044, 390);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		tableViewer.setContentProvider(new ProgramUnitContentProvider());
		ProgramUnitLabelProvider labelProvider = new ProgramUnitLabelProvider();
		tableViewer.setLabelProvider(labelProvider);
		puModelProvider = ProgramUnitModelProvider.getInstance();
		tableViewer.setInput(puModelProvider.getPUList());
	}
	
	public void reloadTable(){
		tableViewer.refresh();
	}
}
