package com.release3.tf.pll;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.release3.tf.pll.model.MigrationPLL;
import com.release3.tf.pll.model.PLLMigrationContentProvider;
import com.release3.tf.pll.model.PLLMigrationLabelProvider;
import com.release3.tf.pll.model.PLLMigrationModelProvider;
import com.release3.transform.constants.PremigrationConstants;

public class PLLMigrationFileChooserWizardPage extends WizardPage {
	private Table table;
	private TableViewer tableViewer;
	private PLLMigrationModelProvider pllModelProvider;

	/**
	 * Create the wizard.
	 */
	public PLLMigrationFileChooserWizardPage() {
		super("PLL Chooser Wizard Page");
		setTitle("Select a PLL file for migration");
		setDescription("Select a PLL file for migration.");
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
		mainGroup.setBounds(0, 0, 789, 411);

		tableViewer = new TableViewer(mainGroup, SWT.BORDER|SWT.MULTI
				| SWT.FULL_SELECTION);
		createColumns(tableViewer);
		tableViewer.setContentProvider(new PLLMigrationContentProvider());
		PLLMigrationLabelProvider labelProvider = new PLLMigrationLabelProvider();
		tableViewer.setLabelProvider(labelProvider);
		pllModelProvider = PLLMigrationModelProvider.getInstance();
		tableViewer.setInput(pllModelProvider.getPLLList());
	}

	private void createColumns(TableViewer viewer) {

		int[] bounds = { 180, 180 };

		for (int i = 0; i < PremigrationConstants.PLL_SELECTION_PAGE_TITLES.length; i++) {
			TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);
			column.getColumn().setText(
					PremigrationConstants.PLL_SELECTION_PAGE_TITLES[i]);
			column.getColumn().setWidth(bounds[i]);
			column.getColumn().setResizable(true);
			column.getColumn().setMoveable(true);
		}
		table = viewer.getTable();
		table.removeAll();
		if (table.getSelectionIndex() == -1) {
			setErrorMessage("Please select a pll.");
		}
		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (table.getSelectionIndex() > -1) {

					TableItem[] items = table.getSelection();
					for (TableItem tableItem : items) {
						MigrationPLL pll = (MigrationPLL) tableItem.getData();
						pllModelProvider.getSelectedPLLs().add(pll);
					}

					setMessage("Ok");
					setErrorMessage(null);
					setPageComplete(true);
				} else {
					setErrorMessage("Please select a pll file");
				}
			}
		});
		table.setBounds(14, 20, 771, 349);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

	}

	@Override
	public boolean isPageComplete() {
		return true;
	}
}
