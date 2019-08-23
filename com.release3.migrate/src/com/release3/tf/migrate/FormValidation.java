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
package com.release3.tf.migrate;

import java.io.File;
import java.io.IOException;
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

import com.converter.toolkit.ui.FormsObject;
import com.oracle.xmlns.forms.Block;
import com.oracle.xmlns.forms.FormModule;
import com.oracle.xmlns.forms.Item;
import com.oracle.xmlns.forms.LOV;
import com.oracle.xmlns.forms.Module;
import com.oracle.xmlns.forms.RecordGroup;
import com.oracle.xmlns.forms.Relation;
import com.release3.dbconnect.DBCommand;
import com.release3.tf.core.form.Settings;
import com.release3.tf.migration.model.MigrationForm;
import com.release3.transform.util.SpecialCharSearcher;

/**
 * Form validation during migration process.
 */
public class FormValidation {
	private final static Logger LOGGER = Logger.getLogger(FormValidation.class
			.getName());
	FileHandler handler;
	private MigrationForm form;
	private static Connection conn = null;
	private StringBuffer analysisResult;

	private IProgressMonitor monitor;

	/**
	 * This contains only PK column corresponding to respective block.
	 */
	private HashMap<String, List<String>> blockColumnMap = new HashMap<String, List<String>>();
	private HashMap<String, Block> blockMap = new HashMap<String, Block>();
	private HashMap<String, String> blockTableMap = new HashMap<String, String>();

	private HashMap<String, LOV> lovRGMap = new HashMap<String, LOV>();
	private HashMap<String, RecordGroup> rgMap = new HashMap<String, RecordGroup>();

	public FormValidation(MigrationForm form, IProgressMonitor monitor) {
		this.form = form;
		this.monitor = monitor;
		analysisResult = new StringBuffer();
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
	public int start() {
		analysisResult.append("Form: " + form.getFormName() + "\n");
		LOGGER.log(Level.INFO, "Form: " + form.getFormName());

		Module module = form.getModule();
		FormModule formModule = module.getFormModule();
		List<FormsObject> children = formModule.getChildren();
		for (FormsObject formsObject : children) {
			if (formsObject instanceof LOV) {
				LOV lov = (LOV) formsObject;
				lovRGMap.put(lov.getRecordGroupName(), lov);
			} else

			if (formsObject instanceof Block) {
				Block block = (Block) formsObject;
				blockValidation(block);

				// itemAnalysis.analysis(block);
				// blockAnalysis.analysis(block);
			} else if (formsObject instanceof Block) {
				if (!validateJoinCondition((Block) formsObject)) {
					LOGGER.log(Level.WARNING,
							"JoinCondition validation failed. Please modify your block for join condition.");
					analysisResult
							.append("JoinCondition validation failed. Please modify your block for join condition.\n");
					return 2;
				}
			} else if (formsObject instanceof RecordGroup) {
				RecordGroup rg = (RecordGroup) formsObject;
				rgMap.put(rg.getName(), rg);
			}
		}

		LOGGER.log(Level.INFO, "Total Blocks: " + blockMap.size());
		Set<String> blockSet = blockMap.keySet();
		for (String blockName : blockSet) {
			itemValidation(blockMap.get(blockName));
		}

		lovAnalysis();
		if (!primaryKeyValidation()) {
			LOGGER.log(Level.WARNING,
					"Primary key validation failed. Please modify your form before migration.");
			analysisResult
					.append("Primary key validation failed. Please modify your form before migration.\n");
			return 1;
		}

		return 0;
	}

	public String getAnalysisResult() {
		return analysisResult.toString();
	}

	public void blockValidation(Block block) {
		String tableName = block.getQueryDataSourceName();
		if (tableName == null) {
			tableName = block.getName();
		}
		LOGGER.log(Level.INFO, "Block Name: " + block.getName() + " Table: "
				+ tableName);
		analysisResult.append("Block Name: " + block.getName() + " Table: "
				+ tableName);
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
					analysisResult
							.append("Block \""
									+ currentBlock
									+ "\" doesn't contains item with primary key column "
									+ column + "\n");
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

	/**
	 * @deprecated user DBCommand.getConnection instead.
	 * @return
	 */
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
						analysisResult.append("Master Block :"
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
									analysisResult
											.append("Condition \""
													+ condition
													+ "\" Column \""
													+ column
													+ "\" doesn't contain block name.\n");
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

					LOGGER.log(Level.INFO, "LOV name: " + lov.getName()
							+ "\n\tTable Name: " + tableCalculation(query));
					analysisResult.append("LOV name: " + lov.getName()
							+ "\n\tTable Name: " + tableCalculation(query)
							+ "\n");

				}
			}
		}
	}

	public void specialCharAnalysis(Item item) {
		boolean retVal = SpecialCharSearcher.findSpecialChar(item.getHint());
		if (retVal) {
			LOGGER.log(Level.INFO, "Item: " + item.getName()
					+ "\t contains special character at: " + item.getHint());
			analysisResult.append("Item: " + item.getName()
					+ "\t contains special character at: " + item.getHint()
					+ "\n");
		}
		retVal = SpecialCharSearcher.findSpecialChar(item.getPrompt());
		if (retVal) {
			LOGGER.log(Level.INFO, "Item: " + item.getName()
					+ "\t contains special character at: " + item.getPrompt());
			analysisResult.append("Item: " + item.getName()
					+ "\t contains special character at: " + item.getPrompt()
					+ "\n");
		}
		retVal = SpecialCharSearcher.findSpecialChar(item.getLabel());
		if (retVal) {
			LOGGER.log(Level.INFO, "Item: " + item.getName()
					+ "\t contains special character at: " + item.getLabel());
			analysisResult.append("Item: " + item.getName()
					+ "\t contains special character at: " + item.getLabel()
					+ "\n");
		}
		retVal = SpecialCharSearcher.findSpecialChar(item.getComment());
		if (retVal) {
			LOGGER.log(Level.INFO, "Item: " + item.getName()
					+ "\t contains special character at: " + item.getComment());
			analysisResult.append("Item: " + item.getName()
					+ "\t contains special character at: " + item.getComment()
					+ "\n");
		}
	}

	private String tableCalculation(String query) {

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
									"The LOV Record group contains multiple table, please create view from Record group query and use as single Table.");
							analysisResult
									.append("The LOV Record group contains multiple table, please create view from Record group query and use as single Table.\n");
						}
					}
				}
			}

		} catch (Exception ex) {
			return table;
		}

		return table.trim();
	}
}
