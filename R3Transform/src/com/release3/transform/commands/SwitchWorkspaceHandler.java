package com.release3.transform.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import r3transform.ActionSwitchWorkspace;
import r3transform.WorkspaceChooserDialog;

public class SwitchWorkspaceHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ActionSwitchWorkspace workspace = new ActionSwitchWorkspace(Display.getDefault().getActiveShell());
		workspace.run();
		return null;
	}

}
