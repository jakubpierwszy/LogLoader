package org.example;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;

public class Counters {
    FileOperations fileOperations = new FileOperations();

    public String rangeTimeOfLogs(String logs) throws IOException {
        List<Date> dateList = parsingListToDate(logs);
        Date maxDate = dateList.stream().max(Date::compareTo).get();
        Date minDate = dateList.stream().min(Date::compareTo).get();
        LocalDateTime maxDateLocal = maxDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime minDateLocal = minDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        Duration duration = Duration.between(maxDateLocal, minDateLocal);
        long sec = Math.abs(duration.toSeconds());
        int min = Math.toIntExact(sec / 60);
        int h = Math.toIntExact(min / 60);
        return "Time between first and last log is " + sec + "sec. In hours " + h + "h";
    }

    public List<Date> parsingListToDate(String logs) {
        List<String> datesAndTimes = fileOperations.datesAndTimesPattern(logs);
        ArrayList<Date> dateList = new ArrayList<Date>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (String dateString : datesAndTimes) {
            try {
                dateList.add(simpleDateFormat.parse(dateString));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return dateList;
    }

    public List<Map.Entry<String, Integer>> countLogsBySeverity(String logs) {
        List<String> logLevels = fileOperations.logLevelsPattern(logs);
        Map<String, Integer> mapSeverity = new HashMap<>();
        for (String log : logLevels) {
            mapSeverity.put(log, Collections.frequency(logLevels, log));
        }

        List<Map.Entry<String, Integer>> entryList = new ArrayList<Map.Entry<String, Integer>>(mapSeverity.entrySet());
        Collections.sort(entryList, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> s1, Map.Entry<String, Integer> t1) {
                return severityMarker(t1.getKey()).compareTo(severityMarker(s1.getKey()));
            }

        });
        return entryList;
    }

    public Double quantityRatio(String logs) {
        List<String> logLevels = fileOperations.logLevelsPattern(logs);
        Double count = 0.0;
        for (String log : logLevels) {
            if (severityMarker(log) >= 200) {
                count += 1;
            }
        }
        count = ((count / logLevels.size()) * 100);
        Double truncatedDouble = BigDecimal.valueOf(count)
                .setScale(1, RoundingMode.HALF_UP)
                .doubleValue();
        return truncatedDouble;
    }

    public Integer countOfUniqueLibraries(String logs) {
        List<String> librariesList = fileOperations.librariesPattern(logs);
        List<String> uniqueList = new ArrayList<>(new HashSet<>(librariesList));
        return uniqueList.size();
    }

    public long countTimeToReadFile(File file) throws IOException {
        long startTime = System.nanoTime();
        fileOperations.readFile(file);
        long estimatedTime = System.nanoTime() - startTime;
        return estimatedTime;
    }

    public Integer severityMarker(String severity) {
        switch (severity) {
            case "OFF": {
                return 0;
            }
            case "FATAL": {
                return 100;
            }
            case "ERROR": {
                return 200;
            }
            case "WARN": {
                return 300;
            }
            case "INFO": {
                return 400;
            }
            case "DEBUG": {
                return 500;
            }
            case "TRACE": {
                return 600;
            }
            default:
                return 0;
        }
    }
}
