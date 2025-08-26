import com.LiveShowBookingSystem.controller.LiveShowController;
import com.LiveShowBookingSystem.model.ShowAvailability;
import com.LiveShowBookingSystem.repository.BookingRepository;
import com.LiveShowBookingSystem.repository.ShowRepository;
import com.LiveShowBookingSystem.repository.WaitlistRepository;
import com.LiveShowBookingSystem.service.BookingService;
import com.LiveShowBookingSystem.service.ShowService;
import com.LiveShowBookingSystem.pojo.Show;

import java.util.*;

public class DebugTest {
    public static void main(String[] args) {
        ShowRepository showRepository = new ShowRepository();
        BookingRepository bookingRepository = new BookingRepository();
        WaitlistRepository waitlistRepository = new WaitlistRepository();
        
        ShowService showService = new ShowService(showRepository);
        BookingService bookingService = new BookingService(bookingRepository, showRepository, waitlistRepository);
        
        LiveShowController controller = new LiveShowController(showService, bookingService);
        
        // Test the exact sequence
        System.out.println("1. Registering show:");
        String result1 = controller.registerShow("Sunil", "Comedy");
        System.out.println(result1);
        
        System.out.println("\n2. Onboarding slots:");
        Map<String, Integer> slots = new HashMap<>();
        slots.put("09:00-10:00", 5);
        slots.put("10:00-11:00", 5);
        String result2 = controller.onboardShowSlots("Sunil", slots);
        System.out.println(result2);
        
        System.out.println("\n3. Checking show in repository:");
        Show show = showRepository.findByName("Sunil");
        if (show != null) {
            System.out.println("Show found: " + show.getName() + ", Genre: " + show.getGenre());
            System.out.println("Time slots: " + show.getTimeSlots());
        }
        
        System.out.println("\n4. Checking available capacity:");
        System.out.println("09:00-10:00 capacity: " + showRepository.getAvailableCapacity("Sunil", "09:00-10:00"));
        System.out.println("10:00-11:00 capacity: " + showRepository.getAvailableCapacity("Sunil", "10:00-11:00"));
        
        System.out.println("\n5. Finding by genre:");
        List<Show> comedyShows = showRepository.findByGenre("Comedy");
        System.out.println("Comedy shows found: " + comedyShows.size());
        for (Show s : comedyShows) {
            System.out.println("- " + s.getName() + " (" + s.getGenre() + ")");
        }
        
        System.out.println("\n6. Getting availability:");
        List<ShowAvailability> availabilities = controller.showAvailByGenre("Comedy");
        System.out.println("Availabilities found: " + availabilities.size());
        for (ShowAvailability avail : availabilities) {
            System.out.println("- " + avail.getShowName() + ": " + avail.getTimeSlot() + " (" + avail.getAvailableCapacity() + ")");
        }
    }
}