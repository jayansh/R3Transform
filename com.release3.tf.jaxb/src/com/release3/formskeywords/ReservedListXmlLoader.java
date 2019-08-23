package com.release3.formskeywords;

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
import com.release3.r3builtin.builtinlist.R3Builtlist;
import com.release3.tf.jaxb.Activator;

public class ReservedListXmlLoader {
	private String reservedListXmlFileURL = "/forms/FormsKeywords.xml";
	private JAXBXMLReader reader = new JAXBXMLReader();
	private File xmlFile;
	private R3Reservedlist r3Reservedlist; 

	public ReservedListXmlLoader() {
		Bundle bundle = Activator.getDefault().getBundle();
		IPath path = new Path(reservedListXmlFileURL);
		URL setupUrl = FileLocator.find(bundle, path, Collections.EMPTY_MAP);
		try {
			xmlFile = new File(FileLocator.toFileURL(setupUrl).toURI());
			r3Reservedlist = (R3Reservedlist) reader.init(xmlFile, R3Reservedlist.class);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public R3Reservedlist getR3Reservedlist(){
		return r3Reservedlist;
	}
}
