package com.converter.toolkit.ui.control;

import java.util.List;

import com.converter.toolkit.ui.custom.BlockCust;
import com.converter.toolkit.ui.custom.Sequence;

public class SequenceControl {
	// private DataControl dc;
	private String filter;
	private String order;
	private List<Sequence> pls;
	private int currentRow;
	// private int masterCurrentRow;
	private Object masterObject = null;

	// private Long sessionID;

	// public SequenceControl(DataControl dc, Long sessionID
	// ) {
	// this.sessionID = sessionID;
	// this.dc = dc;
	// }
	public SequenceControl(Object masterObject) {
		this.masterObject = masterObject;
		currentRow = 0;
		pls = ((BlockCust) masterObject).getSequence();
	}

	// private List makeIterator() {
	// if ((masterObject == null) ||
	// (!masterObject.equals(dc.getCurrentRow()))) {
	//
	// currentRow = 0;
	// masterObject = dc.getCurrentRow();
	// pls = ((BlockCust)dc.getCurrentRow()).getSequence();
	// }
	//
	// return pls;
	// }

	public List getIterator() {
		// return makeIterator();
		return pls;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public String getFilter() {
		return filter;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getOrder() {
		return order;
	}

	public void rowSelection(int rowIndex) {
		currentRow = rowIndex;
	}

	public int getCurrentIndex() {
		return currentRow;
	}

	public Object getCurrentRow() {
		return pls.get(currentRow);
	}

	public boolean getNextDisabled() {
		return false;
	}

	public boolean getPrevDisabled() {
		return false;
	}

	public Object getObject() {
		return null;
	}

	public void refresh() {
	}

	public void registerObject(Object obj, int level) {
	}

	public Object getCurrentRowNoProxy() {
		return null;
	}

	public void removeObject(Object obj, int level) {
	}
}
