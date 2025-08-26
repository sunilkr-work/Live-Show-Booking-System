import com.LiveShowBookingSystem.controller.LiveShowController;
import com.LiveShowBookingSystem.model.ShowAvailability;
import com.LiveShowBookingSystem.pojo.Booking;
import com.LiveShowBookingSystem.repository.BookingRepository;
import com.LiveShowBookingSystem.repository.ShowRepository;
import com.LiveShowBookingSystem.repository.WaitlistRepository;
import com.LiveShowBookingSystem.service.BookingService;
import com.LiveShowBookingSystem.service.ShowService;

import java.util.*;

public class TestRunner {
    public static void main(String[] args) {
        // Initialize components
        ShowRepository showRepository = new ShowRepository();
        BookingRepository bookingRepository = new BookingRepository();
        WaitlistRepository waitlistRepository = new WaitlistRepository();
        
        ShowService showService = new ShowService(showRepository);
        BookingService bookingService = new BookingService(bookingRepository, showRepository, waitlistRepository);
        
        LiveShowController controller = new LiveShowController(showService, bookingService);
        
        System.out.println("=== Live Show Booking System Test ===\n");
        
        // Test 1: Register shows
        System.out.println("1. Registering shows:");
        System.out.println(controller.registerShow("TMKOC", "Comedy"));
        System.out.println(controller.registerShow("The Sonu Nigam Live Event", "Singing"));
        System.out.println(controller.registerShow("The Arijit Singh Live Event", "Singing"));
        
        // Test 2: Onboard show slots
        System.out.println("\n2. Onboarding show slots:");
        Map<String, Integer> tmkocSlots = new HashMap<>();
        tmkocSlots.put("09:00-10:00", 3);
        tmkocSlots.put("12:00-13:00", 2);
        tmkocSlots.put("15:00-16:00", 5);
        System.out.println(controller.onboardShowSlots("TMKOC", tmkocSlots));
        
        Map<String, Integer> sonuSlots = new HashMap<>();
        sonuSlots.put("10:00-11:00", 3);
        sonuSlots.put("13:00-14:00", 2);
        sonuSlots.put("17:00-18:00", 1);
        System.out.println(controller.onboardShowSlots("The Sonu Nigam Live Event", sonuSlots));
        
        Map<String, Integer> arijitSlots = new HashMap<>();
        arijitSlots.put("11:00-12:00", 3);
        arijitSlots.put("14:00-15:00", 2);
        System.out.println(controller.onboardShowSlots("The Arijit Singh Live Event", arijitSlots));
        
        // Test 3: Show availability by genre
        System.out.println("\n3. Show availability by genre:");
        System.out.println("Comedy shows:");
        List<ShowAvailability> comedyShows = controller.showAvailByGenre("Comedy");
        comedyShows.forEach(show -> 
            System.out.println(show.getShowName() + ": (" + show.getTimeSlot() + ") " + show.getAvailableCapacity()));
        
        System.out.println("\nSinging shows:");
        List<ShowAvailability> singingShows = controller.showAvailByGenre("Singing");
        singingShows.forEach(show -> 
            System.out.println(show.getShowName() + ": (" + show.getTimeSlot() + ") " + show.getAvailableCapacity()));
        
        // Test 4: Book tickets
        System.out.println("\n4. Booking tickets:");
        System.out.println(controller.bookTicket("UserA", "TMKOC", "12:00-13:00", 2));
        System.out.println(controller.bookTicket("UserB", "TMKOC", "12:00-13:00", 1));
        System.out.println(controller.bookTicket("UserC", "The Sonu Nigam Live Event", "10:00-11:00", 1));
        
        // Test 5: Show updated availability
        System.out.println("\n5. Updated Comedy show availability:");
        comedyShows = controller.showAvailByGenre("Comedy");
        comedyShows.forEach(show -> 
            System.out.println(show.getShowName() + ": (" + show.getTimeSlot() + ") " + show.getAvailableCapacity()));
        
        // Test 6: View user bookings
        System.out.println("\n6. User bookings:");
        List<Booking> userABookings = controller.getUserBookings("UserA");
        System.out.println("UserA bookings:");
        userABookings.forEach(booking -> 
            System.out.println("ID: " + booking.getBookingId() + ", Show: " + booking.getShowName() + 
                ", Time: " + booking.getTimeSlot() + ", Tickets: " + booking.getTicketCount()));
        
        // Test 7: Cancel booking
        System.out.println("\n7. Canceling booking:");
        if (!userABookings.isEmpty()) {
            String bookingId = userABookings.get(0).getBookingId();
            System.out.println(controller.cancelBooking(bookingId));
        }
        
        // Test 8: Show trending show
        System.out.println("\n8. Trending show:");
        System.out.println("Trending Show: " + controller.getTrendingShow());
        
        // Test 9: Test waitlist functionality
        System.out.println("\n9. Testing waitlist:");
        System.out.println(controller.bookTicket("UserD", "The Sonu Nigam Live Event", "17:00-18:00", 1));
        System.out.println(controller.bookTicket("UserE", "The Sonu Nigam Live Event", "17:00-18:00", 1)); // Should go to waitlist
        
        System.out.println("\n=== Test Complete ===");
    }
}