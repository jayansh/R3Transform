package com.release3.plsql.analysis;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.release3.customization.parameter.ParameterType;

public class ParameterSort {
	/**
	 * This method will order parameters by it's type. Resultant order should be:
	 * in,inLocal,out,outLocal
	 */
	static final Comparator<ParameterType> PARAMETER_ORDER = new Comparator<ParameterType>() {
		public int compare(ParameterType e1, ParameterType e2) {
			return e1.getType().compareTo(e2.getType());
		}
	};

	public static void sort(List<ParameterType> paramList) {
		Collections.sort(paramList, PARAMETER_ORDER);

	}

}
