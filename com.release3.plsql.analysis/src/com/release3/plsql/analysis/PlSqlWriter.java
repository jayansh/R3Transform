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

import javax.xml.bind.JAXBException;

import com.converter.toolkit.sqlparsing.CodeProcessor;
import com.converter.toolkit.ui.FormsObject;
import com.converter.toolkit.ui.JAXBXMLWriter;
import com.oracle.xmlns.forms.Block;
import com.oracle.xmlns.forms.FormModule;
import com.oracle.xmlns.forms.Item;
import com.oracle.xmlns.forms.Module;
import com.oracle.xmlns.forms.ProgramUnit;
import com.oracle.xmlns.forms.Trigger;
import com.release3.customization.R3;
import com.release3.plsqlgen.R3PlSql;
import com.release3.plsqlgen.R3PlSqlFactory;
import com.release3.tf.javasql.JavaSqlGeneration;

/**
 * 
 * @author Java Entry point for PlSql trigger/ProgramUnit and java sql writers.
 * 
 */
public class PlSqlWriter {
	private CodeProcessor sqlparser;
	private Module originalModule;
	

	public PlSqlWriter(Module originalModule) {
		this.originalModule = originalModule;
		
		
	}

	/**
	 * This method will retrieve the plsql triggers and program units from the
	 * form and write to new destination xml file.
	 * 
	 * @param formModule
	 *            - Original Form containing module
	 * @param destXmlPath
	 *            - destination xml path
	 * @throws IOException
	 */

	public void plsqlXmlFileWriter(String destXmlPath, R3 r3PreCustomization)
			throws IOException {
		FormModule formModule = originalModule.getFormModule();
		List<FormsObject> formsChildren = formModule.getChildren();

		R3PlSqlFactory plsqlFactory = new R3PlSqlFactory();
		R3PlSql r3PlSqlType = plsqlFactory.createR3PlSql();
		r3PlSqlType.setFormName(formModule.getName());

		for (FormsObject formsObject : formsChildren) {
			if (formsObject instanceof Block) {
				Block block = ((Block) formsObject);
				List<FormsObject> blockChildren = block.getChildren();
				for (FormsObject blockChild : blockChildren) {

					if (blockChild instanceof Trigger) {
						Trigger trigger = (Trigger) blockChild;
						R3PlSql.R3Trigger r3Trigger = plsqlFactory
								.createR3PlSqlR3Trigger();
						r3Trigger.setTrigger(trigger);
						r3Trigger.setBlockName(block.getName());
						r3Trigger.setName(block.getName() + "."
								+ trigger.getName());
						r3PlSqlType.getR3Trigger().add(r3Trigger);
					} else if (blockChild instanceof Item) {
						Item oldItem = (Item) blockChild;
						List<FormsObject> itemChildren = oldItem.getChildren();

						for (FormsObject itemChild : itemChildren) {
							if (itemChild instanceof Trigger) {
								R3PlSql.R3Trigger r3Trigger = plsqlFactory
										.createR3PlSqlR3Trigger();
								Trigger trigger = (Trigger) itemChild;

								r3Trigger.setTrigger(trigger);
								r3Trigger.setBlockName(block.getName());
								r3Trigger.setItemName(oldItem.getName());
								r3PlSqlType.getR3Trigger().add(r3Trigger);

								r3Trigger.setName(block.getName() + "."
										+ oldItem.getName() + "."
										+ trigger.getName());
							}
						}

					}
				}

			} else if (formsObject instanceof Trigger) {
				Trigger trigger = (Trigger) formsObject;
				R3PlSql.R3Trigger r3Trigger = plsqlFactory
						.createR3PlSqlR3Trigger();
				r3Trigger.setTrigger(trigger);
				r3PlSqlType.getR3Trigger().add(r3Trigger);
				r3Trigger.setName(trigger.getName());
			} else if (formsObject instanceof ProgramUnit) {
				ProgramUnit programUnit = (ProgramUnit) formsObject;

				r3PlSqlType.getProgramUnit().add(programUnit);

			}
		}
		try {
			JAXBXMLWriter.writetoXML(destXmlPath, r3PlSqlType);
		} catch (JAXBException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Module module = new Module();
		// Module module = objFactory.createModule();
		// module.setFormModule(newFormModule);

		/*
		 * Customization generation
		 */
		File parentFile = new File(destXmlPath).getParentFile();
		R3CustomWriter.getPlSqlTextWriter().setOriginalModule(originalModule);
		R3CustomWriter.getPlSqlTextWriter()
				.writetoText(parentFile, r3PlSqlType);

		/*
		 * Java plsql xml generation
		 */
		JavaSqlGeneration javaSqlGen = new JavaSqlGeneration(originalModule);
		javaSqlGen.generateJavaSql(
				parentFile + File.separator + r3PlSqlType.getFormName()
						+ "_R3JavaSql.xml", r3PlSqlType);

		/*
		 * core PlSql xml generation
		 */
		R3CorePlSqlXmlGeneration r3CorePlSqlXmlGen = new R3CorePlSqlXmlGeneration(
				originalModule, parentFile, r3PlSqlType, r3PreCustomization);
		r3CorePlSqlXmlGen.generatePlSql();
		/*
		 * ProgramUnit xml file generation
		 */
		R3ProgramUnitGeneration r3ProgramUnitGen = new R3ProgramUnitGeneration(
				originalModule, parentFile, r3PlSqlType, r3PreCustomization);
		r3ProgramUnitGen.generatePlSql();

	}
}
