package r3transform;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class WorkspaceChooserDialog extends TitleAreaDialog {

	// the name of the file that tells us that the workspace directory belongs
	// to our application
	public static final String WS_IDENTIFIER = ".R3TransformWS";
	// you would probably normally define these somewhere in your Preference
	// Constants
	private static final String WORKSPACE_ROOTDIR = "wsRootDir";
	private static final String REMEMBER_WORKSPACE = "wsRemember";
	private static final String LAST_USED_WORKSPACES = "wsLastUsedWorkspaces";

	// various dialog messages
	private static final String _StrMsg = "R3Transform stores application settings/license in a folder called a workspace.";
	private static final String _StrInfo = "Choose a workspace folder to use for this session.";
	private static final String _StrError = "You must set a directory";
	private boolean _switchWorkspace;
	private List<String> lastUsedWorkspaces;

	// this are our preferences we will be using as the IPreferenceStore is not
	// available yet
	private static Preferences preferences = Preferences
			.userNodeForPackage(WorkspaceChooserDialog.class);

	// used as separator when we save the last used workspace locations
	private static final String _SplitChar = "#";
	// max number of entries in the history box
	private static final int _MaxHistory = 20;
	// whatever the user picks ends up on this variable
	private String selectedWorkspaceRootLocation;
	// public WorkspaceChooserDialog(Shell parentShell) {
	// super(parentShell);
	// // TODO Auto-generated constructor stub
	// }

	private Label lblWorkspace;
	private Button btnBrowse;
	private Button btnRememberWS;
	private Combo cmbWSLocation;

	public WorkspaceChooserDialog(boolean switchWorkspace, Shell parentShell) {
		super(parentShell);
		this._switchWorkspace = switchWorkspace;

	}

	@Override
	protected Control createDialogArea(Composite parent) {
		setTitle("Select a Workspace");
		setMessage(_StrMsg);
		Control ctrl = super.createDialogArea(parent);
		// WSChooserDialogInnerComposite composite = new
		// WSChooserDialogInnerComposite(
		// parent, SWT.NONE);
		Composite composite = new Composite(parent, SWT.NONE);
		lblWorkspace = new Label(composite, SWT.NONE);
		lblWorkspace.setBounds(10, 23, 68, 15);
		lblWorkspace.setText("Workspace:");

		cmbWSLocation = new Combo(composite, SWT.NONE);
		cmbWSLocation.setBounds(89, 19, 405, 23);
		String wsRoot = preferences.get(WORKSPACE_ROOTDIR, "");
		if (wsRoot == null || wsRoot.length() == 0) {
			wsRoot = getWorkspacePathSuggestion();
		}
		cmbWSLocation.setText(wsRoot == null ? "" : wsRoot);

		btnBrowse = new Button(composite, SWT.NONE);
		btnBrowse.setBounds(500, 18, 86, 25);
		btnBrowse.setText("Browse...");

		btnRememberWS = new Button(composite, SWT.CHECK);
		btnRememberWS.setBounds(10, 104, 272, 16);
		btnRememberWS.setText("Use this as default and don't ask again");
		btnRememberWS.setSelection(preferences.getBoolean(REMEMBER_WORKSPACE,
				false));

		String lastUsed = preferences.get(LAST_USED_WORKSPACES, "");
		lastUsedWorkspaces = new ArrayList<String>();
		if (lastUsed != null) {
			String[] all = lastUsed.split(_SplitChar);
			for (String str : all) {
				lastUsedWorkspaces.add(str);
			}
		}
		for (String last : lastUsedWorkspaces) {
			cmbWSLocation.add(last);
		}

		btnBrowse.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				DirectoryDialog dd = new DirectoryDialog(getParentShell());
				dd.setText("Select Workspace Root");
				dd.setMessage(_StrInfo);
				dd.setFilterPath(cmbWSLocation.getText());
				String pick = dd.open();
				if (pick == null && cmbWSLocation.getText().length() == 0) {
					setMessage(_StrError, IMessageProvider.ERROR);
				} else {
					setMessage(_StrMsg);
					cmbWSLocation.setText(pick);
				}
			}

		});
		return ctrl;
	}

	/** suggests a path based on the user.home/temp directory location */
	private String getWorkspacePathSuggestion() {
		StringBuffer buf = new StringBuffer();

		String uHome = System.getProperty("user.home");
		if (uHome == null) {
			uHome = "c:" + File.separator + "temp";
		}

		buf.append(uHome);
		buf.append(File.separator);
		buf.append("R3Transform");
		buf.append("Workspace");

		return buf.toString();
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		if (_switchWorkspace) {
			newShell.setText("Switch Workspace");
		} else {
			newShell.setText("Workspace Selection");
		}
	}

	/**
	 * Returns whether the user selected "remember workspace" in the preferences
	 * 
	 * @return
	 */
	public static boolean isRememberWorkspace() {
		return preferences.getBoolean(REMEMBER_WORKSPACE, false);
	}

	/**
	 * Returns the last set workspace directory from the preferences
	 * 
	 * @return null if none
	 */
	public static String getLastSetWorkspaceDirectory() {
		return preferences.get(WORKSPACE_ROOTDIR, null);
	}

	@Override
	protected void okPressed() {
		String str = cmbWSLocation.getText();

		if (str.length() == 0) {
			setMessage(_StrError, IMessageProvider.ERROR);
			return;
		}

		String ret = checkWorkspaceDirectory(getParentShell(), str, true, true);
		if (ret != null) {
			setMessage(ret, IMessageProvider.ERROR);
			return;
		}

		// save it so we can show it in combo later
		lastUsedWorkspaces.remove(str);

		if (!lastUsedWorkspaces.contains(str)) {
			lastUsedWorkspaces.add(0, str);
		}

		// deal with the max history
		if (lastUsedWorkspaces.size() > _MaxHistory) {
			List<String> remove = new ArrayList<String>();
			for (int i = _MaxHistory; i < lastUsedWorkspaces.size(); i++) {
				remove.add(lastUsedWorkspaces.get(i));
			}

			lastUsedWorkspaces.removeAll(remove);
		}

		// create a string concatenation of all our last used workspaces
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < lastUsedWorkspaces.size(); i++) {
			buf.append(lastUsedWorkspaces.get(i));
			if (i != lastUsedWorkspaces.size() - 1) {
				buf.append(_SplitChar);
			}
		}

		// save them onto our preferences
		preferences
				.putBoolean(REMEMBER_WORKSPACE, btnRememberWS.getSelection());
		preferences.put(LAST_USED_WORKSPACES, buf.toString());

		// now create it
		boolean ok = checkAndCreateWorkspaceRoot(str);
		if (!ok) {
			setMessage("The workspace could not be created, please check the error log");
			return;
		}

		// here we set the location so that we can later fetch it again
		selectedWorkspaceRootLocation = str;

		// and on our preferences as well
		preferences.put(WORKSPACE_ROOTDIR, str);

		super.okPressed();
	}

	/**
	 * Checks to see if a workspace exists at a given directory string, and if
	 * not, creates it. Also puts our identifying file inside that workspace.
	 * 
	 * @param wsRoot
	 *            Workspace root directory as string
	 * @return true if all checks and creations succeeded, false if there was a
	 *         problem
	 */
	public static boolean checkAndCreateWorkspaceRoot(String wsRoot) {
		try {
			File fRoot = new File(wsRoot);
			if (!fRoot.exists())
				return false;

			File dotFile = new File(wsRoot + File.separator
					+ WorkspaceChooserDialog.WS_IDENTIFIER);
			if (!dotFile.exists() && !dotFile.createNewFile())
				return false;

			return true;
		} catch (Exception err) {
			// as it might need to go to some other error log too
			err.printStackTrace();
			return false;
		}
	}

	/**
	 * Ensures a workspace directory is OK in regards of reading/writing, etc.
	 * This method will get called externally as well.
	 * 
	 * @param parentShell
	 *            Shell parent shell
	 * @param workspaceLocation
	 *            Directory the user wants to use
	 * @param askCreate
	 *            Whether to ask if to create the workspace or not in this
	 *            location if it does not exist already
	 * @param fromDialog
	 *            Whether this method was called from our dialog or from
	 *            somewhere else just to check a location
	 * @return null if everything is ok, or an error message if not
	 */
	public static String checkWorkspaceDirectory(Shell parentShell,
			String workspaceLocation, boolean askCreate, boolean fromDialog) {
		File f = new File(workspaceLocation);
		if (!f.exists()) {
			if (askCreate) {
				boolean create = MessageDialog
						.openConfirm(parentShell, "New Directory",
								"The directory does not exist. Would you like to create it?");
				if (create) {
					try {
						f.mkdirs();
						File wsDot = new File(workspaceLocation
								+ File.separator + WS_IDENTIFIER);
						wsDot.createNewFile();
					} catch (Exception err) {
						return "Error creating directories, please check folder permissions";
					}
				}

				if (!f.exists()) {
					return "The selected directory does not exist";
				}
			}
		}

		if (!f.canRead()) {
			return "The selected directory is not readable";
		}

		if (!f.isDirectory()) {
			return "The selected path is not a directory";
		}

		File wsTest = new File(workspaceLocation + File.separator
				+ WS_IDENTIFIER);
		if (fromDialog) {
			if (!wsTest.exists()) {
				boolean create = MessageDialog
						.openConfirm(
								parentShell,
								"New Workspace",
								"The directory '"
										+ wsTest.getAbsolutePath()
										+ "' is not set to be a workspace. Do note that files will be created directly under the specified directory and it is suggested you create a directory that has a name that represents your workspace. \n\nWould you like to create a workspace in the selected location?");
				if (create) {
					try {
						f.mkdirs();
						File wsDot = new File(workspaceLocation
								+ File.separator + WS_IDENTIFIER);
						wsDot.createNewFile();
					} catch (Exception err) {
						return "Error creating directories, please check folder permissions";
					}
				} else {
					return "Please select a directory for your workspace";
				}

				if (!wsTest.exists()) {
					return "The selected directory does not exist";
				}

				return null;
			}
		} else {
			if (!wsTest.exists()) {
				return "The selected directory is not a workspace directory";
			}
		}

		return null;
	}

	/**
	 * Returns whatever path the user selected in the dialog.
	 * 
	 * @return Path
	 */
	public String getSelectedWorkspaceLocation() {
		return selectedWorkspaceRootLocation;
	}

}
