package com.release3.toolkit;

import com.cinnabarsystems.clm.Attribute;
import com.cinnabarsystems.clm.Feature;
import com.cinnabarsystems.clm.InvalidLicenseFormatException;
import com.cinnabarsystems.clm.License;
import com.cinnabarsystems.clm.LicenseManager;
import com.cinnabarsystems.clm.Licensee;

import com.oracle.xmlns.forms.Block;
import com.oracle.xmlns.forms.Module;

import com.converter.premigration.FileFilterExt;
import com.converter.toolkit.ui.FormsObject;
import com.converter.toolkit.ui.JAXBFMBReader;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.InputStream;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

public class LicenseCheck {

	/*
	 * <java classname="com.converter.toolkit.LicenseCheck" failonerror="true">
	 * <arg value="${template}\license.lic"/> <arg value="TF.3.0.0."/> <arg
	 * value="${FMBFile}.fmb"/> <arg
	 * value="${ViewBaseDir}/src/${applicationName}/${FMBFile}/${FMBFile}.xml"/>
	 * <arg value="NA"/>
	 */

	public void check(String licenseLicDir, String product, String feature,
			String file, String licenseeName)
			throws InvalidLicenseFormatException {
		File dir = new File(licenseLicDir);
		if (dir.isDirectory()) {
			String[] str = { "lic" };
			FileFilter filter = new FileFilterExt("*.lic", str);
			File[] fileArr = dir.listFiles(filter);
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

					License license = mgr.acquireLicense(product, feature
							.toLowerCase());
					Feature ft = license.getLicensee(licenseeName).getFeature(
							feature.toLowerCase());

					Licensee licensee = license.getLicensee(licenseeName);

					Attribute issueDateAttr = licensee
							.getAttribute("IssueDate");
					Attribute validityAttr = licensee.getAttribute("Validity");
					

					// Fri Nov 12 00:56:50
					if (issueDateAttr != null) {
						String issueDateStr = (String) issueDateAttr.getValue();
						SimpleDateFormat dateFormat = new SimpleDateFormat(
								"EEE MMM d HH:mm:ss z yyyy");
						Calendar currentCal = Calendar.getInstance();
						Calendar cal1 = Calendar.getInstance();
						Calendar cal2 = Calendar.getInstance();
						Date issueDate = dateFormat.parse(issueDateStr);
						cal1.setTime(issueDate);
						if (validityAttr != null) {
							Integer validity = (Integer) validityAttr
									.getValue();
							
							cal2.setTime(issueDate);
							cal2.add(Calendar.DATE, validity);
							if (!(cal1.before(currentCal) && currentCal
									.before(cal2))) {
								System.out.println("License Expired!!!");
								throw new Exception();
							}
						}
						
					}
					Module mod = new JAXBFMBReader().init(file);

					Iterator itr = mod.getFormModule().getChildren().iterator();
					FormsObject obj = null;
					Vector<Block> blkVect = new Vector<Block>();
					while (itr.hasNext()) {
						obj = (FormsObject) itr.next();
						if (obj instanceof Block)
							blkVect.add((Block) obj);
					}

					if (blkVect.size() + 1 != ft.getAttributes().size()) {
						throw new Exception();
					}

					int i = 0;
					for (Iterator blocks = blkVect.iterator(); blocks.hasNext(); i++) {
						Block blk = (Block) blocks.next();

						Attribute attr = (Attribute) ft.getAttribute("block"
								+ i);
						if (attr == null) {
							throw new Exception();
							// lets try another license
						}
						if (!((String) (attr.getValue())).replace("$", "S")
								.equals(blk.getName())) {
							throw new Exception();
						}
					}
					// No exception occurs
					break;
				} catch (Exception e) {

					if (j < fileArr.length - 1) {
						continue;
					} else {
						throw new InvalidLicenseFormatException();
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
