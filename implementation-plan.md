# Hotel Management System - Implementation Plan

## Phase 1: Environment Setup

### Step 1.1: Install Development Tools
Install Java Development Kit (JDK) 17 or 21 LTS from Adoptium or Oracle's official website. Ensure the installation includes setting JAVA_HOME environment variable.

**Test:** Open terminal/command prompt and run `java -version`. Verify output shows Java 17 or 21.

### Step 1.2: Install IntelliJ IDEA
Download and install IntelliJ IDEA Community Edition from JetBrains website. Launch the IDE to confirm successful installation.

**Test:** Open IntelliJ IDEA. Check that the welcome screen displays and you can access "New Project" option.

### Step 1.3: Verify Maven Installation
Maven comes bundled with IntelliJ IDEA. Verify it's accessible through the IDE's terminal.

**Test:** In IntelliJ's terminal, run `mvn -version`. Confirm Maven version displays correctly.

---

## Phase 2: Project Initialization

### Step 2.1: Create Maven Project
In IntelliJ, select File → New → Project. Choose Maven as project type. Set GroupId as "com.hotel" and ArtifactId as "hotel-management-system". Select JDK 17 or 21.

**Test:** Project creates successfully with a default folder structure visible in the Project Explorer pane. A pom.xml file exists at project root.

### Step 2.2: Configure pom.xml
Open pom.xml file. Add properties section for compiler source/target version 17. Set project version to 1.0.0. Add properties for javafx.version as 21.

**Test:** Right-click pom.xml and select "Maven → Reload Project". Verify no error messages appear in the Maven console.

### Step 2.3: Add JavaFX Dependencies
In pom.xml dependencies section, add javafx-controls and javafx-fxml dependencies with version ${javafx.version}.

**Test:** Reload Maven project. Check that "External Libraries" in Project Explorer now includes JavaFX libraries.

### Step 2.4: Add SQLite JDBC Driver
Add org.xerial sqlite-jdbc dependency with version 3.44.1.0 to pom.xml.

**Test:** Reload Maven. Verify sqlite-jdbc appears under External Libraries with correct version number.

### Step 2.5: Add Security Dependency
Add org.mindrot jbcrypt dependency version 0.4 for password hashing.

**Test:** Reload Maven. Check jbcrypt-0.4.jar appears in External Libraries.

### Step 2.6: Add Logging Dependencies
Add slf4j-api version 2.0.9 and logback-classic version 1.4.11 dependencies.

**Test:** Reload Maven. Verify both logging libraries appear under External Libraries.

### Step 2.7: Add JUnit 5 Dependencies
Add junit-jupiter-api and junit-jupiter-engine dependencies with version 5.10.1, both with test scope.

**Test:** Reload Maven. Confirm JUnit libraries appear under External Libraries with "(test)" scope indicator.

### Step 2.8: Configure Maven Compiler Plugin
In pom.xml build section, add maven-compiler-plugin version 3.11.0.

**Test:** Run `mvn clean compile` in terminal. Build should complete with "BUILD SUCCESS" message.

### Step 2.9: Configure JavaFX Maven Plugin
Add javafx-maven-plugin version 0.0.8 with mainClass configuration pointing to com.hotel.Main.

**Test:** Verify pom.xml has no XML syntax errors. Maven reload completes without issues.

---

## Phase 3: Project Structure Setup

### Step 3.1: Create Package Structure
Under src/main/java, create package hierarchy: com.hotel with subpackages: model, controller, view, dao, and util.

**Test:** Verify five package folders appear in Project Explorer under com.hotel with correct names.

### Step 3.2: Create Resources Folders
Under src/main/resources, create three folders: fxml, css, and images.

**Test:** Navigate to src/main/resources in Project Explorer. Confirm all three folders exist.

### Step 3.3: Create Database Folder
At project root level (same level as src), create a folder named "database".

**Test:** The database folder appears in Project Explorer at the same level as src and pom.xml.

### Step 3.4: Create Test Package Structure
Under src/test/java, create com.hotel.test package.

**Test:** Test package structure mirrors main package structure. Package appears in Project Explorer under test/java.

---

## Phase 4: Database Foundation

### Step 4.1: Create DatabaseConnection Utility Class
In com.hotel.util package, create a new Java class named DatabaseConnection. Mark class as final and add private constructor.

