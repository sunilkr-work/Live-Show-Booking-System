package com.LiveShowBookingSystem.pojo;

public class Booking {
    private String bookingId;
    private String userName;
    private String showName;
    private String timeSlot;
    private int ticketCount;
    
    public Booking(String bookingId, String userName, String showName, String timeSlot, int ticketCount) {
        this.bookingId = bookingId;
        this.userName = userName;
        this.showName = showName;
        this.timeSlot = timeSlot;
        this.ticketCount = ticketCount;
    }
    
    public String getBookingId() { return bookingId; }
    public String getUserName() { return userName; }
    public String getShowName() { return showName; }
    public String getTimeSlot() { return timeSlot; }
    public int getTicketCount() { return ticketCount; }
}