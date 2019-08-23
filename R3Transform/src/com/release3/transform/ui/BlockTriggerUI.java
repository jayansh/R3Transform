package com.release3.transform.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.release3.transform.constants.UIConstants;

public class BlockTriggerUI extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	private Button btnSubmit;
	private Combo combo;
	private Label lblTriggerName; 
	public BlockTriggerUI(Composite parent, int style) {
		super(parent, style);
		
		Label lblTriggerName = new Label(this, SWT.NONE);
		lblTriggerName.setBounds(10, 22, 86, 19);
		lblTriggerName.setText("Trigger Name:");
		
		combo = new Combo(this, SWT.NONE);
		
		combo.setItems(UIConstants.BLOCK_TRIGGER_COMBO_OPTIONS);
		combo.setBounds(102, 21, 151, 21);
		
		btnSubmit = new Button(this, SWT.NONE);
		
		
		btnSubmit.setBounds(270, 20, 68, 23);
		btnSubmit.setText("Submit");

	}

	public Button getSubmitButton(){
		return btnSubmit;
	}
	
	public Combo getCombo(){
		return combo;
	}
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
