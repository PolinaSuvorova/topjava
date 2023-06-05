package ru.javawebinar.topjava.util;

public class Utils {
    static public boolean isEmptyString(String str) {
        return (str == null) || (str.trim().length() == 0);
    }
}
