package com.jaysan.licensemgmt;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class LicensePlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.jaysan.licensemgmt"; //$NON-NLS-1$

	public static final String LICENSETYPE = "LICENSETYPE"; //$NON-NLS-1$
	public static final String DEFAULT_LICENSE_TYPE = "TRIAL"; //$NON-NLS-1$

	public static int trialDaysLeft;

	// The shared instance
	private static LicensePlugin plugin;

	/**
	 * The constructor
	 */
	public LicensePlugin() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
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
	public static LicensePlugin getDefault() {
		return plugin;
	}

}
