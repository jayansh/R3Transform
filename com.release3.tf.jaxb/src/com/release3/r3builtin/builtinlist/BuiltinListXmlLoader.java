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
package com.release3.r3builtin.builtinlist;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.osgi.framework.Bundle;

import com.converter.toolkit.ui.JAXBXMLReader;
import com.release3.tf.jaxb.Activator;

public class BuiltinListXmlLoader {
	private String builtinListXmlFileURL = "/builtins/Builtinlist.xml";
	private JAXBXMLReader reader = new JAXBXMLReader();
	private File xmlFile;
	private Object r3Builtlist; 

	public BuiltinListXmlLoader() {
		Bundle bundle = Activator.getDefault().getBundle();
		IPath path = new Path(builtinListXmlFileURL);
		URL setupUrl = FileLocator.find(bundle, path, Collections.EMPTY_MAP);
		try {
			xmlFile = new File(FileLocator.toFileURL(setupUrl).toURI());
			r3Builtlist = reader.init(xmlFile, R3Builtlist.class);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Object getR3Builtinlist(){
		return r3Builtlist;
	}
}
