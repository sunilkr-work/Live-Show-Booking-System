package com.LiveShowBookingSystem.service;

import com.LiveShowBookingSystem.constant.AppConstants;
import com.LiveShowBookingSystem.model.ShowAvailability;
import com.LiveShowBookingSystem.pojo.Show;
import com.LiveShowBookingSystem.repository.ShowRepository;
import com.LiveShowBookingSystem.utils.TimeValidator;
import java.util.*;

public class ShowService {
    private final ShowRepository showRepository;
    
    public ShowService(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }
    
    public String registerShow(String name, String genre) {
        if (!AppConstants.VALID_GENRES.contains(genre)) {
            return "Invalid genre. Valid genres: " + AppConstants.VALID_GENRES;
        }
        
        if (showRepository.showExists(name)) {
            return "Show already exists!";
        }
        
        Show show = new Show(name, genre);
        showRepository.saveShow(show);
        return name + " show is registered !!";
    }
    
    public String onboardShowSlots(String showName, Map<String, Integer> slots) {
        Show show = showRepository.findByName(showName);
        if (show == null) {
            return "Show not found!";
        }
        
        for (Map.Entry<String, Integer> entry : slots.entrySet()) {
            String timeSlot = entry.getKey();
            int capacity = entry.getValue();
            
            if (!TimeValidator.isValidTimeSlot(timeSlot)) {
                return "Sorry, show timings are of 1 hour only";
            }
            
            show.addTimeSlot(timeSlot, capacity);
            showRepository.updateAvailableCapacity(showName, timeSlot, capacity);
        }
        
        return "Done!";
    }
    
    public List<ShowAvailability> getAvailableShowsByGenre(String genre) {
        List<Show> shows = showRepository.findByGenre(genre);
        List<ShowAvailability> availabilities = new ArrayList<>();
        
        for (Show show : shows) {
            for (Map.Entry<String, Integer> slot : show.getTimeSlots().entrySet()) {
                int available = showRepository.getAvailableCapacity(show.getName(), slot.getKey());
                if (available > 0) {
                    availabilities.add(new ShowAvailability(show.getName(), slot.getKey(), available));
                }
            }
        }
        
        availabilities.sort(Comparator.comparing(ShowAvailability::getTimeSlot));
        return availabilities;
    }
}