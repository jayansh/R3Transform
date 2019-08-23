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

package com.release3.toolkit.positioning;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Stack;

import java.util.Vector;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.ParserAdapter;
import org.xml.sax.helpers.XMLReaderFactory;
import org.xml.sax.ext.DeclHandler;
import org.xml.sax.ext.LexicalHandler;

public class DocumentTracer extends DefaultHandler implements ContentHandler, 
                                                              ErrorHandler // SAX2
{

    // default settings

    /** Default parser name. */
    protected static final String DEFAULT_PARSER_NAME = 
        "org.apache.xerces.parsers.SAXParser";


    /**  Looked for path  **/
    protected static final String LOOCKEDFOR_PATH = "/Module/R3";
    protected static final String YTYPE = "YType";
    protected static final String XTYPE = "XType";

    /**  Block types  **/
    protected static final int BLOCK_TYPE_FORM = 1;
    protected static final int BLOCK_TYPE_GRID = 2;


    //
    // Data
    //

    private Stack currentPath = new Stack();
    private int blockType = -1;
    public Hashtable formsElements = new Hashtable();
    private String savedCanvas = "";
    private String savedTab = "";
    private int RecordsDisplayCount = 0;
    private String blockName = null;
    private String itemName = null;
    private int ID = 0;
    private int ItemsDisplay = 0;

    //
    // ContentHandler methods
    //

    /** Start prefix mapping. */
    public void startPrefixMapping(String prefix, 
                                   String uri) throws SAXException {

    } // startPrefixMapping(String,String)

    /** Start element. */
    public void startElement(String uri, String localName, String qname, 
                             Attributes attributes) throws SAXException {
        int x = 0;
        int xDelta = 0;
        int y = 0;
        int yDelta = 0;
        String name = null;
        String canvas = null;
        Element currentElement;
        boolean isRadioButton;

        currentPath.push(localName);


        if (isParsing()) {

            if (attributes.getValue("XPosition") != null)
                x = new Integer(attributes.getValue("XPosition"));
            if (attributes.getValue("Width") != null)
                xDelta = new Integer(attributes.getValue("Width"));
            if (attributes.getValue("YPosition") != null)
                y = new Integer(attributes.getValue("YPosition"));
            if (attributes.getValue("Height") != null)
                yDelta = new Integer(attributes.getValue("Height"));

            if (attributes.getValue("RecordsDisplayCount") != null)
                RecordsDisplayCount = 
                        new Integer(attributes.getValue("RecordsDisplayCount"));

            if (attributes.getValue("ItemsDisplay") != null)
                ItemsDisplay = 
                        new Integer(attributes.getValue("ItemsDisplay"));

            name = attributes.getValue("Name");
            canvas = attributes.getValue("CanvasName");
            String tab = attributes.getValue("TabPageName");
            String itemType = (String)attributes.getValue("ItemType");


            if (localName.equals("Block")) {
                blockName = name;

                if (RecordsDisplayCount == 1)
                    blockType = this.BLOCK_TYPE_FORM;
                else {
                    blockType = this.BLOCK_TYPE_GRID;
                }
            }

            if (localName.equals("RadioButton")) {
                String key = savedCanvas + "-" + savedTab;
                Hashtable section = (Hashtable)this.formsElements.get(key);
                currentElement = (Element)section.get(blockName + itemName);

                if (currentElement.getX() != 0) {
                    int a = currentElement.getX() - x;
                    if (a < 0)
                        a = 0;
                    int b = 
                        currentElement.getX() + currentElement.getXDelta() - 
                        x - xDelta;
                    if (b < 0)
                        b = 0;

                    currentElement.setXDelta(a + currentElement.getXDelta() + 
                                             b);
                    currentElement.setX(Math.min(x, currentElement.getX()));

                    int q;
                    if (currentElement.getY() > y)
                        q = currentElement.getYDelta();
                    else
                        q = yDelta;

                    currentElement.setYDelta(Math.abs(y - 
                                                      currentElement.getY()) + 
                                             q);
                    currentElement.setY(Math.min(y, currentElement.getY()));
                } else {
                    currentElement.setXDelta(xDelta);
                    currentElement.setX(x);
                    currentElement.setYDelta(yDelta);
                    currentElement.setY(y);
                }
                if (( currentElement.yDelta == 0  )||(currentElement.xDelta == 0)) {
                        throw new RuntimeException("Width or High cannot be 0 in Block " + blockName+ " Item  "+itemName );
                }
                
                section.put(blockName + itemName, currentElement);
                this.formsElements.put(key, section);

            }

            if (localName.equals("Item")) {

                itemName = name;

                String key = canvas + "-" + tab;
                Hashtable section = (Hashtable)this.formsElements.get(key);
                if (section == null) {
                    section = new Hashtable();
                }

                if (blockType == this.BLOCK_TYPE_GRID) {
                    currentElement = (Element)section.get(blockName);

                    if (currentElement == null) {
                        currentElement = new Element();
                        currentElement.setElementType("Block");
                        currentElement.setName(blockName);
                        currentElement.setRecordsDisplayCount(RecordsDisplayCount);
                    }
                    currentElement.setX(x, xDelta);
                    currentElement.setY(y);
                    currentElement.setYDelta(yDelta * RecordsDisplayCount);

                    if (canvas != null) {
                        currentElement.setCanvas(canvas);
                        currentElement.setTab(tab);
                    }
                    if (( currentElement.yDelta == 0  )||(currentElement.xDelta == 0)) {
                            throw new RuntimeException("Width or High cannot be 0 in Block " + blockName+ " Item  "+itemName );
                    }
                    section.put(blockName, currentElement);
                }
                if (blockType == this.BLOCK_TYPE_FORM) {
                    currentElement = (Element)section.get(blockName + name);

                    if (currentElement == null)
                        currentElement = new Element();

                    currentElement.setX(x);
                    currentElement.setXDelta(xDelta);
                    currentElement.setY(y);
                    currentElement.setYDelta(yDelta);
                    currentElement.setCanvas(canvas);
                    currentElement.setTab(tab);
                    currentElement.setBlockName(blockName);
                    currentElement.setRecordsDisplayCount(ItemsDisplay);
                    currentElement.setName(name);

                    currentElement.setElementType("Item");

                    if (( currentElement.yDelta == 0  )||(currentElement.xDelta == 0)) {
                            throw new RuntimeException("Width or High cannot be 0 in Block " + blockName+ " Item  "+itemName );
                    }
                    section.put(blockName + name, currentElement);

                    if ((attributes.getValue("Prompt") != null) && 
                        (!attributes.getValue("Prompt").equals(""))) {

                        currentElement = new Element();
                        String PromptAttachmentEdge = "Start";
                        if (attributes.getValue("PromptAttachmentEdge") != 
                            null)
                            PromptAttachmentEdge = 
                                    attributes.getValue("PromptAttachmentEdge");

                        if (PromptAttachmentEdge.equals("Start")) {
                            x = x - 20;
                            xDelta = 15;
                        }
                        if (PromptAttachmentEdge.equals("End")) {
                            x = x + xDelta + 20;
                            xDelta = 15;
                        }
                        if (PromptAttachmentEdge.equals("Top")) {
                            y = y - 10;
                            yDelta = 8;
                        }
                        if (PromptAttachmentEdge.equals("Bottom")) {
                            y = y + yDelta + 10;
                            yDelta = 8;
                        }
//System.out.println("Name="+name+" x="+x+" xD="+xDelta+" y="+y+" yD="+yDelta);
                        currentElement.setX(x);
                        currentElement.setXDelta(xDelta);
                        currentElement.setY(y);
                        currentElement.setYDelta(yDelta);
                        currentElement.setCanvas(canvas);
                        currentElement.setTab(tab);
                        currentElement.setBlockName(blockName);
                        currentElement.setRecordsDisplayCount(ItemsDisplay);
                        currentElement.setName(name);

                        currentElement.setElementType("Prompt");

                        if (( currentElement.yDelta == 0  )||(currentElement.xDelta == 0)) {
                                throw new RuntimeException("Width or High cannot be 0 in Block " + blockName+ " Item  "+itemName );
                        }
                        section.put(blockName + name + "Prompt", 
                                    currentElement);


                    }


                }
                this.formsElements.put(key, section);
                this.savedCanvas = canvas;
                this.savedTab = tab;
            }
        }
    } // startElement(String,String,String,Attributes)

    /** End element. */
    public void endElement(String uri, String localName, 
                           String qname) throws SAXException {

        currentPath.pop();
    } // endElement(String,String,String)

    /** End prefix mapping. */
    public void endPrefixMapping(String prefix) throws SAXException {


    } // endPrefixMapping(String)

    /** Skipped entity. */
    public void skippedEntity(String name) throws SAXException {


    } // skippedEntity(String)


    /** Warning. */
    public void warning(SAXParseException ex) throws SAXException {
        ex.printStackTrace();

    } // warning(SAXParseException)

    /** Error. */
    public void error(SAXParseException ex) throws SAXException {
        ex.printStackTrace();
    } // error(SAXParseException)

    /** Fatal error. */
    public void fatalError(SAXParseException ex) throws SAXException {
        ex.printStackTrace();
        throw ex;
    } // fatalError(SAXParseException)

    private String getCurrentPath() {
        String path = "/";
        Iterator itr = currentPath.iterator();
        while (itr.hasNext()) {
            path = path + (String)itr.next() + "/";
        }
        return path;
    }

    public boolean isParsing() {
        int i = this.getCurrentPath().indexOf(this.LOOCKEDFOR_PATH);

        if (i == 0)
            return true;
        else
            return false;
    }

    public static Vector array2vector(Object[] arr) {
        Vector v = new Vector();
        for (int i = 0; i < arr.length; i++) {
            v.add(arr[i]);
        }
        return v;
    }


    private Knot down(Element prevElement, Element currentElement, 
                      Knot curKnot) {
       try{               
        if (Math.max(prevElement.getXDelta(), currentElement.getXDelta()) / 
            Math.min(prevElement.getXDelta(), currentElement.getXDelta()) != 
            1) {
            curKnot = curKnot.shift();
            curKnot.getMaster().setKnotType(YTYPE);
        } else {
            if ((curKnot.getKnotType() == null) || 
                (curKnot.getKnotType().equals(YTYPE))) {
                curKnot.setKnotType(XTYPE);

                curKnot.setKnotType(YTYPE);

            } else {
                curKnot.setKnotType(XTYPE);
                curKnot = curKnot.shift();
                curKnot.getMaster().setKnotType(YTYPE);
            }

        }
        }catch(Exception e){
            System.out.println("canvas ="+ currentElement.getCanvas() +" tab "+currentElement.getTab());
            System.out.println("currElemert"+ currentElement.getElementType()+"."+currentElement.getItemName()+"."+currentElement.getBlockName()+"."+currentElement.getName()+"."+currentElement.getElementType());
            System.out.println("x ="+ currentElement.getX()+" dX"+currentElement.xDelta+" y="+currentElement.getY()+" dY="+currentElement.getYDelta());
             
            System.out.println("canvas ="+ prevElement.getCanvas() +" tab "+prevElement.getTab());
            System.out.println("preElemert"+ prevElement.getElementType()+"."+prevElement.getItemName()+"."+prevElement.getBlockName()+"."+prevElement.getName()+"."+prevElement.getElementType());
            System.out.println("x ="+ prevElement.getX()+" dX"+prevElement.xDelta+" y="+prevElement.getY()+" dY="+prevElement.getYDelta());
            throw new RuntimeException();
        }
        
        return curKnot;
    }

    private Knot right(Element prevElement, Element currentElement, 
                       Knot curKnot) {
      try{                 
        if (Math.max(prevElement.getYDelta(), currentElement.getYDelta()) / 
            Math.min(prevElement.getYDelta(), currentElement.getYDelta()) != 
            1) {
            //            curKnot.setKnotType(YTYPE);
            curKnot = curKnot.shift();
            curKnot.getMaster().setKnotType(XTYPE);
        } else {
            if ((curKnot.getKnotType() == null) || 
                (curKnot.getKnotType().equals(XTYPE))) {
                curKnot.setKnotType(XTYPE);
            } else {
                curKnot.setKnotType(YTYPE);
                curKnot = curKnot.shift();
                curKnot.getMaster().setKnotType(XTYPE);
            }
        }
      }catch(Exception e){
          System.out.println("canvas ="+ currentElement.getCanvas() +" tab "+currentElement.getTab());
          System.out.println("currElemert"+ currentElement.getElementType()+"."+currentElement.getItemName()+"."+currentElement.getBlockName()+"."+currentElement.getName()+"."+currentElement.getElementType());
          System.out.println("x ="+ currentElement.getX()+" dX"+currentElement.xDelta+" y="+currentElement.getY()+" dY="+currentElement.getYDelta());
           
          System.out.println("canvas ="+ prevElement.getCanvas() +" tab "+prevElement.getTab());
          System.out.println("preElemert"+ prevElement.getElementType()+"."+prevElement.getItemName()+"."+prevElement.getBlockName()+"."+prevElement.getName()+"."+prevElement.getElementType());
          System.out.println("x ="+ prevElement.getX()+" dX"+prevElement.xDelta+" y="+prevElement.getY()+" dY="+prevElement.getYDelta());
          throw new RuntimeException();
      }
        return curKnot;

    }

    public Knot tableSplit(Vector v) {

        Knot curKnot = new Knot();
        Knot root = curKnot;


        Element prevElement = (Element)v.get(0);
        curKnot.addElement(prevElement);

        for (int i = 1; i < v.size(); i++) {
            Element currentElement = (Element)v.get(i);

            if (prevElement.getX() / ItemComp.STEP == 
                currentElement.getX() / ItemComp.STEP) {

                curKnot = down(prevElement, currentElement, curKnot);

                //                if (Math.max(prevElement.getXDelta(), 
                //                             currentElement.getXDelta()) / 
                //                    Math.min(prevElement.getXDelta(), 
                //                             currentElement.getXDelta()) != 1) {
                //                    curKnot = curKnot.shift();
                //                    curKnot.getMaster().setKnotType(YTYPE);
                //                } else {
                //                    if ((curKnot.getKnotType() == null) || 
                //                        (curKnot.getKnotType().equals(YTYPE))) {
                //                        curKnot.setKnotType(XTYPE);
                //
                //                        curKnot.setKnotType(YTYPE);
                //
                //                    } else {
                //                        curKnot.setKnotType(XTYPE);
                //                        curKnot = curKnot.shift();
                //                        curKnot.getMaster().setKnotType(YTYPE);
                //                    }
                //
                //                }

            }

            if (prevElement.getX() / ItemComp.STEP < 
                currentElement.getX() / ItemComp.STEP) {

                //                if (prevElement.getY() / ItemComp.STEP > 
                //                    currentElement.getY() / ItemComp.STEP) {
                //
                //                    curKnot = curKnot.shift();
                //                    curKnot.getMaster().setKnotType(XTYPE);
                //                } else {

                if ((prevElement.getX() + prevElement.getXDelta()) / 
                    ItemComp.STEP <= currentElement.getX() / ItemComp.STEP) {
                    curKnot = right(prevElement, currentElement, curKnot);

                    //                        if (Math.max(prevElement.getYDelta(), 
                    //                                     currentElement.getYDelta()) / 
                    //                            Math.min(prevElement.getYDelta(), 
                    //                                     currentElement.getYDelta()) != 1) {
                    //                            curKnot.setKnotType(YTYPE);
                    //                            curKnot = curKnot.shift();
                    //                            curKnot.getMaster().setKnotType(XTYPE);
                    //                        } else {
                    //                            if ((curKnot.getKnotType() == null) || 
                    //                                (curKnot.getKnotType().equals(XTYPE))) {
                    //                                curKnot.setKnotType(XTYPE);
                    //                            } else {
                    //                                curKnot.setKnotType(YTYPE);
                    //                                curKnot = curKnot.shift();
                    //                                curKnot.getMaster().setKnotType(XTYPE);
                    //                            }
                    //                        }
                } else {

                    curKnot = down(prevElement, currentElement, curKnot);

                    //                        if (Math.max(prevElement.getXDelta(), 
                    //                                     currentElement.getXDelta()) / 
                    //                            Math.min(prevElement.getXDelta(), 
                    //                                     currentElement.getXDelta()) != 1) {
                    //                            curKnot = curKnot.shift();
                    //                            curKnot.getMaster().setKnotType(YTYPE);
                    //                        } else {
                    //                            if ((curKnot.getKnotType() == null) || 
                    //                                (curKnot.getKnotType().equals(YTYPE))) {
                    //                                curKnot.setKnotType(XTYPE);
                    //
                    //                                curKnot.setKnotType(YTYPE);
                    //
                    //                            } else {
                    //                                curKnot.setKnotType(XTYPE);
                    //                                curKnot = curKnot.shift();
                    //                                curKnot.getMaster().setKnotType(YTYPE);
                    //                            }
                    //
                    //                        }

                }

                //                }
            }
            if (prevElement.getX() / ItemComp.STEP > 
                currentElement.getX() / ItemComp.STEP) {
                curKnot = curKnot.findFit(currentElement);
                curKnot.getMaster().setKnotType(YTYPE);
            }
            prevElement = currentElement;
            curKnot.addElement(currentElement);
            root = curKnot.findRoot();
        }
        return root;
    }

    public Knot eliminateOneColumns(Knot node) {
        Iterator<Knot> itr = node.getKnots().iterator();
        Knot returnNode = null;
        while (itr.hasNext()) {
            Knot nd = itr.next();

            Knot v = eliminateOneColumns(nd);

            if (v != null) {
                returnNode = v;
                break;
            }
            if (nd.getMaster() == null)
                break;
            if (nd.getKnotType() != null) {
                if ((nd.getKnotType().equals(YTYPE)) && 
                    (nd.getMaster().getKnotType().equals(YTYPE))) {
//System.out.println("Node="+nd.getId()+" Master="+nd.getMaster().getId());
                    returnNode = nd;
                }
            }
        }
        return returnNode;
    }

    public Vector mergeColumns(Knot node) {
        int columns;
        int a = 0;
        int b = 0;
        int prevColumns=-1; 
        Knot prevNode=null;

        Vector<Knot> delNode = new Vector<Knot>();
        Iterator<Knot> itr = node.getKnots().iterator();
        while (itr.hasNext()) {
            Knot nd = itr.next();

            Vector v = mergeColumns(nd);

              Iterator<Knot> delItr = v.iterator();
              while(delItr.hasNext()){
                 Knot d =  delItr.next();
//System.out.println("rm="+d.getId());              
                  d.getMaster().getKnots().remove(d);
              }


            if (nd.getMaster() == null)
                break;

            if ((nd.getKnotType() == null) || 
                (nd.getKnotType().equals(XTYPE))) {
                if (nd.getColumnLocker() <0){
                if (nd.getKnots() != null)
                    a = nd.getKnots().size();
                if (nd.getElements() != null)
                    b = nd.getElements().size();
                columns = a + b;
                }else{
                    columns =nd.getColumnLocker(); 
                }

                if ((columns == prevColumns)&&(columns != 1)){
//System.out.println("Prev="+prevNode.getId()+" node="+nd.getId());

                    if (prevNode.getColumnLocker() < 0)
                      prevNode.setColumnLocker(nd.getKnots().size()+nd.getElements().size());

                    prevNode.getKnots().addAll(nd.getKnots());
                    prevNode.getElements().addAll(nd.getElements());
                    delNode.add(nd);
                }else{        
                  prevColumns =  columns;
                  prevNode = nd; 
                }
            }


        }
        return delNode;

    }




    public void clinerOneNode(Knot node) {
        Knot nd = null;
        do {
            nd = eliminateOneColumns(node);
            if (nd != null) {
                Iterator<Knot> curItr = nd.getMaster().getKnots().iterator();
                while (curItr.hasNext()) {
                    curItr.next().setMaster(nd.getMaster());
                }

               if(nd.getMaster().getId() > nd.getId()){  
                nd.getKnots().addAll(nd.getMaster().getKnots());
                nd.getElements().addAll(nd.getMaster().getElements());

                nd.getMaster().setKnots(nd.getKnots());
                nd.getMaster().setElements(nd.getElements());
                nd.getMaster().getKnots().remove(nd);
               }else{
                 nd.getMaster().getKnots().addAll(nd.getKnots());  
                 nd.getMaster().getElements().addAll(nd.getElements());  
                 nd.getMaster().getKnots().remove(nd);
               }

            }
        } while (nd != null);

    }


    public void printTree(Knot knot, PrintStream pr) {
        int columns;
        int a = 0;
        int b = 0;

        if ((knot.getKnotType() == null) || 
            (knot.getKnotType().equals(XTYPE))) {
            if (knot.getColumnLocker()< 0){
            if (knot.getKnots() != null)
                a = knot.getKnots().size();
            if (knot.getElements() != null)
                b = knot.getElements().size();
            columns = a + b;
            }
            else
            columns=knot.getColumnLocker();
        } else
            columns = 1;


        pr.println("<table col=\"" + columns + "\"" + " id=\"" + knot.getId() + 
                   "\"" + " canvas=\"" + knot.getCanvas() + "\"" + " tab=\"" + 
                   knot.getTab() + "\">");

        Iterator<Knot> itr = knot.getKnots().iterator();
        while (itr.hasNext()) {
            Knot k = itr.next();
            printTree(k, pr);
        }

        Iterator<Element> eItr = knot.getElements().iterator();
        while (eItr.hasNext()) {
            Element e = eItr.next();
            pr.println("<Element type=\"" + e.getElementType() + "\" Name=\"" + 
                       e.getName() + "\"" + " canvas=\"" + e.getCanvas() + 
                       "\"" + " RecordsDisplayCount=\"" + 
                       e.getRecordsDisplayCount() + "\"" + " Block=\"" + 
                       e.getBlockName() + "\"" + " Item=\"" + e.getItemName() + 
                       "\"" + " tab=\"" + e.getTab() + "\"/>");
        }
        pr.println("</table>");
    }

    //
    // MAIN
    //


    /** Main. */
    public static void main(String[] argv) throws Exception {
        // is there anything to do?
        if (argv.length == 0) {
            System.out.println("No file for parsing");
            System.exit(1);
        }
        process(argv[0], argv[1]);

    }

    /** Main. */
    public static void process(String arg, String file) throws Exception {

        // variables
        DocumentTracer tracer = new DocumentTracer();
        XMLReader parser = null;
        ItemComp cmp;

        // use default parser?
        if (parser == null) {
            // create parser
            try {
                parser = XMLReaderFactory.createXMLReader(DEFAULT_PARSER_NAME);
            } catch (Exception e) {
                System.err.println("error: Unable to instantiate parser (" + 
                                   DEFAULT_PARSER_NAME + ")");
            }
        }

        // set parser features

        // set handlers
        parser.setDTDHandler(tracer);
        parser.setErrorHandler(tracer);
        if (parser instanceof XMLReader) {
            parser.setContentHandler(tracer);
        }

        // parse file
        try {
            parser.parse(arg);

            //Convert hash table to array and sort it
            Enumeration enm = tracer.formsElements.keys();
            while (enm.hasMoreElements()) {
                String key = (String)enm.nextElement();
                Hashtable h = (Hashtable)tracer.formsElements.get(key);
                Vector<Element> v = new Vector<Element>();
                Enumeration itr = h.keys();
                while (itr.hasMoreElements()) {
                    String k = (String)itr.nextElement();
                    v.add((Element)h.get(k));
                }
                Object[] arr = v.toArray();
//                System.out.println("****************** " + key + 
//                                   " **********************");
//fix by jayansh on 29jan2010
//                cmp = new ItemComp(arr);
//                Arrays.sort(arr, cmp);
                Arrays.sort(arr);
                
                Knot root = tracer.tableSplit(array2vector(arr));
                tracer.clinerOneNode(root);
                Vector vv = tracer.mergeColumns(root);

                Iterator<Knot> delItr = vv.iterator();
                  while(delItr.hasNext()){
                     Knot d =  delItr.next();
                System.out.println("rm="+d.getId());
                      d.getMaster().getKnots().remove(d);
                  }
                
                
                
                tracer.formsElements.put(key, root);
            }

            File f = new File(file);
            PrintStream pr = new PrintStream(new FileOutputStream(f));

            pr.println("<POSITIONING xmlns=\"http://xmlns.oracle.com/Forms\">");
            Enumeration enm1 = tracer.formsElements.keys();
            while (enm1.hasMoreElements()) {
                String key = (String)enm1.nextElement();
                Knot k = (Knot)tracer.formsElements.get(key);
                tracer.printTree(k, pr);
            }
            pr.println("</POSITIONING>");

        } catch (Exception e) {
            System.err.println("error: Parse error occurred - " + 
                               e.getMessage());
            if (e instanceof SAXException) {
                Exception nested = ((SAXException)e).getException();
                if (nested != null) {
                    e = nested;
                }
            }
            e.printStackTrace(System.err);
        }

    } 
    
   public class mergeHolder{
       Knot node1;
       Knot node2;
       
     public  mergeHolder(Knot node1,Knot node2){
         this.node1=node1;
         this.node2=node2;
         
     }
     public Knot getNode1(){
         return node1;
     }
       public Knot getNode2(){
           return node2;
       }
   }
} // class DocumentTracer
