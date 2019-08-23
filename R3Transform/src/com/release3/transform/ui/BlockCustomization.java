package com.release3.transform.ui;

import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.layout.grouplayout.GroupLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.wb.swt.layout.grouplayout.LayoutStyle;
import org.eclipse.swt.widgets.Control;

public class BlockCustomization extends Composite {

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */

	// final CustomParameterGroup groupParam = new CustomParameterGroup(this,
	// SWT.NONE);
	final ParameterCustomizationGroup groupParam = new ParameterCustomizationGroup(
			this, SWT.NONE);

	SequenceCustomization sequenceCustomizationGrp = new SequenceCustomization(
			this, SWT.NONE);

	public BlockCustomization(Composite parent, int style) {
		super(parent, style);
		
		
		groupParam.btnLoadParameters.setVisible(false);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(GroupLayout.TRAILING)
				.add(GroupLayout.LEADING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.add(groupLayout.createParallelGroup(GroupLayout.LEADING)
						.add(groupParam, GroupLayout.PREFERRED_SIZE, 481, GroupLayout.PREFERRED_SIZE)
						.add(sequenceCustomizationGrp))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(GroupLayout.LEADING)
				.add(groupLayout.createSequentialGroup()
					.add(groupParam, GroupLayout.PREFERRED_SIZE, 309, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(LayoutStyle.RELATED)
					.add(sequenceCustomizationGrp, GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
					.addContainerGap())
		);
		setLayout(groupLayout);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public void loadTables() {
		//Load Parameter Table
		groupParam.loadTable();
		//Load Sequence table.
		sequenceCustomizationGrp.loadTable();
	}
}
