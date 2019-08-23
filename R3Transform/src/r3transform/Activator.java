package r3transform;

import java.io.OutputStream;
import java.io.PrintStream;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IOConsole;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "R3Transform";
	public static final String CLEANUP_SRC_DIR_PATH = "CleanUpProcess.SrcDirPath";
	public static final String CLEANUP_DEST_DIR_PATH = "CleanUpProcess.DestDirPath";
	public static final String IS_DEST_SAME_AS_SOURCE_DB = "ApplicationPrefs.IsDestSameAsSourceDB"; 
	public static final String ORACLE_URL_STRING = "jdbc:oracle:thin:@localhost:1521:ORCL";
	public static final String ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";
	public static final String EDB_URL_STRING ="jdbc:edb://localhost:5444/edb?searchpath=hr";
	public static final String EDB_DRIVER ="com.edb.Driver";
	public static final String DB2_URL_STRING ="jdbc:db2://localhost:50000/db_name";
	public static final String DB2_DRIVER ="com.ibm.db2.jcc.DB2XADataSource";
	// The shared instance
	private static Activator plugin;
	public static IOConsole txtConsole = new IOConsole("Migration Console", null);
	/**
	 * The constructor
	 */
	public Activator() {
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

		// Display display = PlatformUI.createDisplay();
		//
		// Shell shell = new Shell(display);
		//
		// Login login = new Login(shell);
		// int open = login.open();
		// System.out.println("open:: " + open);
		// if (open == SWT.CANCEL) {
		// System.exit(0);

		// } else if (open == SWT.OK) {
		// System.out.println("Login success");
		// } else {
		// System.exit(0);
		// }
		
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

		
		// myConsole.activate();
		ConsolePlugin.getDefault().getConsoleManager().addConsoles(
				new IConsole[] { txtConsole });
		ConsolePlugin.getDefault().getConsoleManager().showConsoleView(
				txtConsole);
		
		OutputStream stream = txtConsole.newOutputStream();
				
		System.setOut(new PrintStream(stream));
		System.setErr(new PrintStream(stream));

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
	public static Activator getDefault() {
		return plugin;
	}

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
