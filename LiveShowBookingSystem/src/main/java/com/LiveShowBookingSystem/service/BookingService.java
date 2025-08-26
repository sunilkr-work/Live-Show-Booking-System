package com.LiveShowBookingSystem.service;

import com.LiveShowBookingSystem.pojo.Booking;
import com.LiveShowBookingSystem.pojo.WaitlistEntry;
import com.LiveShowBookingSystem.repository.BookingRepository;
import com.LiveShowBookingSystem.repository.ShowRepository;
import com.LiveShowBookingSystem.repository.WaitlistRepository;
import com.LiveShowBookingSystem.utils.BookingIdGenerator;
import java.util.List;

public class BookingService {
    private final BookingRepository bookingRepository;
    private final ShowRepository showRepository;
    private final WaitlistRepository waitlistRepository;
    
    public BookingService(BookingRepository bookingRepository, ShowRepository showRepository, WaitlistRepository waitlistRepository) {
        this.bookingRepository = bookingRepository;
        this.showRepository = showRepository;
        this.waitlistRepository = waitlistRepository;
    }
    
    public String bookTicket(String userName, String showName, String timeSlot, int ticketCount) {
        if (!showRepository.showExists(showName)) {
            return "Show not found!";
        }
        
        if (bookingRepository.hasUserBookedSlot(userName, timeSlot)) {
            return "You already have a booking for this time slot!";
        }
        
        int available = showRepository.getAvailableCapacity(showName, timeSlot);
        
        if (available >= ticketCount) {
            String bookingId = BookingIdGenerator.generateBookingId();
            Booking booking = new Booking(bookingId, userName, showName, timeSlot, ticketCount);
            bookingRepository.saveBooking(booking);
            showRepository.updateAvailableCapacity(showName, timeSlot, available - ticketCount);
            return "Booked. Booking id: " + bookingId;
        } else if (available > 0) {
            return "Insufficient capacity. Available: " + available + ", Requested: " + ticketCount;
        } else {
            WaitlistEntry entry = new WaitlistEntry(userName, showName, timeSlot, ticketCount);
            waitlistRepository.addToWaitlist(showName, timeSlot, entry);
            return "Show is fully booked. Added to waitlist.";
        }
    }
    
    public String cancelBooking(String bookingId) {
        Booking booking = bookingRepository.findById(bookingId);
        if (booking == null) {
            return "Booking not found!";
        }
        
        bookingRepository.deleteBooking(bookingId);
        int currentCapacity = showRepository.getAvailableCapacity(booking.getShowName(), booking.getTimeSlot());
        showRepository.updateAvailableCapacity(booking.getShowName(), booking.getTimeSlot(), currentCapacity + booking.getTicketCount());
        
        // Check waitlist
        WaitlistEntry waitlistEntry = waitlistRepository.getNextFromWaitlist(booking.getShowName(), booking.getTimeSlot());
        if (waitlistEntry != null) {
            int newAvailable = showRepository.getAvailableCapacity(booking.getShowName(), booking.getTimeSlot());
            if (newAvailable >= waitlistEntry.getTicketCount()) {
                String newBookingId = BookingIdGenerator.generateBookingId();
                Booking newBooking = new Booking(newBookingId, waitlistEntry.getUserName(), 
                    waitlistEntry.getShowName(), waitlistEntry.getTimeSlot(), waitlistEntry.getTicketCount());
                bookingRepository.saveBooking(newBooking);
                showRepository.updateAvailableCapacity(booking.getShowName(), booking.getTimeSlot(), 
                    newAvailable - waitlistEntry.getTicketCount());
            }
        }
        
        return "Booking Canceled";
    }
    
    public List<Booking> getUserBookings(String userName) {
        return bookingRepository.findByUser(userName);
    }
    
    public String getTrendingShow() {
        return bookingRepository.getTrendingShow();
    }
}