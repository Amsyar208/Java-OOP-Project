# Hotel Booking System: Implementation Plan

## Phase 1: Project Setup

### 1. Create New BlueJ Project
- Open BlueJ and start a new project folder named `HotelBookingSystem`.
- Pre-populate HotelManager with sample test data (e.g., 5 rooms of different types).

**Test:** Confirm a new project folder appears with no errors; you see the BlueJ class diagram workspace. Sample rooms are initialized.

---

## Phase 2: Model Classes

### 2. Implement Room Class
- Create a new class `Room`.
- Add fields: roomNumber (int), roomType (String), pricePerNight (double), isAvailable (boolean).
- Add a constructor and appropriate getter/setter methods.

**Test:** Create a Room object in BlueJ's object bench. Check that fields store and return expected values.

---

### 3. Implement Customer Class
- Create a new class `Customer`.
- Add fields: customerID (int), name (String), email (String), phoneNumber (String).
- Add a constructor and getter/setter methods.

**Test:** Instantiate a Customer object. Verify you can set and get each property correctly.

---

### 4. Implement Booking Class
- Create a new class `Booking`.
- Add fields: bookingID (int), customer (Customer), room (Room), checkInDate (String), checkOutDate (String), totalPrice (double).
- Add constructor and methods to set/get all fields.

**Test:** In BlueJ, link Customer and Room objects by creating a Booking object with them as parameters. Verify associations work.

---

### 5. Implement HotelManager Class
- Create a new class `HotelManager`.
- Add `ArrayList<Room> rooms`, `ArrayList<Booking> bookings`, `ArrayList<Customer> customers`.
- Add methods: addRoom(Room), addCustomer(Customer), addBooking(Booking), searchAvailableRooms(String roomType).

**Test:** Add several Room, Customer, and Booking objects. Confirm they appear in the correct lists using the Inspector and list size methods.

---

## Phase 3: Core Business Logic

### 6. Check Room Availability
- In HotelManager, implement logic to check if a room is available by type and date range.
- Same room can be booked by multiple customers for non-overlapping date ranges.
- Check if requested dates overlap with existing bookings for that room.

**Test:** Try to book a room for available dates; booking should succeed. Try booking the same room for overlapping dates; booking should fail. Book same room for non-overlapping dates; both bookings should succeed.

---

### 7. Calculate Total Booking Price
- In Booking, implement a method to calculate total price based on price per night and number of nights.

**Test:** Create a booking for a specific number of nights and verify that totalPrice computes as expected.

---

## Phase 4: Basic GUI

### 8. Create MainFrame Class (JFrame)
- Create a MainFrame class that extends JFrame.
- Set up a basic window with a title, menu bar (File, Bookings, Rooms, Help).
- Implement menu navigation logic on MainFrame to replace panel content using CardLayout or panel swapping.
- Set up content panel area where BookingPanel, ViewBookingsPanel, and RoomSelectionPanel will be displayed.

**Test:** Run MainFrame. Window appears with menu bar. Clicking menu items correctly switches panel content without errors.

---

### 9. Add BookingPanel (JPanel)
- Create BookingPanel extending JPanel with labeled text fields for customer (name, email, phone) and booking details (room type dropdown, check-in/check-out dates in DD/MM/YYYY format).
- Add Confirm and Clear buttons.
- Apply color scheme: Primary #2C3E50, Secondary #3498DB, Background #ECF0F1.
- Integrate BookingPanel into MainFrame.

**Test:** Launch the GUI and see all labeled fields and both buttons with applied colors. Clicking Clear resets all fields.

---

### 10. Add ViewBookingsPanel (JPanel) with List View
- Create ViewBookingsPanel extending JPanel with a search field and search button.
- Add a table (JTable inside JScrollPane) to show all current bookings (columns: ID, Name, Room, Check-In, Check-Out, Price).
- Implement case-sensitive search functionality to filter by customer name or booking ID.
- Apply color scheme to match BookingPanel.
- Integrate ViewBookingsPanel into MainFrame.

**Test:** Run app and add test data. Table displays bookings with correct values. Search with exact case-sensitive query returns matching bookings.

---

## Phase 5: User Interaction and Integration

### 11. Connect Booking Form to Model
- On Confirm, create new Customer and Booking objects; update HotelManager's lists.

**Test:** Fill the form, click Confirm, and verify new booking data appears in the bookings table.

---

### 12. Implement Room Filtering
- Add dropdown for room type in BookingPanel.
- Filter displayed rooms according to selection.

**Test:** Select a room type and verify only matching rooms are available for booking.

---

### 13. Input Validation
- Validate that all required fields are filled.
- Email: must contain "@" symbol.
- Phone: must be numeric only, 10 digits.
- Name: minimum 2 characters, alphabets and spaces only.
- Dates: must be in DD/MM/YYYY format, check-out date must be after check-in date.
- Check-in date must not be in the past.

**Test:** Try submitting invalid data in fields. Error dialogs should appear with specific error messages, and bookings should not be made.

---

### 14. Simple Error Handling
- Show dialog messages if errors/invalid actions occur (e.g., no rooms available).

**Test:** Try booking a full room or entering invalid input. System displays correct error message.

---

## Phase 5.5: Implement Cancel Booking Feature

### 14.5. Add Cancel Booking Functionality
- In ViewBookingsPanel, add a "Cancel Selected" button to cancel selected booking from table.
- Implement method in HotelManager to remove booking by bookingID.
- Add confirmation dialog before canceling.

**Test:** Select a booking and click Cancel Selected. Confirm dialog appears. After confirmation, booking is removed from table and list.

---

## Phase 6: Review and Basic Testing

### 15. Manual Testing of Flows
- Test creating, viewing, and resetting bookings for several different cases and data values.
- Test user interaction with UI controls.
- Test menu navigation and panel switching.
- Test overlapping and non-overlapping room bookings.
- Test case-sensitive search functionality.

**Test:** Confirm that all major workflows (adding, viewing bookings; form reset; filtering; input errors; cancellation; menu navigation) behave as expected without crashes.

---
