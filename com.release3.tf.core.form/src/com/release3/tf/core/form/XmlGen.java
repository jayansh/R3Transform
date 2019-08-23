package com.release3.tf.core.form;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import com.release3.tf.core.form.Settings;

public class XmlGen {
	
	
	private static void frm2Xml(String batchCommand) throws Exception{
		
		Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec(batchCommand);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					proc.getInputStream()));
			String line = in.readLine();
			while (line != null) {
				line = in.readLine();

			}

			proc.waitFor();

		
	}
	
	
	public static void makeXml( String rdfFile) {
		
		String frm2XmlPath = Settings.getSettings().getOracleDeveloperSuite()+ File.separator + "bin"+ File.separator + "frmf2xml.bat";
		try {
			//if 10g developer suite
			File frm2Xml = new File(frm2XmlPath);
			if(frm2Xml.exists()){
				String batchCommand = frm2XmlPath
				+  " OVERWRITE=YES " + rdfFile ;
				frm2Xml(batchCommand);
				
			}else {
				//if 11g developer suite
				frm2XmlPath = Settings.getSettings().getOracleDeveloperSuite()+ File.separator + "forms"+ File.separator +"templates"+File.separator +"scripts"+File.separator + "frmf2xml.bat";
				frm2Xml = new File(frm2XmlPath);
				if(frm2Xml.exists()){
				String batchCommand = frm2XmlPath
				+  " OVERWRITE=YES " + rdfFile ;
				frm2Xml(batchCommand);
				}
				else{
					throw new Exception("frmf2Xml.bat not found. Oracle Forms Developer may not be configured properly." );
				}
			}
			
				
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
