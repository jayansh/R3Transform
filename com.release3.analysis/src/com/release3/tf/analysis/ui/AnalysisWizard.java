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
