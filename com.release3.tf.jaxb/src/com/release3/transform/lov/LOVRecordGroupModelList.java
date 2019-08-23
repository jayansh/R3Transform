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
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2-70- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.08.11 at 10:35:10 PM IST 
//

package com.release3.transform.lov;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.oracle.xmlns.forms.LOV;
import com.oracle.xmlns.forms.RecordGroup;

/**
 * <p>
 * Java class for anonymous complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="LOVRecordGroupModel" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="LOV" type="{http://xmlns.oracle.com/Forms}LOV"/>
 *                   &lt;element name="RecordGroup" type="{http://xmlns.oracle.com/Forms}RecordGroup"/>
 *                   &lt;element name="TableName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="ContainsMultipleTable" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "lovRecordGroupModel" })
@XmlRootElement(name = "LOVRecordGroupModelList")
public class LOVRecordGroupModelList {

	@XmlElement(name = "LOVRecordGroupModel")
	protected List<LOVRecordGroupModelList.LOVRecordGroupModel> lovRecordGroupModel;

	/**
	 * Gets the value of the lovRecordGroupModel property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the lovRecordGroupModel property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getLOVRecordGroupModel().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link LOVRecordGroupModelList.LOVRecordGroupModel }
	 * 
	 * 
	 */
	public List<LOVRecordGroupModelList.LOVRecordGroupModel> getLOVRecordGroupModel() {
		if (lovRecordGroupModel == null) {
			lovRecordGroupModel = new ArrayList<LOVRecordGroupModelList.LOVRecordGroupModel>();
		}
		return this.lovRecordGroupModel;
	}

	/**
	 * <p>
	 * Java class for anonymous complex type.
	 * 
	 * <p>
	 * The following schema fragment specifies the expected content contained
	 * within this class.
	 * 
	 * <pre>
	 * &lt;complexType>
	 *   &lt;complexContent>
	 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *       &lt;sequence>
	 *         &lt;element name="LOV" type="{http://xmlns.oracle.com/Forms}LOV"/>
	 *         &lt;element name="RecordGroup" type="{http://xmlns.oracle.com/Forms}RecordGroup"/>
	 *         &lt;element name="TableName" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *         &lt;element name="ContainsMultipleTable" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
	 *       &lt;/sequence>
	 *     &lt;/restriction>
	 *   &lt;/complexContent>
	 * &lt;/complexType>
	 * </pre>
	 * 
	 * 
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { "lov", "recordGroup", "tableName",
			"containsMultipleTable" })
	public static class LOVRecordGroupModel {

		@XmlElement(name = "LOV", required = true)
		protected LOV lov;
		@XmlElement(name = "RecordGroup", required = true)
		protected RecordGroup recordGroup;
		@XmlElement(name = "TableName", required = true)
		protected String tableName;
		@XmlElement(name = "ContainsMultipleTable")
		protected boolean containsMultipleTable;

		/**
		 * Gets the value of the lov property.
		 * 
		 * @return possible object is {@link LOV }
		 * 
		 */
		public LOV getLOV() {
			return lov;
		}

		/**
		 * Sets the value of the lov property.
		 * 
		 * @param value
		 *            allowed object is {@link LOV }
		 * 
		 */
		public void setLOV(LOV value) {
			this.lov = value;
		}

		/**
		 * Gets the value of the recordGroup property.
		 * 
		 * @return possible object is {@link RecordGroup }
		 * 
		 */
		public RecordGroup getRecordGroup() {
			return recordGroup;
		}

		/**
		 * Sets the value of the recordGroup property.
		 * 
		 * @param value
		 *            allowed object is {@link RecordGroup }
		 * 
		 */
		public void setRecordGroup(RecordGroup value) {
			this.recordGroup = value;
		}

		// /**
		// * Gets the value of the tableName property.
		// *
		// * @return
		// * possible object is
		// * {@link String }
		// *
		// */
		// public String getTableName() {
		// return tableName;
		// }
		//
		// /**
		// * Sets the value of the tableName property.
		// *
		// * @param value
		// * allowed object is
		// * {@link String }
		// *
		// */
		// public void setTableName(String value) {
		// this.tableName = value;
		// }

		/**
		 * Gets the value of the containsMultipleTable property.
		 * 
		 */
		public boolean isContainsMultipleTable() {
			return containsMultipleTable;
		}

		/**
		 * Sets the value of the containsMultipleTable property.
		 * 
		 */
		public void setContainsMultipleTable(boolean value) {
			this.containsMultipleTable = value;
		}

		public String getTableName() {
			if (tableName == null) {
				this.tableName = tableCalculation();
			}
			return tableName;
		}

		public void setTableName(String tableName) {
			this.tableName = tableName;
		}

		private String tableCalculation() {

			String table = null;

			try {
				String query = recordGroup.getRecordGroupQuery();
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
					if (table.contains(" ")) {
						table = table.split(" ")[1];
					}
				}

			} catch (Exception ex) {
				return table;
			}

			return table.trim();
		}
	}

}
