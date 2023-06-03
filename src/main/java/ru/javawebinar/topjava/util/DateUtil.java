package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static final String DATE_PATTERN = "dd/MM/yyyy HH:mm";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static String outputDateTime(LocalDateTime dateTime) {
        if (dateTime == null || dateTime == LocalDateTime.MIN) return "";
        return dateTime.format(DATE_FORMATTER);
    }

    public static LocalDateTime inputDateTime(String date) {
        if (Utils.isEmptyString(date)) {
            return LocalDateTime.MIN;
        }
        return LocalDateTime.parse(date, DATE_FORMATTER);
    }

}
