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
package com.release3.tf.analysis.ui;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;

import com.release3.tf.analysis.AnalysisFormsDependContentProvider;
import com.release3.tf.analysis.AnalysisFormsDependLabelProvider;
import com.release3.tf.analysis.AnalysisFormsDependModelProvider;
import com.release3.tf.analysis.AnalysisResultDetailsContentProvider;
import com.release3.tf.analysis.AnalysisResultDetailsLabelProvider;
import com.release3.tf.analysis.AnalysisResultDetailsModelProvider;
import com.release3.analysis.AnalysisPlugin;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

public class AnalysisResultPage extends WizardPage {

	public AnalysisResultPage() {
		super("Analysis Result");
		setTitle("Analysis Result");
		setDescription("Analysis Result.");

		// TODO Auto-generated constructor stub
	}

	private Table tableAnalysisIssues;
	private Label labelResult;
	private TableViewer tableViewerAnalysisIssues;
	private AnalysisResultDetailsModelProvider analysisModelProvider;
	private AnalysisFormsDependModelProvider formsDependModelProvider;
	private Table tableFormDependencies;
	private TableViewer tableViewerFormsDependencies;

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		setControl(container);

		Group group = new Group(container, SWT.NONE);
		group.setBounds(0, 4, 869, 499);

		labelResult = new Label(group, SWT.NONE);
		labelResult.setLocation(0, 0);
		labelResult.setSize(807, 120);

		TabFolder tabFolder = new TabFolder(group, SWT.NONE);
		tabFolder.setBounds(-4, 128, 848, 305);

		TabItem tbtmFormsDependencies = new TabItem(tabFolder, SWT.NONE);
		tbtmFormsDependencies.setText("Forms Dependencies");

		tableViewerFormsDependencies = new TableViewer(tabFolder, SWT.BORDER
				| SWT.FULL_SELECTION);
		tableFormDependencies = tableViewerFormsDependencies.getTable();
		tbtmFormsDependencies.setControl(tableFormDependencies);
		createColumnsFormsDependencies(tableViewerFormsDependencies);

		TabItem tbtmIssues = new TabItem(tabFolder, SWT.NONE);
		tbtmIssues.setText("Possible Issues");

		tableViewerAnalysisIssues = new TableViewer(tabFolder, SWT.BORDER
				| SWT.FULL_SELECTION);
		tableAnalysisIssues = tableViewerAnalysisIssues.getTable();
		tbtmIssues.setControl(tableAnalysisIssues);
		createColumnsAnalysisIssues(tableViewerAnalysisIssues);

	}

	private void createColumnsFormsDependencies(TableViewer viewer) {

		int[] bounds = { 395, 395 };

		for (int i = 0; i < AnalysisPlugin.ANALYSIS_RESULT_FORMS_DEPENDENT_TITLES.length; i++) {
			TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);
			column.getColumn().setText(
					AnalysisPlugin.ANALYSIS_RESULT_FORMS_DEPENDENT_TITLES[i]);
			column.getColumn().setWidth(bounds[i]);
			column.getColumn().setResizable(true);
			column.getColumn().setMoveable(true);
			// enable editing support

		}
		tableAnalysisIssues = viewer.getTable();
		tableAnalysisIssues.removeAll();
		tableAnalysisIssues.setHeaderVisible(true);
		tableAnalysisIssues.setLinesVisible(true);

	}

	private void createColumnsAnalysisIssues(TableViewer viewer) {

		int[] bounds = { 176, 176, 395 };

		for (int i = 0; i < AnalysisPlugin.ANALYSIS_RESULT_DETAILS_TITLES.length; i++) {
			TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);
			column.getColumn().setText(
					AnalysisPlugin.ANALYSIS_RESULT_DETAILS_TITLES[i]);
			column.getColumn().setWidth(bounds[i]);
			column.getColumn().setResizable(true);
			column.getColumn().setMoveable(true);
			// enable editing support

		}
		tableAnalysisIssues = viewer.getTable();
		tableAnalysisIssues.removeAll();
		tableAnalysisIssues.setHeaderVisible(true);
		tableAnalysisIssues.setLinesVisible(true);

	}

	public void setResult() {
		labelResult.setText(AnalysisResultModelProvider.getInstance()
				.getResultSummary());

	}

	@Override
	public IWizardPage getNextPage() {
		tableViewerFormsDependencies
				.setContentProvider(new AnalysisFormsDependContentProvider());
		AnalysisFormsDependLabelProvider dependenciesLabelProvider = new AnalysisFormsDependLabelProvider();
		tableViewerFormsDependencies
				.setLabelProvider(dependenciesLabelProvider);
		formsDependModelProvider = AnalysisFormsDependModelProvider
				.getInstance();
		tableViewerFormsDependencies.setInput(formsDependModelProvider
				.getFormDependenciesList());

		tableViewerAnalysisIssues
				.setContentProvider(new AnalysisResultDetailsContentProvider());
		AnalysisResultDetailsLabelProvider labelProvider = new AnalysisResultDetailsLabelProvider();
		tableViewerAnalysisIssues.setLabelProvider(labelProvider);
		analysisModelProvider = AnalysisResultDetailsModelProvider
				.getInstance();
		tableViewerAnalysisIssues.setInput(analysisModelProvider
				.getAnalysisResultDetailList());
		return super.getNextPage();
	}
}
