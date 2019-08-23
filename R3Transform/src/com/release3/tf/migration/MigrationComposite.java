package com.release3.tf.migration;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import r3transform.Activator;

import com.release3.tf.core.License;
import com.release3.tf.core.OracleForm;
import com.release3.tf.core.form.Settings;
import com.release3.tf.migrate.CustomizationChooserDialog;
import com.release3.tf.migration.model.LicenseComboContentProvider;
import com.release3.tf.migration.model.LicenseComboLabelProvider;
import com.release3.tf.migration.model.LicenseComboModelProvider;
import com.release3.tf.migration.model.MigrationContentProvider;
import com.release3.tf.migration.model.MigrationForm;
import com.release3.tf.migration.model.MigrationLabelProvider;
import com.release3.tf.migration.model.MigrationModelProvider;
import com.release3.tf.plsql.process.FormLogicPreferencesDialog;
import com.release3.tf.plsql.process.FormLogicProcessor;
import com.release3.toolkit.ui.InputFileController;
import com.release3.transform.Helper;
import com.release3.transform.constants.MigrationConstants;

public class MigrationComposite extends Composite {
	private static class Sorter extends ViewerSorter {
		public int compare(Viewer viewer, Object e1, Object e2) {
			Object item1 = e1;
			Object item2 = e2;
			return 0;
		}
	}

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */

	private TableViewer tableViewer;
	public Table table;
	private IStatus status;
	private Label lblCurrentPath;
	Button btnRefresh;
	Button btnMigrate;
	Button btnHelper;
	private ComboViewer comboViewerAsPerLicense;

