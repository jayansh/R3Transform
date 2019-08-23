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
package com.release3.cleanup;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import org.xml.sax.SAXException;

public class CleanupBlockTask extends Task {

    private static final String TAG_BLOCK = "Block";

    private static final String ATTRIB_NAME = "Name";

    private static final String ATTRIB_DB_BLOCK = "DatabaseBlock";

    private static final String ATTRIB_DATA_NAME = "DMLDataName";
    
    private static final String ATTRIB_DATA_QUERY_SOURCE_NAME = "QueryDataSourceName";

    private static Logger logger = Logger.getLogger(CleanupBlockTask.class);

    private String fileUrl;

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public void execute() throws BuildException {

        if (fileUrl == null) {
            throw new BuildException("fileUrl has to be specified");
        }

        Document doc = loadDocument(fileUrl);

        NodeList blocks = doc.getElementsByTagName(TAG_BLOCK);
        Element blockElement = null;

        String databaseBlockNameAttr = null;
        String databaseBlockAttr = null;
        String dataNameAttr = null;
        
        String dataQuerySourcenameAttr = null;
        

        StringBuilder logMessage = new StringBuilder();
        for (int i = 0, length = blocks.getLength(); i < length; i++) {

            logMessage.setLength(0);

            blockElement = (Element)blocks.item(i);
           databaseBlockAttr = blockElement.getAttribute(ATTRIB_DB_BLOCK);
            dataNameAttr = blockElement.getAttribute(ATTRIB_DATA_NAME);
            dataQuerySourcenameAttr = blockElement.getAttribute(ATTRIB_DATA_QUERY_SOURCE_NAME);
            
           boolean hasModified = false;

            if ((dataQuerySourcenameAttr == null || dataQuerySourcenameAttr.length() == 0 ) && ((dataNameAttr == null) || (dataNameAttr.length() == 0))) {
                if (!"false".equalsIgnoreCase(databaseBlockAttr)) {
                    blockElement.setAttribute(ATTRIB_DB_BLOCK, "false");
                    hasModified = true;
               }
            } else {
                if (!"true".equalsIgnoreCase(databaseBlockAttr)) {
                    blockElement.setAttribute(ATTRIB_DB_BLOCK, "true");
                    hasModified = true;
               }
            }

            // logging
            if (hasModified) {

                databaseBlockNameAttr = blockElement.getAttribute(ATTRIB_NAME);
 

                logMessage.append("Original {");
                logMessage.append(databaseBlockNameAttr);
                logMessage.append(", ");
                logMessage.append(databaseBlockAttr).append(", ");
                logMessage.append(dataNameAttr).append("} ");

                logMessage.append("Modified {");
                logMessage.append(databaseBlockNameAttr);
                logMessage.append(", ");
                logMessage.append(blockElement.getAttribute(ATTRIB_DB_BLOCK)).append(", ");
                logMessage.append(blockElement.getAttribute(ATTRIB_DATA_NAME)).append("}");

            }
        }

        saveDocument(doc, fileUrl);
    }

    private Document loadDocument(String url) throws BuildException {

        DocumentBuilderFactory domFactory = 
            DocumentBuilderFactory.newInstance();
        domFactory.setNamespaceAware(true);

        DocumentBuilder builder = null;
        try {
            builder = domFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new BuildException(e);
        }

        if (builder == null) {
            return null;
        }

        Document doc = null;
        try {
            doc = builder.parse(url);
        } catch (SAXException e) {
            throw new BuildException("Unable to load document", e);
        } catch (IOException e) {
            throw new BuildException("Unable to load document", e);
        }

        return doc;
    }

    private void saveDocument(Document doc, String url) throws BuildException {

        Source source = new DOMSource(doc);

        File file = new File(url);
        Result result = new StreamResult(file);

        try {
            Transformer xformer = 
                TransformerFactory.newInstance().newTransformer();
            xformer.transform(source, result);
        } catch (TransformerConfigurationException e) {
            throw new BuildException("Unable to save document", e);
        } catch (TransformerException e) {
            throw new BuildException("Unable to save document", e);
        }
    }

}
