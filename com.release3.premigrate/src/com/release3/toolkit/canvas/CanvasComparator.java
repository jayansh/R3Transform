/*******************************************************************************
 * Copyright (c) 2009, 2013 ObanSoft Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Jayansh Shinde
 *******************************************************************************/
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
