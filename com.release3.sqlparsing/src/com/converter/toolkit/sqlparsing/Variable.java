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
package com.converter.toolkit.sqlparsing;

import org.apache.commons.lang.StringUtils;

/**
 * Represents a parsed bind variable. Holds the variable attributes: original
 * name, replacement name and type.
 * 
 * A variable is considered to be
 * <code>OUT</OUT> if it is used as the left part of an
 * assignment statement (<code>:=</code>), and <code>IN</code> otherwise.
 * 
 * Instances of this class are generated by the <code>CodeProcessor</code>
 * class.
 * 
 * @author ExpressJava
 * 
 */
public class Variable {

	/**
	 * The <code>IN</code> type.
	 */
	public static final String TYPE_IN = "IN";

	/**
	 * The <code>OUT</code> type.
	 */
	public static final String TYPE_OUT = "OUT";

	/**
	 * The bind variable's original name.
	 */
	private String name;

	/**
	 * This bind variable's IN/OUT type.
	 */
	private String type;

	/**
	 * The bind variable's new replacement name.
	 */
	private String replacedName;

	private String block;
	private String item;
	private String dataType;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            original name
	 * @param type
	 *            variable type (IN/OUT)
	 * @param replacedName
	 *            replacement name
	 */
	public Variable(String name, String type, String replacedName) {
		this.name = name;
		this.type = type;
		this.replacedName = replacedName;

		if ((name.indexOf(":") != -1))
			name = name.substring(1);

		if (name.indexOf(".") != -1)
			block = StringUtils.substringBefore(name, ".").toUpperCase();
		if ((block != null) && (block.indexOf(":") != -1))
			block = block.substring(1);
		if (block == null)
			item = name.toUpperCase();
		else
			item = StringUtils.substringAfter(name, ".").toUpperCase();

	}

	/**
	 * The bind variable's original name.
	 * 
	 * @return The bind variable's original name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * This bind variable's IN/OUT type.
	 * 
	 * @return This bind variable's IN/OUT type.
	 * 
	 */
	public String getType() {
		return type;
	}

	/**
	 * The bind variable's new replacement name.
	 * 
	 * @return The bind variable's new replacement name.
	 */
	public String getReplacedName() {
		return replacedName;
	}

	/**
	 * To instances of this class are considered equal if hold the same name.
	 */
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Variable && ((Variable) obj).name != null && ((Variable) obj).name
				.equals(name));
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public String getBlock() {
		return block;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getItem() {
		return item;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

}
