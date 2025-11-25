import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Panel for creating new hotel bookings.
 * Extends JPanel and provides input fields for customer details and booking information.
 * Uses GridBagLayout for flexible component positioning.
 */
public class BookingPanel extends JPanel
{
    private HotelManager hotelManager;
    
    // Customer details components
    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;
    
    // Booking details components
    private JComboBox<String> roomTypeCombo;
    private JTextField checkInField;
    private JTextField checkOutField;
    private JComboBox<Room> roomCombo;
    
    // Action buttons
    private JButton confirmButton;
    private JButton clearButton;
    
    // Display label
    private JLabel priceLabel;
    
    /**
     * Constructor for BookingPanel.
     * @param hotelManager Reference to the HotelManager instance
     */
    public BookingPanel(HotelManager hotelManager)
    {
        this.hotelManager = hotelManager;
        setLayout(new GridBagLayout());
        setBackground(new Color(236, 240, 241));
        
        createComponents();
        attachListeners();
    }
    
    /**
     * Create all GUI components for the booking form
     */
    private void createComponents()
    {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Title
        JLabel titleLabel = new JLabel("Hotel Booking System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(44, 62, 80));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);
        
        // Customer Details Section
        JLabel customerLabel = new JLabel("Customer Details:");
        customerLabel.setFont(new Font("Arial", Font.BOLD, 14));
        customerLabel.setForeground(new Color(44, 62, 80));
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(customerLabel, gbc);
        
        // Name
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(new JLabel("Name:"), gbc);
        nameField = new JTextField(20);
        nameField.setBackground(Color.WHITE);
        gbc.gridx = 1;
        add(nameField, gbc);
        
        // Email
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Email:"), gbc);
        emailField = new JTextField(20);
        emailField.setBackground(Color.WHITE);
        gbc.gridx = 1;
        add(emailField, gbc);
        
        // Phone
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Phone (10 digits):"), gbc);
        phoneField = new JTextField(20);
        phoneField.setBackground(Color.WHITE);
        gbc.gridx = 1;
        add(phoneField, gbc);
        
        // Booking Details Section
        JLabel bookingLabel = new JLabel("Booking Details:");
        bookingLabel.setFont(new Font("Arial", Font.BOLD, 14));
        bookingLabel.setForeground(new Color(44, 62, 80));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        add(bookingLabel, gbc);
        
        // Room Type
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        add(new JLabel("Room Type:"), gbc);
        roomTypeCombo = new JComboBox<>(new String[]{"Single", "Double", "Suite"});
        roomTypeCombo.setBackground(Color.WHITE);
        roomTypeCombo.addActionListener(e -> updateRoomCombo());
        gbc.gridx = 1;
        add(roomTypeCombo, gbc);
        
        // Room Selection
        gbc.gridx = 0;
        gbc.gridy = 7;
        add(new JLabel("Select Room:"), gbc);
        roomCombo = new JComboBox<>();
        roomCombo.setBackground(Color.WHITE);
        gbc.gridx = 1;
        add(roomCombo, gbc);
        
        // Check-In Date
        gbc.gridx = 0;
        gbc.gridy = 8;
        add(new JLabel("Check-In (DD/MM/YYYY):"), gbc);
        checkInField = new JTextField(20);
        checkInField.setBackground(Color.WHITE);
        gbc.gridx = 1;
        add(checkInField, gbc);
        
        // Check-Out Date
        gbc.gridx = 0;
        gbc.gridy = 9;
        add(new JLabel("Check-Out (DD/MM/YYYY):"), gbc);
        checkOutField = new JTextField(20);
        checkOutField.setBackground(Color.WHITE);
        gbc.gridx = 1;
        add(checkOutField, gbc);
        
        // Price Display
        priceLabel = new JLabel("Total Price: $0.00");
        priceLabel.setFont(new Font("Arial", Font.BOLD, 12));
        priceLabel.setForeground(new Color(39, 174, 96)); // Green
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        add(priceLabel, gbc);
        
        // Buttons
        gbc.gridy = 11;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(236, 240, 241));
        
        confirmButton = new JButton("Confirm Booking");
        confirmButton.setBackground(new Color(52, 152, 219)); // Bright blue
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setFont(new Font("Arial", Font.BOLD, 12));
        confirmButton.setPreferredSize(new Dimension(150, 40));
        
        clearButton = new JButton("Clear");
        clearButton.setBackground(new Color(149, 165, 166)); // Gray
        clearButton.setForeground(Color.WHITE);
        clearButton.setFont(new Font("Arial", Font.BOLD, 12));
        clearButton.setPreferredSize(new Dimension(100, 40));
        
        buttonPanel.add(confirmButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(clearButton);
        add(buttonPanel, gbc);
        
        // Initialize room combo
        updateRoomCombo();
    }
    
    /**
     * Update the room combo box based on selected room type
     */
    private void updateRoomCombo()
    {
        String selectedType = (String) roomTypeCombo.getSelectedItem();
        ArrayList<Room> availableRooms = hotelManager.searchAvailableRooms(selectedType);
        
        roomCombo.removeAllItems();
        for (Room room : availableRooms)
        {
            roomCombo.addItem(room);
        }
    }
    
    /**
     * Attach event listeners to buttons
     */
    private void attachListeners()
    {
        confirmButton.addActionListener(e -> confirmBooking());
        clearButton.addActionListener(e -> clearFields());
        checkInField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener()
        {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { updatePrice(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { updatePrice(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { updatePrice(); }
        });
        checkOutField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener()
        {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { updatePrice(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { updatePrice(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { updatePrice(); }
        });
    }
    
    /**
     * Update the price display based on check-in, check-out, and selected room
     */
    private void updatePrice()
    {
        if (roomCombo.getSelectedItem() == null)
        {
            priceLabel.setText("Total Price: $0.00");
            return;
        }
        
        String checkIn = checkInField.getText();
        String checkOut = checkOutField.getText();
        Room selectedRoom = (Room) roomCombo.getSelectedItem();
        
        if (!checkIn.isEmpty() && !checkOut.isEmpty() && selectedRoom != null)
        {
            Booking tempBooking = new Booking(new Customer("temp", "temp@temp.com", "0000000000"), selectedRoom, checkIn, checkOut);
            double price = tempBooking.calculateTotalPrice();
            priceLabel.setText(String.format("Total Price: $%.2f", price));
        }
        else
        {
            priceLabel.setText("Total Price: $0.00");
        }
    }
    
    /**
     * Confirm and process the booking
     */
    private void confirmBooking()
    {
        // Validate inputs
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String checkIn = checkInField.getText().trim();
        String checkOut = checkOutField.getText().trim();
        
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || checkIn.isEmpty() || checkOut.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Create customer and validate
        Customer customer = new Customer(name, email, phone);
        if (!customer.validateName())
        {
            JOptionPane.showMessageDialog(this, "Name must contain only alphabets and be at least 2 characters!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!customer.validateEmail())
        {
            JOptionPane.showMessageDialog(this, "Email must contain '@' symbol!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!customer.validatePhoneNumber())
        {
            JOptionPane.showMessageDialog(this, "Phone number must be exactly 10 digits!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Get selected room
        Room selectedRoom = (Room) roomCombo.getSelectedItem();
        if (selectedRoom == null)
        {
            JOptionPane.showMessageDialog(this, "Please select a room!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Check date validity
        if (!isValidDateFormat(checkIn) || !isValidDateFormat(checkOut))
        {
            JOptionPane.showMessageDialog(this, "Please use DD/MM/YYYY format for dates!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Check if dates are logical
        if (!isCheckOutAfterCheckIn(checkIn, checkOut))
        {
            JOptionPane.showMessageDialog(this, "Check-out date must be after check-in date!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Check room availability for dates
        if (!hotelManager.isRoomAvailableForDates(selectedRoom.getRoomNumber(), checkIn, checkOut))
        {
            JOptionPane.showMessageDialog(this, "Room not available for selected dates!", "Booking Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Create and add booking
        Booking booking = new Booking(customer, selectedRoom, checkIn, checkOut);
        hotelManager.addBooking(booking);
        hotelManager.addCustomer(customer);
        
        // Show confirmation
        String confirmationText = "Booking Confirmed!\n\n" +
                                 "Booking ID: " + booking.getBookingID() + "\n" +
                                 "Customer: " + customer.getName() + "\n" +
                                 "Room: " + selectedRoom.getRoomNumber() + " (" + selectedRoom.getRoomType() + ")\n" +
                                 "Check-In: " + checkIn + "\n" +
                                 "Check-Out: " + checkOut + "\n" +
                                 String.format("Total Price: $%.2f", booking.getTotalPrice());
        JOptionPane.showMessageDialog(this, confirmationText, "Booking Confirmed", JOptionPane.INFORMATION_MESSAGE);
        
        clearFields();
    }
    
    /**
     * Check if date format is valid (DD/MM/YYYY)
     */
    private boolean isValidDateFormat(String date)
    {
        if (date == null || date.length() != 10)
        {
            return false;
        }
        try
        {
            Integer.parseInt(date.substring(0, 2)); // Day
            Integer.parseInt(date.substring(3, 5)); // Month
            Integer.parseInt(date.substring(6, 10)); // Year
            return date.charAt(2) == '/' && date.charAt(5) == '/';
        }
        catch (Exception e)
        {
            return false;
        }
    }
    
    /**
     * Check if check-out date is after check-in date
     */
    private boolean isCheckOutAfterCheckIn(String checkIn, String checkOut)
    {
        try
        {
            long checkInTime = dateToMillis(checkIn);
            long checkOutTime = dateToMillis(checkOut);
            return checkOutTime > checkInTime;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    
    /**
     * Convert DD/MM/YYYY date to milliseconds
     */
    private long dateToMillis(String date)
    {
        int day = Integer.parseInt(date.substring(0, 2));
        int month = Integer.parseInt(date.substring(3, 5));
        int year = Integer.parseInt(date.substring(6, 10));
        return ((long)year * 365 + month * 30 + day) * 24 * 60 * 60 * 1000L;
    }
    
    /**
     * Clear all input fields
     */
    private void clearFields()
    {
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        checkInField.setText("");
        checkOutField.setText("");
        roomTypeCombo.setSelectedIndex(0);
        updateRoomCombo();
        priceLabel.setText("Total Price: $0.00");
    }
    
    /**
     * Refresh panel when displayed
     */
    public void refreshPanel()
    {
        updateRoomCombo();
    }
}
