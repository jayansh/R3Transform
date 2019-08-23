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
package com.release3.tf.form.anlysis;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.converter.toolkit.ui.FormsObject;
import com.converter.toolkit.ui.JAXBXMLWriter;
import com.oracle.xmlns.forms.Block;
import com.oracle.xmlns.forms.FormModule;
import com.oracle.xmlns.forms.Item;
import com.oracle.xmlns.forms.Module;
import com.oracle.xmlns.forms.ProgramUnit;
import com.oracle.xmlns.forms.Trigger;
import com.release3.dbconnect.DBCommand;
import com.release3.dbplsql.DBPlSql;
import com.release3.dbplsql.ObjectFactory;
import com.release3.formsmap.R3FormsMap;
import com.release3.formsmap.R3FormsMap.Rec;
import com.release3.formsmap.R3FormsMapFactory;
import com.release3.tf.core.form.Settings;

public class FormAnalysisReportGen {
	private Module originalModule;

	public FormAnalysisReportGen(Module originalModule) {
		this.originalModule = originalModule;

	}

	public void formsAnalysisReportWriter(String destXmlPath)
			throws IOException {
		FormModule formModule = originalModule.getFormModule();
		List<FormsObject> formsChildren = formModule.getChildren();
		R3FormsMapFactory formsMapFactory = new R3FormsMapFactory();
		R3FormsMap r3FormsMap = formsMapFactory.createR3FormsMap();
		r3FormsMap.setName(formModule.getName());
		for (FormsObject formsObject : formsChildren) {
			if (formsObject instanceof Block) {
				Block block = (Block) formsObject;
				List<FormsObject> blockObjects = block.getChildren();
				for (FormsObject blockObject : blockObjects) {
					if (blockObject instanceof Item) {
						Item item = (Item) blockObject;
						List<FormsObject> itemObjects = item.getChildren();
						for (FormsObject itemObject : itemObjects) {
							if (itemObject instanceof Trigger) {

								Trigger trigger = (Trigger) itemObject;
								Rec rec = formsMapFactory.createR3FormsMapRec();
								rec.setBlockName(block.getName());
								rec.setItemName(item.getName());
								rec.setName(trigger.getName());
								rec.setSqlStatement(trigger.getTriggerText());
								rec.setType(trigger.getType());
								r3FormsMap.getRec().add(rec);
							}
						}
					}

					if (blockObject instanceof Trigger) {
						Trigger trigger = (Trigger) blockObject;
						Rec rec = formsMapFactory.createR3FormsMapRec();
						rec.setBlockName(block.getName());
						rec.setName(trigger.getName());
						rec.setSqlStatement(trigger.getTriggerText());
						rec.setType(trigger.getType());
						r3FormsMap.getRec().add(rec);
					}
				}

			} else if (formsObject instanceof Trigger) {
				Trigger trigger = (Trigger) formsObject;
				Rec rec = formsMapFactory.createR3FormsMapRec();
				rec.setName(trigger.getName());
				rec.setSqlStatement(trigger.getTriggerText());
				rec.setType(trigger.getType());
				r3FormsMap.getRec().add(rec);
			} else if (formsObject instanceof ProgramUnit) {
				ProgramUnit programUnit = (ProgramUnit) formsObject;
				Rec rec = formsMapFactory.createR3FormsMapRec();
				rec.setName(programUnit.getName());
				rec.setSqlStatement(programUnit.getProgramUnitText().replace("&#10;", "\n"));
				rec.setType(programUnit.getType());
				r3FormsMap.getRec().add(rec);
			}
		}
		try {
			JAXBXMLWriter.writetoXML(destXmlPath, r3FormsMap);
		} catch (JAXBException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void writeDBPlSql(String destXmlPath) throws IOException {
		ObjectFactory dbPlsqlTypeFactory = new ObjectFactory();
		DBPlSql dbPlSql = dbPlsqlTypeFactory.createDBPlSql();
		Connection conn = DBCommand.getConnection();
		// String query =
		// "SELECT OBJECT_NAME,OBJECT_TYPE, PROCEDURE_NAME FROM ALL_PROCEDURES WHERE OWNER='"
		// + Settings.getSettings().getDbSrcUser().toUpperCase() + "'";
		String query = "select p.object_name,o.object_type,p.PROCEDURE_NAME "
				+ " from all_procedures p "
				+ " join all_objects o "
				+ " on p.owner = o.owner "
				+ " and p.object_name = o.object_name "
				+ " where o.object_type in ('PROCEDURE','PACKAGE','FUNCTION') and o.OWNER='"
				+ Settings.getSettings().getDbSrcUser().toUpperCase() + "'";
		System.out.println(query);
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				DBPlSql.Row dbPlSqlRow = dbPlsqlTypeFactory.createDBPlSqlRow();
				String objectName = rs.getString("OBJECT_NAME");
				dbPlSqlRow.setObjectName(objectName);
				String objectType = rs.getString("OBJECT_TYPE");
				dbPlSqlRow.setObjectType(objectType);
				String procName = rs.getString("PROCEDURE_NAME");
				dbPlSqlRow.setFuncProcName(procName);
				dbPlSql.getRow().add(dbPlSqlRow);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			JAXBXMLWriter.writetoXML(destXmlPath, dbPlSql);
		} catch (JAXBException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
