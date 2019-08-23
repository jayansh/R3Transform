package com.release3.transform.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Display;

import r3transform.Activator;

import com.converter.toolkit.ui.FormsObject;
import com.converter.toolkit.ui.JAXBXMLWriter;
import com.oracle.xmlns.forms.Block;
import com.oracle.xmlns.forms.Canvas;
import com.oracle.xmlns.forms.FormModule;
import com.oracle.xmlns.forms.Item;
import com.oracle.xmlns.forms.LOVColumnMapping;
import com.oracle.xmlns.forms.Module;
import com.oracle.xmlns.forms.Window;
import com.release3.customization.R3;
import com.release3.customization.blockcust.BlockCustType;
import com.release3.customization.parameter.ParameterType;
import com.release3.customization.recordgroup.RecordGroupType;
import com.release3.dbconnect.DBCommand;
import com.release3.plsql.analysis.PlSqlWriter;
import com.release3.tf.core.form.CleanupForm;
import com.release3.tf.core.form.Settings;
import com.release3.tf.form.anlysis.FormAnalysisReportGen;
import com.release3.transform.analysis.LOVModelAnalysis;
import com.release3.transform.constants.PremigrationConstants;
import com.release3.transform.lov.LOVRecordGroupModelList;
import com.release3.transform.model.LOVModelProvider;

public class PremigrationWizard extends Wizard {

	WizardPage page1;
	WizardPage page2;
	WizardPage page3;
	WizardPage page4;
	WizardPage page5;
	WizardPage page6;
//	WizardPage page7;
	private DBCommand dbCommand;

	public PremigrationWizard() {
		setWindowTitle("Premigration Wizard");
		setNeedsProgressMonitor(true);
		dbCommand = DBCommand.getDBCommand();
	}

	@Override
	public void addPages() {
		page1 = new FormChooserWizardPage();
		addPage(page1);
		page2 = new AddToolBarWizardPage();
		addPage(page2);
		page3 = new SpecialCharHandlingWizardPage();
		addPage(page3);
		page4 = new PromptHandlingWizardPage();
		addPage(page4);
		page5 = new BlockTypeHandlingWizardPage();
		addPage(page5);
		page6 = new ItemHandlingWizardPage();
		addPage(page6);
//		page7 = new LOVHandlingWizardPage();
//		addPage(page7);

	}

