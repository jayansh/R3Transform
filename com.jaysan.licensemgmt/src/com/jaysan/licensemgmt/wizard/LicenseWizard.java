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

import java.util.Date;
import java.util.prefs.Preferences;

import org.eclipse.jface.wizard.Wizard;

import com.jaysan.licensemgmt.LicensePlugin;

import de.schlichtherle.license.LicenseContent;
import de.schlichtherle.license.LicenseManager;

public class LicenseWizard extends Wizard {

	private LicenseWizardWelcomePage page1;
	private LicenseWizardInstalltionPage page2;
	private LicenseWizardInstallSuccessPage page3;

	private LicenseManager licenseManager;
	private LicenseContent licenseContent;

	public LicenseContent getLicenseContent() {
		return licenseContent;
	}

	public void setLicenseContent(LicenseContent licenseContent) {
		this.licenseContent = licenseContent;
	}

	public LicenseWizard(LicenseManager lm) {
		this.licenseManager = lm;
		page1 = new LicenseWizardWelcomePage(licenseManager);
		addPage(page1);

		try {
			LicenseContent lc = lm.verify();

			Date lastDateOfUse = lc.getNotAfter();
			if (lastDateOfUse == null) {
				Preferences.userNodeForPackage(LicensePlugin.class).put(
						LicensePlugin.LICENSETYPE, "FULL"); //$NON-NLS-1$

			} else {
				Preferences.userNodeForPackage(LicensePlugin.class).put(
						LicensePlugin.LICENSETYPE,
						LicensePlugin.DEFAULT_LICENSE_TYPE);
				Date now = new Date();
				long MILLSECS_PER_DAY = 86400000;
				int daysLeft = (int) ((lastDateOfUse.getTime() - now.getTime()) / MILLSECS_PER_DAY);
				LicensePlugin.trialDaysLeft = daysLeft;
			}

			page1.setSelectBtnVerify(true);
		} catch (Exception exc) {
			page1.setSelectBtnVerify(false);
			System.err.println(Messages
					.getString("LicenseWizard.message_verify_failed")); //$NON-NLS-1$

			Preferences.userNodeForPackage(LicensePlugin.class).put(
					LicensePlugin.LICENSETYPE,
					LicensePlugin.DEFAULT_LICENSE_TYPE);
			exc.printStackTrace();

		}
		setWindowTitle(Messages.getString("LicenseWizard.wizard_title")); //$NON-NLS-1$

	}

	@Override
	public void addPages() {

		page2 = new LicenseWizardInstalltionPage(licenseManager);
		addPage(page2);
		page3 = new LicenseWizardInstallSuccessPage();
		addPage(page3);

	}

	@Override
	public boolean needsProgressMonitor() {
		return true;
	}

	@Override
	public boolean needsPreviousAndNextButtons() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean performFinish() {
		Preferences.userNodeForPackage(LicensePlugin.class).put(
				LicensePlugin.LICENSETYPE, "FULL"); //$NON-NLS-1$
		return true;
	}

	public LicenseWizardInstalltionPage getPage2() {
		return page2;
	}

	public LicenseWizardInstallSuccessPage getPage3() {
		return page3;
	}

	@Override
	public boolean canFinish() {
		if (getContainer().getCurrentPage() == page3) {
			return true;
		} else {
			return false;
		}

	}

}
