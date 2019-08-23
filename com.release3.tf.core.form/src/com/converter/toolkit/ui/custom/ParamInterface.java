package com.converter.toolkit.ui.custom;

import java.util.List;
import java.util.Vector;

public interface ParamInterface {

	public void addParameter(Parameter param);

	public List getParameter();

	public void setParameters(Vector<Parameter> param);

	public void addCanvasSwitchParameter(CanvasSwitchParameter param);

	public List getCanvasSwitchParameters();

	public void setCanvasSwitchParameters(Vector<CanvasSwitchParameter> param);

}
