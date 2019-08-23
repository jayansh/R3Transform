package com.release3.transform;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

public class MigrateUI {

	protected Shell shell;
	private Text txtUploadFile;
	private Table table;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MigrateUI window = new MigrateUI();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(545, 443);
		shell.setText("SWT Application");

		txtUploadFile = new Text(shell, SWT.BORDER);
		txtUploadFile.setBounds(109, 30, 264, 19);

		Button btnUpload = new Button(shell, SWT.NONE);
		btnUpload.setBounds(385, 57, 68, 23);
		btnUpload.setText("Upload");

		Label lblUploadForm = new Label(shell, SWT.NONE);
		lblUploadForm.setBounds(10, 33, 93, 13);
		lblUploadForm.setText("Upload Form:");

		Button btnBrowse = new Button(shell, SWT.NONE);
		btnBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fileDialog = new FileDialog(shell);
				fileDialog.setFilterPath("*.fmb");
				fileDialog.setText("Select Oracle Form");
				String uploadFilePath = fileDialog.open();
				if (uploadFilePath != null) {
					txtUploadFile.setText(uploadFilePath);
				}
			}
		});
		btnBrowse.setBounds(385, 28, 68, 23);
		btnBrowse.setText("Browse...");

		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBounds(39, 122, 461, 238);

		table = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(0, 0, 451, 228);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableColumn tblclmnFile = new TableColumn(table, SWT.NONE);
		tblclmnFile.setWidth(331);
		tblclmnFile.setText("File");

		TableColumn tblclmnStatus = new TableColumn(table, SWT.NONE);
		tblclmnStatus.setWidth(112);
		tblclmnStatus.setText("Status");

		Button btnMigrate = new Button(shell, SWT.NONE);
		btnMigrate.setBounds(432, 366, 68, 23);
		btnMigrate.setText("Migrate");

		Button btnRemove = new Button(shell, SWT.NONE);
		btnRemove.setBounds(348, 366, 68, 23);
		btnRemove.setText("Remove");

	}
}
