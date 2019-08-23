package com.release3.plsql.analysis.property.model;

import com.oracle.xmlns.forms.Trigger;

public class FormProperties {

	private Trigger trigger;
	private String blockName;
	private String itemName;
	private String formName;

	public Trigger getTrigger() {
		return trigger;
	}

	public void setTrigger(Trigger trigger) {
		this.trigger = trigger;
	}

	public String getText() {
		return this.trigger.getTriggerText();
	}

	public String getBlockName() {
		return blockName;
	}

	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public String getJavaMethodSuffix() {
		String methodSuffix = "";
		if (blockName != null) {
			methodSuffix = blockName;
		}
		if (itemName != null ) {
			methodSuffix = methodSuffix + "_" + itemName;
		}

		return methodSuffix;
	}
}
