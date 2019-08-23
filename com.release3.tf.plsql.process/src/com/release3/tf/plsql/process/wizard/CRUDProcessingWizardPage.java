package com.release3.tf.plsql.process.wizard;

import java.util.List;
import java.util.prefs.Preferences;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.release3.formsmap.R3FormsMap.Rec;
import com.release3.r3coreplsqlgen.R3CorePlSql;
import com.release3.tf.plsql.crud.model.CRUDContentProvider;
import com.release3.tf.plsql.crud.model.CRUDEditingSupport;
import com.release3.tf.plsql.crud.model.CRUDLabelProvider;
import com.release3.tf.plsql.crud.model.CRUDModelProvider;
import com.release3.tf.plsql.process.FormLogicAnalysis;
import com.release3.tf.plsql.process.PlSqlProcessPlugin;
import com.release3.transform.pref.PreferenceConstants;
import com.release3.transform.pref.PreferencePlugin;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;

public class CRUDProcessingWizardPage extends WizardPage {
	private Table table;
	private TableViewer tableViewer;
	private CRUDModelProvider crudModelProvider;
	public static Preferences preferences = Preferences
			.userNodeForPackage(CRUDProcessingWizardPage.class);
	private static final Image CHECKED = PlSqlProcessPlugin.getImageDescriptor(
			"icons/checked.gif").createImage();
	private static final Image UNCHECKED = PlSqlProcessPlugin
			.getImageDescriptor("icons/unchecked.gif").createImage();
	// private static boolean checkAllMoveToDB = false;
	private static boolean checkAllToBeMigrate = false;
	public static final String WRITE_TO_DB_OPTION = "CRUDProcessingWizardPage.WriteToDBOption";
	/**
	 * if this writeToDB = true implies that all crud plsql generated will be
	 * moved to database automatically
	 */
	private boolean writeToDB = false;
	private Group mainGroup;

	private boolean migrateCrudToJava;

	private FormLogicAnalysis formLogicAnalysis;

	/**
	 * Create the wizard.
	 */
	public CRUDProcessingWizardPage(FormLogicAnalysis formLogicAnalysis) {
		super("CRUD Processing Wizard Page");
		setTitle("CRUD Processing Wizard Page");
		setDescription("CRUD Processing Wizard Page");
		IPreferenceStore store = PreferencePlugin.getDefault()
				.getPreferenceStore();
		this.migrateCrudToJava = store
				.getBoolean(PreferenceConstants.APPLICATION_MIGRATE_CRUD_TO_JAVA);
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
		// disable this wizard if migrate CRUD to java is set to true in
		// preferences.
		container.setEnabled(!migrateCrudToJava);
		mainGroup = new Group(container, SWT.NONE);
		mainGroup.setBounds(10, 0, 1054, 411);
		tableViewer = new TableViewer(mainGroup, SWT.BORDER
				| SWT.FULL_SELECTION);
		createColumns(tableViewer);

		final Button btnCheckWritetoDB = new Button(container, SWT.CHECK);
		btnCheckWritetoDB.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				writeToDB = btnCheckWritetoDB.getSelection();

				preferences.putBoolean(WRITE_TO_DB_OPTION, writeToDB);
			}
		});
		btnCheckWritetoDB.setBounds(24, 433, 660, 15);
		btnCheckWritetoDB
				.setText("Write PlSql to Database automatically. \r\n(If this option is checked, the Form PlSql will be automatically moved to Database.");
		btnCheckWritetoDB.setSelection(preferences.getBoolean(
				WRITE_TO_DB_OPTION, false));

		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setBounds(385, 454, 299, 15);
		lblNewLabel
				.setText("Applicable only when \"Move to DB\" option is checked.)");

	}

	private void createColumns(TableViewer viewer) {

		// int[] bounds = { 180, 180 };

		for (int i = 0; i < PlSqlProcessPlugin.CRUD_PROCESSING_PAGE_TITLE.length; i++) {
			final TableViewerColumn column = new TableViewerColumn(viewer,
					SWT.NONE);
			if (i == 4) {
				if (checkAllToBeMigrate) {
					column.getColumn().setImage(CHECKED);
				} else {
					column.getColumn().setImage(UNCHECKED);
				}
				column.getColumn().setText(
						PlSqlProcessPlugin.CRUD_PROCESSING_PAGE_TITLE[i]);
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
								for (R3CorePlSql r3CorePlSql : CRUDModelProvider
										.getInstance().getCRUDList()) {
									r3CorePlSql
											.setToBeMigrate(checkAllToBeMigrate);
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
						PlSqlProcessPlugin.CRUD_PROCESSING_PAGE_TITLE[i]);
			}
			column.getColumn().setWidth(
					PlSqlProcessPlugin.CRUD_PROCESSING_PAGE_BOUND[i]);
			// column.getColumn().setWidth(180);
			column.getColumn().setResizable(true);
			column.getColumn().setMoveable(true);
			column.setEditingSupport(new CRUDEditingSupport(viewer, i));

		}
		table = viewer.getTable();
		table.removeAll();
		table.setBounds(0, 10, 1044, 390);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		tableViewer.setContentProvider(new CRUDContentProvider());
		CRUDLabelProvider labelProvider = new CRUDLabelProvider();
		tableViewer.setLabelProvider(labelProvider);
		crudModelProvider = CRUDModelProvider.getInstance();
		tableViewer.setInput(crudModelProvider.getCRUDList());
	}

	@Override
	public IWizardPage getNextPage() {
		for (R3CorePlSql r3CorePlSql : formLogicAnalysis.getCRUDAnalysis()
				.getCrudList()) {
			for (Rec rec : formLogicAnalysis.getR3FormsMap().getRec()) {
				if (rec.getAssociatedWith().equalsIgnoreCase(
						r3CorePlSql.getPlSqlName())) {
					formLogicAnalysis.getPuAnalysis().autoCheck(rec.getName(),
							r3CorePlSql.isToBeMigrate(),
							r3CorePlSql.isMoveToDB());
				}
			}
		}
		return super.getNextPage();
	}

}
