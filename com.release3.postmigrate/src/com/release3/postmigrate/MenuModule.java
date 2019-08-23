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
