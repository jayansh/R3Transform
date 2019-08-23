package com.release3.tf.analysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SubProgressMonitor;

import com.converter.toolkit.ui.FormsObject;
import com.converter.toolkit.ui.JAXBFMBReader;
import com.oracle.xmlns.forms.Block;
import com.oracle.xmlns.forms.Canvas;
import com.oracle.xmlns.forms.FormModule;
import com.oracle.xmlns.forms.Item;
import com.oracle.xmlns.forms.LOV;
import com.oracle.xmlns.forms.Module;
import com.oracle.xmlns.forms.RecordGroup;
import com.oracle.xmlns.forms.Relation;
import com.oracle.xmlns.forms.Trigger;
import com.oracle.xmlns.forms.Window;
import com.release3.dbconnect.DBCommand;
import com.release3.tf.core.form.Settings;
import com.release3.tf.migration.model.MigrationForm;

import com.release3.transform.util.SpecialCharSearcher;

public class FormAnalysis {
	private final static Logger LOGGER = Logger.getLogger(FormAnalysis.class
			.getName());
	FileHandler handler;
	private MigrationForm form;
	private static Connection conn = null;
	private StringBuffer analysisResultStr;
	private AnalysisResult result;
	private JAXBFMBReader jaxbFMBReader;

	private IProgressMonitor monitor;

	/**
	 * This contains only PK column corresponding to respective block.
	 */
	private HashMap<String, List<String>> blockColumnMap = new HashMap<String, List<String>>();
	private HashMap<String, Block> blockMap = new HashMap<String, Block>();
	private HashMap<String, String> blockTableMap = new HashMap<String, String>();

	private HashMap<String, LOV> lovRGMap = new HashMap<String, LOV>();
	private HashMap<String, RecordGroup> rgMap = new HashMap<String, RecordGroup>();

	private List<String> canvasList = new ArrayList<String>();
	private List<String> windowList = new ArrayList<String>();

