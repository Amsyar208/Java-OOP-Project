# Hotel Booking System - Architecture Documentation

## System Overview

The Hotel Booking System follows the **Model-View-Controller (MVC)** architectural pattern. Currently, Phase 1 & 2 implement the **Model** layer with pure business logic classes.

```
┌─────────────────────────────────────────────────────────┐
│                   PRESENTATION LAYER                     │
│  (Phase 4: MainFrame, BookingPanel, ViewBookingsPanel)   │
├─────────────────────────────────────────────────────────┤
│                   BUSINESS LOGIC LAYER                    │
│  (Phase 2 & 3: Model Classes)                            │
├─────────────────────────────────────────────────────────┤
│                   DATA STORAGE LAYER                      │
│  (ArrayList Collections - In-Memory)                     │
└─────────────────────────────────────────────────────────┘
```

---

## Model Classes Documentation

### 1. Room.java
**Purpose:** Represents a single hotel room entity

**Package:** Root (no package, single-file project for BlueJ)

**Responsibility:**
- Store room metadata (number, type, price, availability)
- Provide accessors for room properties
- Display room information via `toString()`

**Key Attributes:**
| Attribute | Type | Purpose |
|-----------|------|---------|
| `roomNumber` | int | Unique identifier (e.g., 101, 202) |
| `roomType` | String | Category: "Single", "Double", "Suite" |
| `pricePerNight` | double | Nightly rate in dollars |
| `isAvailable` | boolean | Current availability status |

**Public Methods:**
- `Room(int, String, double)` - Constructor
- `getRoomNumber()`, `setRoomNumber(int)` - Access room number
- `getRoomType()`, `setRoomType(String)` - Access room type
- `getPricePerNight()`, `setPricePerNight(double)` - Access price
- `isAvailable()`, `setAvailable(boolean)` - Access availability
- `toString()` - Return formatted room details

**Design Decisions:**
- Immutable room number on creation (can be changed but not recommended)
- Availability status starts as `true` (new rooms are available)
- Simple string-based room types (no enum) for BlueJ compatibility

**Example Usage:**
```java
Room single = new Room(101, "Single", 100.0);
Room suite = new Room(301, "Suite", 250.0);
```

---

### 2. Customer.java
**Purpose:** Represents a customer in the booking system

**Responsibility:**
- Store customer contact information
- Auto-generate unique customer IDs
- Validate customer input data
- Provide customer details for bookings

**Key Attributes:**
| Attribute | Type | Purpose |
|-----------|------|---------|
| `customerID` | int | Auto-incrementing unique ID (starting 1001) |
| `name` | String | Customer's full name |
| `email` | String | Contact email address |
| `phoneNumber` | String | 10-digit phone number |
| `customerIDCounter` | static int | Class-level counter for ID generation |

**Public Methods:**
- `Customer(String, String, String)` - Constructor (auto-generates ID)
- `getCustomerID()` - Get unique ID
- `getName()`, `setName(String)` - Access customer name
- `getEmail()`, `setEmail(String)` - Access email
- `getPhoneNumber()`, `setPhoneNumber(String)` - Access phone
- `validateEmail()` - Check if email contains "@"
- `validatePhoneNumber()` - Check if phone is 10 numeric digits
- `validateName()` - Check if name is 2+ characters, alphabets only
- `toString()` - Return formatted customer details

**Validation Logic:**
```
validateEmail():
  - Input: email string
  - Rule: Must contain "@" symbol
  - Return: true/false

validatePhoneNumber():
  - Input: phone string
  - Rule: Must be exactly 10 numeric digits
  - Return: true/false

validateName():
  - Input: name string
  - Rule: 2+ characters, alphabets and spaces only
  - Return: true/false
```

**Design Decisions:**
- Auto-incrementing ID prevents manual ID conflicts
- ID counter starts at 1000 (increments to 1001 for first customer)
- Static counter ensures unique IDs across all instances
- Validation methods separated from constructor for flexibility

**Example Usage:**
```java
Customer john = new Customer("John Doe", "john@email.com", "9876543210");
Customer jane = new Customer("Jane Smith", "jane@email.com", "5551234567");
// john.getCustomerID() = 1001, jane.getCustomerID() = 1002
```

---

### 3. Booking.java
**Purpose:** Represents a hotel booking transaction

**Responsibility:**
- Link customer to a specific room for specific dates
- Calculate total booking cost
- Manage booking metadata (dates, price)
- Provide booking information for searches and reports

