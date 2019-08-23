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
package com.converter.premigration;
         

import java.io.Serializable;

import java.util.Vector;

public class FileInfo implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2307018813807726357L;
	/**
	 * 
	 */
	private String filename;
    private long fileSize;
    public Vector<String> blocks;

    public FileInfo() {
       blocks = new Vector<String>();
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public long getFileSize() {
        return fileSize;
    }
    
}
