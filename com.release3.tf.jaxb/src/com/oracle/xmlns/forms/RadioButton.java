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
 * <p>Java class for RadioButton complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RadioButton">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="AccessKey" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="BackColor" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Comment" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="DirtyInfo" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="DistanceBetweenRecords" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="Enabled" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="FillPattern" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="FontName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="FontSize" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="FontSpacing" type="{http://xmlns.oracle.com/Forms}FontSpacingType" />
 *       &lt;attribute name="FontStyle" type="{http://xmlns.oracle.com/Forms}FontStyleType" />
 *       &lt;attribute name="FontWeight" type="{http://xmlns.oracle.com/Forms}FontWeightType" />
 *       &lt;attribute name="ForegroundColor" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Height" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="Label" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Name" use="required" type="{http://xmlns.oracle.com/Forms}nameType" />
 *       &lt;attribute name="ParentFilename" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ParentFilepath" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ParentModule" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ParentModuleType" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="ParentName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ParentSourceLevel1ObjectName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ParentSourceLevel1ObjectType" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="ParentSourceLevel2ObjectName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ParentSourceLevel2ObjectType" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="ParentType" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="PersistentClientInfoLength" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="Prompt" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="PromptAlign" type="{http://xmlns.oracle.com/Forms}PromptAlignType" />
 *       &lt;attribute name="PromptAlignOffset" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="PromptAttachmentEdge" type="{http://xmlns.oracle.com/Forms}PromptAttachmentEdgeType" />
 *       &lt;attribute name="PromptAttachmentOffset" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="PromptBackColor" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="PromptDisplayStyle" type="{http://xmlns.oracle.com/Forms}PromptDisplayStyleType" />
 *       &lt;attribute name="PromptFillPattern" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="PromptFontName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="PromptFontSize" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="PromptFontSpacing" type="{http://xmlns.oracle.com/Forms}PromptFontSpacingType" />
 *       &lt;attribute name="PromptFontStyle" type="{http://xmlns.oracle.com/Forms}PromptFontStyleType" />
 *       &lt;attribute name="PromptFontWeight" type="{http://xmlns.oracle.com/Forms}PromptFontWeightType" />
 *       &lt;attribute name="PromptForegroundColor" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="PromptJustification" type="{http://xmlns.oracle.com/Forms}PromptJustificationType" />
 *       &lt;attribute name="PromptReadingOrder" type="{http://xmlns.oracle.com/Forms}PromptReadingOrderType" />
 *       &lt;attribute name="PromptVisualAttributeName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="RadioButtonValue" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="SmartClass" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="SubclassSubObject" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="Visible" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="VisualAttributeName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Width" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="XPosition" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="YPosition" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RadioButton", namespace = "http://xmlns.oracle.com/Forms")
public class RadioButton
    extends FormsObject
{

    @XmlAttribute(name = "AccessKey")
    protected String accessKey;
    @XmlAttribute(name = "BackColor")
    protected String backColor;
    @XmlAttribute(name = "Comment")
    protected String comment;
    @XmlAttribute(name = "DirtyInfo")
    protected Boolean dirtyInfo;
    @XmlAttribute(name = "DistanceBetweenRecords")
    protected BigInteger distanceBetweenRecords;
    @XmlAttribute(name = "Enabled")
    protected Boolean enabled;
    @XmlAttribute(name = "FillPattern")
    protected String fillPattern;
    @XmlAttribute(name = "FontName")
    protected String fontName;
    @XmlAttribute(name = "FontSize")
    protected BigInteger fontSize;
    @XmlAttribute(name = "FontSpacing")
    protected String fontSpacing;
    @XmlAttribute(name = "FontStyle")
    protected String fontStyle;
    @XmlAttribute(name = "FontWeight")
    protected String fontWeight;
    @XmlAttribute(name = "ForegroundColor")
    protected String foregroundColor;
    @XmlAttribute(name = "Height")
    protected BigInteger height;
    @XmlAttribute(name = "Label")
    protected String label;
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
    @XmlAttribute(name = "ParentSourceLevel1ObjectName")
    protected String parentSourceLevel1ObjectName;
    @XmlAttribute(name = "ParentSourceLevel1ObjectType")
    protected BigInteger parentSourceLevel1ObjectType;
    @XmlAttribute(name = "ParentSourceLevel2ObjectName")
    protected String parentSourceLevel2ObjectName;
    @XmlAttribute(name = "ParentSourceLevel2ObjectType")
    protected BigInteger parentSourceLevel2ObjectType;
    @XmlAttribute(name = "ParentType")
    protected BigInteger parentType;
    @XmlAttribute(name = "PersistentClientInfoLength")
    protected BigInteger persistentClientInfoLength;
    @XmlAttribute(name = "Prompt")
    protected String prompt;
    @XmlAttribute(name = "PromptAlign")
    protected String promptAlign;
    @XmlAttribute(name = "PromptAlignOffset")
    protected BigInteger promptAlignOffset;
    @XmlAttribute(name = "PromptAttachmentEdge")
    protected String promptAttachmentEdge;
    @XmlAttribute(name = "PromptAttachmentOffset")
    protected BigInteger promptAttachmentOffset;
    @XmlAttribute(name = "PromptBackColor")
    protected String promptBackColor;
    @XmlAttribute(name = "PromptDisplayStyle")
    protected String promptDisplayStyle;
    @XmlAttribute(name = "PromptFillPattern")
    protected String promptFillPattern;
    @XmlAttribute(name = "PromptFontName")
    protected String promptFontName;
    @XmlAttribute(name = "PromptFontSize")
    protected BigInteger promptFontSize;
    @XmlAttribute(name = "PromptFontSpacing")
    protected String promptFontSpacing;
    @XmlAttribute(name = "PromptFontStyle")
    protected String promptFontStyle;
    @XmlAttribute(name = "PromptFontWeight")
    protected String promptFontWeight;
    @XmlAttribute(name = "PromptForegroundColor")
    protected String promptForegroundColor;
    @XmlAttribute(name = "PromptJustification")
    protected String promptJustification;
    @XmlAttribute(name = "PromptReadingOrder")
    protected String promptReadingOrder;
    @XmlAttribute(name = "PromptVisualAttributeName")
    protected String promptVisualAttributeName;
    @XmlAttribute(name = "RadioButtonValue")
    protected String radioButtonValue;
    @XmlAttribute(name = "SmartClass")
    protected Boolean smartClass;
    @XmlAttribute(name = "SubclassSubObject")
    protected Boolean subclassSubObject;
    @XmlAttribute(name = "Visible")
    protected Boolean visible;
    @XmlAttribute(name = "VisualAttributeName")
    protected String visualAttributeName;
    @XmlAttribute(name = "Width")
    protected BigInteger width;
    @XmlAttribute(name = "XPosition")
    protected BigInteger xPosition;
    @XmlAttribute(name = "YPosition")
    protected BigInteger yPosition;

    /**
     * Gets the value of the accessKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccessKey() {
        return accessKey;
    }

    /**
     * Sets the value of the accessKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccessKey(String value) {
        this.accessKey = value;
    }

    /**
     * Gets the value of the backColor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBackColor() {
        return backColor;
    }

    /**
     * Sets the value of the backColor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBackColor(String value) {
        this.backColor = value;
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
     * Gets the value of the distanceBetweenRecords property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getDistanceBetweenRecords() {
        return distanceBetweenRecords;
    }

    /**
     * Sets the value of the distanceBetweenRecords property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setDistanceBetweenRecords(BigInteger value) {
        this.distanceBetweenRecords = value;
    }

    /**
     * Gets the value of the enabled property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets the value of the enabled property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEnabled(Boolean value) {
        this.enabled = value;
    }

    /**
     * Gets the value of the fillPattern property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFillPattern() {
        return fillPattern;
    }

    /**
     * Sets the value of the fillPattern property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFillPattern(String value) {
        this.fillPattern = value;
    }

    /**
     * Gets the value of the fontName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFontName() {
        return fontName;
    }

    /**
     * Sets the value of the fontName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFontName(String value) {
        this.fontName = value;
    }

    /**
     * Gets the value of the fontSize property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getFontSize() {
        return fontSize;
    }

    /**
     * Sets the value of the fontSize property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setFontSize(BigInteger value) {
        this.fontSize = value;
    }

    /**
     * Gets the value of the fontSpacing property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFontSpacing() {
        return fontSpacing;
    }

    /**
     * Sets the value of the fontSpacing property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFontSpacing(String value) {
        this.fontSpacing = value;
    }

    /**
     * Gets the value of the fontStyle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFontStyle() {
        return fontStyle;
    }

    /**
     * Sets the value of the fontStyle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFontStyle(String value) {
        this.fontStyle = value;
    }

    /**
     * Gets the value of the fontWeight property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFontWeight() {
        return fontWeight;
    }

    /**
     * Sets the value of the fontWeight property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFontWeight(String value) {
        this.fontWeight = value;
    }

    /**
     * Gets the value of the foregroundColor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getForegroundColor() {
        return foregroundColor;
    }

    /**
     * Sets the value of the foregroundColor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setForegroundColor(String value) {
        this.foregroundColor = value;
    }

    /**
     * Gets the value of the height property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getHeight() {
        return height;
    }

    /**
     * Sets the value of the height property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setHeight(BigInteger value) {
        this.height = value;
    }

    /**
     * Gets the value of the label property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the value of the label property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLabel(String value) {
        this.label = value;
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
     * Gets the value of the parentSourceLevel1ObjectName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentSourceLevel1ObjectName() {
        return parentSourceLevel1ObjectName;
    }

    /**
     * Sets the value of the parentSourceLevel1ObjectName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentSourceLevel1ObjectName(String value) {
        this.parentSourceLevel1ObjectName = value;
    }

    /**
     * Gets the value of the parentSourceLevel1ObjectType property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getParentSourceLevel1ObjectType() {
        return parentSourceLevel1ObjectType;
    }

    /**
     * Sets the value of the parentSourceLevel1ObjectType property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setParentSourceLevel1ObjectType(BigInteger value) {
        this.parentSourceLevel1ObjectType = value;
    }

    /**
     * Gets the value of the parentSourceLevel2ObjectName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentSourceLevel2ObjectName() {
        return parentSourceLevel2ObjectName;
    }

    /**
     * Sets the value of the parentSourceLevel2ObjectName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentSourceLevel2ObjectName(String value) {
        this.parentSourceLevel2ObjectName = value;
    }

    /**
     * Gets the value of the parentSourceLevel2ObjectType property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getParentSourceLevel2ObjectType() {
        return parentSourceLevel2ObjectType;
    }

    /**
     * Sets the value of the parentSourceLevel2ObjectType property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setParentSourceLevel2ObjectType(BigInteger value) {
        this.parentSourceLevel2ObjectType = value;
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
     * Gets the value of the prompt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrompt() {
        return prompt;
    }

    /**
     * Sets the value of the prompt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrompt(String value) {
        this.prompt = value;
    }

    /**
     * Gets the value of the promptAlign property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPromptAlign() {
        return promptAlign;
    }

    /**
     * Sets the value of the promptAlign property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPromptAlign(String value) {
        this.promptAlign = value;
    }

    /**
     * Gets the value of the promptAlignOffset property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPromptAlignOffset() {
        return promptAlignOffset;
    }

    /**
     * Sets the value of the promptAlignOffset property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPromptAlignOffset(BigInteger value) {
        this.promptAlignOffset = value;
    }

    /**
     * Gets the value of the promptAttachmentEdge property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPromptAttachmentEdge() {
        return promptAttachmentEdge;
    }

    /**
     * Sets the value of the promptAttachmentEdge property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPromptAttachmentEdge(String value) {
        this.promptAttachmentEdge = value;
    }

    /**
     * Gets the value of the promptAttachmentOffset property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPromptAttachmentOffset() {
        return promptAttachmentOffset;
    }

    /**
     * Sets the value of the promptAttachmentOffset property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPromptAttachmentOffset(BigInteger value) {
        this.promptAttachmentOffset = value;
    }

    /**
     * Gets the value of the promptBackColor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPromptBackColor() {
        return promptBackColor;
    }

    /**
     * Sets the value of the promptBackColor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPromptBackColor(String value) {
        this.promptBackColor = value;
    }

    /**
     * Gets the value of the promptDisplayStyle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPromptDisplayStyle() {
        return promptDisplayStyle;
    }

    /**
     * Sets the value of the promptDisplayStyle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPromptDisplayStyle(String value) {
        this.promptDisplayStyle = value;
    }

    /**
     * Gets the value of the promptFillPattern property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPromptFillPattern() {
        return promptFillPattern;
    }

    /**
     * Sets the value of the promptFillPattern property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPromptFillPattern(String value) {
        this.promptFillPattern = value;
    }

    /**
     * Gets the value of the promptFontName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPromptFontName() {
        return promptFontName;
    }

    /**
     * Sets the value of the promptFontName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPromptFontName(String value) {
        this.promptFontName = value;
    }

    /**
     * Gets the value of the promptFontSize property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPromptFontSize() {
        return promptFontSize;
    }

    /**
     * Sets the value of the promptFontSize property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPromptFontSize(BigInteger value) {
        this.promptFontSize = value;
    }

    /**
     * Gets the value of the promptFontSpacing property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPromptFontSpacing() {
        return promptFontSpacing;
    }

    /**
     * Sets the value of the promptFontSpacing property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPromptFontSpacing(String value) {
        this.promptFontSpacing = value;
    }

    /**
     * Gets the value of the promptFontStyle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPromptFontStyle() {
        return promptFontStyle;
    }

    /**
     * Sets the value of the promptFontStyle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPromptFontStyle(String value) {
        this.promptFontStyle = value;
    }

    /**
     * Gets the value of the promptFontWeight property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPromptFontWeight() {
        return promptFontWeight;
    }

    /**
     * Sets the value of the promptFontWeight property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPromptFontWeight(String value) {
        this.promptFontWeight = value;
    }

    /**
     * Gets the value of the promptForegroundColor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPromptForegroundColor() {
        return promptForegroundColor;
    }

    /**
     * Sets the value of the promptForegroundColor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPromptForegroundColor(String value) {
        this.promptForegroundColor = value;
    }

    /**
     * Gets the value of the promptJustification property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPromptJustification() {
        return promptJustification;
    }

    /**
     * Sets the value of the promptJustification property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPromptJustification(String value) {
        this.promptJustification = value;
    }

    /**
     * Gets the value of the promptReadingOrder property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPromptReadingOrder() {
        return promptReadingOrder;
    }

    /**
     * Sets the value of the promptReadingOrder property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPromptReadingOrder(String value) {
        this.promptReadingOrder = value;
    }

    /**
     * Gets the value of the promptVisualAttributeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPromptVisualAttributeName() {
        return promptVisualAttributeName;
    }

    /**
     * Sets the value of the promptVisualAttributeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPromptVisualAttributeName(String value) {
        this.promptVisualAttributeName = value;
    }

    /**
     * Gets the value of the radioButtonValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRadioButtonValue() {
        return radioButtonValue;
    }

    /**
     * Sets the value of the radioButtonValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRadioButtonValue(String value) {
        this.radioButtonValue = value;
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
     * Gets the value of the visible property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isVisible() {
        return visible;
    }

    /**
     * Sets the value of the visible property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setVisible(Boolean value) {
        this.visible = value;
    }

    /**
     * Gets the value of the visualAttributeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVisualAttributeName() {
        return visualAttributeName;
    }

    /**
     * Sets the value of the visualAttributeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVisualAttributeName(String value) {
        this.visualAttributeName = value;
    }

    /**
     * Gets the value of the width property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getWidth() {
        return width;
    }

    /**
     * Sets the value of the width property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setWidth(BigInteger value) {
        this.width = value;
    }

    /**
     * Gets the value of the xPosition property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getXPosition() {
        return xPosition;
    }

    /**
     * Sets the value of the xPosition property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setXPosition(BigInteger value) {
        this.xPosition = value;
    }

    /**
     * Gets the value of the yPosition property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getYPosition() {
        return yPosition;
    }

    /**
     * Sets the value of the yPosition property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setYPosition(BigInteger value) {
        this.yPosition = value;
    }

}
