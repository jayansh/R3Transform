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
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.Platform;

import com.converter.toolkit.ui.JAXBXMLReader;
import com.release3.javasql.JavaPlSqlType;
import com.release3.javasql.R3JavaSqlGen;
import com.release3.javasql.R3JavaSqlGenFactory;
import com.release3.r3coreplsqlgen.R3CorePlSql;
import com.release3.r3coreplsqlgen.R3CorePlSqlGen;
import com.release3.r3coreplsqlgen.R3CorePlSqlGenFactory;
import com.release3.tf.javagen.PlSqlParser;
import com.release3.tf.migration.model.MigrationForm;
import com.release3.tf.plsql.parser.ProcFuncParser;

public class NonCRUDAnalysis {
	private MigrationForm migrationForm;
	private StringBuffer plsqlStrBuffer = new StringBuffer();

	public NonCRUDAnalysis(MigrationForm migrationForm) {
		this.migrationForm = migrationForm;
	}

	private R3JavaSqlGen javaSql;

	// private List<JavaPlSqlType> noncrudList = new ArrayList<JavaPlSqlType>();

	public R3JavaSqlGen readJavaSqlXml(File xmlFile) {
		JAXBXMLReader xmlReader = new JAXBXMLReader();
		javaSql = (R3JavaSqlGen) xmlReader.init(xmlFile,
				R3JavaSqlGenFactory.class);
		return javaSql;
		// List<R3CorePlSql> r3CorePlSqlList = corePlSql.getR3CorePlSql();
		// for (R3CorePlSql r3CorePlSql : r3CorePlSqlList) {
		// R3CorePlSql crud = r3CorePlSql;
		// crudList.add(crud);
		// }
	}

	public List<JavaPlSqlType> getNonCrudList() {
		if (javaSql == null) {
			return new ArrayList<JavaPlSqlType>();
		}
		return javaSql.getJavaSql();
	}

	public R3JavaSqlGen getR3JavaSqlGen() {
		return javaSql;
	}

	public String getNONCRUDXmlFile() {
		String xmlParentFilePath = Platform.getInstanceLocation().getURL()
				.getFile()
				+ File.separator
				+ "xmlFiles"
				+ File.separator
				+ javaSql.getName();

		File xmlParentFile = new File(xmlParentFilePath);
		if (!xmlParentFile.exists()) {
			xmlParentFile.mkdirs();
		}
		String xmlPath = xmlParentFilePath + File.separator + javaSql.getName()
				+ "_NONCRUD.xml";
		return xmlPath;

	}

	public String getNONCRUDTxtFile() {
		String txtParentFilePath = Platform.getInstanceLocation().getURL()
				.getFile()
				+ File.separator
				+ "xmlFiles"
				+ File.separator
				+ javaSql.getName();

		File txtParentFile = new File(txtParentFilePath);
		if (!txtParentFile.exists()) {
			txtParentFile.mkdirs();
		}
		String txtPath = txtParentFilePath + File.separator + javaSql.getName()
				+ "_NONCRUD.txt";
		return txtPath;

	}

	public void processCode() {
		for (JavaPlSqlType javaPlSqlType : getNonCrudList()) {
			String plsqlText = javaPlSqlType.getJavaPlSqlText().replace(
					"&#10;", "\n");

			if (( javaPlSqlType
					.isToBeMigrate())
					&& (javaPlSqlType.isMoveToDB() == null || !javaPlSqlType
							.isMoveToDB())) {
				// PlSqlParser parser = new PlSqlParser();
				// plsqlText = parser.migrateToJava(plsqlText, getR3JavaSqlGen()
				// .getName(), null);
				// javaPlSqlType.setJavaPlSqlText(plsqlText);
				plsqlStrBuffer.append("PROCEDURE "
						+ javaPlSqlType.getJavaPlSql().replace('-', '_')
								.replace('.', '_') + " IS \n");
				plsqlStrBuffer.append(plsqlText + "\n");
			}
		}

		try {
			FileWriter fstream = new FileWriter(getNONCRUDTxtFile());
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(plsqlStrBuffer.toString());
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// ProcFuncParser parser = new ProcFuncParser();
		// parser.process(plsqlStrBuffer.toString());
	}

	public MigrationForm getMigrationForm() {
		return migrationForm;
	}

	public StringBuffer getPlsqlStrBuffer() {
		return plsqlStrBuffer;
	}

}
