package com.LiveShowBookingSystem.pojo;

import java.util.HashMap;
import java.util.Map;

public class Show {
    private String name;
    private String genre;
    private Map<String, Integer> timeSlots; // time -> capacity
    
    public Show(String name, String genre) {
        this.name = name;
        this.genre = genre;
        this.timeSlots = new HashMap<>();
    }
    
    public String getName() { return name; }
    public String getGenre() { return genre; }
    public Map<String, Integer> getTimeSlots() { return timeSlots; }
    
    public void addTimeSlot(String time, int capacity) {
        timeSlots.put(time, capacity);
    }
}