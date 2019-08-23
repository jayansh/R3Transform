package com.release3.analysis;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class AnalysisPlugin extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.release3.analysis";
	public static final String[] ANALYSIS_RESULT_DETAILS_TITLES={"Element","Issue","Suggestion"}; 
	public static final String[] ANALYSIS_RESULT_FORMS_DEPENDENT_TITLES={"Object","Type"}; 

	// The shared instance
	private static AnalysisPlugin plugin;
	
	/**
	 * The constructor
	 */
	public AnalysisPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
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
	public static AnalysisPlugin getDefault() {
		return plugin;
	}

}