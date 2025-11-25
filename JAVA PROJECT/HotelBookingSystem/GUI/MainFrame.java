import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Main application window for the Hotel Booking System.
 * Extends JFrame and manages the overall GUI layout with menu bar and panel switching.
 * Uses CardLayout to switch between different panels (BookingPanel, ViewBookingsPanel, RoomSelectionPanel).
 */
public class MainFrame extends JFrame
{
    private HotelManager hotelManager;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private BookingPanel bookingPanel;
    private ViewBookingsPanel viewBookingsPanel;
    private RoomSelectionPanel roomSelectionPanel;
    
    // Panel identifiers for CardLayout
    private static final String BOOKING_PANEL = "BookingPanel";
    private static final String VIEW_BOOKINGS_PANEL = "ViewBookingsPanel";
    private static final String ROOM_SELECTION_PANEL = "RoomSelectionPanel";
    
    /**
     * Constructor for MainFrame.
     * Initializes the main window with menu bar, toolbar, and panels.
     */
    public MainFrame()
    {
        hotelManager = new HotelManager();
        
        // Set window properties
        setTitle("Hotel Booking System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(true);
        
        // Create menu bar
        createMenuBar();
        
        // Create main panel with CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(new Color(236, 240, 241)); // Light gray background
        
        // Initialize all panels
        bookingPanel = new BookingPanel(hotelManager);
        viewBookingsPanel = new ViewBookingsPanel(hotelManager);
        roomSelectionPanel = new RoomSelectionPanel(hotelManager);
        
        // Add panels to main panel
        mainPanel.add(bookingPanel, BOOKING_PANEL);
        mainPanel.add(viewBookingsPanel, VIEW_BOOKINGS_PANEL);
        mainPanel.add(roomSelectionPanel, ROOM_SELECTION_PANEL);
        
        // Add main panel to frame
        add(mainPanel, BorderLayout.CENTER);
        
        // Show BookingPanel by default
        cardLayout.show(mainPanel, BOOKING_PANEL);
        
        setVisible(true);
    }
    
    /**
     * Create and configure the menu bar for the application.
     * Menu structure:
     * - File: Exit
     * - Bookings: New Booking, View Bookings, Cancel Booking
     * - Rooms: View Rooms, Room Availability
     * - Help: About
     */
    private void createMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(44, 62, 80)); // Dark blue-gray
        menuBar.setForeground(Color.WHITE);
        
        // File Menu
        JMenu fileMenu = createMenu("File", Color.WHITE);
        JMenuItem exitItem = createMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        
        // Bookings Menu
        JMenu bookingsMenu = createMenu("Bookings", Color.WHITE);
        JMenuItem newBookingItem = createMenuItem("New Booking");
        newBookingItem.addActionListener(e -> showBookingPanel());
        bookingsMenu.add(newBookingItem);
        
        bookingsMenu.addSeparator();
        
        JMenuItem viewBookingsItem = createMenuItem("View Bookings");
        viewBookingsItem.addActionListener(e -> showViewBookingsPanel());
        bookingsMenu.add(viewBookingsItem);
        
        bookingsMenu.addSeparator();
        
        JMenuItem cancelBookingItem = createMenuItem("Cancel Booking");
        cancelBookingItem.addActionListener(e -> showCancelBookingDialog());
        bookingsMenu.add(cancelBookingItem);
        
        menuBar.add(bookingsMenu);
        
        // Rooms Menu
        JMenu roomsMenu = createMenu("Rooms", Color.WHITE);
        JMenuItem viewRoomsItem = createMenuItem("View Rooms");
        viewRoomsItem.addActionListener(e -> showRoomSelectionPanel());
        roomsMenu.add(viewRoomsItem);
        
        roomsMenu.addSeparator();
        
        JMenuItem roomAvailabilityItem = createMenuItem("Room Availability");
        roomAvailabilityItem.addActionListener(e -> showRoomAvailabilityDialog());
        roomsMenu.add(roomAvailabilityItem);
        
        menuBar.add(roomsMenu);
        
        // Help Menu
        JMenu helpMenu = createMenu("Help", Color.WHITE);
        JMenuItem aboutItem = createMenuItem("About");
        aboutItem.addActionListener(e -> showAboutDialog());
        helpMenu.add(aboutItem);
        menuBar.add(helpMenu);
        
        setJMenuBar(menuBar);
    }
    
    /**
     * Helper method to create a styled menu
     */
    private JMenu createMenu(String text, Color foreground)
    {
        JMenu menu = new JMenu(text);
        menu.setForeground(foreground);
        return menu;
    }
    
    /**
     * Helper method to create a styled menu item
     */
    private JMenuItem createMenuItem(String text)
    {
        return new JMenuItem(text);
    }
    
    /**
     * Show the BookingPanel
     */
    public void showBookingPanel()
    {
        bookingPanel.refreshPanel();
        cardLayout.show(mainPanel, BOOKING_PANEL);
    }
    
    /**
     * Show the ViewBookingsPanel
     */
    public void showViewBookingsPanel()
    {
        viewBookingsPanel.loadBookings();
        cardLayout.show(mainPanel, VIEW_BOOKINGS_PANEL);
    }
    
    /**
     * Show the RoomSelectionPanel
     */
    public void showRoomSelectionPanel()
    {
        roomSelectionPanel.displayRooms();
        cardLayout.show(mainPanel, ROOM_SELECTION_PANEL);
    }
    
    /**
     * Show dialog for canceling a booking
     */
    private void showCancelBookingDialog()
    {
        String bookingIDStr = JOptionPane.showInputDialog(this, "Enter Booking ID to cancel:");
        if (bookingIDStr != null && !bookingIDStr.trim().isEmpty())
        {
            try
            {
                int bookingID = Integer.parseInt(bookingIDStr);
                if (hotelManager.cancelBooking(bookingID))
                {
                    JOptionPane.showMessageDialog(this, "Booking cancelled successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    if (viewBookingsPanel != null)
                    {
                        viewBookingsPanel.loadBookings();
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(this, "Booking not found!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            catch (NumberFormatException ex)
            {
                JOptionPane.showMessageDialog(this, "Please enter a valid booking ID!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Show dialog for room availability
     */
    private void showRoomAvailabilityDialog()
    {
        String roomTypeInput = JOptionPane.showInputDialog(this, "Enter room type (Single/Double/Suite):");
        if (roomTypeInput != null && !roomTypeInput.trim().isEmpty())
        {
            java.util.ArrayList<Room> availableRooms = hotelManager.searchAvailableRooms(roomTypeInput);
            if (availableRooms.size() > 0)
            {
                StringBuilder message = new StringBuilder("Available " + roomTypeInput + " rooms:\n\n");
                for (Room room : availableRooms)
                {
                    message.append(room.toString()).append("\n");
                }
                JOptionPane.showMessageDialog(this, message.toString(), "Available Rooms", JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
                JOptionPane.showMessageDialog(this, "No available rooms of this type!", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    /**
     * Show About dialog
     */
    private void showAboutDialog()
    {
        String aboutText = "Hotel Booking System v1.0\n\n" +
                          "A simple Java Swing application for managing hotel reservations.\n\n" +
                          "Features:\n" +
                          "- Make new bookings\n" +
                          "- View existing bookings\n" +
                          "- Cancel bookings\n" +
                          "- Search available rooms\n\n" +
                          "Built with Java Swing\n" +
                          "Data stored in memory (ArrayList)";
        JOptionPane.showMessageDialog(this, aboutText, "About", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Main method to launch the application
     */
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new MainFrame();
            }
        });
    }
}
