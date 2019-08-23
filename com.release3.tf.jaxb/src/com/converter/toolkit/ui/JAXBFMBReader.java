package com.converter.toolkit.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.sax.SAXSource;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.converter.toolkit.ui.InvalidXMLCharacterFilterReader;
import com.converter.toolkit.ui.NamespaceFilter;
import com.oracle.xmlns.forms.Module;

public class JAXBFMBReader {
	private JAXBElement<?> poElement;
	private Unmarshaller u;
	private Marshaller m;
	private JAXBContext jc;

	public JAXBFMBReader() {
	}

	public Module init(String xmlFile) {
		try {
			// create a JAXBContext capable of handling classes generated into
			// the primer.po package
			jc = JAXBContext.newInstance("com.oracle.xmlns.forms");

			// create an Unmarshaller
			u = jc.createUnmarshaller();

			// Create an XMLReader to use with our filter
			XMLReader xmlReader = XMLReaderFactory.createXMLReader();

			// Create the filter (to add namespace) and set the xmlReader as its
			// parent.
			NamespaceFilter inFilter = new NamespaceFilter(
					"http://xmlns.oracle.com/Forms", true);
			try {
				inFilter
						.startPrefixMapping("", "http://xmlns.oracle.com/Forms");
			} catch (SAXException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			inFilter.setParent(xmlReader);

			// unmarshal a po instance document into a tree of Java content
			// objects composed of classes from the primer.po package.
			// InputStream io = this.getClass().getResourceAsStream(resource);

			File f = new File(xmlFile);
			InputStream inStream = new FileInputStream(f);
			InputStreamReader inReader = new InputStreamReader(inStream,"UTF-8");
			
			Reader reader = new InvalidXMLCharacterFilterReader(inReader);
			
			// Prepare the input, in this case a java.io.File (output)
			InputSource is = new InputSource(reader);
			
			
			// Create a SAXSource specifying the filter
			SAXSource source = new SAXSource(inFilter, is);

			// Do unmarshalling
			poElement = (JAXBElement<?>) u.unmarshal(source);
			return (Module) poElement.getValue();
		} catch (Exception e) {
			return null;
		}

	}

	

	public void save(String xmlFile) throws JAXBException,
			FileNotFoundException, UnsupportedEncodingException {
		m = jc.createMarshaller();
		File outFile = new File(xmlFile);
		FileOutputStream fout = new FileOutputStream(outFile);
		// Create a filter that will remove the xmlns attribute
		// NamespaceFilter outFilter = new
		// NamespaceFilter("http://xmlns.oracle.com/Forms", true);
		// try {
		// outFilter.startPrefixMapping("", "http://xmlns.oracle.com/Forms");
		// } catch (SAXException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		//		
		//				
		// // Do some formatting, this is obviously optional and may effect
		// // performance
		// OutputFormat format = new OutputFormat();
		// format.setIndent(true);
		// format.setNewlines(true);
		//		
		//		
		//
		// // Create a new org.dom4j.io.XMLWriter that will serve as the
		// // ContentHandler for our filter.
		// XMLWriter writer = new XMLWriter(fout, format);
		//
		// // Attach the writer to the filter
		// outFilter.setContentHandler(writer);

		try {
			// Tell JAXB to marshall to the filter which in turn will call the
			// writer
			Writer writer = new OutputStreamWriter(fout,"UTF-8");
			m.marshal(poElement, writer);

			// m.marshal(poElement, outFile);
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
