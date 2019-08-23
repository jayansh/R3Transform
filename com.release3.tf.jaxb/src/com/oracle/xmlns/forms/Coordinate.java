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
 * <p>Java class for Coordinate complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Coordinate">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="CharacterCellHeight" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="CharacterCellWidth" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="CoordinateSystem" type="{http://xmlns.oracle.com/Forms}CoordinateSystemType" />
 *       &lt;attribute name="DefaultFontScaling" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="RealUnit" type="{http://xmlns.oracle.com/Forms}RealUnitType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Coordinate", namespace = "http://xmlns.oracle.com/Forms")
public class Coordinate
    extends FormsObject
{

    @XmlAttribute(name = "CharacterCellHeight")
    protected BigInteger characterCellHeight;
    @XmlAttribute(name = "CharacterCellWidth")
    protected BigInteger characterCellWidth;
    @XmlAttribute(name = "CoordinateSystem")
    protected String coordinateSystem;
    @XmlAttribute(name = "DefaultFontScaling")
    protected Boolean defaultFontScaling;
    @XmlAttribute(name = "RealUnit")
    protected String realUnit;

    /**
     * Gets the value of the characterCellHeight property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCharacterCellHeight() {
        return characterCellHeight;
    }

    /**
     * Sets the value of the characterCellHeight property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCharacterCellHeight(BigInteger value) {
        this.characterCellHeight = value;
    }

    /**
     * Gets the value of the characterCellWidth property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCharacterCellWidth() {
        return characterCellWidth;
    }

    /**
     * Sets the value of the characterCellWidth property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCharacterCellWidth(BigInteger value) {
        this.characterCellWidth = value;
    }

    /**
     * Gets the value of the coordinateSystem property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCoordinateSystem() {
        return coordinateSystem;
    }

    /**
     * Sets the value of the coordinateSystem property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCoordinateSystem(String value) {
        this.coordinateSystem = value;
    }

    /**
     * Gets the value of the defaultFontScaling property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDefaultFontScaling() {
        return defaultFontScaling;
    }

    /**
     * Sets the value of the defaultFontScaling property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDefaultFontScaling(Boolean value) {
        this.defaultFontScaling = value;
    }

    /**
     * Gets the value of the realUnit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRealUnit() {
        return realUnit;
    }

    /**
     * Sets the value of the realUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRealUnit(String value) {
        this.realUnit = value;
    }

}
