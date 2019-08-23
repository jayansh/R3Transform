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
package com.converter.toolkit.ui.control;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

import com.release3.forms.dataControl.DataControl;
import com.release3.tf.core.form.InputFileData;


public class FormsControl 
//extends DataControl
{
    private DataControl dc;
    private String filter;
    private String order;
    private List<InputFileData> pls;
    private int currentRow;
    private int masterCurrentRow;
    private Object masterObject = null;
    private Long sessionID;

    public FormsControl(DataControl dc, Long sessionID
                         ) {
        this.sessionID = sessionID;
        this.dc = dc;
    }

//    private List makeIterator() {
//        pls = new Vector<InputFileData>();
//        FacesContext context = FacesContext.getCurrentInstance();
//        Application app = context.getApplication();
//        ValueBinding binding = 
//            app.createValueBinding("#{inputFileController.uploadDirectory}");
//        String uploadDirectory = (String)binding.getValue(context);
//
//        File[] files = new File(uploadDirectory).listFiles(new FileFilterExt());
//        if (files == null)
//            return pls;
//        for (File file : files)  {
//            FileInfo fi = new FileInfo();
//            fi.setFile(file);
//            //fi.setPhysicalPath(f[j].getAbsolutePath());
//            fi.setFileName(file.getName());
//            synchronized (pls) {
//                pls.add(new InputFileData(fi, file));
//            }
//        }
//        return pls;
//    }


//    public List getIterator() {
//        if (pls == null) {
//            pls = makeIterator();
//        }
//        return pls;
//    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getFilter() {
        return filter;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getOrder() {
        return order;
    }

    public void rowSelection(int rowIndex) {
        currentRow = rowIndex;
    }

    public int getCurrentIndex() {
        return currentRow;
    }

    public Object getCurrentRow() {
        return pls.get(currentRow);
    }

//    public void next(ActionEvent event) {
//    }
//
//    public boolean getNextDisabled() {
//        return false;
//    }
//
//    public void prev(ActionEvent event) {
//    }
//
//    public boolean getPrevDisabled() {
//        return false;
//    }
//
//    public void save(ActionEvent event) throws Exception {
//
//    }
//
//    public void create(ActionEvent event) throws Exception {
//    }

    public Object getObject() {
        return null;
    }

//    public void refresh() {
//        pls = makeIterator();
//    }

    public void registerObject(Object obj, int level) {
    }

    public Object getCurrentRowNoProxy() {
        return null;
    }

//    public void remove(ActionEvent event) {
//    }

    public void removeObject(Object obj, int level) {
    }

    public class FileFilterExt implements FileFilter {
        public boolean accept(File pathname) {
            if (pathname.getAbsolutePath().toUpperCase().indexOf(".FMB") != -1)
                return true;
            else
                return false;
        }
    }

//	@Override
//	public List getIterator() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void refresh() {
//		// TODO Auto-generated method stub
//		
//	}

}
