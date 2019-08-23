package com.converter.toolkit.ui.control;

import java.util.Iterator;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import com.converter.toolkit.ui.custom.CustomizationPreferences;
import com.converter.toolkit.ui.custom.RecordGroup;
import com.converter.toolkit.ui.custom.Trigger;
import com.release3.forms.dataControl.DataControl;
import com.release3.tf.core.form.TreeObject;
import com.release3.tf.core.form.UISettings;


public class RecordGroupsControl  {
    private DataControl dc;
    private String filter;
    private String order;
    private List<Trigger> pls;
    private int currentRow;
    
    private RecordGroup currenObject = null;

  
    public RecordGroupsControl(DefaultMutableTreeNode node) {
        try{
        currentRow = 0;
        String type = ((TreeObject)node.getUserObject()).getFrmObj().getType();
        currenObject = 
                findObject(((TreeObject)node.getUserObject()).getFrmObj().getName());
        }catch(Exception ex){
        	ex.printStackTrace();
        }
       
    }

    private List makeIterator() {
        return null;
    }

    public List getIterator() {
        return null;
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
         return currenObject;
    }

    public boolean getNextDisabled() {
        return false;
    }

    public boolean getPrevDisabled() {
        return false;
    }

    public void save() throws Exception {
        UISettings.getUISettingsBean().getTreeControl().getCustomizationModel().addRecordGroup(currenObject);
        UISettings.getUISettingsBean().getTreeControl().getCustomizationModel().saveDocument();
    }

    public Object getObject() {
        return currenObject;
    }

    public void refresh() {
    }


    private RecordGroup findObject(String name) {
        CustomizationPreferences modelFormsModel= UISettings.getUISettingsBean().getTreeControl().getCustomizationModel();
//        System.out.println("The value of name in the find object :"+ name);
        if (modelFormsModel.getRecordGroup() == null)
            return new RecordGroup(name);
        Iterator itr = modelFormsModel.getRecordGroup().iterator();
        while (itr.hasNext()) {
            RecordGroup rg = (RecordGroup)itr.next();
            if (rg.getName().equals(name)) {
                return rg;
            }
        }
        return new RecordGroup(name);
    }

   
    public void registerObject(Object obj, int level) {
    }

    public Object getCurrentRowNoProxy() {
        return null;
    }

    public void removeObject(Object obj, int level) {
    }

}
