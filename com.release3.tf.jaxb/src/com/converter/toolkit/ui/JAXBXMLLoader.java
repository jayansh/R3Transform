package com.converter.toolkit.ui;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.transform.sax.SAXSource;

import org.eclipse.core.runtime.Platform;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.release3.tf.jaxb.Activator;
import com.release3.triggerlist.TriggerList;

public class JAXBXMLLoader {
	JAXBXMLReader reader = new JAXBXMLReader();
	private TriggerList triggerList;

	private static JAXBXMLLoader loader;

	private JAXBXMLLoader() {

	}

	public static JAXBXMLLoader getInstance() {
		if (loader == null) {
			loader = new JAXBXMLLoader();
		}
		return loader;
	}

	public void loadTriggerList() {
		try {
			triggerList = (TriggerList) reader.init(
					readXmlFile("triggers/TriggerList.xml"),
					"http://www.release3.com/TriggerList", TriggerList.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public InputStream readXmlFile(String xmlFile) throws IOException {
		URL url = Platform.getBundle(Activator.PLUGIN_ID).getEntry(xmlFile);
		InputStream inputStream = url.openStream();

		return inputStream;
	}

	public TriggerList getTriggerList() {
		if (triggerList == null) {
			loadTriggerList();
		}
		return triggerList;
	}

}
