# Hotel Booking System - Design Document

## 1. Project Overview

**Project Name:** Hotel Booking System  
**Platform:** BlueJ IDE  
**Language:** Java  
**GUI Framework:** Java Swing  
**Version:** 1.0  

The Hotel Booking System is a desktop application designed to manage hotel reservations through an intuitive graphical user interface. The system allows users to search for available rooms, make bookings, view booking details, and manage customer information.

## 2. System Architecture

### Design Pattern
The application follows the **Model-View-Controller (MVC)** pattern to separate concerns and improve maintainability:
- **Model:** Manages data and business logic (Room, Booking, Customer classes)
- **View:** GUI components using Java Swing (JFrame, JPanel, JTable)
- **Controller:** Handles user interactions and updates the model/view

### Technology Stack
- **Frontend:** Java Swing components (JFrame, JPanel, JButton, JTextField, JTable)
- **Backend:** Pure Java with ArrayList-based data storage
- **IDE:** BlueJ (suitable for educational and small-scale projects)

## 3. Core Features

### 3.1 User Features
- View available rooms by room type
- Make new bookings with customer details
- View booking confirmation
- Check booking history
- Cancel existing bookings

### 3.2 Admin Features
- Add/remove rooms
- View all bookings
- Search bookings by customer name or booking ID
- Generate booking reports

## 4. System Components

### 4.1 Main Classes

#### Room Class
```java
- roomNumber: int
- roomType: String (Single, Double, Suite)
- pricePerNight: double
- isAvailable: boolean
+ Room(roomNumber, roomType, price)
+ getters/setters
+ checkAvailability(): boolean
```

#### Customer Class
```java
- customerID: int
- name: String
- email: String
- phoneNumber: String
+ Customer(name, email, phone)
+ getters/setters
+ validateEmail(): boolean
```

#### Booking Class
```java
- bookingID: int
- customer: Customer
- room: Room
- checkInDate: String
- checkOutDate: String
- totalPrice: double
+ Booking(customer, room, checkIn, checkOut)
+ calculateTotalPrice(): double
+ getters/setters
```

#### HotelManager Class
```java
- rooms: ArrayList<Room>
- bookings: ArrayList<Booking>
- customers: ArrayList<Customer>
+ addRoom(Room): void
+ removeRoom(int): void
+ makeBooking(Booking): boolean
+ cancelBooking(int): boolean
+ searchAvailableRooms(String): ArrayList<Room>
+ getAllBookings(): ArrayList<Booking>
```

### 4.2 GUI Classes

#### MainFrame Class
```java
- Extends JFrame
- Contains main window setup
- Manages navigation between panels
+ MainFrame()
+ showBookingPanel(): void
+ showViewBookingsPanel(): void
```

#### BookingPanel Class
```java
- Extends JPanel
- Input fields: name, email, phone, room type, dates
- Confirm and Clear buttons
+ BookingPanel()
+ confirmBooking(): void
+ clearFields(): void
```

#### ViewBookingsPanel Class
```java
- Extends JPanel
- JTable to display bookings
- Search functionality
+ ViewBookingsPanel()
+ loadBookings(): void
+ searchBooking(String): void
```

#### RoomSelectionPanel Class
```java
- Extends JPanel
- Display available rooms
- Room selection interface
+ RoomSelectionPanel()
+ displayRooms(): void
+ selectRoom(int): void
```

## 5. GUI Layout

### Main Window Structure
```
[Menu Bar: File | Bookings | Rooms | Help]
├─ File: Exit
├─ Bookings: New Booking, View Bookings, Cancel Booking
├─ Rooms: View Rooms, Room Availability
└─ Help: About
```

### Booking Form Layout
```
┌─────────────────────────────────────┐
│  Hotel Booking System              │
├─────────────────────────────────────┤
│  Customer Details:                  │
│  Name: [____________]              │
│  Email: [____________]             │
│  Phone: [____________]             │
│                                     │
│  Booking Details:                   │
│  Room Type: [Dropdown▼]           │
│  Check-In: [DD/MM/YYYY]           │
│  Check-Out: [DD/MM/YYYY]          │
│                                     │
│  [Confirm] [Clear]                 │
└─────────────────────────────────────┘
```

### Bookings View Layout
```
┌──────────────────────────────────────────────────────┐
│  Current Bookings                                    │
├──────────────────────────────────────────────────────┤
│  Search: [____________] [Search Button]             │
│                                                      │
│  ┌──────────────────────────────────────────────┐  │
│  │ID │Name   │Room │Check-In  │Check-Out │Price│  │
│  ├───┼───────┼─────┼──────────┼──────────┼─────┤  │
│  │001│John   │101  │25/11/2025│27/11/2025│$200│  │
│  └──────────────────────────────────────────────┘  │
│                                                      │
│  [Cancel Selected] [Refresh]                        │
└──────────────────────────────────────────────────────┘
```

