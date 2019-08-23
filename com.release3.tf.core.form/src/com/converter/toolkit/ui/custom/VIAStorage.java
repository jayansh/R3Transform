package com.converter.toolkit.ui.custom;

import com.release3.forms.pref.Preferences;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;


public class VIAStorage extends Preferences {

    private String dirPath;
    private String formName;

    public Writer getDocumentWriter() throws Exception {

        File f = new File(dirPath + "\\" + formName + "_customization.xml");
        return new FileWriter(f);


    }

    public Reader getDocumentReader() throws Exception {
        File f = new File(dirPath + "\\" + formName + "_customization.xml");
        return new FileReader(f);
    }


    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }

    public String getDirPath() {
        return dirPath;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getFormName() {
        return formName;
    }
    
}
