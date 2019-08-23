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
package com.release3.tf.plsql.process;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class PlSqlProcessPlugin extends AbstractUIPlugin implements
		BundleActivator {
	// The plug-in ID
	public static final String PLUGIN_ID = "com.release3.tf.plsql.process";

	private static BundleContext context;

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
		PlSqlProcessPlugin.context = bundleContext;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		PlSqlProcessPlugin.context = null;
	}

	public static String[] CRUD_PROCESSING_PAGE_TITLE = { "Block", "Item",
			"Trigger Name", "Pl/Sql Text", "Migrate Y/N" ,"Move to DB(Unchecked Moves to Java)"};
	public static int[] CRUD_PROCESSING_PAGE_BOUND = {90,90,110,600,60,90};
	public static String[] NON_CRUD_PROCESSING_PAGE_TITLE = { "Block", "Item",
			"Trigger Name", "Pl/Sql Text", "Migrate Y/N", "Move to DB(Unchecked Moves to Java)" ,"Java Options"};
	public static String[] PU_PROCESSING_PAGE_TITLE = { 
		"ProgramUnit Name", "Pl/Sql Text", "Migrate Y/N","Move to DB(Unchecked Moves to Java)","Java Options" };
	/**
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path
	 * 
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
}
