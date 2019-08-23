package com.release3.tf.plsql.process;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import com.release3.formsmap.R3FormsMap;
import com.release3.tf.core.Utils;
import com.release3.tf.core.form.Settings;
import com.release3.tf.migration.model.MigrationForm;
import com.release3.tf.plsql.parser.ProcFuncParser;
import com.release3.transform.pref.PreferenceConstants;
import com.release3.transform.pref.PreferencePlugin;

public class FormLogicAnalysis {
	private NonCRUDAnalysis nonCRUDAnalysis;
	private CRUDAnalysis crudAnalysis;
	private ProgramUnitAnalysis puAnalysis;
	private MigrationForm migrationForm;
	private R3FormsMap r3FormsMap;

	public FormLogicAnalysis(MigrationForm migrationForm, R3FormsMap r3FormsMap) {
		this.migrationForm = migrationForm;
		this.nonCRUDAnalysis = new NonCRUDAnalysis(migrationForm);
		this.crudAnalysis = new CRUDAnalysis(migrationForm);
		this.puAnalysis = new ProgramUnitAnalysis(migrationForm);
		this.r3FormsMap = r3FormsMap;
	}

	public NonCRUDAnalysis getNonCRUDAnalysis() {
		return nonCRUDAnalysis;
	}

	public CRUDAnalysis getCRUDAnalysis() {
		return crudAnalysis;
	}

	public ProgramUnitAnalysis getPuAnalysis() {
		return puAnalysis;
	}

	public void writePlSqlToTxt() {
		StringBuffer plsqlStrBuffer = crudAnalysis.getPlsqlStrBuffer();
		plsqlStrBuffer.append("\n");
		plsqlStrBuffer.append(nonCRUDAnalysis.getPlsqlStrBuffer());
		plsqlStrBuffer.append("\n");
		plsqlStrBuffer.append(puAnalysis.getPlsqlStrBuffer());
		String plsqlStr = plsqlStrBuffer.toString().trim();
		try {
			File sourceDir = new File(migrationForm.getXmlFolderPath()
					+ File.separator + "SourceForms");
			if (!sourceDir.exists()) {
				sourceDir.mkdirs();
			}
			String sourcePath = migrationForm.getXmlFolderPath()
					+ File.separator + "SourceForms" + File.separator
					+ migrationForm.getFormName() + "NonCrud.sql";

			FileWriter fstream = new FileWriter(sourcePath);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(plsqlStr);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String functionMapFileName = "function_map.xml";

	public String convertToJavaUsingSqlways() {
		String sourcePath = migrationForm.getXmlFolderPath() + File.separator
				+ "SourceForms" + File.separator + migrationForm.getFormName()
				+ "NonCrud.sql";
		String result = null;
		File sourceFile = new File(sourcePath);
		if (!sourceFile.exists() || sourceFile.length() == 0) {
			return result;
		}
		String destPath = migrationForm.getXmlFolderPath() + File.separator
				+ "SourceForms" + File.separator + "rslt_forms";
		copyFunctionMapXmlFile(
				new File(migrationForm.getXmlFolderPath() + File.separator
						+ "SourceForms" + File.separator + functionMapFileName),
				PreferencePlugin
						.getDefault()
						.getPreferenceStore()
						.getBoolean(
								PreferenceConstants.APPLICATION_SQLWAYS_OVERWRITE_FUNCTION_MAP_XML));
		Runtime rt = Runtime.getRuntime();

		try {
			String sqlWaysHomePath = PreferencePlugin.getDefault()
					.getPreferenceStore()
					.getString(PreferenceConstants.APPLICATION_SQLWAYS_HOME);
			// sqlWaysHomePath =
			// "C:\\Release3\\R3Transform53M1_24May2012_win32_x86\\Ispirer\\SQLWays6";

			String sqlwaysCommand = sqlWaysHomePath + File.separator
					+ "SQLWays.exe /D=FIXED /SOURCE=ORACLE /TARGET=Java /F="
					+ sourcePath + " /Dir=" + destPath + " /INI="
					+ sqlWaysHomePath + File.separator + "sqlways.ini";
			System.out.println(sqlwaysCommand);
			Process proc = rt.exec(sqlwaysCommand);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					proc.getInputStream()));
			String line = in.readLine();
			int error = 0;
			while (line != null) {
				if (line.indexOf("The product license has expired") > -1) {
					error = 1;
					result = "Ispirer's SQLWays license has expired.";
					break;
				}else if (line.indexOf("The software is unregistered") > -1){
					error = 1;
					result = "Ispirer's SQLWays is unregistered.";
				}
				line = in.readLine();
			}

			proc.waitFor();
			if (error == 0) {
				result = "Script, " + sourcePath + "\n"
						+ " is Converted to Java, " + destPath + File.separator
						+ migrationForm.getFormName() + "NonCrud.java";
			}
			System.out.println(result);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	/**
	 * 
	 * @param targetFile
	 *            - target file
	 * @param overwite
	 *            - true to overwrite existing file else false.
	 */
	private void copyFunctionMapXmlFile(File targetFile, boolean overwite) {
		File sourceFunctionXmlFile = new File(Settings.getSettings()
				.getFmbRootDir() + File.separator + functionMapFileName);
		// if(sourceFunctionXmlFile.exists()){
		try {
			Utils.copyFile(sourceFunctionXmlFile, targetFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// }else{
		// System.out.println("File not found");
		// }
	}

	public R3FormsMap getR3FormsMap() {
		return r3FormsMap;
	}

}
