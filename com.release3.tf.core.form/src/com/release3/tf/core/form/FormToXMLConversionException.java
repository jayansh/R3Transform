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
package com.release3.tf.core.form;

/**
 * 
 * @author jayansh
 * Exception class to handle error during form to xml conversion process.
 */
public class FormToXMLConversionException extends Exception {
	public FormToXMLConversionException() {
		super();
	}
	public FormToXMLConversionException(String message) {
		super(message);
	}
	public FormToXMLConversionException(Throwable t) {
		super(t);
	}
	public FormToXMLConversionException(String message, Throwable t) {
		super(message,t);
	}
}
