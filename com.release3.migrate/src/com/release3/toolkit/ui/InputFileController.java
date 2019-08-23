package com.release3.toolkit.ui;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.tools.ant.BuildException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.widgets.Shell;
import org.osgi.framework.Bundle;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.cinnabarsystems.clm.InvalidLicenseFormatException;
import com.converter.premigration.ProductInfo;
import com.converter.toolkit.ui.JAXBXMLReader;
import com.release3.javasql.R3JavaSqlGen;
import com.release3.javasql.R3JavaSqlGenFactory;

import com.release3.programunitgen.R3ProgramUnitGen;
import com.release3.programunitgen.R3ProgramUnitGenFactory;
import com.release3.r3coreplsqlgen.R3CorePlSqlGen;
import com.release3.r3coreplsqlgen.R3CorePlSqlGenFactory;
import com.release3.tf.core.Utils;
import com.release3.tf.core.form.InputFileData;
import com.release3.tf.core.form.Settings;
import com.release3.tf.core.form.XmlGen;
import com.release3.tf.javasql.JavaSqlModelGen;
import com.release3.tf.migrate.CustomizationChooserDialog;
import com.release3.tf.migrate.FormValidation;
import com.release3.tf.migration.model.MigrationForm;
import com.release3.tf.plsql.process.CustomizationAnalysis;
import com.release3.toolkit.LicenseCheck;
import com.release3.transform.constants.Environment;
import com.release3.transform.constants.PremigrationConstants;
import com.release3.transform.pref.PreferenceConstants;
import com.release3.transform.pref.PreferencePlugin;

/**
 * <p>
 * The InputFileController is responsible for the file upload logic as well as
 * the file deletion object. A users file uploads are only visible to them and
 * are deleted when the session is destroyed.
 * </p>
 * 
 * @since 1.7
 */
public class InputFileController {

	// public static final Log log =
	// LogFactory.getLog(InputFileController.class);

	// files associated with the current user
	private final List fileList = Collections.synchronizedList(new ArrayList());
	// latest file uploaded by client
	private InputFileData currentFile;
	// file upload completed percent (Progress)
	private int fileProgress;

	private String uploadDirectory = System.getProperty("java.io.tmpdir");

	private String fileDirectory = "";
	private String projectParentDir = "";
	private boolean autoUpload = true;

	private String FMBFileName = "";
	private PrintStream consoleStream = null;
	private PrintStream fileStream = null;
	private BufferedOutputStream bufferedStream;

	public InputFileController() {
		consoleStream = new PrintStream(System.out, true);

		// fileStream = new PrintStream(bufferedStream);

	}

	/**
	 * <p>
	 * Action event method which is triggered when a user clicks on the upload
	 * file button. Uploaded files are added to a list so that user have the
	 * option to delete them programatically. Any errors that occurs during the
	 * file uploaded are added the messages output.
	 * </p>
	 * 
	 * @param event
	 *            jsf action event.
	 */
	public void uploadFile(File file) {

		currentFile = new InputFileData(file.getPath());

	}

