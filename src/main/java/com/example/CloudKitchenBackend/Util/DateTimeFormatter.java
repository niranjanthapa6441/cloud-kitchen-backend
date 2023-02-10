package com.example.CloudKitchenBackend.Util;

import lombok.Data;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class DateTimeFormatter {
    public static LocalDate
    getDateFromString(String string,
                      java.time.format.DateTimeFormatter format)
    {

        LocalDate date = LocalDate.parse(string, format);// Returning the converted date
        return date;
    }
    public LocalDate formatDate(String date){
        java.time.format.DateTimeFormatter format
                = java.time.format.DateTimeFormatter.ofPattern("MMMM d, yyyy");
        // Try block tp check for exceptions
        try {

            // Getting the Date from String
            LocalDate formattedDate = getDateFromString(date, format);
            return formattedDate;
        }

        // Block 1
        // Catch block to handle exceptions occuring
        // if the String pattern is invalid
        catch (IllegalArgumentException e) {
            throw new CustomException(CustomException.Type.DATE_INVALID);
        }
    }
    public LocalDateTime formatLocalDateTime(String string){
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a");
        LocalDateTime dateTime = LocalDateTime.parse(string, formatter);
        return dateTime;
    }
    public Time getTimeFromString(String time) {
        DateFormat formatter = new SimpleDateFormat("HH:mm");

        try{
            Time timeValue = new java.sql.Time(formatter.parse(time).getTime());
            return timeValue;
        }
        catch (Exception e){
            throw new CustomException(CustomException.Type.TIME_INVALID);
        }
    }
}
