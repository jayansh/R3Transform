package com.converter.toolkit.ui.control;

import java.io.File;
import java.util.List;
import java.util.Vector;

import com.release3.tf.core.form.Settings;

public class CanvasesControl {
	 
	  
	    private Vector<String> pls;

	    public CanvasesControl( ) {
	    }

	    

	    public List<String> getIterator() {
	        pls = new Vector<String>();

	        
	        Settings set = Settings.getSettings();
	        String uploadDirectory = 
	            set.getBaseDir() + File.separator + "ViewController"+File.separator+"WebContent"+File.separator + 
	            set.getApplicationName();


	        File[] f = new File(uploadDirectory).listFiles();
	        if (f == null)
	            return pls;
	        for (int j = 0; j < f.length; j++) {

	            File[] f1 = f[j].listFiles();
	            for (int k = 0; k < f1.length; k++) {

	                pls.add( f1[k].getName());
	            }
	        }
	        return pls;
	    }

	  
	    
	  
	    public Object getObject() {
	        return null;
	    }

	    
}
