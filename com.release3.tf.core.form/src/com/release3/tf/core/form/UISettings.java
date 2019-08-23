package com.release3.tf.core.form;

import java.util.List;

import com.converter.toolkit.ui.control.BlockCustsControl;
import com.converter.toolkit.ui.control.BlocksControl;
import com.converter.toolkit.ui.control.CanvasSwitchParametersControl;
import com.converter.toolkit.ui.control.CanvasesControl;
import com.converter.toolkit.ui.control.ConvertedFormsControl;
import com.converter.toolkit.ui.control.ItemsControl;
import com.converter.toolkit.ui.control.ParametersControl;
import com.converter.toolkit.ui.control.RecordGroupsControl;
import com.converter.toolkit.ui.control.RefreshControl;
import com.converter.toolkit.ui.control.SequenceControl;
import com.converter.toolkit.ui.control.TreesControl;
import com.converter.toolkit.ui.control.TriggersControl;
import com.converter.toolkit.ui.control.ViewPortsControl;
import com.oracle.xmlns.forms.Block;
import com.oracle.xmlns.forms.Item;
import com.oracle.xmlns.forms.Module;

/**
 * 
 * @author jayansh This is a simple class.
 */
public class UISettings {
	private static UISettings settings;
	private TreesControl treeControl;
	private BlocksControl blockControl;
	private BlockCustsControl blockCustsControl;
	private RecordGroupsControl recordGroupsControl;
	private ItemsControl itemControl;
	private TriggersControl triggersControl;
	private ParametersControl parameterControl;
	private SequenceControl sequenceControl;
	private RefreshControl refreshControl;
	private ViewPortsControl viewPortControl;
	private CanvasesControl bindCanvases;
	private ConvertedFormsControl bindConvertedForms;
	private boolean updateFlag = false;

	private CanvasSwitchParametersControl canvasSwitchParamControl;

	private UISettings() {

	}

	public static UISettings getUISettingsBean() {
		if (settings != null) {
			return settings;
		} else {
			settings = new UISettings();
			return settings;
		}
	}

	public TreesControl getTreeControl() {
		return treeControl;
	}

	public void setTreesControl(TreesControl treeControl) {
		this.treeControl = treeControl;
	}

	public Module getModule() {
		return treeControl.getModel();
	}

	public BlocksControl getBlocksConrol() {
		if (blockControl != null) {
			return blockControl;
		} else {
			blockControl = new BlocksControl(getModule());
			return blockControl;
		}
	}

	public ItemsControl getItemsControl() {
		if (itemControl != null) {
			return itemControl;
		} else {
			itemControl = new ItemsControl(getModule());
			return itemControl;
		}

	}

	public void setBlockCustsControl(BlockCustsControl blockCustsControl) {
		this.blockCustsControl = blockCustsControl;
	}

	public BlockCustsControl getBlockCustsControl() {
		return blockCustsControl;
	}

	public void setTriggersControl(TriggersControl triggersControl) {
		this.triggersControl = triggersControl;
	}

	public TriggersControl getTriggersControl() {
		return triggersControl;
	}

	public void setParametersControl(ParametersControl parameterControl) {
		this.parameterControl = parameterControl;
	}

	public ParametersControl getParametersControl() {
		return parameterControl;
	}

	public void setCanvasSwitchParametersControl(
			CanvasSwitchParametersControl canvasSwitchParamControl) {
		this.canvasSwitchParamControl = canvasSwitchParamControl;
	}

	public CanvasSwitchParametersControl getCanvasSwitchParametersControl() {
		return canvasSwitchParamControl;
	}

	public void setRecordGroupsControl(RecordGroupsControl recordGroupsControl) {
		this.recordGroupsControl = recordGroupsControl;
	}

	public RecordGroupsControl getRecordGroupsControl() {
		return recordGroupsControl;
	}

	public ViewPortsControl getViewPortControl() {
		if (viewPortControl == null) {
			viewPortControl = new ViewPortsControl(getTreeControl().getModel());
		}
		return viewPortControl;
	}

	public ConvertedFormsControl getConvertedFormsControl() {
		if (bindConvertedForms == null) {
			bindConvertedForms = new ConvertedFormsControl();
		}
		return bindConvertedForms;
	}

	public CanvasesControl getCanvases() {
		if (bindCanvases == null) {
			bindCanvases = new CanvasesControl();
		}
		return bindCanvases;
	}

	// public void setRefreshControl(RefreshControl refreshControl) {
	// this.refreshControl = refreshControl;
	// }
	//
	// public RefreshControl getRefreshControl() {
	//
	// return refreshControl;
	// }

	public SequenceControl getSequenceControl() {
		return sequenceControl;
	}

	public void setSequenceControl(SequenceControl sequenceControl) {
		this.sequenceControl = sequenceControl;
	}

	public boolean isUpdated() {
		return updateFlag;
	}

	public String[] toStringArray(List<String> list) {
		String[] strArray = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			strArray[i] = list.get(i);
		}
		return strArray;
	}

	public String[] getBlockNamesArray(List<Block> list) {
		String[] strArray = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			strArray[i] = list.get(i).getName();
		}
		return strArray;
	}

	public String[] getItemNamesArray(List<Item> list) {
		String[] strArray = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			strArray[i] = list.get(i).getName();
		}
		return strArray;

	}

	public void save() {
		try {
			if (getTriggersControl() != null) {
				getTriggersControl().save();
			}
			if (getBlockCustsControl() != null) {
				getBlockCustsControl().save();
			}
			if (getRecordGroupsControl() != null) {
				getRecordGroupsControl().save();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int indexOf(String[] strArray, String strValue) {
		int index = 0;
		for (String string : strArray) {
			if (string.equals(strValue) || string == strValue) {
				return index;
			}
			index++;
		}
		return -1;
	}

	public void clearAll() {
		treeControl = null;
		blockControl = null;
		blockCustsControl = null;
		recordGroupsControl = null;
		itemControl = null;
		triggersControl = null;
		parameterControl = null;
		canvasSwitchParamControl = null;
		viewPortControl = null;
		bindCanvases = null;
		bindConvertedForms = null;
	}
}
