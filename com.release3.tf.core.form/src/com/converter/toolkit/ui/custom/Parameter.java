package com.converter.toolkit.ui.custom;

public class Parameter {
    String block;
    String item;
    String type;
    String name;
    String viewPortForm;
    String viewPort;
    String canvas;
    String formname;
    Boolean selected;
//    int rowIndex;

    public Parameter() {
        type = "in";
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getBlock() {
        return block;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getItem() {
        return item;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setViewPort(String viewPort) {
        this.viewPort = viewPort;
    }

    public String getViewPort() {
        return viewPort;
    }

    public void setCanvas(String canvas) {
        this.canvas = canvas;
    }

    public String getCanvas() {
        return canvas;
    }

    public void setFormname(String formname) {
        this.formname = formname;
    }

    public String getFormname() {
        return formname;
    }

    public void setViewPortForm(String viewPortForm) {
        this.viewPortForm = viewPortForm;
    }

    public String getViewPortForm() {
        return viewPortForm;
    }
    
    
    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public Boolean getSelected() {
        return selected;
    }
//    public void setRowIndex(int rowIndex){
//    	this.rowIndex = rowIndex;
//    }
//    public int getRowIndex(){
//    	return rowIndex;
//    }
}
