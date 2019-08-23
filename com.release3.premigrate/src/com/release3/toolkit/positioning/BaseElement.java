package com.release3.toolkit.positioning;

public class BaseElement {

    private Long id;
    private String canvas;
    private String tab;
    private String elementType;


    protected void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setCanvas(String canvas) {
        this.canvas = canvas;
    }

    public String getCanvas() {
        return canvas;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }

    public String getTab() {
        return tab;
    }

    public void setElementType(String elementType) {
        this.elementType = elementType;
    }

    public String getElementType() {
        return elementType;
    }
}
