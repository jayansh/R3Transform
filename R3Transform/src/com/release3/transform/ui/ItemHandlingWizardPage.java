package com.release3.transform.ui;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;

import com.oracle.xmlns.forms.Block;
import com.release3.tf.premigrate.item.model.BlockComboContentProvider;
import com.release3.tf.premigrate.item.model.BlockComboLabelProvider;
import com.release3.tf.premigrate.item.model.DataBlockModelProvider;
import com.release3.tf.premigrate.item.model.ItemContentProvider;
import com.release3.tf.premigrate.item.model.ItemEditingSupport;
import com.release3.tf.premigrate.item.model.ItemLabelProvider;
import com.release3.tf.premigrate.item.model.ItemModelProvider;
import com.release3.transform.constants.PremigrationConstants;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class ItemHandlingWizardPage extends WizardPage {
	private Table table;
	TableViewer tableViewer;
	private ItemModelProvider itemModelProvider;
	private ComboViewer blockComboViewer;
	private Combo comboBlock;

	/**
	 * Create the wizard.
	 */
	public ItemHandlingWizardPage() {
		super("ItemHandlingWizardPage");
		setTitle("Item Handling Wizard Page");
		setDescription("Choose atleast one item for block as primary key column.");
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
		mainGroup.setBounds(0, 0, 772, 490);

		tableViewer = new TableViewer(mainGroup, SWT.BORDER
				| SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setBounds(10, 58, 752, 399);
		createColumns(tableViewer);

		blockComboViewer = new ComboViewer(mainGroup, SWT.NONE);
		comboBlock = blockComboViewer.getCombo();
		comboBlock.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				process();
			}
		});
		comboBlock.setBounds(10, 23, 233, 23);
		blockComboViewer.setLabelProvider(new BlockComboLabelProvider());
		blockComboViewer.setContentProvider(new BlockComboContentProvider());

		setControl(container);
	}

	private void createColumns(TableViewer viewer) {

		// int[] bounds = { 176, 176, 176, 176 };

		for (int i = 0; i < PremigrationConstants.ITEM_HANDLING_PAGE_TITLES.length; i++) {
			TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);
			column.getColumn().setText(
					PremigrationConstants.ITEM_HANDLING_PAGE_TITLES[i]);
			column.getColumn().setWidth(
					(viewer.getTable().getBounds().width / 4) - 1);
			// column.getColumn().setWidth(bounds[i]);
			column.getColumn().setResizable(true);
			column.getColumn().setMoveable(true);
			// enable editing support

			column.setEditingSupport(new ItemEditingSupport(viewer, i));

		}
		table = viewer.getTable();
		table.removeAll();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

	}

	@Override
	public IWizardPage getNextPage() {
		DataBlockModelProvider.getInstance().filterBlocks();
		blockComboViewer.setInput(DataBlockModelProvider.getInstance()
				.getBlockList());

		return super.getNextPage();
	}

	public void process() {
		tableViewer.setContentProvider(new ItemContentProvider());
		ItemLabelProvider labelProvider = new ItemLabelProvider();
		tableViewer.setLabelProvider(labelProvider);
		itemModelProvider = ItemModelProvider.getInstance();
		Block selectedBlock = DataBlockModelProvider.getInstance().getBlockList().get(
				(comboBlock.getSelectionIndex()));
		tableViewer.setInput(itemModelProvider.getItemList(selectedBlock));
	}
}
