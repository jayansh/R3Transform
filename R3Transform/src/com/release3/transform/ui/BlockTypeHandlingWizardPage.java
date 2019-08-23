package com.release3.transform.ui;

import java.util.List;

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

import com.oracle.xmlns.forms.Block;
import com.release3.tf.core.form.CleanupForm;
import com.release3.transform.constants.PremigrationConstants;
import com.release3.transform.model.BlockContentProvider;
import com.release3.transform.model.BlockLabelProvider;
import com.release3.transform.model.BlockModelProvider;
import com.release3.transform.pref.PropertiesReadWrite;
import com.release3.transform.rule.RuleHandling;

public class BlockTypeHandlingWizardPage extends WizardPage {
	private Table table;
	TableViewer tableViewer;
	private BlockModelProvider blockModelProvider;

	/**
	 * Create the wizard.
	 */
	public BlockTypeHandlingWizardPage() {
		super("wizardPage");
		setTitle("Block Type Handling");
		setDescription("Block Type Handling");
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
		table.setBounds(10, 10, 752, 427);
		createColumns(tableViewer);

		Button btnApply = new Button(mainGroup, SWT.NONE);
		btnApply.setLocation(694, 455);
		btnApply.setSize(68, 23);
		btnApply.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
//				CleanupForm currentForm = ((FormChooserWizardPage) getPreviousPage()
//						.getPreviousPage().getPreviousPage()).getCleanupForm();
//				currentForm.save(false);
//				PropertiesReadWrite.getPropertiesReadWrite().write();
//				tableViewer.refresh();
				applyAction();
			}
		});
		btnApply.setText("Apply");

		final Button btnApplyRules = new Button(mainGroup, SWT.CHECK);
		btnApplyRules.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (btnApplyRules.getSelection()) {
					PremigrationConstants.ApplyRules = true;
					RuleHandling ruleHandling = ((FormChooserWizardPage) getPreviousPage()
							.getPreviousPage().getPreviousPage()).getRuleHandling();
					List<Block> list = blockModelProvider.getBlockList();
					for (Block block : list) {
						ruleHandling.applyBlockTypeAsPerRule(block);
						
						System.out.println("block.isDatabaseBlock()" +block.isDatabaseBlock());
						String[] properties = { PremigrationConstants.BLOCKTYPE_TITLES[1]};
						tableViewer.update(block,properties);
					}
					
					//applyAction();
				}else{
					PremigrationConstants.ApplyRules = false;	
				}

			}
		});
		btnApplyRules.setBounds(10, 455, 85, 16);
		btnApplyRules.setText("Apply Rules");

		// tableViewer.setContentProvider(new BlockContentProvider());
		// BlockLabelProvider labelProvider = new BlockLabelProvider();
		// tableViewer.setLabelProvider(labelProvider);
		// blockModelProvider = BlockModelProvider.getInstance();
		// tableViewer.setInput(blockModelProvider.getBlockList());
	}

	private void createColumns(TableViewer viewer) {

		// int[] bounds = { 176, 176, 176, 176 };

		for (int i = 0; i < PremigrationConstants.BLOCKTYPE_TITLES.length; i++) {
			TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);
			column.getColumn().setText(
					PremigrationConstants.BLOCKTYPE_TITLES[i]);
			column.getColumn().setWidth(
					(viewer.getTable().getBounds().width / 4) - 1);
			// column.getColumn().setWidth(bounds[i]);
			column.getColumn().setResizable(true);
			column.getColumn().setMoveable(true);
			// enable editing support
			column.setEditingSupport(new BlockEditingSupport(viewer, i));

		}
		table = viewer.getTable();
		table.removeAll();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

	}

	private void applyAction(){
		CleanupForm currentForm = ((FormChooserWizardPage) getPreviousPage()
				.getPreviousPage().getPreviousPage().getPreviousPage()).getCleanupForm();
		currentForm.save(false);
		PropertiesReadWrite.getPropertiesReadWrite().write();
		tableViewer.refresh();
	}
	@Override
	public IWizardPage getNextPage() {
		tableViewer.setContentProvider(new BlockContentProvider());
		BlockLabelProvider labelProvider = new BlockLabelProvider();
		tableViewer.setLabelProvider(labelProvider);
		blockModelProvider = BlockModelProvider.getInstance();
		tableViewer.setInput(blockModelProvider.getBlockList());
		return super.getNextPage();
	}
}
