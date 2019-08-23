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
package com.release3.tf.plsql.process;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.Platform;

import com.converter.toolkit.ui.JAXBXMLReader;
import com.release3.formsmap.R3FormsMap;
import com.release3.formsmap.R3FormsMap.Rec;
import com.release3.javasql.JavaPlSqlType;
import com.release3.programunitgen.R3ProgramUnit;
import com.release3.programunitgen.R3ProgramUnitGen;
import com.release3.programunitgen.R3ProgramUnitGenFactory;
import com.release3.tf.javagen.PlSqlParser;
import com.release3.tf.migration.model.MigrationForm;
import com.release3.tf.plsql.parser.ProcFuncParser;

public class ProgramUnitAnalysis {
	private MigrationForm migrationForm;
	private StringBuffer plsqlStrBuffer = new StringBuffer();

	public ProgramUnitAnalysis(MigrationForm migrationForm) {
		this.migrationForm = migrationForm;
	}

	private R3ProgramUnitGen r3ProgramUnitGen;

	public R3ProgramUnitGen readProgramUnitPlSqlXml(File xmlFile) {

		JAXBXMLReader xmlReader = new JAXBXMLReader();
		r3ProgramUnitGen = (R3ProgramUnitGen) xmlReader.init(xmlFile,
				R3ProgramUnitGenFactory.class);

		return r3ProgramUnitGen;
	}

	public List<R3ProgramUnit> getProgramUnitList() {
		if (r3ProgramUnitGen == null) {
			return new ArrayList<R3ProgramUnit>();
		}
		return r3ProgramUnitGen.getR3ProgramUnit();
	}

	public R3ProgramUnitGen getR3ProgramUnitGen() {
		return r3ProgramUnitGen;
	}

	public String getPUXmlFile() {
		String xmlParentFilePath = Platform.getInstanceLocation().getURL()
				.getPath()
				+ File.separator
				+ "xmlFiles"
				+ File.separator
				+ r3ProgramUnitGen.getFormName();

		File xmlParentFile = new File(xmlParentFilePath);
		if (!xmlParentFile.exists()) {
			xmlParentFile.mkdirs();
		}
		String xmlPath = xmlParentFilePath + File.separator
				+ r3ProgramUnitGen.getFormName() + "_R3PU_After.xml";
		return xmlPath;

	}

	public String getPUTxtFile() {
		String txtParentFilePath = getRootDirectory();

		File txtParentFile = new File(txtParentFilePath);
		if (!txtParentFile.exists()) {
			txtParentFile.mkdirs();
		}
		String txtPath = txtParentFilePath + File.separator
				+ r3ProgramUnitGen.getFormName() + "_R3PU.txt";
		return txtPath;

	}

	public void processCode() {
		for (R3ProgramUnit r3PrgramUnit : getProgramUnitList()) {
			if ((r3PrgramUnit.isToBeMigrate() != null && r3PrgramUnit
					.isToBeMigrate())
					&& (r3PrgramUnit.isMoveToDB() == null || !r3PrgramUnit
							.isMoveToDB())) {
				String plsqlText = r3PrgramUnit.getR3ProgramUnit()
						.getProgramUnitText().replace("&#10;", "\n");
				plsqlStrBuffer.append(plsqlText + "\n");
			}
		}
		try {
			FileWriter fstream = new FileWriter(getPUTxtFile());
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(plsqlStrBuffer.toString());
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getRootDirectory() {
		String parentFilePath = Platform.getInstanceLocation().getURL()
				.getPath()
				+ File.separator
				+ "xmlFiles"
				+ File.separator
				+ r3ProgramUnitGen.getFormName();
		return parentFilePath;
	}

	public StringBuffer getPlsqlStrBuffer() {
		return plsqlStrBuffer;
	}

	public void autoCheck(String assiciatePU,boolean isMigrate, boolean isMovetoDB) {
		for (R3ProgramUnit programUnit : r3ProgramUnitGen.getR3ProgramUnit()) {
			if (programUnit.getR3ProgramUnit().getName()
					.equalsIgnoreCase(assiciatePU)) {
				programUnit.setToBeMigrate(true);
				programUnit.setMoveToDB(isMovetoDB);
				programUnit.setToBeMigrate(isMigrate);
			}
		}
	}

}
