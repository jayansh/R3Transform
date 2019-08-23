package com.release3.transform.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.wb.swt.layout.grouplayout.GroupLayout;
import org.eclipse.wb.swt.layout.grouplayout.LayoutStyle;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.converter.toolkit.ui.custom.Trigger;
import com.release3.tf.core.form.UISettings;
import com.release3.transform.constants.UIConstants;

public class TriggerCustomization extends Composite {
	public Label lblPlsql;
	public Text txtPLSQLProcedure;
	public Combo cmbCallType;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	final TriggerCustCanvasSwitchGroup groupCanvas = new TriggerCustCanvasSwitchGroup(
			this, SWT.NONE);
	// final CustomParameterGroup groupParam = new CustomParameterGroup(this,
	// SWT.NONE);
	final ParameterCustomizationGroup groupParam = new ParameterCustomizationGroup(
			this, SWT.NONE);
	final TriggerCustRefeshBlockGroup groupRefreshBlk = new TriggerCustRefeshBlockGroup(
			this, SWT.NONE);
	public Label lblJavaMethod;
	public Text txtJavaMethod;

	public TriggerCustomization(Composite parent, int style) {
		super(parent, style);

		final Group group = new Group(this, SWT.NONE);

		lblPlsql = new Label(group, SWT.NONE);
		lblPlsql.setBounds(8, 14, 93, 13);
		lblPlsql.setText("PL/SQL Procedure");

		txtPLSQLProcedure = new Text(group, SWT.BORDER);
		txtPLSQLProcedure.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				((Trigger) UISettings.getUISettingsBean().getTriggersControl()
						.getObject()).setPlsql(txtPLSQLProcedure.getText());
			}
		});
		txtPLSQLProcedure.setBounds(109, 11, 162, 19);

		Label lblCallType = new Label(group, SWT.NONE);
		lblCallType.setBounds(279, 14, 49, 13);
		lblCallType.setText("Call Type");

		groupCanvas.setEnabled(false);

		groupRefreshBlk.setEnabled(false);

		cmbCallType = new Combo(group, SWT.NONE);
		cmbCallType.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// System.out.println(cmbCallType.getSelectionIndex());
				// enabled only when canvasSwitcher is selected.
				if (cmbCallType.getSelectionIndex() == 11) {
					groupParam.setEnabled(false);
					groupCanvas.setEnabled(true);
					groupCanvas.loadTable();
				} else {
					groupParam.setEnabled(true);
					groupCanvas.setEnabled(false);

				}
				// enabled only when plsqlCall is selected.
				if (cmbCallType.getSelectionIndex() == 0) {
					groupRefreshBlk.setEnabled(true);
					groupRefreshBlk.loadRefreshBlockTable();

				} else {
					groupRefreshBlk.setEnabled(false);
				}
				((Trigger) UISettings.getUISettingsBean().getTriggersControl()
						.getObject()).setMethodType(cmbCallType
						.getItem(cmbCallType.getSelectionIndex()));
			}

		});
		cmbCallType.setItems(UIConstants.TRIGGER_CUSTMIZATION_COMBO_OPTIONS);
		cmbCallType.setBounds(336, 10, 93, 19);

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(GroupLayout.LEADING)
						.add(groupLayout
								.createSequentialGroup()
								.add(groupLayout
										.createParallelGroup(
												GroupLayout.LEADING)
										.add(groupLayout
												.createSequentialGroup()
												.add(10)
												.add(groupLayout
														.createParallelGroup(
																GroupLayout.LEADING)
														.add(groupLayout
																.createSequentialGroup()
																.add(groupParam,
																		GroupLayout.PREFERRED_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		LayoutStyle.UNRELATED)
																.add(groupRefreshBlk,
																		GroupLayout.PREFERRED_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE))
														.add(group,
																GroupLayout.PREFERRED_SIZE,
																438,
																GroupLayout.PREFERRED_SIZE)))
										.add(groupLayout
												.createSequentialGroup()
												.addContainerGap()
												.add(groupCanvas,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE)))
								.addContainerGap(59, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				GroupLayout.LEADING).add(
				groupLayout
						.createSequentialGroup()
						.add(group, GroupLayout.PREFERRED_SIZE, 41,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.RELATED)
						.add(groupLayout
								.createParallelGroup(GroupLayout.LEADING)
								.add(groupParam, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.add(groupRefreshBlk,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.add(18)
						.add(groupCanvas, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap(32, Short.MAX_VALUE)));

		txtJavaMethod = new Text(group, SWT.BORDER);
		txtJavaMethod.setBounds(109, 11, 162, 19);

		lblJavaMethod = new Label(group, SWT.NONE);
		lblJavaMethod.setBounds(8, 14, 93, 13);
		lblJavaMethod.setText("Java Method: ");
		txtJavaMethod.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				((Trigger) UISettings.getUISettingsBean().getTriggersControl()
						.getObject()).setJavaMethod(txtJavaMethod.getText());
			}
		});
		setLayout(groupLayout);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public void loadParamTable() {
		// enabled only when canvasSwitcher is selected.
		if (cmbCallType.getSelectionIndex() == 11) {
			groupCanvas.loadTable();
			groupParam.setEnabled(false);
			groupCanvas.setEnabled(true);
		} else {
			groupParam.loadTable();
			groupParam.setEnabled(true);
			groupCanvas.setEnabled(false);
		}

		if (cmbCallType.getSelectionIndex() == 0) {
			groupRefreshBlk.setEnabled(true);
			groupRefreshBlk.loadRefreshBlockTable();
		}
	}
}
