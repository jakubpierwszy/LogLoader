package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

    public void readFile(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String currentLine = reader.lines().collect(Collectors.joining());
        System.out.println(currentLine);
        System.out.println(currentLine.subSequence(0, 10));

        Pattern pattern = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}");
        Matcher matcher = pattern.matcher(currentLine);
        List<String> dates = new ArrayList<>();

        while (matcher.find()) {
            dates.add(matcher.group() + " " + matcher.start());
        }
        System.out.println(dates.get(1));

        reader.close();
    }
}
