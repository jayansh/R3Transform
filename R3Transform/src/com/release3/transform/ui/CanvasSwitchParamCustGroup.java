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

import com.converter.toolkit.ui.custom.CanvasSwitchParameter;
import com.release3.tf.core.form.UISettings;
import com.release3.tf.customiz.model.param.canvas.CanvasSwitchParameterAnalysis;
import com.release3.tf.customiz.model.param.canvas.CanvasSwitchParameterContentProvider;
import com.release3.tf.customiz.model.param.canvas.CanvasSwitchParameterEditingSupport;
import com.release3.tf.customiz.model.param.canvas.CanvasSwitchParameterLabelProvider;
import com.release3.tf.customiz.model.param.canvas.CanvasSwitchParameterModelProvider;

public class CanvasSwitchParamCustGroup extends Group {
	private final Table table;
	TableViewer tableViewer;
	private CanvasSwitchParameterModelProvider parameterModelProvider;

	private Button btnNewParam;
	private Button btnSaveParam;
	public Button btnLoadParameters;
	private Button btnRemove;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public CanvasSwitchParamCustGroup(final Composite parent, int style) {
		super(parent, style);
		this.setText("Parameter");
		tableViewer = new TableViewer(this, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setBounds(10, 20, 600, 214);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		createColumns(tableViewer);

		btnNewParam = new Button(this, SWT.NONE);
		btnNewParam.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				try {
					CanvasSwitchParameter param = new CanvasSwitchParameter();
					param.setSourceBlock(CanvasSwitchParameterAnalysis
							.getBlockArray()[0]);
					param.setSourceItem(CanvasSwitchParameterAnalysis
							.getItemArray(null)[0]);
					param.setDestinationBlock(CanvasSwitchParameterAnalysis
							.getBlockArray()[0]);
					param.setDestinationItem(CanvasSwitchParameterAnalysis
							.getItemArray(null)[0]);
					// param.setName("");
					CanvasSwitchParameterModelProvider.getInstance()
							.getParameterList().add(param);

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
		btnNewParam.setBounds(395, 240, 68, 23);
		btnNewParam.setText("New");

		btnSaveParam = new Button(this, SWT.NONE);
		btnSaveParam.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				// if (parent instanceof RecordGroupCustomization) {
				//
				// RecordGroupCustomization rgCust = ((RecordGroupCustomization)
				// parent);
				// RecordGroupsControl recordGrpControl = UISettings
				// .getUISettingsBean().getRecordGroupsControl();
				// ((RecordGroup) recordGrpControl.getObject())
				// .setTable(rgCust.txtTableName.getText());
				// ((RecordGroup) recordGrpControl.getObject())
				// .setSchema(rgCust.txtSchemaName.getText());
				//
				// ((RecordGroup) recordGrpControl.getObject())
				// .setComponentOrderBy(rgCust.btnComponentOrderBy
				// .getSelection());
				// }
				// if (parent instanceof TriggerCustomization) {
				// TriggerCustomization trgCust = (TriggerCustomization) parent;
				// //
				// System.out.println(trgCust.cmbCallType.getSelectionIndex());
				// if (trgCust.cmbCallType.getSelectionIndex() == -1) {
				// MessageBox msgBox = new MessageBox(parent.getShell(),
				// SWT.ICON_WARNING);
				// msgBox.setText("Warning!");
				// msgBox.setMessage("Please select a Call Type.");
				// msgBox.open();
				// }
				// }

				// ParametersControl paramControl =
				// UISettings.getUISettingsBean()
				// .getParametersControl();

				UISettings.getUISettingsBean().save();
			}
		});
		btnSaveParam.setBounds(543, 240, 68, 23);
		btnSaveParam.setText("Save");

		// btnLoadParameters = new Button(this, SWT.NONE);
		// btnLoadParameters.addSelectionListener(new SelectionAdapter() {
		// @Override
		// public void widgetSelected(SelectionEvent e) {
		// Object obj = UISettings.getUISettingsBean()
		// .getTriggersControl().getObject();
		// String formName = Settings.getSettings().getFmbFile();
		// formName = formName.substring(0, formName.indexOf('.'));
		// UISettings.getUISettingsBean().getParametersControl()
		// .loadParameters(obj, formName);
		// loadTable();
		//
		// }
		// });
		// btnLoadParameters.setBounds(299, 270, 120, 23);
		// btnLoadParameters.setText("Load Parameters");

		Button btnUpButton = new Button(this, SWT.NONE);
		btnUpButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				parameterModelProvider.upIntheList(table.getSelectionIndex());
				tableViewer.refresh();
			}
		});
		btnUpButton.setBounds(616, 80, 54, 25);
		btnUpButton.setText("Up");

		Button btnDownButton = new Button(this, SWT.NONE);
		btnDownButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				parameterModelProvider.downIntheList(table.getSelectionIndex());
				tableViewer.refresh();
			}
		});
		btnDownButton.setBounds(616, 149, 54, 25);
		btnDownButton.setText("Down");

		btnRemove = new Button(this, SWT.NONE);
		btnRemove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				parameterModelProvider.delete(table.getSelectionIndex());
				tableViewer.refresh();
			}
		});
		btnRemove.setText("Remove");
		btnRemove.setBounds(469, 240, 68, 23);

	}

	private void createColumns(TableViewer viewer) {

		for (int i = 0; i < CanvasSwitchParameterAnalysis.COLUMN_HEADERS.length; i++) {
			TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);
			column.getColumn().setText(
					CanvasSwitchParameterAnalysis.COLUMN_HEADERS[i]);
			column.getColumn().setWidth(100);
			column.getColumn().setResizable(true);
			column.getColumn().setMoveable(true);
			column.setEditingSupport(new CanvasSwitchParameterEditingSupport(
					viewer, i));
		}

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public void setEnabled(boolean value) {
		super.setEnabled(value);
		table.setEnabled(value);
		btnNewParam.setEnabled(value);
		btnSaveParam.setEnabled(value);
		btnLoadParameters.setEnabled(value);
	}

	public void loadTable() {
		// ParametersControl paramControl = UISettings.getUISettingsBean()
		// .getParametersControl();
		// List<Parameter> params = paramControl.getIterator();
		// for (Parameter parameter : params) {
		// // fillTable(parameter);
		// }
		tableViewer
				.setContentProvider(new CanvasSwitchParameterContentProvider());
		CanvasSwitchParameterLabelProvider labelProvider = new CanvasSwitchParameterLabelProvider();
		tableViewer.setLabelProvider(labelProvider);
		parameterModelProvider = CanvasSwitchParameterModelProvider
				.getInstance();
		tableViewer.setInput(parameterModelProvider.getParameterList());
		tableViewer.refresh();
	}
}