	/**
	 * <p>
	 * This method is bound to the inputFile component and is executed multiple
	 * times during the file upload process. Every call allows the user to finds
	 * out what percentage of the file has been uploaded. This progress
	 * information can then be used with a progressBar component for user
	 * feedback on the file upload progress.
	 * </p>
	 * 
	 * @param event
	 *            holds a InputFile object in its source which can be probed for
	 *            the file upload percentage complete.
	 */
	// public void fileUploadProgress(EventObject event) {
	// InputFile ifile = (InputFile)event.getSource();
	// fileProgress = ifile.getFileInfo().getPercent();
	// }
	/**
	 * <p>
	 * Allows a user to remove a file from a list of uploaded files. This
	 * methods assumes that a request param "fileName" has been set to a valid
	 * file name that the user wishes to remove or delete
	 * </p>
	 * 
	 * @param event
	 *            jsf action event
	 * @throws InvalidLicenseFormatException
	 */
	// public void removeUploadedFile(ActionEvent event) {
	// // Get the inventory item ID from the context.
	// FacesContext context = FacesContext.getCurrentInstance();
	// Map map = context.getExternalContext().getRequestParameterMap();
	// String fileName = (String)map.get("fileNameDelete");
	//
	//
	// new File(this.uploadDirectory + "\\" + fileName).delete();
	// ValueBinding binding =
	// app.createValueBinding("#{BindingFactory.forms}");
	// DataControl dc = (DataControl)binding.getValue(context);
	// dc.refresh();
	// }
	// public void refreshUploadedFile(ActionEvent event) {
	// // Get the inventory item ID from the context.
	// FacesContext context = FacesContext.getCurrentInstance();
	// Map map = context.getExternalContext().getRequestParameterMap();
	// String fileName = (String)map.get("refreshfileName");
	//
	//
	// ValueBinding binding =
	// app.createValueBinding("#{BindingFactory.forms.iterator}");
	// List fileList = (List)binding.getValue(context);
	//
	//
	// for (int i = 0; i < fileList.size(); i++) {
	// InputFileData inputFileData = (InputFileData)fileList.get(i);
	// if (inputFileData.getFileName().equals(fileName)) {
	// int j = inputFileData.getRefresh();
	// inputFileData.setRefresh(++j);
	// }
	// }
	// }
	/**
	 * Start migration process
	 */
	public IStatus migrate(List<MigrationForm> formList,
			IProgressMonitor monitor, final Shell parentShell) {

		String fileName = "";
		InputFileData inputFileData;
		monitor.beginTask("Starting Migration...", formList.size() * 14);
		// LicenseCheck licCheck = new LicenseCheck();
		for (int i = 0; i < formList.size(); i++) {
			final MigrationForm migrationForm = formList.get(i);
			setCustomizationFile(migrationForm);
			inputFileData = new InputFileData(migrationForm.getForm()
					.getAbsolutePath());
			fileName = inputFileData.getName();
			fileName = fileName.substring(0, fileName.length() - 4);
			Settings.getSettings().setFmbFile(fileName);
			Settings.getSettings().save();
			Settings settings = Settings.getSettings();
			try {
				File logFileDir = new File(Platform.getLocation().toString()
						+ File.separator + "logs");
				if (!logFileDir.exists()) {
					logFileDir.mkdirs();
				}
				File logFile = new File(logFileDir.getPath() + File.separator
						+ fileName + ".migration.log");
				if (logFile.exists()) {
					Date lastModified = new Date(logFile.lastModified());
					// SimpleDateFormat dateFormat = new SimpleDateFormat(
					// "ddMMyyyyHHmmss");
					// try {
					// String dateStr = dateFormat.format(lastModified);
					// lastModified = dateFormat.parse(dateStr);
					// Date currentDate = dateFormat.parse(dateFormat
					// .format(new Date()));
					// if (lastModified.before(currentDate)) {

					File oldFile = new File(logFileDir.getPath()
							+ File.separator + lastModified.getTime() + "_"
							+ fileName + ".migration.log");
					logFile.renameTo(oldFile);

					// }
					// } catch (ParseException e) {
					// // TODO Auto-generated catch block
					// e.printStackTrace();
					// }

				}
				BufferedOutputStream bufferedStream = new BufferedOutputStream(
						new FileOutputStream(logFile, true));
				fileStream = new PrintStream(bufferedStream, true);

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			fileDirectory = inputFileData.getParent();
			monitor.worked(1);
			/* License check */
			monitor.setTaskName("Verifying license for form " + fileName
					+ "...");
			// String xmlFilePath = settings.getBaseDir() + File.separator
			// + "ViewController" + File.separator + "src"
			// + File.separator + settings.getApplicationName()
			// + File.separator + settings.getFmbFile() + File.separator
			// + settings.getFmbFile() + ".xml";
			String xmlFilePath = migrationForm.getForm().getAbsolutePath()
					.replace(".fmb", "_fmb")
					+ ".xml";
			if (xmlFilePath.indexOf(".FMB") > 0) {
				xmlFilePath = xmlFilePath.replace(".FMB", "_FMB");
			}
			/* xml generation */
			XmlGen.makeXml(formList.get(i).getForm().getAbsolutePath());
			try {
				// licCheck.check(new File(Settings.getSettings().getBaseDir())
				// .getParentFile().getPath(), "TF.3.0.0.", inputFileData
				// .getName(), xmlFilePath, "NA");
				// ProductInfo productInfo = ProductInfo.getProductInfo();
				// licCheck.check(Platform.getLocation().toFile().getPath()
				// + File.separator + "licenses", productInfo,
				// xmlFilePath, "NA");
			} catch (Exception ex) {

				// License failed...
				// migrationForm.setStatus("Invalid License");
				migrationForm.setStatus(ex.getMessage());
				IStatus iStatus = new Status(IStatus.ERROR,
						"com.release3.migrate", IStatus.ERROR, ex.getMessage(),
						new InvalidLicenseFormatException());
				return iStatus;

				// throw new OperationCanceledException("Invalid License.");
			}
			/**
			 * Other validation code goes here...
			 */
			monitor.worked(2);
			monitor.setTaskName("Validating form " + fileName + "...");
			migrationForm.setFmbXmlPath(xmlFilePath);
			FormValidation formValidation = new FormValidation(migrationForm,
					monitor);
			int result = formValidation.start();
			if (result > 1) {
				String errorMsg = null;
				switch (result) {

				case 1:
					errorMsg = "PrimaryKey validation failed.";

					break;
				case 2:
					errorMsg = "JoinCondition validation failed.";
					break;
				default:
					errorMsg = "Form Validation failed.";
				}

				migrationForm.setStatus(errorMsg);

				IStatus iStatus = new Status(IStatus.ERROR,
						"com.release3.migrate", IStatus.ERROR, errorMsg,
						new Exception());
				return iStatus;
			}

			new File(xmlFilePath).deleteOnExit();
			monitor.worked(2);

			monitor.setTaskName("Migrating form " + fileName + "...");

			/**
			 * Migrating javaSql
			 */
			boolean useSqlways = PreferencePlugin
					.getDefault()
					.getPreferenceStore()
					.getBoolean(
							PreferenceConstants.APPLICATION_SQLWAYS_USE_ISPIRER_SQLWAYS);
			if (!useSqlways) {
				try {
					JavaSqlModelGen javaGen = new JavaSqlModelGen();
					JAXBXMLReader reader = new JAXBXMLReader();
					R3JavaSqlGen javaSqlGen = (R3JavaSqlGen) reader.init(
							migrationForm.getR3NonCRUDXmlFile(),
							R3JavaSqlGenFactory.class);

					R3CorePlSqlGen r3CorePlSqlGen = (R3CorePlSqlGen) reader
							.init(migrationForm.getR3CRUDXmlFile(),
									R3CorePlSqlGenFactory.class);
					// javaGen.generateJavaClass(javaSqlGen, r3CorePlSqlGen,
					// migrationForm);

					R3ProgramUnitGen r3ProgramUnitGen = (R3ProgramUnitGen) reader
							.init(migrationForm.getR3ProgramUnitAfterXmlFile(),
									R3ProgramUnitGenFactory.class);
					javaGen.generateJavaClass(javaSqlGen, r3CorePlSqlGen,
							r3ProgramUnitGen, migrationForm);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
//			projectParentDir = new File(settings.getBaseDir()).getParent();
			projectParentDir = settings.getTransformHome();

			try {
				// String executeSentece = projectParentDir + "\\"
				// + Environment.TOOL_EXEC + " -logfile " + fileDirectory
				// + File.separator + "migration.log" + " -b "
				// + projectParentDir + " -p " + fileDirectory + " -debug";
				String executeSentece = projectParentDir + File.separator
						+ Environment.TOOL_EXEC + " -b " + projectParentDir
						+ " -p " + fileDirectory + " -debug";

				Process p = Runtime.getRuntime().exec(
						"cmd.exe /C " + executeSentece);
				BufferedReader in = new BufferedReader(new InputStreamReader(
						p.getInputStream()));
				monitor.worked(2);

				String line = in.readLine();
				while (line != null) {
					if (!(line.indexOf("Setting project property:") > -1 || line
							.indexOf("Overriding previous definition of property") > -1)) {
						consoleStream.println(line);
						fileStream.println(line);
					}
					line = in.readLine();

					if (monitor.isCanceled()) {
						p.destroy();
						throw new OperationCanceledException(
								"Operation cancelled by user.");
					}

				}
				in.close();

				monitor.worked(6);
				/** removed */
				monitor.setTaskName("Generating workspace " + fileName + "...");
				copyR3BuiltinsPropertyMapFile();
				monitor.worked(1);
				// create a ZipOutputStream to zip the data to
				ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(
						fileDirectory + File.separator + "outfile.zip"));
				// assuming that there is a directory named inFolder (If there
				// isn't create one) in the same directory as the one the code
				// runs from,
				// call the zipDir method
				zipDir(settings.getBaseDir(), zos,
						new File(settings.getBaseDir()).getPath());
				// close the stream
				zos.close();
				monitor.worked(1);

			} catch (Exception e) {
				IStatus iStatus = new Status(IStatus.ERROR,
						"com.release3.migrate", IStatus.ERROR,
						"Error occur during migration: " + e.getMessage(), e);
				return iStatus;

			} finally {
				// if (bufferedStream != null) {
				// try {
				//
				// // flush the BufferedOutputStream
				// bufferedStream.flush();
				//
				// // close the BufferedOutputStream
				// // bufferedStream.close();
				//
				// } catch (Exception e) {
				// }
				// }
				if (consoleStream != null) {
					try {
						consoleStream.flush();
						// consoleStream.close();
					} catch (Exception e) {
					}
				}
				if (fileStream != null) {
					try {
						fileStream.flush();
						// fileStream.close();
					} catch (Exception e) {
					}
				}
			}

		}
		return new Status(Status.OK, "com.release3.migrate", "Job finished");
	}

	// public Resource getDownload() {
	// File f = new File(this.getLinkText());
	//
	// return new FileResource(f);
	// }

	public InputFileData getCurrentFile() {
		return currentFile;
	}

	public int getFileProgress() {
		return fileProgress;
	}

	public List getFileList() {
		return fileList;
	}

	// public String getLinkText() {
	// return linkText;
	// }
	//
	// public void setLinkText(String link) {
	// linkText = link;
	// }

	public String getUploadDirectory() {
		return uploadDirectory;
	}

	public String getFMBFileName() {
		return FMBFileName;
	}

	public void setFMBFileName(String FMBName) {
		FMBFileName = FMBName;
	}

	public void zipDir(String dir2zip, ZipOutputStream zos, String fileDirectory) {
		try {
			// create a new File object based on the directory we have to zip
			File zipDir = new File(dir2zip);
			// get a listing of the directory content
			String[] dirList = zipDir.list();
			byte[] readBuffer = new byte[2156];
			int bytesIn = 0;
			// loop through dirList, and zip the files
			for (int i = 0; i < dirList.length; i++) {
				File f = new File(zipDir, dirList[i]);
				if (f.isDirectory()) {
					// if the File object is a directory, call this
					// function again to add its content recursively
					String filePath = f.getPath();
					zipDir(filePath, zos, fileDirectory);
					// loop again
					continue;
				}
				// if we reached here, the File object f was not a directory
				// create a FileInputStream on top of f
				FileInputStream fis = new FileInputStream(f);
				// create a new zip entry

				String s = f.getPath().substring(fileDirectory.length() + 1);
				ZipEntry anEntry = new ZipEntry(s);
				// place the zip entry in the ZipOutputStream object
				zos.putNextEntry(anEntry);
				// now write the content of the file to the ZipOutputStream
				while ((bytesIn = fis.read(readBuffer)) != -1) {
					zos.write(readBuffer, 0, bytesIn);
				}
				// close the Stream
				fis.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Document loadDocument(String url) throws BuildException {

		DocumentBuilderFactory domFactory = DocumentBuilderFactory
				.newInstance();
		domFactory.setNamespaceAware(true);

		DocumentBuilder builder = null;
		try {
			builder = domFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new BuildException(e);
		}

		if (builder == null) {
			return null;
		}

		Document doc = null;
		try {
			doc = builder.parse(url);
		} catch (SAXException e) {
			throw new BuildException("Unable to load document", e);
		} catch (IOException e) {
			throw new BuildException("Unable to load document", e);
		}

		return doc;
	}

	public boolean isAutoUpload() {
		return autoUpload;
	}

	public void setAutoUpload(boolean autoUpload) {
		this.autoUpload = autoUpload;
	}

	public void setCustomizationFile(MigrationForm migrationForm) {
		String formName = migrationForm.getFormName().replace(".fmb", "")
				.replace(".FMB", "");
		String autoGeneratedCustomizationPath = migrationForm
				.getFormWorkspaceLocation()
				+ File.separator
				+ formName
				+ "_R3PreCustomization.xml";

		File autoGeneratedCustomizationFile = new File(
				autoGeneratedCustomizationPath);

		String defaultCustomizationPath = migrationForm
				.getFormWorkspaceLocation()
				+ File.separator
				+ formName
				+ "_customization.xml";
		File defaultCustomizationFile = new File(defaultCustomizationPath);

		String sampleCustomizationPath = Settings.getSettings().getFmbRootDir()
				+ File.separator + formName + "_customization.xml";
		File sampleCustomizationFile = new File(sampleCustomizationPath);

		String projectCustomizationPath = Settings.getSettings().getBaseDir()
				+ File.separator + "ViewController" + File.separator + "src"
				+ File.separator + Settings.getSettings().getApplicationName()
				+ File.separator + formName + File.separator + formName
				+ "_customization.xml";

		File projectCustomizationFile = new File(projectCustomizationPath);
		if (!defaultCustomizationFile.exists()) {
			if (sampleCustomizationFile.exists()) {
				try {
					Utils.copyFile(sampleCustomizationFile,
							defaultCustomizationFile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (projectCustomizationFile.exists()) {
				try {
					Utils.copyFile(projectCustomizationFile,
							defaultCustomizationFile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				}
			}
		}
		/*
		 * By default defaultCustomization is selected
		 */
		Boolean lastSelectedOption = CustomizationChooserDialog.preferences
				.getBoolean(
						CustomizationChooserDialog.REMEMBER_LAST_SELECTED_OPTION,
						true);

		if (lastSelectedOption) {
			System.out.println("Last Selection: Default Customization");
			if (defaultCustomizationFile.exists()) {
				try {
					if (sampleCustomizationFile.exists()) {
						String sampleCustomizationBackupPath = migrationForm
								.getFormWorkspaceLocation()
								+ File.separator
								+ formName
								+ "_customization"
								+ sampleCustomizationFile.lastModified()
								+ ".xml";
						File sampleCustomizationBackupFile = new File(
								sampleCustomizationBackupPath);
						Utils.copyFile(sampleCustomizationFile,
								sampleCustomizationBackupFile);
					}
					Utils.copyFile(sampleCustomizationFile,
							defaultCustomizationFile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			System.out.println("Last Selection: AutoGenerated Customization");
			if (autoGeneratedCustomizationFile.exists()) {
				// Analysis autogenerated customization file, than save to
				// sample forms folder as _customization.xml
				CustomizationAnalysis analysis = new CustomizationAnalysis(
						migrationForm, autoGeneratedCustomizationFile,
						sampleCustomizationPath);
				analysis.analysis();
				// Utils.copyFile(autoGeneratedCustomizationFile,
				// sampleCustomizationFile);

			}
		}
	}

	private void copyR3BuiltinsPropertyMapFile() {
		Bundle bundle = Platform
				.getBundle(com.release3.tf.jaxb.Activator.PLUGIN_ID);
		URL fileURL = bundle.getEntry("builtins/R3BuiltinsPropertyMap.xml");
		File file = null;
		try {
			file = new File(FileLocator.resolve(fileURL).toURI());
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		// InputStream templateStream = getClass().getResourceAsStream(
		// templateFileName);
		// try {
		// Utils.copy(templateStream, new FileOutputStream(temporaryFile));
		// } catch (FileNotFoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	private void newFileStream(File logFile) throws Exception {
		if (logFile.exists()) {
			Date lastModified = new Date(logFile.lastModified());
			File oldFile = new File(logFile.getParent() + File.separator
					+ lastModified.getTime() + "_" + logFile.getName()
					+ ".migration.log");
			logFile.renameTo(oldFile);

		}
		bufferedStream = new BufferedOutputStream(new FileOutputStream(logFile,
				true));
		fileStream = new PrintStream(bufferedStream, true);
	}
}
