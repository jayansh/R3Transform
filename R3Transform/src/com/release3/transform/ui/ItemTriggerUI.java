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

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.release3.transform.constants.UIConstants;

public class ItemTriggerUI extends Composite {

	private Label lblTriggerName;
	private Combo combo;
	private Button btnSubmit;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ItemTriggerUI(Composite parent, int style) {
		super(parent, style);
		
		lblTriggerName = new Label(this, SWT.NONE);
		lblTriggerName.setBounds(10, 10, 86, 20);
		lblTriggerName.setText("Trigger Name:");
		
		combo = new Combo(this, SWT.NONE);
		combo.setItems(UIConstants.ITEM_TRIGGER_COMBO_OPTIONS);
		combo.setBounds(106, 10, 128, 21);
		
		btnSubmit = new Button(this, SWT.NONE);
		btnSubmit.setBounds(248, 10, 68, 23);
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
