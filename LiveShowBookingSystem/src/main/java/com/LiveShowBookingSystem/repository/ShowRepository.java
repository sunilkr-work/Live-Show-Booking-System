package com.LiveShowBookingSystem.repository;

import com.LiveShowBookingSystem.pojo.Show;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ShowRepository {
    private final Map<String, Show> shows = new ConcurrentHashMap<>();
    private final Map<String, Map<String, Integer>> availableCapacity = new ConcurrentHashMap<>();
    
    public void saveShow(Show show) {
        shows.put(show.getName(), show);
        availableCapacity.put(show.getName(), new ConcurrentHashMap<>());
    }
    
    public Show findByName(String name) {
        return shows.get(name);
    }
    
    public List<Show> findByGenre(String genre) {
        return shows.values().stream()
                .filter(show -> show.getGenre().equalsIgnoreCase(genre))
                .toList();
    }
    
    public void updateAvailableCapacity(String showName, String timeSlot, int capacity) {
        availableCapacity.computeIfAbsent(showName, k -> new ConcurrentHashMap<>())
                .put(timeSlot, capacity);
    }
    
    public int getAvailableCapacity(String showName, String timeSlot) {
        return availableCapacity.getOrDefault(showName, new HashMap<>())
                .getOrDefault(timeSlot, 0);
    }
    
    public boolean showExists(String showName) {
        return shows.containsKey(showName);
    }
}