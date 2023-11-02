package org.example;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        FileFinder fileFinder = new FileFinder();
        FileOperations fileOperation = new FileOperations();
        Counters counter = new Counters();

        String diskName = "F:\\";
        String folderName = "logs";

        if (FileFinder.checkIfDiskExist(diskName)) {
            System.out.println("Found disk: " + diskName);
            String logsPath = fileFinder.folderSearcher(diskName, folderName);

            //return list with extension .log
            File[] logFiles = fileFinder.logFiles(logsPath);
            //Shows list of files by last modified
            System.out.println(Arrays.stream(fileOperation.sortByLastModifed(logFiles)).toList());
            fileOperation.readFile(new File("F:\\logs\\zadanie_server.log"));

            System.out.println(counter.rangeTimeOfLogs());

            System.out.println("Numbers of logs, grouped by severty: " + counter.coutLogsBySeverity());

            System.out.println("Quantity ratio logs with severity ERROR or higher: " + counter.quantityRatio() + "%");

        } else System.out.println("Disk not exist!");


    }


}