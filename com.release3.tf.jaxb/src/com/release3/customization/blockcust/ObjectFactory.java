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
// Generated on: 2011.09.05 at 10:07:58 PM IST 
//


package com.release3.customization.blockcust;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.release3.customization.blockcust package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _BlockCust_QNAME = new QName("http://www.release3.com/Customization/BlockCust", "BlockCust");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.release3.customization.blockcust
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link BlockCustType }
     * 
     */
    public BlockCustType createBlockCustType() {
        return new BlockCustType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BlockCustType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.release3.com/Customization/BlockCust", name = "BlockCust")
    public JAXBElement<BlockCustType> createBlockCust(BlockCustType value) {
        return new JAXBElement<BlockCustType>(_BlockCust_QNAME, BlockCustType.class, null, value);
    }

}
