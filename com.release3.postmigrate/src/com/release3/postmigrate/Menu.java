package com.release3.postmigrate;

import java.util.List;

public class Menu {
	private String menuName;
	private List<MenuItem> menuItems;
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public List<MenuItem> getMenuItems() {
		return menuItems;
	}
	public void setMenuItems(List<MenuItem> menuItems) {
		this.menuItems = menuItems;
	}
	
	
}
