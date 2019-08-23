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

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;

import com.converter.toolkit.ui.JAXBXMLWriter;
import com.release3.tf.core.form.Settings;
import com.release3.transform.constants.PremigrationConstants;
import com.release3.transform.model.LOVContentProvider;
import com.release3.transform.model.LOVLabelProvider;
import com.release3.transform.model.LOVModelProvider;
import com.release3.transform.pref.PropertiesReadWrite;

public class LOVHandlingWizardPage extends WizardPage {

	private Table table;
	private TableViewer tableViewer;
	private LOVModelProvider lovModelProvider;

	protected LOVHandlingWizardPage() {
		super("LOVWizardPage");
		setTitle("LOV Wizard Page");
		setDescription("LOV Handling");
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		setControl(container);
		Group mainGroup = new Group(container, SWT.NONE);
		mainGroup.setBounds(0, 0, 772, 525);

		tableViewer = new TableViewer(mainGroup, SWT.BORDER
				| SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setBounds(10, 10, 752, 427);
		createColumns(tableViewer);

		Button btnApply = new Button(mainGroup, SWT.NONE);
		btnApply.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FormChooserWizardPage page1 = ((FormChooserWizardPage) (getPreviousPage()
						.getPreviousPage().getPreviousPage().getPreviousPage()));
				try {
					JAXBXMLWriter.writetoXML(
							Settings.getSettings().getFmbRootDir()
									+ File.separator
									+ page1.getCleanupForm().getFormName()
											.replace('.', '_') + "_LOV.xml",
							page1.getLovModelAnalysis()
									.getLOVRecordGroupModelList());

					PropertiesReadWrite.getPropertiesReadWrite().write();
					// page1.generatCustomization();
				} catch (JAXBException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnApply.setBounds(694, 457, 68, 23);
		btnApply.setText("Apply");


//		final Button btnCreateView = new Button(mainGroup, SWT.CHECK);
//		ToolTip tip = new ToolTip(parent.getShell(), SWT.NONE);
//		tip.setText("This option automatically creates view for LOVs containing multiple table queries.");
//		tip.setAutoHide(true);
//		
//		btnCreateView
//				.setToolTipText("This option automatically creates view for LOVs containing multiple table queries.");
//		r3transform.Activator.getDefault().getPreferenceStore()
//				.setDefault(PremigrationConstants.CreateViewForTables, true);
//		btnCreateView.setSelection(r3transform.Activator.getDefault()
//				.getPreferenceStore()
//				.getBoolean(PremigrationConstants.CreateViewForTables));
//		btnCreateView.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				r3transform.Activator
//						.getDefault()
//						.getPreferenceStore()
//						.setValue(PremigrationConstants.CreateViewForTables,
//								btnCreateView.getSelection());
//			}
//		});
//		btnCreateView.setBounds(10, 455, 185, 16);
//		btnCreateView.setText("Create view for multiple tables");

	}

	private void createColumns(TableViewer viewer) {

		int[] bounds = { 176, 176, 395 };

		for (int i = 0; i < PremigrationConstants.LOV_RG_TITLES.length; i++) {
			TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);
			column.getColumn().setText(PremigrationConstants.LOV_RG_TITLES[i]);
			column.getColumn().setWidth(bounds[i]);
			column.getColumn().setResizable(true);
			column.getColumn().setMoveable(true);
			// enable editing support
			column.setEditingSupport(new LovRgModelEditingSupport(viewer, i));

		}
		table = viewer.getTable();
		table.removeAll();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

	}

	@Override
	public IWizardPage getNextPage() {
		tableViewer.setContentProvider(new LOVContentProvider());
		LOVLabelProvider labelProvider = new LOVLabelProvider();
		tableViewer.setLabelProvider(labelProvider);
		lovModelProvider = LOVModelProvider.getInstance();
		tableViewer.setInput(lovModelProvider.getLovRgList());
		return super.getNextPage();
	}
}
