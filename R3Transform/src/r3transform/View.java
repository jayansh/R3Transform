package r3transform;

import java.io.File;
import java.io.FileFilter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.eclipse.core.databinding.AggregateValidationStatus;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.PojoObservables;
import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.core.databinding.observable.value.ValueChangeEvent;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.wb.swt.layout.grouplayout.GroupLayout;
import org.eclipse.wb.swt.layout.grouplayout.LayoutStyle;

import com.converter.premigration.FileFilterExt;
import com.converter.premigration.UserInfo;
import com.release3.dbconnect.DBCommand;
import com.release3.dbconnect.DBConnector;
import com.release3.premigrate.Premigration;
import com.release3.tf.analysis.ui.AnalysisModelProvider;
import com.release3.tf.analysis.ui.AnalysisWizard;
import com.release3.tf.core.form.CleanupForm;
import com.release3.tf.core.form.Settings;
import com.release3.tf.migration.MigrationComposite;
import com.release3.tf.migration.model.MigrationForm;
import com.release3.tf.pll.PLLMigrationWizard;
import com.release3.transform.Licensing;
import com.release3.transform.constants.UIConstants;
import com.release3.transform.model.CleanupFormModelProvider;
import com.release3.transform.ui.PremigrationWizard;
import com.release3.transform.validators.EmptyStringValidator;
import com.swtdesigner.ResourceManager;

public class View extends ViewPart {
	public View() {
		setTitleImage(ResourceManager.getPluginImage("R3Transform",
				"icons/r3_temp16X16.GIF"));
	}

	public static final String ID = "R3Transform.view";

	private Settings settings;

	private String[] colNames = new String[] { "File", "Status" };
	private String[] dataBasesTypes = new String[] { "Oracle", "Enterprise DB",
			"IBM DB2" };
	private final UserInfo userInfo = new UserInfo();

	private final String errorMessage = "Field cannot be empty.";
	private Label lblDefGenMessage;
	private IObservableValue uiElementDefGen;
	private IObservableValue uiElementAppPrefs;

	private ControlDecoration controlDecorationTxtFullName;
	private ControlDecoration controlDecorationTxtCompany;
	private ControlDecoration controlDecorationTxtDept;
	private ControlDecoration controlDecorationtxtCity;
	private ControlDecoration controlDecorationTxtState;
	private ControlDecoration controlDecorationTxtCountry;
	private ControlDecoration controlDecorationTxtPhone;
	private ControlDecoration controlDecorationTxtEmail;
	private ControlDecoration controlDecorationTxtZipcode;

	private TabFolder tabFolder;
	private TabItem tbtmMigration;
	private TabItem previousTabItem;
	private TabItem currentTabItem;
	private TabItem tbtmDatabasePreferences;
	private MigrationComposite migrateComposite;
	private boolean appPrefApplyButtonPress = true;