**Key Attributes:**
| Attribute | Type | Purpose |
|-----------|------|---------|
| `bookingID` | int | Auto-incrementing unique ID (starting 5001) |
| `customer` | Customer | Reference to Customer object |
| `room` | Room | Reference to Room object |
| `checkInDate` | String | Check-in date (DD/MM/YYYY format) |
| `checkOutDate` | String | Check-out date (DD/MM/YYYY format) |
| `totalPrice` | double | Total booking cost |
| `bookingIDCounter` | static int | Class-level counter for ID generation |

**Public Methods:**
- `Booking(Customer, Room, String, String)` - Constructor (auto-generates ID)
- `getBookingID()` - Get unique booking ID
- `getCustomer()`, `setCustomer(Customer)` - Access customer
- `getRoom()`, `setRoom(Room)` - Access room
- `getCheckInDate()`, `setCheckInDate(String)` - Access check-in date
- `getCheckOutDate()`, `setCheckOutDate(String)` - Access check-out date
- `getTotalPrice()`, `setTotalPrice(double)` - Access price
- `calculateTotalPrice()` - Compute total based on duration and rate
- `toString()` - Return formatted booking details

**Price Calculation:**
```
Formula: totalPrice = numberOfNights × room.getPricePerNight()

Algorithm:
1. Parse check-in date (DD/MM/YYYY)
2. Parse check-out date (DD/MM/YYYY)
3. Calculate day difference: (checkOutYear-checkInYear)*365 + (checkOutMonth-checkInMonth)*30 + (checkOutDay-checkInDay)
4. Ensure minimum 1 night
5. Multiply by room price
6. Return total
```

**Date Format:**
- All dates must be in DD/MM/YYYY format
- Example: "25/11/2025" for November 25, 2025
- Invalid dates return totalPrice of 0

**Design Decisions:**
- Booking objects maintain references to Customer and Room (composition)
- Date strings for simplicity (no Date/LocalDate for BlueJ compatibility)
- ID counter starts at 5000 to distinguish from Customer IDs
- `calculateTotalPrice()` called by HotelManager before storing booking

**Example Usage:**
```java
Room room = new Room(101, "Single", 100.0);
Customer cust = new Customer("John Doe", "john@email.com", "9876543210");
Booking booking = new Booking(cust, room, "25/11/2025", "27/11/2025");
double total = booking.calculateTotalPrice(); // 2 nights × $100 = $200.0
```

---

### 4. HotelManager.java
**Purpose:** Central manager and controller for all hotel operations

**Responsibility:**
- Manage collections of rooms, bookings, and customers
- Provide CRUD operations (Create, Read, Update, Delete)
- Implement search and filtering functionality
- Check room availability and date conflicts
- Initialize system with sample data

**Key Attributes:**
| Attribute | Type | Purpose |
|-----------|------|---------|
| `rooms` | ArrayList<Room> | Collection of all hotel rooms |
| `bookings` | ArrayList<Booking> | Collection of all bookings |
| `customers` | ArrayList<Customer> | Collection of all customers |

**Public Methods:**

**Room Management:**
- `addRoom(Room)` - Add new room to inventory
- `removeRoom(int roomNumber)` - Remove room by number
- `getAllRooms()` - Get all rooms in system

**Customer Management:**
- `addCustomer(Customer)` - Add new customer
- `getAllCustomers()` - Get all customers

**Booking Management:**
- `addBooking(Booking)` - Add booking (calculates price)
- `cancelBooking(int bookingID)` - Cancel booking by ID
- `getAllBookings()` - Get all bookings

**Search Methods:**
- `searchAvailableRooms(String roomType)` - Find available rooms of specific type
- `searchBookingsByCustomerName(String customerName)` - Case-sensitive search by name
- `searchBookingByID(int bookingID)` - Find specific booking

**Availability Methods:**
- `isRoomAvailableForDates(int roomNumber, String checkIn, String checkOut)` - Check date availability

**Private Helper Methods:**
- `initializeSampleRooms()` - Pre-populate with test data
- `datesOverlap(String, String, String, String)` - Detect date conflicts
- `dateToMillis(String)` - Convert DD/MM/YYYY to milliseconds

**Initialization:**
```
Sample Rooms (Pre-populated):
- Room 101: Single, $100/night
- Room 102: Single, $100/night
- Room 201: Double, $150/night
- Room 202: Double, $150/night
- Room 301: Suite, $250/night
```

