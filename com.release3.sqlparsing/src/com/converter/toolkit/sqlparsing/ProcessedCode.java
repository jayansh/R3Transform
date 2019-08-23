package com.converter.toolkit.sqlparsing;

import java.util.Vector;

/**
 * The result of the parsing process. Holds the processed code along with a
 * vector of variables
 * 
 * Instances of this class are created by the <code>CodeProcessor</code> class.
 * 
 * @author ExpressJava
 * 
 */
public class ProcessedCode {

	/**
	 * The collection of bind variables as instances of the
	 * <code>Variable</code> class.
	 */
	private Vector<Variable> variables;

	/**
	 * The processed code with the bind variables replaced.
	 */
	private String code;

	/**
	 * Constructor.
	 * 
	 * @param variables
	 *            The collection of bind variables as instances of the
	 *            <code>Variable</code> class.
	 * @param code
	 *            The processed code with the bind variables replaced.
	 */
	ProcessedCode(Vector<Variable> variables, String code) {
		super();
		this.variables = variables;
		this.code = code;
	}

	/**
	 * The collection of bind variables as instances of the
	 * <code>Variable</code> class.
	 * 
	 * @return The collection of bind variables as instances of the
	 *         <code>Variable</code> class.
	 */
	public Vector<Variable> getVariables() {
		return variables;
	}

	/**
	 * The processed code with the bind variables replaced.
	 * 
	 * @return The processed code with the bind variables replaced.
	 */
	public String getCode() {
		return code.replaceAll("&#10;","\n");
	}

}
