import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Main is the entry point of the Hotel Reservation System.
 * It provides a beautiful, menu-driven CLI (Command Line Interface) for users.
 */
public class Main {
    // ANSI Color Escape Codes (used to style terminal output with colors)
    private static final String RESET = "\u001B[0m";
    private static final String BOLD = "\u001B[1m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";

    private static final Scanner scanner = new Scanner(System.in);
    private static final Hotel hotel = new Hotel();
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void main(String[] args) {
        showWelcomeScreen();
        
        boolean running = true;
        while (running) {
            showMainMenu();
            
            // Read input with validation between option 1 and 6
            int choice = readIntInput("Select an option (1-6): ", 1, 6);
            System.out.println();
            
            // Classic switch-case menu (very simple for beginners to understand)
            switch (choice) {
                case 1:
                    viewAllRooms();
                    break;
                case 2:
                    searchAndBookRoom();
                    break;
                case 3:
                    cancelExistingReservation();
                    break;
                case 4:
                    viewReservationDetails();
                    break;
                case 5:
                    viewAllReservations();
                    break;
                case 6:
                    running = false;
                    showGoodbyeScreen();
                    break;
                default:
                    break;
            }
            
            // Pause before printing the main menu again
            if (running) {
                pressEnterToContinue();
            }
        }
    }

    private static void showWelcomeScreen() {
        System.out.println(CYAN + BOLD + "╔════════════════════════════════════════════════════════════════════╗" + RESET);
        System.out.println(CYAN + BOLD + "║                                                                    ║" + RESET);
        System.out.println(CYAN + BOLD + "║             " + YELLOW + "WELCOME TO THE PREMIER HOTEL RESERVATION" + CYAN + "               ║" + RESET);
        System.out.println(CYAN + BOLD + "║                        " + GREEN + "MANAGEMENT SYSTEM" + CYAN + "                           ║" + RESET);
        System.out.println(CYAN + BOLD + "║                                                                    ║" + RESET);
        System.out.println(CYAN + BOLD + "╚════════════════════════════════════════════════════════════════════╝" + RESET);
        System.out.println();
    }

    private static void showGoodbyeScreen() {
        System.out.println(CYAN + BOLD + "╔════════════════════════════════════════════════════════════════════╗" + RESET);
        System.out.println(CYAN + BOLD + "║            " + YELLOW + "Thank you for using Premier Hotel Reservation System!" + CYAN + "          ║" + RESET);
        System.out.println(CYAN + BOLD + "║                       " + GREEN + "Have a wonderful day!" + CYAN + "                        ║" + RESET);
        System.out.println(CYAN + BOLD + "╚════════════════════════════════════════════════════════════════════╝" + RESET);
    }

    private static void showMainMenu() {
        System.out.println(BLUE + BOLD + "┌──────────────────────────────────────────────────┐" + RESET);
        System.out.println(BLUE + BOLD + "│                    MAIN MENU                     │" + RESET);
        System.out.println(BLUE + BOLD + "├──────────────────────────────────────────────────┤" + RESET);
        System.out.println(BLUE + "│ " + CYAN + "1." + RESET + " View All Rooms & General Status               │");
        System.out.println(BLUE + "│ " + CYAN + "2." + RESET + " Search & Book a Room (Payment Simulation)      │");
        System.out.println(BLUE + "│ " + CYAN + "3." + RESET + " Cancel a Reservation                          │");
        System.out.println(BLUE + "│ " + CYAN + "4." + RESET + " View Booking Details (Search by ID)           │");
        System.out.println(BLUE + "│ " + CYAN + "5." + RESET + " View All Bookings (Admin View)                │");
        System.out.println(BLUE + "│ " + CYAN + "6." + RESET + " Exit System                                   │");
        System.out.println(BLUE + BOLD + "└──────────────────────────────────────────────────┘" + RESET);
    }

    /**
     * Prints a formatted table of all rooms in the hotel.
     */
    private static void viewAllRooms() {
        System.out.println(PURPLE + BOLD + "=== HOTEL ROOM INVENTORY ===" + RESET);
        System.out.println("--------------------------------------------------");
        System.out.printf(BOLD + "%-8s | %-12s | %-12s | %-10s\n" + RESET, "Room No", "Room Type", "Price/Night", "Active Status");
        System.out.println("--------------------------------------------------");
        for (Room r : hotel.getRooms()) {
            String status = r.isAvailable() ? GREEN + "In Service" + RESET : RED + "Maintenance" + RESET;
            System.out.printf("%-8d | %-12s | ₹%-10d | %-10s\n", 
                    r.getRoomNumber(), 
                    r.getRoomType().getDisplayName(), 
                    r.getPrice(), 
                    status);
        }
        System.out.println("--------------------------------------------------");
    }

