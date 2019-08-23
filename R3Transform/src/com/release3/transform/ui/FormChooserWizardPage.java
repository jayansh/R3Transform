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
package com.release3.transform.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolTip;
import org.xml.sax.SAXException;

import com.converter.toolkit.ui.FormsObject;
import com.oracle.xmlns.forms.Block;
import com.oracle.xmlns.forms.Canvas;
import com.oracle.xmlns.forms.FormModule;
import com.oracle.xmlns.forms.LOV;
import com.oracle.xmlns.forms.Module;
import com.oracle.xmlns.forms.RecordGroup;
import com.oracle.xmlns.forms.Trigger;
import com.oracle.xmlns.forms.Window;
import com.release3.tf.core.form.Settings;
import com.release3.tf.premigrate.toolbar.analysis.ToolbarModelAnalysis;
import com.release3.tf.premigrate.toolbar.model.DefaultToolbarModelProvider;
import com.release3.tf.premigrate.trigger.TriggerFileWriter;
import com.release3.transform.LovXmlReaderWriter;
import com.release3.transform.analysis.BlockAnalysis;
import com.release3.transform.analysis.GraphicsAnalysis;
import com.release3.transform.analysis.ItemAnalysis;
import com.release3.transform.analysis.LOVAnalysis;
import com.release3.transform.analysis.LOVModelAnalysis;
import com.release3.transform.analysis.RecordGroupAnalysis;
import com.release3.transform.constants.PremigrationConstants;
import com.release3.transform.lov.LOVRecordGroupModelList;
import com.release3.transform.model.BlockModelProvider;
import com.release3.transform.model.CanvasModel;
import com.release3.tf.core.form.CleanupForm;
import com.release3.transform.model.CleanupFormContentProvider;
import com.release3.transform.model.CleanupFormLabelProvider;
import com.release3.transform.model.CleanupFormModelProvider;
import com.release3.transform.model.LOVModelProvider;
import com.release3.transform.model.SpecialCharsItemModel;
import com.release3.transform.model.SpecialCharsModelProvider;
import com.release3.transform.pref.PropertiesReadWrite;
import com.release3.transform.rule.RuleHandling;
import com.release3.transform.rules.model.Rules;
import r3transform.Activator;

public class FormChooserWizardPage extends WizardPage {

	private Table table;

	private ToolbarModelAnalysis toolbarModelAnalysis;
	private GraphicsAnalysis graphicsAnalysis;
	private ItemAnalysis itemAnalysis;
	private BlockAnalysis blockAnalysis;
	private RecordGroupAnalysis rgAnalysis;
	private LOVAnalysis lovAnalysis;
	private LOVModelAnalysis lovModelAnalysis;

	private CleanupFormModelProvider formModelProvider;

	private CleanupForm cleanupForm;
	private TableViewer tableViewer;
	private Rules rules;
	private RuleHandling ruleHandling;

	Button btnChkOrignal;
	Button btnQuickCleanup;
	Button btnShowFormConnection;

	/**
	 * Create the wizard.
	 */
	public FormChooserWizardPage() {
		super("Form Chooser Wizard Page");
		setTitle("Select a form");
		setDescription("Select a form for premigration.");
		toolbarModelAnalysis = new ToolbarModelAnalysis();
		graphicsAnalysis = new GraphicsAnalysis();
		itemAnalysis = new ItemAnalysis();
		blockAnalysis = new BlockAnalysis();
		rgAnalysis = new RecordGroupAnalysis();
		lovAnalysis = new LOVAnalysis();
		lovModelAnalysis = new LOVModelAnalysis();
		ruleHandling = new RuleHandling();
		// getting rules
		rules = ruleHandling.init();
	}

