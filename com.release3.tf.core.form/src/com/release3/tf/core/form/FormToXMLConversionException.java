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
