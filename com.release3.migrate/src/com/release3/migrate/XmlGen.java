package com.release3.migrate;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import com.release3.tf.core.form.Settings;

@Deprecated
/**
 * use com.release3.tf.core.form.XmlGen instead
 */
public class XmlGen {
	
	public static void makeXml( String rdfFile) {
		Runtime rt = Runtime.getRuntime();

		try {

			String batchCommand = Settings.getSettings().getOracleDeveloperSuite() + File.separator + "bin"
			+ File.separator + "frmf2xml.bat OVERWRITE=YES " + rdfFile ;
				
			Process proc = rt.exec(batchCommand );
			BufferedReader in = new BufferedReader(new InputStreamReader(proc
					.getInputStream()));
			String line = in.readLine();
			while (line != null) {
				line = in.readLine();

			}
		

			proc.waitFor();

		} catch (Exception ex) {

		}
	}
}
