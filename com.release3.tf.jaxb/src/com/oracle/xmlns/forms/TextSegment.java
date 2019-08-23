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
 * <p>Java class for TextSegment complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TextSegment">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="DirtyInfo" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="FontName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="FontSize" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="FontSpacing" type="{http://xmlns.oracle.com/Forms}FontSpacingType" />
 *       &lt;attribute name="FontStyle" type="{http://xmlns.oracle.com/Forms}FontStyleType" />
 *       &lt;attribute name="FontWeight" type="{http://xmlns.oracle.com/Forms}FontWeightType" />
 *       &lt;attribute name="ForegroundColor" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Name" use="required" type="{http://xmlns.oracle.com/Forms}nameType" />
 *       &lt;attribute name="SubclassSubObject" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="Text" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TextSegment", namespace = "http://xmlns.oracle.com/Forms")
public class TextSegment
    extends FormsObject
{

    @XmlAttribute(name = "DirtyInfo")
    protected Boolean dirtyInfo;
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
    @XmlAttribute(name = "Name", required = true)
    protected String name;
    @XmlAttribute(name = "SubclassSubObject")
    protected Boolean subclassSubObject;
    @XmlAttribute(name = "Text")
    protected String text;

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
     * Gets the value of the text property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the value of the text property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setText(String value) {
        this.text = value;
    }

}