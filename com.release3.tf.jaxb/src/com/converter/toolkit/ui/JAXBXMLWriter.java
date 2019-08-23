package com.converter.toolkit.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Iterator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.converter.toolkit.ui.PreferredMapper;

public class JAXBXMLWriter {
	public static void writetoXML(String xmlFilePath, Object obj)
			throws JAXBException, IOException {
		JAXBContext context = JAXBContext.newInstance(obj.getClass());
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(obj, new FileWriter(xmlFilePath));
	}

	public static void write(String xmlFilePath, Object obj)
			throws JAXBException, IOException {
		write(xmlFilePath, obj, "com.oracle.xmlns.forms");
	}

	public static void write(String xmlFilePath, Object obj, String packageNames)
			throws JAXBException, IOException {
		JAXBContext context = JAXBContext.newInstance(packageNames);
		Marshaller marshaller = context.createMarshaller();
		PreferredMapper mapper = new PreferredMapper();
		marshaller
				.setProperty("com.sun.xml.bind.namespacePrefixMapper", mapper);
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		File outFile = new File(xmlFilePath);
		FileOutputStream fout = new FileOutputStream(outFile);

		try {
			// Tell JAXB to marshall to the filter which in turn will call the
			// writer
			Writer writer = new OutputStreamWriter(fout, "UTF-8");
			marshaller.marshal(obj, writer);

		} finally {
			try {
				if (fout != null) {
					fout.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static void writeXmlWithOutNS(String xmlFilePath, Object obj)
			throws JAXBException, IOException {
		JAXBContext context = JAXBContext.newInstance(obj.getClass());
		Marshaller marshaller = context.createMarshaller();
//		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		File outFile = new File(xmlFilePath);
		FileOutputStream fout = new FileOutputStream(outFile);

		try {
			// Tell JAXB to marshall to the filter which in turn will call the
			// writer
			Writer writer = new OutputStreamWriter(fout, "UTF-8");
			XMLStreamWriter xmlStreamWriter = XMLOutputFactory.newInstance()
					.createXMLStreamWriter(writer);
			xmlStreamWriter.setNamespaceContext(new NamespaceContext() {

				@Override
				public String getNamespaceURI(String arg0) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public String getPrefix(String arg0) {
					// TODO Auto-generated method stub
					return "";
				}

				@Override
				public Iterator getPrefixes(String arg0) {
					// TODO Auto-generated method stub
					return null;
				}

			});
			marshaller.marshal(obj, xmlStreamWriter);

		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (fout != null) {
					fout.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
