package com.release3.transform.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.layout.grouplayout.GroupLayout;
import org.eclipse.wb.swt.layout.grouplayout.LayoutStyle;

public class RecordGroupCustomization extends Composite {

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */

//	final CustomParameterGroup groupParam = new CustomParameterGroup(this,
//			SWT.NONE);
	final ParameterCustomizationGroup groupParam = new ParameterCustomizationGroup(this,
			SWT.NONE);
	public Text txtTableName = new Text(this, SWT.BORDER);
	public Text txtSchemaName = new Text(this, SWT.BORDER);
	public Button btnComponentOrderBy = new Button(this, SWT.CHECK);

	public RecordGroupCustomization(Composite parent, int style) {
		super(parent, style);
//		groupParam.btnLoadParameters.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				
//			}
//		});
		groupParam.setText("Parameters");
//		txtTableName.setText(UISettings.getUISettingsBean().getRecordGroupsControl().getObject().toString());	
		Label lblTableName = new Label(this, SWT.NONE);
		lblTableName.setText("Table Name:");
		
		Label lblSchemaName = new Label(this, SWT.NONE);
		lblSchemaName.setText("Schema Name:");
		
		
		btnComponentOrderBy.setText("Component Order by");
		
		
		
		
	
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(GroupLayout.TRAILING)
				.add(groupLayout.createSequentialGroup()
					.addContainerGap(16, Short.MAX_VALUE)
					.add(groupLayout.createParallelGroup(GroupLayout.LEADING)
						.add(groupParam, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.add(groupLayout.createSequentialGroup()
							.addPreferredGap(LayoutStyle.RELATED)
							.add(groupLayout.createParallelGroup(GroupLayout.LEADING)
								.add(groupLayout.createSequentialGroup()
									.add(groupLayout.createParallelGroup(GroupLayout.LEADING)
										.add(lblTableName)
										.add(lblSchemaName))
									.add(18)
									.add(groupLayout.createParallelGroup(GroupLayout.LEADING)
										.add(txtTableName, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE)
										.add(txtSchemaName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
								.add(btnComponentOrderBy, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(GroupLayout.TRAILING)
				.add(groupLayout.createSequentialGroup()
					.addContainerGap()
					.add(groupLayout.createParallelGroup(GroupLayout.LEADING)
						.add(groupLayout.createSequentialGroup()
							.add(3)
							.add(lblTableName)
							.add(10)
							.add(lblSchemaName))
						.add(groupLayout.createSequentialGroup()
							.add(txtTableName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.add(4)
							.add(txtSchemaName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(LayoutStyle.UNRELATED)
					.add(btnComponentOrderBy)
					.addPreferredGap(LayoutStyle.RELATED, 13, Short.MAX_VALUE)
					.add(groupParam, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		groupLayout.linkSize(new Control[] {txtTableName, txtSchemaName}, GroupLayout.HORIZONTAL);
		setLayout(groupLayout);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public void loadParamTable() {
		groupParam.loadTable();
	}
	
}