    /**
     * Directs the room booking workflow.
     */
    private static void searchAndBookRoom() {
        System.out.println(PURPLE + BOLD + "=== BOOK A NEW RESERVATION ===" + RESET);
        
        String guestName = readNonEmptyString("Enter Guest Full Name: ");
        String guestContact = readNonEmptyString("Enter Guest Contact Info (Phone/Email): ");
        
        System.out.println("\nSelect Room Category:");
        System.out.println("1. Standard (₹1500/night)");
        System.out.println("2. Deluxe (₹3500/night)");
        System.out.println("3. Suite (₹7000/night)");
        int typeChoice = readIntInput("Select room category (1-3): ", 1, 3);
        
        RoomType selectedType;
        switch (typeChoice) {
            case 1:
                selectedType = RoomType.STANDARD;
                break;
            case 2:
                selectedType = RoomType.DELUXE;
                break;
            default:
                selectedType = RoomType.SUITE;
                break;
        }

        // Gather check-in/out dates with validation
        LocalDate checkIn = readFutureDate("Enter Check-in Date (YYYY-MM-DD): ", LocalDate.now());
        LocalDate checkOut = readFutureDate("Enter Check-out Date (YYYY-MM-DD): ", checkIn.plusDays(1));

        // Retrieve rooms that are available in this date range
        List<Room> availableRooms = hotel.searchAvailableRooms(selectedType, checkIn, checkOut);
        
        if (availableRooms.isEmpty()) {
            System.out.println(RED + "\n❌ No " + selectedType.getDisplayName() + " rooms are available for the selected dates (" + checkIn + " to " + checkOut + ")." + RESET);
            return;
        }

        // Show available rooms
        System.out.println("\n" + GREEN + "Available Rooms:" + RESET);
        for (Room r : availableRooms) {
            System.out.println(" - Room #" + r.getRoomNumber() + " (₹" + r.getPrice() + "/night)");
        }

        int roomNum = readIntInput("\nEnter Room Number to book: ");
        
        // Match user choice against the available rooms list
        Room selectedRoom = null;
        for (Room r : availableRooms) {
            if (r.getRoomNumber() == roomNum) {
                selectedRoom = r;
                break;
            }
        }

        if (selectedRoom == null) {
            System.out.println(RED + "❌ Invalid selection. That room is either not available or not of the selected type." + RESET);
            return;
        }

        // Calculate nights and cost
        long nights = java.time.temporal.ChronoUnit.DAYS.between(checkIn, checkOut);
        if (nights <= 0) {
            nights = 1;
        }
        double totalCost = nights * selectedRoom.getPrice();

        // Print booking invoice
        System.out.println("\n" + YELLOW + BOLD + "┌──────────────────────────────────────────────────┐" + RESET);
        System.out.println(YELLOW + BOLD + "│               BOOKING SUMMARY                    │" + RESET);
        System.out.println(YELLOW + BOLD + "├──────────────────────────────────────────────────┤" + RESET);
        System.out.printf("│ Guest Name   : %-33s │\n", guestName);
        System.out.printf("│ Room Number  : %-33s │\n", "#" + roomNum + " (" + selectedType.getDisplayName() + ")");
        System.out.printf("│ Dates        : %s to %s        │\n", checkIn, checkOut);
        System.out.printf("│ Duration     : %-33s │\n", nights + " Night(s)");
        System.out.printf("│ Total Amount : ₹%-32.2f │\n", totalCost);
        System.out.println(YELLOW + BOLD + "└──────────────────────────────────────────────────┘" + RESET);

        String confirm = readNonEmptyString("Confirm booking and proceed to payment simulation? (y/n): ");
        if (!confirm.equalsIgnoreCase("y")) {
            System.out.println(RED + "Booking discarded." + RESET);
            return;
        }

        // Simulate payment selection
        System.out.println("\n" + BOLD + "Select Payment Method:" + RESET);
        System.out.println("1. Credit / Debit Card");
        System.out.println("2. UPI / Net Banking");
        System.out.println("3. PayPal / Wallet");
        int payChoice = readIntInput("Select (1-3): ", 1, 3);

        String paymentMethod;
        switch (payChoice) {
            case 1:
                paymentMethod = "Card";
                break;
            case 2:
                paymentMethod = "UPI";
                break;
            default:
                paymentMethod = "PayPal";
                break;
        }

        // Simulated processing delays (aesthetically pleasing loading flow)
        System.out.println("\n" + CYAN + "Connecting to Payment Gateway..." + RESET);
        delay(800);
        System.out.println("Authorizing transaction of " + GREEN + "₹" + totalCost + RESET + " via " + paymentMethod + "...");
        delay(1200);
        System.out.println(GREEN + "✔ Payment Authorization SUCCESSFUL!" + RESET);
        
        // Generate random mock transaction code
        String txnId = "TXN-" + (100000 + new Random().nextInt(900000));
        System.out.println("Transaction ID: " + BOLD + txnId + RESET);

        // Book the room and apply transaction details
        try {
            Reservation res = hotel.bookRoom(guestName, guestContact, roomNum, checkIn, checkOut);
            hotel.updateReservationPayment(res.getReservationId(), txnId);
            
            System.out.println("\n" + GREEN + BOLD + "🎉 RESERVATION CONFIRMED SUCCESSFULLY!" + RESET);
            System.out.println(res);
        } catch (Exception e) {
            System.out.println(RED + "❌ Booking failed: " + e.getMessage() + RESET);
        }
    }

