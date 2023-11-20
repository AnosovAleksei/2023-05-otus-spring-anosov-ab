package ru.otus.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Converter {

    public static final String DATE_FORMAT = "yyyy-MM-dd";

    private static final DateFormat FORMATTER = new SimpleDateFormat(DATE_FORMAT);

    public static String dateToString(Date date) {
        return FORMATTER.format(date);
    }

    public static Date stringToDate(String string) {
        try {
            return FORMATTER.parse(string);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


}
