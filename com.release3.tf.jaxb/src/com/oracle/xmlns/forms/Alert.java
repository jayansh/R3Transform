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
 * <p>Java class for Alert complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Alert">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="AlertMessage" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="AlertStyle" type="{http://xmlns.oracle.com/Forms}AlertStyleType" />
 *       &lt;attribute name="BackColor" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Button1Label" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Button2Label" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Button3Label" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Comment" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="DefaultAlertButton" type="{http://xmlns.oracle.com/Forms}DefaultAlertButtonType" />
 *       &lt;attribute name="DirtyInfo" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="FillPattern" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="FontName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="FontSize" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="FontSpacing" type="{http://xmlns.oracle.com/Forms}FontSpacingType" />
 *       &lt;attribute name="FontStyle" type="{http://xmlns.oracle.com/Forms}FontStyleType" />
 *       &lt;attribute name="FontWeight" type="{http://xmlns.oracle.com/Forms}FontWeightType" />
 *       &lt;attribute name="ForegroundColor" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="LanguageDirection" type="{http://xmlns.oracle.com/Forms}LanguageDirectionType" />
 *       &lt;attribute name="Name" use="required" type="{http://xmlns.oracle.com/Forms}nameType" />
 *       &lt;attribute name="ParentFilename" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ParentFilepath" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ParentModule" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ParentModuleType" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="ParentName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ParentType" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="PersistentClientInfoLength" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="SmartClass" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="SubclassObjectGroup" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="Title" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="VisualAttributeName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Alert", namespace = "http://xmlns.oracle.com/Forms")
public class Alert
    extends FormsObject
{

    @XmlAttribute(name = "AlertMessage")
    protected String alertMessage;
    @XmlAttribute(name = "AlertStyle")
    protected String alertStyle;
    @XmlAttribute(name = "BackColor")
    protected String backColor;
    @XmlAttribute(name = "Button1Label")
    protected String button1Label;
    @XmlAttribute(name = "Button2Label")
    protected String button2Label;
    @XmlAttribute(name = "Button3Label")
    protected String button3Label;
    @XmlAttribute(name = "Comment")
    protected String comment;
    @XmlAttribute(name = "DefaultAlertButton")
    protected String defaultAlertButton;
    @XmlAttribute(name = "DirtyInfo")
    protected Boolean dirtyInfo;
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
    @XmlAttribute(name = "LanguageDirection")
    protected String languageDirection;
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
    @XmlAttribute(name = "SmartClass")
    protected Boolean smartClass;
    @XmlAttribute(name = "SubclassObjectGroup")
    protected Boolean subclassObjectGroup;
    @XmlAttribute(name = "Title")
    protected String title;
    @XmlAttribute(name = "VisualAttributeName")
    protected String visualAttributeName;

    /**
     * Gets the value of the alertMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlertMessage() {
        return alertMessage;
    }

    /**
     * Sets the value of the alertMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlertMessage(String value) {
        this.alertMessage = value;
    }

    /**
     * Gets the value of the alertStyle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlertStyle() {
        return alertStyle;
    }

    /**
     * Sets the value of the alertStyle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlertStyle(String value) {
        this.alertStyle = value;
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
     * Gets the value of the button1Label property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getButton1Label() {
        return button1Label;
    }

    /**
     * Sets the value of the button1Label property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setButton1Label(String value) {
        this.button1Label = value;
    }

    /**
     * Gets the value of the button2Label property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getButton2Label() {
        return button2Label;
    }

    /**
     * Sets the value of the button2Label property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setButton2Label(String value) {
        this.button2Label = value;
    }

    /**
     * Gets the value of the button3Label property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getButton3Label() {
        return button3Label;
    }

    /**
     * Sets the value of the button3Label property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setButton3Label(String value) {
        this.button3Label = value;
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
     * Gets the value of the defaultAlertButton property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefaultAlertButton() {
        return defaultAlertButton;
    }

    /**
     * Sets the value of the defaultAlertButton property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefaultAlertButton(String value) {
        this.defaultAlertButton = value;
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
     * Gets the value of the languageDirection property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLanguageDirection() {
        return languageDirection;
    }

    /**
     * Sets the value of the languageDirection property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLanguageDirection(String value) {
        this.languageDirection = value;
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

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
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

}
