package r3transform;

import java.util.prefs.Preferences;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.TrayItem;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

	private IWorkbenchWindow window;
	private TrayItem trayItem;
	private Image trayImage;
	private final static String COMMAND_ID = "R3Transform.Exit";

	public ApplicationWorkbenchWindowAdvisor(
			IWorkbenchWindowConfigurer configurer) {
		super(configurer);
	}

	public void preWindowOpen() {
		IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		String licType = Preferences.userNodeForPackage(
				com.jaysan.licensemgmt.LicensePlugin.class).get(
				com.jaysan.licensemgmt.LicensePlugin.LICENSETYPE,
				com.jaysan.licensemgmt.LicensePlugin.DEFAULT_LICENSE_TYPE);
		//
		//		if (!(licType == "FULL" || licType.equals("FULL"))) { //$NON-NLS-1$ //$NON-NLS-2$
		com.jaysan.licensemgmt.LicenseCheck.getLicenseCheck().licenseCheck();
		// }
		configurer.setInitialSize(new Point(800, 600));
		configurer.setShowCoolBar(true);
		configurer.setShowStatusLine(true);
		configurer.setTitle("R3Transform");
		configurer.setShowPerspectiveBar(true);
		configurer.setShowProgressIndicator(true);
	}
	/*
	 * // As of here is the new stuff
	 * 
	 * @Override public void postWindowOpen() { super.postWindowOpen(); window =
	 * getWindowConfigurer().getWindow(); trayItem = initTaskItem(window); //
	 * Some OS might not support tray items if (trayItem != null) {
	 * minimizeBehavior(); // Create exit and about action on the icon
	 * hookPopupMenu(); } }
	 * 
	 * // Add a listener to the shell
	 * 
	 * private void minimizeBehavior() { window.getShell().addShellListener(new
	 * ShellAdapter() { // If the window is minimized hide the window public
	 * void shellIconified(ShellEvent e) { window.getShell().setVisible(false);
	 * } }); // If user double-clicks on the tray icons the application will be
	 * // visible again trayItem.addListener(SWT.DefaultSelection, new
	 * Listener() { public void handleEvent(Event event) { Shell shell =
	 * window.getShell(); if (!shell.isVisible()) {
	 * window.getShell().setMinimized(false); shell.setVisible(true); } } }); }
	 * 
	 * // We hook up on menu entry which allows to close the application private
	 * void hookPopupMenu() { trayItem.addListener(SWT.MenuDetect, new
	 * Listener() { public void handleEvent(Event event) { Menu menu = new
	 * Menu(window.getShell(), SWT.POP_UP);
	 * 
	 * // Creates a new menu item that terminates the program // when selected
	 * MenuItem exit = new MenuItem(menu, SWT.NONE); exit.setText("Goodbye!");
	 * exit.addListener(SWT.Selection, new Listener() { public void
	 * handleEvent(Event event) { // Lets call our command IHandlerService
	 * handlerService = (IHandlerService) window
	 * .getService(IHandlerService.class); try {
	 * handlerService.executeCommand(COMMAND_ID, null); } catch (Exception ex) {
	 * throw new RuntimeException(COMMAND_ID); } } }); // We need to make the
	 * menu visible menu.setVisible(true); } }); }
	 * 
	 * // This methods create the tray item and return a reference private
	 * TrayItem initTaskItem(IWorkbenchWindow window) { final Tray tray =
	 * window.getShell().getDisplay().getSystemTray(); TrayItem trayItem = new
	 * TrayItem(tray, SWT.NONE); trayImage =
	 * AbstractUIPlugin.imageDescriptorFromPlugin( "R3Transform",
	 * "/icons/r3_temp16X16.GIF") .createImage(); trayItem.setImage(trayImage);
	 * trayItem.setToolTipText("TrayItem"); return trayItem;
	 * 
	 * }
	 * 
	 * // We need to clean-up after ourself
	 * 
	 * @Override public void dispose() { if (trayImage != null) {
	 * trayImage.dispose(); } if (trayItem != null) { trayItem.dispose(); } }
	 */
}