	public void createPartControl(final Composite parent) {

		tabFolder = new TabFolder(parent, SWT.NONE);
		tabFolder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				previousTabItem = currentTabItem;
				currentTabItem = tabFolder.getSelection()[0];
				if (previousTabItem == tbtmDatabasePreferences
						|| previousTabItem.equals(tbtmDatabasePreferences)) {
					if (!appPrefApplyButtonPress) {
						// MessageDialog.openQuestion(parent.getShell(),
						// "Save Prefrences",
						// "'Application Prefrences' has been modified. Save changes?");
						// boolean result =
						// MessageDialog.open(MessageDialog.QUESTION_WITH_CANCEL,parent.getShell(),
						// "Save Prefrences",
						// "'Application Prefrences' has been modified. Save changes?",SWT.SHEET);
						MessageDialog msgDialog = new MessageDialog(
								parent.getShell(),
								"Save Prefrences",
								null,
								"'Application Prefrences' has been modified. Save changes?",
								// MessageDialog.QUESTION_WITH_CANCEL
								MessageDialog.QUESTION, new String[] {
										IDialogConstants.YES_LABEL,
										IDialogConstants.NO_LABEL,
										IDialogConstants.CANCEL_LABEL }, 0);
						switch (msgDialog.open()) {
						case 0:
							// yes
							if (uiElementAppPrefs.getValue().equals("OK")) {
								settings.setApplicationName(txtAppName
										.getText());
								settings.setDbSrcPassword(txtSrcDataBasePwd
										.getText());
								settings.setDbSrcUrl(txtSrcDataBaseString
										.getText());
								settings.setDbSrcDriver(txtSrcDbDriver
										.getText());
								settings.setDbSrcUser(txtSrcDBUser.getText());
								settings.setBaseDir(txtOutputDir.getText());
								settings.setOracleDeveloperSuite(txtDevSuiteHome
										.getText());

								settings.setDbDestDriver(txtDestDbDriver
										.getText());
								settings.setDbDestUrl(txtDestDataBaseString
										.getText());
								settings.setDbDestUser(txtDestDBUser.getText());
								settings.setDbDestPassword(txtDestDataBasePwd
										.getText());
								settings.setFmbRootDir(txtRootFormsDir
										.getText());
								settings.save();
								((MigrationComposite) migrateComposite)
										.reload();
								appPrefApplyButtonPress = true;
							}
							System.out.println("yes");
							break;
						case 1:
							// no
							System.out.println("no");
							break;
						case 2:
							tabFolder.setSelection(previousTabItem);
							System.out.println("cancel");
							break;
						}
						appPrefApplyButtonPress = true;
					}
				}
				if (currentTabItem == tbtmMigration
						|| currentTabItem.equals(tbtmMigration)) {
					migrateComposite.refreshLicenseCombo();
				}

			}
		});

		tbtmDatabasePreferences = new TabItem(tabFolder, SWT.NONE);
		tbtmDatabasePreferences.setText("Application Preferences");

		currentTabItem = tbtmDatabasePreferences;

		final ScrolledComposite scrollBox = new ScrolledComposite(tabFolder,
				SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		tbtmDatabasePreferences.setControl(scrollBox);
		scrollBox.setExpandHorizontal(true);
		scrollBox.setExpandVertical(true);
		// Using 0 here ensures the horizontal scroll bar will never appear. If
		// you want the horizontal bar to appear at some threshold (say 100
		// pixels) then send that value instead.
		scrollBox.setMinWidth(0);

		final Composite dbPrefComposite = createDBPreferencesUI(scrollBox);

		dbPrefComposite.addListener(SWT.Resize, new Listener() {
			int width = -1;

			public void handleEvent(Event e) {
				int newWidth = dbPrefComposite.getSize().x;
				if (newWidth != width) {
					scrollBox.setMinHeight(dbPrefComposite.computeSize(
							newWidth, SWT.DEFAULT).y);
					width = newWidth;
				}
			}
		});
		scrollBox.setContent(dbPrefComposite);

		Group group = new Group(composite, SWT.NONE);
		group.setText("Workspace Settings");

		final Combo combo = new Combo(group, SWT.NONE);
		combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				settings.setWorkspaceSettings(combo.getItem(combo
						.getSelectionIndex()));
			}
		});
		combo.setItems(new String[] {"JBoss Studio+JBoss Server", "Spring-Source-Tool-Suite", "MyEclipse+IBM Websphere", "Eclipse+Oracle WebLogic"});
		combo.setBounds(10, 22, 408, 14);
		combo.select(0);
		int index = settings.indexOf(UIConstants.WORKSPACE_SETTINGS_OPTIONS,
				settings.getWorkspaceSettings());
		if (index > -1) {
			combo.select(index);
		}

		Group group_1 = new Group(composite, SWT.NONE);

		GroupLayout gl_composite = new GroupLayout(composite);
		gl_composite
				.setHorizontalGroup(gl_composite
						.createParallelGroup(GroupLayout.LEADING)
						.add(gl_composite
								.createSequentialGroup()
								.addContainerGap()
								.add(gl_composite
										.createParallelGroup(
												GroupLayout.LEADING)
										.add(GroupLayout.TRAILING, btnApply_1,
												GroupLayout.PREFERRED_SIZE, 68,
												GroupLayout.PREFERRED_SIZE)
										.add(GroupLayout.TRAILING,
												gl_composite
														.createParallelGroup(
																GroupLayout.LEADING)
														.add(grpDestinationDatabase,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.add(grpSourceDatabase,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)))
								.addPreferredGap(LayoutStyle.RELATED)
								.add(gl_composite
										.createParallelGroup(
												GroupLayout.LEADING)
										.add(group_1,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.add(grpMessage,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)))
						.add(gl_composite
								.createSequentialGroup()
								.addContainerGap()
								.add(grpAppSettings,
										GroupLayout.PREFERRED_SIZE, 554,
										GroupLayout.PREFERRED_SIZE))
						.add(gl_composite
								.createSequentialGroup()
								.addContainerGap()
								.add(group, GroupLayout.PREFERRED_SIZE, 554,
										GroupLayout.PREFERRED_SIZE)));
		gl_composite
				.setVerticalGroup(gl_composite
						.createParallelGroup(GroupLayout.LEADING)
						.add(gl_composite
								.createSequentialGroup()
								.add(4)
								.add(grpAppSettings,
										GroupLayout.PREFERRED_SIZE, 144,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.RELATED)
								.add(group, GroupLayout.PREFERRED_SIZE, 54,
										GroupLayout.PREFERRED_SIZE)
								.add(8)
								.add(gl_composite
										.createParallelGroup(
												GroupLayout.TRAILING)
										.add(gl_composite
												.createSequentialGroup()
												.add(27)
												.add(grpMessage,
														GroupLayout.PREFERRED_SIZE,
														160,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(
														LayoutStyle.RELATED)
												.add(group_1,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))
										.add(GroupLayout.LEADING,
												gl_composite
														.createSequentialGroup()
														.addPreferredGap(
																LayoutStyle.RELATED)
														.add(grpSourceDatabase,
																GroupLayout.PREFERRED_SIZE,
																144,
																GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(
																LayoutStyle.RELATED)
														.add(grpDestinationDatabase,
																GroupLayout.PREFERRED_SIZE,
																206,
																GroupLayout.PREFERRED_SIZE)))
								.add(8)
								.add(btnApply_1, GroupLayout.PREFERRED_SIZE,
										25, GroupLayout.PREFERRED_SIZE).add(46)));

		btnChkDestDbSame.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {

				Activator
						.getDefault()
						.getPreferenceStore()
						.setValue(Activator.IS_DEST_SAME_AS_SOURCE_DB,
								btnChkDestDbSame.getSelection());

				if (!btnChkDestDbSame.getSelection()) {
					// grpDestinationDatabase.setEnabled(true);
					disableDestinationControls(true);
					cmbDatabaseType.select(-1);
				} else {
					// grpDestinationDatabase.setEnabled(false);
					disableDestinationControls(false);
					cmbDatabaseType.select(0);
					txtDestDataBaseString.setText(txtSrcDataBaseString
							.getText());
					txtDestDbDriver.setText(txtSrcDbDriver.getText());
					txtDestDBUser.setText(txtSrcDBUser.getText());
					txtDestDataBasePwd.setText(txtSrcDataBasePwd.getText());
				}
			}
		});

		// Canvas canvas = new Canvas(group_3, SWT.NONE);
		// canvas.setBounds(10, 10, 269, 140);
		// Image logoImage = new Image(parent.getDisplay(),"splash.bmp");

		Label label = new Label(group_1, SWT.NONE);
		label.setBounds(10, 10, 338, 108);
		label.setText("For EnterpriseDB:\r\nDetails are required here, if your target database will be EnterpriseDB \r\nPostgres Plus Advanced Server.\r\n\r\nFor Oracle DB:\r\nPlease enter information here ONLY IF your target Oracle database \r\nwill be different than the source.\r\n\r\n\r\n");
		composite.setLayout(gl_composite);

		// TabItem tbtmPreMigration = new TabItem(tabFolder, SWT.NONE);
		// tbtmPreMigration.setText("Pre Migration");
		//
		// Composite cleanupComposite = new Composite(tabFolder, SWT.NONE);
		// tbtmPreMigration.setControl(cleanupComposite);

		TabItem tbtmDefGeneration = new TabItem(tabFolder, SWT.NONE);
		// tbtmDefGeneration.setText("Definition Generation");
		tbtmDefGeneration.setText("Pre Migration");

		Composite preMigrationComposite = createPreMigrationUI(tabFolder,
				SWT.NONE);
		tbtmDefGeneration.setControl(preMigrationComposite);

		tbtmMigration = new TabItem(tabFolder, SWT.NONE);
		tbtmMigration.setText("Migration");

		migrateComposite = createMigrateUI(tabFolder, SWT.NONE);
		tbtmMigration.setControl(migrateComposite);

		TabItem tbtmLicensing = new TabItem(tabFolder, SWT.NONE);
		tbtmLicensing.setText("Licensing");

		Composite licenseComposite = new Licensing(tabFolder, SWT.NONE);
		tbtmLicensing.setControl(licenseComposite);

		// setup bold font
		Font boldFont = JFaceResources.getFontRegistry().getBold(
				JFaceResources.DEFAULT_FONT);
	}

	private Text txtAppName;
	private Text txtOutputDir;
	private Text txtDevSuiteHome;
	private Text txtRootFormsDir;
	private Text txtSrcDataBaseString;
	private Text txtSrcDbDriver;
	private Text txtSrcDBUser;
	private Text txtSrcDataBasePwd;
	private Text txtDestDataBaseString;
	private Combo cmbDatabaseType;
	private Text txtDestDbDriver;
	private Text txtDestDBUser;
	private Text txtDestDataBasePwd;

	private ControlDecoration controlDecorationTxtAppName;
	private ControlDecoration controlDecorationTxtOutputDir;
	private ControlDecoration controlDecorationTxtDevSuiteHome;
	private ControlDecoration controlDecorationTxtRootFormsDir;

	private ControlDecoration controlDecorationTxtSrcDataBaseString;
	private ControlDecoration controlDecorationTxtSrcDbDriver;
	private ControlDecoration controlDecorationTxtSrcDBUser;
	private ControlDecoration controlDecorationTxtSrcDataBasePwd;
	private ControlDecoration controlDecorationTxtDestDataBaseString;
	private ControlDecoration controlDecorationTxtDestDbDriver;
	private ControlDecoration controlDecorationTxtDestDBUser;
	private ControlDecoration controlDecorationTxtDestDataBasePwd;
	private Label lblAppPrefMessage;

	protected Composite createDBPreferencesUI(Composite parent) {
		composite = new Composite(parent, SWT.NONE);
		// composite.setExpandHorizontal(true);
		// composite.setExpandVertical(true);
		settings = Settings.getSettings();

		grpAppSettings = new Group(composite, SWT.NONE);

		Label lblRootFormsDir = new Label(grpAppSettings, SWT.NONE);
		lblRootFormsDir.setBounds(10, 109, 93, 19);
		lblRootFormsDir.setText("Root Forms Dir:");

		txtRootFormsDir = new Text(grpAppSettings, SWT.BORDER);
		txtRootFormsDir.setBounds(178, 109, 254, 19);
		// txtRootFormsDir.setText(settings.getFmbRootDir());

		btnBrowseRootFrmDir = new Button(grpAppSettings, SWT.NONE);
		btnBrowseRootFrmDir.setBounds(443, 107, 68, 23);
		btnBrowseRootFrmDir.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dirDialog = new DirectoryDialog(composite
						.getShell());
				dirDialog.setText("Select a directory");
				dirDialog.setFilterPath(txtRootFormsDir.getText());
				String uploadFilePath = dirDialog.open();
				if (uploadFilePath != null) {
					txtRootFormsDir.setText(uploadFilePath);
				}
			}
		});
		btnBrowseRootFrmDir.setText("Browse...");

		lblOracleDeveloperSuite = new Label(grpAppSettings, SWT.NONE);
		lblOracleDeveloperSuite.setBounds(10, 77, 162, 19);
		lblOracleDeveloperSuite.setText("Oracle Developer Suite Home:");

		txtDevSuiteHome = new Text(grpAppSettings, SWT.BORDER);
		txtDevSuiteHome.setBounds(178, 77, 254, 19);
		// txtDevSuiteHome.setText(settings.getOracleDeveloperSuite());

		btnBrowseDevSuiteHome = new Button(grpAppSettings, SWT.NONE);
		btnBrowseDevSuiteHome.setBounds(443, 75, 68, 23);
		btnBrowseDevSuiteHome.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				DirectoryDialog dirDialog = new DirectoryDialog(composite
						.getShell());
				dirDialog.setText("Select a directory");
				dirDialog.setFilterPath(txtDevSuiteHome.getText());
				String outputDirPath = dirDialog.open();
				if (outputDirPath != null) {
					txtDevSuiteHome.setText(outputDirPath);
				}
			}
		});
		btnBrowseDevSuiteHome.setText("Browse...");

		txtOutputDir = new Text(grpAppSettings, SWT.BORDER);
		txtOutputDir.setBounds(178, 45, 254, 19);
		// txtOutputDir.setText(settings.getBaseDir());

		btnBrowseOutputDir = new Button(grpAppSettings, SWT.NONE);
		btnBrowseOutputDir.setBounds(443, 43, 68, 23);
		btnBrowseOutputDir.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				DirectoryDialog dirDialog = new DirectoryDialog(composite
						.getShell());
				dirDialog.setText("Select a directory");
				dirDialog.setFilterPath(txtOutputDir.getText());
				String outputDirPath = dirDialog.open();
				if (outputDirPath != null) {
					txtOutputDir.setText(outputDirPath);
				}

			}
		});
		btnBrowseOutputDir.setText("Browse...");

		lblOutputDirectory = new Label(grpAppSettings, SWT.NONE);
		lblOutputDirectory.setBounds(10, 45, 105, 19);
		lblOutputDirectory.setText("Output Directory:");

		txtAppName = new Text(grpAppSettings, SWT.BORDER);
		txtAppName.setBounds(178, 13, 254, 19);
		// txtAppName.setText(settings.getApplicationName());

		lblApplicationName = new Label(grpAppSettings, SWT.NONE);
		lblApplicationName.setBounds(10, 13, 105, 19);
		lblApplicationName.setText("Application Name:");

		grpSourceDatabase = new Group(composite, SWT.NONE);
		grpSourceDatabase.setText("Source Database");

		txtSrcDataBaseString = new Text(grpSourceDatabase, SWT.BORDER);
		txtSrcDataBaseString.addKeyListener(new KeyAdapter() {

			public void keyReleased(KeyEvent e) {
				if (btnChkDestDbSame.getSelection()) {
					txtDestDataBaseString.setText(txtSrcDataBaseString
							.getText());
				}
			}
		});

		txtSrcDataBaseString.setBounds(178, 14, 254, 19);

		// txtSrcDataBaseString.setText(settings.getDbSrcUrl());
		grpMessage = new Group(composite, SWT.NONE);

		Label lblSrcDataBaseString = new Label(grpSourceDatabase, SWT.NONE);
		lblSrcDataBaseString.setBounds(10, 14, 157, 19);
		lblSrcDataBaseString.setText("DataBase String Connection:");

		Label lblSrcDatabaseDriver = new Label(grpSourceDatabase, SWT.NONE);
		lblSrcDatabaseDriver.setBounds(10, 47, 125, 19);
		lblSrcDatabaseDriver.setText("DataBase Driver:");

		txtSrcDbDriver = new Text(grpSourceDatabase, SWT.BORDER);
		txtSrcDbDriver.addKeyListener(new KeyAdapter() {

			public void keyReleased(KeyEvent e) {
				if (btnChkDestDbSame.getSelection()) {
					txtDestDbDriver.setText(txtSrcDbDriver.getText());
				}
			}
		});
		txtSrcDbDriver.setBounds(178, 47, 254, 19);
		// txtSrcDbDriver.setText(settings.getDbSrcDriver());

		txtSrcDBUser = new Text(grpSourceDatabase, SWT.BORDER);
		txtSrcDBUser.addKeyListener(new KeyAdapter() {

			public void keyReleased(KeyEvent e) {
				if (btnChkDestDbSame.getSelection()) {
					txtDestDBUser.setText(txtSrcDBUser.getText());
				}
			}
		});
		txtSrcDBUser.setBounds(178, 80, 254, 19);
		// txtSrcDBUser.setText(settings.getDbSrcUser());

		Label lblSrcDatabaseUser = new Label(grpSourceDatabase, SWT.NONE);
		lblSrcDatabaseUser.setBounds(10, 80, 85, 19);
		lblSrcDatabaseUser.setText("DataBase User:");

		Label lblSrcDataBasePassword = new Label(grpSourceDatabase, SWT.NONE);
		lblSrcDataBasePassword.setBounds(10, 113, 105, 19);
		lblSrcDataBasePassword.setText("DataBase Password:");

		txtSrcDataBasePwd = new Text(grpSourceDatabase, SWT.BORDER
				| SWT.PASSWORD);
		txtSrcDataBasePwd.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (btnChkDestDbSame.getSelection()) {
					txtDestDataBasePwd.setText(txtSrcDataBasePwd.getText());
				}
			}
		});
		txtSrcDataBasePwd.setBounds(178, 113, 254, 19);
		Button btnSrcDBTestConnection = new Button(grpSourceDatabase, SWT.NONE);
		btnSrcDBTestConnection.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DBConnector dbConnector = new DBConnector();
				dbConnector.setDbDriver(txtSrcDbDriver.getText());
				dbConnector.setDbURL(txtSrcDataBaseString.getText());
				dbConnector.setUserName(txtSrcDBUser.getText());
				dbConnector.setPassword(txtSrcDataBasePwd.getText());

				try {
					boolean result = dbConnector.testConnection();
					if (result) {
						MessageDialog.openInformation(composite.getShell(),
								"Connection successful.",
								"Connection successful.");
					} else {
						MessageDialog.openError(composite.getShell(),
								"Connection failed.", "Connection failed.");
					}
				} catch (ClassNotFoundException e1) {
					MessageDialog.openError(composite.getShell(),
							"Connection error!", e1.getLocalizedMessage());
					e1.printStackTrace();
				} catch (SQLException e1) {
					MessageDialog.openError(composite.getShell(),
							"Connection error!", e1.getLocalizedMessage());
					e1.printStackTrace();
				}

			}
		});
		btnSrcDBTestConnection.setBounds(443, 111, 105, 23);
		btnSrcDBTestConnection.setText("Test Connection");
		// txtSrcDataBasePwd.setText(settings.getDbSrcPassword());

		grpDestinationDatabase = new Group(composite, SWT.NONE);
		grpDestinationDatabase.setText("Destination Database");

		btnChkDestDbSame = new Button(grpDestinationDatabase, SWT.CHECK);
		btnChkDestDbSame.setBounds(10, 17, 292, 16);
		btnChkDestDbSame
				.setText("Destination Database is same as Source Database");
		btnChkDestDbSame.setSelection(Activator.getDefault()
				.getPreferenceStore()
				.getBoolean(Activator.IS_DEST_SAME_AS_SOURCE_DB));

		cmbDatabaseType = new Combo(grpDestinationDatabase, SWT.NONE);
		cmbDatabaseType.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				switch (cmbDatabaseType.getSelectionIndex()) {
				case 0:
					txtDestDataBaseString.setText(Activator.ORACLE_URL_STRING);
					txtDestDbDriver.setText(Activator.ORACLE_DRIVER);
					break;
				case 1:
					txtDestDataBaseString.setText(Activator.EDB_URL_STRING);
					txtDestDbDriver.setText(Activator.EDB_DRIVER);
					break;
				case 2:
					txtDestDataBaseString.setText(Activator.DB2_URL_STRING);
					txtDestDbDriver.setText(Activator.DB2_DRIVER);
					break;
				}
				Settings.getSettings().setDatabaseType(
						cmbDatabaseType.getText());
			}
		});
		cmbDatabaseType.setItems(dataBasesTypes);

		cmbDatabaseType.setBounds(171, 46, 149, 19);

		cmbDatabaseType.select(getIndex());
		Label lblDatabaseType = new Label(grpDestinationDatabase, SWT.NONE);
		lblDatabaseType.setBounds(10, 50, 105, 15);
		lblDatabaseType.setText("DataBase Type:");

		txtDestDataBaseString = new Text(grpDestinationDatabase, SWT.BORDER);

		txtDestDataBaseString.setBounds(171, 82, 254, 19);
		// txtDestDataBaseString.setText(settings.getDbDestUrl());

		Label lblDestDataBaseString = new Label(grpDestinationDatabase,
				SWT.NONE);
		lblDestDataBaseString.setText("DataBase String Connection:");
		lblDestDataBaseString.setBounds(10, 82, 155, 19);

		Label lblDestDatabaseDriver = new Label(grpDestinationDatabase,
				SWT.NONE);
		lblDestDatabaseDriver.setText("DataBase Driver:");
		lblDestDatabaseDriver.setBounds(10, 118, 125, 13);

		txtDestDbDriver = new Text(grpDestinationDatabase, SWT.BORDER);
		// txtDestDbDriver.setText(settings.getDbDestDriver());
		txtDestDbDriver.setBounds(171, 115, 254, 19);

		txtDestDBUser = new Text(grpDestinationDatabase, SWT.BORDER);
		// txtDestDBUser.setText(settings.getDbDestUser());
		txtDestDBUser.setBounds(171, 145, 254, 19);

		Label lblDestDatabaseUser = new Label(grpDestinationDatabase, SWT.NONE);
		lblDestDatabaseUser.setText("DataBase User:");
		lblDestDatabaseUser.setBounds(10, 148, 85, 13);

		Label lblDestDataBasePassword = new Label(grpDestinationDatabase,
				SWT.NONE);
		lblDestDataBasePassword.setText("DataBase Password:");
		lblDestDataBasePassword.setBounds(10, 178, 105, 13);

		txtDestDataBasePwd = new Text(grpDestinationDatabase, SWT.BORDER
				| SWT.PASSWORD);
		// txtDestDataBasePwd.setText(settings.getDbDestPassword());
		txtDestDataBasePwd.setBounds(171, 175, 254, 19);

		btnChkDestDbSame.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {

				Activator
						.getDefault()
						.getPreferenceStore()
						.setValue(Activator.IS_DEST_SAME_AS_SOURCE_DB,
								btnChkDestDbSame.getSelection());

				if (!btnChkDestDbSame.getSelection()) {
					// grpDestinationDatabase.setEnabled(true);
					disableDestinationControls(true);
					cmbDatabaseType.select(-1);
				} else {
					// grpDestinationDatabase.setEnabled(false);
					disableDestinationControls(false);
					cmbDatabaseType.select(0);
					txtDestDataBaseString.setText(txtSrcDataBaseString
							.getText());
					txtDestDbDriver.setText(txtSrcDbDriver.getText());
					txtDestDBUser.setText(txtSrcDBUser.getText());
					txtDestDataBasePwd.setText(txtSrcDataBasePwd.getText());
				}
			}
		});

		Button btnDestDBTestConnection = new Button(grpDestinationDatabase,
				SWT.NONE);
		btnDestDBTestConnection.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DBConnector dbConnector = new DBConnector();
				dbConnector.setDbDriver(txtDestDbDriver.getText());
				dbConnector.setDbURL(txtDestDataBaseString.getText());
				dbConnector.setUserName(txtDestDBUser.getText());
				dbConnector.setPassword(txtDestDataBasePwd.getText());

				try {
					boolean result = dbConnector.testConnection();
					if (result) {
						MessageDialog.openInformation(composite.getShell(),
								"Connection successful.",
								"Connection successful.");
					} else {
						MessageDialog.openError(composite.getShell(),
								"Connection failed.", "Connection failed.");
					}
				} catch (ClassNotFoundException e1) {
					MessageDialog.openError(composite.getShell(),
							"Connection error!", e1.getLocalizedMessage());
					e1.printStackTrace();
				} catch (SQLException e1) {
					MessageDialog.openError(composite.getShell(),
							"Connection error!", e1.getLocalizedMessage());
					e1.printStackTrace();
				}

			}
		});
		btnDestDBTestConnection.setBounds(443, 173, 105, 23);
		btnDestDBTestConnection.setText("Test Connection");

		btnApply_1 = new Button(composite, SWT.NONE);
		btnApply_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (uiElementAppPrefs.getValue().equals("OK")) {
					settings.setApplicationName(txtAppName.getText());
					settings.setDbSrcPassword(txtSrcDataBasePwd.getText());
					settings.setDbSrcUrl(txtSrcDataBaseString.getText());
					settings.setDbSrcDriver(txtSrcDbDriver.getText());
					settings.setDbSrcUser(txtSrcDBUser.getText());
					settings.setBaseDir(txtOutputDir.getText());
					settings.setOracleDeveloperSuite(txtDevSuiteHome.getText());

					settings.setDbDestDriver(txtDestDbDriver.getText());
					settings.setDbDestUrl(txtDestDataBaseString.getText());
					settings.setDbDestUser(txtDestDBUser.getText());
					settings.setDbDestPassword(txtDestDataBasePwd.getText());
					settings.setFmbRootDir(txtRootFormsDir.getText());
					settings.save();
					((MigrationComposite) migrateComposite).reload();
					appPrefApplyButtonPress = true;
				}
			}
		});
		btnApply_1.setText("Apply");
		controlDecorationTxtAppName = createDecorator(txtAppName, errorMessage);
		controlDecorationTxtOutputDir = createDecorator(txtOutputDir,
				errorMessage);
		controlDecorationTxtDevSuiteHome = createDecorator(txtDevSuiteHome,
				errorMessage);
		controlDecorationTxtRootFormsDir = createDecorator(txtRootFormsDir,
				errorMessage);
		controlDecorationTxtSrcDataBaseString = createDecorator(
				txtSrcDataBaseString, errorMessage);
		controlDecorationTxtSrcDbDriver = createDecorator(txtSrcDbDriver,
				errorMessage);
		controlDecorationTxtSrcDBUser = createDecorator(txtSrcDBUser,
				errorMessage);
		controlDecorationTxtSrcDataBasePwd = createDecorator(txtSrcDataBasePwd,
				errorMessage);
		controlDecorationTxtDestDataBaseString = createDecorator(
				txtDestDataBaseString, errorMessage);
		controlDecorationTxtDestDbDriver = createDecorator(txtDestDbDriver,
				errorMessage);
		controlDecorationTxtDestDBUser = createDecorator(txtDestDBUser,
				errorMessage);
		controlDecorationTxtDestDataBasePwd = createDecorator(
				txtDestDataBasePwd, errorMessage);

		lblAppPrefMessage = new Label(grpMessage, SWT.NONE);
		lblAppPrefMessage.setBounds(10, 10, 165, 140);

		checkBoxListner();
		bindValuesAppPref();

		return composite;
	}

	private void disableDestinationControls(boolean editable) {
		if (!editable) {
			cmbDatabaseType.select(0);

		}
		cmbDatabaseType.setEnabled(editable);
		txtDestDataBaseString.setEditable(editable);
		txtDestDbDriver.setEditable(editable);
		txtDestDBUser.setEditable(editable);
		txtDestDataBasePwd.setEditable(editable);
	}

	private void checkBoxListner() {
		if (!btnChkDestDbSame.getSelection()) {
			if (Settings.getSettings().getDbDestUrl().contains("Oracle")) {
				cmbDatabaseType.select(0);

			} else if (Settings.getSettings().getDbDestUrl().contains("edb")) {
				cmbDatabaseType.select(1);
			} else if (Settings.getSettings().getDbDestUrl().contains("db2")) {
				cmbDatabaseType.select(2);
			}
			// grpDestinationDatabase.setEnabled(true);
			disableDestinationControls(true);

		} else {
			cmbDatabaseType.select(0);
			txtDestDataBaseString.setText(txtSrcDataBaseString.getText());
			txtDestDbDriver.setText(txtSrcDbDriver.getText());
			txtDestDBUser.setText(txtSrcDBUser.getText());
			txtDestDataBasePwd.setText(txtSrcDataBasePwd.getText());
			// grpDestinationDatabase.setEnabled(false);
			disableDestinationControls(false);

		}
	}

	protected Composite createPreMigrationUI(final Composite parent, int style) {

		final Composite preMigrateComposite = new Composite(parent, style);

		Label lblSourceFolder = new Label(preMigrateComposite, SWT.NONE);
		lblSourceFolder.setBounds(10, 13, 86, 18);
		lblSourceFolder.setText("Source Folder");

		final Text txtFileSrc = new Text(preMigrateComposite, SWT.BORDER);
		txtFileSrc.setBounds(118, 12, 262, 19);
		txtFileSrc.setText(Activator.getDefault().getPreferenceStore()
				.getString(Activator.CLEANUP_SRC_DIR_PATH));

		Button btnBrowseSrc = new Button(preMigrateComposite, SWT.NONE);
		btnBrowseSrc.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dialog = new DirectoryDialog(
						preMigrateComposite.getShell());
				dialog.setFilterPath(txtFileSrc.getText());
				String path = dialog.open();
				if (path != null) {
					txtFileSrc.setText(path);
				}
			}
		});
		btnBrowseSrc.setBounds(403, 10, 68, 23);
		btnBrowseSrc.setText("Browse");

		final Text txtFileDest = new Text(preMigrateComposite, SWT.BORDER);
		txtFileDest.setBounds(118, 39, 262, 19);
		txtFileDest.setText(Activator.getDefault().getPreferenceStore()
				.getString(Activator.CLEANUP_DEST_DIR_PATH));

		Label lblDestinationFolder = new Label(preMigrateComposite, SWT.NONE);
		lblDestinationFolder.setBounds(10, 39, 102, 19);
		lblDestinationFolder.setText("Destination Folder");

		Button btnBrowse = new Button(preMigrateComposite, SWT.NONE);
		btnBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dialog = new DirectoryDialog(
						preMigrateComposite.getShell());
				dialog.setFilterPath(txtFileDest.getText());
				String path = dialog.open();
				if (path != null) {
					txtFileDest.setText(path);
				}
			}
		});
		btnBrowse.setBounds(403, 37, 68, 23);
		btnBrowse.setText("Browse");

		ExpandBar expandBarPremigration = new ExpandBar(preMigrateComposite,
				SWT.NONE);
		expandBarPremigration.setForeground(SWTResourceManager
				.getColor(0, 0, 0));
		expandBarPremigration.setLocation(5, 65);
		expandBarPremigration.setSize(678, 512);

		Group grpAnalysis = new Group(expandBarPremigration, SWT.NONE);

		Button btnAnalyse = new Button(grpAnalysis, SWT.NONE);
		btnAnalyse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				DirectoryDialog dirDialog = new DirectoryDialog(
						preMigrateComposite.getShell());
				dirDialog.setFilterPath(txtFileSrc.getText());
				String path = dirDialog.open();

				File formsSrcDir = new File(path);
				String[] str = { "fmb" };
				FileFilter filter = new FileFilterExt("*.fmb", str);
				File[] fileArr = formsSrcDir.listFiles(filter);

				AnalysisModelProvider modelProvider = AnalysisModelProvider
						.getInstance();
				if (modelProvider.getFormList().size() > 0) {
					modelProvider.getFormList().clear();
				}
				File destFormDir = new File(txtFileDest.getText());
				for (File file : fileArr) {
					MigrationForm form = new MigrationForm(file, "False");
					String formXmlPath = destFormDir + File.separator
							+ file.getName().replaceFirst(".fmb", "_fmb")
							+ ".xml";

					if (formXmlPath.contains(".FMB")) {
						formXmlPath = destFormDir + File.separator
								+ file.getName().replaceFirst(".FMB", "_FMB");
					}
					form.setFmbXmlPath(formXmlPath);
					modelProvider.getFormList().add(form);
				}

				AnalysisWizard wizard = new AnalysisWizard();

				WizardDialog dialog = new WizardDialog(parent.getShell(),
						wizard);
				dialog.create();
				dialog.open();
				// if (dialog.close()) {
				// wizard.dispose();
				// wizard = null;
				// }
			}
		});
		btnAnalyse.setBounds(10, 22, 67, 23);
		btnAnalyse.setText("Analyse");

		ExpandItem xpndtmMigratePLLExpanditem = new ExpandItem(
				expandBarPremigration, SWT.NONE);
		xpndtmMigratePLLExpanditem.setExpanded(true);
		xpndtmMigratePLLExpanditem.setText("Migrate PLL");

		Group grpMigratePLL = new Group(expandBarPremigration, SWT.NONE);
		xpndtmMigratePLLExpanditem.setControl(grpMigratePLL);

		Button btnMigratePll = new Button(grpMigratePLL, SWT.NONE);
		btnMigratePll.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PLLMigrationWizard wizard = new PLLMigrationWizard();

				WizardDialog dialog = new WizardDialog(parent.getShell(),
						wizard);
				dialog.create();
				dialog.open();
			}
		});
		btnMigratePll.setBounds(10, 26, 75, 25);
		btnMigratePll.setText("Migrate PLL");
		xpndtmMigratePLLExpanditem.setHeight(xpndtmMigratePLLExpanditem
				.getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT).y);

		ExpandItem xpndtmCleanupExpanditem = new ExpandItem(
				expandBarPremigration, SWT.NONE);
		xpndtmCleanupExpanditem.setExpanded(true);
		xpndtmCleanupExpanditem.setText("Clean-Up Process");

		Group grpCleanUpProcess = new Group(expandBarPremigration, SWT.NONE);
		xpndtmCleanupExpanditem.setControl(grpCleanUpProcess);

		Button btnProcess = new Button(grpCleanUpProcess, SWT.NONE);
		btnProcess.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				/*
				 * Cleanup cleanup = new Cleanup();
				 * cleanup.cleanupOperation(txtFileSrc.getText(), txtFileDest
				 * .getText());
				 */

				Activator
						.getDefault()
						.getPreferenceStore()
						.setValue(Activator.CLEANUP_SRC_DIR_PATH,
								txtFileSrc.getText());
				Activator
						.getDefault()
						.getPreferenceStore()
						.setValue(Activator.CLEANUP_DEST_DIR_PATH,
								txtFileDest.getText());
				/**
				 * Moved code from here do it one by one once user select a
				 * form.
				 */
				// FormtoXML formtoxml = new FormtoXML();
				// formtoxml.forms2xmls(new File( txtFileDest
				// .getText()));
				// List<File> xmlList = formtoxml.getFormXmlList();
				// CleanupFormModelProvider modelProvider =
				// CleanupFormModelProvider.getInstance();
				//
				// modelProvider.setCleanupForms(xmlList);
				/**
				 * Check for database version.
				 */
				String databaseVersion = DBCommand.getDBCommand()
						.getSourceDatabaseVersion();
				if (databaseVersion == null) {
					MessageDialog.openError(Display.getDefault()
							.getActiveShell(), "Connection Error",
							"Database connection failed.");
				}
				/** Commented by Jay on 14-10-2011, only for DLA */
				// else if (databaseVersion.indexOf("11g") > -1) {
				// MessageDialog
				// .openWarning(
				// Display.getDefault().getActiveShell(),
				// "Connection Warning",
				// "Your DevSuite is currently connecting to 11g Database. " +
				// "There is a known issue with 11g Database where you may lose "
				// +
				// "the Record Group query during this cleanup process if you decide to continue. "
				// +
				// "Please connect to a 9i or 10g database instead.");
				// }
				/**
				 * Add new code for populating forms in table.
				 */
				File formsSrcDir = new File(txtFileSrc.getText());
				String[] str = { "fmb" };
				FileFilter filter = new FileFilterExt("*.fmb", str);
				File[] fileArr = formsSrcDir.listFiles(filter);

				CleanupFormModelProvider modelProvider = CleanupFormModelProvider
						.getInstance();
				if (modelProvider.getCleanupForms().size() > 0) {
					modelProvider.getCleanupForms().clear();
				}
				File destFormDir = new File(txtFileDest.getText());
				for (File file : fileArr) {
					CleanupForm cleanupForm = new CleanupForm(file, "False");
					String formXmlPath = destFormDir + File.separator
							+ file.getName().replaceFirst(".fmb", "_fmb")
							+ ".xml";

					if (formXmlPath.contains(".FMB")) {
						formXmlPath = destFormDir + File.separator
								+ file.getName().replaceFirst(".FMB", "_FMB");
					}
					cleanupForm.setFormXml(new File(formXmlPath));
					modelProvider.getCleanupForms().add(cleanupForm);
				}

				PremigrationWizard wizard = new PremigrationWizard();

				WizardDialog dialog = new WizardDialog(parent.getShell(),
						wizard);
				dialog.create();
				dialog.open();
				// if (dialog.close()) {
				// wizard.dispose();
				// wizard = null;
				// }
			}
		});
		btnProcess.setBounds(10, 28, 67, 23);
		btnProcess.setText("Process");

		Label lblProcess = new Label(grpCleanUpProcess, SWT.NONE);
		lblProcess.setBounds(94, 20, 398, 31);
		lblProcess
				.setText("Use the Cleanup process to make changes related to object\r\ndependencies, special characters, prompts, LOVs and datablocks.");
		xpndtmCleanupExpanditem.setHeight(xpndtmCleanupExpanditem.getControl()
				.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);

		ExpandItem xpndtmDefinitionGenExpanditem = new ExpandItem(
				expandBarPremigration, SWT.NONE);
		xpndtmDefinitionGenExpanditem.setExpanded(true);
		xpndtmDefinitionGenExpanditem.setText("Definition File Generation");

		Group grpDefinationFileGeneration = new Group(expandBarPremigration,
				SWT.NONE);
		xpndtmDefinitionGenExpanditem.setControl(grpDefinationFileGeneration);

		Button btnGenerate = new Button(grpDefinationFileGeneration, SWT.NONE);
		btnGenerate.setBounds(10, 25, 68, 23);
		btnGenerate.setText("Generate");

		btnGenerate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Premigration premigration = new Premigration();

				String destPath = txtFileDest.getText();
				File destDir = new File(destPath);
				try {
					if (uiElementDefGen.getValue().equals("OK")) {

						Activator
								.getDefault()
								.getPreferenceStore()
								.setValue(Activator.CLEANUP_SRC_DIR_PATH,
										txtFileSrc.getText());
						Activator
								.getDefault()
								.getPreferenceStore()
								.setValue(Activator.CLEANUP_DEST_DIR_PATH,
										txtFileDest.getText());
						premigration.definitionGenerator(destPath);
						MessageDialog.openWarning(parent.getShell(),
								"Success!!!",
								"Definition file generated sucessfully.");
					} else {
						MessageDialog.openWarning(parent.getShell(),
								"Warning!!!",
								"Please fill User Information and press Save.");
					}
				} catch (Exception exc) {
					MessageDialog.openWarning(parent.getShell(), "Error!!!",
							exc.getMessage());
					exc.printStackTrace();
				}
			}
		});
		Label lblGenerate = new Label(grpDefinationFileGeneration, SWT.NONE);
		lblGenerate.setBounds(95, 24, 397, 45);
		lblGenerate
				.setText("Once the clean-Up is performed, generate the Definition File. Send this \r\nfile to Release3 at licensing@release3.com. A license file will be issued \r\non the basis of this definition file.\r\n");

		Group groupMessage = new Group(grpDefinationFileGeneration, SWT.NONE);
		groupMessage.setBounds(10, 405, 478, 53);
		lblDefGenMessage = new Label(groupMessage, SWT.NONE);
		lblDefGenMessage.setBounds(10, 10, 458, 33);
		xpndtmDefinitionGenExpanditem.setHeight(80);

		ExpandItem xpndtmUserInfoExpanditem = new ExpandItem(
				expandBarPremigration, SWT.NONE);
		xpndtmUserInfoExpanditem.setExpanded(true);
		xpndtmUserInfoExpanditem.setText("User Information");

		Group grpUserInformation = new Group(expandBarPremigration, SWT.NONE);
		xpndtmUserInfoExpanditem.setControl(grpUserInformation);

		txtClientID = new Text(grpUserInformation, SWT.BORDER);
		txtClientID.setBounds(85, 14, 208, 19);
		txtClientID.setEditable(false);
		txtClientID.setText(userInfo.getClientId() + "");

		txtFullName = new Text(grpUserInformation, SWT.BORDER);
		txtFullName.setBounds(85, 40, 310, 19);
		// txtFullName.setText(userInfo.getCommanName());

		Label lblCommanName = new Label(grpUserInformation, SWT.NONE);
		lblCommanName.setBounds(10, 40, 59, 19);
		lblCommanName.setText("Full Name:");

		txtCompany = new Text(grpUserInformation, SWT.BORDER);
		txtCompany.setBounds(85, 70, 180, 19);
		// txtCompany.setText(userInfo.getOrganization());

		txtDepartment = new Text(grpUserInformation, SWT.BORDER);
		txtDepartment.setBounds(85, 100, 310, 19);
		// txtDepartment.setText(userInfo.getOrganizationUnit());

		txtAddress1 = new Text(grpUserInformation, SWT.BORDER);
		txtAddress1.setBounds(85, 130, 310, 19);
		txtAddress1.setText(userInfo.getAddress1());

		txtAddress2 = new Text(grpUserInformation, SWT.BORDER);
		txtAddress2.setBounds(85, 160, 310, 19);
		txtAddress2.setText(userInfo.getAddress2());

		txtCity = new Text(grpUserInformation, SWT.BORDER);
		txtCity.setBounds(85, 190, 208, 19);
		// txtCity.setText(userInfo.getLocation());

		txtState = new Text(grpUserInformation, SWT.BORDER);
		txtState.setBounds(85, 220, 171, 19);
		// txtState.setText(userInfo.getState());

		txtCountry = new Text(grpUserInformation, SWT.BORDER);
		txtCountry.setBounds(326, 220, 142, 19);
		// txtCountry.setText(userInfo.getCountry());
		txtPhone = new Text(grpUserInformation, SWT.BORDER);
		txtPhone.setBounds(85, 253, 171, 19);
		// txtPhone.setText(userInfo.getPhone());

		txtZipcode = new Text(grpUserInformation, SWT.BORDER);
		txtZipcode.setBounds(326, 253, 142, 19);

		txtEmail = new Text(grpUserInformation, SWT.BORDER);
		txtEmail.setBounds(85, 283, 208, 19);
		// txtEmail.setText(userInfo.getEmail());

		// txtZipcode.setText(userInfo.getZipcode());

		Label lblCompany = new Label(grpUserInformation, SWT.NONE);
		lblCompany.setBounds(10, 70, 59, 19);
		lblCompany.setText("Company:");

		Label lblDepartment = new Label(grpUserInformation, SWT.NONE);
		lblDepartment.setBounds(10, 100, 69, 19);
		lblDepartment.setText("Department:");

		Label lblCity = new Label(grpUserInformation, SWT.NONE);
		lblCity.setBounds(10, 190, 30, 19);
		lblCity.setText("City:");

		Label lblState = new Label(grpUserInformation, SWT.NONE);
		lblState.setBounds(10, 220, 49, 19);
		lblState.setText("State:");

		Label lblCountry = new Label(grpUserInformation, SWT.NONE);
		lblCountry.setBounds(266, 220, 54, 19);
		lblCountry.setText("Country:");

		Label lblPhoene = new Label(grpUserInformation, SWT.NONE);
		lblPhoene.setBounds(10, 253, 49, 19);
		lblPhoene.setText("Phone:");

		Label lblEmail = new Label(grpUserInformation, SWT.NONE);
		lblEmail.setBounds(10, 283, 43, 19);
		lblEmail.setText("Email:");

		Button btnSave = new Button(grpUserInformation, SWT.NONE);
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (uiElementDefGen.getValue().equals("OK")) {

					userInfo.setCommanName(txtFullName.getText());
					userInfo.setOrganization(txtCompany.getText());
					userInfo.setOrganizationUnit(txtDepartment.getText());
					userInfo.setLocation(txtCity.getText());
					userInfo.setState(txtState.getText());
					userInfo.setCountry(txtCountry.getText());
					userInfo.setEmail(txtEmail.getText());
					userInfo.setPhone(txtPhone.getText());

					userInfo.setAddress1(txtAddress1.getText());
					userInfo.setAddress2(txtAddress2.getText());
					userInfo.setZipcode(txtZipcode.getText());

					userInfo.save();

				}

			}
		});
		btnSave.setBounds(400, 297, 68, 23);
		btnSave.setText("Save");

		Label lblnotePleaseEnter = new Label(grpUserInformation, SWT.NONE);
		lblnotePleaseEnter.setBounds(10, 307, 357, 13);
		lblnotePleaseEnter
				.setText("(Note:- Please enter user information for license file generation)");

		Label lblClientId = new Label(grpUserInformation, SWT.NONE);
		lblClientId.setBounds(10, 14, 49, 19);
		lblClientId.setText("Client ID:");

		Label lblAddress1 = new Label(grpUserInformation, SWT.NONE);
		lblAddress1.setBounds(10, 130, 59, 19);
		lblAddress1.setText("Address1:");

		Label lblAddress2 = new Label(grpUserInformation, SWT.NONE);
		lblAddress2.setBounds(10, 160, 59, 19);
		lblAddress2.setText("Address2:");

		Label lblZipcode = new Label(grpUserInformation, SWT.NONE);
		lblZipcode.setBounds(266, 253, 54, 19);
		lblZipcode.setText("Zipcode:");

		controlDecorationTxtFullName = createDecorator(txtFullName,
				errorMessage);
		controlDecorationTxtCompany = createDecorator(txtCompany, errorMessage);
		controlDecorationTxtDept = createDecorator(txtDepartment, errorMessage);
		controlDecorationtxtCity = createDecorator(txtCity, errorMessage);
		controlDecorationTxtState = createDecorator(txtState, errorMessage);
		controlDecorationTxtCountry = createDecorator(txtCountry, errorMessage);
		controlDecorationTxtPhone = createDecorator(txtPhone, errorMessage);
		controlDecorationTxtEmail = createDecorator(txtEmail, errorMessage);
		controlDecorationTxtZipcode = createDecorator(txtZipcode, errorMessage);
		xpndtmUserInfoExpanditem.setHeight(xpndtmUserInfoExpanditem
				.getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		bindValuesDefGen();
		return preMigrateComposite;
	}

	final Vector<File> formFilesVec = new Vector<File>();

	private Composite composite;

	private Text txtFullName;
	private Text txtCompany;
	private Text txtDepartment;
	private Text txtState;
	private Text txtCountry;
	private Text txtPhone;
	private Text txtEmail;
	private Text txtCity;
	private Text txtAddress1;
	private Text txtAddress2;
	private Text txtClientID;
	private Text txtZipcode;

	private Group grpMessage;
	private Group grpAppSettings;
	private Label lblApplicationName;

	private Label lblOutputDirectory;

	private Button btnBrowseOutputDir;
	private Label lblOracleDeveloperSuite;

	private Button btnBrowseDevSuiteHome;
	private Group grpSourceDatabase;

	private Button btnChkDestDbSame;
	private Group grpDestinationDatabase;
	private Button btnApply_1;

	private Button btnBrowseRootFrmDir;

	private IStatus status;

	private IStatus getStatus() {
		return status;
	}

	private void setStatus(IStatus status) {
		this.status = status;
	}

	protected MigrationComposite createMigrateUI(Composite parent, int style) {

		MigrationComposite migrateComp = new MigrationComposite(parent, style);
		// migrateComp.load();

		return migrateComp;

	}

	public void setFocus() {
	}

	public List getList(Vector<File> forms, int[] indices) {
		List selectedFormsPath = new ArrayList();
		for (int i = 0, j = 0; i < forms.size(); i++) {
			if (i == indices[j]) {
				selectedFormsPath.add(forms.get(i));
				if (j < indices.length - 1) {
					j++;
				}
			}

		}
		return selectedFormsPath;

	}

	private ControlDecoration createDecorator(Text text, String message) {
		ControlDecoration controlDecoration = new ControlDecoration(text,
				SWT.LEFT | SWT.TOP);
		controlDecoration.setDescriptionText(message);
		FieldDecoration fieldDecoration = FieldDecorationRegistry.getDefault()
				.getFieldDecoration(FieldDecorationRegistry.DEC_ERROR);
		controlDecoration.setImage(fieldDecoration.getImage());
		return controlDecoration;
	}

	private void bindValuesDefGen() {
		UserInfo userInfo = new UserInfo();
		// The DataBindingContext object will manage the databindings
		DataBindingContext bindingContext = new DataBindingContext();
		// First we bind the text field to the model
		// Here we define the UpdateValueStrategy
		UpdateValueStrategy update = new UpdateValueStrategy();
		update.setAfterConvertValidator(new EmptyStringValidator(errorMessage,
				controlDecorationTxtFullName));
		// Bind common name
		bindingContext.bindValue(
				SWTObservables.observeText(txtFullName, SWT.Modify),
				PojoObservables.observeValue(userInfo, "commanName"), update,
				null);

		// Bind organization
		bindingContext.bindValue(SWTObservables.observeText(txtCompany,
				SWT.Modify), PojoObservables.observeValue(userInfo,
				"organization"), new UpdateValueStrategy()
				.setAfterConvertValidator(new EmptyStringValidator(
						errorMessage, controlDecorationTxtCompany)), null);

		bindingContext.bindValue(SWTObservables.observeText(txtDepartment,
				SWT.Modify), PojoObservables.observeValue(userInfo,
				"organizationUnit"), new UpdateValueStrategy()
				.setAfterConvertValidator(new EmptyStringValidator(
						errorMessage, controlDecorationTxtDept)), null);

		bindingContext.bindValue(SWTObservables
				.observeText(txtCity, SWT.Modify), PojoObservables
				.observeValue(userInfo, "location"), new UpdateValueStrategy()
				.setAfterConvertValidator(new EmptyStringValidator(
						errorMessage, controlDecorationtxtCity)), null);

		bindingContext
				.bindValue(
						SWTObservables.observeText(txtState, SWT.Modify),
						PojoObservables.observeValue(userInfo, "state"),
						new UpdateValueStrategy()
								.setAfterConvertValidator(new EmptyStringValidator(
										errorMessage, controlDecorationTxtState)),
						null);

		bindingContext.bindValue(SWTObservables.observeText(txtCountry,
				SWT.Modify), PojoObservables.observeValue(userInfo, "country"),
				new UpdateValueStrategy()
						.setAfterConvertValidator(new EmptyStringValidator(
								errorMessage, controlDecorationTxtCountry)),
				null);

		bindingContext
				.bindValue(
						SWTObservables.observeText(txtPhone, SWT.Modify),
						PojoObservables.observeValue(userInfo, "phone"),
						new UpdateValueStrategy()
								.setAfterConvertValidator(new EmptyStringValidator(
										errorMessage, controlDecorationTxtPhone)),
						null);

		bindingContext
				.bindValue(
						SWTObservables.observeText(txtEmail, SWT.Modify),
						PojoObservables.observeValue(userInfo, "email"),
						new UpdateValueStrategy()
								.setAfterConvertValidator(new EmptyStringValidator(
										errorMessage, controlDecorationTxtEmail)),
						null);

		bindingContext.bindValue(SWTObservables.observeText(txtZipcode,
				SWT.Modify), PojoObservables.observeValue(userInfo, "zipcode"),
				new UpdateValueStrategy()
						.setAfterConvertValidator(new EmptyStringValidator(
								errorMessage, controlDecorationTxtZipcode)),
				null);
		// We listen to all errors via this binding
		uiElementDefGen = SWTObservables.observeText(lblDefGenMessage);
		// This one listenes to all changes
		bindingContext.bindValue(uiElementDefGen,
				new AggregateValidationStatus(bindingContext.getBindings(),
						AggregateValidationStatus.MAX_SEVERITY), null, null);

		uiElementDefGen.addChangeListener(new IChangeListener() {
			@Override
			public void handleChange(ChangeEvent event) {
				if (uiElementDefGen.getValue().equals("OK")) {
					lblDefGenMessage.setForeground(Display.getCurrent()
							.getSystemColor(SWT.COLOR_BLACK));

				} else {

					lblDefGenMessage.setForeground(Display.getCurrent()
							.getSystemColor(SWT.COLOR_RED));

				}
			}
		});
	}

	private void bindValuesAppPref() {

		// The DataBindingContext object will manage the databindings
		DataBindingContext bindingContext = new DataBindingContext();
		// First we bind the text field to the model
		// Here we define the UpdateValueStrategy
		UpdateValueStrategy update = new UpdateValueStrategy();
		update.setAfterConvertValidator(new EmptyStringValidator(errorMessage,
				controlDecorationTxtAppName));

		IObservableValue observeTxtAppName = SWTObservables.observeText(
				txtAppName, SWT.Modify);
		observeTxtAppName.addValueChangeListener(new IValueChangeListener() {
			@Override
			public void handleValueChange(ValueChangeEvent event) {
				appPrefApplyButtonPress = false;
			}
		});
		bindingContext.bindValue(observeTxtAppName,
				PojoObservables.observeValue(settings, "applicationName"),
				update, null);

		IObservableValue observeTxtOutputDir = SWTObservables.observeText(
				txtOutputDir, SWT.Modify);
		observeTxtOutputDir.addValueChangeListener(new IValueChangeListener() {
			@Override
			public void handleValueChange(ValueChangeEvent event) {
				appPrefApplyButtonPress = false;
			}
		});
		bindingContext.bindValue(observeTxtOutputDir, PojoObservables
				.observeValue(settings, "baseDir"), new UpdateValueStrategy()
				.setAfterConvertValidator(new EmptyStringValidator(
						errorMessage, controlDecorationTxtOutputDir)), null);

		IObservableValue observeTxtDevSuiteHome = SWTObservables.observeText(
				txtDevSuiteHome, SWT.Modify);
		observeTxtDevSuiteHome
				.addValueChangeListener(new IValueChangeListener() {
					@Override
					public void handleValueChange(ValueChangeEvent event) {
						appPrefApplyButtonPress = false;
					}
				});
		bindingContext
				.bindValue(
						observeTxtDevSuiteHome,
						PojoObservables.observeValue(settings,
								"oracleDeveloperSuite"),
						new UpdateValueStrategy()
								.setAfterConvertValidator(new EmptyStringValidator(
										errorMessage,
										controlDecorationTxtDevSuiteHome)),
						null);

		IObservableValue observeTxtRootFormsDir = SWTObservables.observeText(
				txtRootFormsDir, SWT.Modify);
		observeTxtRootFormsDir
				.addValueChangeListener(new IValueChangeListener() {
					@Override
					public void handleValueChange(ValueChangeEvent event) {
						appPrefApplyButtonPress = false;
					}
				});
		bindingContext
				.bindValue(
						observeTxtRootFormsDir,
						PojoObservables.observeValue(settings, "fmbRootDir"),
						new UpdateValueStrategy()
								.setAfterConvertValidator(new EmptyStringValidator(
										errorMessage,
										controlDecorationTxtRootFormsDir)),
						null);

		IObservableValue observeTxtSrcDataBaseString = SWTObservables
				.observeText(txtSrcDataBaseString, SWT.Modify);
		observeTxtSrcDataBaseString
				.addValueChangeListener(new IValueChangeListener() {
					@Override
					public void handleValueChange(ValueChangeEvent event) {
						appPrefApplyButtonPress = false;
					}
				});
		bindingContext.bindValue(observeTxtSrcDataBaseString, PojoObservables
				.observeValue(settings, "dbSrcUrl"), new UpdateValueStrategy()
				.setAfterConvertValidator(new EmptyStringValidator(
						errorMessage, controlDecorationTxtSrcDataBaseString)),
				null);

		IObservableValue observeTxtSrcDbDriver = SWTObservables.observeText(
				txtSrcDbDriver, SWT.Modify);
		observeTxtSrcDbDriver
				.addValueChangeListener(new IValueChangeListener() {
					@Override
					public void handleValueChange(ValueChangeEvent event) {
						appPrefApplyButtonPress = false;
					}
				});
		bindingContext
				.bindValue(
						observeTxtSrcDbDriver,
						PojoObservables.observeValue(settings, "dbSrcDriver"),
						new UpdateValueStrategy()
								.setAfterConvertValidator(new EmptyStringValidator(
										errorMessage,
										controlDecorationTxtSrcDbDriver)), null);

		IObservableValue observeTxtSrcDBUser = SWTObservables.observeText(
				txtSrcDBUser, SWT.Modify);
		observeTxtSrcDBUser.addValueChangeListener(new IValueChangeListener() {
			@Override
			public void handleValueChange(ValueChangeEvent event) {
				appPrefApplyButtonPress = false;
			}
		});
		bindingContext.bindValue(observeTxtSrcDBUser, PojoObservables
				.observeValue(settings, "dbSrcUser"), new UpdateValueStrategy()
				.setAfterConvertValidator(new EmptyStringValidator(
						errorMessage, controlDecorationTxtSrcDBUser)), null);

		IObservableValue observeTxtSrcDataBasePwd = SWTObservables.observeText(
				txtSrcDataBasePwd, SWT.Modify);
		observeTxtSrcDataBasePwd
				.addValueChangeListener(new IValueChangeListener() {
					@Override
					public void handleValueChange(ValueChangeEvent event) {
						appPrefApplyButtonPress = false;
					}
				});
		bindingContext.bindValue(observeTxtSrcDataBasePwd, PojoObservables
				.observeValue(settings, "dbSrcPassword"),
				new UpdateValueStrategy()
						.setAfterConvertValidator(new EmptyStringValidator(
								errorMessage,
								controlDecorationTxtSrcDataBasePwd)), null);

		IObservableValue observeTxtDestDataBaseString = SWTObservables
				.observeText(txtDestDataBaseString, SWT.Modify);
		observeTxtDestDataBaseString
				.addValueChangeListener(new IValueChangeListener() {
					@Override
					public void handleValueChange(ValueChangeEvent event) {
						appPrefApplyButtonPress = false;
					}
				});
		bindingContext.bindValue(observeTxtDestDataBaseString, PojoObservables
				.observeValue(settings, "dbDestUrl"), new UpdateValueStrategy()
				.setAfterConvertValidator(new EmptyStringValidator(
						errorMessage, controlDecorationTxtDestDataBaseString)),
				null);

		IObservableValue observeTxtDestDbDriver = SWTObservables.observeText(
				txtDestDbDriver, SWT.Modify);
		observeTxtDestDbDriver
				.addValueChangeListener(new IValueChangeListener() {
					@Override
					public void handleValueChange(ValueChangeEvent event) {
						appPrefApplyButtonPress = false;
					}
				});
		bindingContext
				.bindValue(
						observeTxtDestDbDriver,
						PojoObservables.observeValue(settings, "dbDestDriver"),
						new UpdateValueStrategy()
								.setAfterConvertValidator(new EmptyStringValidator(
										errorMessage,
										controlDecorationTxtDestDbDriver)),
						null);

		IObservableValue observeTxtDestDBUser = SWTObservables.observeText(
				txtDestDBUser, SWT.Modify);
		observeTxtDestDBUser.addValueChangeListener(new IValueChangeListener() {
			@Override
			public void handleValueChange(ValueChangeEvent event) {
				appPrefApplyButtonPress = false;
			}
		});
		bindingContext.bindValue(observeTxtDestDBUser, PojoObservables
				.observeValue(settings, "dbDestUser"),
				new UpdateValueStrategy()
						.setAfterConvertValidator(new EmptyStringValidator(
								errorMessage, controlDecorationTxtDestDBUser)),
				null);

		IObservableValue observeTxtDestDataBasePwd = SWTObservables
				.observeText(txtDestDataBasePwd, SWT.Modify);
		observeTxtDestDataBasePwd
				.addValueChangeListener(new IValueChangeListener() {
					@Override
					public void handleValueChange(ValueChangeEvent event) {
						appPrefApplyButtonPress = false;
					}
				});
		bindingContext.bindValue(SWTObservables.observeText(txtDestDataBasePwd,
				SWT.Modify), PojoObservables.observeValue(settings,
				"dbDestPassword"), new UpdateValueStrategy()
				.setAfterConvertValidator(new EmptyStringValidator(
						errorMessage, controlDecorationTxtDestDataBasePwd)),
				null);

		// We listen to all errors via this binding
		uiElementAppPrefs = SWTObservables.observeText(lblAppPrefMessage);
		// This one listenes to all changes
		bindingContext.bindValue(uiElementAppPrefs,
				new AggregateValidationStatus(bindingContext.getBindings(),
						AggregateValidationStatus.MAX_SEVERITY), null, null);

		uiElementAppPrefs.addChangeListener(new IChangeListener() {
			@Override
			public void handleChange(ChangeEvent event) {
				if (uiElementAppPrefs.getValue().equals("OK")) {
					lblAppPrefMessage.setForeground(Display.getCurrent()
							.getSystemColor(SWT.COLOR_BLACK));

				} else {
					lblDefGenMessage.setForeground(Display.getCurrent()
							.getSystemColor(SWT.COLOR_RED));
				}
			}
		});

	}

	private int getIndex() {

		String dbType = Settings.getSettings().getDatabaseType();
		//
		for (int i = 0; i < dataBasesTypes.length; i++) {
			String dbTypeByIndex = dataBasesTypes[i];
			if (dbTypeByIndex.equals(dbType)) {
				return i;
			}
		}
		return 0;
	}
}
