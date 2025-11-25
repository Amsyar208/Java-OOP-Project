/**
 * Represents a booking in the hotel booking system.
 * Manages booking information including customer, room, dates, and price calculation.
 */
public class Booking
{
    private static int bookingIDCounter = 5000;
    private int bookingID;
    private Customer customer;
    private Room room;
    private String checkInDate;
    private String checkOutDate;
    private double totalPrice;
    
    /**
     * Constructor for Booking class.
     * @param customer The customer making the booking
     * @param room The room being booked
     * @param checkInDate The check-in date (DD/MM/YYYY format)
     * @param checkOutDate The check-out date (DD/MM/YYYY format)
     */
    public Booking(Customer customer, Room room, String checkInDate, String checkOutDate)
    {
        this.bookingID = ++bookingIDCounter;
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalPrice = 0;
    }
    
    /**
     * Get the booking ID
     * @return The unique booking ID
     */
    public int getBookingID()
    {
        return bookingID;
    }
    
    /**
     * Get the customer
     * @return The Customer object associated with this booking
     */
    public Customer getCustomer()
    {
        return customer;
    }
    
    /**
     * Set the customer
     * @param customer The customer to set
     */
    public void setCustomer(Customer customer)
    {
        this.customer = customer;
    }
    
    /**
     * Get the room
     * @return The Room object associated with this booking
     */
    public Room getRoom()
    {
        return room;
    }
    
    /**
     * Set the room
     * @param room The room to set
     */
    public void setRoom(Room room)
    {
        this.room = room;
    }
    
    /**
     * Get the check-in date
     * @return The check-in date (DD/MM/YYYY format)
     */
    public String getCheckInDate()
    {
        return checkInDate;
    }
    
    /**
     * Set the check-in date
     * @param checkInDate The check-in date to set
     */
    public void setCheckInDate(String checkInDate)
    {
        this.checkInDate = checkInDate;
    }
    
    /**
     * Get the check-out date
     * @return The check-out date (DD/MM/YYYY format)
     */
    public String getCheckOutDate()
    {
        return checkOutDate;
    }
    
    /**
     * Set the check-out date
     * @param checkOutDate The check-out date to set
     */
    public void setCheckOutDate(String checkOutDate)
    {
        this.checkOutDate = checkOutDate;
    }
    
    /**
     * Get the total booking price
     * @return The total price for the booking
     */
    public double getTotalPrice()
    {
        return totalPrice;
    }
    
    /**
     * Set the total booking price
     * @param totalPrice The total price to set
     */
    public void setTotalPrice(double totalPrice)
    {
        this.totalPrice = totalPrice;
    }
    
    /**
     * Calculate the total booking price based on number of nights and room price.
     * Parses dates in DD/MM/YYYY format.
     * @return The calculated total price
     */
    public double calculateTotalPrice()
    {
        try
        {
            // Parse dates in DD/MM/YYYY format
            int checkInDay = Integer.parseInt(checkInDate.substring(0, 2));
            int checkInMonth = Integer.parseInt(checkInDate.substring(3, 5));
            int checkInYear = Integer.parseInt(checkInDate.substring(6, 10));
            
            int checkOutDay = Integer.parseInt(checkOutDate.substring(0, 2));
            int checkOutMonth = Integer.parseInt(checkOutDate.substring(3, 5));
            int checkOutYear = Integer.parseInt(checkOutDate.substring(6, 10));
            
            // Simple day difference calculation (assumes all months have 30 days for simplicity)
            int daysDifference = (checkOutYear - checkInYear) * 365 +
                                (checkOutMonth - checkInMonth) * 30 +
                                (checkOutDay - checkInDay);
            
            // Ensure at least 1 night
            if (daysDifference < 1)
            {
                daysDifference = 1;
            }
            
            totalPrice = daysDifference * room.getPricePerNight();
            return totalPrice;
        }
        catch (Exception e)
        {
            return 0;
        }
    }
    
    /**
     * Return a string representation of the booking
     * @return Booking details as a string
     */
    @Override
    public String toString()
    {
        return "Booking ID: " + bookingID + ", Customer: " + customer.getName() + 
               ", Room: " + room.getRoomNumber() + ", Check-In: " + checkInDate + 
               ", Check-Out: " + checkOutDate + ", Total: $" + totalPrice;
    }
}
