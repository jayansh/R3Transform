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
package com.release3.plsql.analysis;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.xml.bind.JAXBException;

import com.converter.toolkit.sqlparsing.CodeProcessor;
import com.converter.toolkit.ui.JAXBXMLWriter;
import com.oracle.xmlns.forms.Module;
import com.oracle.xmlns.forms.ProgramUnit;
import com.release3.customization.R3;
import com.release3.plsqlgen.R3PlSql;
import com.release3.programunitgen.R3ProgramUnit;
import com.release3.programunitgen.R3ProgramUnitGen;
import com.release3.programunitgen.R3ProgramUnitGenFactory;

public class R3ProgramUnitGeneration {
	Properties props = new Properties();
	private Module originalModule;
	private CodeProcessor sqlparser;
	private File destParentFile;
	private R3PlSql plsqlModule;
	private R3 preCustomization;

	public R3ProgramUnitGeneration(Module originalModule, File destParentFile,
			R3PlSql plsqlModule, R3 preCustomization) {
		this.originalModule = originalModule;
		this.sqlparser = new CodeProcessor();
		this.destParentFile = destParentFile;
		this.plsqlModule = plsqlModule;
		this.preCustomization = preCustomization;
	}

	public void generatePlSql() throws IOException {

		String r3CorePlSqlXmlPath = destParentFile + File.separator
				+ plsqlModule.getFormName() + "_R3PU.xml";
		// String r3PreCustomizationXmlPath = destParentFile + File.separator
		// + plsqlModule.getFormName() + "_R3PreCustomization.xml";
		List<ProgramUnit> r3PUList = plsqlModule.getProgramUnit();
		R3ProgramUnitGenFactory r3PUGenfactory = new R3ProgramUnitGenFactory();
		R3ProgramUnitGen r3PUGen = r3PUGenfactory.createR3ProgramUnitGen();

		for (ProgramUnit programUnit : r3PUList) {
			if (!isRejectedProgramUnit(programUnit.getName())) {
//				programUnit.setProgramUnitText(programUnit.getProgramUnitText()
//						.replace("&#10;", "\n"));
				R3ProgramUnit r3ProgramUnit = r3PUGenfactory
						.createR3ProgramUnit();
				r3ProgramUnit.setR3ProgramUnit(programUnit);
				r3ProgramUnit.setToBeMigrate(false);
				r3PUGen.getR3ProgramUnit().add(r3ProgramUnit);
			}
		}
		r3PUGen.setFormName(plsqlModule.getFormName());
		try {
			JAXBXMLWriter.writetoXML(r3CorePlSqlXmlPath, r3PUGen);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// try {
		// JAXBXMLWriter.writetoXML(r3PreCustomizationXmlPath,
		// preCustomization);
		// } catch (JAXBException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

	}

	public boolean isRejectedProgramUnit(String programUnit) {

		for (String rejectedPU : PlSqlAnalysisPlugin.rejectedProgramUnits) {
			if (rejectedPU.equalsIgnoreCase(programUnit)) {
				return true;
			}
		}
		return false;
	}
}
