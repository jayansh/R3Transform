package com.release3.tf.plsql.process;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;

import com.converter.toolkit.ui.FormsObject;
import com.converter.toolkit.ui.JAXBXMLReader;
import com.converter.toolkit.ui.JAXBXMLWriter;
import com.oracle.xmlns.forms.Block;
import com.oracle.xmlns.forms.FormModule;
import com.oracle.xmlns.forms.Item;
import com.oracle.xmlns.forms.Module;
import com.release3.formsmap.R3FormsMap;
import com.release3.formsmap.R3FormsMapFactory;
import com.release3.javasql.JavaPlSqlType;
import com.release3.programunitgen.R3ProgramUnit;
import com.release3.r3coreplsqlgen.R3CorePlSql;
import com.release3.tf.core.form.AbstractForm;
import com.release3.tf.migration.model.MigrationForm;
import com.release3.tf.plsql.crud.model.CRUDModelProvider;
import com.release3.tf.plsql.noncrud.model.NonCRUDModelProvider;
import com.release3.tf.plsql.parser.ProcFuncParser;
import com.release3.tf.plsql.process.wizard.FormLogicProcessingWizard;
import com.release3.tf.plsql.pu.ProgramUnitModelProvider;
import com.release3.transform.pref.PreferenceConstants;
import com.release3.transform.pref.PreferencePlugin;
import com.release3.variablemap.ObjectFactory;
import com.release3.variablemap.Variables;
import com.release3.variablemap.Variables.Variable;

public class FormLogicProcessor {
	private AbstractForm abstractForm;
	private R3FormsMap r3FormsMap;
	private R3FormsMapFactory formsMapFactory;

	public FormLogicProcessor(AbstractForm abstractForm) {
		this.abstractForm = abstractForm;
		this.formsMapFactory = new R3FormsMapFactory();
		this.r3FormsMap = formsMapFactory.createR3FormsMap();

	}

	public void processFormPlSqlXmlFile() {
		if (CRUDModelProvider.getInstance().getCRUDList().size() > 0) {
			CRUDModelProvider.getInstance().getCRUDList().clear();
		}
		if (NonCRUDModelProvider.getInstance().getNonCRUDList().size() > 0) {
			NonCRUDModelProvider.getInstance().getNonCRUDList().clear();
		}
		if (ProgramUnitModelProvider.getInstance().getPUList().size() > 0) {
			ProgramUnitModelProvider.getInstance().getPUList().clear();
		}
		boolean usePreviousXmls = FormLogicPreferencesDialog.preferences
				.getBoolean(
						FormLogicPreferencesDialog.REMEMBER_LAST_SELECTED_OPTION,
						false);

		this.formsMapFactory = new R3FormsMapFactory();
		this.r3FormsMap = formsMapFactory.createR3FormsMap();

		ProcFuncParser parser = new ProcFuncParser(abstractForm, r3FormsMap);
		MigrationForm migrationForm = (MigrationForm) abstractForm;
		FormLogicAnalysis formLogicAnalysis = new FormLogicAnalysis(
				migrationForm, r3FormsMap);

		CRUDAnalysis crudAnalysis = formLogicAnalysis.getCRUDAnalysis();
		if (usePreviousXmls && migrationForm.getR3CRUDXmlFile().exists()) {
			crudAnalysis.readCorePlSqlXml(migrationForm.getR3CRUDXmlFile());
		} else {
			crudAnalysis
					.readCorePlSqlXml(migrationForm.getR3CorePlSqlXmlFile());
		}
		CRUDModelProvider.getInstance().setCRUDList(crudAnalysis.getCrudList());
		// crudAnalysisList.add(crudAnalysis);

		NonCRUDAnalysis nonCrudAnalysis = formLogicAnalysis
				.getNonCRUDAnalysis();
		if (usePreviousXmls && migrationForm.getR3NonCRUDXmlFile().exists()) {
			nonCrudAnalysis.readJavaSqlXml(migrationForm.getR3NonCRUDXmlFile());
		} else {
			nonCrudAnalysis.readJavaSqlXml(migrationForm.getR3JavaSqlXmlFile());
		}
		NonCRUDModelProvider.getInstance().setNonCRUDAnalysis(nonCrudAnalysis);

		ProgramUnitAnalysis puAnalysis = formLogicAnalysis.getPuAnalysis();
		if (usePreviousXmls
				&& migrationForm.getR3ProgramUnitAfterXmlFile().exists()) {
			puAnalysis.readProgramUnitPlSqlXml(migrationForm
					.getR3ProgramUnitAfterXmlFile());
		} else {
			puAnalysis.readProgramUnitPlSqlXml(migrationForm.getPUXmlFile());
		}
		ProgramUnitModelProvider.getInstance().setPUList(
				puAnalysis.getProgramUnitList());

		// nonCrudAnalysisList.add(nonCrudAnalysis);

		parse(crudAnalysis, nonCrudAnalysis, puAnalysis, parser);
		generateVariableMap(abstractForm);

		FormLogicProcessingWizard wizard = new FormLogicProcessingWizard(
				formLogicAnalysis);
		WizardDialog dialog = new WizardDialog(Display.getCurrent()
				.getActiveShell(), wizard);
		dialog.open();
	}

