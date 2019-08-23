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
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.05.26 at 12:20:16 AM IST 
//


package com.release3.plsql.analysis.builtins.model;

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
 *         &lt;element name="Builtin" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="builtinName" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *                 &lt;attribute name="propertyName" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *                 &lt;attribute name="propertyValue" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *                 &lt;attribute name="formName" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *                 &lt;attribute name="method" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *                 &lt;attribute name="builtinExpression" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
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
    "builtin"
})
@XmlRootElement(name = "BuiltinProperties")
public class BuiltinProperties {

    @XmlElement(name = "Builtin")
    protected List<BuiltinProperties.Builtin> builtin;

    /**
     * Gets the value of the builtin property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the builtin property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBuiltin().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BuiltinProperties.Builtin }
     * 
     * 
     */
    public List<BuiltinProperties.Builtin> getBuiltin() {
        if (builtin == null) {
            builtin = new ArrayList<BuiltinProperties.Builtin>();
        }
        return this.builtin;
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
     *       &lt;attribute name="builtinName" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
     *       &lt;attribute name="propertyName" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
     *       &lt;attribute name="propertyValue" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
     *       &lt;attribute name="formName" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
     *       &lt;attribute name="method" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
     *       &lt;attribute name="builtinExpression" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Builtin {

        @XmlAttribute(name = "builtinName", required = true)
        @XmlSchemaType(name = "anySimpleType")
        protected String builtinName;
        @XmlAttribute(name = "propertyName", required = true)
        @XmlSchemaType(name = "anySimpleType")
        protected String propertyName;
        @XmlAttribute(name = "propertyValue")
        @XmlSchemaType(name = "anySimpleType")
        protected String propertyValue;
        @XmlAttribute(name = "formName", required = true)
        @XmlSchemaType(name = "anySimpleType")
        protected String formName;
        @XmlAttribute(name = "method")
        @XmlSchemaType(name = "anySimpleType")
        protected String method;
        @XmlAttribute(name = "builtinExpression", required = true)
        @XmlSchemaType(name = "anySimpleType")
        protected String builtinExpression;

        /**
         * Gets the value of the builtinName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBuiltinName() {
            return builtinName;
        }

        /**
         * Sets the value of the builtinName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBuiltinName(String value) {
            this.builtinName = value;
        }

        /**
         * Gets the value of the propertyName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPropertyName() {
            return propertyName;
        }

        /**
         * Sets the value of the propertyName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPropertyName(String value) {
            this.propertyName = value;
        }

        /**
         * Gets the value of the propertyValue property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPropertyValue() {
            return propertyValue;
        }

        /**
         * Sets the value of the propertyValue property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPropertyValue(String value) {
            this.propertyValue = value;
        }

        /**
         * Gets the value of the formName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFormName() {
            return formName;
        }

        /**
         * Sets the value of the formName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFormName(String value) {
            this.formName = value;
        }

        /**
         * Gets the value of the method property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMethod() {
            return method;
        }

        /**
         * Sets the value of the method property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMethod(String value) {
            this.method = value;
        }

        /**
         * Gets the value of the builtinExpression property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBuiltinExpression() {
            return builtinExpression;
        }

        /**
         * Sets the value of the builtinExpression property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBuiltinExpression(String value) {
            this.builtinExpression = value;
        }

    }

}
