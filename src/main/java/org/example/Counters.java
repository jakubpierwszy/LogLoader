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
    FileFinder fileFinder = new FileFinder();
    FileOperations fileOperations = new FileOperations();
    File file = new File("F:\\logs\\zadanie_server.log");
    String log = fileOperations.readFile(file);

    public Counters() throws IOException {
    }

    public String rangeTimeOfLogs() throws ParseException {
        List<Date> dateList = parsingListToDate();
        Date maxDate = dateList.stream().max(Date::compareTo).get();
        Date minDate = dateList.stream().min(Date::compareTo).get();
        LocalDateTime maxDateLocal = maxDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime minDateLocal = minDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        Duration duration = Duration.between(maxDateLocal, minDateLocal);
        Long sec = Math.abs(duration.toSeconds());
        Integer min = Math.toIntExact(sec / 60);
        Integer h = Math.toIntExact(min / 60);
        return "Time between first and last log is " + sec + "sec. In hours " + h + "h";
    }

    public List<Date> parsingListToDate() throws ParseException {
        List<String> datesAndTimes = fileOperations.datesAndTimesPattern(log);
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

    public List<Map.Entry<String, Integer>> coutLogsBySeverity() {
        List<String> logLevels = fileOperations.logLevelsPattern(log);
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

    public Double quantityRatio() {
        List<String> logLevels = fileOperations.logLevelsPattern(log);
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