    /**
     * Allows user to cancel an existing reservation.
     */
    private static void cancelExistingReservation() {
        System.out.println(PURPLE + BOLD + "=== CANCEL A RESERVATION ===" + RESET);
        String resId = readNonEmptyString("Enter Reservation ID (e.g. RES-1001): ").toUpperCase();
        
        Reservation res = hotel.getReservationById(resId);
        if (res == null) {
            System.out.println(RED + "❌ Reservation not found with ID " + resId + RESET);
            return;
        }

        if (res.isCancelled()) {
            System.out.println(YELLOW + "⚠ This reservation is already cancelled." + RESET);
            return;
        }

        System.out.println("\nReservation Details:");
        System.out.println(res);
        
        String confirm = readNonEmptyString("Are you sure you want to cancel this reservation? (y/n): ");
        if (confirm.equalsIgnoreCase("y")) {
            boolean success = hotel.cancelReservation(resId);
            if (success) {
                System.out.println(GREEN + "✔ Reservation " + resId + " has been successfully cancelled." + RESET);
            } else {
                System.out.println(RED + "❌ Failed to cancel reservation." + RESET);
            }
        } else {
            System.out.println(YELLOW + "Cancellation discarded." + RESET);
        }
    }

    /**
     * Views details of a single reservation by ID.
     */
    private static void viewReservationDetails() {
        System.out.println(PURPLE + BOLD + "=== SEARCH RESERVATION BY ID ===" + RESET);
        String resId = readNonEmptyString("Enter Reservation ID: ").toUpperCase();
        
        Reservation res = hotel.getReservationById(resId);
        if (res == null) {
            System.out.println(RED + "❌ Reservation not found." + RESET);
            return;
        }

        System.out.println("\n" + GREEN + BOLD + "--- BOOKING DETAILS ---" + RESET);
        System.out.println("Reservation ID  : " + res.getReservationId());
        System.out.println("Guest Name      : " + res.getGuestName());
        System.out.println("Guest Contact   : " + res.getGuestContact());
        System.out.println("Room Number     : " + res.getRoomNumber());
        System.out.println("Stay Dates      : " + res.getCheckIn() + " to " + res.getCheckOut());
        System.out.println("Total Paid      : ₹" + String.format("%.2f", res.getTotalAmount()));
        System.out.println("Payment Status  : " + (res.isPaid() ? GREEN + "PAID" : RED + "PENDING") + RESET);
        System.out.println("Transaction Ref : " + res.getPaymentTransactionId());
        System.out.println("Booking Status  : " + (res.isCancelled() ? RED + "CANCELLED" : GREEN + "ACTIVE") + RESET);
        System.out.println("-----------------------");
    }

    /**
     * Admin tool: print out all bookings stored in CSV.
     */
    private static void viewAllReservations() {
        System.out.println(PURPLE + BOLD + "=== ADMIN: ALL RESERVATIONS ===" + RESET);
        List<Reservation> reservations = hotel.getReservations();
        if (reservations.isEmpty()) {
            System.out.println("No reservations found in the database.");
            return;
        }

        for (Reservation res : reservations) {
            System.out.println(res);
        }
    }

    // --- INPUT GATHERING AND VALIDATION METHODS ---

    /**
     * Reads integer inputs and handles NumberFormatException errors safely.
     */
    private static int readIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println(RED + "❌ Invalid input. Please enter a valid number." + RESET);
            }
        }
    }

    /**
     * Reads integer inputs within a specific valid range (min to max).
     */
    private static int readIntInput(String prompt, int min, int max) {
        while (true) {
            int input = readIntInput(prompt);
            if (input >= min && input <= max) {
                return input;
            }
            System.out.println(RED + "❌ Input out of range. Must be between " + min + " and " + max + "." + RESET);
        }
    }

    /**
     * Reads string inputs, ensuring they are not empty spaces.
     */
    private static String readNonEmptyString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println(RED + "❌ Input cannot be empty. Please enter a value." + RESET);
        }
    }

    /**
     * Reads date inputs, validating the format (YYYY-MM-DD) and ensuring
     * the date is not before the minimumDate limit.
     */
    private static LocalDate readFutureDate(String prompt, LocalDate minimumDate) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                LocalDate date = LocalDate.parse(input, dtf);
                if (date.isBefore(minimumDate)) {
                    System.out.println(RED + "❌ Date cannot be before " + minimumDate.format(dtf) + "." + RESET);
                    continue;
                }
                return date;
            } catch (DateTimeParseException e) {
                System.out.println(RED + "❌ Invalid date format. Please use YYYY-MM-DD (e.g. 2026-06-25)." + RESET);
            }
        }
    }

    private static void pressEnterToContinue() {
        System.out.println("\nPress [ENTER] to return to the Main Menu...");
        scanner.nextLine();
    }

    /**
     * Pauses the code execution for MS milliseconds to create loading transitions.
     */
    private static void delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {
            // Ignore interruption
        }
    }
}
