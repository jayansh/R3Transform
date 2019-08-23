package com.release3.toolkit.positioning;

import java.util.UUID;
import java.util.Vector;

public class Knot {

    public static  long counter;
    private int x;
    private int y;
    private int xDelta;
    private int yDelta;
    private Long id;
    private String canvas;
    private String tab;
    private Knot master;
    private String knotType = null;
    private Vector<Element> elementsHolder = new Vector<Element>();
    private Vector<Knot> knots = new Vector<Knot>();
    private int columnLocker=-1;
    
    
    public Knot(){
        this.id = new Long(counter);
        counter++;
    }

    public Vector getElements(){
        return elementsHolder;
    }
    public void setElements(Vector el ){
        elementsHolder=el;
    }



    public void addElement(Element el) {
        this.incX(el.getX(), el.getXDelta());
        this.incY(el.getY(), el.getYDelta());

        this.setCanvas(el.getCanvas());
        this.setTab(el.getTab());

        elementsHolder.add(el);
    }

    private void incX(int x, int xDelta) {
        int a;
        int b;

        if ((this.x == 0) && (this.xDelta == 0)) {
            this.x = x;
            this.xDelta = xDelta;
        } else {
            if (this.x + this.xDelta < x + xDelta) {
                a = x + xDelta - this.x - this.xDelta;
            } else
                a = 0;

            if (x < this.x) {
                b = this.x - x;
            } else
                b = 0;

            this.x = this.x - b;
            this.xDelta = this.xDelta + a + b;
        }
        if (master != null)
            master.incX(this.x, this.xDelta);
    }

    public int getX() {
        return x;
    }

    private void incY(int y, int yDelta) {
        int a;
        int b;

        if ((this.y == 0) && (this.yDelta == 0)) {
            this.y = y;
            this.yDelta = yDelta;
        } else {

            if (this.y + this.yDelta < y + yDelta) {
                a = y + yDelta - this.y - this.yDelta;
            } else
                a = 0;

            if (y < this.y) {
                b = this.y - y;
            } else
                b = 0;

            this.y = this.y - b;
            this.yDelta = this.yDelta + a + b;
        }
        if (master != null)
           master.incY(this.y, this.yDelta);
    }

    private Knot shift(Knot tx){
        Knot knot = new Knot();
        if (tx.getMaster() != null){
           tx.getMaster().getKnots().removeElement(tx);
           tx.getMaster().getKnots().add(knot);
           knot.setMaster(tx.getMaster());
        }
        tx.setMaster(knot);
        knot.getKnots().add(tx);

        Knot current = new Knot();
        current.setMaster(knot);
        knot.getKnots().add(current);

        knot.incX(tx.getX(), tx.getXDelta());
        knot.incY(tx.getY(), tx.getYDelta());

//        current.incY(tx.getY(), tx.getYDelta());
//        current.incX(tx.getX(), tx.getXDelta());
        current.setCanvas(tx.getCanvas());
        current.setTab(tx.getTab());

        return current;
    }

    public Knot shift() {
        Knot tx = this;
        return shift(tx);
    }

    public Knot shift(String type) {

        Knot tx = this;
        while (tx.getMaster() != null) {
            if ((tx.getKnotType()!= null )&&(tx.getKnotType().equals(type)))
                break;
            tx=tx.getMaster();    
        }
        return shift(tx);
    }

    public Knot findRoot() {
        Knot tx = this;
        while (tx.getMaster() != null) {
            tx = tx.getMaster();
        }
        return tx;
    }

    public Knot findFit(Element el) {
        Knot tx = this;
        Knot svTx =this;
        boolean flag = false;
         do{

            if ((tx.getX() / ItemComp.STEP <= el.getX() / ItemComp.STEP) && 
                ((tx.getX() + tx.getXDelta()) / ItemComp.STEP >= (el.getX() + el.getXDelta()) /ItemComp.STEP) && 
                (tx.getY() /ItemComp.STEP <= el.getY() /ItemComp.STEP) && 
                ((tx.getY() + tx.getYDelta()) / ItemComp.STEP >= (el.getY() + el.getYDelta()) /ItemComp.STEP)) {
                flag = true;
                break;
            }
            if (tx.getMaster() != null){
               svTx=tx;
               tx = tx.getMaster();
            }   
        }while (tx.getMaster() != null);
        if ((flag == false)&&(tx.getMaster() == null))
          return tx.shift();
        
        return svTx.shift();
    }

    public int getY() {
        return y;
    }

    public int getXDelta() {
        return xDelta;
    }

    public void setYDelta(int yDelta) {
        this.yDelta = yDelta;
    }

    public int getYDelta() {
        return yDelta;
    }

    public void setMaster(Knot master) {
        this.master = master;
    }

    public Knot getMaster() {
        return master;
    }

    public void setKnotType(String knotType) {
        this.knotType = knotType;
    }

    public String getKnotType() {
        return knotType;
    }

    public void setKnots(Vector<Knot> knots) {
        this.knots = knots;
    }

    public Vector<Knot> getKnots() {
        return knots;
    }

    public Long getId() {
        return id;
    }

    public void setCanvas(String canvas) {
        this.canvas = canvas;
        if (master != null)
            master.setCanvas(canvas);
        
    }

    public String getCanvas() {
        return canvas;
    }

    public void setTab(String tab) {
        this.tab = tab;
        if (master != null)
            master.setTab(tab);
    }

    public String getTab() {
        return tab;
    }

    public void setColumnLocker(int columnLocker) {
        this.columnLocker = columnLocker;
    }

    public int getColumnLocker() {
        return columnLocker;
    }
}