**Date Overlap Detection:**
```
Logic: Two date ranges overlap if:
  newCheckInTime < existingCheckOutTime AND newCheckOutTime > existingCheckInTime

This allows:
✓ Same room booked for: 25-26/11 and 26-27/11 (back-to-back, non-overlapping)
✗ Same room booked for: 25-26/11 and 25-27/11 (overlapping)
```

**Design Decisions:**
- Singleton-like pattern (one manager per application)
- ArrayList for flexibility (no size limit)
- Date overlap logic enables multi-booking same room
- Case-sensitive search per requirements
- Pre-populated sample data for immediate testing

**Example Usage:**
```java
HotelManager hotel = new HotelManager();

// Access sample rooms
ArrayList<Room> allRooms = hotel.getAllRooms(); // Returns 5 rooms

// Search for available single rooms
ArrayList<Room> singles = hotel.searchAvailableRooms("Single"); // Returns 2

// Create and add booking
Customer john = new Customer("John Doe", "john@email.com", "9876543210");
Room room101 = allRooms.get(0);
Booking booking = new Booking(john, room101, "25/11/2025", "27/11/2025");
hotel.addBooking(booking);
hotel.addCustomer(john);

// Search bookings
ArrayList<Booking> johnBookings = hotel.searchBookingsByCustomerName("John Doe");

// Check availability
boolean available = hotel.isRoomAvailableForDates(101, "28/11/2025", "30/11/2025"); // true
```

---

## Data Flow Diagram

```
┌──────────────┐
│   Customer   │
│ (stores info)│
└──────┬───────┘
       │
       │ linked to
       ↓
┌──────────────┐         ┌──────────────┐
│   Booking    │         │     Room     │
│(transaction) │ ◄──────→│  (inventory) │
└──────┬───────┘         └──────────────┘
       │
       │ managed by
       ↓
┌─────────────────────────┐
│   HotelManager          │
│ (orchestrator, storage) │
└─────────────────────────┘
```

---

## Key Design Patterns

### 1. Auto-Incrementing ID Pattern
```java
private static int customerIDCounter = 1000;

public Customer(...) {
    this.customerID = ++customerIDCounter;
    ...
}
```
**Purpose:** Ensure unique IDs without manual assignment

### 2. Composition Pattern
```java
public class Booking {
    private Customer customer;  // HAS-A relationship
    private Room room;          // HAS-A relationship
}
```
**Purpose:** Link entities without inheritance

### 3. Collection Management Pattern
```java
public class HotelManager {
    private ArrayList<Room> rooms;
    private ArrayList<Booking> bookings;
    private ArrayList<Customer> customers;
}
```
**Purpose:** Centralized data storage with query capabilities

### 4. Validation Pattern
```java
public boolean validateEmail() {
    return email != null && email.contains("@");
}
```
**Purpose:** Separate validation logic from constructors

---

## Phase 2 Summary

**Model Layer Complete:**
- ✓ Room entity with full lifecycle management
- ✓ Customer entity with auto-ID and validation
- ✓ Booking entity with price calculation and associations
- ✓ HotelManager as central orchestrator

**Files:**
- Room.java (104 lines)
- Customer.java (152 lines)
- Booking.java (173 lines)
- HotelManager.java (240 lines)

**Status:** Ready for Phase 3 & 4 (Business Logic & GUI)

---

---

## View Layer Documentation (Phase 4)

### MainFrame.java
**Purpose:** Main application window and GUI orchestrator

**Responsibility:**
- Initialize application window
- Create and manage menu bar
- Use CardLayout to switch between panels
- Handle menu events and user dialogs
- Coordinate panel updates

**Key Attributes:**
| Attribute | Type | Purpose |
|-----------|------|---------|
| `hotelManager` | HotelManager | Reference to data manager |
| `mainPanel` | JPanel | Container with CardLayout |
| `cardLayout` | CardLayout | Layout manager for panel switching |
| `bookingPanel` | BookingPanel | Panel for booking creation |
| `viewBookingsPanel` | ViewBookingsPanel | Panel for booking management |
| `roomSelectionPanel` | RoomSelectionPanel | Panel for room browsing |

**Menu Structure:**
```
Menu Bar
├── File
│   └── Exit
├── Bookings
│   ├── New Booking (shows BookingPanel)
│   ├── View Bookings (shows ViewBookingsPanel)
│   └── Cancel Booking (dialog)
├── Rooms
│   ├── View Rooms (shows RoomSelectionPanel)
│   └── Room Availability (search dialog)
└── Help
    └── About (info dialog)
```

**Panel Switching:**
- Uses CardLayout with string identifiers
- `cardLayout.show(mainPanel, panelName)` switches panels
- Replaces panel content (not creates new windows)

