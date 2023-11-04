package org.example;

import java.io.File;
import java.io.FilenameFilter;

public class FileFinder {

    public boolean checkIfDiskExist(String diskName) {
        File[] paths;
        paths = File.listRoots();
        for (File path : paths) {
            if (path.getPath().equals(diskName)) {
                return true;
            }
        }
        return false;
    }

    public String folderSearcher(String diskName, String folderName) {
        File directory = new File(diskName);
        File[] fList = directory.listFiles();

        for (File file : fList) {
            if (file.isDirectory()) {
                if (file.getName().toLowerCase().equals(folderName)) {
                    return file.getAbsolutePath();
                }
            }
        }

        return "";
    }


    public File[] logFiles(String paths) throws NullPointerException {
        File directory = new File(paths);
        File[] fList = directory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File file, String name) {
                return name.toLowerCase().endsWith(".log");
            }
        });

        return fList;
    }

}
