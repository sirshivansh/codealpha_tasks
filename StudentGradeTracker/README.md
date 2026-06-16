# Student Grade Tracker

A command-line based **Student Grade Tracker** developed in Java. The application is built using standard Object-Oriented Programming (OOP) design patterns and persists its data inside a local text file.

It is designed to be **highly readable for beginners** (using classic switch statements and standard control flows), handles input buffer issues cleanly, and performs robust validation.

---

## Key Features

- **Dynamic Student Management**: Add and delete student profiles dynamically at runtime.
- **Statistics Engine**: Calculate and generate real-time metrics including total marks, class average, highest score, and lowest score.
- **Data Persistence**: Automatic file storage in comma-separated values format (`students.txt`), loading existing records on startup and saving changes on modifications.
- **Robust Input Validation**: Validates grades to ensure they lie within the 0-100 range and checks bounds when deleting entries.
- **CLI Navigation**: A smooth, interactive console-based menu system.

---

## OOP Architecture

The project contains 2 core classes representing different layers of the application:

1. **`Student`**: A stateful model class representing a student's profile.
   - **Fields**: `name` (String), `marks` (int).
   - **Constructor**: Initializes the name and marks, using the `this` keyword to resolve scope variables.
2. **`Main`**: The Orchestrator and User Interface Controller.
   - **Menu Loop**: Drives the console interface.
   - **CRUD Operations**: Handles logic for adding, viewing, and removing students.
   - **Statistics & Formatting**: Aggregates records, performs floating-point division with type-casting, and formats tabular reports using `printf` specifiers.
   - **File I/O**: Reads and writes records from/to `students.txt` line-by-line using `BufferedReader` and `FileWriter`.

---

## Data Schema (`students.txt`)

Stores the list of students and their marks in a comma-separated format. The application automatically loads from and writes to this file.
```text
Shivansh Mishra,99
Jaya Mishra,100
Vijaya Mishra,8
```

---

## Compilation & Run Instructions

To run the application manually from the terminal:

1. Open your terminal and navigate to the project directory:
   ```bash
   cd StudentGradeTracker
   ```

2. Compile the Java files:
   ```bash
   javac -d bin src/Main.java
   ```

3. Launch the application:
   ```bash
   java -cp bin Main
   ```

---

## Educational Design Details

This codebase was refactored with learning in mind:
- **Scanner Buffer Management**: Avoids common scanner input issues by explicitly flushing the buffer stream after numeric inputs.
- **Dynamic Sizing**: Uses `ArrayList` to manage records on the heap dynamically rather than rigid fixed-size arrays.
- **Graceful Exception Handling**: Uses structured `try-catch` blocks to handle Checked Exceptions (`IOException`) without crashing the program.
