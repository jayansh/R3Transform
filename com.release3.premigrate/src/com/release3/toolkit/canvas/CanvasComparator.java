package com.release3.toolkit.canvas;

import java.util.Iterator;
import java.util.Vector;

public class CanvasComparator {
    private Vector<Canvas> v = new Vector<Canvas>();

    public boolean add(Canvas c){
        Iterator<Canvas> itr = v.iterator();
        boolean flag=false;
        while(itr.hasNext()){

            Canvas q = itr.next();
            if (q.equals(c)){
             flag=true;  
            }
        }
        if(!flag)
           v.add(c);
        return flag;
    }

}
