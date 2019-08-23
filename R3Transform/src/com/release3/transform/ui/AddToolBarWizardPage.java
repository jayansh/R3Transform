package com.release3.transform.ui;

import java.io.File;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Label;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.StructuredSelection;

import com.oracle.xmlns.forms.Block;
import com.oracle.xmlns.forms.Window;
import com.release3.tf.core.form.Settings;
import com.release3.tf.premigrate.toolbar.model.DefaultToolbarBlockComboLabelProvider;
import com.release3.tf.premigrate.toolbar.model.DefaultToolbarBlockContentProvider;
import com.release3.tf.premigrate.toolbar.model.DefaultToolbarWinComboLabelProvider;
import com.release3.tf.premigrate.toolbar.model.DefaultToolbarWinContentProvider;
import com.release3.tf.premigrate.toolbar.model.DefaultToolbarModelProvider;
import com.release3.transform.constants.PremigrationConstants;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import r3transform.Activator;

public class AddToolBarWizardPage extends WizardPage {
	private ComboViewer windowComboViewer;
	private Button btnCheckSearch;
	private Button btnCheckQuery;
	private Button btnCheckPrev;
	private Button btnCheckNext;
	private Combo windowCombo;
	private int currWinComboIndex;
	private Combo blockCombo;
	private ComboViewer blockComboViewer;
	private int currBlockComboIndex;
	private Button btnCheckNew;
	private Button btnCheckSave;
	private Button btnCheckDelete;
	private Button btnCheckExit;
	private Button btnCheckClear;

	/**
	 * Create the wizard.
	 */
	public AddToolBarWizardPage() {
		super("wizardPage");
		setTitle("Add Default Toolbar");
		setDescription("This page will add a new horizontal toolbar to form.");
	}