	public FormAnalysis(MigrationForm form, IProgressMonitor monitor) {

		this.form = form;
		this.monitor = monitor;
		jaxbFMBReader = new JAXBFMBReader();
		analysisResultStr = new StringBuffer();
		result = new AnalysisResult();

		File logFileDir = new File(Platform.getLocation().toString()
				+ File.separator + "logs");
		if (!logFileDir.exists()) {
			logFileDir.mkdirs();
		}
		File logFile = new File(logFileDir.getPath() + File.separator
				+ form.getFormName() + "_analysis.log");
		try {
			handler = new FileHandler(logFile.getPath());
			handler.setFormatter(new SimpleFormatter());
			LOGGER.addHandler(handler);

		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 0 = Validation Success. 1 = PrimaryKey validation failed. 2 =
	 * JoinCondition validation failed.
	 * 
	 * @return
	 */
	public void start() {
		analysisResultStr.append("Form: " + form.getFormName() + " analysed\n");
		result.setFormName(form.getFormName());
		LOGGER.log(Level.INFO, "Form: " + form.getFormName());
		monitor.setTaskName("Generating xml files...");
		getDependencies(form.getForm().getPath(), new SubProgressMonitor(
				monitor, 2));
		Module module = jaxbFMBReader.init(form.getFmbXmlPath());

		FormModule formModule = module.getFormModule();
		if (!form.getFormName().equalsIgnoreCase(formModule.getName())) {
			LOGGER.log(Level.WARNING, "Form name(" + form.getFormName()
					+ ") is not equals to Module name(" + formModule.getName()
					+ ")!");
			analysisResultStr.append("Form Name(" + form.getFormName()
					+ ") is not equals to Module name(" + formModule.getName()
					+ ")!");
			AnalysisResultDetail analysisResultDetail = new AnalysisResultDetail();
			analysisResultDetail.setElement("Module Name: "
					+ formModule.getName());
			analysisResultDetail.setIssue("Form name(" + form.getFormName()
					+ ") is not equals to Module name");
			analysisResultDetail
					.setSuggestion("Form name and Module name should be same. Please modify your form before migration.");
			result.getDetailList().add(analysisResultDetail);

		}
		List<FormsObject> children = formModule.getChildren();
		for (FormsObject formsObject : children) {
			if (formsObject instanceof LOV) {
				LOV lov = (LOV) formsObject;
				lovRGMap.put(lov.getRecordGroupName(), lov);
			} else

			if (formsObject instanceof Block) {
				Block block = (Block) formsObject;
				blockValidation(block);
				List<FormsObject> blockObjects = block.getChildren();
				for (FormsObject blockObject : blockObjects) {
					if (blockObject instanceof Item) {
						Item item = (Item) blockObject;
						specialCharAnalysis(item);
						List<FormsObject> itemObjects = item.getChildren();
						for (FormsObject itemObject : itemObjects) {
							if (itemObject instanceof Trigger) {
								result.setTotalItemBasedTriggers(result
										.getTotalItemBasedTriggers() + 1);
							}
						}
					}

					if (blockObject instanceof Trigger) {
						result.setTotalBlockBasedTriggers(result
								.getTotalBlockBasedTriggers() + 1);
					}
				}

				if (!validateJoinCondition((Block) formsObject)) {
					LOGGER.log(Level.WARNING,
							"JoinCondition validation failed. Please modify your block for join condition.");
					//
					// analysisResultStr
					// .append("JoinCondition validation failed. Please modify your block for join condition.\n");

				}
			} else if (formsObject instanceof RecordGroup) {
				RecordGroup rg = (RecordGroup) formsObject;
				rgMap.put(rg.getName(), rg);
			} else if (formsObject instanceof Canvas) {
				canvasList.add(((Canvas) formsObject).getName());
				result.setTotalCanvases(result.getTotalCanvases() + 1);

			} else if (formsObject instanceof Window) {
				windowList.add(((Window) formsObject).getName());
				result.setTotalWindows(result.getTotalWindows() + 1);

			} else if (formsObject instanceof Trigger) {
				result.setTotalFormBasedTriggers(result
						.getTotalFormBasedTriggers() + 1);
			}
		}
		for (String window : windowList) {
			for (String canvas : canvasList) {
				if (window.equalsIgnoreCase(canvas)) {
					LOGGER.log(Level.WARNING,
							"Window name(" +window
									+ ") is equals to canvas name("
									+ canvas + ")!");
					analysisResultStr.append("Window name(" +window
							+ ") is equals to canvas name("
							+ canvas + ")!");
					AnalysisResultDetail analysisResultDetail = new AnalysisResultDetail();
					analysisResultDetail.setElement("Window Name: "
							+ window);
					analysisResultDetail.setIssue("Window name("
							+ window
							+ ") is equals to Canvas name");
					analysisResultDetail
							.setSuggestion("Window name and Canvas name should not be same. Please rename window or canvas name before migration.");
					result.getDetailList().add(analysisResultDetail);

				}
			}
			for (String blockName : blockMap.keySet()) {
				if (window.equalsIgnoreCase(blockName)) {
					LOGGER.log(Level.WARNING,
							"Window name(" +window
									+ ") is equals to Block name("
									+ blockName + ")!");
					analysisResultStr.append("Window name(" +window
							+ ") is equals to Block name("
							+ blockName + ")!");
					AnalysisResultDetail analysisResultDetail = new AnalysisResultDetail();
					analysisResultDetail.setElement("Window Name: "
							+ window);
					analysisResultDetail.setIssue("Window name("
							+ window
							+ ") is equals to Block name");
					analysisResultDetail
							.setSuggestion("Window name and Block name should not be same. Please rename window or block name before migration.");
					result.getDetailList().add(analysisResultDetail);

				}
			}
		}
		for (String canvas : canvasList) {
			for (String blockName : blockMap.keySet()) {
				if (canvas.equalsIgnoreCase(blockName)) {
					LOGGER.log(Level.WARNING,
							"Canvas name(" +canvas
									+ ") is equals to Block name("
									+ blockName + ")!");
					analysisResultStr.append("Canvas name(" +canvas
							+ ") is equals to Block name("
							+ blockName + ")!");
					AnalysisResultDetail analysisResultDetail = new AnalysisResultDetail();
					analysisResultDetail.setElement("Canvas Name: "
							+ canvas);
					analysisResultDetail.setIssue("Canvas name("
							+ canvas
							+ ") is equals to Block name");
					analysisResultDetail
							.setSuggestion("Canvas name and Block name should not be same. Please rename canvas or block name before migration.");
					result.getDetailList().add(analysisResultDetail);

				}
			}
		}

		result.setTotalBlocks(blockMap.size());
		LOGGER.log(Level.INFO, "Total Blocks: " + blockMap.size());
		Set<String> blockSet = blockMap.keySet();
		for (String blockName : blockSet) {
			itemValidation(blockMap.get(blockName));
		}

		lovAnalysis();
		if (!primaryKeyValidation()) {
			LOGGER.log(Level.WARNING,
					"Primary key validation failed. Please modify your form before migration.");
			// analysisResultStr
			// .append("Primary key validation failed. Please modify your form before migration.\n");

		}
		calculateLibsAndSize();
		calculateComplexity();

	}

	private void calculateLibsAndSize() {
		File parentFile = form.getForm().getParentFile();
		List<String> dependentForms = result.getFormList();
		for (String string : dependentForms) {
			File dependentForm = new File(parentFile + File.separator + string);
			if (!dependentForm.exists()) {
				result.getMissingLibs().add(string);
			}
			long size = result.getFormSize() + dependentForm.length();
			result.setFormSize(size);
		}
		List<String> dependentOLBs = result.getOlbList();
		for (String string : dependentOLBs) {
			File dependentOLB = new File(parentFile + File.separator + string);
			if (!dependentOLB.exists()) {
				result.getMissingLibs().add(string);
			}
			long size = result.getOlbSize() + dependentOLB.length();
			result.setOlbSize(size);
		}

	}

	public String getAnalysisResultString() {
		return analysisResultStr.toString();
	}

	public AnalysisResult getAnalysisResult() {
		return result;
	}

	public void blockValidation(Block block) {
		String tableName = block.getQueryDataSourceName();
		if (tableName == null) {
			tableName = block.getName();
		}

		LOGGER.log(Level.INFO, "Block Name: " + block.getName() + " Table: "
				+ tableName);
		analysisResultStr.append("Block Name: " + block.getName() + " Table: "
				+ tableName + "\n");
		blockTableMap.put(block.getName(), tableName);
		blockMap.put(block.getName(), block);
	}

	public void itemValidation(Block block) {
		System.out.println(block);
		System.out.println(block.getQueryDataSourceType());
		if (block.getQueryDataSourceType() == null) {
			block.setQueryDataSourceType("Table");
		}
		if (block.getQueryDataSourceType() == "Table"
				|| block.getQueryDataSourceType().equals("Table")) {
			List<String> pkColumns = new ArrayList<String>();
			List<FormsObject> objectList = block.getChildren();
			for (FormsObject formsObject : objectList) {
				if (formsObject instanceof Item) {
					Item item = (Item) formsObject;
					Boolean primaryKey = item.isPrimaryKey();
					String copyValueFromItem = item.getCopyValueFromItem();
					String columnName = item.getColumnName();
					if ((primaryKey != null && primaryKey)
							|| copyValueFromItem != null) {
						if (columnName == null) {
							// if (copyValueFromItem == null) {
							columnName = item.getName();
							// } else {
							//
							// String blockName = copyValueFromItem.substring(
							// 0, copyValueFromItem.indexOf('.') - 1);
							// columnName = copyValueFromItem
							// .substring(copyValueFromItem
							// .indexOf('.') + 1);
							// if (blockTableMap.get(blockName) != null) {
							// columnName = blockTableMap.get(blockName)
							// + "." + columnName;
							// }
							//
							// }
						}
						pkColumns.add(columnName);
					}

				}
			}

			blockColumnMap.put(block.getName(), pkColumns);
		}
	}

	public boolean checkColumns(String tableName, String owner,
			List<String> itemColumns) {
		String query = "SELECT UNIQUE COLUMN_NAME, POSITION  FROM ALL_CONS_COLUMNS A JOIN ALL_CONSTRAINTS C "
				+ " ON A.CONSTRAINT_NAME = C.CONSTRAINT_NAME WHERE C.TABLE_NAME = '"
				+ tableName.toUpperCase()
				+ "' AND C.CONSTRAINT_TYPE = 'P' AND C.OWNER='"
				+ owner.toUpperCase() + "' ORDER BY POSITION";
		try {
			Statement stmt = DBCommand.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String column = rs.getString(1);
				if (!itemColumns.contains(column)) {
					LOGGER.log(
							Level.WARNING,
							"Block \""
									+ currentBlock
									+ "\" doesn't contains item with primary key column "
									+ column);
					analysisResultStr
							.append("Block \""
									+ currentBlock
									+ "\" doesn't contains item with primary key column "
									+ column + "\n");
					AnalysisResultDetail detail = new AnalysisResultDetail();

					detail.setElement("Block: " + currentBlock);
					detail.setIssue("Doesn't contains item with primary key column "
							+ column);
					detail.setSuggestion("Primary key validation failed. Please modify your form before migration.");
					result.getDetailList().add(detail);
					return false;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		return true;
	}

	private String currentBlock;

	/**
	 * 
	 * @return true if validation succeeded false if validation failed
	 */
	public boolean primaryKeyValidation() {
		Set<String> blockSet = blockColumnMap.keySet();

		for (String block : blockSet) {
			currentBlock = block;
			LOGGER.log(Level.INFO, "Block \"" + currentBlock
					+ "\" validation  started");
			String tableName = blockTableMap.get(block);
			String owner = Settings.getSettings().getDbSrcUser();
			int dotIndex = tableName.indexOf('.');
			if (dotIndex > -1) {
				owner = tableName.substring(0, dotIndex - 1);
				tableName = tableName.substring(dotIndex);
			}

			boolean status = checkColumns(tableName, owner,
					blockColumnMap.get(block));
			if (!status) {
				return status;
			}
		}
		return true;
	}

	@Deprecated
	public static Connection getConnection() {

		if (conn == null) {
			String driverClass = Settings.getSettings().getDbSrcDriver();
			String userName = Settings.getSettings().getDbSrcUser();
			String userPassword = Settings.getSettings().getDbSrcPassword();
			String connectionURL = Settings.getSettings().getDbSrcUrl();

			try {

				Class.forName(driverClass).newInstance();
				conn = DriverManager.getConnection(connectionURL, userName,
						userPassword);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return conn;
	}

	public boolean validateJoinCondition(Block block) {

		List<FormsObject> objectList = block.getChildren();
		for (FormsObject formsObject : objectList) {
			if (formsObject instanceof Relation) {
				Relation relation = (Relation) formsObject;
				if (relation != null) {
					String relationType = relation.getRelationType();
					String joinCondition = relation.getJoinCondition()
							.toUpperCase();
					String detailBlock = relation.getDetailBlock();

					if ((relationType != null) || joinCondition != null) {
						LOGGER.log(Level.INFO,
								"Master Block \"" + block.getName()
										+ "\" Detail Block \"" + detailBlock
										+ "\" validation started.");
						analysisResultStr.append("Master Block :"
								+ block.getName() + "\t Detail Block :"
								+ detailBlock + "\n");
						joinCondition = joinCondition.replace("&#10;", " ");
						String token[] = joinCondition.split(" AND ");
						for (String condition : token) {
							// System.out.println(condition);
							StringTokenizer st = new StringTokenizer(condition,
									"=");
							while (st.hasMoreTokens()) {
								String column = st.nextToken();
								if (column.indexOf('.') == -1) {
									LOGGER.log(Level.SEVERE, "Condition \""
											+ condition + "\" Column \""
											+ column
											+ "\" doesn't contain block name.");
									// analysisResultStr
									// .append("Condition \""
									// + condition
									// + "\" Column \""
									// + column
									// + "\" doesn't contain block name.\n");
									AnalysisResultDetail detail = new AnalysisResultDetail();
									detail.setElement("Master Block :"
											+ block.getName()
											+ ", Detail Block :" + detailBlock);
									detail.setIssue("Condition \"" + condition
											+ "\" Column \"" + column
											+ "\" doesn't contain block name.");
									detail.setSuggestion("JoinCondition validation failed. Please modify your block for join condition.\n");
									return false;
								}
								// System.out.println(column);
							}
						}

					}
				}
			}
		}
		return true;
	}

	public void lovAnalysis() {
		Collection<LOV> lovList = lovRGMap.values();
		Collection<RecordGroup> rgList = rgMap.values();
		for (LOV lov : lovList) {
			for (RecordGroup recordGroup : rgList) {
				if (lov.getRecordGroupName().equals(recordGroup.getName())
						|| lov.getRecordGroupName() == recordGroup.getName()) {
					String query = recordGroup.getRecordGroupQuery();

					LOGGER.log(Level.INFO,
							"LOV name: " + lov.getName() + "\n\tTable Name: "
									+ tableCalculation(lov.getName(), query));
					// analysisResultStr.append("LOV name: " + lov.getName()
					// + "\n\tTable Name: " + tableCalculation(query)
					// + "\n");

				}
			}
		}
		result.setTotalLOVs(lovList.size());
	}

	public void specialCharAnalysis(Item item) {
		boolean retVal = SpecialCharSearcher.findSpecialChar(item.getHint());
		if (retVal) {
			LOGGER.log(
					Level.INFO,
					"Item: " + item.getName()
							+ "\t contains special character at hint: "
							+ item.getHint());
			// analysisResultStr.append("Item: " + item.getName()
			// + "\t contains special character at hint: " + item.getHint()
			// + "\n");
			AnalysisResultDetail detail = new AnalysisResultDetail();
			detail.setElement("Item: " + item.getName());
			detail.setIssue("Contains special character at hint: "
					+ item.getHint());
			detail.setSuggestion("Remove special character at hint: "
					+ item.getHint());
			result.addDetail(detail);
		}
		retVal = SpecialCharSearcher.findSpecialChar(item.getPrompt());
		if (retVal) {
			LOGGER.log(Level.INFO,
					"Item: " + item.getName()
							+ "\t contains special character at prompt: "
							+ item.getPrompt());
			// analysisResultStr.append("Item: " + item.getName()
			// + "\t contains special character at prompt: " + item.getPrompt()
			// + "\n");
			AnalysisResultDetail detail = new AnalysisResultDetail();
			detail.setElement("Item: " + item.getName());
			detail.setIssue("Contains special character at prompt: "
					+ item.getPrompt());
			detail.setSuggestion("Remove special character at prompt: "
					+ item.getPrompt());
			result.addDetail(detail);
		}
		retVal = SpecialCharSearcher.findSpecialChar(item.getLabel());
		if (retVal) {
			LOGGER.log(
					Level.INFO,
					"Item: " + item.getName()
							+ "\t contains special character at label: "
							+ item.getLabel());
			// analysisResultStr.append("Item: " + item.getName()
			// + "\t contains special character at label: " + item.getLabel()
			// + "\n");
			AnalysisResultDetail detail = new AnalysisResultDetail();
			detail.setElement("Item: " + item.getName());
			detail.setIssue("Contains special character at label: "
					+ item.getLabel());
			detail.setSuggestion("Remove special character at label: "
					+ item.getLabel());
			result.addDetail(detail);
		}
		retVal = SpecialCharSearcher.findSpecialChar(item.getComment());
		if (retVal) {
			LOGGER.log(Level.INFO,
					"Item: " + item.getName()
							+ "\t contains special character at comment: "
							+ item.getComment());
			// analysisResultStr.append("Item: " + item.getName()
			// + "\t contains special character at comment: " +
			// item.getComment()
			// + "\n");
			AnalysisResultDetail detail = new AnalysisResultDetail();
			detail.setElement("Item: " + item.getName());
			detail.setIssue("Contains special character at comment: "
					+ item.getComment());
			detail.setSuggestion("Remove special character at comment: "
					+ item.getComment());
			result.addDetail(detail);
		}

		retVal = SpecialCharSearcher.findSpecialChar(item.getFormatMask());
		if (retVal) {
			LOGGER.log(Level.INFO,
					"Item: " + item.getName()
							+ "\t contains special character at FormatMask: "
							+ item.getFormatMask());
			// analysisResultStr.append("Item: " + item.getName()
			// + "\t contains special character at comment: " +
			// item.getComment()
			// + "\n");
			AnalysisResultDetail detail = new AnalysisResultDetail();
			detail.setElement("Item: " + item.getName());
			detail.setIssue("Contains special character at FormatMask: "
					+ item.getFormatMask());
			detail.setSuggestion("Remove special character at FormatMask: "
					+ item.getFormatMask());
			result.addDetail(detail);
		}
	}

	private String tableCalculation(String lovName, String query) {

		String table = null;

		try {

			int indexFrom = query.toLowerCase().indexOf("from");

			table = query.substring(indexFrom + 4);
			int indexWhere = table.toLowerCase().indexOf("where");
			int indexOrderby = table.toLowerCase().indexOf("order");
			int indexGroupby = table.toLowerCase().indexOf("group");

			if (indexWhere > -1) {
				table = table.substring(0, indexWhere);

			} else if (indexGroupby > -1) {
				table = table.substring(0, indexGroupby);

			} else if (indexOrderby > -1) {
				table = table.substring(0, indexOrderby);
			}

			if (table != null) {
				table = table.replaceAll("&#10;", "");
				table = table.trim();
				if (table.contains(" ")) {
					String[] tableArray = table.split(" ");
					if (tableArray.length > 1) {
						if (tableArray[1].length() > 1) {
							LOGGER.log(
									Level.WARNING,
									"The LOV Record group contains multiple table, "
											+ "please create view from Record group query and use as single Table.");
							analysisResultStr
									.append("The LOV Record group contains multiple table, "
											+ "please create view from Record group query and use as single Table.\n");

							AnalysisResultDetail detail = new AnalysisResultDetail();
							detail.setElement("LOV name: " + lovName);
							detail.setIssue("The LOV Record group contains multiple table.");
							detail.setSuggestion("Please create view from Record group query and use as single Table.");
							result.addDetail(detail);
						}
					}
				}
			}

		} catch (Exception ex) {
			return table;
		}

		return table.trim();
	}

	private ArrayList<String> getDependencies(String frmFilePath,
			SubProgressMonitor monitor) {

		ArrayList<String> dependentLibs = new ArrayList<String>();
		String executeSentece = "java -jar "
				+ new File(Platform.getInstallLocation().getURL().getFile())
				+ File.separator + "DependencyChecker.jar " + frmFilePath
				+ " dependent";
		try {
			Process p = Runtime.getRuntime().exec(
					"cmd.exe /C " + executeSentece);

			BufferedReader in = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line = in.readLine();

			while (line != null) {
				if (line.toLowerCase().endsWith(".fmb")) {
					// dependentLibs.add(line);
					result.getFormList().add(line);
					FormDependency dependency = new FormDependency(line, "Form");
					result.getDependenciesList().add(dependency);

				} else if (line.toLowerCase().endsWith(".olb")) {
					// dependentLibs.add(line);
					result.getOlbList().add(line);
					FormDependency dependency = new FormDependency(line,
							"Object Library");
					result.getDependenciesList().add(dependency);
				}
				line = in.readLine();
			}
			in.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		monitor.worked(2);
		return dependentLibs;
	}

	private int weightFactor = 40;

	public void calculateComplexity() {

		int totalBlocks = result.getTotalBlocks() * weightFactor;
		int totalCanvases = result.getTotalCanvases() * weightFactor;
		int totalFormBasedTriggers = result.getTotalFormBasedTriggers()
				* weightFactor;
		int totalBlockBasedTriggers = result.getTotalBlockBasedTriggers();
		int totalItemBasedTriggers = result.getTotalItemBasedTriggers()
				* weightFactor;
		int totalLOVs = result.getTotalLOVs() * weightFactor;
		int totalWindows = result.getTotalWindows() * weightFactor;
		long dependentFormsSize = result.getFormSize();
		long olbSize = result.getOlbSize();
		long pllSize = result.getPllSize();
		long formSize = form.getForm().length();

		long weight = dependentFormsSize + olbSize + pllSize + formSize;
		// weight in kilo...
		if (weight != 0) {
			weight = weight / 1024;
		}
		int elementWeight = totalBlocks + totalCanvases
				+ totalFormBasedTriggers + totalItemBasedTriggers
				+ totalBlockBasedTriggers + totalLOVs + totalWindows;
		long totalWeight = elementWeight + weight;
		String complexity = "Unknown";
		if (totalWeight <= 2500) {
			complexity = "Simple";
		} else if (totalWeight > 2500 && totalWeight <= 4300) {
			complexity = "Medium";
		} else {
			complexity = "Complex";
		}
		result.setComplexity(complexity);
	}
}
