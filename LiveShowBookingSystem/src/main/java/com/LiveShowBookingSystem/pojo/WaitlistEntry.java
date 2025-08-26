package com.LiveShowBookingSystem.pojo;

public class WaitlistEntry {
    private String userName;
    private String showName;
    private String timeSlot;
    private int ticketCount;
    
    public WaitlistEntry(String userName, String showName, String timeSlot, int ticketCount) {
        this.userName = userName;
        this.showName = showName;
        this.timeSlot = timeSlot;
        this.ticketCount = ticketCount;
    }
    
    public String getUserName() { return userName; }
    public String getShowName() { return showName; }
    public String getTimeSlot() { return timeSlot; }
    public int getTicketCount() { return ticketCount; }
}