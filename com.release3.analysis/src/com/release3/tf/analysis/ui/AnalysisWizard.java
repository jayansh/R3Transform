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
package com.release3.tf.analysis.ui;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;

public class AnalysisWizard extends Wizard {
	WizardPage page1;
	WizardPage page2;

	public AnalysisWizard() {
		setWindowTitle("Analysis Wizard");
		setNeedsProgressMonitor(true);
	}

	@Override
	public void addPages() {
		page1 = new AnalysisFormChooserPage();
		addPage(page1);
		page2 = new AnalysisResultPage();
		addPage(page2);

	}

	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean canFinish() {
		if (getContainer().getCurrentPage() == page2)
			return true;
		else
			return false;
	}
}
