package com.release3.tf.plsql.process.wizard;

import java.util.List;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;

import com.release3.formsmap.R3FormsMap.Rec;
import com.release3.javasql.JavaPlSqlType;
import com.release3.programunitgen.R3ProgramUnit;
import com.release3.r3coreplsqlgen.R3CorePlSql;
import com.release3.tf.plsql.noncrud.model.NonCRUDContentProvider;
import com.release3.tf.plsql.noncrud.model.NonCRUDEditingSupport;
import com.release3.tf.plsql.noncrud.model.NonCRUDLabelProvider;
import com.release3.tf.plsql.noncrud.model.NonCRUDModelProvider;
import com.release3.tf.plsql.process.FormLogicAnalysis;
import com.release3.tf.plsql.process.PlSqlProcessPlugin;
import com.release3.tf.plsql.pu.ProgramUnitModelProvider;

public class NonCRUDProcessingWizardPage extends WizardPage {
	private Table table;
	private TableViewer tableViewer;
	private static final Image CHECKED = PlSqlProcessPlugin.getImageDescriptor(
			"icons/checked.gif").createImage();
	private static final Image UNCHECKED = PlSqlProcessPlugin
			.getImageDescriptor("icons/unchecked.gif").createImage();
	private static boolean checkAllMoveToDB = false;
	private static boolean checkAllToBeMigrate = false;
	private FormLogicAnalysis formLogicAnalysis;

	/**
	 * Create the wizard.
	 */
	public NonCRUDProcessingWizardPage(FormLogicAnalysis formLogicAnalysis) {
		super("NonCRUD Processing Wizard Page");
		setTitle("NonCRUD Processing Wizard Page");
		setDescription("NonCRUD Processing Wizard Page ");
		this.formLogicAnalysis = formLogicAnalysis;
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

		// int[] bounds = { 180, 180 };

		for (int i = 0; i < PlSqlProcessPlugin.NON_CRUD_PROCESSING_PAGE_TITLE.length; i++) {
			final TableViewerColumn column = new TableViewerColumn(viewer,
					SWT.NONE);
			if (i == 4) {
				if (checkAllToBeMigrate) {
					column.getColumn().setImage(CHECKED);
				} else {
					column.getColumn().setImage(UNCHECKED);
				}
				column.getColumn().setText(
						PlSqlProcessPlugin.NON_CRUD_PROCESSING_PAGE_TITLE[i]);
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
								for (JavaPlSqlType javaPlSql : NonCRUDModelProvider
										.getInstance().getNonCRUDList()) {
									javaPlSql
											.setToBeMigrate(checkAllToBeMigrate);
								}
								tableViewer.refresh();
							}

							@Override
							public void widgetDefaultSelected(SelectionEvent e) {
								// TODO Auto-generated method stub

							}

						});
			} else if (i == 5) {
				if (checkAllMoveToDB) {
					column.getColumn().setImage(CHECKED);
				} else {
					column.getColumn().setImage(UNCHECKED);
				}
				column.getColumn().setText(
						PlSqlProcessPlugin.NON_CRUD_PROCESSING_PAGE_TITLE[i]);
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
								for (JavaPlSqlType javaPlSql : NonCRUDModelProvider
										.getInstance().getNonCRUDList()) {
									javaPlSql.setMoveToDB(checkAllMoveToDB);
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
						PlSqlProcessPlugin.NON_CRUD_PROCESSING_PAGE_TITLE[i]);
			}
			column.getColumn().setWidth(180);
			column.getColumn().setResizable(true);
			column.getColumn().setMoveable(true);
			column.setEditingSupport(new NonCRUDEditingSupport(viewer, i));
		}
		table = viewer.getTable();
		table.removeAll();
		table.setBounds(0, 10, 1044, 390);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		tableViewer.setContentProvider(new NonCRUDContentProvider());
		NonCRUDLabelProvider labelProvider = new NonCRUDLabelProvider();
		tableViewer.setLabelProvider(labelProvider);
		tableViewer.setInput(NonCRUDModelProvider.getInstance()
				.getNonCRUDList());
	}

	@Override
	public IWizardPage getNextPage() {
		for (JavaPlSqlType javaPlSql : formLogicAnalysis.getNonCRUDAnalysis()
				.getNonCrudList()) {
			for (Rec rec : formLogicAnalysis.getR3FormsMap().getRec()) {
				if (rec.getAssociatedWith().equalsIgnoreCase(
						javaPlSql.getJavaPlSql())) {
					formLogicAnalysis.getPuAnalysis().autoCheck(rec.getName(),
							javaPlSql.isToBeMigrate(), javaPlSql.isMoveToDB());
				}
			}

		}
		ProgramUnitProcessingWizardPage puProcessingPage = (ProgramUnitProcessingWizardPage) super
				.getNextPage();
		puProcessingPage.reloadTable();
		return puProcessingPage;
	}

}
