package com.LiveShowBookingSystem.utils;

import com.LiveShowBookingSystem.constant.AppConstants;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TimeValidator {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(AppConstants.TIME_FORMAT);
    
    public static boolean isValidTimeSlot(String timeSlot) {
        try {
            String[] parts = timeSlot.split("-");
            if (parts.length != 2) return false;
            
            LocalTime start = LocalTime.parse(parts[0], formatter);
            LocalTime end = LocalTime.parse(parts[1], formatter);
            
            return start.getHour() >= AppConstants.START_HOUR && 
                   end.getHour() <= AppConstants.END_HOUR && 
                   start.plusHours(1).equals(end);
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    
    public static boolean hasTimeConflict(String timeSlot1, String timeSlot2) {
        return timeSlot1.equals(timeSlot2);
    }
}