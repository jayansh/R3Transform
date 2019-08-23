package com.release3.tf.customiz.model.param.canvas;

import java.util.ArrayList;
import java.util.List;

import com.converter.toolkit.ui.control.CanvasSwitchParametersControl;
import com.converter.toolkit.ui.custom.CanvasSwitchParameter;
import com.release3.tf.core.form.UISettings;

public class CanvasSwitchParameterModelProvider {
	private List<CanvasSwitchParameter> parameterList;
	private static CanvasSwitchParameterModelProvider content;

	private CanvasSwitchParameterModelProvider() {
		CanvasSwitchParametersControl paramControl = UISettings
				.getUISettingsBean().getCanvasSwitchParametersControl();

		parameterList = paramControl.getIterator();
		if (parameterList == null) {
			this.parameterList = new ArrayList<CanvasSwitchParameter>();
		}
	}

	public static CanvasSwitchParameterModelProvider getInstance() {
		if (content != null) {
			return content;
		} else {
			content = new CanvasSwitchParameterModelProvider();
			return content;
		}
	}

	public void setParameterList(List<CanvasSwitchParameter> parameterList) {
		if (parameterList != null) {
			this.parameterList = parameterList;
		} else {
			this.parameterList = new ArrayList<CanvasSwitchParameter>();
		}
	}

	public List<CanvasSwitchParameter> getParameterList() {
		return parameterList;
	}

	public void upIntheList(int index) {
		if (index > 0) {
			CanvasSwitchParameter currentParam = parameterList.get(index);
			CanvasSwitchParameter previousParam = parameterList.get(index - 1);
			parameterList.set(index, previousParam);
			parameterList.set(index - 1, currentParam);
		}
	}

	public void downIntheList(int index) {
		if (index < parameterList.size() - 1) {
			CanvasSwitchParameter currentParam = parameterList.get(index);
			CanvasSwitchParameter nextParam = parameterList.get(index + 1);
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
