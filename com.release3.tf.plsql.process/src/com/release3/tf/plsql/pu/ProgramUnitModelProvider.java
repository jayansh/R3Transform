package com.release3.tf.plsql.pu;

import java.util.ArrayList;
import java.util.List;

import com.release3.programunitgen.R3ProgramUnit;

public class ProgramUnitModelProvider {
	private List<R3ProgramUnit> r3puList;
	private static ProgramUnitModelProvider content;

	private ProgramUnitModelProvider() {
		r3puList = new ArrayList<R3ProgramUnit>();
	}

	public static ProgramUnitModelProvider getInstance() {
		if (content != null) {
			return content;
		} else {
			content = new ProgramUnitModelProvider();
			return content;
		}
	}

	public void setPUList(List<R3ProgramUnit> crudList) {
		if (this.r3puList == null) {
			this.r3puList = new ArrayList<R3ProgramUnit>();
		} else if (this.r3puList.size() > 0) {
			this.r3puList.addAll(crudList);
		} else {
			this.r3puList = crudList;
		}

	}

	public List<R3ProgramUnit> getPUList() {
		return r3puList;
	}
}