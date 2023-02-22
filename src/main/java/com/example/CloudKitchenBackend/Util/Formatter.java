package com.example.CloudKitchenBackend.Util;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Formatter {
    public static LocalDate convertStrToDate(String dateStr, String dateFormat) {
        LocalDate date = null;
        if (dateStr != null) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat);
            date = LocalDate.parse(dateStr, dateTimeFormatter);
        }
        return date;
    }

    public static String convertDateToStr(LocalDate date, String dateFormat) {
        String dateStr = null;
        if (date != null) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat);
            dateStr = date.format(dateTimeFormatter);
        }
        return dateStr;
    }
    public static LocalDateTime formatLocalDateTime(String string){
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a");
        LocalDateTime dateTime = LocalDateTime.parse(string, formatter);
        return dateTime;
    }
    public static Time getTimeFromString(String time) {
        DateFormat formatter = new SimpleDateFormat("HH:mm");

        try{
            Time timeValue = new Time(formatter.parse(time).getTime());
            return timeValue;
        }
        catch (Exception e){
            throw new CustomException(CustomException.Type.TIME_INVALID);
        }
    }
}
