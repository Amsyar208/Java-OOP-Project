# Hotel Management System - App Design Document

## 1. Project Overview

### 1.1 Project Name

**Hotel Management System (HMS)**

### 1.2 Purpose

A comprehensive hotel management application designed to streamline hotel operations including room bookings, customer management, billing, and staff administration.

### 1.3 Target Users

- Hotel Administrators
- Receptionists
- Hotel Staff
- Customers/Guests

### 1.4 Technology Stack

- **Programming Language:** Java
- **GUI Framework:** Java Swing / JavaFX
- **Database:** MySQL / SQLite
- **Design Pattern:** MVC (Model-View-Controller)

---

## 2. System Architecture

### 2.1 Architecture Pattern

**Model-View-Controller (MVC)**

```
┌─────────────┐
│    View     │ ← User Interface Layer
└──────┬──────┘
       │
┌──────▼──────┐
│ Controller  │ ← Business Logic Layer
└──────┬──────┘
       │
┌──────▼──────┐
│    Model    │ ← Data Layer
└─────────────┘
```

---

## 3. Core Modules

### 3.1 User Management Module

- **Admin Login/Logout**
- **Staff Management**
- **Role-based Access Control**

### 3.2 Room Management Module

- **Add/Update/Delete Rooms**
- **Room Categories** (Single, Double, Suite, Deluxe)
- **Room Status** (Available, Occupied, Maintenance)
- **Room Pricing**

### 3.3 Booking/Reservation Module

- **New Booking**
- **Check-in/Check-out**
- **Booking History**
- **Search Bookings**

### 3.4 Customer Management Module

- **Customer Registration**
- **Customer Profile Management**
- **Customer History**

### 3.5 Billing Module

- **Generate Bills**
- **Payment Processing**
- **Invoice Generation**
- **Payment History**

### 3.6 Reports Module

- **Occupancy Reports**
- **Revenue Reports**
- **Customer Reports**

---

## 4. Class Diagram Structure

### 4.1 Core Classes

#### **User Classes**

java

Copy code

`- User (Abstract)   - Admin   - Staff   - Customer`

#### **Room Classes**

java

Copy code

`- Room - RoomType (Enum) - RoomStatus (Enum)`

#### **Booking Classes**

java

Copy code

`- Booking - Reservation - BookingStatus (Enum)`

#### **Payment Classes**

java

Copy code

`- Payment - Bill - PaymentMethod (Enum)`

---

## 5. Key Classes and Attributes

### 5.1 User Class

java

Copy code

`public abstract class User {     - userId: String     - name: String     - email: String     - phone: String     - password: String     - role: String          + login()     + logout()     + updateProfile() }`

### 5.2 Room Class

java

Copy code

`public class Room {     - roomId: String     - roomNumber: int     - roomType: RoomType     - price: double     - status: RoomStatus     - capacity: int     - amenities: List<String>          + checkAvailability()     + updateStatus()     + getRoomDetails() }`

### 5.3 Booking Class

java

Copy code

`public class Booking {     - bookingId: String     - customer: Customer     - room: Room     - checkInDate: Date     - checkOutDate: Date     - numberOfGuests: int     - status: BookingStatus     - totalAmount: double          + createBooking()     + cancelBooking()     + updateBooking()     + calculateTotal() }`

### 5.4 Payment Class

java

Copy code

`public class Payment {     - paymentId: String     - booking: Booking     - amount: double     - paymentDate: Date     - paymentMethod: PaymentMethod     - status: String          + processPayment()     + generateReceipt()     + refundPayment() }`

---

## 6. Database Schema

### 6.1 Tables

#### **users**

|Column|Type|Constraints|
|---|---|---|
|user_id|VARCHAR(50)|PRIMARY KEY|
|name|VARCHAR(100)|NOT NULL|
|email|VARCHAR(100)|UNIQUE|
|phone|VARCHAR(15)|NOT NULL|
|password|VARCHAR(255)|NOT NULL|
|role|VARCHAR(20)|NOT NULL|

#### **rooms**

|Column|Type|Constraints|
|---|---|---|
|room_id|VARCHAR(50)|PRIMARY KEY|
|room_number|INT|UNIQUE, NOT NULL|
|room_type|VARCHAR(50)|NOT NULL|
|price|DECIMAL(10,2)|NOT NULL|
|status|VARCHAR(20)|NOT NULL|
|capacity|INT|NOT NULL|

#### **bookings**

|Column|Type|Constraints|
|---|---|---|
|booking_id|VARCHAR(50)|PRIMARY KEY|
|customer_id|VARCHAR(50)|FOREIGN KEY|
|room_id|VARCHAR(50)|FOREIGN KEY|
|check_in_date|DATE|NOT NULL|
|check_out_date|DATE|NOT NULL|
|num_guests|INT|NOT NULL|
|total_amount|DECIMAL(10,2)|NOT NULL|
|status|VARCHAR(20)|NOT NULL|

