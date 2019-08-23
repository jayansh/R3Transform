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

import com.converter.toolkit.ui.control.ParametersControl;
import com.converter.toolkit.ui.custom.Parameter;
import com.release3.tf.core.form.UISettings;
import com.release3.tf.customiz.model.canvas.CanvasAnalysis;
import com.release3.tf.customiz.model.canvas.CanvasSwitchContentProvider;
import com.release3.tf.customiz.model.canvas.CanvasSwitchEditingSupport;
import com.release3.tf.customiz.model.canvas.CanvasSwitchLabelProvider;
import com.release3.tf.customiz.model.param.ParameterModelProvider;

public class TriggerCustCanvasSwitchGroup extends Group {
	private Table tblCanvas;
	TableViewer tableViewer;
	private Button btnSave;
	private Button btnNew;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public TriggerCustCanvasSwitchGroup(Composite parent, int style) {
		super(parent, style);
		this.setText("Canvas Switching");
		tableViewer = new TableViewer(this, SWT.BORDER | SWT.FULL_SELECTION);
		tblCanvas = tableViewer.getTable();
		tblCanvas.setBounds(10, 20, 430, 92);
		tblCanvas.setHeaderVisible(true);
		tblCanvas.setLinesVisible(true);
		createColumns(tableViewer);

		btnSave = new Button(this, SWT.NONE);
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ParametersControl paramControl = UISettings.getUISettingsBean()
						.getParametersControl();

				UISettings.getUISettingsBean().save();
			}
		});
		btnSave.setBounds(298, 118, 68, 23);
		btnSave.setText("Save");

		btnNew = new Button(this, SWT.NONE);
		btnNew.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				Parameter param = new Parameter();
				if (CanvasAnalysis.getViewPortList()[0] != null
						&& CanvasAnalysis.getViewPortList()[0].indexOf("-") > -1) {
					param.setViewPort(CanvasAnalysis.getViewPortList()[0]
							.substring(CanvasAnalysis.getViewPortList()[0]
									.indexOf("-") + 1));
				}
				param.setFormname(CanvasAnalysis.getFormsArray()[0]);
				param.setViewPortForm(CanvasAnalysis.getFormViewPortList()[0]);
				param.setCanvas(CanvasAnalysis.getCanvasArray()[0]);

				ParameterModelProvider.getInstance().getParameterList()
						.add(param);

				tableViewer.refresh();
			}
		});
		btnNew.setBounds(224, 118, 68, 23);
		btnNew.setText("New");

		Button btnRemove = new Button(this, SWT.NONE);
		btnRemove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ParameterModelProvider.getInstance().delete(
						tblCanvas.getSelectionIndex());
				tableViewer.refresh();
			}
		});
		btnRemove.setBounds(372, 118, 68, 23);
		btnRemove.setText("Remove");
		
		Button btnParameters = new Button(this, SWT.NONE);
		btnParameters.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				CanvasSwitchParamDialog dialog = new CanvasSwitchParamDialog(getShell());
				dialog.open();
			}
		});
		btnParameters.setBounds(143, 118, 75, 25);
		btnParameters.setText("Parameters");

	}

	private void createColumns(TableViewer viewer) {

		for (int i = 0; i < CanvasAnalysis.COLUMN_HEADERS.length; i++) {
			TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);
			column.getColumn().setText(CanvasAnalysis.COLUMN_HEADERS[i]);
			column.getColumn().setWidth(100);
			column.getColumn().setResizable(true);
			column.getColumn().setMoveable(true);
			column.setEditingSupport(new CanvasSwitchEditingSupport(viewer, i));
		}
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public void loadTable() {
		tableViewer.setContentProvider(new CanvasSwitchContentProvider());
		CanvasSwitchLabelProvider labelProvider = new CanvasSwitchLabelProvider();
		tableViewer.setLabelProvider(labelProvider);
		tableViewer.setInput(ParameterModelProvider.getInstance()
				.getParameterList());
		tableViewer.refresh();
	}

	public void setEnabled(boolean value) {
		super.setEnabled(value);
		tblCanvas.setEnabled(value);
		btnNew.setEnabled(value);
		btnSave.setEnabled(value);
	}
}
