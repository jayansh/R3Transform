package com.converter.toolkit.ui.control;

import java.io.File;
import java.io.FileFilter;
import java.util.List;
import java.util.Vector;

import com.release3.tf.core.form.Settings;


public class ConvertedFormsControl {
   
    private List<String> pls;
    private int currentRow;
   
    

    private List<String> makeIterator() {
        pls = new Vector<String>();

        
        Settings set = Settings.getSettings();
        String uploadDirectory = 
            set.getBaseDir() + File.separator + "ViewController"+ File.separator +"src"+ File.separator + 
            set.getApplicationName();

        File[] f = new File(uploadDirectory).listFiles(new FileFilterExt());
        if (f == null)
            return pls;
        for (int j = 0; j < f.length; j++) {
            File qq = f[j];
            pls.add(f[j].getName());
        }
        return pls;
    }


    public List<String> getIterator() {
        if (pls == null) {
            pls = makeIterator();
        }
        return pls;
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


    public boolean getNextDisabled() {
        return false;
    }

    public boolean getPrevDisabled() {
        return false;
    }

    public Object getObject() {
        return null;
    }

    public void refresh() {
        pls = makeIterator();
    }

    public void registerObject(Object obj, int level) {
    }

    public Object getCurrentRowNoProxy() {
        return null;
    }

    public void removeObject(Object obj, int level) {
    }

    public class FileFilterExt implements FileFilter {
        public boolean accept(File pathname) {
            if (pathname.isDirectory())
                return true;
            else
                return false;
        }
    }

}
