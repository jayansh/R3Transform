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
package com.release3.tf.plsql.noncrud.model;

import org.eclipse.core.databinding.AggregateValidationStatus;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

import com.release3.javasql.JavaPlSqlType;
import com.release3.tf.plsql.process.JavaEventValidation;
import com.release3.tf.plsql.process.PlSqlTextDialog;
import com.release3.transform.validators.JavaMethodOptionValidator;

public class NonCRUDEditingSupport extends EditingSupport {
	private CellEditor editor;
	private int column;
	private ColumnViewer viewer;
	private String errorMessage = "Only push buttons can have actionListener as option. Please select other option.";

	private String[] javaMethodArray = { "actionListener", "onblur",
			"Plain Java Method" };
	private JavaEventValidation javaEventValidator;

	public NonCRUDEditingSupport(ColumnViewer viewer, int column) {
		super(viewer);
		switch (column) {
		case 3:
			// editor = new TextCellEditor(((TableViewer) viewer).getTable());
			editor = new DialogCellEditor(((TableViewer) viewer).getTable()) {

				@Override
				protected Object openDialogBox(Control cellEditorWindow) {
					String value = (String) getValue();
					PlSqlTextDialog ftDialog = new PlSqlTextDialog(
							cellEditorWindow.getShell(), value);

					if (value != null) {
						int result = ftDialog.open();
						value = ftDialog.getText();
					}
					return value;
				}
			};
			break;
		case 4:
			editor = new CheckboxCellEditor(((TableViewer) viewer).getTable());
			break;
		case 5:
			editor = new CheckboxCellEditor(((TableViewer) viewer).getTable());
			break;
		case 6:
			editor = new ComboBoxCellEditor(((TableViewer) viewer).getTable(),
					javaMethodArray);
			this.javaEventValidator = new JavaEventValidation(
					NonCRUDModelProvider.getInstance().getNonCRUDAnalysis()
							.getMigrationForm());
			break;

		}
		this.viewer = viewer;
		this.column = column;

	}

	@Override
	protected CellEditor getCellEditor(Object element) {

		return editor;
	}

	@Override
	protected boolean canEdit(Object element) {
		return true;
	}

	@Override
	protected Object getValue(Object element) {
		JavaPlSqlType nonCrud = (JavaPlSqlType) element;
		System.out.println("getValue" + element);
		switch (this.column) {
		// case 0:
		// String blockName = crud.getBlockName();
		// return blockName;
		// case 1:
		// String itemName = crud.getItemName();
		// return itemName;
		// case 2:
		// String triggerName = crud.getPlSqlName();
		// return triggerName;
		case 3:
			String plsqlText = nonCrud.getJavaPlSqlText();
			return plsqlText;
		case 4:
			// if (nonCrud.isToBeMigrate() == null) {
			// return 0;
			// } else if (nonCrud.isToBeMigrate()) {
			// return 1;
			// } else {
			// return 0;
			// }
			return nonCrud.isToBeMigrate();
		case 5:
			if (nonCrud.isMoveToDB() == null) {
				return false;
			} else {
				return nonCrud.isMoveToDB();
			}
		case 6:
			if (nonCrud.isToBeMigrate()
					&& (nonCrud.isMoveToDB() == null || !nonCrud.isMoveToDB())) {
				String javaMethod = nonCrud.getJavaMethod();
				int index = match(javaMethod);
				if (index > -1) {
					return index;
				}
			}
			return 0;

		default:
			return null;
		}
	}

	@Override
	protected void setValue(Object element, Object value) {
		JavaPlSqlType nonCrud = (JavaPlSqlType) element;
		System.out.println("setValue " + value.toString());
		switch (this.column) {
		// case 0:
		// crud.setBlockName((String) value);
		// break;
		// case 1:
		// crud.setItemName((String) value);
		// break;
		// case 2:
		// crud.setPlSqlName((String) value);
		//
		// break;
		case 3:
			nonCrud.setJavaPlSqlText((String) value);
			break;
		case 4:
			// Integer index = (Integer) value;
			// if (index == 1) {
			// nonCrud.setToBeMigrate(true);
			// } else {
			// nonCrud.setToBeMigrate(false);
			// }
			Boolean boolVal = (Boolean) value;
			nonCrud.setToBeMigrate(boolVal);

			break;
		case 5:
			if (nonCrud.isToBeMigrate()) {
				Boolean moveToDB = (Boolean) value;
				nonCrud.setMoveToDB(moveToDB);
			}
			break;
		case 6:
			if (nonCrud.isToBeMigrate()
					&& (nonCrud.isMoveToDB() == null || !nonCrud.isMoveToDB())) {
				Integer javaMethodIndex = (Integer) value;

				nonCrud.setJavaMethod(javaMethodArray[javaMethodIndex]);
				// if (javaMethodIndex == 0
				// && !getJavaEventValidator().buttonItemValidation(
				// nonCrud.getBlockName(), nonCrud.getItemName())) {
				// MessageDialog.openError(Display.getDefault()
				// .getActiveShell(), "Error", errorMessage);
				// nonCrud.setJavaMethod(null);
				// }
			}
			break;

		default:
			break;
		}

		getViewer().update(element, null);

	}

	private int match(String javaMethod) {
		for (int i = 0; i < javaMethodArray.length; i++) {
			if (javaMethodArray[i] == javaMethod
					|| javaMethodArray[i].equalsIgnoreCase(javaMethod)) {
				return i;
			}
		}
		return -1;
	}

	private ControlDecoration createDecorator(Control control, String message) {
		ControlDecoration controlDecoration = new ControlDecoration(control,
				SWT.LEFT | SWT.TOP);
		controlDecoration.setDescriptionText(message);
		FieldDecoration fieldDecoration = FieldDecorationRegistry.getDefault()
				.getFieldDecoration(FieldDecorationRegistry.DEC_ERROR);
		controlDecoration.setImage(fieldDecoration.getImage());
		return controlDecoration;
	}

	private void bindValuesJavaOption(ControlDecoration controlDecoration,
			Control combo) {

		// The DataBindingContext object will manage the databindings
		DataBindingContext bindingContext = new DataBindingContext();
		// First we bind the text field to the model
		// Here we define the UpdateValueStrategy

		UpdateValueStrategy update = new UpdateValueStrategy();

		update.setAfterConvertValidator(new JavaMethodOptionValidator(
				errorMessage, controlDecoration));

		// We listen to all errors via this binding
		final IObservableValue uiElementJavaOption = SWTObservables
				.observeText(combo);
		// This one listenes to all changes
		bindingContext.bindValue(uiElementJavaOption,
				new AggregateValidationStatus(bindingContext.getBindings(),
						AggregateValidationStatus.MAX_SEVERITY), null, null);

		uiElementJavaOption.addChangeListener(new IChangeListener() {
			@Override
			public void handleChange(ChangeEvent event) {
				System.out.println("uiElementJavaOption.getValue()"
						+ uiElementJavaOption.getValue());
				if (uiElementJavaOption.getValue().equals(javaMethodArray[0])) {

					// if(getJavaEventValidator().buttonItemValidation(blockName,
					// itemName))
					// setMessage("OK");

				} else {
					// setErrorMessage(errorMessage);
				}
			}
		});

	}

	public JavaEventValidation getJavaEventValidator() {
		return javaEventValidator;
	}

}
