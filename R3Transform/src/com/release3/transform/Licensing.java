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
package com.release3.transform;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;

import com.jaysan.licensemgmt.LicenseCheck;
import com.jaysan.licensemgmt.wizard.Messages;
import com.release3.tf.core.License;
import com.release3.tf.core.Utils;
import com.release3.tf.migration.model.LicenseComboModelProvider;

import de.schlichtherle.license.LicenseContent;

public class Licensing extends Composite {
	private Text txtLicFile;
	private Vector<List<String>> licenseTable = new Vector<List<String>>();
	private String[] currElementsInfoViewer = null;
	private Text txtHolder;
	private Text txtSubject;
	private Text txtLicensingtype;
	private Text txtNotbefore;
	private Text txtNotafter;
	private Text txtIssuer;
	private Text txtIssued;
	private Text txtInfo;

	private LicenseContent content;
	private Vector<License> licenses = new Vector<License>();

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public Licensing(final Composite parent, int style) {
		super(parent, style);

		Label lblUploadLicenseFile = new Label(this, SWT.NONE);
		lblUploadLicenseFile.setBounds(10, 20, 113, 16);
		lblUploadLicenseFile.setText("Replace License File:");

		txtLicFile = new Text(this, SWT.BORDER);
		txtLicFile.setBounds(129, 17, 230, 19);

		Button btnBrowse = new Button(this, SWT.NONE);
		btnBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fileDialog = new FileDialog(parent.getShell());
				fileDialog.setText("Select a license");
				String[] fileExt = { "*.lic" };
				fileDialog.setFilterExtensions(fileExt);
				String uploadFilePath = fileDialog.open();
				if (uploadFilePath != null) {
					txtLicFile.setText(uploadFilePath);
				}
			}
		});
		btnBrowse.setBounds(365, 15, 68, 23);
		btnBrowse.setText("Browse");

		

		final Button btnInstall = new Button(this, SWT.NONE);
		btnInstall.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (txtLicFile.getText() != "" || txtLicFile.getText() != null) {
					try {
						LicenseContent content = LicenseCheck.getLicenseCheck()
								.install(new File(txtLicFile.getText()));

						fillContent(content);
						MessageBox msgBox = new MessageBox(parent.getShell(),
								SWT.OK);
						msgBox.setMessage(Messages
								.getString("LicenseWizardInstalltionPage.wizard_success_message"));
						//$NON-NLS-1$
						msgBox.setText("Success!");
						msgBox.open();

					} catch (Exception failure) {
						MessageBox msgBox = new MessageBox(parent.getShell(),
								SWT.OK);
						msgBox.setMessage(Messages
								.getString("LicenseWizardInstalltionPage.wizard_failure_message"));
						//$NON-NLS-1$
						msgBox.setText("Error!");
						msgBox.open();
						failure.printStackTrace();
					}
				} else {
					MessageBox msgBox = new MessageBox(parent.getShell(),
							SWT.OK);
					msgBox.setMessage("Output Directory is empty. Please set the \"Output Directory\" under Application Preferences tab.");
					msgBox.setText("Workspace path empty!");
					msgBox.open();

				}
			}
		});
		btnInstall.setBounds(439, 15, 68, 23);
		btnInstall.setText("Install");

		Group grpLicenseContent = new Group(this, SWT.NONE);
		grpLicenseContent.setText(Messages.getString("Licensing.grpLicenseContent.text")); //$NON-NLS-1$
		grpLicenseContent.setBounds(10, 44, 575, 379);

		Composite composite = licContentComposite(grpLicenseContent);
		composite.setBounds(10, 18, 553, 351);
		LicenseContent content = LicenseCheck.getLicenseCheck()
				.getLicenseContent();
		if (content != null) {
			fillContent(content);
		}

	}


	private Composite licContentComposite(Composite parent) {
		Composite installSuccessContainer = new Composite(parent, SWT.NULL);

		Label lblHolder = new Label(installSuccessContainer, SWT.NONE);
		lblHolder.setBounds(10, 10, 142, 15);
		lblHolder.setText(Messages
				.getString("LicenseWizardInstallSuccessPage.holder")); //$NON-NLS-1$

		txtHolder = new Text(installSuccessContainer, SWT.BORDER
				| SWT.READ_ONLY | SWT.V_SCROLL);
		txtHolder.setBackground(getColor(SWT.COLOR_WHITE));
		txtHolder.setBounds(174, 7, 370, 54);

		Label lblLicensingSubject = new Label(installSuccessContainer, SWT.NONE);
		lblLicensingSubject.setBounds(10, 70, 142, 15);
		lblLicensingSubject.setText(Messages
				.getString("LicenseWizardInstallSuccessPage.subject")); //$NON-NLS-1$

		txtSubject = new Text(installSuccessContainer, SWT.BORDER
				| SWT.READ_ONLY);
		txtSubject.setBackground(getColor(SWT.COLOR_WHITE));
		txtSubject.setBounds(174, 67, 370, 21);

		Label lblLicensingTypeamount = new Label(installSuccessContainer,
				SWT.NONE);
		lblLicensingTypeamount.setBounds(10, 97, 142, 15);
		lblLicensingTypeamount.setText(Messages
				.getString("LicenseWizardInstallSuccessPage.lic_type")); //$NON-NLS-1$

		txtLicensingtype = new Text(installSuccessContainer, SWT.BORDER
				| SWT.READ_ONLY);
		txtLicensingtype.setBackground(getColor(SWT.COLOR_WHITE));
		txtLicensingtype.setBounds(174, 94, 370, 21);

		Label lblNotBefore = new Label(installSuccessContainer, SWT.NONE);
		lblNotBefore.setBounds(10, 124, 142, 15);
		lblNotBefore.setText(Messages
				.getString("LicenseWizardInstallSuccessPage.not_before")); //$NON-NLS-1$

		txtNotbefore = new Text(installSuccessContainer, SWT.BORDER
				| SWT.READ_ONLY);
		txtNotbefore.setBackground(getColor(SWT.COLOR_WHITE));
		txtNotbefore.setBounds(174, 121, 370, 21);

		Label lblNotAfter = new Label(installSuccessContainer, SWT.NONE);
		lblNotAfter.setBounds(10, 152, 142, 15);
		lblNotAfter.setText(Messages
				.getString("LicenseWizardInstallSuccessPage.not_after")); //$NON-NLS-1$

		txtNotafter = new Text(installSuccessContainer, SWT.BORDER
				| SWT.READ_ONLY);
		txtNotafter.setBackground(getColor(SWT.COLOR_WHITE));
		txtNotafter.setBounds(174, 149, 370, 21);

		Label lblIssuer = new Label(installSuccessContainer, SWT.NONE);
		lblIssuer.setBounds(10, 179, 142, 15);
		lblIssuer.setText(Messages
				.getString("LicenseWizardInstallSuccessPage.issuer")); //$NON-NLS-1$

		txtIssuer = new Text(installSuccessContainer, SWT.BORDER
				| SWT.READ_ONLY | SWT.V_SCROLL);
		txtIssuer.setBackground(getColor(SWT.COLOR_WHITE));
		txtIssuer.setBounds(174, 176, 370, 54);

		txtIssued = new Text(installSuccessContainer, SWT.BORDER
				| SWT.READ_ONLY);
		txtIssued.setBackground(getColor(SWT.COLOR_WHITE));
		txtIssued.setBounds(174, 237, 370, 21);

		Label lblIssued = new Label(installSuccessContainer, SWT.NONE);
		lblIssued.setBounds(10, 241, 142, 15);
		lblIssued.setText(Messages
				.getString("LicenseWizardInstallSuccessPage.issued")); //$NON-NLS-1$

		Label lblInfo = new Label(installSuccessContainer, SWT.NONE);
		lblInfo.setBounds(10, 268, 142, 15);
		lblInfo.setText(Messages
				.getString("LicenseWizardInstallSuccessPage.info")); //$NON-NLS-1$

		txtInfo = new Text(installSuccessContainer, SWT.BORDER | SWT.READ_ONLY
				| SWT.V_SCROLL);
		txtInfo.setBackground(getColor(SWT.COLOR_WHITE));
		txtInfo.setBounds(174, 264, 370, 54);

		return installSuccessContainer;
	}

	public Color getColor(int systemColorID) {
		Display display = Display.getCurrent();
		return display.getSystemColor(systemColorID);
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	private void fillContent(LicenseContent content) {

		if (content.getHolder() != null) {
			txtHolder.setText(content.getHolder().getName());
		}
		if (content.getSubject() != null) {
			txtSubject.setText(content.getSubject());
		}
		if (content.getConsumerType() != null) {
			txtLicensingtype.setText(content.getConsumerType() + "(" //$NON-NLS-1$
					+ content.getConsumerAmount() + ")"); //$NON-NLS-1$
		}
		if (content.getNotBefore() != null) {
			txtNotbefore.setText(content.getNotBefore().toString());
		}
		if (content.getNotAfter() != null) {
			txtNotafter.setText(content.getNotAfter().toString());
		}
		if (content.getIssuer() != null) {
			txtIssuer.setText(content.getIssuer().getName());
		}
		if (content.getIssued() != null) {
			txtIssued.setText(content.getIssued().toString());
		}
		if (content.getInfo() != null) {
			txtInfo.setText(content.getInfo());
		}

	}
}
