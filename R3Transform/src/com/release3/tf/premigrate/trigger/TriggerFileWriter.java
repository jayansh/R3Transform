package com.release3.tf.premigrate.trigger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.converter.toolkit.ui.FormsObject;
import com.oracle.xmlns.forms.Block;
import com.oracle.xmlns.forms.Item;
import com.oracle.xmlns.forms.Trigger;

public class TriggerFileWriter {
	FileWriter fileWriter;

	public TriggerFileWriter(String triggerTxtFilePath, boolean append)
			throws IOException {
		File triggerFile = new File(triggerTxtFilePath);
		if (!append) {
			triggerFile.delete();
		}
		fileWriter = new FileWriter(triggerFile, true);

	}

	public void triggerTextFileWriter(FormsObject object) throws IOException {

		if (object instanceof Trigger) {
			Trigger trigger = (Trigger) object;
			Object trigParentObj = trigger.getParentModule();
			String triggerType = "";
			if (trigParentObj instanceof Item) {
				triggerType = "\nBlock: "
						+ ((Item) trigParentObj).getParentName() + "\nItem: "
						+ ((Item) trigParentObj).getName();
			} else if (trigParentObj instanceof Block) {
				triggerType = "\nBlock: " + ((Block) trigParentObj).getName();
			} else {
				if (trigParentObj != null) {
					triggerType = ((FormsObject) trigParentObj).getName();
				}
			}
			fileWriter.append("--Trigger: " + triggerType + "\n");
			fileWriter
					.append("----------------------------------------------------------\n");
			fileWriter.append(trigger.getTriggerText());
			fileWriter
					.append("\n----------------------------------------------------------\n");
		}
	}

	
	
	public void close() throws IOException {
		fileWriter.close();
	}
}