	/**
	 * Create contents of the wizard.
	 * 
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);

		Group mainGroup = new Group(container, SWT.NONE);
		mainGroup.setBounds(0, 0, 574, 282);

		Label lblNewLabel = new Label(mainGroup, SWT.NONE);
		lblNewLabel.setBounds(10, 20, 125, 15);
		lblNewLabel.setText("Select a main window:");

		windowComboViewer = new ComboViewer(mainGroup, SWT.NONE);

		windowCombo = windowComboViewer.getCombo();
		windowCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				currWinComboIndex = windowCombo.getSelectionIndex();

			}
		});
		windowCombo.setBounds(141, 17, 172, 23);

		blockComboViewer = new ComboViewer(mainGroup, SWT.NONE);

		blockCombo = blockComboViewer.getCombo();
		blockCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				currBlockComboIndex = blockCombo.getSelectionIndex();

			}
		});
		blockCombo.setBounds(141, 47, 172, 23);

		btnCheckSearch = new Button(mainGroup, SWT.CHECK);
		btnCheckSearch.setSelection(true);
		btnCheckSearch.setBounds(10, 82, 93, 16);
		btnCheckSearch.setText("Search");

		btnCheckQuery = new Button(mainGroup, SWT.CHECK);
		btnCheckQuery.setSelection(true);
		btnCheckQuery.setBounds(10, 104, 93, 16);
		btnCheckQuery.setText("Query");

		btnCheckPrev = new Button(mainGroup, SWT.CHECK);
		btnCheckPrev.setSelection(true);
		btnCheckPrev.setBounds(10, 126, 93, 16);
		btnCheckPrev.setText("Previous");

		btnCheckNext = new Button(mainGroup, SWT.CHECK);
		btnCheckNext.setSelection(true);
		btnCheckNext.setBounds(10, 149, 93, 16);
		btnCheckNext.setText("Next");

		btnCheckNew = new Button(mainGroup, SWT.CHECK);
		btnCheckNew.setSelection(false);
		btnCheckNew.setBounds(10, 169, 93, 16);
		btnCheckNew.setText("New");

		btnCheckSave = new Button(mainGroup, SWT.CHECK);
		btnCheckSave.setSelection(false);
		btnCheckSave.setBounds(10, 211, 93, 16);
		btnCheckSave.setText("Save");

		btnCheckDelete = new Button(mainGroup, SWT.CHECK);
		btnCheckDelete.setSelection(false);
		btnCheckDelete.setBounds(10, 189, 93, 16);
		btnCheckDelete.setText("Delete");

		Label lblSelectABlock = new Label(mainGroup, SWT.NONE);
		lblSelectABlock.setText("Select a block:");
		lblSelectABlock.setBounds(10, 51, 125, 15);

		btnCheckClear = new Button(mainGroup, SWT.CHECK);
		btnCheckClear.setBounds(10, 233, 54, 16);
		btnCheckClear.setSelection(false);
		btnCheckClear.setText("Clear");

		btnCheckExit = new Button(mainGroup, SWT.CHECK);
		btnCheckExit.setBounds(10, 256, 54, 16);
		btnCheckExit.setText("Exit");
		btnCheckExit.setSelection(false);
		btnCheckExit.setEnabled(getGlobalCustomization());

	}

	@Override
	public boolean canFlipToNextPage() {
		Boolean quickCleanupWizard = Activator.getDefault()
				.getPreferenceStore()
				.getDefaultBoolean(PremigrationConstants.QUICK_CLEANUP_WIZARD);
		if (quickCleanupWizard) {
			return false;
		}
		return super.canFlipToNextPage();
	}

	@Override
	public IWizardPage getNextPage() {
		windowComboViewer
				.setLabelProvider(new DefaultToolbarWinComboLabelProvider());
		windowComboViewer
				.setContentProvider(new DefaultToolbarWinContentProvider());
		windowComboViewer.setInput(DefaultToolbarModelProvider.getInstance()
				.getWindowList());
		blockComboViewer
				.setLabelProvider(new DefaultToolbarBlockComboLabelProvider());
		blockComboViewer
				.setContentProvider(new DefaultToolbarBlockContentProvider());
		blockComboViewer.setInput(DefaultToolbarModelProvider.getInstance()
				.getBlockList());

		return super.getNextPage();
	}

	public Window getSelectedWindow() {

		int index = currWinComboIndex;
		if (index < 0) {
			index = 0;
		}
		if (DefaultToolbarModelProvider.getInstance().getWindowList().size() > index) {
			Window selectedWindow = DefaultToolbarModelProvider.getInstance()
					.getWindowList().get(index);
			return selectedWindow;
		}
		return null;
	}

	public Block getSelectedBlock() {

		int index = currBlockComboIndex;
		if (index < 0) {
			index = 0;
		}
		if (DefaultToolbarModelProvider.getInstance().getBlockList().size() > index) {
			Block selectedBlock = DefaultToolbarModelProvider.getInstance()
					.getBlockList().get(index);
			return selectedBlock;
		}
		return null;
	}

	public boolean getBtnCheckSearchSelection() {
		return btnCheckSearch.getSelection();
	}

	public boolean getBtnCheckQuerySelection() {
		return btnCheckQuery.getSelection();
	}

	public boolean getBtnCheckNextSelection() {
		return btnCheckNext.getSelection();
	}

	public boolean getBtnCheckPrevSelection() {
		return btnCheckPrev.getSelection();
	}

	public boolean getBtnCheckNewSelection() {
		return btnCheckNew.getSelection();
	}

	public boolean getBtnCheckDeleteSelection() {
		return btnCheckDelete.getSelection();
	}

	public boolean getBtnCheckSaveSelection() {
		return btnCheckSave.getSelection();
	}

	public boolean getBtnCheckClearSelection() {
		return btnCheckClear.getSelection();
	}

	public boolean getBtnCheckExitSelection() {
		return btnCheckExit.getSelection();
	}

	public boolean getGlobalCustomization() {
		File globalCustFile = new File(Settings.getSettings().getBaseDir()
				+ File.separator + "ViewController" + File.separator + "src"
				+ File.separator + Settings.getSettings().getApplicationName()
				+ File.separator + "GLOBAL" + File.separator
				+ "GLOBAL_customization.xml");
		if (globalCustFile.exists()) {
			return true;
		}
		return false;

	}

	@Override
	public boolean isPageComplete() {
		Boolean quickCleanupWizard = Activator.getDefault()
				.getPreferenceStore()
				.getDefaultBoolean(PremigrationConstants.QUICK_CLEANUP_WIZARD);
		if (quickCleanupWizard) {
			return true;
		}
		return super.isPageComplete();
	}

}