**Test:** Class file appears under util package. Class compiles without errors when you build project.

### Step 4.2: Add Database URL Constant
In DatabaseConnection class, add a private static final String constant URL with value "jdbc:sqlite:database/hotel_management.db".

**Test:** No compilation errors. Constant appears in code with correct visibility modifiers.

### Step 4.3: Add Connection Instance Variable
Add a private static Connection variable named connection, initialized to null.

**Test:** Code compiles. Variable declaration follows Singleton pattern with static modifier.

### Step 4.4: Implement getConnection Method
Create public static method getConnection() that returns Connection. Method should check if connection is null or closed, then create new connection using DriverManager.getConnection(URL). Wrap in try-catch for SQLException.

**Test:** Method compiles without errors. Return type matches Connection interface. Try-catch block handles SQLException.

### Step 4.5: Add closeConnection Method
Create public static void method closeConnection() that checks if connection is not null and not closed, then calls connection.close() within try-catch block.

**Test:** Method compiles. Can be called without parameters. SQLException is properly caught.

### Step 4.6: Create Initial Database File
Run a simple test by creating a Main class temporarily with a main method that calls DatabaseConnection.getConnection(). Run this main method.

**Test:** After running, check database folder. A file named hotel_management.db should be created with non-zero size.

---

## Phase 5: Database Schema Creation

### Step 5.1: Create DatabaseInitializer Class
In com.hotel.util package, create class DatabaseInitializer with method initializeDatabase().

**Test:** Class appears under util package and compiles without errors.

### Step 5.2: Create Users Table SQL
In initializeDatabase method, create String variable with SQL CREATE TABLE statement for users table. Include columns: user_id VARCHAR(50) PRIMARY KEY, name VARCHAR(100) NOT NULL, email VARCHAR(100) UNIQUE, phone VARCHAR(15) NOT NULL, password VARCHAR(255) NOT NULL, role VARCHAR(20) NOT NULL.

**Test:** SQL string is syntactically correct. No compilation errors in Java code.

### Step 5.3: Execute Users Table Creation
Use DatabaseConnection.getConnection() to get connection. Create Statement object. Execute the users table SQL. Close statement in finally block.

**Test:** Run initializeDatabase method. Open hotel_management.db with SQLite browser tool. Verify users table exists with correct columns.

### Step 5.4: Create Rooms Table SQL
Add SQL CREATE TABLE statement for rooms table with columns: room_id VARCHAR(50) PRIMARY KEY, room_number INT UNIQUE NOT NULL, room_type VARCHAR(50) NOT NULL, price DECIMAL(10,2) NOT NULL, status VARCHAR(20) NOT NULL, capacity INT NOT NULL.

**Test:** Execute method. Check database file shows rooms table with six columns matching the schema.

### Step 5.5: Create Bookings Table SQL
Add SQL CREATE TABLE for bookings with columns: booking_id VARCHAR(50) PRIMARY KEY, customer_id VARCHAR(50), room_id VARCHAR(50), check_in_date DATE NOT NULL, check_out_date DATE NOT NULL, num_guests INT NOT NULL, total_amount DECIMAL(10,2) NOT NULL, status VARCHAR(20) NOT NULL. Include FOREIGN KEY constraints for customer_id and room_id.

**Test:** Run initialization. Verify bookings table exists with foreign key constraints visible in database schema.

### Step 5.6: Create Payments Table SQL
Add SQL CREATE TABLE for payments with columns: payment_id VARCHAR(50) PRIMARY KEY, booking_id VARCHAR(50) FOREIGN KEY, amount DECIMAL(10,2) NOT NULL, payment_date DATETIME NOT NULL, payment_method VARCHAR(50) NOT NULL, status VARCHAR(20) NOT NULL.

**Test:** Execute method. Confirm payments table exists with foreign key to bookings table.

### Step 5.7: Add Database Initialization to Main
Modify Main class to call DatabaseInitializer.initializeDatabase() at application startup before any other operations.

**Test:** Run application. All four tables (users, rooms, bookings, payments) exist in database file with correct structure.

---

## Phase 6: Core Model Classes

### Step 6.1: Create RoomType Enum
In com.hotel.model package, create enum RoomType with values: SINGLE, DOUBLE, SUITE, DELUXE.

**Test:** Enum file compiles. Can reference RoomType.SINGLE in other classes without errors.

