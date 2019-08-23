package com.release3.transform.rule;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URL;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.sax.SAXSource;

import org.eclipse.core.runtime.Platform;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.oracle.xmlns.forms.Block;
import com.release3.transform.rules.model.ObjectFactory;
import com.release3.transform.rules.model.Rules;
import com.release3.transform.rules.model.Rules.DatabaseBlockRule;

public class RuleHandling {
	private JAXBElement<?> poElement;
	private Unmarshaller u;
	private Marshaller m;
	private JAXBContext jc;
	private Rules rules;

	public InputStream readXmlFile() throws IOException {
		URL url = Platform.getBundle(Activator.PLUGIN_ID).getEntry("Rules.xml");
		InputStream inputStream = url.openStream();

//		final char[] buffer = new char[0x10000];
//		StringBuilder out = new StringBuilder();
//		Reader in = new InputStreamReader(inputStream, "UTF-8");
//		int read;
//		do {
//			read = in.read(buffer, 0, buffer.length);
//			if (read > 0) {
//				out.append(buffer, 0, read);
//			}
//		} while (read >= 0);
//
//		System.out.println(out);

		return inputStream;
	}

	public Rules init() {

		try {
			// create a JAXBContext capable of handling classes generated into
			// the primer.po package
			jc = JAXBContext.newInstance("com.release3.transform.rules.model");

			// create an Unmarshaller
			u = jc.createUnmarshaller();

			// Create an XMLReader to use with our filter
			XMLReader reader = XMLReaderFactory.createXMLReader();

			// Create the filter (to add namespace) and set the xmlReader as its
			// parent.
			NamespaceFilter inFilter = new NamespaceFilter(
					"http://www.release3.com/rules", true);
			try {
				inFilter
						.startPrefixMapping("tns", "http://www.release3.com/rules");
			} catch (SAXException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			inFilter.setParent(reader);

			// unmarshal a po instance document into a tree of Java content
			// objects composed of classes from the primer.po package.
			// InputStream io = this.getClass().getResourceAsStream(resource);

			// File f = new File(xmlFile);
			// InputStream inStream = new FileInputStream(f);
			// Prepare the input, in this case a java.io.File (output)
			InputStream in = readXmlFile();
			InputSource is = new InputSource(in);

			// Create a SAXSource specifying the filter
			SAXSource source = new SAXSource(inFilter, is);

			// Do unmarshalling
			rules = (Rules) u.unmarshal(source);
			
			return rules;
		} catch (Exception e) {
			
			return null;
		}

	}

	public void save(String xmlFile) throws JAXBException,
			FileNotFoundException, UnsupportedEncodingException {
		m = jc.createMarshaller();
		File outFile = new File(xmlFile);
		

		FileOutputStream fout = new FileOutputStream(outFile);
		Writer writer = new OutputStreamWriter(fout, "ISO-8859-1");
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

	public void applyBlockTypeAsPerRule(Block block) {
		String queryDataSourceType = block.getQueryDataSourceType();
		boolean isHasQueryDataSource = false;
		if (block.getQueryDataSourceName() != null && block.getQueryDataSourceName().length()>0) {
			isHasQueryDataSource = true;
		}
		if (!queryDataSourceType.equals("Table")
				|| !(queryDataSourceType == "Table")) {
			queryDataSourceType = "Other";
		}
		List<Rules.DatabaseBlockRule> dbRuleList = rules.getDatabaseBlockRule();
		for (DatabaseBlockRule databaseBlockRule : dbRuleList) {
			if (block.isDatabaseBlock() == databaseBlockRule.isIsDBBlockType()
					&& isHasQueryDataSource == databaseBlockRule
							.isHasQueryDataSourceName()
					&& (queryDataSourceType.equals(databaseBlockRule
							.getQueryDataSourceType()) || (queryDataSourceType == databaseBlockRule
							.getQueryDataSourceType()))) {
			 boolean val =	(databaseBlockRule.getResult()
						.equals("DBBlock") || databaseBlockRule
						.getResult() == "DBBlock");
				block
						.setDatabaseBlock(val);
				break;
			}
		}

	}

	public static void main(String args[]) {
		// RuleHandling ruleHandling = new RuleHandling();
		// ruleHandling.readXmlRules();

		try {
			JAXBContext context = JAXBContext.newInstance(Rules.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			ObjectFactory objFactory = new ObjectFactory();
			Rules rules = objFactory.createRules();
			Rules.DatabaseBlockRule dbRule = objFactory
					.createRulesDatabaseBlockRule();
			dbRule.setIsDBBlockType(false);
			dbRule.setHasQueryDataSourceName(false);
			dbRule.setQueryDataSourceType("Other");
			dbRule.setResult("NonDBBlock");
			dbRule.setName("Rule1");
			rules.getDatabaseBlockRule().add(dbRule);

			marshaller.marshal(rules, new FileWriter("c:\\Person.xml"));
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
