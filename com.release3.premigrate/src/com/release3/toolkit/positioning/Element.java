package com.release3.toolkit.positioning;

public class Element extends BaseElement{

    protected int x = 4000;
    protected int xDelta = 0;
    protected int y = 4000;
    protected int yDelta = 0;
    protected String blockName;
    protected String itemName;
    protected int recordsDisplayCount;
    private String name;


    public void setX(int x, int xDelta) {
        if (x < this.x)
            this.x = x;
        this.xDelta = this.xDelta + xDelta;
    }


    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setXDelta(int xDelta) {
        this.xDelta = xDelta;
    }

    public int getXDelta() {
        return xDelta;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public void setYDelta(int yDelta) {
        this.yDelta = yDelta;
    }

    public int getYDelta() {
        return yDelta;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setRecordsDisplayCount(int recordsDisplayCount) {
        this.recordsDisplayCount = recordsDisplayCount;
    }

    public int getRecordsDisplayCount() {
        return recordsDisplayCount;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }


}
