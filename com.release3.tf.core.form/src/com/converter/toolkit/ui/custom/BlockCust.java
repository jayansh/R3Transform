package com.converter.toolkit.ui.custom;

import java.util.List;
import java.util.Vector;

public class BlockCust implements ParamInterface {
    private String name;
    public Vector<Parameter> parameter = new Vector<Parameter>();
    public Vector<Sequence> sequence = new Vector<Sequence>();
    public Boolean immediate = false;

    public BlockCust() {
    }

    public BlockCust(String name) {
        this.name = name;
    }

    public void sSequence(Vector pk) {
        this.sequence = pk;
    }

    public void addSequence(Sequence pk) {
        sequence.add(pk);
    }

    public List getSequence() {
        return sequence;
    }


    public void setParameters(Vector param) {
        this.parameter = param;
    }

    public void addParameter(Parameter param) {
        parameter.add((Parameter)param);
    }

    public List getParameter() {
        return parameter;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public void setImmediate(Boolean immediate) {
        this.immediate = immediate;
    }

    public Boolean getImmediate() {
        return immediate;
    }

	

	@Override
	public List getCanvasSwitchParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCanvasSwitchParameters(Vector<CanvasSwitchParameter> param) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addCanvasSwitchParameter(CanvasSwitchParameter param) {
		// TODO Auto-generated method stub
		
	}
}