**Design Decisions:**
- CardLayout for smooth panel switching
- Single JFrame for entire application
- Menu-driven navigation
- Dialog boxes for simple operations (cancel, search)

---

### BookingPanel.java
**Purpose:** Form for creating new hotel bookings

**Responsibility:**
- Collect customer information
- Collect booking details
- Validate all inputs
- Calculate booking price in real-time
- Process booking submission
- Display confirmation

**Key Attributes:**
| Attribute | Type | Purpose |
|-----------|------|---------|
| `nameField` | JTextField | Customer name input |
| `emailField` | JTextField | Customer email input |
| `phoneField` | JTextField | Customer phone input |
| `roomTypeCombo` | JComboBox<String> | Room type selector |
| `roomCombo` | JComboBox<Room> | Available room selector |
| `checkInField` | JTextField | Check-in date input |
| `checkOutField` | JTextField | Check-out date input |
| `priceLabel` | JLabel | Real-time price display |
| `confirmButton` | JButton | Submit booking |
| `clearButton` | JButton | Reset form |

**Layout:** GridBagLayout for flexible positioning

**Validation Flow:**
```
1. Required fields check
2. Customer object creation
3. validateName() - Alphabets only, 2+ chars
4. validateEmail() - Must contain @
5. validatePhoneNumber() - 10 digits, numeric
6. Date format check (DD/MM/YYYY)
7. Check-out after check-in
8. Room availability check
9. Booking creation and storage
10. Confirmation dialog
```

**Event Listeners:**
- Document listeners on date fields for real-time price update
- Action listener on room type combo to update room options
- Action listeners on buttons

**Color Scheme:**
- Blue (Confirm button): #3498DB
- Gray (Clear button): #95A5A6
- Green (Price display): #27AE60
- White backgrounds for inputs

---

### ViewBookingsPanel.java
**Purpose:** Display and search hotel bookings

**Responsibility:**
- Display all bookings in tabular format
- Provide search functionality
- Allow filtering by customer name or booking ID
- Update booking list dynamically
- Provide cancellation dialog integration

**Key Attributes:**
| Attribute | Type | Purpose |
|-----------|------|---------|
| `bookingsTable` | JTable | Bookings display table |
| `tableModel` | DefaultTableModel | Data model for table |
| `searchField` | JTextField | Search input field |
| `searchTypeCombo` | JComboBox<String> | Search criteria selector |
| `searchButton` | JButton | Trigger search |
| `refreshButton` | JButton | Show all bookings |

**Table Columns:**
- Booking ID (int)
- Customer Name (String)
- Room Number (int)
- Room Type (String)
- Check-In (DD/MM/YYYY)
- Check-Out (DD/MM/YYYY)
- Total Price ($)

**Search Methods:**
- **Customer Name:** Case-sensitive exact match
- **Booking ID:** Numeric ID search

**Features:**
- Read-only table (no editing)
- Professional header styling (dark background, white text)
- Scrollable for many bookings
- Dynamic row updates

**Design Decisions:**
- DefaultTableModel for simplicity
- Read-only table prevents accidental data modification
- Case-sensitive search per requirements
- Integrated with HotelManager search methods

---

### RoomSelectionPanel.java
**Purpose:** Browse and filter available hotel rooms

**Responsibility:**
- Display all rooms in card-based layout
- Filter rooms by type
- Show room availability status
- Display room prices
- Update display dynamically

**Key Attributes:**
| Attribute | Type | Purpose |
|-----------|------|---------|
| `roomsDisplayPanel` | JPanel | Container for room cards |
| `roomTypeFilterCombo` | JComboBox<String> | Filter options |
| `filterButton` | JButton | Apply filter |
| `showAllButton` | JButton | Reset filter |

**Room Card Display:**
- Room number (large, bold)
- Room type
- Price per night
- Availability status (color-coded)

**Layout:** GridLayout with 3 columns, scrollable

**Color Coding:**
- Green: Available room
- Red: Unavailable room

**Design Decisions:**
- Card-based UI for visual appeal
- 3-column grid for balanced layout
- JScrollPane for many rooms
- Color-coded availability for quick identification

---

## Integration Layer

### GUI-Model Integration

**MainFrame → Panels:**
```
MainFrame creates instances and passes HotelManager reference
↓
BookingPanel, ViewBookingsPanel, RoomSelectionPanel all receive hotelManager
↓
Panels call methods on hotelManager for data operations
```

