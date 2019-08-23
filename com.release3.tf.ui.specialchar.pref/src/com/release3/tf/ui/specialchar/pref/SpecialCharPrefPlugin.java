package com.release3.tf.ui.specialchar.pref;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class SpecialCharPrefPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.release3.tf.ui.specialchar.pref"; //$NON-NLS-1$

	public static final String[] TITLE_SPECIAL_CHAR_REPLACER_TABLE = {  "Special Character" ,"Replacer"};
	// The shared instance
	private static SpecialCharPrefPlugin plugin;
	
	/**
	 * The constructor
	 */
	public SpecialCharPrefPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
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
	public static SpecialCharPrefPlugin getDefault() {
		return plugin;
	}

}
