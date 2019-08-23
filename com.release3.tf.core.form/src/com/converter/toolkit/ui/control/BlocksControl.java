package com.converter.toolkit.ui.control;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import com.oracle.xmlns.forms.Block;

import com.converter.toolkit.ui.FormsObject;
import com.oracle.xmlns.forms.Module;

public class BlocksControl {
	
    private String filter;
    private String order;
    private Module modelFormsModel;
    private int currentRow;
    private int masterCurrentRow;
    private List pls;
    private Long sessionID;

    public BlocksControl(Module model) {
       
        modelFormsModel = model;
        
    }

    public List<Block> getIterator() {
        if (pls == null) {
            pls = new Vector();
            Iterator itr = 
                modelFormsModel.getFormModule().getChildren().iterator();
            while (itr.hasNext()) {
                FormsObject obj = (FormsObject)itr.next();
                if (obj instanceof Block) {
                    pls.add(obj);
                }
            }
        }
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