**Data Flow:**
```
User Input → Validation → Model Method Call → Data Storage → Display Update
```

**Example - Booking Creation:**
```
User fills BookingPanel form
  ↓
User clicks Confirm
  ↓
BookingPanel.confirmBooking() validates inputs
  ↓
Creates Customer and Booking objects
  ↓
Calls hotelManager.addBooking()
  ↓
Booking stored in ArrayList
  ↓
Confirmation dialog shown to user
  ↓
Form cleared, ready for next booking
```

---

## Updated System Architecture

```
┌─────────────────────────────────────────────────────────┐
│                  PRESENTATION LAYER (Phase 4)            │
│                                                           │
│  MainFrame (JFrame)                                       │
│    ├── Menu Bar (File, Bookings, Rooms, Help)            │
│    └── CardLayout Panel Manager                          │
│        ├── BookingPanel (Create bookings)                │
│        ├── ViewBookingsPanel (Browse/search bookings)    │
│        └── RoomSelectionPanel (Browse rooms)             │
├─────────────────────────────────────────────────────────┤
│              BUSINESS LOGIC LAYER (Phase 3)              │
│                                                           │
│  Validation Logic                                         │
│  - Input validation (name, email, phone, dates)          │
│  - Date overlap detection                                │
│  - Room availability checking                            │
│  - Price calculation                                      │
├─────────────────────────────────────────────────────────┤
│              DATA MANAGEMENT LAYER (Phase 2)             │
│                                                           │
│  HotelManager (Orchestrator)                             │
│    ├── ArrayList<Room> - Room inventory                  │
│    ├── ArrayList<Booking> - Booking records              │
│    └── ArrayList<Customer> - Customer data               │
│                                                           │
│  Model Classes                                            │
│  ├── Room - Room entity                                  │
│  ├── Customer - Customer entity                          │
│  ├── Booking - Booking transaction                       │
│  └── HotelManager - Data orchestrator                    │
├─────────────────────────────────────────────────────────┤
│           DATA STORAGE LAYER (In-Memory)                 │
│                                                           │
│  ArrayList Collections (No database yet)                 │
│  - Rooms persist during session                          │
│  - Bookings persist during session                       │
│  - Customers persist during session                      │
└─────────────────────────────────────────────────────────┘
```

---

## Design Patterns Summary

### 1. MVC Pattern
- **Model:** Room, Customer, Booking, HotelManager
- **View:** MainFrame, BookingPanel, ViewBookingsPanel, RoomSelectionPanel
- **Controller:** Event handlers in panels trigger model operations

### 2. CardLayout Pattern
- Switches panels without creating new windows
- Single main window with replaceable content
- Smooth navigation between features

### 3. Observer Pattern (Implicit)
- UI components listen to user input
- Document listeners for real-time updates
- Action listeners for button clicks

### 4. Validation Pattern
- Input validation before model operations
- Business logic validation in model classes
- User feedback via dialogs

### 5. Composition Pattern
- Booking HAS-A Customer relationship
- Booking HAS-A Room relationship
- Panels HAS-A HotelManager reference

---

## File Organization

```
HotelBookingSystem/
├── Model Layer
│   ├── Room.java (104 lines)
│   ├── Customer.java (152 lines)
│   ├── Booking.java (173 lines)
│   └── HotelManager.java (240 lines)
│
├── View Layer
│   ├── MainFrame.java (310 lines)
│   ├── BookingPanel.java (380 lines)
│   ├── ViewBookingsPanel.java (260 lines)
│   └── RoomSelectionPanel.java (220 lines)
│
└── Documentation
    ├── progress.md
    └── architecture.md
```

---

## Complete Phase Roadmap

| Phase | Status | Files | Lines | Focus |
|-------|--------|-------|-------|-------|
| Phase 1 | ✓ | Project setup | - | Folder structure |
| Phase 2 | ✓ | 4 classes | 669 | Model entities |
| Phase 3 | ✓ | Business logic | - | Validation, availability |
| Phase 4 | ✓ | 4 GUI classes | 1,170 | User interface |
| **Total** | ✓ | **8 files** | **1,839** | **Complete App** |

---

## Future Enhancements

### Phase 5: Advanced Features
- Database integration (MySQL/SQLite)
- User authentication (login/signup)
- Room images and descriptions
- Advanced reporting and analytics

### Phase 6: Payment & Notifications
- Payment processing module
- Email confirmation system
- SMS notifications

### Phase 7: Mobile & Web
- REST API for backend
- Mobile application
- Web interface

## Future Enhancements
