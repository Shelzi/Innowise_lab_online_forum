package com.innowise.onlineforum.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateFormatter {
    public static String formatLocalDateTime(LocalDateTime ldt, String pattern) {
        if (ldt == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return ldt.format(formatter);
    }
}