#### **payments**

|Column|Type|Constraints|
|---|---|---|
|payment_id|VARCHAR(50)|PRIMARY KEY|
|booking_id|VARCHAR(50)|FOREIGN KEY|
|amount|DECIMAL(10,2)|NOT NULL|
|payment_date|DATETIME|NOT NULL|
|payment_method|VARCHAR(50)|NOT NULL|
|status|VARCHAR(20)|NOT NULL|

---

## 7. Features and Functionalities

### 7.1 Admin Features

- ✅ Manage rooms (Add, Update, Delete)
- ✅ Manage staff accounts
- ✅ View all bookings
- ✅ Generate reports
- ✅ System configuration

### 7.2 Staff Features

- ✅ Create new bookings
- ✅ Check-in/Check-out guests
- ✅ Search available rooms
- ✅ Process payments
- ✅ View customer information

### 7.3 Customer Features

- ✅ View available rooms
- ✅ Make reservations
- ✅ View booking history
- ✅ Update profile

---

## 8. User Interface Design

### 8.1 Main Screens

#### **Login Screen**

- Username/Email field
- Password field
- Login button
- Role selection (Admin/Staff)

#### **Dashboard**

- Summary statistics
- Quick actions menu
- Recent bookings
- Room availability status

#### **Room Management Screen**

- Room list table
- Add/Edit/Delete buttons
- Search and filter options
- Room details panel

#### **Booking Screen**

- Customer selection
- Room selection
- Date picker (Check-in/Check-out)
- Guest count
- Price calculation
- Confirm booking button

#### **Billing Screen**

- Booking details
- Itemized charges
- Total amount
- Payment method selection
- Generate invoice button

---

## 9. Design Patterns Used

### 9.1 Singleton Pattern

- **DatabaseConnection:** Ensures single database connection instance

### 9.2 Factory Pattern

- **UserFactory:** Creates different types of users (Admin, Staff, Customer)
- **RoomFactory:** Creates different room types

### 9.3 Observer Pattern

- **BookingObserver:** Notifies when booking status changes

### 9.4 Strategy Pattern

- **PaymentStrategy:** Different payment methods (Cash, Card, Online)

---

## 10. Security Features

- 🔒 Password encryption (SHA-256/BCrypt)
- 🔒 Role-based access control
- 🔒 Session management
- 🔒 Input validation
- 🔒 SQL injection prevention

---

## 11. Error Handling

- Exception handling for database operations
- Input validation with user-friendly error messages
- Logging system for debugging
- Transaction rollback on failures

---

## 12. Future Enhancements

- 📱 Mobile application
- 🌐 Web-based interface
- 📧 Email notifications
- 💳 Online payment gateway integration
- 📊 Advanced analytics dashboard
- 🗓️ Calendar view for bookings
- ⭐ Customer review system
- 🍽️ Restaurant/Room service integration

---

## 13. Project Timeline

|Phase|Duration|Tasks|
|---|---|---|
|**Phase 1: Planning**|1 week|Requirements gathering, design|
|**Phase 2: Database**|1 week|Schema design, implementation|
|**Phase 3: Core Logic**|2 weeks|Model classes, business logic|
|**Phase 4: UI Development**|2 weeks|GUI implementation|
|**Phase 5: Integration**|1 week|Connect UI with backend|
|**Phase 6: Testing**|1 week|Unit testing, bug fixes|
|**Phase 7: Documentation**|1 week|User manual, code documentation|

---

## 14. Testing Strategy

### 14.1 Unit Testing

- Test individual classes and methods
- JUnit framework

### 14.2 Integration Testing

- Test module interactions
- Database connectivity

### 14.3 User Acceptance Testing

- Test with sample users
- Gather feedback

---

## 15. Deliverables

1. ✅ Source code (Java files)
2. ✅ Database schema and scripts
3. ✅ User manual
4. ✅ Technical documentation
5. ✅ UML diagrams (Class, Sequence, Use Case)
6. ✅ Presentation slides
7. ✅ Demo video

---

## 16. Team Roles (If applicable)

- **Project Manager:** Overall coordination
- **Backend Developer:** Core logic and database
- **Frontend Developer:** GUI design
- **Tester:** Quality assurance
- **Documentation:** Technical writing

---

## 17. References and Resources

- Java Documentation: [https://docs.oracle.com/javase/](https://docs.oracle.com/javase/)
- MySQL Documentation: [https://dev.mysql.com/doc/](https://dev.mysql.com/doc/)
- Design Patterns: Gang of Four (GoF)
- OOP Principles: SOLID

---

**Document Version:** 1.0  
**Last Updated:** 2025-11-24  
**Prepared By:** Development Team

---

_This document serves as a comprehensive guide for developing the Hotel Management System as an Object-Oriented Programming project._