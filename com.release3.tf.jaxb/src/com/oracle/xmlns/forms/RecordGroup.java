//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0.5-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.12.12 at 11:40:08 AM EST 
//


package com.oracle.xmlns.forms;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.converter.toolkit.ui.FormsObject;


/**
 * <p>Java class for RecordGroup complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RecordGroup">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded">
 *         &lt;element ref="{http://xmlns.oracle.com/Forms}RecordGroupColumn" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/choice>
 *       &lt;attribute name="Comment" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="DirtyInfo" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="Name" use="required" type="{http://xmlns.oracle.com/Forms}nameType" />
 *       &lt;attribute name="ParentFilename" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ParentFilepath" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ParentModule" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ParentModuleType" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="ParentName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ParentType" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="PersistentClientInfoLength" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="RecordGroupFetchSize" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="RecordGroupQuery" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="RecordGroupType" type="{http://xmlns.oracle.com/Forms}RecordGroupTypeType" />
 *       &lt;attribute name="SmartClass" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="SubclassObjectGroup" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RecordGroup", namespace = "http://xmlns.oracle.com/Forms", propOrder = {
    "recordGroupColumn"
})
public class RecordGroup
    extends FormsObject
{

    @XmlElement(name = "RecordGroupColumn", namespace = "http://xmlns.oracle.com/Forms")
    protected List<RecordGroupColumn> recordGroupColumn;
    @XmlAttribute(name = "Comment")
    protected String comment;
    @XmlAttribute(name = "DirtyInfo")
    protected Boolean dirtyInfo;
    @XmlAttribute(name = "Name", required = true)
    protected String name;
    @XmlAttribute(name = "ParentFilename")
    protected String parentFilename;
    @XmlAttribute(name = "ParentFilepath")
    protected String parentFilepath;
    @XmlAttribute(name = "ParentModule")
    protected String parentModule;
    @XmlAttribute(name = "ParentModuleType")
    protected BigInteger parentModuleType;
    @XmlAttribute(name = "ParentName")
    protected String parentName;
    @XmlAttribute(name = "ParentType")
    protected BigInteger parentType;
    @XmlAttribute(name = "PersistentClientInfoLength")
    protected BigInteger persistentClientInfoLength;
    @XmlAttribute(name = "RecordGroupFetchSize")
    protected BigInteger recordGroupFetchSize;
    @XmlAttribute(name = "RecordGroupQuery")
    protected String recordGroupQuery;
    @XmlAttribute(name = "RecordGroupType")
    protected String recordGroupType;
    @XmlAttribute(name = "SmartClass")
    protected Boolean smartClass;
    @XmlAttribute(name = "SubclassObjectGroup")
    protected Boolean subclassObjectGroup;

    /**
     * Gets the value of the recordGroupColumn property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the recordGroupColumn property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRecordGroupColumn().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RecordGroupColumn }
     * 
     * 
     */
    public List<RecordGroupColumn> getRecordGroupColumn() {
        if (recordGroupColumn == null) {
            recordGroupColumn = new ArrayList<RecordGroupColumn>();
        }
        return this.recordGroupColumn;
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
     * Gets the value of the parentFilename property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentFilename() {
        return parentFilename;
    }

    /**
     * Sets the value of the parentFilename property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentFilename(String value) {
        this.parentFilename = value;
    }

    /**
     * Gets the value of the parentFilepath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentFilepath() {
        return parentFilepath;
    }

    /**
     * Sets the value of the parentFilepath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentFilepath(String value) {
        this.parentFilepath = value;
    }

    /**
     * Gets the value of the parentModule property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentModule() {
        return parentModule;
    }

    /**
     * Sets the value of the parentModule property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentModule(String value) {
        this.parentModule = value;
    }

    /**
     * Gets the value of the parentModuleType property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getParentModuleType() {
        return parentModuleType;
    }

    /**
     * Sets the value of the parentModuleType property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setParentModuleType(BigInteger value) {
        this.parentModuleType = value;
    }

    /**
     * Gets the value of the parentName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentName() {
        return parentName;
    }

    /**
     * Sets the value of the parentName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentName(String value) {
        this.parentName = value;
    }

    /**
     * Gets the value of the parentType property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getParentType() {
        return parentType;
    }

    /**
     * Sets the value of the parentType property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setParentType(BigInteger value) {
        this.parentType = value;
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
     * Gets the value of the recordGroupFetchSize property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getRecordGroupFetchSize() {
        return recordGroupFetchSize;
    }

    /**
     * Sets the value of the recordGroupFetchSize property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setRecordGroupFetchSize(BigInteger value) {
        this.recordGroupFetchSize = value;
    }

    /**
     * Gets the value of the recordGroupQuery property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecordGroupQuery() {
        return recordGroupQuery;
    }

    /**
     * Sets the value of the recordGroupQuery property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecordGroupQuery(String value) {
        this.recordGroupQuery = value;
    }

    /**
     * Gets the value of the recordGroupType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecordGroupType() {
        return recordGroupType;
    }

    /**
     * Sets the value of the recordGroupType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecordGroupType(String value) {
        this.recordGroupType = value;
    }

    /**
     * Gets the value of the smartClass property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSmartClass() {
        return smartClass;
    }

    /**
     * Sets the value of the smartClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSmartClass(Boolean value) {
        this.smartClass = value;
    }

    /**
     * Gets the value of the subclassObjectGroup property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSubclassObjectGroup() {
        return subclassObjectGroup;
    }

    /**
     * Sets the value of the subclassObjectGroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSubclassObjectGroup(Boolean value) {
        this.subclassObjectGroup = value;
    }

}
