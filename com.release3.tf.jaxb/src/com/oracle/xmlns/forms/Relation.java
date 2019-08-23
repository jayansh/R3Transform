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
 * <p>Java class for Relation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Relation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="AutoQuery" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="Comment" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Deferred" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="DeleteRecord" type="{http://xmlns.oracle.com/Forms}DeleteRecordType" />
 *       &lt;attribute name="DetailBlock" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="DetailItemref" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="DirtyInfo" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="JoinCondition" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Name" use="required" type="{http://xmlns.oracle.com/Forms}nameType" />
 *       &lt;attribute name="PersistentClientInfoLength" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="PreventMasterlessOperations" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="RelationType" type="{http://xmlns.oracle.com/Forms}RelationTypeType" />
 *       &lt;attribute name="SubclassSubObject" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Relation", namespace = "http://xmlns.oracle.com/Forms")
public class Relation
    extends FormsObject
{

    @XmlAttribute(name = "AutoQuery")
    protected Boolean autoQuery;
    @XmlAttribute(name = "Comment")
    protected String comment;
    @XmlAttribute(name = "Deferred")
    protected Boolean deferred;
    @XmlAttribute(name = "DeleteRecord")
    protected String deleteRecord;
    @XmlAttribute(name = "DetailBlock")
    protected String detailBlock;
    @XmlAttribute(name = "DetailItemref")
    protected String detailItemref;
    @XmlAttribute(name = "DirtyInfo")
    protected Boolean dirtyInfo;
    @XmlAttribute(name = "JoinCondition")
    protected String joinCondition;
    @XmlAttribute(name = "Name", required = true)
    protected String name;
    @XmlAttribute(name = "PersistentClientInfoLength")
    protected BigInteger persistentClientInfoLength;
    @XmlAttribute(name = "PreventMasterlessOperations")
    protected Boolean preventMasterlessOperations;
    @XmlAttribute(name = "RelationType")
    protected String relationType;
    @XmlAttribute(name = "SubclassSubObject")
    protected Boolean subclassSubObject;

    /**
     * Gets the value of the autoQuery property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAutoQuery() {
        return autoQuery;
    }

    /**
     * Sets the value of the autoQuery property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAutoQuery(Boolean value) {
        this.autoQuery = value;
    }

    /**
     * Gets the value of the comment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the value of the comment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComment(String value) {
        this.comment = value;
    }

    /**
     * Gets the value of the deferred property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDeferred() {
        return deferred;
    }

    /**
     * Sets the value of the deferred property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDeferred(Boolean value) {
        this.deferred = value;
    }

    /**
     * Gets the value of the deleteRecord property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeleteRecord() {
        return deleteRecord;
    }

    /**
     * Sets the value of the deleteRecord property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeleteRecord(String value) {
        this.deleteRecord = value;
    }

    /**
     * Gets the value of the detailBlock property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDetailBlock() {
        return detailBlock;
    }

    /**
     * Sets the value of the detailBlock property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDetailBlock(String value) {
        this.detailBlock = value;
    }

    /**
     * Gets the value of the detailItemref property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDetailItemref() {
        return detailItemref;
    }

    /**
     * Sets the value of the detailItemref property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDetailItemref(String value) {
        this.detailItemref = value;
    }

    /**
     * Gets the value of the dirtyInfo property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDirtyInfo() {
        return dirtyInfo;
    }

    /**
     * Sets the value of the dirtyInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDirtyInfo(Boolean value) {
        this.dirtyInfo = value;
    }

    /**
     * Gets the value of the joinCondition property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJoinCondition() {
        return joinCondition;
    }

    /**
     * Sets the value of the joinCondition property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJoinCondition(String value) {
        this.joinCondition = value;
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
     * Gets the value of the preventMasterlessOperations property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isPreventMasterlessOperations() {
        return preventMasterlessOperations;
    }

    /**
     * Sets the value of the preventMasterlessOperations property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPreventMasterlessOperations(Boolean value) {
        this.preventMasterlessOperations = value;
    }

    /**
     * Gets the value of the relationType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelationType() {
        return relationType;
    }

    /**
     * Sets the value of the relationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelationType(String value) {
        this.relationType = value;
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

}
