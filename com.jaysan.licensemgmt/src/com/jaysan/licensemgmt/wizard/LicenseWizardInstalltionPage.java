/*******************************************************************************
 * Copyright (c) 2009, 2013 JaySan and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Jayansh Shinde
 *******************************************************************************/
package com.jaysan.licensemgmt.wizard;

import java.io.File;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import de.schlichtherle.license.LicenseContent;
import de.schlichtherle.license.LicenseManager;

public class LicenseWizardInstalltionPage extends WizardPage implements
		IWizardPage {
	private Text txtBrowse;
	private String subject=Messages.getString("LicenseWizardInstalltionPage.product_title"); //$NON-NLS-1$
	private String licenseFilePath;
	private LicenseManager manager;
	private LicenseWizardInstallSuccessPage page3;

	/**
	 * Create the wizard.
	 */
	public LicenseWizardInstalltionPage(LicenseManager manager) {

		super(Messages.getString("LicenseWizardInstalltionPage.wizard_page")); //$NON-NLS-1$
		setTitle(Messages.getString("LicenseWizardInstalltionPage.wizard_title")); //$NON-NLS-1$
		setDescription(Messages.getString("LicenseWizardInstalltionPage.wizard_description") //$NON-NLS-1$
				+ subject + Messages.getString("LicenseWizardInstalltionPage.wizard_description_1")); //$NON-NLS-1$
		this.manager = manager;
		setPageComplete(false);
	}

	/**
	 * Create contents of the wizard.
	 * 
	 * @param parent
	 */
	public void createControl(Composite parent) {
		final Composite container = new Composite(parent, SWT.NULL);

		setControl(container);

		Button btnBrowse = new Button(container, SWT.NONE);
		btnBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fileBrowser = new FileDialog(Display.getCurrent()
						.getActiveShell(), SWT.OPEN);
				fileBrowser.setFilterExtensions(new String[] { "*.lic" }); //$NON-NLS-1$
				licenseFilePath = fileBrowser.open();
				txtBrowse.setText(licenseFilePath);

			}
		});
		btnBrowse.setBounds(528, 45, 36, 25);
		btnBrowse.setText("..."); //$NON-NLS-1$

		txtBrowse = new Text(container, SWT.BORDER);
		txtBrowse.setBounds(10, 47, 512, 21);

		final Button btnInstall = new Button(container, SWT.NONE);
		btnInstall.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					LicenseContent content = manager.install(new File(
							licenseFilePath));
					((LicenseWizard) getWizard()).setLicenseContent(content);
					btnInstall.setEnabled(false);
					
					((LicenseWizardInstallSuccessPage) ((LicenseWizard) getWizard())
							.getPage3()).fillContent(content);

					
					setMessage(
							Messages.getString("LicenseWizardInstalltionPage.wizard_success_message"), //$NON-NLS-1$
							SWT.ICON_WORKING);
					((LicenseWizardWelcomePage)getPreviousPage()).getBtnVerify().setEnabled(true);
					setPageComplete(true);
					// fireLicenseInstalled(content);
				} catch (Exception failure) {
					setErrorMessage(Messages.getString("LicenseWizardInstalltionPage.wizard_failure_message")); //$NON-NLS-1$
					failure.printStackTrace();
					setPageComplete(false);
					// Dialogs.showMessageDialog(this, failure
					// .getLocalizedMessage(), Resources
					// .getString("InstallPanel.failure.title"),
					// Dialogs.ERROR_MESSAGE);
				}
			}
		});
		btnInstall.setBounds(395, 85, 169, 25);
		btnInstall.setText(Messages.getString("LicenseWizardInstalltionPage.button_install_text")); //$NON-NLS-1$

	}

	public String getLicenseFilePath() {
		return licenseFilePath;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
}
