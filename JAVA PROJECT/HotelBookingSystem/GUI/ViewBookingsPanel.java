import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Panel for viewing all hotel bookings.
 * Extends JPanel and displays bookings in a JTable with search functionality.
 * Allows users to view booking details and search by customer name or booking ID.
 */
public class ViewBookingsPanel extends JPanel
{
    private HotelManager hotelManager;
    private JTable bookingsTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JComboBox<String> searchTypeCombo;
    private JButton searchButton;
    private JButton refreshButton;
    
    /**
     * Constructor for ViewBookingsPanel.
     * @param hotelManager Reference to the HotelManager instance
     */
    public ViewBookingsPanel(HotelManager hotelManager)
    {
        this.hotelManager = hotelManager;
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(236, 240, 241));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        createComponents();
    }
    
    /**
     * Create all GUI components for the view bookings panel
     */
    private void createComponents()
    {
        // Title
        JLabel titleLabel = new JLabel("Current Bookings");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(44, 62, 80));
        add(titleLabel, BorderLayout.NORTH);
        
        // Search Panel
        JPanel searchPanel = createSearchPanel();
        add(searchPanel, BorderLayout.NORTH);
        
        // Table Panel
        JPanel tablePanel = createTablePanel();
        add(tablePanel, BorderLayout.CENTER);
        
        // Button Panel
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Create the search panel with search fields
     */
    private JPanel createSearchPanel()
    {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setBackground(new Color(236, 240, 241));
        panel.setBorder(BorderFactory.createTitledBorder("Search Bookings"));
        
        // Search type dropdown
        searchTypeCombo = new JComboBox<>(new String[]{"Customer Name", "Booking ID"});
        searchTypeCombo.setBackground(Color.WHITE);
        panel.add(new JLabel("Search By:"));
        panel.add(searchTypeCombo);
        
        // Search field
        searchField = new JTextField(20);
        searchField.setBackground(Color.WHITE);
        panel.add(new JLabel("Search:"));
        panel.add(searchField);
        
        // Search button
        searchButton = new JButton("Search");
        searchButton.setBackground(new Color(52, 152, 219)); // Bright blue
        searchButton.setForeground(Color.WHITE);
        searchButton.setFont(new Font("Arial", Font.BOLD, 11));
        searchButton.addActionListener(e -> performSearch());
        panel.add(searchButton);
        
        // Refresh button
        refreshButton = new JButton("Show All");
        refreshButton.setBackground(new Color(39, 174, 96)); // Green
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFont(new Font("Arial", Font.BOLD, 11));
        refreshButton.addActionListener(e -> loadBookings());
        panel.add(refreshButton);
        
        return panel;
    }
    
    /**
     * Create the table panel with JTable
     */
    private JPanel createTablePanel()
    {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        // Create table model
        String[] columnNames = {"Booking ID", "Customer Name", "Room Number", "Room Type", "Check-In", "Check-Out", "Total Price"};
        tableModel = new DefaultTableModel(columnNames, 0)
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false; // Make table read-only
            }
        };
        
        // Create table
        bookingsTable = new JTable(tableModel);
        bookingsTable.setFont(new Font("Arial", Font.PLAIN, 11));
        bookingsTable.setRowHeight(25);
        bookingsTable.getTableHeader().setBackground(new Color(44, 62, 80));
        bookingsTable.getTableHeader().setForeground(Color.WHITE);
        bookingsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        // Add scroll pane
        JScrollPane scrollPane = new JScrollPane(bookingsTable);
        scrollPane.setBackground(Color.WHITE);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Create the button panel with action buttons
     */
    private JPanel createButtonPanel()
    {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panel.setBackground(new Color(236, 240, 241));
        
        JButton infoButton = new JButton("Total Bookings: 0");
        infoButton.setEnabled(false);
        infoButton.setBackground(new Color(149, 165, 166));
        infoButton.setForeground(Color.WHITE);
        panel.add(infoButton);
        
        return panel;
    }
    
    /**
     * Load all bookings into the table
     */
    public void loadBookings()
    {
        tableModel.setRowCount(0); // Clear table
        ArrayList<Booking> allBookings = hotelManager.getAllBookings();
        
        for (Booking booking : allBookings)
        {
            Object[] row = {
                booking.getBookingID(),
                booking.getCustomer().getName(),
                booking.getRoom().getRoomNumber(),
                booking.getRoom().getRoomType(),
                booking.getCheckInDate(),
                booking.getCheckOutDate(),
                String.format("$%.2f", booking.getTotalPrice())
            };
            tableModel.addRow(row);
        }
        
        updateBookingCount();
    }
    
    /**
     * Perform search based on selected criteria
     */
    private void performSearch()
    {
        String searchTerm = searchField.getText().trim();
        if (searchTerm.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Please enter a search term!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        tableModel.setRowCount(0); // Clear table
        String searchType = (String) searchTypeCombo.getSelectedItem();
        ArrayList<Booking> searchResults = new ArrayList<>();
        
        if ("Customer Name".equals(searchType))
        {
            // Search is case-sensitive as per requirements
            searchResults = hotelManager.searchBookingsByCustomerName(searchTerm);
            if (searchResults.isEmpty())
            {
                JOptionPane.showMessageDialog(this, "No bookings found for customer: " + searchTerm, "Search Result", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        else if ("Booking ID".equals(searchType))
        {
            try
            {
                int bookingID = Integer.parseInt(searchTerm);
                Booking booking = hotelManager.searchBookingByID(bookingID);
                if (booking != null)
                {
                    searchResults.add(booking);
                }
                else
                {
                    JOptionPane.showMessageDialog(this, "Booking ID not found: " + bookingID, "Search Result", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            catch (NumberFormatException e)
            {
                JOptionPane.showMessageDialog(this, "Please enter a valid booking ID!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        // Display search results
        for (Booking booking : searchResults)
        {
            Object[] row = {
                booking.getBookingID(),
                booking.getCustomer().getName(),
                booking.getRoom().getRoomNumber(),
                booking.getRoom().getRoomType(),
                booking.getCheckInDate(),
                booking.getCheckOutDate(),
                String.format("$%.2f", booking.getTotalPrice())
            };
            tableModel.addRow(row);
        }
        
        updateBookingCount();
    }
    
    /**
     * Update the booking count display
     */
    private void updateBookingCount()
    {
        int rowCount = tableModel.getRowCount();
        // This can be enhanced to show total count vs displayed count
    }
}