	public void parse(CRUDAnalysis crudAnalysis,
			NonCRUDAnalysis nonCrudAnalysis, ProgramUnitAnalysis puAnalysis,
			ProcFuncParser parser) {
		for (R3CorePlSql r3CorePlSql : crudAnalysis.getCrudList()) {
			String plsqlText = r3CorePlSql.getPlSqlText()
					.replace("&#10;", "\n");
			parser.process(plsqlText, r3CorePlSql.getPlSqlName(),
					r3CorePlSql.getBlockName(), r3CorePlSql.getItemName());
		}
		for (JavaPlSqlType javaPlSqlType : nonCrudAnalysis.getNonCrudList()) {
			String plsqlText = "PROCEDURE "
					+ javaPlSqlType.getJavaPlSql().replace('-', '_')
							.replace('.', '_') + " IS \n"
					+ javaPlSqlType.getJavaPlSqlText().replace("&#10;", "\n");
			parser.process(plsqlText, javaPlSqlType.getJavaPlSql(),
					javaPlSqlType.getBlockName(), javaPlSqlType.getItemName());
		}
		for (R3ProgramUnit r3ProgramUnit : puAnalysis.getProgramUnitList()) {
			String plsqlText = r3ProgramUnit.getR3ProgramUnit()
					.getProgramUnitText().replace("&#10;", "\n");
			parser.process(plsqlText, r3ProgramUnit.getR3ProgramUnit()
					.getName(), null, null);
		}
		parser.write();
	}

	public void generateVariableMap(AbstractForm abstractForm) {
		ObjectFactory objectFactory = new ObjectFactory();
		Variables variables = objectFactory.createVariables();
		Module module = abstractForm.getModule();
		FormModule formModule = module.getFormModule();
		String formName = formModule.getName().toUpperCase();
		Variable formVar = objectFactory.createVariablesVariable();
		formVar.setName("formname");
		formVar.setValue(formName.toUpperCase() // + "ModelEntityManagerFactory"
		);
		variables.getVariable().add(formVar);
		List<FormsObject> children = formModule.getChildren();
		for (FormsObject formsObject : children) {
			if (formsObject instanceof Block) {
				Block block = (Block) formsObject;
				String blockName = block.getName().toUpperCase();
				List<FormsObject> itemsList = block.getChildren();

				for (FormsObject blockItem : itemsList) {
					if (blockItem instanceof Item) {

						String itemName = blockItem.getName().toUpperCase();
						// e.g.
						// "#{BindingsDEPT_EMP.DEPT_EMP_DEPT.currentRow.DEPTNO_KEY}"
						String binding = "#{Bindings" + formName + "."
								+ formName + "_" + blockName + ".currentRow."
								+ formatItemName(itemName) + "}";
						Variable variable1 = objectFactory
								.createVariablesVariable();
						variable1.setBinding(binding);
						variable1.setName(":" + itemName);
						variable1.setDatatype(((Item) blockItem).getDataType());
						variables.getVariable().add(variable1);
						Variable variable2 = objectFactory
								.createVariablesVariable();
						variable2.setBinding(binding);
						variable2.setName(":" + blockName + "." + itemName);
						variable2.setDatatype(((Item) blockItem).getDataType());
						variables.getVariable().add(variable2);

					}

				}
			}

		}
		// remove duplicates.
		ArrayList<Variables.Variable> varialbesToBeRemoved = new ArrayList<Variables.Variable>();

		for (int i = 0; i < variables.getVariable().size(); i++) {
			Variable variable = variables.getVariable().get(i);
			for (int j = 0; j < variables.getVariable().size(); j++) {
				Variable variable2 = variables.getVariable().get(j);
				if (i != j && variable.getName().equals(variable2.getName())) {
					varialbesToBeRemoved.add(variable);
					varialbesToBeRemoved.add(variable2);
				}
			}
		}
		variables.getVariable().removeAll(varialbesToBeRemoved);
		try {
			File sourceDir = new File(abstractForm.getXmlFolderPath()
					+ File.separator + "SourceForms");
			if (!sourceDir.exists()) {
				sourceDir.mkdirs();
			}
			File itemVarXmlFile = new File(abstractForm.getXmlFolderPath()
					.getAbsoluteFile()
					+ File.separator
					+ "SourceForms"
					+ File.separator + "item_variable.xml");

			if (!itemVarXmlFile.exists()
					|| PreferencePlugin
							.getDefault()
							.getPreferenceStore()
							.getBoolean(
									PreferenceConstants.APPLICATION_SQLWAYS_OVERWRITE_ITEM_VARIABLE_XML)) {
				JAXBXMLWriter.writetoXML(abstractForm.getXmlFolderPath()
						.getAbsoluteFile()
						+ File.separator
						+ "SourceForms"
						+ File.separator + "item_variable.xml", variables);
			}
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private String formatItemName(String itemName) {
		if (itemName.charAt(1) == '_') {
			// int beginIndex = 0;
			int endIndex = itemName.length();
			itemName = itemName.charAt(0) + itemName.substring(2, endIndex);
		}
		return itemName;
	}
}
