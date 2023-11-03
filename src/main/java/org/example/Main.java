package org.example;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class Main {
    public static void main(String[] args) throws IOException {
        FileFinder fileFinder = new FileFinder();
        FileOperations fileOperation = new FileOperations();
        Counters counter = new Counters();

        String diskName = "D:\\";
        String folderName = "logs";

        if (FileFinder.checkIfDiskExist(diskName)) {
            System.out.println("Found disk: " + diskName);


            String logsPath = fileFinder.folderSearcher(diskName, folderName);

            if (!logsPath.isEmpty()) {
                //return list with extension .log
                File[] logFiles = fileFinder.logFiles(logsPath);

                if (logFiles.length <= 0) {
                    System.out.println("There is not any *.log file.");
                } else {

                    System.out.println(Arrays.stream(fileOperation.sortByLastModified(logFiles)).toList());
                    System.out.println();

                    for (File file : logFiles) {

                        System.out.println("File: " + file.getName());
                        String logs = fileOperation.readFile(file);
                        System.out.println("File reading time: " + counter.countTimeToReadFile(file) + "ms");

                        try {
                            System.out.println(counter.rangeTimeOfLogs(logs));
                            System.out.println("Numbers of logs, grouped by severty: " + counter.countLogsBySeverity(logs));
                            System.out.println("Quantity ratio logs with severity ERROR or higher: " + counter.quantityRatio(logs) + "%");
                            System.out.println("Number of unique libraries: " + counter.countOfUniqueLibraries(logs));

                        } catch (NoSuchElementException e) {
                            System.out.println("File: " + file.getName() + " haven't logs.");
                        }

                        System.out.println();
                    }
                }
            } else System.out.println("Folder " + folderName + " not found!");

        } else System.out.println("Disk not exist!");

    }

}