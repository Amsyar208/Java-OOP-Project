# Hotel Booking System: Implementation Plan

## Phase 1: Project Setup

### 1. Create New BlueJ Project
- Open BlueJ and start a new project folder named `HotelBookingSystem`.

**Test:** Confirm a new project folder appears with no errors; you see the BlueJ class diagram workspace.

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
- In HotelManager, implement logic to check if a room is available (by type and date).

**Test:** Try to book a room that is available; booking should succeed. Try booking the same room for the same date; booking should fail.

---

### 7. Calculate Total Booking Price
- In Booking, implement a method to calculate total price based on price per night and number of nights.

**Test:** Create a booking for a specific number of nights and verify that totalPrice computes as expected.

---

## Phase 4: Basic GUI

### 8. Create MainFrame Class (JFrame)
- Create a MainFrame class that extends JFrame.
- Set up a basic window with a title.

**Test:** Run MainFrame. Window appears with the correct title and no errors.

---

### 9. Add BookingPanel (JPanel)
- Create BookingPanel extending JPanel with labeled text fields for customer and booking details, and Confirm/Clear buttons.
- Integrate BookingPanel into MainFrame.

**Test:** Launch the GUI and see all labeled fields and both buttons. Clicking Clear resets all fields.

---

### 10. Add List View for Bookings
- Add a table (JTable inside JScrollPane) to show all current bookings.
- Integrate this view in MainFrame/JPanel.

**Test:** Run app and add test data. Table displays bookings with correct values for each column.

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
- Validate that all required fields are filled, email contains "@" and ".", phone is numeric, dates make sense (e.g., check-out after check-in).

**Test:** Try submitting invalid data in fields. Error dialogs should appear, and bookings should not be made.

---

### 14. Simple Error Handling
- Show dialog messages if errors/invalid actions occur (e.g., no rooms available).

**Test:** Try booking a full room or entering invalid input. System displays correct error message.

---

## Phase 6: Review and Basic Testing

### 15. Manual Testing of Flows
- Test creating, viewing, and resetting bookings for several different cases and data values.
- Test user interaction with UI controls.

**Test:** Confirm that all major workflows (adding, viewing bookings; form reset; filtering; input errors) behave as expected without crashes.

---
