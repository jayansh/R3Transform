package com.release3.tf.core.form;

import java.io.File;

import com.release3.transform.constants.Environment;

/**
 * <p>The InputFileData Class is a simple wrapper/storage for object that are
 * returned by the inputFile component.  The FileInfo Class contains file
 * attributes that are associated during the file upload process.  The File
 * Object is a standard java.io File object which contains the uploaded
 * file data. </p>
 *
 * @since 1.0
 */
public class InputFileData extends File {

    public InputFileData(String pathname) {
		super(pathname);
	}
    
    
	// file info attributes
   // private FileInfo fileInfo;
    // file that was uploaded
//    private File file;
    private boolean migrate = false;
//    private int refresh;
//    public Boolean selected = false;
    

    /**
     * Create a new InputFileData object.
     *
     * @param fileInfo fileInfo object created by the inputFile component for
     *                 a given File object.
     * @param file     corresponding File object which as properties described
     *                 by the fileInfo object.
     */
   
    
//    public InputFileData(FileInfo fileInfo) {
//        this.fileInfo = fileInfo;
//        this.file = file.getParentFile();
//    }



    

    /**
     * Method to return the file size as a formatted string
     * For example, 4000 bytes would be returned as 4kb
     *
     *@return formatted file size
     */
    public String getSizeFormatted() {
        long ourLength = super.length();

        // Generate formatted label, such as 4kb, instead of just a plain number
        if (ourLength >= Environment.MEGABYTE_LENGTH_BYTES) {
            return ourLength / Environment.MEGABYTE_LENGTH_BYTES + " MB";
        } else if (ourLength >= Environment.KILOBYTE_LENGTH_BYTES) {
            return ourLength / Environment.KILOBYTE_LENGTH_BYTES + " KB";
        } else if (ourLength == 0) {
            return "0";
        } else if (ourLength < Environment.KILOBYTE_LENGTH_BYTES) {
            return ourLength + " B";
        }

        return Long.toString(ourLength);
    }

    public void setMigrate(boolean migrate) {
        this.migrate = migrate;
    }

    public boolean isMigrate() {
        return migrate;
    }

        
}
