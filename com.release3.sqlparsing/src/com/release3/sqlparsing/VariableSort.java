package com.release3.sqlparsing;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.converter.toolkit.sqlparsing.Variable;

public class VariableSort {
	static final Comparator<Variable> PARAMETER_ORDER = new Comparator<Variable>() {
		public int compare(Variable e1, Variable e2) {
			int returnVal = e1.getType().compareTo(e2.getType());
			if(returnVal==0){
				returnVal = e1.getReplacedName().compareTo(e2.getReplacedName());
			}
			return returnVal;
		}
	};

	public static void sort(List<Variable> variableList) {
		Collections.sort(variableList, PARAMETER_ORDER);
	}

}
