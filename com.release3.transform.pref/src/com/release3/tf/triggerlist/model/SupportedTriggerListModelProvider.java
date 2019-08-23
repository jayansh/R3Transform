package com.release3.tf.triggerlist.model;

import java.util.ArrayList;
import java.util.List;

import com.converter.toolkit.ui.JAXBXMLLoader;
import com.release3.triggerlist.SupportedTriggerType;

public class SupportedTriggerListModelProvider {
	private List<SupportedTriggerType> triggerList;
	private static SupportedTriggerListModelProvider content;

	private SupportedTriggerListModelProvider() {
		triggerList = new ArrayList<SupportedTriggerType>();
	}

	public static SupportedTriggerListModelProvider getInstance() {
		if (content != null) {
			return content;
		} else {
			content = new SupportedTriggerListModelProvider();
			return content;
		}
	}


	public List<SupportedTriggerType> getSupportedTriggerList() {
		triggerList = JAXBXMLLoader.getInstance().getTriggerList().getSupportedTrigger();
		return triggerList;
	}
}
