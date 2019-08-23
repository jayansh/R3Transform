package com.release3.postmigrate;

public class MenuItem {
	private String name;
	private Menu subMenu;
	private String subMenuName;
	private String commandType;
	private String visualAttributeName;
	private String label;
	private String menuItemCode;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Menu getSubMenu() {
		return subMenu;
	}

	public void setSubMenu(Menu subMenu) {
		this.subMenu = subMenu;
	}

	public String getSubMenuName() {
		return subMenuName;
	}

	public void setSubMenuName(String subMenuName) {
		this.subMenuName = subMenuName;
	}

	public String getCommandType() {
		return commandType;
	}

	public void setCommandType(String commandType) {
		this.commandType = commandType;
	}

	public String getVisualAttributeName() {
		return visualAttributeName;
	}

	public void setVisualAttributeName(String visualAttributeName) {
		this.visualAttributeName = visualAttributeName;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getMenuItemCode() {
		return menuItemCode;
	}

	public void setMenuItemCode(String menuItemCode) {
		this.menuItemCode = menuItemCode;
	}
}