	public MigrationComposite(final Composite parent, int style) {

		super(parent, style);

		Group groupMigration = new Group(this, SWT.NONE);
		groupMigration.setBounds(10, 0, 633, 546);
		tableViewer = new TableViewer(groupMigration, SWT.BORDER
				| SWT.FULL_SELECTION | SWT.MULTI);

		createColumns(tableViewer);

		lblCurrentPath = new Label(groupMigration, SWT.NONE);
		lblCurrentPath.setBounds(10, 24, 431, 23);
		lblCurrentPath.setText("");
		setCurrentFormPath();

		btnRefresh = new Button(groupMigration, SWT.NONE);
		btnRefresh.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				reload();
			}
		});
		btnRefresh.setBounds(497, 24, 68, 23);
		btnRefresh.setText("Refresh");

		btnMigrate = new Button(groupMigration, SWT.NONE);
		btnMigrate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Activator.getDefault().txtConsole.clearConsole();
				startMigration();
			}
		});
		btnMigrate.setBounds(497, 461, 68, 23);
		btnMigrate.setText("Migrate");

		btnHelper = new Button(groupMigration, SWT.NONE);
		btnHelper.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int indices[] = table.getSelectionIndices();

				if (indices.length == 0) {
					MessageBox msgBox = new MessageBox(parent.getShell(),
							SWT.OK | SWT.ICON_WARNING);
					msgBox.setMessage("You have not selected any form!");
					msgBox.setText("Select a form!");
					int result = msgBox.open();
				} else if (indices.length > 1) {
					MessageBox msgBox = new MessageBox(parent.getShell(),
							SWT.OK | SWT.ICON_WARNING);
					msgBox.setMessage("You have selected multiple forms. Please select a single form!");
					msgBox.setText("Multiple forms selected!");
					int result = msgBox.open();
				} else {
					MigrationForm form = getCurrentForm();
					File formFile = form.getForm();

					Helper helper = new Helper(parent.getShell(), formFile);
					helper.open();
					form.refresh();
				}
			}
		});
		btnHelper.setBounds(423, 461, 68, 23);
		btnHelper.setText("Helper");

		Button btnRemove = new Button(groupMigration, SWT.NONE);
		btnRemove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MigrationModelProvider.getInstance().getFormList()
						.remove(table.getSelectionIndex());
			}
		});
		btnRemove.setBounds(348, 461, 68, 23);
		btnRemove.setText("Remove");

		comboViewerAsPerLicense = new ComboViewer(groupMigration, SWT.BORDER);
		comboViewerAsPerLicense
				.addSelectionChangedListener(new ISelectionChangedListener() {
					public void selectionChanged(SelectionChangedEvent event) {
						// System.out.println("in comboviewer");
						License license = (License) comboViewerAsPerLicense
								.getElementAt(comboViewerAsPerLicense
										.getCombo().getSelectionIndex());
						List<OracleForm> forms = license.getForms();
						List<TableItem> selectedItems = getSelection(forms);
						TableItem[] arrayTableItem = selectedItems
								.toArray(new TableItem[selectedItems.size()]);
						tableViewer.getTable().setSelection(arrayTableItem);
					}
				});

		final Combo comboAsPerLicense = comboViewerAsPerLicense.getCombo();
		comboAsPerLicense.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// System.out.println(comboAsPerLicense.getItem(comboAsPerLicense.getSelectionIndex()));
				// System.out.println(comboAsPerLicense.getData());
			}
		});

		comboAsPerLicense.setBounds(10, 463, 332, 21);

		CLabel lblSelectFormAs = new CLabel(groupMigration, SWT.NONE);
		lblSelectFormAs.setBounds(10, 442, 132, 19);
		lblSelectFormAs.setText("Select form as per license:");

		final Button btnEnableSupportFor = new Button(groupMigration, SWT.CHECK);
		btnEnableSupportFor.setSelection(Settings.getSettings()
				.isJbossPortalSupport());
		btnEnableSupportFor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Settings.getSettings().setJbossPortalSupport(
						btnEnableSupportFor.getSelection());
				Settings.getSettings().save();
			}
		});
		btnEnableSupportFor.setBounds(10, 53, 212, 16);
		btnEnableSupportFor.setText("Enable Support for JBoss Portal");

		Button btnProcessLogic = new Button(groupMigration, SWT.NONE);
		btnProcessLogic.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final int indices[] = table.getSelectionIndices();

				if (indices.length == 0) {
					MessageBox msgBox = new MessageBox(parent.getShell(),
							SWT.OK | SWT.ICON_WARNING);
					msgBox.setMessage("You have not selected any form!");
					msgBox.setText("Select a form!");
					int result = msgBox.open();

				} else {
					if (indices.length > 1) {
						MessageBox msgBox = new MessageBox(parent.getShell(),
								SWT.OK | SWT.ICON_WARNING);
						msgBox.setMessage("You have selected multiple forms! Please select a single form.");
						msgBox.setText("Multiple forms selected!");
						int result = msgBox.open();
					} else {
						FormLogicPreferencesDialog dialog = new FormLogicPreferencesDialog(
								Display.getDefault().getActiveShell());
						int pick = dialog.open();
						if (pick == Dialog.CANCEL) {
							return;
						}
						FormLogicProcessor processor = new FormLogicProcessor(
								getCurrentForm());
						processor.processFormPlSqlXmlFile();
					}
				}
			}
		});
		btnProcessLogic.setBounds(348, 490, 145, 23);
		btnProcessLogic.setText("Process Form Logic");
		comboViewerAsPerLicense
				.setLabelProvider(new LicenseComboLabelProvider());
		comboViewerAsPerLicense
				.setContentProvider(new LicenseComboContentProvider());
		comboViewerAsPerLicense.setInput(LicenseComboModelProvider
				.getInstance().getLicenseList());

		tableViewer.setContentProvider(new MigrationContentProvider());
		MigrationLabelProvider labelProvider = new MigrationLabelProvider();
		tableViewer.setLabelProvider(labelProvider);
		tableViewer
				.setInput(MigrationModelProvider.getInstance().getFormList());
		tableViewer.getTable().addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void widgetSelected(SelectionEvent e) {

				TableItem[] selection = table.getSelection();
				MigrationModelProvider.getInstance().getSelectedForms().clear();
				for (TableItem tableItem : selection) {
					MigrationForm form = (MigrationForm) tableItem.getData();
					MigrationModelProvider.getInstance().getSelectedForms()
							.add(form);
				}

			}
		});
	}

	public MigrationForm getCurrentForm() {
		TableItem item = table.getSelection()[0];
		MigrationForm form = (MigrationForm) item.getData();
		return form;
	}

	private void createColumns(TableViewer viewer) {

		int[] bounds = { 180, 180 };

		for (int i = 0; i < MigrationConstants.FORMS_MIGRATION_PAGE_TITLES.length; i++) {
			TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);
			column.getColumn().setText(
					MigrationConstants.FORMS_MIGRATION_PAGE_TITLES[i]);
			column.getColumn().setWidth(bounds[i]);
			column.getColumn().setResizable(true);
			column.getColumn().setMoveable(true);

		}
		table = viewer.getTable();
		table.removeAll();
		table.setBounds(10, 79, 555, 357);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

	}

	public void reload() {

		MigrationModelProvider.getInstance().loadForms();
		setCurrentFormPath();
		tableViewer.refresh();
		refreshLicenseCombo();
		// tableViewer.setContentProvider(new MigrationContentProvider());
		// MigrationLabelProvider labelProvider = new MigrationLabelProvider();
		// tableViewer.setLabelProvider(labelProvider);

	}

	public void setCurrentFormPath() {
		if (MigrationModelProvider.getInstance().getFormList().size() > 0) {
			lblCurrentPath.setText("Current Path: "
					+ Settings.getSettings().getFmbRootDir());
		} else {
			lblCurrentPath.setText("Current Path: "
					+ "Selected directory is empty.");
		}
	}

	private IStatus getStatus() {
		return status;
	}

	private void setStatus(IStatus status) {
		this.status = status;
	}

	public List<MigrationForm> getList(List<MigrationForm> forms, int[] indices) {
		List<MigrationForm> selectedForms = new ArrayList<MigrationForm>();
		for (int i = 0, j = 0; i < forms.size(); i++) {
			if (i == indices[j]) {
				selectedForms.add(forms.get(i));
				if (j < indices.length - 1) {
					j++;
				}
			}

		}
		return selectedForms;

	}

	private List<TableItem> getSelection(List<OracleForm> forms) {
		TableItem items[] = tableViewer.getTable().getItems();
		ArrayList<TableItem> selectedItems = new ArrayList<TableItem>();
		for (OracleForm form : forms) {
			for (TableItem tableItem : items) {
				String tableformName = tableItem.getText();
				if (tableformName.equalsIgnoreCase(form.getFormName())
						|| tableformName == form.getFormName()) {
					// System.out.println(tableformName);
					selectedItems.add(tableItem);

				}
			}
		}

		return selectedItems;
		// TableItem[] arrayTableItem = selectedItems
		// .toArray(new TableItem[selectedItems.size()]);
		// tableViewer.getTable().setSelection(arrayTableItem);
	}

	public void refreshLicenseCombo() {
		try {
			LicenseComboModelProvider.getInstance().loadLicense();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		comboViewerAsPerLicense.refresh();
	}

	public void startMigration() {

		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				CustomizationChooserDialog customizationChooserDialog = new CustomizationChooserDialog(
						Display.getDefault().getActiveShell());
				int pick = customizationChooserDialog.open();
				if (pick == Dialog.CANCEL) {
					return;
				}

				final int indices[] = table.getSelectionIndices();

				if (indices.length == 0) {
					MessageBox msgBox = new MessageBox(Display.getDefault()
							.getActiveShell(), SWT.OK | SWT.ICON_WARNING);
					msgBox.setMessage("You have not selected any form!");
					msgBox.setText("Select a form!");
					int result = msgBox.open();
				} else {

					final InputFileController inFC = new InputFileController();
					final Shell parentShell = Display.getDefault()
							.getActiveShell();

					ProgressMonitorDialog dialog = new ProgressMonitorDialog(
							parentShell);
					try {
						dialog.run(true, true, new IRunnableWithProgress() {

							public void run(final IProgressMonitor monitor) {
								try {

									IStatus resultStatus = inFC.migrate(
											getList(MigrationModelProvider
													.getInstance()
													.getFormList(), indices),
											monitor, parentShell);

									setStatus(resultStatus);

								} finally {
									monitor.done();
								}
							}
						});
					} catch (InvocationTargetException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					// Job job = new Job("Migration Job") {
					//
					// @Override
					// protected IStatus run(IProgressMonitor monitor) {
					// try {
					// IStatus resultStatus = inFC.migrate(
					// getList(MigrationModelProvider
					// .getInstance()
					// .getFormList(), indices),
					// monitor,parentShell);
					//
					// setStatus(resultStatus);
					//
					// }
					//
					// finally {
					// monitor.done();
					// }
					// return status;
					// }
					//
					// };
					// job.setUser(false);
					// job.schedule();
					if (getStatus().getCode() == Status.OK) {
						MessageDialog
								.openInformation(
										Display.getDefault().getActiveShell(),
										"Migration Completed.",
										"Forms migration completed. Please check the console or the migration.log under \"Root Forms Directory\".");
					} else {

						MessageDialog.openError(Display.getDefault()
								.getActiveShell(), "Failure", getStatus()
								.getMessage());

					}
				}

			}
		});
	}

}
