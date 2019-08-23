package com.release3.migrate;

import java.io.OutputStream;
import java.io.PrintStream;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IOConsole;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.release3.migrate";

	// The shared instance
	private static Activator plugin;

	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;

		// MessageConsole myConsole = new MessageConsole("Migration Console",
		// null);
		// //myConsole.activate();
		// ConsolePlugin.getDefault().getConsoleManager().addConsoles(new
		// IConsole[]{ myConsole });
		// ConsolePlugin.getDefault().getConsoleManager().showConsoleView(myConsole);
		// MessageConsoleStream stream = myConsole.newMessageStream();
		//        
		// System.setOut(new PrintStream(stream));
		// System.setErr(new PrintStream(stream));

//		IOConsole txtConsole = new IOConsole("Migration Console", null);
//		// myConsole.activate();
//		ConsolePlugin.getDefault().getConsoleManager().addConsoles(
//				new IConsole[] { txtConsole });
//		ConsolePlugin.getDefault().getConsoleManager().showConsoleView(
//				txtConsole);
//		
//		OutputStream stream = txtConsole.newOutputStream();
//		
//		System.setOut(new PrintStream(stream));
//		System.setErr(new PrintStream(stream));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
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
	public static Activator getDefault() {
		return plugin;
	}

}
