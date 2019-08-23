package com.release3.tf.core.form;

import javax.swing.tree.DefaultMutableTreeNode;

import com.converter.toolkit.ui.FormsObject;
import com.icesoft.faces.component.tree.IceUserObject;

public class TreeObject 
extends IceUserObject 
{


    private FormsObject frmObj;
    private String Type;
    private int id;

    public TreeObject(DefaultMutableTreeNode defaultMutableTreeNode) {
        super(defaultMutableTreeNode);
        setLeafIcon("tree_document.gif");
        setBranchContractedIcon("tree_folder_closed.gif");
        setBranchExpandedIcon("tree_folder_open.gif");
        setExpanded(false);
    }


    public void setFrmObj(FormsObject frmObj) {
        this.frmObj = frmObj;
    }

    public FormsObject getFrmObj() {
        return frmObj;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
