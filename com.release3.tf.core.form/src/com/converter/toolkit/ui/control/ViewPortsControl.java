package com.converter.toolkit.ui.control;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;

import com.converter.toolkit.ui.FormsObject;
import com.oracle.xmlns.forms.Block;
import com.oracle.xmlns.forms.Item;
import com.oracle.xmlns.forms.Module;
import com.release3.tf.core.form.Settings;
import com.release3.tf.core.form.UISettings;

public class ViewPortsControl {

	private Module modelFormsModel;
	private int currentRow;

	private HashMap<String, List<Block>> formBlockMap = new HashMap<String, List<Block>>();

	public ViewPortsControl(Module model) {
		modelFormsModel = model;
	}

	public ViewPortsControl() {

	}

	public List<String> getIterator() {
		List<String> pls = new ArrayList<String>();
		Unmarshaller u = (UISettings.getUISettingsBean().getTreeControl())
				.getU();
		Settings set = Settings.getSettings();

		try {
			File f = new File(set.getBaseDir() + File.separator
					+ "ViewController" + File.separator + "src"
					+ File.separator + set.getApplicationName());

			File[] f1 = f.listFiles();
			for (int i = 0; i < f1.length; i++) {
				File ff = new File(f1[i], f1[i].getName() + "_2.xml");
				if (ff.exists()) {
					FileInputStream io = new FileInputStream(ff);

					JAXBElement<?> poElement = (JAXBElement<?>) u.unmarshal(io);
					Module globalModel = (Module) poElement.getValue();

					Iterator itr = globalModel.getFormModule().getChildren()
							.iterator();
					List<Block> blockList = new ArrayList<Block>();
					while (itr.hasNext()) {
						FormsObject obj = (FormsObject) itr.next();
						if ((obj instanceof Block)) {
							blockList.add((Block) obj);
							if (((Block) obj).getName().equals("VIRTUAL_BLOCK")) {
								Iterator itmItr = obj.getChildren().iterator();
								while (itmItr.hasNext()) {
									FormsObject o = (FormsObject) itmItr.next();
									if (o instanceof Item) {

										pls.add(f1[i].getName() + "-"
												+ o.getName());
									}
								}
							}
						}
					}
					formBlockMap.put(f1[i].getName(), blockList);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pls;
	}

	public void rowSelection(int rowIndex) {
		currentRow = rowIndex;
	}

	public int getCurrentIndex() {
		return currentRow;
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

	public HashMap<String, List<Block>> getFormBlockMap() {
		return formBlockMap;
	}
}
