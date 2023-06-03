package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Utils {
    static public boolean isEmptyString(String str) {
        if (str == null || str.trim().length() == 0) {
            return true;
        }
        return false;
    }

    public static boolean isEmptyDateTime(LocalDateTime lt) {
        return (lt == null || LocalDateTime.MIN.compareTo(lt) == 0);
    }

    static public boolean isEmptyInteger(Integer iParam) {
        if (iParam == null || iParam == 0) {
            return true;
        }
        return false;
    }
}
