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