### Step 6.2: Create RoomStatus Enum
Create enum RoomStatus with values: AVAILABLE, OCCUPIED, MAINTENANCE.

**Test:** Enum compiles. All three values are accessible from other classes.

### Step 6.3: Create BookingStatus Enum
Create enum BookingStatus with values: CONFIRMED, CHECKED_IN, CHECKED_OUT, CANCELLED.

**Test:** Enum compiles. Can create variables of type BookingStatus in other classes.

### Step 6.4: Create PaymentMethod Enum
Create enum PaymentMethod with values: CASH, CARD, ONLINE.

**Test:** Enum compiles successfully with all three payment method options.

### Step 6.5: Create User Abstract Class
In model package, create abstract class User with private fields: userId (String), name (String), email (String), phone (String), password (String), role (String). Generate constructor with all parameters and getter/setter methods.

**Test:** Class compiles. Cannot instantiate User directly (abstract). Getters and setters are accessible.

### Step 6.6: Add User Methods
Add abstract method login() returning boolean. Add concrete method updateProfile() returning void. Add toString() method.

**Test:** Class compiles with abstract and concrete methods. Abstract method has no implementation.

### Step 6.7: Create Admin Class
Create class Admin that extends User. Implement constructor calling super(). Override login() method with implementation returning true temporarily.

**Test:** Admin class compiles. Can instantiate Admin objects. instanceof User returns true.

### Step 6.8: Create Staff Class
Create class Staff extending User. Implement required constructor and login() method.

**Test:** Staff class compiles without errors. Can create Staff instances.

### Step 6.9: Create Customer Class  
Create class Customer extending User with additional field: loyaltyPoints (int). Implement constructor and login() method.

**Test:** Customer class compiles. Has all User fields plus loyaltyPoints. Can instantiate Customer objects.

### Step 6.10: Create Room Class
Create Room class with private fields: roomId (String), roomNumber (int), roomType (RoomType), price (double), status (RoomStatus), capacity (int). Generate constructor, getters, and setters.

**Test:** Class compiles. Can create Room objects with all fields. Getters and setters work correctly.

### Step 6.11: Add Room Methods
Add method checkAvailability() returning boolean. Add updateStatus(RoomStatus newStatus) method. Add toString() method.

**Test:** Methods compile. Can call methods on Room instances without errors.

### Step 6.12: Create Booking Class
Create Booking class with fields: bookingId (String), customerId (String), roomId (String), checkInDate (LocalDate), checkOutDate (LocalDate), numGuests (int), totalAmount (double), status (BookingStatus). Generate constructor and accessors.

**Test:** Class compiles. Can create Booking objects. LocalDate fields are properly typed.

### Step 6.13: Add Booking Methods
Add calculateTotal() method returning double. Add method createBooking() returning boolean. Add method cancelBooking() returning void.

**Test:** All methods compile. Booking class has complete method signatures.

### Step 6.14: Create Payment Class
Create Payment class with fields: paymentId (String), bookingId (String), amount (double), paymentDate (LocalDateTime), paymentMethod (PaymentMethod), status (String). Generate constructor and accessors.

**Test:** Class compiles. Can instantiate Payment with LocalDateTime and PaymentMethod enum.

---

## Phase 7: Data Access Layer (DAO)

### Step 7.1: Create UserDAO Interface
In dao package, create interface UserDAO with method signatures: insertUser(User user) returning boolean, getUserById(String userId) returning User, getUserByEmail(String email) returning User, updateUser(User user) returning boolean, deleteUser(String userId) returning boolean.

**Test:** Interface compiles. All methods are abstract with proper return types.

### Step 7.2: Create UserDAOImpl Class
Create class UserDAOImpl implementing UserDAO interface. Add private Connection field initialized via DatabaseConnection.getConnection().

**Test:** Class shows error markers until all interface methods are implemented. Connection field is properly declared.

### Step 7.3: Implement insertUser Method
Implement insertUser() using PreparedStatement with SQL INSERT statement. Use parameterized query with placeholders. Hash password before storing using BCrypt. Return true on success, false on failure.

**Test:** Method compiles. PreparedStatement prevents SQL injection. SQLException is caught.

### Step 7.4: Implement getUserById Method
Implement getUserById() using PreparedStatement with SELECT query. Use ResultSet to extract data. Determine user type from role field and return appropriate subclass (Admin/Staff/Customer). Return null if not found.

