package com.converter.premigration;

import java.io.File;
import java.io.FileFilter;


public class FileFilterExt implements FileFilter {
  String description;

  String extensions[];

    public FileFilterExt(String description, String extension) {
        this(description, new String[]{ extension });
    }

    public FileFilterExt(String description, String extensions[]) {
        this.description = description;
        this.extensions = (String[]) extensions.clone();
        toLower(this.extensions);
    }

    private void toLower(String array[]) {
        for (int i = 0, n = array.length; i < n; i++) {
            array[i] = array[i].toLowerCase();
        }
    }

    public String getDescription() {
        return description;
    }

    public boolean accept(File file) {
        if (file.isDirectory()) {
            return false;
        } 
        else {
            String path = file.getAbsolutePath().toLowerCase();
            for (int i = 0, n = extensions.length; i < n; i++) 
            {
                String extension = extensions[i];
                if ((path.endsWith(extension) && (path.charAt(path.length() - extension.length() - 1)) == '.')) {
                    return true;
                }
            }
        }
        return false;
    }
}


