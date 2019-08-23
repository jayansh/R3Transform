package com.release3.transform.ui;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.release3.tf.core.form.Settings;

public class FormConnectionDialog extends Dialog {
	private Text txtUsername;
	private Text txtPassword;
	private Text txtDatabase;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public FormConnectionDialog(Shell parentShell) {
		super(parentShell);
		setShellStyle(SWT.CLOSE);
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = (GridLayout) container.getLayout();
		gridLayout.numColumns = 5;

		Label lblUserName = new Label(container, SWT.NONE);
		lblUserName.setText("User Name:");
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		Label label = new Label(container, SWT.NONE);
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false,
				1, 1));

		txtUsername = new Text(container, SWT.BORDER);
		txtUsername.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		if (Settings.getSettings().getDbSrcUser() != null) {
			txtUsername.setText(Settings.getSettings().getDbSrcUser());
		}

		Label lblPassword = new Label(container, SWT.NONE);
		lblPassword.setText("Password:");
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		Label label_1 = new Label(container, SWT.NONE);
		label_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false,
				1, 1));

		txtPassword = new Text(container, SWT.BORDER | SWT.PASSWORD);
		txtPassword.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		if (Settings.getSettings().getDbSrcPassword() != null) {
			txtPassword.setText(Settings.getSettings().getDbSrcPassword());
		}

		Label lblDatabase = new Label(container, SWT.NONE);
		lblDatabase.setText("Database:");
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		Label label_2 = new Label(container, SWT.NONE);
		label_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false,
				1, 1));

		txtDatabase = new Text(container, SWT.BORDER);
		txtDatabase.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		if (Settings.getSettings().getDbFormConnectionSID() != null) {
			txtDatabase
					.setText(Settings.getSettings().getDbFormConnectionSID());
		}

		return container;
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		Button btnConnect = createButton(parent, SWT.PUSH,
				"Connect", true);
//		Button btnConnect =new Button(parent, SWT.PUSH);
		btnConnect.setText("Connect");
		btnConnect.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Settings.getSettings().setDbSrcUser(txtUsername.getText());
				Settings.getSettings().setDbSrcPassword(txtPassword.getText());
				Settings.getSettings().setDbFormConnectionSID(
						txtDatabase.getText());
				okPressed();
				
			}
		});
		Button btnCancel = createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

			}
		});
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(331, 172);
	}

}
