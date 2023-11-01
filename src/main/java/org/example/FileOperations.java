package org.example;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

public class FileOperations {

    public File[] sortByLastModifed(File[] files) {
        Arrays.sort(files, new Comparator<File>() {

            @Override
            public int compare(File f1, File f2) {
                return Long.valueOf(f1.lastModified()).compareTo(f2.lastModified());
            }
        });


        return files;
    }
}