	/**
	 * Create contents of the wizard.
	 * 
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		setControl(container);
		Group mainGroup = new Group(container, SWT.NONE);
		mainGroup.setBounds(0, 0, 803, 463);

		tableViewer = new TableViewer(mainGroup, SWT.BORDER
				| SWT.FULL_SELECTION);
		createColumns(tableViewer);

		btnChkOrignal = new Button(mainGroup, SWT.CHECK);
		btnChkOrignal.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (btnChkOrignal.getSelection()) {
					PremigrationConstants.UsePreviousCleanupFormPref = true;
				} else {
					PremigrationConstants.UsePreviousCleanupFormPref = false;
				}
			}
		});
		btnChkOrignal
				.setSelection(PremigrationConstants.UsePreviousCleanupFormPref);
		btnChkOrignal.setLocation(10, 336);
		btnChkOrignal.setSize(267, 24);
		btnChkOrignal.setText("Use Preferences from previously cleanup form.");
		btnChkOrignal.setEnabled(false);

		btnQuickCleanup = new Button(mainGroup, SWT.CHECK);
		btnQuickCleanup.setText("Quick Cleanup");
		btnQuickCleanup.setSize(99, 24);
		btnQuickCleanup.setLocation(10, 366);
		btnQuickCleanup.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Activator
						.getDefault()
						.getPreferenceStore()
						.setDefault(PremigrationConstants.QUICK_CLEANUP_WIZARD,
								btnQuickCleanup.getSelection());
			}
		});
		Boolean quickCleanupWizard = Activator.getDefault()
				.getPreferenceStore()
				.getDefaultBoolean(PremigrationConstants.QUICK_CLEANUP_WIZARD);
		btnQuickCleanup.setSelection(quickCleanupWizard);

		btnShowFormConnection = new Button(mainGroup, SWT.CHECK);
		r3transform.Activator
				.getDefault()
				.getPreferenceStore()
				.setDefault(PremigrationConstants.ShowFormConnectionWindow,
						true);
		btnShowFormConnection.setSelection(r3transform.Activator.getDefault()
				.getPreferenceStore()
				.getBoolean(PremigrationConstants.ShowFormConnectionWindow));

		btnShowFormConnection.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				r3transform.Activator
						.getDefault()
						.getPreferenceStore()
						.setValue(
								PremigrationConstants.ShowFormConnectionWindow,
								btnShowFormConnection.getSelection());

			}
		});
		btnShowFormConnection.setBounds(10, 396, 185, 24);
		btnShowFormConnection.setText("Show form connection window");
		// Label lblToolkitRemembers = new Label(mainGroup, SWT.NONE);
		// lblToolkitRemembers.setBounds(277, 336, 480, 52);
		// lblToolkitRemembers
		// .setText("(The toolkit remembers the history if the same form has been migrated earlier also.\r\nIf you check the option toolkit will use this saved preferences for cleanup.  This\r\nmakes cleanup process quite easy.)");

		final ToolTip useOrignalToolTip = new ToolTip(container.getShell(),
				SWT.NONE);
		useOrignalToolTip.setAutoHide(true);
		useOrignalToolTip
				.setMessage("The toolkit remembers the history if the same form has been migrated earlier also. "
						+ "If you check the option toolkit will use this saved preferences for cleanup. "
						+ "This makes cleanup process quite easy.");

		// Label lblQuickCleanupLabel = new Label(mainGroup, SWT.NONE);
		// lblQuickCleanupLabel.setBounds(128, 392, 459, 38);
		// lblQuickCleanupLabel
		// .setText("This option will allow Cleanup form quickly with standard settings.");
		final ToolTip quickCleanupToolTip = new ToolTip(container.getShell(),
				SWT.NONE);
		quickCleanupToolTip
				.setMessage("This option will allow Cleanup form quickly with standard settings.");
		quickCleanupToolTip.setAutoHide(true);

		btnChkOrignal.addMouseTrackListener(new MouseTrackListener() {
			@Override
			public void mouseHover(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExit(MouseEvent e) {
				useOrignalToolTip.setVisible(false);

			}

			@Override
			public void mouseEnter(MouseEvent e) {
				useOrignalToolTip.setVisible(true);
			}
		});

		btnQuickCleanup.addMouseTrackListener(new MouseTrackListener() {
			@Override
			public void mouseHover(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExit(MouseEvent e) {
				quickCleanupToolTip.setVisible(false);

			}

			@Override
			public void mouseEnter(MouseEvent e) {
				quickCleanupToolTip.setVisible(true);

			}
		});

		tableViewer.setContentProvider(new CleanupFormContentProvider());
		CleanupFormLabelProvider labelProvider = new CleanupFormLabelProvider();
		tableViewer.setLabelProvider(labelProvider);
		formModelProvider = CleanupFormModelProvider.getInstance();
		tableViewer.setInput(formModelProvider.getCleanupForms());

	}

	private void createColumns(TableViewer viewer) {

		int[] bounds = { 180, 180 };

		for (int i = 0; i < PremigrationConstants.FORMS_SELECTION_PAGE_TITLES.length; i++) {
			TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);
			column.getColumn().setText(
					PremigrationConstants.FORMS_SELECTION_PAGE_TITLES[i]);
			column.getColumn().setWidth(bounds[i]);
			column.getColumn().setResizable(true);
			column.getColumn().setMoveable(true);
		}
		table = viewer.getTable();
		table.removeAll();
		if (table.getSelectionIndex() == -1) {
			setErrorMessage("Please select a form");
		}
		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (table.getSelectionIndex() > -1) {

					TableItem item = table.getSelection()[0];
					CleanupForm form = (CleanupForm) item.getData();
					File propertiesFile = new File(Platform
							.getInstallLocation().getURL().getFile()
							+ File.separator
							+ form.getFormName().replace('.', '_')
							+ ".properties");
					if (propertiesFile.exists()) {
						btnChkOrignal.setEnabled(true);
						PremigrationConstants.UsePreviousCleanupFormPref = true;
					} else {
						PremigrationConstants.UsePreviousCleanupFormPref = false;
						btnChkOrignal.setEnabled(false);
					}
					btnChkOrignal
							.setSelection(PremigrationConstants.UsePreviousCleanupFormPref);
					setMessage("Ok");
					setErrorMessage(null);
					setPageComplete(true);
				} else {
					setErrorMessage("Please select a form");
				}
			}
		});
		table.setBounds(0, 0, 793, 330);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

	}

	@Override
	public boolean canFlipToNextPage() {

		if (getErrorMessage() != null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean isPageComplete() {
		return false;
	}

	
	@Override
	public IWizardPage getNextPage() {
		/**
		 * convert form to xml.
		 * 
		 */

