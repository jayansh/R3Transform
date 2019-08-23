package com.release3.transform.ui;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;

import com.converter.toolkit.ui.custom.Refresh;
import com.release3.tf.core.form.UISettings;
import com.release3.tf.customiz.model.refresh.RefreshAnalysis;
import com.release3.tf.customiz.model.refresh.RefreshBlockContentProvider;
import com.release3.tf.customiz.model.refresh.RefreshBlockEditingSupport;
import com.release3.tf.customiz.model.refresh.RefreshBlockLabelProvider;
import com.release3.tf.customiz.model.refresh.RefreshBlockModelProvider;

public class TriggerCustRefeshBlockGroup extends Group {
	private Table tblBlock;
	TableViewer tableViewer;
	private RefreshBlockModelProvider refreshblockModelProvider;

	private Button btnNewRefBlk;
	private Button btnSaveRefBlk;
	private Button btnRemove ;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public TriggerCustRefeshBlockGroup(Composite parent, int style) {
		super(parent, style);
		this.setText("Refresh Blocks");
		tableViewer = new TableViewer(this, SWT.BORDER | SWT.FULL_SELECTION);
		tblBlock = tableViewer.getTable();
		tblBlock.setBounds(10, 20, 153, 212);
		tblBlock.setHeaderVisible(true);
		tblBlock.setLinesVisible(true);
		createColumn(tableViewer);

		btnSaveRefBlk = new Button(this, SWT.NONE);
		btnSaveRefBlk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				UISettings.getUISettingsBean().save();
				tableViewer.refresh();
			}
		});
		btnSaveRefBlk.setBounds(94, 238, 68, 23);
		btnSaveRefBlk.setText("Save");

		btnNewRefBlk = new Button(this, SWT.NONE);
		btnNewRefBlk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Refresh refresh = new Refresh();
				refresh.setBlock(RefreshAnalysis.getRefreshArray()[0]);
				RefreshBlockModelProvider.getInstance().getRefreshList().add(
						refresh);
				tableViewer.refresh();
			}
		});
		btnNewRefBlk.setBounds(20, 238, 68, 23);
		btnNewRefBlk.setText("New");

		btnRemove = new Button(this, SWT.NONE);
		btnRemove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (tblBlock.getSelectionIndex() >= 0) {
					RefreshBlockModelProvider.getInstance().getRefreshList()
							.remove(tblBlock.getSelectionIndex());
					tableViewer.refresh();

				}
			}
		});
		btnRemove.setBounds(94, 267, 68, 23);
		btnRemove.setText("Remove");

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	private void createColumn(TableViewer viewer) {
		TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);
		column.getColumn().setText("Block");
		column.getColumn().setWidth(145);
		column.getColumn().setResizable(true);
		column.getColumn().setMoveable(true);
		column.setEditingSupport(new RefreshBlockEditingSupport(viewer, 0));
	}

	public void setEnabled(boolean value) {
		super.setEnabled(value);
		tblBlock.setEnabled(value);
		btnNewRefBlk.setEnabled(value);
		btnSaveRefBlk.setEnabled(value);
		btnRemove.setEnabled(value);
	}

	public void loadRefreshBlockTable() {
		tableViewer.setContentProvider(new RefreshBlockContentProvider());
		RefreshBlockLabelProvider labelProvider = new RefreshBlockLabelProvider();
		tableViewer.setLabelProvider(labelProvider);
		refreshblockModelProvider = RefreshBlockModelProvider.getInstance();
		tableViewer.setInput(refreshblockModelProvider.getRefreshList());
	}
}
