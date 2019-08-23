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

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import de.schlichtherle.license.LicenseContent;

public class LicenseWizardInstallSuccessPage extends WizardPage {
	private Text txtHolder;
	private Text txtSubject;
	private Text txtLicensingtype;
	private Text txtNotbefore;
	private Text txtNotafter;
	private Text txtIssuer;
	private Text txtIssued;
	private Text txtInfo;

	private LicenseContent content;

	/**
	 * Create the wizard.
	 */
	public LicenseWizardInstallSuccessPage() {
		super(Messages.getString("LicenseWizardInstallSuccessPage.wizardPage")); //$NON-NLS-1$
		setTitle(Messages.getString("LicenseWizardInstallSuccessPage.LicCert")); //$NON-NLS-1$
		setDescription(Messages.getString("LicenseWizardInstallSuccessPage.LicCert")); //$NON-NLS-1$
		setPageComplete(true);
	}

	/**
	 * Create contents of the wizard.
	 * 
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite installSuccessContainer = new Composite(parent, SWT.NULL);

		setControl(installSuccessContainer);
		{

			{
				Label lblHolder = new Label(installSuccessContainer, SWT.NONE);
				lblHolder.setBounds(10, 10, 142, 15);
				lblHolder.setText(Messages.getString("LicenseWizardInstallSuccessPage.holder")); //$NON-NLS-1$
			}
			{
				txtHolder = new Text(installSuccessContainer, SWT.BORDER
						| SWT.READ_ONLY | SWT.V_SCROLL);
				txtHolder.setBackground(getColor(SWT.COLOR_WHITE));
				txtHolder.setBounds(174, 7, 370, 54);

			}
			{
				Label lblLicensingSubject = new Label(installSuccessContainer,
						SWT.NONE);
				lblLicensingSubject.setBounds(10, 70, 142, 15);
				lblLicensingSubject.setText(Messages.getString("LicenseWizardInstallSuccessPage.subject")); //$NON-NLS-1$
			}
			{
				txtSubject = new Text(installSuccessContainer, SWT.BORDER
						| SWT.READ_ONLY);
				txtSubject.setBackground(getColor(SWT.COLOR_WHITE));
				txtSubject.setBounds(174, 67, 370, 21);

			}
			{
				Label lblLicensingTypeamount = new Label(
						installSuccessContainer, SWT.NONE);
				lblLicensingTypeamount.setBounds(10, 97, 142, 15);
				lblLicensingTypeamount.setText(Messages.getString("LicenseWizardInstallSuccessPage.lic_type")); //$NON-NLS-1$
			}
			{
				txtLicensingtype = new Text(installSuccessContainer, SWT.BORDER
						| SWT.READ_ONLY);
				txtLicensingtype.setBackground(getColor(SWT.COLOR_WHITE));
				txtLicensingtype.setBounds(174, 94, 370, 21);

			}
			{
				Label lblNotBefore = new Label(installSuccessContainer,
						SWT.NONE);
				lblNotBefore.setBounds(10, 124, 142, 15);
				lblNotBefore.setText(Messages.getString("LicenseWizardInstallSuccessPage.not_before")); //$NON-NLS-1$
			}
			{
				txtNotbefore = new Text(installSuccessContainer, SWT.BORDER
						| SWT.READ_ONLY);
				txtNotbefore.setBackground(getColor(SWT.COLOR_WHITE));
				txtNotbefore.setBounds(174, 121, 370, 21);

			}
			{
				Label lblNotAfter = new Label(installSuccessContainer, SWT.NONE);
				lblNotAfter.setBounds(10, 152, 142, 15);
				lblNotAfter.setText(Messages.getString("LicenseWizardInstallSuccessPage.not_after")); //$NON-NLS-1$
			}
			{
				txtNotafter = new Text(installSuccessContainer, SWT.BORDER
						| SWT.READ_ONLY);
				txtNotafter.setBackground(getColor(SWT.COLOR_WHITE));
				txtNotafter.setBounds(174, 149, 370, 21);

			}
			{
				Label lblIssuer = new Label(installSuccessContainer, SWT.NONE);
				lblIssuer.setBounds(10, 179, 142, 15);
				lblIssuer.setText(Messages.getString("LicenseWizardInstallSuccessPage.issuer")); //$NON-NLS-1$
			}
			{
				txtIssuer = new Text(installSuccessContainer, SWT.BORDER
						| SWT.READ_ONLY | SWT.V_SCROLL);
				txtIssuer.setBackground(getColor(SWT.COLOR_WHITE));
				txtIssuer.setBounds(174, 176, 370, 54);

			}
			{
				txtIssued = new Text(installSuccessContainer, SWT.BORDER
						| SWT.READ_ONLY);
				txtIssued.setBackground(getColor(SWT.COLOR_WHITE));
				txtIssued.setBounds(174, 237, 370, 21);

			}
			{
				Label lblIssued = new Label(installSuccessContainer, SWT.NONE);
				lblIssued.setBounds(10, 241, 142, 15);
				lblIssued.setText(Messages.getString("LicenseWizardInstallSuccessPage.issued")); //$NON-NLS-1$
			}
			{
				Label lblInfo = new Label(installSuccessContainer, SWT.NONE);
				lblInfo.setBounds(10, 268, 142, 15);
				lblInfo.setText(Messages.getString("LicenseWizardInstallSuccessPage.info")); //$NON-NLS-1$
			}
			{
				txtInfo = new Text(installSuccessContainer, SWT.BORDER
						| SWT.READ_ONLY | SWT.V_SCROLL);
				txtInfo.setBackground(getColor(SWT.COLOR_WHITE));
				txtInfo.setBounds(174, 264, 370, 54);

			}

		}
	}

	public void fillContent(LicenseContent content) {

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

	public Color getColor(int systemColorID) {
		Display display = Display.getCurrent();
		return display.getSystemColor(systemColorID);
	}

}