## 6. Data Storage

Since BlueJ is used for educational purposes, the system uses **in-memory data storage** with ArrayList collections:
- ArrayList<Room> for storing room information
- ArrayList<Booking> for booking records
- ArrayList<Customer> for customer data

**Note:** Data is not persisted after application closes. For persistence, file I/O (text files or serialization) can be added in future versions.

## 7. Key Workflows

### Booking Process Flow
1. User opens "New Booking" from menu
2. System displays BookingPanel with input fields
3. User enters customer details and booking information
4. User selects room type from dropdown
5. System validates input fields
6. System checks room availability
7. System calculates total price
8. User clicks "Confirm" button
9. System creates Booking object and adds to bookings list
10. System displays confirmation message with booking ID

### View Bookings Flow
1. User selects "View Bookings" from menu
2. System loads ViewBookingsPanel
3. System retrieves all bookings from HotelManager
4. System populates JTable with booking data
5. User can search or filter bookings
6. User can select booking to cancel

## 8. Input Validation

### Customer Details
- Name: Required, minimum 2 characters, alphabets only
- Email: Required, valid email format (contains @ and .)
- Phone: Required, 10 digits, numeric only

### Booking Details
- Room Type: Required, selected from dropdown
- Check-In Date: Required, cannot be in the past
- Check-Out Date: Required, must be after check-in date

## 9. Error Handling

The system handles the following scenarios:
- Empty or invalid input fields → Display error dialog
- No rooms available → Display "No rooms available" message
- Invalid date selection → Display date validation error
- Booking cancellation failure → Display error message
- Duplicate booking ID → Generate unique ID automatically

## 10. GUI Components Used

### Java Swing Components
- **JFrame:** Main application window
- **JPanel:** Container panels for different views
- **JLabel:** Field labels and headings
- **JTextField:** Text input for customer details
- **JComboBox:** Dropdown for room type selection
- **JButton:** Action buttons (Confirm, Clear, Search, Cancel)
- **JTable:** Display booking records in tabular format
- **JMenuBar/JMenu/JMenuItem:** Navigation menu
- **JOptionPane:** Dialog boxes for messages and confirmations
- **JScrollPane:** Scrollable content for tables

## 11. Color Scheme & Design

### Suggested Color Palette
- **Primary:** #2C3E50 (Dark Blue-Gray) - Headers
- **Secondary:** #3498DB (Bright Blue) - Buttons
- **Background:** #ECF0F1 (Light Gray) - Main panels
- **Text:** #2C3E50 (Dark Gray)
- **Success:** #27AE60 (Green) - Confirmation messages
- **Error:** #E74C3C (Red) - Error messages

## 12. Implementation Steps

### Phase 1: Model Classes
1. Create Room class with attributes and methods
2. Create Customer class with validation methods
3. Create Booking class with price calculation
4. Create HotelManager class with collection management

### Phase 2: Basic GUI
1. Create MainFrame with menu bar
2. Create BookingPanel with input fields
3. Create ViewBookingsPanel with JTable
4. Test basic navigation between panels

### Phase 3: Integration
1. Connect GUI components to model classes
2. Implement event handlers for buttons
3. Add input validation logic
4. Implement booking creation and storage

### Phase 4: Testing & Refinement
1. Test all workflows and edge cases
2. Refine GUI layout and styling
3. Add error handling and user feedback
4. Document code with comments

## 13. Future Enhancements

- Database integration (MySQL) for data persistence
- User authentication (login/signup system)
- Payment processing module
- Email confirmation for bookings
- Room images and detailed descriptions
- Advanced search and filtering options
- Reporting and analytics dashboard
- Multi-language support

## 14. BlueJ-Specific Considerations

### Running the Application
1. Compile all classes in BlueJ
2. Right-click on MainFrame class
3. Select "new MainFrame()" to create instance
4. Application window will appear

### Testing in BlueJ
- Use BlueJ's object bench to test individual classes
- Create test objects for Room, Customer, and Booking
- Verify methods work correctly before GUI integration

### Project Structure in BlueJ
```
HotelBookingSystem/
├── Model/
│   ├── Room.java
│   ├── Customer.java
│   ├── Booking.java
│   └── HotelManager.java
└── View/
    ├── MainFrame.java
    ├── BookingPanel.java
    ├── ViewBookingsPanel.java
    └── RoomSelectionPanel.java
```

## 15. Conclusion

This Hotel Booking System provides a foundational framework for managing hotel reservations using Java Swing in BlueJ. The MVC architecture ensures clean separation of concerns, making the code maintainable and extensible. The simple ArrayList-based storage is suitable for learning purposes, with clear paths for future enhancements including database integration and advanced features.
