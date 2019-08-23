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
package com.release3.toolkit;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.InputStream;

import com.cinnabarsystems.clm.Feature;
import com.cinnabarsystems.clm.License;
import com.cinnabarsystems.clm.LicenseManager;
import com.cinnabarsystems.clm.Licensee;
import com.converter.premigration.FileFilterExt;
import com.converter.premigration.ProductInfo;

/**
 * 
 * @author Jayansh Shinde
 * 
 */
@Deprecated
public class LicenseCheck {

	/*
	 * <java classname="com.converter.toolkit.LicenseCheck" failonerror="true">
	 * <arg value="${template}\license.lic"/> <arg value="TF.3.0.0."/> <arg
	 * value="${FMBFile}.fmb"/> <arg
	 * value="${ViewBaseDir}/src/${applicationName}/${FMBFile}/${FMBFile}.xml"/>
	 * <arg value="NA"/>
	 */
	@Deprecated
	public void check(String licenseLicDir, ProductInfo product, String file,
			String licenseeName) throws Exception {
		File dir = new File(licenseLicDir);
		if (dir.isDirectory()) {
			String[] str = { "lic" };
			FileFilter filter = new FileFilterExt("*.lic", str);
			File[] fileArr = dir.listFiles(filter);

			if (fileArr.length == 0) {

				throw new Exception("License not found.");
			}
			for (int j = 0; j < fileArr.length; j++) {
				File licenseLic = fileArr[j];
				try {

					InputStream myCLMLicenseStream = this.getClass()
							.getResourceAsStream("CLM.lic");

					InputStream myLicenseStream = new FileInputStream(
							licenseLic);
					InputStream myPublicKeyStream = this.getClass()
							.getResourceAsStream("key.public");

					LicenseManager mgr = new LicenseManager(myCLMLicenseStream,
							myLicenseStream, myPublicKeyStream);

					String productName = "TF.5.5.0";
					String feature = product.getProductName().toLowerCase();
					License license = mgr.acquireLicense(productName,
							feature.toLowerCase());
					Feature ft = license.getLicensee(licenseeName).getFeature(
							feature.toLowerCase());

					Licensee licensee = license.getLicensee(licenseeName);
					/**
					 * TF is now free source thus 1. validity of license 2.
					 * Block validation. Attribute issueDateAttr = licensee
					 * .getAttribute("IssueDate"); Attribute validityAttr =
					 * licensee.getAttribute("Validity"); // Fri Nov 12 00:56:50
					 * if (issueDateAttr != null) { String issueDateStr =
					 * (String) issueDateAttr.getValue(); SimpleDateFormat
					 * dateFormat = new SimpleDateFormat(
					 * "EEE MMM d HH:mm:ss z yyyy"); Calendar currentCal =
					 * Calendar.getInstance(); Calendar cal1 =
					 * Calendar.getInstance(); Calendar cal2 =
					 * Calendar.getInstance(); Date issueDate =
					 * dateFormat.parse(issueDateStr); cal1.setTime(issueDate);
					 * if (validityAttr != null) { Integer validity = (Integer)
					 * validityAttr .getValue();
					 * 
					 * cal2.setTime(issueDate); cal2.add(Calendar.DATE,
					 * validity); if (!(cal1.before(currentCal) && currentCal
					 * .before(cal2))) {
					 * System.out.println("License Expired!!!"); throw new
					 * Exception("License Expired!!!"); } }
					 * 
					 * } Module mod = new JAXBFMBReader().init(file);
					 * 
					 * Iterator itr =
					 * mod.getFormModule().getChildren().iterator(); FormsObject
					 * obj = null; Vector<Block> blkVect = new Vector<Block>();
					 * while (itr.hasNext()) { obj = (FormsObject) itr.next();
					 * if (obj instanceof Block) { // Ignoring R3Controls block
					 * added on 25-06-2011 Block block = (Block) obj; String
					 * blkName = block.getName(); if (!(blkName == "R3CONTROLS"
					 * || blkName .equalsIgnoreCase("R3CONTROLS"))) {
					 * blkVect.add(block); } } }
					 * 
					 * if (blkVect.size() + 1 != ft.getAttributes().size()) {
					 * throw new Exception("Invalid block counts."); }
					 * 
					 * int i = 0; for (Iterator blocks = blkVect.iterator();
					 * blocks.hasNext(); i++) { Block blk = (Block)
					 * blocks.next();
					 * 
					 * Attribute attr = (Attribute) ft.getAttribute("block" +
					 * i); if (attr == null) { throw new Exception(); // lets
					 * try another license } if (!((String)
					 * (attr.getValue())).replace("$", "S")
					 * .equals(blk.getName())) { throw new
					 * Exception("Invalid Block Name"); } }
					 */
					// No exception occurs
					break;
				} catch (Exception e) {

					if (j < fileArr.length - 1) {
						continue;
					} else {
						throw e;
					}
				}
			}

		}
	}

	// public List<String> getFormNames(String licenseLic){
	//
	// InputStream myCLMLicenseStream =
	// this.getClass().getResourceAsStream("CLM.lic");
	//
	// InputStream myLicenseStream = new FileInputStream(licenseLic);
	// InputStream myPublicKeyStream =
	// this.getClass().getResourceAsStream("key.public");
	//
	// LicenseManager mgr =
	// new LicenseManager(myCLMLicenseStream, myLicenseStream,
	// myPublicKeyStream);
	//
	//
	// }

	// public static void main(String[] args) {
	// new LicenseCheck().check(args[0], args[1], args[2], args[3], args[4]);
	//
	// }
}
