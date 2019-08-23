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
package com.release3.tf.plsql.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBException;

import com.converter.toolkit.ui.JAXBXMLWriter;
import com.release3.formskeywords.KeywordType;
import com.release3.formskeywords.ReservedListXmlLoader;
import com.release3.formsmap.R3FormsMap;
import com.release3.formsmap.R3FormsMapFactory;
import com.release3.formsmap.R3FormsMap.Rec;
import com.release3.r3builtin.builtinlist.BuiltinListXmlLoader;
import com.release3.r3builtin.builtinlist.BuiltinsMap;
import com.release3.r3builtin.builtinlist.R3Builtlist;
import com.release3.tf.core.form.AbstractForm;
import com.release3.tf.migration.model.MigrationForm;

public class ProcFuncParser {
	public final String DEFAULT_REGEXP = "([a-zA-Z0-9_]+)([\\s]*)([(][['|\"]*a-zA-Z0-9_-['|\"]*]*[,[\\s]*['|\"]*a-zA-Z0-9_-['|\"]*]*[)])([\\s]*)([;]*)";
	private String regexp = DEFAULT_REGEXP;
	private List<BuiltinsMap> builinsMapList;
	private BuiltinListXmlLoader builinlistXmlloader;
	private ReservedListXmlLoader reservedlistXmlLoader;
	private List<KeywordType> keywordList;
	private AbstractForm migrationForm;
	
	private R3FormsMap r3FormsMap;

	public ProcFuncParser(AbstractForm abstractForm,R3FormsMap r3FormsMap) {
		this.builinlistXmlloader = new BuiltinListXmlLoader();
		this.builinsMapList = ((R3Builtlist) builinlistXmlloader
				.getR3Builtinlist()).getBuiltinAction();
		this.reservedlistXmlLoader = new ReservedListXmlLoader();
		this.keywordList = reservedlistXmlLoader.getR3Reservedlist()
				.getKeyword();
		this.migrationForm = abstractForm;
		
		this.r3FormsMap = r3FormsMap;
		r3FormsMap.setName(abstractForm.getFormName());
	}

	public void process(String code,String associatedWith,String blockName,String itemName) {
		Pattern p = Pattern.compile(regexp, Pattern.CASE_INSENSITIVE);
		Matcher match = p.matcher(code);
		String name;
		int start, end;
		// if (match.Success)
		// {
		// string method = match.Groups["method"].Value;
		// }

		while (match.find()) {

			// The variable's name is stored in group 1.
			start = match.start(1);

			end = match.end(1);

			name = code.substring(start, end);
			// System.out.println("name: "+name);
			String mGrp = match.group(0);
			String mGrp1 = match.group(1);
			if (!isContains(mGrp1)) {
				System.out.println(mGrp);
				System.out.println(mGrp1);
				Rec rec = new Rec();
				rec.setName(mGrp1);
				rec.setType("function/procedure");
				rec.setAssociatedWith(associatedWith);
				rec.setBlockName(blockName);
				rec.setItemName(itemName);
				r3FormsMap.getRec().add(rec);
			}
			// com.release3.r3builtin.builtinlist.ObjectFactory
			// builtinListFactory = new
			// com.release3.r3builtin.builtinlist.ObjectFactory();
		}
		
	}

	public void write(){
		try {
			JAXBXMLWriter.writetoXML(
					migrationForm.getProcFuncCallMapFilePath(), r3FormsMap);
		} catch (JAXBException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		File inputFile = new File(args[0]);
		// ProcFuncParser parser = new ProcFuncParser();
		// parser.parse(inputFile);
	}

	public void parse(File inputFile) {
		int ch;
		StringBuffer strContent = new StringBuffer("");
		try {
			FileInputStream fin = new FileInputStream(inputFile);
			while ((ch = fin.read()) != -1) {
				strContent.append((char) ch);
			}
			fin.close();
			String code = strContent.toString();
			process(code,null,null,null);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean isContains(String text) {
		for (BuiltinsMap builtinMap : builinsMapList) {
			boolean val = builtinMap.getFormBuiltin().equalsIgnoreCase(text);
			if (val) {
				return val;
			}
		}
		for (KeywordType keyword : keywordList) {
			boolean val = keyword.getName().equalsIgnoreCase(text);
			if (val) {
				return val;
			}
		}

		return false;
	}

}
