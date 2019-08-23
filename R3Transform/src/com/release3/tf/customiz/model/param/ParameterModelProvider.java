package com.release3.tf.customiz.model.param;

import java.util.ArrayList;
import java.util.List;

import com.converter.toolkit.ui.control.ParametersControl;
import com.converter.toolkit.ui.custom.Parameter;
import com.release3.tf.core.form.UISettings;

public class ParameterModelProvider {
	private List<Parameter> parameterList;
	private static ParameterModelProvider content;

	private ParameterModelProvider() {
		ParametersControl paramControl = UISettings.getUISettingsBean()
				.getParametersControl();

		parameterList = paramControl.getIterator();
		if (parameterList == null) {
			this.parameterList = new ArrayList<Parameter>();
		}
	}

	public static ParameterModelProvider getInstance() {
		if (content != null) {
			return content;
		} else {
			content = new ParameterModelProvider();
			return content;
		}
	}

	public void setParameterList(List<Parameter> parameterList) {
		if (parameterList != null) {
			this.parameterList = parameterList;
		} else {
			this.parameterList = new ArrayList<Parameter>();
		}
	}

	public List<Parameter> getParameterList() {
		return parameterList;
	}

	public void upIntheList(int index) {
		if (index > 0) {
			Parameter currentParam = parameterList.get(index);
			Parameter previousParam = parameterList.get(index - 1);
			parameterList.set(index, previousParam);
			parameterList.set(index - 1, currentParam);
		}
	}

	public void downIntheList(int index) {
		if (index < parameterList.size() - 1) {
			Parameter currentParam = parameterList.get(index);
			Parameter nextParam = parameterList.get(index + 1);
			parameterList.set(index, nextParam);
			parameterList.set(index + 1, currentParam);
		}
	}

	public void delete(int index) {
		if (index >= 0) {
			parameterList.remove(index);
		}
	}
}
