package com.release3.transfrom.login;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;



public class Login extends Dialog {

	protected int result;
	protected Shell shell;
	private Text txtUsername;
	private Text txtPassword;
	private Label lblMessage;
	private static String username;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public Login(Shell parent) {
		super(parent, SWT.DIALOG_TRIM);
		setText("Login");
	}

	/**
	 * Open the dialog.
	 * 
	 * @return the result
	 */
	public int open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), getStyle());
		shell.setSize(393, 230);
		shell.setText(getText());
		{
			Label lblUsername = new Label(shell, SWT.NONE);
			lblUsername.setBounds(61, 50, 68, 15);
			lblUsername.setText("Username:");
		}
		{
			txtUsername = new Text(shell, SWT.BORDER);
			txtUsername.setBounds(152, 47, 176, 21);
		}
		{
			Label lblPassword = new Label(shell, SWT.NONE);
			lblPassword.setBounds(61, 102, 55, 15);
			lblPassword.setText("Password:");
		}
		{
			txtPassword = new Text(shell, SWT.BORDER | SWT.PASSWORD);
			txtPassword.setBounds(152, 99, 177, 21);
		}
		{
			final Button btnLogin = new Button(shell, SWT.NONE);
			btnLogin.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					btnLogin.setEnabled(false);
					if (PCLogin.isUserExist(txtUsername.getText(), txtPassword
							.getText())) {
						username = txtUsername.getText();
						
						result = SWT.OK;
						shell.close();
					} else {
						lblMessage.setText("Invalid Username or Password");
						btnLogin.setEnabled(true);
					}
				}
			});
			btnLogin.setBounds(152, 149, 75, 25);
			btnLogin.setText("Login");
		}
		{
			Button btnCancel = new Button(shell, SWT.NONE);
			btnCancel.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {

					result = SWT.CANCEL;
					shell.close();

				}
			});
			btnCancel.setBounds(253, 149, 75, 25);
			btnCancel.setText("Cancel");
		}
		{
			lblMessage = new Label(shell, SWT.NONE);
			lblMessage.setBounds(61, 10, 267, 15);
		}

	}
	public static String getUsername(){
		return username;
		
	}
}
