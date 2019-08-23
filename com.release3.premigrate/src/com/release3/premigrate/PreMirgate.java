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
package com.release3.premigrate;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.IProduct;
import org.eclipse.core.runtime.Platform;

import com.converter.premigration.FileFilterExt;
import com.converter.premigration.FileInfo;
import com.converter.premigration.ProductInfo;
import com.converter.premigration.UserInfo;
import com.converter.premigration.Xcipher;
import com.converter.toolkit.ui.FormsObject;
import com.release3.tf.core.form.CleanupForm;

/*
 * Use instead of Deprecated com.converter.premigration.PreMigrate 
 */

public class PreMirgate {

	/**
	 * Transform Toolkit Free Source Edition definition file generation
	 * 
	 * @param dir
	 * @throws IOException 
	 */
	public void makeDefinitionFS(File dir) throws IOException {

		Vector<ProductInfo> attr = new Vector<ProductInfo>();
		ProductInfo productInfo = ProductInfo.getProductInfo();
		attr.add(productInfo);
		// CryptInfo
		ByteArrayOutputStream fs = null;
		try {
			fs = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(fs);
			os.writeObject(attr);
			UserInfo userInfo = new UserInfo();
			Vector<UserInfo> attr2 = new Vector<UserInfo>();
			attr2.add(userInfo);
			os.writeObject(attr2);
			os.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		// Encrypt
		Xcipher c = new Xcipher();
		String s = c.EncryptCode(fs.toString(), 0, 0);
		// String s =fs.toString();
		File file;
		FileWriter fw;
		BufferedWriter bw;
		file = new File(dir, "DefinitionInfo.txt");
		fw = new FileWriter(file);
		bw = new BufferedWriter(fw);
		bw.write(s);
		bw.close();
		fw.close();
	}

	@Deprecated
	public void makeDefinition2(File dir) {
		String[] str = { "fmb", "mmb", "pll", "olb" };
		FileFilter filter = new FileFilterExt("*.fmb;*.mmb;*.pll;*.olb", str);

		File[] fileArr = dir.listFiles(filter);
		Vector<FileInfo> attr = new Vector<FileInfo>();
		try {
			for (int i = 0; i < fileArr.length; i++) {
				FileInfo fi = new FileInfo();
				fi.setFilename(fileArr[i].getName());
				fi.setFileSize(fileArr[i].length());
				attr.add(fi);
				invoke(dir.getCanonicalPath(), fi);

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// CryptInfo
		ByteArrayOutputStream fs = null;
		try {
			fs = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(fs);
			os.writeObject(attr);
			UserInfo userInfo = new UserInfo();
			Vector<UserInfo> attr2 = new Vector<UserInfo>();
			attr2.add(userInfo);
			os.writeObject(attr2);
			os.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		// Encrypt
		Xcipher c = new Xcipher();
		String s = c.EncryptCode(fs.toString(), 0, 0);
		// String s =fs.toString();
		File file;
		FileWriter fw;
		BufferedWriter bw;
		try {
			file = new File(dir, "DefinitionInfo.txt");
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			bw.write(s);
			bw.close();
			fw.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	// @Deprecated
	// public void makeDefinition(File dir) {
	//
	// String[] str = { "fmb", "mmb", "pll", "olb" };
	// FileFilter filter = new FileFilterExt("*.fmb;*.mmb;*.pll;*.olb", str);
	//
	// File[] fileArr = dir.listFiles(filter);
	// Vector<FileInfo> attr = new Vector<FileInfo>();
	// try {
	// for (int i = 0; i < fileArr.length; i++) {
	// FileInfo fi = new FileInfo();
	// fi.setFilename(fileArr[i].getName());
	// fi.setFileSize(fileArr[i].length());
	// attr.add(fi);
	// JdapiModule.openModule(fileArr[i]);
	// invoke(fi);
	// Jdapi.shutdown();
	// }
	//
	// } finally {
	// Jdapi.shutdown();
	// }
	//
	// // CryptInfo
	// ByteArrayOutputStream fs = null;
	// try {
	// fs = new ByteArrayOutputStream();
	// ObjectOutputStream os = new ObjectOutputStream(fs);
	// os.writeObject(attr);
	// UserInfo userInfo = new UserInfo();
	// Vector<UserInfo> attr2 = new Vector<UserInfo>();
	// attr2.add(userInfo);
	// os.writeObject(attr2);
	// os.close();
	// } catch (FileNotFoundException ex) {
	// ex.printStackTrace();
	// } catch (IOException ex) {
	// ex.printStackTrace();
	// }
	//
	// // Encrypt
	// Xcipher c = new Xcipher();
	// String s = c.EncryptCode(fs.toString(), 0, 0);
	// // String s =fs.toString();
	// File file;
	// FileWriter fw;
	// BufferedWriter bw;
	// try {
	// file = new File(dir, "DefinitionInfo.txt");
	// fw = new FileWriter(file);
	// bw = new BufferedWriter(fw);
	// bw.write(s);
	// bw.close();
	// fw.close();
	// } catch (IOException ex) {
	// ex.printStackTrace();
	// }
	//
	//
	//
	// }

	// @Deprecated
	// private static void invoke(FileInfo fi) {
	// for (JdapiIterator modules = Jdapi.getModules(); modules.hasNext();) {
	// JdapiModule module = (JdapiModule) modules.next();
	// if (module instanceof FormModule) {
	// FormModule mod = (FormModule) module;
	// for (JdapiIterator blocks = mod.getBlocks(); blocks.hasNext();) {
	// Block blk = (Block) blocks.next();
	// fi.blocks.add(blk.getName());
	// }
	// }
	// }
	// }

	private void invoke(String formPath, FileInfo fi) {
		File formFile = new File(formPath + File.separator + fi.getFilename());
		CleanupForm cleanupForm = new CleanupForm();
		cleanupForm.setForm(formFile);
		cleanupForm.setFormXml(new File(formPath + File.separator
				+ fi.getFilename().replaceFirst(".fmb", "_fmb") + ".xml"));
		cleanupForm.form2xml();
		com.oracle.xmlns.forms.FormModule formModule = cleanupForm.getModule()
				.getFormModule();
		List<FormsObject> formsObjects = formModule.getChildren();
		for (FormsObject formsObject : formsObjects) {
			if (formsObject instanceof com.oracle.xmlns.forms.Block) {
				com.oracle.xmlns.forms.Block block = (com.oracle.xmlns.forms.Block) formsObject;
				String blkName = block.getName();
				if (!(blkName == "R3CONTROLS" || blkName
						.equalsIgnoreCase("R3CONTROLS"))) {
					fi.blocks.add(block.getName());
				}

			}

		}

		System.out.println(fi.getFilename());

	}

	public static void main(String[] args) {

		if (args.length == 0) {
			System.out.print("You have to specifiy path to you FMB files");
		}
		File dir = new File(args[0]);
		(new PreMirgate()).makeDefinition2(dir);
	}
}
