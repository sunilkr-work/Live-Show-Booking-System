package com.LiveShowBookingSystem.repository;

import com.LiveShowBookingSystem.pojo.Booking;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BookingRepository {
    private final Map<String, Booking> bookings = new ConcurrentHashMap<>();
    private final Map<String, List<Booking>> userBookings = new ConcurrentHashMap<>();
    private final Map<String, Integer> showBookingCounts = new ConcurrentHashMap<>();
    
    public void saveBooking(Booking booking) {
        bookings.put(booking.getBookingId(), booking);
        userBookings.computeIfAbsent(booking.getUserName(), k -> new ArrayList<>()).add(booking);
        showBookingCounts.merge(booking.getShowName(), booking.getTicketCount(), Integer::sum);
    }
    
    public Booking findById(String bookingId) {
        return bookings.get(bookingId);
    }
    
    public List<Booking> findByUser(String userName) {
        return userBookings.getOrDefault(userName, new ArrayList<>());
    }
    
    public void deleteBooking(String bookingId) {
        Booking booking = bookings.remove(bookingId);
        if (booking != null) {
            userBookings.get(booking.getUserName()).remove(booking);
            showBookingCounts.merge(booking.getShowName(), -booking.getTicketCount(), Integer::sum);
        }
    }
    
    public boolean hasUserBookedSlot(String userName, String timeSlot) {
        return userBookings.getOrDefault(userName, new ArrayList<>()).stream()
                .anyMatch(booking -> booking.getTimeSlot().equals(timeSlot));
    }
    
    public String getTrendingShow() {
        return showBookingCounts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("No bookings yet");
    }
}