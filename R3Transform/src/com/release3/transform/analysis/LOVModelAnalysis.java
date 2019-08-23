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
package com.release3.transform.analysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.converter.toolkit.sqlparsing.CodeProcessor;
import com.converter.toolkit.sqlparsing.ProcessedCode;
import com.converter.toolkit.sqlparsing.Variable;
import com.oracle.xmlns.forms.LOV;
import com.oracle.xmlns.forms.RecordGroup;
import com.release3.customization.parameter.ParameterType;
import com.release3.customization.recordgroup.RecordGroupType;
import com.release3.transform.lov.LOVRecordGroupModelList;
import com.release3.transform.lov.LOVRecordGroupModelList.LOVRecordGroupModel;

public class LOVModelAnalysis {

	private LOVRecordGroupModelList lovRGModelList;

	public LOVModelAnalysis() {
		lovRGModelList = new LOVRecordGroupModelList();
	}

	public void analysis(List<LOV> lovList, List<RecordGroup> rgList) {
		for (LOV lov : lovList) {
			for (RecordGroup recordGroup : rgList) {
				if (lov.getRecordGroupName().equals(recordGroup.getName())
						|| lov.getRecordGroupName() == recordGroup.getName()) {
					// LOVRecordGroupModelList.LOVRecordGroupModel lovModel =
					// new LOVRecordGroupModelList.LOVRecordGroupModel(lov,
					// recordGroup);
					LOVRecordGroupModelList.LOVRecordGroupModel lovModel = new LOVRecordGroupModelList.LOVRecordGroupModel();
					if (lov.getRecordGroupName().contains("$")) {
						String name = lov.getRecordGroupName()
								.replace("$", "S");
						lov.setRecordGroupName(name);
					}
					if (recordGroup.getName().contains("$")) {
						String name = recordGroup.getName().replace("$", "S");
						recordGroup.setName(name);
					}
					lovModel.setLOV(lov);
					lovModel.setRecordGroup(recordGroup);
					lovRGModelList.getLOVRecordGroupModel().add(lovModel);
				}
			}
		}

	}

	public void setLOVRecordGroupModelList(
			LOVRecordGroupModelList lovRGModelList) {
		if (lovRGModelList != null) {
			this.lovRGModelList = lovRGModelList;
		} else {
			this.lovRGModelList = new LOVRecordGroupModelList();
		}

	}

	public LOVRecordGroupModelList getLOVRecordGroupModelList() {
		return lovRGModelList;
	}

	public List<RecordGroupType> getCustomRecordGroupList() {
		return getRecordGroupCustomizationFromLovRGModel(lovRGModelList);
	}

	public List<RecordGroupType> getRecordGroupCustomizationFromLovRGModel(
			LOVRecordGroupModelList lovRecordGroupModelList) {
		List<RecordGroupType> rgList = new ArrayList<RecordGroupType>();
		List<LOVRecordGroupModel> lovRgModelList = lovRecordGroupModelList
				.getLOVRecordGroupModel();
		for (LOVRecordGroupModel lovRecordGroupModel : lovRgModelList) {

			String tableName = lovRecordGroupModel.getTableName();
			String schemaName = null;
			boolean componentOrderBy = false;
			if (tableName.indexOf('.') > -1) {
				schemaName = tableName.substring(0, tableName.indexOf('.'));
				tableName = tableName.substring(tableName.indexOf('.'));
			}

			com.oracle.xmlns.forms.RecordGroup oracleRG = lovRecordGroupModel
					.getRecordGroup();
			RecordGroupType recordGroup = new RecordGroupType();
			recordGroup.setName(oracleRG.getName());
			recordGroup.setSchema(schemaName);
			recordGroup.setTable(tableName);
			recordGroup.setComponentOrderBy(componentOrderBy);
			String query = oracleRG.getRecordGroupQuery();
			CodeProcessor processor = new CodeProcessor();
			ProcessedCode processedCode = processor.process(query);
			Vector<Variable> variables = processedCode.getVariables();
			for (Variable variable : variables) {
				ParameterType paramType = new ParameterType();
				paramType.setName(variable.getReplacedName());
				paramType.setBlock(variable.getBlock());
				paramType.setItem(paramType.getItem());
//				paramType.setType(variable.getType());
				paramType.setType("in");
				if (recordGroup.getParameter().contains(
						variable.getReplacedName())) {
					recordGroup.getParameter().add(paramType);
				}
			}
			rgList.add(recordGroup);
		}
		return rgList;
	}
}
