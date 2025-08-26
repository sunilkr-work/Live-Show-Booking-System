package com.LiveShowBookingSystem.model;

public class ShowAvailability {
    private String showName;
    private String timeSlot;
    private int availableCapacity;
    
    public ShowAvailability(String showName, String timeSlot, int availableCapacity) {
        this.showName = showName;
        this.timeSlot = timeSlot;
        this.availableCapacity = availableCapacity;
    }
    
    public String getShowName() { return showName; }
    public String getTimeSlot() { return timeSlot; }
    public int getAvailableCapacity() { return availableCapacity; }
}