		if (processes()) {
			fillToolbarModelProvider();
			fillSpecialCharsModelProvider();
			fillBlockTableProvider();
			fillLOVTableProvider();
			return super.getNextPage();
		}
		return null;
	}

	public ToolbarModelAnalysis getToolbarModelAnalysis() {
		return toolbarModelAnalysis;
	}

	public GraphicsAnalysis getGraphicsAnalysis() {
		return graphicsAnalysis;
	}

	public ItemAnalysis getItemAnalysis() {
		return itemAnalysis;
	}

	public BlockAnalysis getBlockAnalysis() {
		return blockAnalysis;
	}

	public LOVModelAnalysis getLovModelAnalysis() {
		return lovModelAnalysis;
	}

	private ArrayList<String> getMissingDependencies(String frmFilePath,
			SubProgressMonitor monitor) {

		ArrayList<String> missingLibs = new ArrayList<String>();
		String executeSentence = "java -jar "
				+ new File(Platform.getInstallLocation().getURL().getFile())
				+ File.separator + "DependencyChecker.jar " + frmFilePath;

		System.out.println(executeSentence);
		try {
			Process p = Runtime.getRuntime().exec(
					"cmd.exe /C " + executeSentence);

			BufferedReader in = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line = in.readLine();

			while (line != null) {
				System.out.println("missing:::" + line);
				if (line.endsWith(".fmb") || line.endsWith(".olb")) {
					missingLibs.add(line);

				}
				line = in.readLine();
			}
			in.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		monitor.worked(2);
		return missingLibs;
	}

	private boolean processes() {
		if (toolbarModelAnalysis != null) {
			toolbarModelAnalysis.setWindowList(null);
			toolbarModelAnalysis.setBlockList(null);
		}
		if (graphicsAnalysis != null) {
			graphicsAnalysis.setCanvasModelList(null);
		}
		if (itemAnalysis != null) {
			itemAnalysis.setItemModelList(null);
			itemAnalysis.getSpecialCharAnalysis().setItemsList(null);
		}
		if (blockAnalysis != null) {
			blockAnalysis.setBlockList(null);
		}
		if (rgAnalysis != null) {
			rgAnalysis.setRecordGroupList(null);
		}
		if (lovAnalysis != null) {
			lovAnalysis.setLovList(null);
		}
		if (lovModelAnalysis != null) {
			lovModelAnalysis.setLOVRecordGroupModelList(null);
		}

		TableItem item = getTable().getSelection()[0];
		final CleanupForm form = (CleanupForm) item.getData();
		setCleanupForm(form);

		File propertiesFile = new File(Platform.getInstallLocation().getURL()
				.getFile()
				+ File.separator
				+ form.getFormName().replace('.', '_')
				+ ".properties");
		PropertiesReadWrite.getPropertiesReadWrite().setPropertyFile(
				propertiesFile);
		PropertiesReadWrite.getPropertiesReadWrite().read();

		IRunnableWithProgress op = new IRunnableWithProgress() {

			public void run(final IProgressMonitor monitor)
					throws InvocationTargetException, InterruptedException {

				try {

					monitor.setTaskName("Extraction");
					monitor.beginTask("Retrieving form...", 8);

					monitor.worked(1);
					monitor.setTaskName("Checking for dependencies...");
					final ArrayList<String> missingLibs = getMissingDependencies(
							getCleanupForm().getForm().getPath(),
							new SubProgressMonitor(monitor, 2));
					if (missingLibs.size() > 0) {
						System.out.println(missingLibs);
						monitor.setCanceled(true);
						getShell().getDisplay().asyncExec(new Runnable() {
							public void run() {
								setErrorMessage("Missing dependencies: "
										+ missingLibs + " for form "
										+ getCleanupForm().getFormName());
								getCleanupForm().setStatus("Error");
								TableItem item = getTable().getItem(
										getTable().getSelectionIndex());
								Color redColor = getShell().getDisplay()
										.getSystemColor(SWT.COLOR_RED);
								item.setForeground(redColor);
								Object obj = item.getData();
								String[] properties = { PremigrationConstants.FORMS_SELECTION_PAGE_TITLES[1] };
								tableViewer.update(obj, properties);
							}
						});

						throw new OperationCanceledException(
								"Missing dependencies: " + missingLibs);

					} else {
						getShell().getDisplay().asyncExec(new Runnable() {
							public void run() {

								getCleanupForm().setStatus("True");
								TableItem item = getTable().getItem(
										getTable().getSelectionIndex());
								Color blueColor = getShell().getDisplay()
										.getSystemColor(SWT.COLOR_BLUE);
								item.setForeground(blueColor);
								Object obj = item.getData();
								String[] properties = { PremigrationConstants.FORMS_SELECTION_PAGE_TITLES[1] };
								tableViewer.update(obj, properties);
							}
						});
					}
					monitor.setTaskName("Generating xml files...");
					getCleanupForm().form2xml();
					monitor.worked(3);
					monitor.setTaskName("Analysing form...");
					Module module = cleanupForm.getModule();
					FormModule formModule = module.getFormModule();

					formModule.setMenuModule(formModule.getMenuModule()
							.replace("&", " "));
					List<FormsObject> children = formModule.getChildren();
					String path = form.getForm().getPath().replace(".", "_")
							+ "_trigger.txt";
					TriggerFileWriter triggerFileWriter = new TriggerFileWriter(
							path, false);
					for (FormsObject formsObject : children) {
						if (formsObject.getName() != null
								&& formsObject.getName().length() > 30) {
							String name = formsObject.getName();
							name.replace(" ", "");
							name = name.substring(0, 29);
							formsObject.setName(name);

						}

						if (formsObject instanceof Canvas) {
							graphicsAnalysis.analysis((Canvas) formsObject);
						}
						if (formsObject instanceof Block) {
							Block block = (Block) formsObject;
							itemAnalysis.analysis(block);
							blockAnalysis.analysis(block);
							if (block.isDatabaseBlock() != null
									&& block.isDatabaseBlock()) {
								toolbarModelAnalysis.getBlockList().add(block);
							}
						}

						if (formsObject instanceof RecordGroup) {
							RecordGroup rg = (RecordGroup) formsObject;
							rgAnalysis.preAnalysis(rg);

						}
						if (formsObject instanceof LOV) {
							LOV lov = (LOV) formsObject;
							lovAnalysis.preAnalysis(lov);
						}
						if (formsObject instanceof Trigger) {
							Trigger trigger = (Trigger) formsObject;
							triggerFileWriter.triggerTextFileWriter(trigger);

						}
						if (formsObject instanceof Window) {
							Window window = (Window) formsObject;
							toolbarModelAnalysis.getWindowList().add(window);
							for (CanvasModel canvasModel : graphicsAnalysis
									.getCanvasModelList()) {
								String canvasName = canvasModel.getCanvas()
										.getName();

								if (canvasName == window.getName()
										|| window.getName().equalsIgnoreCase(
												canvasName)) {
									window.setName(window.getName() + "_Win");
									canvasModel.getCanvas().setWindowName(
											window.getName());
								}
							}

							// System.out.println(window.getName());

						}
					}
					triggerFileWriter.close();
					monitor.worked(3);
					lovModelAnalysis.analysis(lovAnalysis.getLovList(),
							rgAnalysis.getRecordGroupList());
					monitor.worked(1);

				} catch (Exception e) {
					throw new InvocationTargetException(e);
				} finally {
					monitor.done();
				}

			}

		};

		try {
			getContainer().run(true, true, op);
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			// // ErrorDialog stuff
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public CleanupForm getCurrentForm() {
		TableItem item = getTable().getSelection()[0];
		CleanupForm form = (CleanupForm) item.getData();
		return form;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public CleanupForm getCleanupForm() {
		return cleanupForm;
	}

	public void setCleanupForm(CleanupForm form) {
		this.cleanupForm = form;
	}

	private void fillToolbarModelProvider() {
		DefaultToolbarModelProvider modelProvider = DefaultToolbarModelProvider
				.getInstance();
		if (modelProvider.getWindowList().size() > 0) {
			modelProvider.getWindowList().clear();

		}
		if (modelProvider.getBlockList().size() > 0) {
			modelProvider.getBlockList().clear();
		}
		List<Window> windowList = getToolbarModelAnalysis().getWindowList();
		modelProvider.setWindowList(windowList);

		List<Block> blockList = getToolbarModelAnalysis().getBlockList();
		modelProvider.setBlockList(blockList);
	}

	private void fillSpecialCharsModelProvider() {
		SpecialCharsModelProvider modelProvider = SpecialCharsModelProvider
				.getInstance();
		if (modelProvider.getItemsList().size() > 0) {
			modelProvider.getItemsList().clear();
			modelProvider.setIndexItemHasSC(-1);

		}
		List<SpecialCharsItemModel> itemList = getItemAnalysis()
				.getSpecialCharAnalysis().getItemsWithSpecialCharList();
		modelProvider.setItems(itemList);
	}

	private void fillBlockTableProvider() {
		BlockModelProvider modelProvider = BlockModelProvider.getInstance();
		if (modelProvider.getBlockList().size() > 0) {
			modelProvider.getBlockList().clear();

		}
		List<Block> blockList = getBlockAnalysis().getBlockList();
		modelProvider.setBlocks(blockList);
	}

	private void fillLOVTableProvider() {
		LOVModelProvider lovModelProvider = LOVModelProvider.getInstance();
		if (lovModelProvider.getLovRgList().size() > 0) {
			lovModelProvider.getLovRgList().clear();
		}
		List<LOVRecordGroupModelList.LOVRecordGroupModel> lovRgList = lovModelAnalysis
				.getLOVRecordGroupModelList().getLOVRecordGroupModel();
		lovModelProvider.setLovRgList(lovRgList);
	}

	public Rules getRules() {
		if (ruleHandling == null) {
			ruleHandling = new RuleHandling();
			rules = ruleHandling.init();
		}

		return rules;
	}

	public RuleHandling getRuleHandling() {
		return ruleHandling;
	}

	@Deprecated
	public void generatCustomization() {

		LovXmlReaderWriter lovXml = new LovXmlReaderWriter();
		lovXml.setLovRgModelList(getLovModelAnalysis()
				.getLOVRecordGroupModelList());
		try {

			File custFile = new File(Settings.getSettings().getFmbRootDir()
					+ File.separator
					+ getCleanupForm().getFormName().replaceAll(".fmb", "")
							.toUpperCase() + "_customization.xml");
			lovXml.generateCustomization(custFile);
			try {
				lovXml.writeToFile(custFile);
			} catch (TransformerConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerFactoryConfigurationError e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
