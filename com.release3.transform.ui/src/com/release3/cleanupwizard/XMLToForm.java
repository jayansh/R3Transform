package com.release3.cleanupwizard;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import com.release3.tf.core.form.Settings;

@Deprecated
/**
 * Moved to com.release3.tf.core.form.XMLToForm
 */
public class XMLToForm {
	public void xml2form(File srcfile) {

		String devSuitePath = Settings.getSettings().getOracleDeveloperSuite();
		String dbConnectionString = "USERID="
				+ Settings.getSettings().getDbSrcUser() + "/"
				+ Settings.getSettings().getDbSrcPassword() + "@"
				+ Settings.getSettings().getDbFormConnectionSID()+" ";
		String command = devSuitePath + File.separator + "bin" + File.separator
				+ "frmxml2f.bat OVERWRITE=YES " + dbConnectionString
				// + File.separator + "frmxml2f.bat "
				+ srcfile;
		System.out.println(command);
		try {
			Process p = Runtime.getRuntime().exec("cmd.exe /C " + command);
			BufferedReader in = new BufferedReader(new InputStreamReader(p
					.getInputStream()));
			String line = in.readLine();
			while (line != null) {
				line = in.readLine();
				System.out.println(line);
			}
			in.close();
			p.waitFor();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// }
		}
	}

	// public static void main(String args[]){
	// String filePath =
	// "C:\\Cleanup\\Javier\\cleanup_test\\new\\PROMPTEST_fmb.xml";
	// File file = new File(filePath);
	// XMLtoForm xmltoform = new XMLtoForm();
	// xmltoform.xml2form(file);
	// }
}
