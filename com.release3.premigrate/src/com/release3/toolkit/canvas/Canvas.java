package com.release3.toolkit.canvas;

public class Canvas {
    private int x;
    private int y;
    private int dx;
    private int dy;
    private String window;


    public Canvas(int x, int y, int dx, int dy, 
                  String window) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        if (window == null)
            this.window="na";
        this.window=window;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getX() {
        return x;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getY() {
        return y;
    }

    public void setDx(Integer dx) {
        this.dx = dx;
    }

    public Integer getDx() {
        return dx;
    }

    public void setDy(Integer dy) {
        this.dy = dy;
    }

    public Integer getDy() {
        return dy;
    }

    public void setWindow(String window) {
        this.window = window;
    }

    public String getWindow() {
        return window;
    }
    
    public boolean equals(Canvas c) {
        if (
             (c.getX() == this.x)&&
             (c.getY() == this.y)&&
             (c.getDx() == this.dx)&&
             (c.getDy() == this.dy)&&
             (c.getWindow().equals(this.window))
           )
          return true; 
        else
          return false;
    }
    
}