**Test:** Method compiles. Returns correct User subclass based on role. Handles ResultSet properly.

### Step 7.5: Implement getUserByEmail Method
Implement getUserByEmail() similar to getUserById but query by email column. Return appropriate User subclass or null.

**Test:** Method compiles. Can retrieve users by email address. Returns null for non-existent emails.

### Step 7.6: Implement updateUser Method
Implement updateUser() using PreparedStatement with UPDATE query. Update all user fields except userId. Return boolean indicating success.

**Test:** Method compiles. Updates user information in database. Returns true/false appropriately.

### Step 7.7: Implement deleteUser Method
Implement deleteUser() using PreparedStatement with DELETE query. Use userId as WHERE condition. Return boolean indicating success.

**Test:** Method compiles. Can remove users from database. Returns false if user doesn't exist.

### Step 7.8: Create RoomDAO Interface
Create interface RoomDAO with methods: insertRoom(Room), getRoomById(String), getAllRooms() returning List<Room>, getAvailableRooms() returning List<Room>, updateRoom(Room), deleteRoom(String).

**Test:** Interface compiles with all method signatures. Return types are correct including List types.

### Step 7.9: Create RoomDAOImpl Class
Create RoomDAOImpl implementing RoomDAO. Implement all methods using PreparedStatement pattern similar to UserDAO.

**Test:** Class compiles. All interface methods are implemented. Database queries use proper SQL syntax.

---

## Phase 8: Utility Classes

### Step 8.1: Create PasswordUtil Class
In util package, create class PasswordUtil with static method hashPassword(String plainPassword) returning String. Use BCrypt.hashpw() to hash the password.

**Test:** Class compiles. Method accepts plain string and returns hashed string. BCrypt library is properly imported.

### Step 8.2: Add Password Verification Method
Add static method checkPassword(String plainPassword, String hashedPassword) returning boolean. Use BCrypt.checkpw() for verification.

**Test:** Method compiles. Returns true for matching passwords, false otherwise. Handles null inputs safely.

### Step 8.3: Create ValidationUtil Class
Create ValidationUtil class with static method isValidEmail(String email) using regex pattern to validate email format.

**Test:** Class compiles. Method returns true for valid emails, false for invalid. Handles null input.

### Step 8.4: Add Phone Validation
Add static method isValidPhone(String phone) checking phone number format (10-15 digits).

**Test:** Method compiles. Returns true for valid phone formats, false otherwise.

### Step 8.5: Add Date Validation
Add static method isValidDateRange(LocalDate start, LocalDate end) checking if start is before end and both are not in the past.

**Test:** Method compiles. Correctly validates date ranges. Returns false for invalid ranges.

### Step 8.6: Create IDGenerator Class
Create class IDGenerator with static method generateUserId(), generateRoomId(), generateBookingId(), and generatePaymentId(). Each returns unique String ID using UUID or timestamp-based approach.

**Test:** Class compiles. Each method generates unique IDs. Running multiple times produces different IDs.

---

## Phase 9: Basic UI Setup

### Step 9.1: Create Main Application Class
In com.hotel package, create Main class extending javafx.application.Application. Override start(Stage primaryStage) method with empty implementation.

**Test:** Class compiles. Extends Application properly. start() method signature matches JavaFX requirements.

### Step 9.2: Add Main Method
Add public static void main(String[] args) method. Call Application.launch(args) inside main.

**Test:** Can run Main class. JavaFX runtime initializes without errors. Application window appears briefly then closes (expected behavior with empty start method).

### Step 9.3: Create Login FXML File
In src/main/resources/fxml folder, create file login.fxml. Add basic FXML structure with AnchorPane as root, VBox container, two TextFields (username, password), and Button (login).

**Test:** FXML file is well-formed XML. Scene Builder can open it without errors (if available). No syntax errors in IDE.

### Step 9.4: Create LoginController Class
In view package, create LoginController class. Add @FXML annotated fields for TextField usernameField, PasswordField passwordField, and Button loginButton.

**Test:** Class compiles. FXML annotations are properly imported from javafx.fxml package.

### Step 9.5: Add handleLogin Method
In LoginController, create method handleLogin() with @FXML annotation. Method should print "Login clicked" to console temporarily.

