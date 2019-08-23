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
package com.release3.tf.plsql.process.wizard;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;

import com.converter.toolkit.ui.JAXBXMLWriter;
import com.release3.dbconnect.DBCommand;
import com.release3.tf.plsql.process.CRUDAnalysis;
import com.release3.tf.plsql.process.FormLogicAnalysis;
import com.release3.tf.plsql.process.NonCRUDAnalysis;
import com.release3.tf.plsql.process.ProgramUnitAnalysis;
import com.release3.tf.plsql.process.R3PlSqlTextWriter;
import com.release3.transform.pref.PreferenceConstants;
import com.release3.transform.pref.PreferencePlugin;

public class FormLogicProcessingWizard extends Wizard {
	private WizardPage page1;
	private WizardPage page2;
	private WizardPage page3;
	private FormLogicAnalysis formLogicAnalysis;

	public FormLogicProcessingWizard(FormLogicAnalysis formLogicAnalysis) {
		setWindowTitle("Form Logic Processing Wizard");
		this.formLogicAnalysis = formLogicAnalysis;

	}

	@Override
	public void addPages() {
		page1 = new CRUDProcessingWizardPage(formLogicAnalysis);
		page2 = new NonCRUDProcessingWizardPage(formLogicAnalysis);
		page3 = new ProgramUnitProcessingWizardPage();
		addPage(page1);
		addPage(page2);
		addPage(page3);
	}

	@Override
	public boolean performFinish() {
		// List<R3CorePlSql> r3PlSqlList =
		// CRUDModelProvider.getInstance().getCRUDList();
		ProgramUnitAnalysis puAnalysis = formLogicAnalysis.getPuAnalysis();

		try {
			puAnalysis.processCode();
			JAXBXMLWriter.writetoXML(puAnalysis.getPUXmlFile(),
					puAnalysis.getR3ProgramUnitGen());
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		NonCRUDAnalysis nonCrudAnalysis = formLogicAnalysis
				.getNonCRUDAnalysis();

		try {
			nonCrudAnalysis.processCode();
			JAXBXMLWriter.writetoXML(nonCrudAnalysis.getNONCRUDXmlFile(),
					nonCrudAnalysis.getR3JavaSqlGen());
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		CRUDAnalysis crudAnalysis = formLogicAnalysis.getCRUDAnalysis();
		try {
			crudAnalysis.processCode();
			JAXBXMLWriter.writetoXML(crudAnalysis.getCRUDXmlFile(),
					crudAnalysis.getR3CorePlSqlGen());
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String parentRootDir = crudAnalysis.getRootDirectory();
		try {
			R3PlSqlTextWriter.getPlSqlTextWriter().writetoText(
					crudAnalysis.getRootDirectory(),
					crudAnalysis.getR3CorePlSqlGen(),
					nonCrudAnalysis.getR3JavaSqlGen(),
					puAnalysis.getR3ProgramUnitGen());
			// read files and writing to database...
			String defAferPlSqlPath = parentRootDir + File.separator
					+ crudAnalysis.getR3CorePlSqlGen().getName()
					+ "_def_after.sql";
			String bodyAferPlSqlPath = parentRootDir + File.separator
					+ crudAnalysis.getR3CorePlSqlGen().getName()
					+ "_body_after.sql";
			String defSqlStr = getFileContent(defAferPlSqlPath);
			String bodySqlStr = getFileContent(bodyAferPlSqlPath);

			if (CRUDProcessingWizardPage.preferences.getBoolean(
					CRUDProcessingWizardPage.WRITE_TO_DB_OPTION, false)) {
				DBCommand dbCommand = DBCommand.getDBCommand();
				try {
					dbCommand.runCreateCommand(defSqlStr);
				} catch (Exception e) {
					// Logger.getLogger("Form Logic Processing").log(Level.SEVERE,
					// e.getMessage());
					MessageDialog.openError(Display.getCurrent()
							.getActiveShell(),
							"Error during package creation!",
							"Unable to create package. Check log for details.");
					e.printStackTrace();
				}
				try {
					dbCommand.runCreateCommand(bodySqlStr);
				} catch (Exception e) {

					MessageDialog.openError(Display.getCurrent()
							.getActiveShell(),
							"Error during package creation!",
							"Unable to create package. Check log for details.");
					e.printStackTrace();
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		formLogicAnalysis.writePlSqlToTxt();
		boolean useSqlways = PreferencePlugin
				.getDefault()
				.getPreferenceStore()
				.getBoolean(
						PreferenceConstants.APPLICATION_SQLWAYS_USE_ISPIRER_SQLWAYS);
		if (useSqlways) {
			String result = formLogicAnalysis.convertToJavaUsingSqlways();
			if (result != null) {
				MessageDialog.openInformation(Display.getCurrent()
						.getActiveShell(), "Message!", result);
			}
		}
		return true;
	}

	@Override
	public boolean canFinish() {
		if (getContainer().getCurrentPage() == page3)
			return true;
		else
			return false;
	}

	public String getFileContent(String filePath) throws IOException {
		// filePath = filePath.replace("//", File.separator);
		FileReader reader = new FileReader(filePath);
		BufferedReader buffReader = new BufferedReader(reader);
		String str = null;
		String content = "";
		while ((str = buffReader.readLine()) != null) {
			content = content + str + "\n";
		}
		return content;
	}

}
