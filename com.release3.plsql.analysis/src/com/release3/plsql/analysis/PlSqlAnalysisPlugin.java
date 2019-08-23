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
package com.release3.plsql.analysis;

import java.io.File;

//import org.apache.lucene.analysis.standard.StandardAnalyzer;
//import org.apache.lucene.util.Version;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class PlSqlAnalysisPlugin implements BundleActivator {

	public static final File INDEX_DIR = new File(Platform.getLocation()
			+ File.separator + "Lucene" + File.separator + "R3Index");
//	public static final StandardAnalyzer ANALYZER = new StandardAnalyzer(
//			Version.LUCENE_30);
	private static BundleContext context;

	public static String[] rejectedProgramUnits = {"QUERY_MASTER_DETAILS"};
	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext bundleContext) throws Exception {
		PlSqlAnalysisPlugin.context = bundleContext;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		PlSqlAnalysisPlugin.context = null;
	}

	

	
}
