/**
 * Represents a customer in the hotel booking system.
 * Stores customer information and provides validation methods.
 */
public class Customer
{
    private static int customerIDCounter = 1000;
    private int customerID;
    private String name;
    private String email;
    private String phoneNumber;
    
    /**
     * Constructor for Customer class.
     * @param name The customer's full name
     * @param email The customer's email address
     * @param phoneNumber The customer's phone number
     */
    public Customer(String name, String email, String phoneNumber)
    {
        this.customerID = ++customerIDCounter;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
    
    /**
     * Get the customer ID
     * @return The unique customer ID
     */
    public int getCustomerID()
    {
        return customerID;
    }
    
    /**
     * Get the customer name
     * @return The customer's name
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Set the customer name
     * @param name The name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }
    
    /**
     * Get the customer email
     * @return The customer's email address
     */
    public String getEmail()
    {
        return email;
    }
    
    /**
     * Set the customer email
     * @param email The email to set
     */
    public void setEmail(String email)
    {
        this.email = email;
    }
    
    /**
     * Get the customer phone number
     * @return The customer's phone number
     */
    public String getPhoneNumber()
    {
        return phoneNumber;
    }
    
    /**
     * Set the customer phone number
     * @param phoneNumber The phone number to set
     */
    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }
    
    /**
     * Validate the email address (must contain @)
     * @return true if valid email format, false otherwise
     */
    public boolean validateEmail()
    {
        return email != null && email.contains("@");
    }
    
    /**
     * Validate the customer phone number (must be numeric and 10 digits)
     * @return true if valid phone format, false otherwise
     */
    public boolean validatePhoneNumber()
    {
        if (phoneNumber == null || phoneNumber.length() != 10)
        {
            return false;
        }
        try
        {
            Long.parseLong(phoneNumber);
            return true;
        }
        catch (NumberFormatException e)
        {
            return false;
        }
    }
    
    /**
     * Validate the customer name (must be at least 2 characters and contain only alphabets and spaces)
     * @return true if valid name, false otherwise
     */
    public boolean validateName()
    {
        if (name == null || name.length() < 2)
        {
            return false;
        }
        // Check if name contains only alphabets and spaces
        return name.matches("^[a-zA-Z\\s]+$");
    }
    
    /**
     * Return a string representation of the customer
     * @return Customer details as a string
     */
    @Override
    public String toString()
    {
        return "Customer ID: " + customerID + ", Name: " + name + 
               ", Email: " + email + ", Phone: " + phoneNumber;
    }
}
