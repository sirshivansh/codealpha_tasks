# CodeAlpha Software Development Internship - Tasks Submission

Welcome to my repository for the CodeAlpha Software Development Internship! This repository contains the projects I implemented during the internship program.

---

## Projects Included

### 1. 🏨 Hotel Reservation System (`HotelManagementSystem/`)
A premium, command-line hotel room search, booking, and management console application built using Java.
- **Key Features**:
  - Room categorization (Standard, Deluxe, Suite) with rates in Indian Rupees (Rs).
  - Multi-guest scheduling with date-range checking to prevent double-booking.
  - Interactive payment simulation (Card, UPI, PayPal) and detailed booking invoices.
  - File-based persistence (CSV database for rooms and reservations).
  - Designed with beginner-friendly object-oriented patterns and detailed Javadoc comments.

### 2. 🎓 Student Grade Tracker (`StudentGradeTracker/`)
A utility to track and compute grades for students.
- **Key Features**:
  - Grade calculations (Average, GPA, letter grades).
  - File storage to persist student information.
  - Grade distribution analytics.

---

## Quick Start Guide

Each project is self-contained and run via the command line. Ensure you have Java JDK (version 17 or higher) installed.

### Run Hotel Reservation System:
1. Navigate to the project folder:
   ```bash
   cd HotelManagementSystem
   ```
2. Compile the source code:
   ```bash
   javac -d bin src/*.java
   ```
3. Run the application:
   ```bash
   java -cp bin Main
   ```

### Run Student Grade Tracker:
1. Navigate to the project folder:
   ```bash
   cd StudentGradeTracker
   ```
2. Compile the source code:
   ```bash
   javac -d bin src/Main.java
   ```
3. Run the application:
   ```bash
   java -cp bin Main
   ```

---

## Technologies Used
- **Language**: Java 17+ (built/tested with OpenJDK 25)
- **Data Persistence**: File I/O (CSV / Text files)
- **Aesthetics**: ANSI Escape terminal codes for bold text and colored menus.
