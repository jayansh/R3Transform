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
package com.jaysan.licensemgmt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.prefs.Preferences;

import javax.security.auth.x500.X500Principal;

import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.jaysan.licensemgmt.wizard.LicenseWizard;

import de.schlichtherle.license.CipherParam;
import de.schlichtherle.license.KeyStoreParam;
import de.schlichtherle.license.LicenseContent;
import de.schlichtherle.license.ftp.LicenseParam;

public class LicenseCheck {
	private static LicenseCheck licenseCheck;

	private LicenseCheck() {
	}

	public static LicenseCheck getLicenseCheck() {
		if (licenseCheck == null) {
			licenseCheck = new LicenseCheck();
		}
		return licenseCheck;
	}

	private LicenseContent content;

	public LicenseContent getLicenseContent() {
		return content;
	}

	KeyStoreParam publicKeyStoreParam = new KeyStoreParam() {
		public InputStream getStream() throws IOException {
			final String resourceName = "/com/jaysan/licensemgmt/tfPublic.ks"; //$NON-NLS-1$
			final InputStream in = LicensePlugin.class
					.getResourceAsStream(resourceName);
			if (in == null)
				throw new FileNotFoundException(resourceName);
			return in;
		}

		public String getAlias() {
			return "TF"; //$NON-NLS-1$
		}

		public String getStorePwd() {
			return "Jaysan@123"; //$NON-NLS-1$
		}

		public String getKeyPwd() {
			// These parameters are not used to create any licenses.
			// Therefore there should never be a private key in the keystore
			// entry. To enforce this policy, we return null here.
			return null; // causes failure if private key is found in this
			// entry
		}
	};

	KeyStoreParam privateFTPKeyStoreParam = new KeyStoreParam() {
		public InputStream getStream() throws IOException {
			final String resourceName = "/com/jaysan/licensemgmt/ftpPrivateKeys.store"; //$NON-NLS-1$
			final InputStream in = LicensePlugin.class
					.getResourceAsStream(resourceName);
			if (in == null)
				throw new FileNotFoundException(resourceName);
			return in;
		}

		public String getAlias() {
			return "TF";
		}

		public String getStorePwd() {
			return "Jaysan@123";
		}

		public String getKeyPwd() {
			// // These parameters are not used to create any licenses.
			// // Therefore there should never be a private key in the keystore
			// // entry. To enforce this policy, we return null here.
			// return null;// causes failure if private key is found in
			// // this
			// // entry
			return "Jaysan@123";
		}
	};

	CipherParam cipherParam = new CipherParam() {
		public String getKeyPwd() {
			return "Jaysan@123";
		}
	};

	LicenseParam useLicenceParam = new LicenseParam() {

		public LicenseContent createFTPLicenseContent() {
			LicenseContent result = new LicenseContent();
			X500Principal holder = new X500Principal(
					"CN=demo, OU=TransForm, O=Release3, L=Mississauga, ST=on, C=ca");
			result.setHolder(holder);
			X500Principal issuer = new X500Principal(
					"CN=demo, OU=TransForm, O=Release3, L=Mississauga, ST=on, C=ca");
			result.setIssuer(issuer);
			result.setConsumerAmount(1);
			result.setConsumerType("User");
			result.setInfo("Trial version of TransForm.");
			return result;
		}

		public void ftpGranted(LicenseContent content) {
			// Preferences.userNodeForPackage(LicensePlugin.class).put(
			// LicensePlugin.LICENSETYPE,
			//					LicensePlugin.DEFAULT_LICENSE_TYPE); //$NON-NLS-1$ //$NON-NLS-2$
			getPreferences().put(LicensePlugin.LICENSETYPE,
					LicensePlugin.DEFAULT_LICENSE_TYPE); //$NON-NLS-1$ //$NON-NLS-2$
		}

		public int getFTPDays() {
			// TODO Auto-generated method stub
			return 30;
		}

		public KeyStoreParam getFTPKeyStoreParam() {

			return privateFTPKeyStoreParam;

		}

		public boolean isFTPEligible() {
			String preferenceValue = Preferences.userNodeForPackage(
					LicensePlugin.class).get("FTPELIGIBILITY", "EMPTY"); //$NON-NLS-1$//$NON-NLS-2$
			if (!(preferenceValue.equals("REMOVED") || //$NON-NLS-1$
			preferenceValue == "REMOVED")) { //$NON-NLS-1$
				return true;
			}
			return false;

		}

		public void removeFTPEligibility() {
			Preferences.userNodeForPackage(LicensePlugin.class).put(
					"FTPELIGIBILITY", "REMOVED"); //$NON-NLS-1$ //$NON-NLS-2$

		}

		public String getSubject() {
			if (content != null) {
				content.getSubject();
			}
			// TODO Auto-generated method stub
			return "tfPrivateKey"; //$NON-NLS-1$
		}

		public Preferences getPreferences() {
			// return Preferences.userNodeForPackage(LicensePlugin.class);
			return Preferences.userRoot().node("com/jaysan/licensemgmt");
		}

		public KeyStoreParam getKeyStoreParam() {
			return publicKeyStoreParam;
		}

		public CipherParam getCipherParam() {
			return cipherParam;
		}

	};

	public void licenseCheck() {
		de.schlichtherle.license.ftp.LicenseManager lm = new de.schlichtherle.license.ftp.LicenseManager(
				useLicenceParam);
		try {
			// Verify the previously installed current license certificate.
			content = lm.verify();
		} catch (Exception exc) {
			System.err.println("Could not verify license key");
			exc.printStackTrace();
			LicenseWizard wizard = new LicenseWizard(lm);

			Shell shell = Display.getCurrent().getActiveShell();
			WizardDialog dialog = new WizardDialog(shell, wizard);
			dialog.create();
			dialog.open();
		}

	}

	/**
	 * Upload and Install current license key using TrueLicense's
	 * LicenseManager.install() method
	 * 
	 * @throws Exception
	 */
	public LicenseContent install(File licFile) throws Exception {
		de.schlichtherle.license.ftp.LicenseManager lm = new de.schlichtherle.license.ftp.LicenseManager(
				useLicenceParam);
		content = lm.install(licFile);
		return content;
	}

}
