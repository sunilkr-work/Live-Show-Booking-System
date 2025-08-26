# Live Show Booking System

A console-based application for booking live shows with time slot management, waitlist functionality, and trending show tracking.

## Features

- **Show Registration**: Organizers can register shows with genres (Comedy, Theatre, Tech, Singing)
- **Time Slot Management**: 1-hour slots from 9 AM to 9 PM
- **Ticket Booking**: Users can book tickets with capacity validation
- **Conflict Prevention**: Users cannot book overlapping time slots
- **Waitlist System**: Automatic waitlist management when shows are full
- **Booking Cancellation**: Cancel bookings with automatic waitlist processing
- **Trending Shows**: Track most booked shows
- **User Booking History**: View all bookings for a user

### Register a Show
```
Enter your choice: 1
Enter show name: TMKOC
Available genres: [Comedy, Theatre, Tech, Singing]
Enter genre: Comedy
TMKOC show is registered !!
```

### Onboard Show Slots
```
Enter your choice: 2
Enter show name: TMKOC
Enter time slots (format: HH:mm-HH:mm capacity). Type 'done' to finish:
Example: 09:00-10:00 5
Time slot and capacity: 09:00-10:00 3
Time slot and capacity: 12:00-13:00 2
Time slot and capacity: done
Done!
```

### Book Ticket
```
Enter your choice: 4
Enter user name: UserA
Enter show name: TMKOC
Enter time slot (HH:mm-HH:mm): 12:00-13:00
Enter number of tickets: 2
Booked. Booking id: 1001
```

## Key Components

- **ShowService**: Manages show registration and slot availability
- **BookingService**: Handles ticket booking, cancellation, and waitlist
- **TimeValidator**: Validates time slot formats and constraints
- **BookingIdGenerator**: Generates unique booking IDs
- **Repositories**: In-memory data storage for shows, bookings, and waitlist

## Business Rules

1. Shows must be registered before adding time slots
2. Time slots must be exactly 1 hour (9 AM - 9 PM)
3. Users cannot book overlapping time slots
4. Tickets cannot be partially booked
5. Waitlist automatically processes when bookings are cancelled
6. Shows are ranked by start time by default

## Error Handling

- Invalid time slot formats
- Insufficient capacity
- Duplicate bookings
- Non-existent shows/bookings
- Invalid user inputs

The application runs continuously until the user chooses to exit, with comprehensive error handling and user-friendly messages.
