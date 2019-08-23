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
package com.converter.toolkit.ui.control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;

import com.converter.toolkit.sqlparsing.CodeProcessor;
import com.converter.toolkit.sqlparsing.ProcessedCode;
import com.converter.toolkit.sqlparsing.Variable;
import com.converter.toolkit.ui.custom.ParamInterface;
import com.converter.toolkit.ui.custom.Parameter;
import com.converter.toolkit.ui.custom.RecordGroup;
import com.converter.toolkit.ui.custom.Trigger;
import com.release3.tf.core.form.Settings;
import com.release3.tf.core.form.TreeObject;
import com.release3.tf.core.form.UISettings;

public class ParametersControl {

	private String filter;
	private String order;
	private List<Parameter> pls;
	private int currentRow;
	private Object masterObject = null;

	public ParametersControl(Object masterObject) {
		this.masterObject = masterObject;
		currentRow = 0;
		pls = ((ParamInterface) masterObject).getParameter();
	}

	private void makeIterator() {
	}

	public List getIterator() {
		return pls;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public String getFilter() {
		return filter;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getOrder() {
		return order;
	}

	public void rowSelection(int rowIndex) {
		currentRow = rowIndex;
	}

	public int getCurrentIndex() {
		return currentRow;
	}

	public Object getCurrentRow() {
		return pls.get(currentRow);
	}

	public boolean getNextDisabled() {
		return false;
	}

	public boolean getPrevDisabled() {
		return false;
	}

	public void save() throws Exception {
		// ((ParamInterface) dc.getCurrentRow())
		// .setParameters((Vector<Parameter>) pls);
		// ((ParamInterface) UISettings.getUISettingsBean().getTriggersControl()
		// .getCurrentRow()).setParameters((Vector<Parameter>) pls);
		// UISettings.getUISettingsBean().getTriggersControl().save();

	}

	public void create() throws Exception {
		currentRow = pls.size();
		pls.add(currentRow, new Parameter());
	}

	public Object getObject() {
		return null;
	}

	public void refresh() {
	}

	public void registerObject(Object obj, int level) {
	}

	public Object getCurrentRowNoProxy() {
		return null;
	}

	public void removeObject(Object obj, int level) {
	}

	/*
	 * Object obj could be Trigger or RecordGroup
	 */
	public void loadParameters(Object obj, String formName) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rset = null;

		if (obj instanceof Trigger) {
			String proc = ((Trigger) UISettings.getUISettingsBean()
					.getTriggersControl().getObject()).getPlsql();
			// ignore the form name, check in the package specified by user.
			if (proc.indexOf(".") > 0) {
				String packageName = proc.substring(0, proc.indexOf("."));
				proc = proc.substring(proc.indexOf(".") + 1);
				formName = packageName;
			}
			Settings settings = Settings.getSettings();

			try {
				// Load Oracle driver
				// DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
				Locale.setDefault(Locale.ENGLISH);
				Class.forName(settings.getDbSrcDriver());
				// Connect to the local database
				conn = DriverManager.getConnection(settings.getDbSrcUrl(),
						settings.getDbSrcUser(), settings.getDbSrcPassword());
				// Query the employee names
				stmt = conn
						.prepareStatement("select argument_name,in_out from ALL_ARGUMENTS where owner=:1 AND package_name=:2 AND object_name=:3 and position >1  order by position");
				stmt.setString(1, settings.getDbSrcUser().toUpperCase());
				stmt.setString(2, formName.toUpperCase());
				stmt.setString(3, proc.toUpperCase());
				rset = stmt.executeQuery();

				pls.clear();
				while (rset.next()) {
					Parameter prm = new Parameter();
					prm.setName(rset.getString(1));
					prm.setType(rset.getString(2).toLowerCase());
					pls.add(prm);
				}
				// Close the result set, statement, and the connection
			} catch (Exception e) {
				e.printStackTrace();
				try {
					if (rset != null)
						rset.close();
					if (stmt != null)
						stmt.close();
					if (conn != null)
						conn.close();
				} catch (Exception e1) {
				}
			}
		}
		if (obj instanceof RecordGroup) {

			com.oracle.xmlns.forms.RecordGroup frmRecGroup = (com.oracle.xmlns.forms.RecordGroup) ((TreeObject) (((DefaultMutableTreeNode) obj)
					.getUserObject())).getFrmObj();

			CodeProcessor codeProcessor = new CodeProcessor();

			ProcessedCode code = codeProcessor.process(frmRecGroup
					.getRecordGroupQuery());
			Vector<Variable> var = (Vector<Variable>) code.getVariables();
			Iterator<Variable> itr = var.iterator();

			while (itr.hasNext()) {
				Variable v = itr.next();

				Parameter prm = new Parameter();
				prm.setName(v.getName());
				prm.setBlock(v.getBlock());
				prm.setItem(v.getItem());
				prm.setType("in");
				pls.add(prm);
				if (!v.getBlock().equals("GLOBAL")) {
					prm = new Parameter();
					prm.setBlock(v.getBlock());
					prm.setItem(v.getItem());
					prm.setType("depend");
					pls.add(prm);

				}

			}

		}

	}

}
