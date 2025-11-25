import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Panel for viewing available rooms in the hotel.
 * Extends JPanel and displays all rooms with their details.
 * Allows users to filter rooms by type and view availability status.
 */
public class RoomSelectionPanel extends JPanel
{
    private HotelManager hotelManager;
    private JPanel roomsDisplayPanel;
    private JComboBox<String> roomTypeFilterCombo;
    private JButton filterButton;
    private JButton showAllButton;
    
    /**
     * Constructor for RoomSelectionPanel.
     * @param hotelManager Reference to the HotelManager instance
     */
    public RoomSelectionPanel(HotelManager hotelManager)
    {
        this.hotelManager = hotelManager;
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(236, 240, 241));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        createComponents();
    }
    
    /**
     * Create all GUI components for the room selection panel
     */
    private void createComponents()
    {
        // Title
        JLabel titleLabel = new JLabel("Available Rooms");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(44, 62, 80));
        
        // Filter Panel
        JPanel filterPanel = createFilterPanel();
        
        JPanel topPanel = new JPanel(new BorderLayout(10, 0));
        topPanel.setBackground(new Color(236, 240, 241));
        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(filterPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);
        
        // Rooms Display Panel
        roomsDisplayPanel = new JPanel();
        roomsDisplayPanel.setLayout(new GridLayout(0, 3, 10, 10)); // 3 columns
        roomsDisplayPanel.setBackground(new Color(236, 240, 241));
        
        JScrollPane scrollPane = new JScrollPane(roomsDisplayPanel);
        scrollPane.setBackground(new Color(236, 240, 241));
        add(scrollPane, BorderLayout.CENTER);
    }
    
    /**
     * Create the filter panel for room type selection
     */
    private JPanel createFilterPanel()
    {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panel.setBackground(new Color(236, 240, 241));
        
        panel.add(new JLabel("Filter by Type:"));
        
        roomTypeFilterCombo = new JComboBox<>(new String[]{"All", "Single", "Double", "Suite"});
        roomTypeFilterCombo.setBackground(Color.WHITE);
        panel.add(roomTypeFilterCombo);
        
        filterButton = new JButton("Filter");
        filterButton.setBackground(new Color(52, 152, 219));
        filterButton.setForeground(Color.WHITE);
        filterButton.setFont(new Font("Arial", Font.BOLD, 11));
        filterButton.addActionListener(e -> filterRooms());
        panel.add(filterButton);
        
        showAllButton = new JButton("Show All");
        showAllButton.setBackground(new Color(39, 174, 96));
        showAllButton.setForeground(Color.WHITE);
        showAllButton.setFont(new Font("Arial", Font.BOLD, 11));
        showAllButton.addActionListener(e -> displayRooms());
        panel.add(showAllButton);
        
        return panel;
    }
    
    /**
     * Display all rooms in the system
     */
    public void displayRooms()
    {
        roomsDisplayPanel.removeAll();
        ArrayList<Room> allRooms = hotelManager.getAllRooms();
        
        if (allRooms.isEmpty())
        {
            JLabel noRoomsLabel = new JLabel("No rooms available");
            noRoomsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            roomsDisplayPanel.add(noRoomsLabel);
        }
        else
        {
            for (Room room : allRooms)
            {
                JPanel roomCard = createRoomCard(room);
                roomsDisplayPanel.add(roomCard);
            }
        }
        
        roomsDisplayPanel.revalidate();
        roomsDisplayPanel.repaint();
    }
    
    /**
     * Filter and display rooms by selected type
     */
    private void filterRooms()
    {
        String selectedType = (String) roomTypeFilterCombo.getSelectedItem();
        
        roomsDisplayPanel.removeAll();
        
        if ("All".equals(selectedType))
        {
            displayRooms();
            return;
        }
        
        ArrayList<Room> filteredRooms = hotelManager.searchAvailableRooms(selectedType);
        
        if (filteredRooms.isEmpty())
        {
            JLabel noRoomsLabel = new JLabel("No available " + selectedType + " rooms");
            noRoomsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            roomsDisplayPanel.add(noRoomsLabel);
        }
        else
        {
            for (Room room : filteredRooms)
            {
                JPanel roomCard = createRoomCard(room);
                roomsDisplayPanel.add(roomCard);
            }
        }
        
        roomsDisplayPanel.revalidate();
        roomsDisplayPanel.repaint();
    }
    
    /**
     * Create a card panel for displaying room information
     */
    private JPanel createRoomCard(Room room)
    {
        JPanel card = new JPanel();
        card.setLayout(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(44, 62, 80), 2));
        card.setPreferredSize(new Dimension(200, 150));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        
        // Room number
        JLabel roomNumberLabel = new JLabel("Room " + room.getRoomNumber());
        roomNumberLabel.setFont(new Font("Arial", Font.BOLD, 16));
        roomNumberLabel.setForeground(new Color(44, 62, 80));
        card.add(roomNumberLabel, gbc);
        
        // Room type
        JLabel roomTypeLabel = new JLabel("Type: " + room.getRoomType());
        roomTypeLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        card.add(roomTypeLabel, gbc);
        
        // Price
        JLabel priceLabel = new JLabel("Price: $" + String.format("%.2f", room.getPricePerNight()) + "/night");
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        card.add(priceLabel, gbc);
        
        // Availability status
        JLabel availabilityLabel = new JLabel("Status: " + (room.isAvailable() ? "Available" : "Unavailable"));
        availabilityLabel.setFont(new Font("Arial", Font.BOLD, 12));
        availabilityLabel.setForeground(room.isAvailable() ? new Color(39, 174, 96) : new Color(231, 76, 60)); // Green or Red
        card.add(availabilityLabel, gbc);
        
        return card;
    }
}
