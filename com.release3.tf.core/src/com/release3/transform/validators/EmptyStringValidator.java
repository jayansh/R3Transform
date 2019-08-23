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
package com.release3.transform.validators;

import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.fieldassist.ControlDecoration;

public class EmptyStringValidator 
	implements IValidator {

		private final String message;
		private final ControlDecoration controlDecoration;

		public EmptyStringValidator(String message,
				ControlDecoration controlDecoration) {
			super();
			this.message = message;
			this.controlDecoration = controlDecoration;
		}

		@Override
		public IStatus validate(Object value) {
			if (value instanceof String) {
				String s = (String) value;
				// We check if the string is empty
				if (s.length() > 0) {
					controlDecoration.hide();
					return Status.OK_STATUS;
				} else {
					controlDecoration.show();
					return ValidationStatus.error(message);
				}
			} else {
				throw new RuntimeException(
						"Not supposed to be called for non-strings.");
			}
		}
	}


