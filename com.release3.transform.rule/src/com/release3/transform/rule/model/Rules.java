package com.release3.transform.rule.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Deprecated
public class Rules {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
