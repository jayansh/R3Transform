package com.release3.tf;

import java.util.ArrayList;
import java.util.List;

public class Procedure extends ProcedureDeclaration  implements CompilationUnit,Declaration {

	List<String> parameterList = new ArrayList<String>();
	String procedureBody = null;
	String procedureName = null;

	public void addParameter(String parameter) {
		parameterList.add(parameter);
	}

	public List<String> getParameterList() {
		return parameterList;
	}

	public void setParameterList(List<String> parameterList) {
		this.parameterList = parameterList;
	}

	public String getProcedureBody() {
		return procedureBody;
	}

	public void setProcedureBody(String procedureBody) {
		this.procedureBody = procedureBody;
	}

	public String getProcedureName() {
		return procedureName;
	}

	public void setProcedureName(String procedureName) {
		this.procedureName = procedureName;
	}

}
