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
