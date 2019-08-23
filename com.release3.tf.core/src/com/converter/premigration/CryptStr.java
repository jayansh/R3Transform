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

public class CryptStr implements Serializable{
    private String fileNum;
    private String[] fileSize;
    private String pos;

    public CryptStr() {
    }

    public void setFileNum(String fileNum) {
        this.fileNum = fileNum;
    }

    public String getFileNum() {
        return fileNum;
    }

    public void setFileSize(String[] fileSize) {
        this.fileSize = fileSize;
    }

    public String[] getFileSize() {
        return fileSize;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getPos() {
        return pos;
    }
}
