package com.LiveShowBookingSystem;

import com.LiveShowBookingSystem.constant.AppConstants;
import com.LiveShowBookingSystem.controller.LiveShowController;
import com.LiveShowBookingSystem.model.ShowAvailability;
import com.LiveShowBookingSystem.pojo.Booking;
import com.LiveShowBookingSystem.repository.BookingRepository;
import com.LiveShowBookingSystem.repository.ShowRepository;
import com.LiveShowBookingSystem.repository.WaitlistRepository;
import com.LiveShowBookingSystem.service.BookingService;
import com.LiveShowBookingSystem.service.ShowService;

import java.util.*;
import java.util.InputMismatchException;

public class LiveShowBookingSystemApplication {
    private static final Scanner scanner = new Scanner(System.in);
    private static LiveShowController controller;
    
    public static void main(String[] args) {
        initializeApplication();
        System.out.println("Welcome to Live Show Booking System!");
        
        while (true) {
            try {
                displayMenu();
                int choice = scanner.nextInt();
                scanner.nextLine();
                
                if (!handleUserChoice(choice)) {
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        
        System.out.println("Thank you for using Live Show Booking System!");
        scanner.close();
    }
    
    private static void initializeApplication() {
        ShowRepository showRepository = new ShowRepository();
        BookingRepository bookingRepository = new BookingRepository();
        WaitlistRepository waitlistRepository = new WaitlistRepository();
        
        ShowService showService = new ShowService(showRepository);
        BookingService bookingService = new BookingService(bookingRepository, showRepository, waitlistRepository);
        
        controller = new LiveShowController(showService, bookingService);
    }
    
    private static void displayMenu() {
        System.out.println("\n=== Live Show Booking System ===");
        System.out.println("1. Register Show");
        System.out.println("2. Onboard Show Slots");
        System.out.println("3. Show Availability by Genre");
        System.out.println("4. Book Ticket");
        System.out.println("5. Cancel Booking");
        System.out.println("6. View User Bookings");
        System.out.println("7. Get Trending Show");
        System.out.println("8. Exit");
        System.out.print("Enter your choice: ");
    }
    
    private static boolean handleUserChoice(int choice) {
        switch (choice) {
            case 1 -> registerShow();
            case 2 -> onboardShowSlots();
            case 3 -> showAvailabilityByGenre();
            case 4 -> bookTicket();
            case 5 -> cancelBooking();
            case 6 -> viewUserBookings();
            case 7 -> getTrendingShow();
            case 8 -> {
                return false;
            }
            default -> System.out.println("Invalid choice. Please try again.");
        }
        return true;
    }
    
    private static void registerShow() {
        System.out.print("Enter show name: ");
        String name = scanner.nextLine().trim();
        
        System.out.println("Available genres: " + AppConstants.VALID_GENRES);
        System.out.print("Enter genre: ");
        String genre = scanner.nextLine().trim();
        
        String result = controller.registerShow(name, genre);
        System.out.println(result);
    }
    
    private static void onboardShowSlots() {
        System.out.print("Enter show name: ");
        String showName = scanner.nextLine().trim();
        
        System.out.println("Enter time slots (format: HH:mm-HH:mm capacity). Type 'done' to finish:");
        System.out.println("Example: 09:00-10:00 5");
        
        Map<String, Integer> slots = new HashMap<>();
        
        while (true) {
            System.out.print("Time slot and capacity(or type done): ");
            String input = scanner.nextLine().trim();
            
            if ("done".equalsIgnoreCase(input)) {
                break;
            }
            
            String[] parts = input.split(" ");
            if (parts.length != 2) {
                System.out.println("Invalid format. Use: HH:mm-HH:mm capacity");
                continue;
            }
            
            try {
                String timeSlot = parts[0];
                int capacity = Integer.parseInt(parts[1]);
                slots.put(timeSlot, capacity);
            } catch (NumberFormatException e) {
                System.out.println("Invalid capacity. Please enter a number.");
            }
        }
        
        if (!slots.isEmpty()) {
            String result = controller.onboardShowSlots(showName, slots);
            System.out.println(result);
        }
    }
    
    private static void showAvailabilityByGenre() {
        System.out.print("Enter genre: ");
        String genre = scanner.nextLine().trim();
        
        List<ShowAvailability> availabilities = controller.showAvailByGenre(genre);
        
        if (availabilities.isEmpty()) {
            System.out.println("No shows with available slots for genre: " + genre);
            System.out.println("Note: Shows must have time slots onboarded to appear here.");
        } else {
            for (ShowAvailability availability : availabilities) {
                System.out.println(availability.getShowName() + ": (" + 
                    availability.getTimeSlot() + ") " + availability.getAvailableCapacity());
            }
        }
    }
    
    private static void bookTicket() {
        System.out.print("Enter user name: ");
        String userName = scanner.nextLine().trim();
        
        System.out.print("Enter show name: ");
        String showName = scanner.nextLine().trim();
        
        System.out.print("Enter time slot (HH:mm-HH:mm): ");
        String timeSlot = scanner.nextLine().trim();
        
        System.out.print("Enter number of tickets: ");
        try {
            int ticketCount = Integer.parseInt(scanner.nextLine().trim());
            String result = controller.bookTicket(userName, showName, timeSlot, ticketCount);
            System.out.println(result);
        } catch (NumberFormatException e) {
            System.out.println("Invalid ticket count. Please enter a number.");
        }
    }
    
    private static void cancelBooking() {
        System.out.print("Enter booking ID: ");
        String bookingId = scanner.nextLine().trim();
        
        String result = controller.cancelBooking(bookingId);
        System.out.println(result);
    }
    
    private static void viewUserBookings() {
        System.out.print("Enter user name: ");
        String userName = scanner.nextLine().trim();
        
        List<Booking> bookings = controller.getUserBookings(userName);
        
        if (bookings.isEmpty()) {
            System.out.println("No bookings found for user: " + userName);
        } else {
            System.out.println("Bookings for " + userName + ":");
            for (Booking booking : bookings) {
                System.out.println("ID: " + booking.getBookingId() + 
                    ", Show: " + booking.getShowName() + 
                    ", Time: " + booking.getTimeSlot() + 
                    ", Tickets: " + booking.getTicketCount());
            }
        }
    }
    
    private static void getTrendingShow() {
        String trendingShow = controller.getTrendingShow();
        System.out.println("Trending Show: " + trendingShow);
    }
}