package com.release3.transform.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import com.release3.tf.core.form.CleanupForm;

public class CleanupFormModelProvider {
	private List<CleanupForm> forms;
	private static CleanupFormModelProvider content;

	private CleanupFormModelProvider() {
		forms = new ArrayList<CleanupForm>();
	}

	public static CleanupFormModelProvider getInstance() {
		if (content != null) {
			return content;
		} else {
			content = new CleanupFormModelProvider();
			return content;
		}
	}

	public void setCleanupForms(List<File> formList) {
		if (forms == null) {
			forms = new ArrayList<CleanupForm>();
		}
		// setting new list of files *not adding.
		if (forms.size() > 0) {
			forms.clear();
		}
		for (File file : formList) {
			CleanupForm cleanupForm = new CleanupForm(file, "False");
			forms.add(cleanupForm);
		}
	}

	public List<CleanupForm> getCleanupForms() {
		return forms;
	}

}