**Test:** Method compiles with correct FXML annotation. Can be referenced from FXML file.

### Step 9.6: Connect FXML to Controller
In login.fxml, add fx:controller attribute to root element pointing to full class name of LoginController. Add fx:id attributes to UI components matching controller fields. Add onAction attribute to login button referencing handleLogin method.

**Test:** FXML file validates without errors. IDE shows no warnings about missing controller methods or fields.

### Step 9.7: Load Login Scene in Main
In Main class start() method, use FXMLLoader to load login.fxml. Create Scene with loaded parent. Set scene to primaryStage. Set title to "Hotel Management System - Login". Call primaryStage.show().

**Test:** Run Main class. Login window appears with username field, password field, and login button. Window title displays correctly.

### Step 9.8: Set Window Properties
In start() method, set primaryStage dimensions (width 800, height 600). Set resizable to false. Center window on screen.

**Test:** Run application. Window appears with specified dimensions. Cannot resize window. Window is centered on screen.

---

## Phase 10: Basic Login Functionality

### Step 10.1: Add Default Admin to Database
Create method in DatabaseInitializer to insert a default admin user. Use credentials: email "admin@hotel.com", password "admin123" (hashed), role "ADMIN". Execute this during database initialization.

**Test:** Check database users table after initialization. Default admin record exists with hashed password.

### Step 10.2: Implement Login Validation
In LoginController handleLogin method, get text from usernameField and passwordField. Check both are not empty. Display error message if empty.

**Test:** Run application. Click login with empty fields. Appropriate error message displays (use Alert dialog or Label).

### Step 10.3: Create AuthenticationService Class
In controller package, create AuthenticationService class with method authenticate(String email, String password) returning User. Method should call UserDAO to get user by email, then verify password using PasswordUtil.

**Test:** Class compiles. Method returns User object on successful authentication, null otherwise.

### Step 10.4: Integrate Authentication in Login
In LoginController handleLogin, create instance of AuthenticationService. Call authenticate() with entered credentials. If successful, print success message. If failed, show error alert.

**Test:** Run application. Login with admin@hotel.com and admin123. Success message displays. Login with wrong credentials shows error.

### Step 10.5: Create Dashboard FXML
In resources/fxml folder, create dashboard.fxml with BorderPane as root. Add MenuBar at top with menus: Rooms, Bookings, Customers. Add Label in center showing "Welcome to Hotel Management System".

**Test:** FXML file is valid. Can be opened in Scene Builder. Displays menu bar and welcome message when loaded.

### Step 10.6: Create DashboardController Class
In view package, create DashboardController class. Add @FXML annotated Label for welcome message. Add method setUserName(String name) to update welcome message.

**Test:** Class compiles. Controller can be referenced from FXML. Methods are accessible.

### Step 10.7: Navigate to Dashboard After Login
In LoginController handleLogin, after successful authentication, use FXMLLoader to load dashboard.fxml. Get DashboardController and call setUserName with authenticated user's name. Create new Scene and set to stage.

**Test:** Run application. Successful login transitions to dashboard screen. Welcome message shows logged-in user's name.

### Step 10.8: Add Logout Functionality
In dashboard.fxml, add menu item "Logout" under File menu. In DashboardController, add handleLogout method that loads login.fxml again.

**Test:** From dashboard, click Logout menu. Application returns to login screen.

---

## Phase 11: Basic Room Management

### Step 11.1: Create Room Management FXML
Create room-management.fxml with TableView for displaying rooms. Add columns: Room Number, Room Type, Price, Status, Capacity. Add buttons: Add Room, Edit Room, Delete Room.

**Test:** FXML is valid. TableView displays with five columns. Three buttons appear below or beside table.

### Step 11.2: Create RoomManagementController
Create RoomManagementController class. Add @FXML TableView<Room> roomTable field. Add TableColumn fields for each column. Add Button fields for action buttons.

**Test:** Class compiles. All FXML-annotated fields match elements in FXML file.

### Step 11.3: Initialize Room Table
In RoomManagementController, add initialize() method with @FXML annotation. Set cell value factories for each column mapping to Room class properties. Create ObservableList<Room> for table data.

**Test:** Method compiles. Cell value factories use correct property names from Room class.

### Step 11.4: Load Rooms from Database
In initialize() method, create RoomDAO instance. Call getAllRooms() method. Add returned rooms to ObservableList. Set list as table items.

