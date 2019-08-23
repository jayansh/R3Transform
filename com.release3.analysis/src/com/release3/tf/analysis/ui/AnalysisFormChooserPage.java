package com.release3.tf.analysis.ui;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.release3.tf.core.form.FormtoXML;
import com.release3.tf.analysis.AnalysisFormsDependModelProvider;
import com.release3.tf.analysis.AnalysisResult;
import com.release3.tf.analysis.AnalysisResultDetailsModelProvider;
import com.release3.tf.analysis.FormAnalysis;
import com.release3.tf.migration.model.MigrationContentProvider;
import com.release3.tf.migration.model.MigrationForm;
import com.release3.tf.migration.model.MigrationLabelProvider;
import com.release3.transform.constants.PremigrationConstants;


public class AnalysisFormChooserPage extends WizardPage {
	protected AnalysisFormChooserPage() {
		super("Form Chooser Wizard Page");
		setTitle("Select a form for analysis");
		setDescription("Select a form for analysis.");
	}

	private Table table;
	private AnalysisModelProvider formModelProvider;
	private MigrationForm cleanupForm;
	private TableViewer tableViewer;

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		setControl(container);
		Group mainGroup = new Group(container, SWT.NONE);
		mainGroup.setBounds(0, 0, 772, 490);

		tableViewer = new TableViewer(mainGroup, SWT.BORDER
				| SWT.FULL_SELECTION);
		createColumns(tableViewer);
		tableViewer.setContentProvider(new MigrationContentProvider());
		MigrationLabelProvider labelProvider = new MigrationLabelProvider();
		tableViewer.setLabelProvider(labelProvider);
		formModelProvider = AnalysisModelProvider.getInstance();
		tableViewer.setInput(formModelProvider.getFormList());

	}

	private void createColumns(TableViewer viewer) {

		int[] bounds = { 180, 180 };

		for (int i = 0; i < PremigrationConstants.FORMS_SELECTION_PAGE_TITLES.length; i++) {
			TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);
			column.getColumn().setText(
					PremigrationConstants.FORMS_SELECTION_PAGE_TITLES[i]);
			column.getColumn().setWidth(bounds[i]);
			column.getColumn().setResizable(true);
			column.getColumn().setMoveable(true);
		}
		table = viewer.getTable();
		table.removeAll();
		if (table.getSelectionIndex() == -1) {
			setErrorMessage("Please select a form");
		}
		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (table.getSelectionIndex() > -1) {

					TableItem item = table.getSelection()[0];
					MigrationForm form = (MigrationForm) item.getData();
					File propertiesFile = new File(Platform
							.getInstallLocation().getURL().getFile()
							+ File.separator
							+ form.getFormName().replace('.', '_')
							+ ".properties");

					setMessage("Ok");
					setErrorMessage(null);
					setPageComplete(true);
				} else {
					setErrorMessage("Please select a form");
				}
			}
		});
		table.setBounds(0, 0, 771, 400);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

	}

	@Override
	public boolean canFlipToNextPage() {

		if (getErrorMessage() != null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean isPageComplete() {
		return false;
	}

	public MigrationForm getCurrentForm() {
		TableItem item = getTable().getSelection()[0];
		MigrationForm form = (MigrationForm) item.getData();
		return form;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public MigrationForm getMigrationForm() {
		return cleanupForm;
	}

	public void setMigrationForm(MigrationForm form) {
		this.cleanupForm = form;
	}

	@Override
	public IWizardPage getNextPage() {
		/**
		 * convert form to xml.
		 * 
		 */

		if (processes()) {
			AnalysisResultPage page = (AnalysisResultPage) super.getNextPage();
			page.setResult();
			return page;
		}
		return null;
	}

	private boolean processes() {

		TableItem item = getTable().getSelection()[0];
		final MigrationForm form = (MigrationForm) item.getData();
		setMigrationForm(form);

		IRunnableWithProgress op = new IRunnableWithProgress() {

			public void run(final IProgressMonitor monitor)
					throws InvocationTargetException, InterruptedException {

				try {

					monitor.setTaskName("Extraction");
					monitor.beginTask("Analysing form...", 2);
					FormtoXML formtoxml = new FormtoXML();

					String xmlFilePath = form.getForm().getParent()
							+ File.separator
							+ form.getForm().getName().replaceFirst(".fmb",
									"_fmb") + ".xml";
					form.setFmbXmlPath(xmlFilePath);
					formtoxml.form2xml(form.getForm(), new File(xmlFilePath));
					FormAnalysis analysis = new FormAnalysis(form, monitor);
					analysis.start();
					AnalysisResult result = analysis.getAnalysisResult();
					AnalysisResultModelProvider resultModel = AnalysisResultModelProvider
							.getInstance();

					resultModel.setResult(result);

					AnalysisFormsDependModelProvider dependenciesModel = AnalysisFormsDependModelProvider
							.getInstance();
					dependenciesModel.setFormDependenciesList(result
							.getDependenciesList());

					String resultStr = "Form Name: " + result.getFormName()
							+ "\nTotal Blocks: " + result.getTotalBlocks()
							+ "\nTotal LOVs: " + result.getTotalLOVs()
							+ "\nComplexity: " + result.getComplexity();
					resultModel.setResultSummary(resultStr);

					AnalysisResultDetailsModelProvider resultDetailModel = AnalysisResultDetailsModelProvider
							.getInstance();
					resultDetailModel.setAnalysisResultDetailList(result
							.getDetailList());
					monitor.worked(2);

				} catch (Exception e) {
					throw new InvocationTargetException(e);
				} finally {
					monitor.done();
				}

			}

		};

		try {
			getContainer().run(true, true, op);
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			// // ErrorDialog stuff
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private String result;

	public void setResult(String result) {
		this.result = result;
	}

	public String getResult() {
		return result;
	}

}
