package com.release3.transform.ui;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;

import com.release3.transform.constants.PremigrationConstants;
import com.release3.tf.core.form.CleanupForm;
import com.release3.transform.model.SpecialCharsContentProvider;
import com.release3.transform.model.SpecialCharsLabelProvider;
import com.release3.transform.model.SpecialCharsModelProvider;
import com.release3.transform.pref.PropertiesReadWrite;

public class SpecialCharHandlingWizardPage extends WizardPage {
	private Table table;
	private TableViewer tableViewer;
	private SpecialCharsModelProvider specialCharsModelProvider;
	
	boolean containSC = false;

	/**
	 * Create the wizard.
	 */
	public SpecialCharHandlingWizardPage() {
		super("Special Character Wizard Page");
		setTitle("Special Character Handling");
		setDescription("Special Character Handling");
	}

	/**
	 * Create contents of the wizard.
	 * 
	 * @param parent
	 */
	public void createControl(final Composite parent) {
		final Composite container = new Composite(parent, SWT.NULL);

		setControl(container);

		Group mainGroup = new Group(container, SWT.NONE);
		mainGroup.setBounds(0, 0, 772, 490);

		tableViewer = new TableViewer(mainGroup, SWT.BORDER
				| SWT.FULL_SELECTION);
		createColumns(tableViewer);

		Button btnApply = new Button(mainGroup, SWT.NONE);
		btnApply.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				specialCharsModelProvider.setUpdated(true);
				
				if(process()){
					setPageComplete(true);
				}
				
				CleanupForm currentForm = ((FormChooserWizardPage) getPreviousPage().getPreviousPage())
						.getCleanupForm();
				currentForm.save(false);
				PropertiesReadWrite.getPropertiesReadWrite().write();
				tableViewer.refresh();
				
			}
		});
		btnApply.setBounds(694, 457, 68, 23);
		btnApply.setText("Apply");
		
		Button btnCharPref = new Button(mainGroup, SWT.NONE);
		btnCharPref.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SpecialCharPreferenceDialog dialog = new SpecialCharPreferenceDialog(parent.getShell());
				dialog.open();
			}
		});
		btnCharPref.setBounds(613, 457, 75, 25);
		btnCharPref.setText("Char Pref");
		// tableViewer.setContentProvider(new SpecialCharsContentProvider());
		// SpecialCharsLabelProvider labelProvider = new
		// SpecialCharsLabelProvider();
		// tableViewer.setLabelProvider(labelProvider);
		// specialCharsModelProvider = SpecialCharsModelProvider.getInstance();
		// tableViewer.setInput(specialCharsModelProvider.getItemsList());
		
	}

	private void createColumns(TableViewer viewer) {

		int[] bounds = { 200, 300 };

		for (int i = 0; i < PremigrationConstants.SPECIAL_CHARS_TITLES.length; i++) {
			TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);
			column.getColumn().setText(PremigrationConstants.SPECIAL_CHARS_TITLES[i]);
			column.getColumn().setWidth(bounds[i]);
			column.getColumn().setResizable(true);
			column.getColumn().setMoveable(true);

			// enable editing support
			column.setEditingSupport(new SpecialCharsEditingSupport(viewer, i));

		}
		table = viewer.getTable();
		table.removeAll();

		table.setBounds(10, 10, 752, 427);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

	}

//	@Override
//	public boolean canFlipToNextPage() {
//
//		if (getErrorMessage() != null) {
//			return false;
//		} else {
//			return true;
//		}
//	}

	@Override
	public IWizardPage getNextPage() {

		tableViewer.setContentProvider(new SpecialCharsContentProvider());
		SpecialCharsLabelProvider labelProvider = new SpecialCharsLabelProvider();
		tableViewer.setLabelProvider(labelProvider);
		specialCharsModelProvider = SpecialCharsModelProvider.getInstance();
		tableViewer.setInput(specialCharsModelProvider.getItemsList());
//		specialCharsModelProvider.setUpdated(true);
		if (process()) {
			
			return super.getNextPage();
		} else {
			return null;
		}
	}

	private boolean process() {
		if (specialCharsModelProvider.getItemHasSC() > -1) {
			setErrorMessage("Item contains special character.");
			return false;
		} else {
			setErrorMessage(null);
		}
		return true;
	}
}
