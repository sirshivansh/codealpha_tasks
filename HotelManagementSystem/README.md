# Task 4: Hotel Reservation System

A command-line based **Hotel Reservation System** developed in Java. The application is built using standard Object-Oriented Programming (OOP) design patterns and persists its data inside a local CSV database. 

It is designed to be **highly readable for beginners** (using classic switch statements and standard control flows) and outputs all pricing details in **Indian Rupees (₹)**.

---

## Key Features

- **Room Categories**: Supports Standard (₹1,500/night), Deluxe (₹3,500/night), and Suite (₹7,000/night) room types.
- **Dynamic Search**: Search for available rooms within custom check-in and check-out dates.
- **Overlap Prevention**: Includes date overlap algorithms to prevent double-booking the same room during intersecting dates.
- **Interactive Booking Flow**: Prompts guest details, calculates total cost, and displays a clean invoice.
- **Simulated Payment Gateway**: Simulates card processing, UPI, and digital wallet authorizations with loading animations and transaction ID generations.
- **Cancellation**: Easily cancel existing active reservations.
- **Database Persistence**: Automatic file storage in CSV format (`data/rooms.csv` and `data/reservations.csv`).

---

## OOP Architecture

The project contains 6 core classes representing different layers of the application:

1. **`RoomType` (Enum)**: Declares the room categories and associates them with their default rates.
2. **`Room`**: Holds details about room numbers, types, and base pricing.
3. **`Reservation`**: Holds customer reservation details, payment status, transaction codes, and calculates number of nights.
4. **`FileManager`**: Manages reading and writing data to CSV files. Safely handles exceptions and folder loading.
5. **`Hotel`**: Orchestrates bookings, cancellations, availability search, and generates incremental reservation codes.
6. **`Main`**: User-facing interface controller. Contains validation loops, ASCII menus, colored notifications, and payment animations.

---

## CSV Database Schema

### Rooms (`data/rooms.csv`)
Stores the register of rooms. Automatically bootstraps on the first launch.
```csv
roomNumber,roomType,price,isAvailable
101,STANDARD,1500,true
102,STANDARD,1500,true
201,DELUXE,3500,true
301,SUITE,7000,true
```

### Reservations (`data/reservations.csv`)
Stores customer logs and transaction status.
```csv
reservationId,guestName,guestContact,roomNumber,checkInDate,checkOutDate,totalAmount,isPaid,paymentTransactionId,isCancelled
RES-1001,John Doe,9876543210,101,2026-06-20,2026-06-25,7500.00,true,TXN-582314,false
```

---

## Compilation & Run Instructions

To run the application:

1. Open your terminal and navigate to the project directory:
   ```bash
   cd HotelManagementSystem
   ```

2. Compile the Java files:
   ```bash
   javac -d bin src/*.java
   ```

3. Launch the application:
   ```bash
   java -cp bin Main
   ```

---

## Educational Design Details
This codebase was refactored with learning in mind:
- **Classic Switches**: Uses classic `switch-case` blocks with `break` lines instead of newer expression arrow switches to ensure code style is instantly recognizable.
- **Encapsulation**: Follows strict getter/setter conventions.
- **Try-With-Resources**: Implements safe automatic-closing file streams.
