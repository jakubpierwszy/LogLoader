package org.example;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        FileFinder fileFinder = new FileFinder();
        FileOperations fileOperations = new FileOperations();

        String diskName = "F:\\";
        String folderName = "logs";

        if (FileFinder.checkIfDiskExist(diskName)) {
            System.out.println("Found disk: " + diskName);
        } else System.out.println("Disk not exist!");

        System.out.println();
        String logsPath = fileFinder.folderSearcher(diskName, folderName);


        System.out.println();
        System.out.println(Arrays.stream(fileFinder.logFiles(logsPath)).toList());

        System.out.println();
        File[] logFiles = fileFinder.logFiles(logsPath);
        System.out.println(Arrays.stream(fileOperations.sortByLastModifed(logFiles)).toList());

    }


}