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
package com.release3.transform.ui;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Table;

import com.converter.toolkit.ui.control.RecordGroupsControl;
import com.converter.toolkit.ui.custom.RecordGroup;
import com.converter.toolkit.ui.custom.Sequence;
import com.release3.tf.core.form.UISettings;
import com.release3.tf.customiz.model.param.ParameterAnalysis;
import com.release3.tf.customiz.model.param.ParameterEditingSupport;
import com.release3.tf.customiz.model.sequence.SequenceAnalysis;
import com.release3.tf.customiz.model.sequence.SequenceContentProvider;
import com.release3.tf.customiz.model.sequence.SequenceEditingSupport;
import com.release3.tf.customiz.model.sequence.SequenceLabelProvider;
import com.release3.tf.customiz.model.sequence.SequenceModelProvider;

public class SequenceCustomization extends Group {
	private final Table table;
	TableViewer tableViewer;
	private SequenceModelProvider sequenceModelProvider;

	private Button btnNewSeq;
	private Button btnSaveSeq;
	private Button btnUpButton;
	private Button btnDownButton;
	private Button btnRemove;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public SequenceCustomization(final Composite parent, int style) {
		super(parent, style);
		this.setText("Sequence");
		tableViewer = new TableViewer(this, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setBounds(10, 20, 153, 212);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		createColumns(tableViewer);

		table.setBounds(10, 20, 409, 212);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		btnNewSeq = new Button(this, SWT.NONE);
		btnNewSeq.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				try {
					Sequence seq = new Sequence();

					seq.setItem(ParameterAnalysis.getItemArray()[0]
							.substring(ParameterAnalysis.getItemArray()[0]
									.indexOf('-') + 1));

					SequenceModelProvider.getInstance().getSequenceList()
							.add(seq);

					// UISettings.getUISettingsBean().getParametersControl()
					// .create();
					// Parameter param = ((Parameter) UISettings
					// .getUISettingsBean().getParametersControl()
					// .getCurrentRow());
					//
					tableViewer.refresh();

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		btnNewSeq.setBounds(201, 238, 68, 23);
		btnNewSeq.setText("New");

		btnSaveSeq = new Button(this, SWT.NONE);
		btnSaveSeq.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				if (parent instanceof RecordGroupCustomization) {

					RecordGroupCustomization rgCust = ((RecordGroupCustomization) parent);
					RecordGroupsControl recordGrpControl = UISettings
							.getUISettingsBean().getRecordGroupsControl();
					((RecordGroup) recordGrpControl.getObject())
							.setTable(rgCust.txtTableName.getText());
					((RecordGroup) recordGrpControl.getObject())
							.setSchema(rgCust.txtSchemaName.getText());

					((RecordGroup) recordGrpControl.getObject())
							.setComponentOrderBy(rgCust.btnComponentOrderBy
									.getSelection());
				}
				if (parent instanceof TriggerCustomization) {
					TriggerCustomization trgCust = (TriggerCustomization) parent;
					// System.out.println(trgCust.cmbCallType.getSelectionIndex());
					if (trgCust.cmbCallType.getSelectionIndex() == -1) {
						MessageBox msgBox = new MessageBox(parent.getShell(),
								SWT.ICON_WARNING);
						msgBox.setText("Warning!");
						msgBox.setMessage("Please select a Call Type.");
						msgBox.open();
					}
				}

				// ParametersControl paramControl =
				// UISettings.getUISettingsBean()
				// .getParametersControl();

				UISettings.getUISettingsBean().save();
			}
		});
		btnSaveSeq.setBounds(351, 238, 68, 23);
		btnSaveSeq.setText("Save");

		btnUpButton = new Button(this, SWT.NONE);
		btnUpButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				sequenceModelProvider.upIntheList(table.getSelectionIndex());
				tableViewer.refresh();
			}
		});
		btnUpButton.setBounds(425, 79, 54, 25);
		btnUpButton.setText("Up");

		btnDownButton = new Button(this, SWT.NONE);
		btnDownButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				sequenceModelProvider.downIntheList(table.getSelectionIndex());
				tableViewer.refresh();
			}
		});
		btnDownButton.setBounds(425, 143, 54, 25);
		btnDownButton.setText("Down");

		btnRemove = new Button(this, SWT.NONE);
		btnRemove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				sequenceModelProvider.delete(table.getSelectionIndex());
				tableViewer.refresh();
			}
		});
		btnRemove.setBounds(277, 238, 68, 23);
		btnRemove.setText("Remove");

	}

	private void createColumns(TableViewer viewer) {

		for (int i = 0; i < SequenceAnalysis.COLUMN_HEADERS.length; i++) {
			TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);
			column.getColumn().setText(SequenceAnalysis.COLUMN_HEADERS[i]);
			column.getColumn().setWidth(100);
			column.getColumn().setResizable(true);
			column.getColumn().setMoveable(true);
			column.setEditingSupport(new SequenceEditingSupport(viewer, i));
		}

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public void setEnabled(boolean value) {
		super.setEnabled(value);
		table.setEnabled(value);
		btnNewSeq.setEnabled(value);
		btnSaveSeq.setEnabled(value);

	}

	public void loadTable() {
		// ParametersControl paramControl = UISettings.getUISettingsBean()
		// .getParametersControl();
		// List<Parameter> params = paramControl.getIterator();
		// for (Parameter parameter : params) {
		// // fillTable(parameter);
		// }
		tableViewer.setContentProvider(new SequenceContentProvider());
		SequenceLabelProvider labelProvider = new SequenceLabelProvider();
		tableViewer.setLabelProvider(labelProvider);
		sequenceModelProvider = SequenceModelProvider.getInstance();
		tableViewer.setInput(sequenceModelProvider.getSequenceList());
		tableViewer.refresh();
	}

}
