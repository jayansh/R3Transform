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
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0.5-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.12.12 at 11:40:08 AM EST 
//


package com.oracle.xmlns.forms;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import com.converter.toolkit.ui.FormsObject;


/**
 * <p>Java class for DataSourceColumn complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DataSourceColumn">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="DSCLength" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="DSCMandatory" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="DSCName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="DSCNochildren" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="DSCParentName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="DSCPrecision" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="DSCScale" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="DSCType" type="{http://xmlns.oracle.com/Forms}DSCTypeType" />
 *       &lt;attribute name="DSCTypeName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="PersistentClientInfoLength" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="SubclassSubObject" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="Type">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="Delete"/>
 *             &lt;enumeration value="Insert"/>
 *             &lt;enumeration value="Lock"/>
 *             &lt;enumeration value="Query"/>
 *             &lt;enumeration value="Update"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DataSourceColumn", namespace = "http://xmlns.oracle.com/Forms")
public class DataSourceColumn
    extends FormsObject
{

    @XmlAttribute(name = "DSCLength")
    protected BigInteger dscLength;
    @XmlAttribute(name = "DSCMandatory")
    protected Boolean dscMandatory;
    @XmlAttribute(name = "DSCName")
    protected String name;
    @XmlAttribute(name = "DSCNochildren")
    protected Boolean dscNochildren;
    @XmlAttribute(name = "DSCParentName")
    protected String dscParentName;
    @XmlAttribute(name = "DSCPrecision")
    protected BigInteger dscPrecision;
    @XmlAttribute(name = "DSCScale")
    protected BigInteger dscScale;
    @XmlAttribute(name = "DSCType")
    protected String dscType;
    @XmlAttribute(name = "DSCTypeName")
    protected String dscTypeName;
    @XmlAttribute(name = "PersistentClientInfoLength")
    protected BigInteger persistentClientInfoLength;
    @XmlAttribute(name = "SubclassSubObject")
    protected Boolean subclassSubObject;
    @XmlAttribute(name = "Type")
    protected String type;

    /**
     * Gets the value of the dscLength property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getDSCLength() {
        return dscLength;
    }

    /**
     * Sets the value of the dscLength property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setDSCLength(BigInteger value) {
        this.dscLength = value;
    }

    /**
     * Gets the value of the dscMandatory property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDSCMandatory() {
        return dscMandatory;
    }

    /**
     * Sets the value of the dscMandatory property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDSCMandatory(Boolean value) {
        this.dscMandatory = value;
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

    /**
     * Gets the value of the dscNochildren property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDSCNochildren() {
        return dscNochildren;
    }

    /**
     * Sets the value of the dscNochildren property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDSCNochildren(Boolean value) {
        this.dscNochildren = value;
    }

    /**
     * Gets the value of the dscParentName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDSCParentName() {
        return dscParentName;
    }

    /**
     * Sets the value of the dscParentName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDSCParentName(String value) {
        this.dscParentName = value;
    }

    /**
     * Gets the value of the dscPrecision property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getDSCPrecision() {
        return dscPrecision;
    }

    /**
     * Sets the value of the dscPrecision property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setDSCPrecision(BigInteger value) {
        this.dscPrecision = value;
    }

    /**
     * Gets the value of the dscScale property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getDSCScale() {
        return dscScale;
    }

    /**
     * Sets the value of the dscScale property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setDSCScale(BigInteger value) {
        this.dscScale = value;
    }

    /**
     * Gets the value of the dscType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDSCType() {
        return dscType;
    }

    /**
     * Sets the value of the dscType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDSCType(String value) {
        this.dscType = value;
    }

    /**
     * Gets the value of the dscTypeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDSCTypeName() {
        return dscTypeName;
    }

    /**
     * Sets the value of the dscTypeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDSCTypeName(String value) {
        this.dscTypeName = value;
    }

    /**
     * Gets the value of the persistentClientInfoLength property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPersistentClientInfoLength() {
        return persistentClientInfoLength;
    }

    /**
     * Sets the value of the persistentClientInfoLength property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPersistentClientInfoLength(BigInteger value) {
        this.persistentClientInfoLength = value;
    }

    /**
     * Gets the value of the subclassSubObject property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSubclassSubObject() {
        return subclassSubObject;
    }

    /**
     * Sets the value of the subclassSubObject property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSubclassSubObject(Boolean value) {
        this.subclassSubObject = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

}
