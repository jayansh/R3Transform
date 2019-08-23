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
import com.release3.javasql.JavaPlSqlType;
import com.release3.r3coreplsqlgen.R3CorePlSql;
import com.release3.r3coreplsqlgen.R3CorePlSqlGen;
import com.release3.r3coreplsqlgen.R3CorePlSqlGenFactory;
import com.release3.tf.javagen.PlSqlParser;
import com.release3.tf.migration.model.MigrationForm;
import com.release3.tf.plsql.parser.ProcFuncParser;

public class CRUDAnalysis {
	private MigrationForm migrationForm;
	private StringBuffer plsqlStrBuffer = new StringBuffer();

	public CRUDAnalysis(MigrationForm migrationForm) {
		this.migrationForm = migrationForm;
	}

	private R3CorePlSqlGen corePlSql;

	// private List<R3CorePlSql> crudList = new ArrayList<R3CorePlSql>();

	public R3CorePlSqlGen readCorePlSqlXml(File xmlFile) {
		JAXBXMLReader xmlReader = new JAXBXMLReader();
		corePlSql = (R3CorePlSqlGen) xmlReader.init(xmlFile,
				R3CorePlSqlGenFactory.class);
		return corePlSql;
	}

	public List<R3CorePlSql> getCrudList() {
		if (corePlSql == null) {
			return new ArrayList<R3CorePlSql>();
		}
		return corePlSql.getR3CorePlSql();
	}

	public R3CorePlSqlGen getR3CorePlSqlGen() {
		return corePlSql;
	}

	public String getCRUDXmlFile() {
		String xmlParentFilePath = Platform.getInstanceLocation().getURL()
				.getPath()
				+ File.separator
				+ "xmlFiles"
				+ File.separator
				+ corePlSql.getName();

		File xmlParentFile = new File(xmlParentFilePath);
		if (!xmlParentFile.exists()) {
			xmlParentFile.mkdirs();
		}
		String xmlPath = xmlParentFilePath + File.separator
				+ corePlSql.getName() + "_CRUD.xml";
		return xmlPath;

	}

	public String getPlSqlAfterTextFile() {
		String parentFilePath = getRootDirectory();

		File xmlParentFile = new File(parentFilePath);
		if (!xmlParentFile.exists()) {
			xmlParentFile.mkdirs();
		}
		String xmlPath = parentFilePath + File.separator + corePlSql.getName()
				+ "_after.sql";
		return xmlPath;

	}

	public String getPlSqlTextFile() {
		String parentFilePath = getRootDirectory();

		File xmlParentFile = new File(parentFilePath);
		if (!xmlParentFile.exists()) {
			xmlParentFile.mkdirs();
		}
		String xmlPath = parentFilePath + File.separator + corePlSql.getName()
				+ "_CRUD.txt";
		return xmlPath;

	}

	public String getRootDirectory() {
		String parentFilePath = Platform.getInstanceLocation().getURL()
				.getPath()
				+ File.separator
				+ "xmlFiles"
				+ File.separator
				+ corePlSql.getName();
		return parentFilePath;
	}

	public void processCode() {
		for (R3CorePlSql r3CorePlSql : getCrudList()) {
			if ((r3CorePlSql
					.isToBeMigrate()) && (!r3CorePlSql.isMoveToDB())) {
				String plsqlText = r3CorePlSql.getPlSqlText().replace("&#10;",
						"\n");
				plsqlStrBuffer.append(plsqlText + "\n");
			}
		}
		try {
			FileWriter fstream = new FileWriter(getPlSqlTextFile());
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

	public StringBuffer getPlsqlStrBuffer() {
		return plsqlStrBuffer;
	}

}
