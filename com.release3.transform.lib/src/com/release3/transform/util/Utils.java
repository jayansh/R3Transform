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
package com.release3.transform.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
/**
 * Moved to com.release3.tf.core plugin
 * @author Jayansh Shinde
 *
 */
@Deprecated
public class Utils {

	public static void copyFile(File in, File out) throws IOException {
		FileChannel inChannel = new FileInputStream(in).getChannel();
		FileChannel outChannel = new FileOutputStream(out).getChannel();
		try {
			inChannel.transferTo(0, inChannel.size(), outChannel);
		} catch (IOException e) {
			throw e;
		} finally {
			if (inChannel != null)
				inChannel.close();
			if (outChannel != null)
				outChannel.close();
		}
	}

	public static List<String> getTokens(String strLine, String delim) {
		List<String> tokens = new ArrayList<String>();
		StringTokenizer strTokenizer = new StringTokenizer(strLine, delim);
		while (strTokenizer.hasMoreTokens()) {
			String token = strTokenizer.nextToken().trim();
			tokens.add(token);
		}
		return tokens;
	}
}
