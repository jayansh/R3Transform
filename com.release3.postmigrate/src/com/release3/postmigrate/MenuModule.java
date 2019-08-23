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
package com.release3.postmigrate;

public class MenuModule {
	private String name;
	private Menu menu;
	private String menuFilename;
	private boolean sharedLibrary;
	private String startupCode;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public String getMenuFilename() {
		return menuFilename;
	}

	public void setMenuFilename(String menuFilename) {
		this.menuFilename = menuFilename;
	}

	public boolean isSharedLibrary() {
		return sharedLibrary;
	}

	public void setSharedLibrary(boolean sharedLibrary) {
		this.sharedLibrary = sharedLibrary;
	}

	public String getStartupCode() {
		return startupCode;
	}

	public void setStartupCode(String startupCode) {
		this.startupCode = startupCode;
	}
}
