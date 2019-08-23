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
package com.release3.transform;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;

@Deprecated
public class DocumentationComposite extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public DocumentationComposite(Composite parent, int style) {
		super(parent, style);
		
		Label lblDocuments = new Label(this, SWT.NONE);
		lblDocuments.setBounds(10, 10, 224, 21);
		lblDocuments.setText("Documents:");
		
		Label lblHowToInstall = new Label(this, SWT.NONE);
		lblHowToInstall.setBounds(10, 37, 200, 21);
		lblHowToInstall.setText("HOW TO - Installing TransForm");
		
		Label lblHowTo = new Label(this, SWT.NONE);
		lblHowTo.setBounds(10, 74, 365, 13);
		lblHowTo.setText("HOW TO - Set Up jDeveloper Migration and Application Environment");
		
		Label lblHowTo_1 = new Label(this, SWT.NONE);
		lblHowTo_1.setBounds(10, 107, 365, 13);
		lblHowTo_1.setText("HOW TO - Using the TransForm License Generator (Batch process)");
		
		Label lblHowTo_2 = new Label(this, SWT.NONE);
		lblHowTo_2.setBounds(10, 141, 49, 13);
		lblHowTo_2.setText("HOW TO - Migrating using the TransForm Toolkit");
		
		Label label_3 = new Label(this, SWT.NONE);
		label_3.setBounds(10, 182, 49, 13);
		label_3.setText("New Label");
		
		Label label_4 = new Label(this, SWT.NONE);
		label_4.setBounds(10, 228, 49, 13);
		label_4.setText("New Label");
		
		Label label_5 = new Label(this, SWT.NONE);
		label_5.setBounds(10, 267, 49, 13);
		label_5.setText("New Label");
		
		Label label_6 = new Label(this, SWT.NONE);
		label_6.setBounds(10, 310, 49, 13);
		label_6.setText("New Label");

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
