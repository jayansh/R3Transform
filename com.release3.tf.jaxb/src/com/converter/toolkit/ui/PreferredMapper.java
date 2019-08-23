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
package com.converter.toolkit.ui;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

public class PreferredMapper extends NamespacePrefixMapper {

	@Override
	public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
		 
    	if (namespaceUri == null || namespaceUri.equals("")) {
            return "";
        }
        if (namespaceUri.equalsIgnoreCase("http://xmlns.oracle.com/Forms")){
            return null;
        }   
         
        if (namespaceUri.equalsIgnoreCase("http://www.release3.com/plsqlgen")){
            return null;
        } 
        return suggestion;

	}

}
