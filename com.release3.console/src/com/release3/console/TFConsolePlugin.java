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
package com.release3.console;

import java.io.PrintStream;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class TFConsolePlugin extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.release3.console";

	// The shared instance
	private static TFConsolePlugin plugin;
	
	/**
	 * The constructor
	 */
	public TFConsolePlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
//		MessageConsole myConsole = new MessageConsole("Migration Console", null); 
//        //myConsole.activate(); 
//        ConsolePlugin.getDefault().getConsoleManager().addConsoles(new IConsole[]{ myConsole }); 
//        ConsolePlugin.getDefault().getConsoleManager().showConsoleView(myConsole); 
//        MessageConsoleStream stream = myConsole.newMessageStream(); 
//        System.setOut(new PrintStream(stream)); 
//		System.setErr(new PrintStream(stream));
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static TFConsolePlugin getDefault() {
		return plugin;
	}

}
