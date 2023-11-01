package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        FileFinder fileFinder = new FileFinder();
        String diskName = "F:\\";
        String folderName = "logs";

        if (FileFinder.checkIfDiskExist(diskName)) {
            System.out.println("Found disk: " + diskName);
        } else System.out.println("Disk not exist!");

        System.out.println();
        //System.out.println(fileFinder.folderSearcher(diskName, folderName));


        String logsPath = fileFinder.folderSearcher(diskName, folderName);

        List<String> files = fileFinder.listOfLogFiles(Paths.get(logsPath), "log");
        files.forEach(x -> {
            System.out.println(x);
        });


    }


}