package r3transform;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.console.IConsoleConstants;

public class Perspective implements IPerspectiveFactory {

	/**
	 * The ID of the perspective as specified in the extension.
	 */
	public static final String ID = "R3Transform.perspective";

	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(false);
		layout.addStandaloneView(View.ID, false, IPageLayout.LEFT, 0.99f,
				editorArea);

		IFolderLayout consoleFolder = layout.createFolder("console",
				IPageLayout.BOTTOM, 0.65f, "messages");
		consoleFolder.addView(IConsoleConstants.ID_CONSOLE_VIEW);

		layout.getViewLayout(View.ID).setCloseable(false);
	}
}
