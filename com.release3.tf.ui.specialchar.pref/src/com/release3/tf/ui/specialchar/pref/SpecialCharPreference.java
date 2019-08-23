package com.release3.tf.ui.specialchar.pref;

import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;

import com.release3.specialchars.SpecialCharsReplacer;
import com.release3.tf.ui.specialchar.pref.model.SpecialCharsReplacerContentProvider;
import com.release3.tf.ui.specialchar.pref.model.SpecialCharsReplacerEditingSupport;
import com.release3.tf.ui.specialchar.pref.model.SpecialCharsReplacerLabelProvider;
import com.release3.tf.ui.specialchar.pref.model.SpecialCharsReplacerModelProvider;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class SpecialCharPreference extends Composite {
	protected Table specialCharTable;
	protected SpecialCharsReplacerModelProvider specialCharsModelProvider;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public SpecialCharPreference(Composite parent, int style) {
		super(parent, style);
		
		final TableViewer tableViewer = new TableViewer(this, SWT.BORDER | SWT.FULL_SELECTION);
		tableViewer.setContentProvider(new SpecialCharsReplacerContentProvider());
		specialCharTable = tableViewer.getTable();
		specialCharTable.setBounds(10, 10, 571, 416);
		createColumns(tableViewer);
		
		Button btnSave = new Button(this, SWT.NONE);
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				specialCharsModelProvider.saveSpecialCharsReplacerModel();
			}
		});
		btnSave.setBounds(506, 432, 75, 25);
		btnSave.setText("Save");
		
		Button btnAddChar = new Button(this, SWT.NONE);
		btnAddChar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SpecialCharsReplacer specialChar = new SpecialCharsReplacer();
				specialCharsModelProvider.addSpecialChar(specialChar);
				tableViewer.refresh();
			}
		});
		btnAddChar.setBounds(425, 432, 75, 25);
		btnAddChar.setText("Add Char\r\n");
		
		Button btnRemoveChar = new Button(this, SWT.NONE);
		btnRemoveChar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				StructuredSelection structuredSelection = (StructuredSelection) tableViewer.getSelection();
				List selectedElements = structuredSelection.toList();
				specialCharsModelProvider.removeSpecialChars(selectedElements);
				tableViewer.refresh();
			}
		});
		btnRemoveChar.setBounds(341, 432, 75, 25);
		btnRemoveChar.setText("Remove Char");
		
		SpecialCharsReplacerLabelProvider labelProvider = new SpecialCharsReplacerLabelProvider();
		tableViewer.setLabelProvider(labelProvider);
		specialCharsModelProvider = SpecialCharsReplacerModelProvider.getInstance();
		tableViewer.setInput(specialCharsModelProvider.getSpecialChars());
	}

	private void createColumns(TableViewer tableViewer) {
		int[] bounds = { 200, 300 };

		for (int i = 0; i < SpecialCharPrefPlugin.TITLE_SPECIAL_CHAR_REPLACER_TABLE.length; i++) {
			TableViewerColumn column = new TableViewerColumn(tableViewer, SWT.NONE);
			column.getColumn().setText(SpecialCharPrefPlugin.TITLE_SPECIAL_CHAR_REPLACER_TABLE[i]);
			column.getColumn().setWidth(bounds[i]);
			column.getColumn().setResizable(true);
			column.getColumn().setMoveable(true);

			// enable editing support
			column.setEditingSupport(new SpecialCharsReplacerEditingSupport(tableViewer, i));

		}
		specialCharTable = tableViewer.getTable();
		specialCharTable.removeAll();

		specialCharTable.setHeaderVisible(true);
		specialCharTable.setLinesVisible(true);		
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
