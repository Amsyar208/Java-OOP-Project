/**
 * Represents a room in the hotel.
 * Stores room information including number, type, price, and availability status.
 */
public class Room
{
    private int roomNumber;
    private String roomType;
    private double pricePerNight;
    private boolean isAvailable;
    
    /**
     * Constructor for Room class.
     * @param roomNumber The unique room number
     * @param roomType The type of room (Single, Double, Suite)
     * @param pricePerNight The nightly rate for this room
     */
    public Room(int roomNumber, String roomType, double pricePerNight)
    {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.isAvailable = true;
    }
    
    /**
     * Get the room number
     * @return The room number
     */
    public int getRoomNumber()
    {
        return roomNumber;
    }
    
    /**
     * Set the room number
     * @param roomNumber The room number to set
     */
    public void setRoomNumber(int roomNumber)
    {
        this.roomNumber = roomNumber;
    }
    
    /**
     * Get the room type
     * @return The room type (Single, Double, Suite)
     */
    public String getRoomType()
    {
        return roomType;
    }
    
    /**
     * Set the room type
     * @param roomType The room type to set
     */
    public void setRoomType(String roomType)
    {
        this.roomType = roomType;
    }
    
    /**
     * Get the price per night
     * @return The nightly rate
     */
    public double getPricePerNight()
    {
        return pricePerNight;
    }
    
    /**
     * Set the price per night
     * @param pricePerNight The nightly rate to set
     */
    public void setPricePerNight(double pricePerNight)
    {
        this.pricePerNight = pricePerNight;
    }
    
    /**
     * Check if the room is available
     * @return true if available, false otherwise
     */
    public boolean isAvailable()
    {
        return isAvailable;
    }
    
    /**
     * Set the availability status of the room
     * @param available The availability status to set
     */
    public void setAvailable(boolean available)
    {
        isAvailable = available;
    }
    
    /**
     * Return a string representation of the room
     * @return Room details as a string
     */
    @Override
    public String toString()
    {
        return "Room " + roomNumber + " (" + roomType + ") - $" + pricePerNight + 
               "/night - " + (isAvailable ? "Available" : "Unavailable");
    }
}
