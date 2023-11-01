package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileFinder {

    public static boolean checkIfDiskExist(String diskName) {
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
                if (file.getName().equals(folderName)) {
                    return file.getAbsolutePath();
                }
            }
        }
        return "Searching in subfolders...";
    }

    public List<String> listOfLogFiles(Path path, String fileExtension) throws IOException {
        List<String> result;
        try (Stream<Path> walk = Files.walk(path)) {
            result = walk
                    .filter(p -> !Files.isDirectory(p))
                    .map(p -> p.toString().toLowerCase())
                    .filter(p -> p.endsWith(fileExtension))
                    .collect(Collectors.toList());
        }
        return result;
    }


    public void subFolderSearcher(String diskName, String folderName) {
        File directory = new File(diskName);
        File[] fList = directory.listFiles();

        try {
            for (File file : fList) {
                if (file.isDirectory()) {
                    if (file.getName().equals("logs")) {
                        System.out.println();
                        System.out.println("Path to logs folder: " + file.getPath());
                        break;
                    }
                    subFolderSearcher(file.getAbsolutePath(), folderName);

                }
            }
        } catch (NullPointerException exception) {

        }
    }


    public void listFilesAndFilesSubDireddctories(String directoryName) {
        File directory = new File(directoryName);
        File[] fList = directory.listFiles();

        try {
            for (File file : fList) {
                if (file.isFile()) {
                    System.out.println(file.getAbsolutePath());
                } else if (file.isDirectory()) {
                    if (file.getName().equals("logs")) {
                        System.out.println();
                        System.out.println("Znaleziono");
                    }
                    System.out.println(file.getAbsolutePath());
                    listFilesAndFilesSubDireddctories(file.getAbsolutePath());
                }
            }
        } catch (NullPointerException exception) {

        }
    }

}
