package com.LiveShowBookingSystem.utils;

import java.util.concurrent.atomic.AtomicInteger;

public class BookingIdGenerator {
    private static final AtomicInteger counter = new AtomicInteger(1000);
    
    public static String generateBookingId() {
        return String.valueOf(counter.incrementAndGet());
    }
}