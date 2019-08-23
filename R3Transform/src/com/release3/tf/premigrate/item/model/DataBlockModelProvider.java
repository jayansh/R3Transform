package com.release3.tf.premigrate.item.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import com.converter.toolkit.ui.FormsObject;
import com.oracle.xmlns.forms.Block;
import com.oracle.xmlns.forms.Item;
import com.release3.dbconnect.DBCommand;
import com.release3.tf.analysis.AnalysisResultDetail;
import com.release3.tf.core.form.Settings;
import com.release3.transform.model.BlockModelProvider;

public class DataBlockModelProvider {
	private List<Block> dbBlockList;
	private static DataBlockModelProvider content;
	/**
	 * This contains only PK column corresponding to respective block.
	 */
	private HashMap<String, List<String>> blockColumnMap = new HashMap<String, List<String>>();

	private DataBlockModelProvider() {

	}

	public static DataBlockModelProvider getInstance() {
		if (content != null) {
			return content;
		} else {
			content = new DataBlockModelProvider();
			return content;
		}
	}

	/**
	 * Filter blocks list for a form, retrieve only database blocks
	 */
	public void filterBlocks() {
		dbBlockList = new ArrayList<Block>();
		List<Block> blockList = BlockModelProvider.getInstance().getBlockList();
		for (Block block : blockList) {
			if (block.isDatabaseBlock() != null && block.isDatabaseBlock()) {
				itemValidation(block);
				if (!primaryKeyValidation(block)) {
					dbBlockList.add(block);
				}

			}
		}

	}

	public List<Block> getBlockList() {
		return dbBlockList;
	}

	public void itemValidation(Block block) {
		System.out.println(block);
		System.out.println(block.getQueryDataSourceType());
		if (block.getQueryDataSourceType() == null) {
			block.setQueryDataSourceType("Table");
		}
		if (block.getQueryDataSourceType() == "Table"
				|| block.getQueryDataSourceType().equals("Table")) {
			List<String> columns = new ArrayList<String>();
			List<FormsObject> objectList = block.getChildren();
			for (FormsObject formsObject : objectList) {
				if (formsObject instanceof Item) {
					Item item = (Item) formsObject;

					if (item.isDatabaseItem()==null || item.isDatabaseItem() ) {
						String columnName = item.getColumnName();

						if (columnName == null) {

							columnName = item.getName();

						}
						columns.add(columnName);
					}
				}

			}

			blockColumnMap.put(block.getName(), columns);
		}
	}

	/**
	 * 
	 * @return true if validation succeeded false if validation failed
	 */
	public boolean primaryKeyValidation(Block block) {

		String tableName = block.getQueryDataSourceName();
		if (tableName == null) {
			tableName = block.getName();
		}

		String owner = Settings.getSettings().getDbSrcUser();
		int dotIndex = tableName.indexOf('.');
		if (dotIndex > -1) {
			owner = tableName.substring(0, dotIndex - 1);
			tableName = tableName.substring(dotIndex);
		}

		try {
			boolean status = checkColumns(tableName, owner,
					blockColumnMap.get(block.getName()));
			
				return status;
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	public boolean checkColumns(String tableName, String owner,
			List<String> itemColumns) throws ClassNotFoundException {
		String query = "SELECT UNIQUE COLUMN_NAME, POSITION  FROM ALL_CONS_COLUMNS A JOIN ALL_CONSTRAINTS C "
				+ " ON A.CONSTRAINT_NAME = C.CONSTRAINT_NAME WHERE C.TABLE_NAME = '"
				+ tableName.toUpperCase()
				+ "' AND C.CONSTRAINT_TYPE = 'P' AND C.OWNER='"
				+ owner.toUpperCase() + "' ORDER BY POSITION";
		try {
			Statement stmt = DBCommand.getDBCommand().getConnection()
					.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String column = rs.getString(1);
				if (itemColumns.contains(column)) {

					return true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		return false;
	}

}