	@Override
	public boolean performFinish() {

		try {

			if (r3transform.Activator.getDefault().getPreferenceStore()
					.getBoolean(PremigrationConstants.ShowFormConnectionWindow)) {
				FormConnectionDialog dialog = new FormConnectionDialog(
						getShell());
				dialog.open();
			}
			R3 r3PreCustomization = addToolbar();
			// if (r3transform.Activator.getDefault().getPreferenceStore()
			// .getBoolean(PremigrationConstants.CreateViewForTables)) {
			//
			// LOVModelProvider lovModelProvider = LOVModelProvider
			// .getInstance();
			// List<LOVRecordGroupModelList.LOVRecordGroupModel> lovRgList =
			// lovModelProvider
			// .getLovRgList();
			// for (LOVRecordGroupModelList.LOVRecordGroupModel
			// lovRecordGroupModel : lovRgList) {
			// boolean containsMultipleTables =
			// ((LOVRecordGroupModelList.LOVRecordGroupModel)
			// lovRecordGroupModel)
			// .isContainsMultipleTable();
			// if (containsMultipleTables) {
			// List<LOVColumnMapping> lovColumns = lovRecordGroupModel
			// .getLOV().getLOVColumnMapping();
			// String columns = "";
			// for (LOVColumnMapping lovColumn : lovColumns) {
			// String columnName = lovColumn.getName();
			// if (columnName != null) {
			// columnName = dbCommand.getQualifiedColumnName(
			// lovRecordGroupModel.getTableName(),
			// columnName);
			// if (columns.length() == 0) {
			// columns = columnName;
			// } else {
			//
			// columns = columns + ", " + columnName;
			// }
			// }
			// }
			// String query = "SELECT " + columns + " FROM "
			// + lovRecordGroupModel.getTableName();
			// String viewName = dbCommand.createView(query,
			// lovRecordGroupModel.getTableName());
			//
			// if (viewName != null) {
			// lovRecordGroupModel.setTableName(viewName);
			// lovRecordGroupModel.getRecordGroup()
			// .setRecordGroupQuery(
			// "SELECT * FROM " + " " + viewName);
			//
			// }
			// }
			// }
			//
			// }

			CleanupForm cleanupForm = ((CleanupForm) (((FormChooserWizardPage) page1)
					.getTable().getItem(
							((FormChooserWizardPage) page1).getTable()
									.getSelectionIndex()).getData()));
			cleanupForm.save(true);
			LOVModelAnalysis lovModelAnalysis = ((FormChooserWizardPage) page1)
					.getLovModelAnalysis();
			JAXBXMLWriter.writetoXML(Settings.getSettings().getFmbRootDir()
					+ File.separator
					+ ((FormChooserWizardPage) page1).getCleanupForm()
							.getFormName().replace('.', '_') + "_LOV.xml",
					lovModelAnalysis.getLOVRecordGroupModelList());

			/** writing recordGroups to Customization file */
			List<RecordGroupType> rgList = lovModelAnalysis
					.getCustomRecordGroupList();
			r3PreCustomization.getRecordGroup().addAll(rgList);

			/** Writing BlockCust to Customization file */
			// List<BlockCustType> blockCustomizationList =
			// ((FormChooserWizardPage) page1)
			// .getBlockAnalysis().getBlockCustomizationList();
			// r3PreCustomization.getBlockCust().addAll(blockCustomizationList);
			// ((FormChooserWizardPage) page1).generatCustomization();

			/**
			 * Creating nondbblock
			 */
			dbCommand.createNonDBBlockTable();
			dbCommand.createRecIdSequence();

			/* generating programunits/trigger xml file */
			Module module = cleanupForm.getModule();
			PlSqlWriter plSqlWriter = new PlSqlWriter(module);

			try {
				plSqlWriter.plsqlXmlFileWriter(cleanupForm
						.getPlsqlAnalysisFile().getPath(), r3PreCustomization);
			} catch (FileNotFoundException fnfEx) {
				MessageDialog.openError(Display.getCurrent().getActiveShell(),
						"FileNotFoundException", fnfEx.getLocalizedMessage());
			}

			/* generating Form map. */
			FormAnalysisReportGen reportGen = new FormAnalysisReportGen(module);
			reportGen.formsAnalysisReportWriter(cleanupForm
					.getFormsMapFilePath());
			reportGen.writeDBPlSql(cleanupForm.getDBPlSqlFilePath());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return true;
	}

	@Override
	public boolean canFinish() {
		Boolean quickCleanupWizard = Activator.getDefault()
		.getPreferenceStore()
		.getDefaultBoolean(PremigrationConstants.QUICK_CLEANUP_WIZARD);
		if(quickCleanupWizard && getContainer().getCurrentPage() == page2){
			return true;
		}
		if (getContainer().getCurrentPage() == page6)
			return true;
		else
			return false;
	}

	public R3 addToolbar() {
		R3 r3PreCustomization = new R3();
		AddToolBarWizardPage toolBarWizardPage = (AddToolBarWizardPage) page2;
		Window window = toolBarWizardPage.getSelectedWindow();
		// Canvas canvas = new Canvas();
		// canvas.setName("HORTB");
		// canvas.setHeight(new BigInteger("10"));
		// canvas.setViewportHeight(new BigInteger("10"));
		// canvas.setDirtyInfo(true);
		// canvas.setWidth(new BigInteger("600"));
		// canvas.setViewportWidth(new BigInteger("600"));
		// canvas.setCanvasType("Horizontal Toolbar");
		// canvas.setWindowName(window.getName());
		Block selectedBlock = toolBarWizardPage.getSelectedBlock();
		if (selectedBlock != null) {
			Block r3ControlBlock = new Block();
			r3ControlBlock.setName("R3CONTROLS");
			r3ControlBlock.setDatabaseBlock(false);
			String canvasName = null;
			List<FormsObject> blockChildren = selectedBlock.getChildren();
			for (FormsObject formsObject : blockChildren) {
				if (formsObject instanceof Item) {
					canvasName = ((Item) formsObject).getCanvasName();
					break;
				}
			}
			String tableName = selectedBlock.getQueryDataSourceName();
			List<String> pkColumns = dbCommand.getColumnList(tableName);
			List<ParameterType> paramList = new ArrayList<ParameterType>();
			for (String pkColumn : pkColumns) {
				// <Parameter Block="DEPT" Item="DEPTNO" Type="in"/>
				ParameterType param = new ParameterType();
				param.setBlock(selectedBlock.getName());
				param.setItem(pkColumn);
				param.setType("in");
				paramList.add(param);
			}

			List<R3.Trigger> triggerList = new ArrayList<R3.Trigger>();
			// Previous Button
			if (toolBarWizardPage.getBtnCheckPrevSelection()) {
				Item prevItem = new Item();
				prevItem.setName("Prev");
				prevItem.setItemType("Push Button");
				// prevItem.setCanvasName(canvas.getName());
				prevItem.setCanvasName(canvasName);
				prevItem.setXPosition(new BigInteger("28"));
				prevItem.setYPosition(new BigInteger("8"));
				prevItem.setWidth(new BigInteger("20"));
				prevItem.setHeight(new BigInteger("14"));
				r3ControlBlock.getChildren().add(prevItem);

				R3.Trigger trigger = new R3.Trigger();
				trigger.setBlock("R3CONTROLS");
				trigger.setItem("Prev");
				trigger.setJsfattr("actionListener");
				trigger.setMethodType("prevRecord");
				trigger.getParameter().addAll(paramList);
				triggerList.add(trigger);

			}
			// Save Button
			if (toolBarWizardPage.getBtnCheckSaveSelection()) {
				Item saveItem = new Item();
				saveItem.setName("Save");
				saveItem.setItemType("Push Button");
				// saveItem.setCanvasName(canvas.getName());
				saveItem.setCanvasName(canvasName);
				saveItem.setXPosition(new BigInteger("49"));
				saveItem.setYPosition(new BigInteger("8"));
				saveItem.setWidth(new BigInteger("20"));
				saveItem.setHeight(new BigInteger("14"));

				r3ControlBlock.getChildren().add(saveItem);

				R3.Trigger trigger = new R3.Trigger();
				trigger.setBlock("R3CONTROLS");
				trigger.setItem("Save");
				trigger.setJsfattr("actionListener");
				trigger.setMethodType("saveRecord");
				trigger.getParameter().addAll(paramList);
				triggerList.add(trigger);
			}
			// New Button
			if (toolBarWizardPage.getBtnCheckNewSelection()) {
				Item newItem = new Item();
				newItem.setName("New");
				newItem.setItemType("Push Button");
				// newItem.setCanvasName(canvas.getName());
				newItem.setCanvasName(canvasName);
				newItem.setXPosition(new BigInteger("70"));
				newItem.setYPosition(new BigInteger("8"));
				newItem.setWidth(new BigInteger("20"));
				newItem.setHeight(new BigInteger("14"));
				r3ControlBlock.getChildren().add(newItem);

				R3.Trigger trigger = new R3.Trigger();
				trigger.setBlock("R3CONTROLS");
				trigger.setItem("New");
				trigger.setJsfattr("actionListener");
				trigger.setMethodType("newRecord");
				trigger.getParameter().addAll(paramList);
				triggerList.add(trigger);

			}
			// Delete Button
			if (toolBarWizardPage.getBtnCheckDeleteSelection()) {
				Item deleteItem = new Item();
				deleteItem.setName("Delete");
				deleteItem.setItemType("Push Button");
				// deleteItem.setCanvasName(canvas.getName());
				deleteItem.setCanvasName(canvasName);
				deleteItem.setXPosition(new BigInteger("91"));
				deleteItem.setYPosition(new BigInteger("8"));
				deleteItem.setWidth(new BigInteger("20"));
				deleteItem.setHeight(new BigInteger("14"));
				r3ControlBlock.getChildren().add(deleteItem);

				R3.Trigger trigger = new R3.Trigger();
				trigger.setBlock("R3CONTROLS");
				trigger.setItem("Delete");
				trigger.setJsfattr("actionListener");
				trigger.setMethodType("Remove");
				trigger.getParameter().addAll(paramList);
				triggerList.add(trigger);

			}

			// Search Button
			if (toolBarWizardPage.getBtnCheckSearchSelection()) {
				Item searchItem = new Item();
				searchItem.setName("Search");
				searchItem.setItemType("Push Button");
				// searchItem.setCanvasName(canvas.getName());
				searchItem.setCanvasName(canvasName);
				searchItem.setXPosition(new BigInteger("112"));
				searchItem.setYPosition(new BigInteger("8"));
				searchItem.setWidth(new BigInteger("20"));
				searchItem.setHeight(new BigInteger("14"));
				r3ControlBlock.getChildren().add(searchItem);

				R3.Trigger trigger = new R3.Trigger();
				trigger.setBlock("R3CONTROLS");
				trigger.setItem("Search");
				trigger.setJsfattr("actionListener");
				trigger.setMethodType("searchRecord");
				trigger.getParameter().addAll(paramList);
				triggerList.add(trigger);
			}
			// Query Button
			if (toolBarWizardPage.getBtnCheckQuerySelection()) {
				Item queryItem = new Item();
				queryItem.setName("Query");
				queryItem.setItemType("Push Button");
				// queryItem.setCanvasName(canvas.getName());
				queryItem.setCanvasName(canvasName);
				queryItem.setXPosition(new BigInteger("133"));
				queryItem.setYPosition(new BigInteger("8"));
				queryItem.setWidth(new BigInteger("20"));
				queryItem.setHeight(new BigInteger("14"));
				r3ControlBlock.getChildren().add(queryItem);

				R3.Trigger trigger = new R3.Trigger();
				trigger.setBlock("R3CONTROLS");
				trigger.setItem("Query");
				trigger.setJsfattr("actionListener");
				trigger.setMethodType("queryRecord");
				trigger.getParameter().addAll(paramList);
				triggerList.add(trigger);

			}
			// Next Button
			if (toolBarWizardPage.getBtnCheckNextSelection()) {
				Item nextItem = new Item();
				nextItem.setName("Next");
				nextItem.setItemType("Push Button");
				// nextItem.setCanvasName(canvas.getName());
				nextItem.setCanvasName(canvasName);
				nextItem.setXPosition(new BigInteger("154"));
				nextItem.setYPosition(new BigInteger("8"));
				nextItem.setWidth(new BigInteger("20"));
				nextItem.setHeight(new BigInteger("14"));
				r3ControlBlock.getChildren().add(nextItem);

				R3.Trigger trigger = new R3.Trigger();
				trigger.setBlock("R3CONTROLS");
				trigger.setItem("Next");
				trigger.setJsfattr("actionListener");
				trigger.setMethodType("nextRecord");
				trigger.getParameter().addAll(paramList);
				triggerList.add(trigger);
			}

			// clean block
			if (toolBarWizardPage.getBtnCheckClearSelection()) {
				Item clearItem = new Item();
				clearItem.setName("Clear");
				clearItem.setItemType("Push Button");
				clearItem.setCanvasName(canvasName);
				clearItem.setXPosition(new BigInteger("175"));
				clearItem.setYPosition(new BigInteger("8"));
				clearItem.setWidth(new BigInteger("20"));
				clearItem.setHeight(new BigInteger("14"));

				r3ControlBlock.getChildren().add(clearItem);

				R3.Trigger trigger = new R3.Trigger();
				trigger.setBlock(selectedBlock.getName());
				trigger.setItem("Clear");
				trigger.setJsfattr("actionListener");
				trigger.setMethodType("clearBlock");
				trigger.getParameter().addAll(paramList);
				triggerList.add(trigger);
			}

			if (toolBarWizardPage.getBtnCheckExitSelection()) {
				Item exitItem = new Item();
				exitItem.setName("Exit");
				exitItem.setItemType("Push Button");
				exitItem.setCanvasName(canvasName);
				exitItem.setXPosition(new BigInteger("196"));
				exitItem.setYPosition(new BigInteger("8"));
				exitItem.setWidth(new BigInteger("20"));
				exitItem.setHeight(new BigInteger("14"));

				r3ControlBlock.getChildren().add(exitItem);

				R3.Trigger trigger = new R3.Trigger();
				trigger.setBlock(selectedBlock.getName());
				trigger.setItem("Exit");
				trigger.setJsfattr("actionListener");
				trigger.setMethodType("canvasSwitcher");
				ParameterType param = new ParameterType();
				param.setBlock(selectedBlock.getName());
				// Type="in" ViewPortForm="GLOBAL" ViewPort="CANVAS3"
				// Canvas="GLOBAL_CANVAS3.jspx" FormName="GLOBAL"
				param.setType("in");
				param.setViewPortForm("GLOBAL");
				param.setViewPort("CANVAS3");
				param.setCanvas("GLOBAL_CANVAS3.jspx");
				param.setFormname("GLOBAL");
				trigger.getParameter().add(param);
				triggerList.add(trigger);
			}
			FormModule formModule = ((FormChooserWizardPage) page1)
					.getCleanupForm().getModule().getFormModule();
			if (r3ControlBlock.getChildren().size() > 0) {
				formModule.getChildren().add(r3ControlBlock);
				// formModule.getChildren().add(canvas);
				r3PreCustomization.getTrigger().addAll(triggerList);
				r3PreCustomization.setName(formModule.getName());
				// try {
				// JAXBXMLWriter.writetoXML(Settings.getSettings().getFmbRootDir()
				// + File.separator
				// + ((FormChooserWizardPage) page1).getCleanupForm()
				// .getFormName().replace('.', '_')
				// + "_R3PreCustomization.xml", r3PreCustomization);
				// } catch (JAXBException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// } catch (IOException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
			}
		}
		return r3PreCustomization;
	}
	
	
}
