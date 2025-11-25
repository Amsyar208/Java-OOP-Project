import java.util.ArrayList;

/**
 * Manages all hotel operations including rooms, bookings, and customers.
 * Acts as the central data manager for the hotel booking system.
 */
public class HotelManager
{
    private ArrayList<Room> rooms;
    private ArrayList<Booking> bookings;
    private ArrayList<Customer> customers;
    
    /**
     * Constructor for HotelManager class.
     * Initializes empty collections for rooms, bookings, and customers.
     * Pre-populates with sample test data (5 rooms of different types).
     */
    public HotelManager()
    {
        rooms = new ArrayList<Room>();
        bookings = new ArrayList<Booking>();
        customers = new ArrayList<Customer>();
        
        // Pre-populate with sample test data
        initializeSampleRooms();
    }
    
    /**
     * Initialize sample rooms for testing purposes
     */
    private void initializeSampleRooms()
    {
        rooms.add(new Room(101, "Single", 100.0));
        rooms.add(new Room(102, "Single", 100.0));
        rooms.add(new Room(201, "Double", 150.0));
        rooms.add(new Room(202, "Double", 150.0));
        rooms.add(new Room(301, "Suite", 250.0));
    }
    
    /**
     * Add a room to the system
     * @param room The Room object to add
     */
    public void addRoom(Room room)
    {
        rooms.add(room);
    }
    
    /**
     * Remove a room from the system by room number
     * @param roomNumber The room number to remove
     * @return true if room was removed, false if not found
     */
    public boolean removeRoom(int roomNumber)
    {
        for (int i = 0; i < rooms.size(); i++)
        {
            if (rooms.get(i).getRoomNumber() == roomNumber)
            {
                rooms.remove(i);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Get all rooms
     * @return ArrayList of all rooms
     */
    public ArrayList<Room> getAllRooms()
    {
        return rooms;
    }
    
    /**
     * Add a customer to the system
     * @param customer The Customer object to add
     */
    public void addCustomer(Customer customer)
    {
        customers.add(customer);
    }
    
    /**
     * Get all customers
     * @return ArrayList of all customers
     */
    public ArrayList<Customer> getAllCustomers()
    {
        return customers;
    }
    
    /**
     * Add a booking to the system
     * @param booking The Booking object to add
     * @return true if booking was added, false if not
     */
    public boolean addBooking(Booking booking)
    {
        // Calculate the total price before adding
        booking.calculateTotalPrice();
        bookings.add(booking);
        return true;
    }
    
    /**
     * Get all bookings
     * @return ArrayList of all bookings
     */
    public ArrayList<Booking> getAllBookings()
    {
        return bookings;
    }
    
    /**
     * Cancel a booking by booking ID
     * @param bookingID The booking ID to cancel
     * @return true if booking was cancelled, false if not found
     */
    public boolean cancelBooking(int bookingID)
    {
        for (int i = 0; i < bookings.size(); i++)
        {
            if (bookings.get(i).getBookingID() == bookingID)
            {
                bookings.remove(i);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Search for available rooms by room type
     * @param roomType The room type to search for
     * @return ArrayList of available rooms of the specified type
     */
    public ArrayList<Room> searchAvailableRooms(String roomType)
    {
        ArrayList<Room> availableRooms = new ArrayList<Room>();
        for (Room room : rooms)
        {
            if (room.getRoomType().equals(roomType) && room.isAvailable())
            {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }
    
    /**
     * Search for bookings by customer name (case-sensitive)
     * @param customerName The customer name to search for
     * @return ArrayList of bookings matching the customer name
     */
    public ArrayList<Booking> searchBookingsByCustomerName(String customerName)
    {
        ArrayList<Booking> matchingBookings = new ArrayList<Booking>();
        for (Booking booking : bookings)
        {
            if (booking.getCustomer().getName().equals(customerName))
            {
                matchingBookings.add(booking);
            }
        }
        return matchingBookings;
    }
    
    /**
     * Search for a booking by booking ID
     * @param bookingID The booking ID to search for
     * @return The Booking object if found, null otherwise
     */
    public Booking searchBookingByID(int bookingID)
    {
        for (Booking booking : bookings)
        {
            if (booking.getBookingID() == bookingID)
            {
                return booking;
            }
        }
        return null;
    }
    
    /**
     * Check if a specific room is available for a given date range
     * @param roomNumber The room number to check
     * @param checkInDate The check-in date (DD/MM/YYYY format)
     * @param checkOutDate The check-out date (DD/MM/YYYY format)
     * @return true if room is available for the date range, false otherwise
     */
    public boolean isRoomAvailableForDates(int roomNumber, String checkInDate, String checkOutDate)
    {
        // Check all bookings to see if this room is booked during the requested dates
        for (Booking booking : bookings)
        {
            if (booking.getRoom().getRoomNumber() == roomNumber)
            {
                // Check for date overlap
                if (datesOverlap(checkInDate, checkOutDate, booking.getCheckInDate(), booking.getCheckOutDate()))
                {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Helper method to check if two date ranges overlap
     * @param newCheckIn New check-in date (DD/MM/YYYY format)
     * @param newCheckOut New check-out date (DD/MM/YYYY format)
     * @param existingCheckIn Existing check-in date (DD/MM/YYYY format)
     * @param existingCheckOut Existing check-out date (DD/MM/YYYY format)
     * @return true if dates overlap, false otherwise
     */
    private boolean datesOverlap(String newCheckIn, String newCheckOut, String existingCheckIn, String existingCheckOut)
    {
        try
        {
            long newCheckInTime = dateToMillis(newCheckIn);
            long newCheckOutTime = dateToMillis(newCheckOut);
            long existingCheckInTime = dateToMillis(existingCheckIn);
            long existingCheckOutTime = dateToMillis(existingCheckOut);
            
            // Check overlap: new booking starts before existing ends and ends after existing starts
            return newCheckInTime < existingCheckOutTime && newCheckOutTime > existingCheckInTime;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    
    /**
     * Helper method to convert a date string to milliseconds
     * @param dateStr Date in DD/MM/YYYY format
     * @return Milliseconds representation of the date
     */
    private long dateToMillis(String dateStr)
    {
        try
        {
            int day = Integer.parseInt(dateStr.substring(0, 2));
            int month = Integer.parseInt(dateStr.substring(3, 5));
            int year = Integer.parseInt(dateStr.substring(6, 10));
            
            // Simple conversion: (year * 365 + month * 30 + day) * 24 * 60 * 60 * 1000
            long millis = ((long)year * 365 + month * 30 + day) * 24 * 60 * 60 * 1000L;
            return millis;
        }
        catch (Exception e)
        {
            return 0;
        }
    }
}
