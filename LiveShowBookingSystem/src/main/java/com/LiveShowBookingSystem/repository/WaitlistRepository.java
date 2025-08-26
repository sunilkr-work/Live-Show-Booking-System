package com.LiveShowBookingSystem.repository;

import com.LiveShowBookingSystem.pojo.WaitlistEntry;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class WaitlistRepository {
    private final Map<String, Queue<WaitlistEntry>> waitlists = new ConcurrentHashMap<>();
    
    public void addToWaitlist(String showName, String timeSlot, WaitlistEntry entry) {
        String key = showName + "-" + timeSlot;
        waitlists.computeIfAbsent(key, k -> new LinkedList<>()).offer(entry);
    }
    
    public WaitlistEntry getNextFromWaitlist(String showName, String timeSlot) {
        String key = showName + "-" + timeSlot;
        Queue<WaitlistEntry> queue = waitlists.get(key);
        return queue != null ? queue.poll() : null;
    }
}