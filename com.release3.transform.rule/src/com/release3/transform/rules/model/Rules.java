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
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.07.22 at 02:57:06 AM IST 
//


package com.release3.transform.rules.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DatabaseBlockRule" maxOccurs="8">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="IsDBBlockType" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                   &lt;element name="QueryDataSourceType">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;enumeration value="Table"/>
 *                         &lt;enumeration value="Other"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="HasQueryDataSourceName" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                   &lt;element name="Result">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;enumeration value="DBBlock"/>
 *                         &lt;enumeration value="NonDBBlock"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
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
@XmlType(name = "", propOrder = {
    "databaseBlockRule"
})
@XmlRootElement(name = "Rules")
public class Rules {

    @XmlElement(name = "DatabaseBlockRule", required = true)
    protected List<Rules.DatabaseBlockRule> databaseBlockRule;

    /**
     * Gets the value of the databaseBlockRule property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the databaseBlockRule property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDatabaseBlockRule().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Rules.DatabaseBlockRule }
     * 
     * 
     */
    public List<Rules.DatabaseBlockRule> getDatabaseBlockRule() {
        if (databaseBlockRule == null) {
            databaseBlockRule = new ArrayList<Rules.DatabaseBlockRule>();
        }
        return this.databaseBlockRule;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="IsDBBlockType" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
     *         &lt;element name="QueryDataSourceType">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;enumeration value="Table"/>
     *               &lt;enumeration value="Other"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="HasQueryDataSourceName" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
     *         &lt;element name="Result">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;enumeration value="DBBlock"/>
     *               &lt;enumeration value="NonDBBlock"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *       &lt;/sequence>
     *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "isDBBlockType",
        "queryDataSourceType",
        "hasQueryDataSourceName",
        "result"
    })
    public static class DatabaseBlockRule {

        @XmlElement(name = "IsDBBlockType")
        protected boolean isDBBlockType;
        @XmlElement(name = "QueryDataSourceType", required = true)
        protected String queryDataSourceType;
        @XmlElement(name = "HasQueryDataSourceName")
        protected boolean hasQueryDataSourceName;
        @XmlElement(name = "Result", required = true)
        protected String result;
        @XmlAttribute(required = true)
        @XmlSchemaType(name = "anySimpleType")
        protected String name;

        /**
         * Gets the value of the isDBBlockType property.
         * 
         */
        public boolean isIsDBBlockType() {
            return isDBBlockType;
        }

        /**
         * Sets the value of the isDBBlockType property.
         * 
         */
        public void setIsDBBlockType(boolean value) {
            this.isDBBlockType = value;
        }

        /**
         * Gets the value of the queryDataSourceType property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getQueryDataSourceType() {
            return queryDataSourceType;
        }

        /**
         * Sets the value of the queryDataSourceType property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setQueryDataSourceType(String value) {
            this.queryDataSourceType = value;
        }

        /**
         * Gets the value of the hasQueryDataSourceName property.
         * 
         */
        public boolean isHasQueryDataSourceName() {
            return hasQueryDataSourceName;
        }

        /**
         * Sets the value of the hasQueryDataSourceName property.
         * 
         */
        public void setHasQueryDataSourceName(boolean value) {
            this.hasQueryDataSourceName = value;
        }

        /**
         * Gets the value of the result property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getResult() {
            return result;
        }

        /**
         * Sets the value of the result property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setResult(String value) {
            this.result = value;
        }

        /**
         * Gets the value of the name property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getName() {
            return name;
        }

        /**
         * Sets the value of the name property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setName(String value) {
            this.name = value;
        }

    }

}
