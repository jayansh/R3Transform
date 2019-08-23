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
package com.release3.toolkit.converter;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

public class Helper {

    public static Vector lookup (String source, String pattern){
        Vector v = new Vector();
        String[] q = source.split("[ \\t\\n\\x0B\\f\\r()=]");

        for(int i=0; i<q.length;i++){
          int index = q[i].indexOf(pattern);
          if (index >= 0){
            v.add(q[i].substring(pattern.length()));  
          }
        }
        return v;
    }
    
    public static boolean compare(Vector v1, Vector v2){
       Comparator comparator = Collections.reverseOrder();
       Collections.sort(v1,comparator);
       Collections.sort(v2,comparator);
       return(v1.equals(v2));
    }

   

    public static void main(String[] args) {
//
//     
//
//
//     System.out.println(new Integer("20")- new Integer(10));
//
//
    }
}
