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
                return Long.valueOf(f2.lastModified()).compareTo(f1.lastModified());
            }
        });
        return files;
    }

    public void readFile(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String currentLine = reader.lines().collect(Collectors.joining());
        //System.out.println(currentLine);

        //datePattern(currentLine);
        //System.out.println(timePattern(currentLine).get(10));


        reader.close();
    }

    public List<String> datePattern(String logs) {
        Pattern pattern = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}");
        Matcher matcher = pattern.matcher(logs);
        List<String> dates = new ArrayList<>();
        while (matcher.find()) {
            dates.add(matcher.group());
        }
        return dates;
    }

    public List<String> timePattern(String logs) {
        final Pattern pattern = Pattern.compile("[0-9]{2}:[0-9]{2}:[0-9]{2}(\\.[0-9]{1,3})?");
        final Matcher matcher = pattern.matcher(logs);
        List<String> times = new ArrayList<>();
        while (matcher.find()) {
            times.add(matcher.group());
        }
        return times;
    }

}
