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
package r3transform;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class ActionSwitchWorkspace extends Action {

    private Shell titleShell;

    public ActionSwitchWorkspace(Shell titleShell) {
        super("Switch Workspace");
        this.titleShell = titleShell;
    }

    @Override
    public void run() {
        WorkspaceChooserDialog pwd = new WorkspaceChooserDialog(true, titleShell );
        int pick = pwd.open();
        if (pick == Dialog.CANCEL)
            return;

        MessageDialog.openInformation(Display.getDefault().getActiveShell(), "Switch Workspace", "The R3Transform will now restart with the new workspace");

        // restart client
        PlatformUI.getWorkbench().restart();
    }
}