**Test:** Run application and navigate to room management. Table initializes without errors. Displays empty table if no rooms exist.

### Step 11.5: Create Add Room Dialog
Create method showAddRoomDialog() that creates new Stage with form fields: Room Number (TextField), Room Type (ComboBox), Price (TextField), Capacity (TextField), Status (ComboBox). Add Save and Cancel buttons.

**Test:** Method compiles. Clicking Add Room button opens dialog window with all form fields. ComboBoxes populate with enum values.

### Step 11.6: Implement Add Room Logic
In Add Room dialog, when Save clicked, validate all fields. Create new Room object with generated ID. Set status to AVAILABLE. Call RoomDAO.insertRoom(). Refresh table. Close dialog.

**Test:** Fill dialog with valid data and click Save. New room appears in table. Database contains new room record. Dialog closes automatically.

### Step 11.7: Implement Edit Room
Create method handleEditRoom() triggered when Edit button clicked. Get selected room from table. Open dialog pre-filled with room data. On save, call RoomDAO.updateRoom(). Refresh table.

**Test:** Select a room, click Edit. Dialog opens with room's current data. Modify and save. Changes reflect in table and database.

### Step 11.8: Implement Delete Room
Create method handleDeleteRoom() triggered when Delete clicked. Show confirmation alert. If confirmed, get selected room and call RoomDAO.deleteRoom(). Refresh table.

**Test:** Select a room, click Delete. Confirmation dialog appears. Clicking Yes removes room from table and database.

### Step 11.9: Add Room Management to Dashboard Menu
In dashboard.fxml, add menu item "Room Management" under Rooms menu. In DashboardController, add handleRoomManagement method that loads room-management.fxml in center region of BorderPane.

**Test:** From dashboard, click Rooms → Room Management. Room management view displays in dashboard center region without opening new window.

---

## Phase 12: Application Testing & Polish

### Step 12.1: Add Sample Data
Create method in DatabaseInitializer to insert 5-10 sample rooms with various types, prices, and statuses.

**Test:** After initialization, room management table displays sample rooms. Database contains sample data.

### Step 12.2: Add Input Validation
In all dialog forms, add validation before saving. Check required fields are not empty, numeric fields contain valid numbers, prices are positive.

**Test:** Try submitting forms with invalid data. Appropriate error messages display. Cannot save invalid data.

### Step 12.3: Add Error Handling
Wrap all database operations in try-catch blocks. Display user-friendly error messages when database operations fail. Log errors using SLF4J logger.

**Test:** Simulate database error (disconnect database). Operations fail gracefully with error messages. Errors appear in log file.

### Step 12.4: Add CSS Styling
Create style.css in resources/css folder. Add basic styling for buttons, tables, and forms. Apply stylesheet to all scenes.

**Test:** Run application. UI components display with consistent, improved styling. Colors and fonts match CSS definitions.

### Step 12.5: Test Complete User Flow
Perform complete workflow: Launch application, login as admin, navigate to room management, add room, edit room, delete room, logout, login again.

**Test:** All operations complete without errors. Data persists across logout/login cycles. Application remains stable throughout.

### Step 12.6: Create README.md
In project root, create README.md file documenting: project description, prerequisites, how to build, how to run, default login credentials, project structure.

**Test:** README file is readable and accurate. Instructions can be followed by someone new to the project.

### Step 12.7: Build Final JAR
Run `mvn clean package` in terminal. Verify JAR file is created in target folder.

**Test:** JAR file exists in target folder. Can run application using `java -jar` command (may need additional JavaFX runtime configuration).

---

## Completion Checklist

Once all steps are complete, you should have:

- ✅ Working Hotel Management System base application
- ✅ Functional login system with authentication
- ✅ Dashboard with navigation menu
- ✅ Complete room management (CRUD operations)
- ✅ SQLite database with proper schema
- ✅ MVC architecture properly implemented
- ✅ Clean project structure following best practices
- ✅ Basic error handling and validation
- ✅ Sample data for testing

**Final System Test:** Start application, login, perform all room management operations, logout, verify data persistence. All operations complete successfully without errors or crashes.

---

This implementation plan provides the foundation for your Hotel Management System. Advanced features like booking management, customer management, billing, and reporting can be added following similar patterns once this base is stable and tested.
