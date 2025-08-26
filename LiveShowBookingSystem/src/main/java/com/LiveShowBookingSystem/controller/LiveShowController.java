package com.LiveShowBookingSystem.controller;

import com.LiveShowBookingSystem.model.ShowAvailability;
import com.LiveShowBookingSystem.pojo.Booking;
import com.LiveShowBookingSystem.service.BookingService;
import com.LiveShowBookingSystem.service.ShowService;
import java.util.List;
import java.util.Map;

public class LiveShowController {
    private final ShowService showService;
    private final BookingService bookingService;
    
    public LiveShowController(ShowService showService, BookingService bookingService) {
        this.showService = showService;
        this.bookingService = bookingService;
    }
    
    public String registerShow(String name, String genre) {
        return showService.registerShow(name, genre);
    }
    
    public String onboardShowSlots(String showName, Map<String, Integer> slots) {
        return showService.onboardShowSlots(showName, slots);
    }
    
    public List<ShowAvailability> showAvailByGenre(String genre) {
        return showService.getAvailableShowsByGenre(genre);
    }
    
    public String bookTicket(String userName, String showName, String timeSlot, int ticketCount) {
        return bookingService.bookTicket(userName, showName, timeSlot, ticketCount);
    }
    
    public String cancelBooking(String bookingId) {
        return bookingService.cancelBooking(bookingId);
    }
    
    public List<Booking> getUserBookings(String userName) {
        return bookingService.getUserBookings(userName);
    }
    
    public String getTrendingShow() {
        return bookingService.getTrendingShow();
    }
}