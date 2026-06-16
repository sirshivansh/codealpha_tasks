import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * FileManager is a helper class that handles saving and loading data to/from CSV files.
 * CSV stands for Comma-Separated Values, a simple text-based format for storing tables.
 */
public class FileManager {
    private static final String DATA_DIR = "data";
    private static final String ROOMS_FILE = DATA_DIR + "/rooms.csv";
    private static final String RESERVATIONS_FILE = DATA_DIR + "/reservations.csv";

    /**
     * Ensures that the directory "data" exists. If not, it creates it.
     */
    public static void ensureDataDirectoryExists() {
        File directory = new File(DATA_DIR);
        if (!directory.exists()) {
            directory.mkdirs(); // mkdirs() creates the directory folder
        }
    }

    /**
     * Loads rooms from data/rooms.csv.
     * If the file does not exist, it populates it with standard default rooms.
     */
    public static List<Room> loadRooms() {
        ensureDataDirectoryExists();
        List<Room> rooms = new ArrayList<>();
        File file = new File(ROOMS_FILE);

        // Bootstrap: If no database exists, generate a set of default rooms
        if (!file.exists()) {
            rooms.add(new Room(101, RoomType.STANDARD));
            rooms.add(new Room(102, RoomType.STANDARD));
            rooms.add(new Room(103, RoomType.STANDARD));
            rooms.add(new Room(201, RoomType.DELUXE));
            rooms.add(new Room(202, RoomType.DELUXE));
            rooms.add(new Room(203, RoomType.DELUXE));
            rooms.add(new Room(301, RoomType.SUITE));
            rooms.add(new Room(302, RoomType.SUITE));
            saveRooms(rooms); // Save these defaults immediately
            return rooms;
        }

        // Try-with-resources automatically closes the BufferedReader when done
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                // Skip the CSV column headers (first line)
                if (isFirstLine && line.contains("roomNumber")) {
                    isFirstLine = false;
                    continue;
                }
                isFirstLine = false;
                
                // Split the comma-separated text into parts
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    try {
                        // Convert string values back to original types
                        int roomNumber = Integer.parseInt(parts[0].trim());
                        RoomType type = RoomType.fromString(parts[1].trim());
                        int price = Integer.parseInt(parts[2].trim());
                        boolean isAvailable = Boolean.parseBoolean(parts[3].trim());
                        
                        Room room = new Room(roomNumber, type, price);
                        room.setAvailable(isAvailable);
                        rooms.add(room);
                    } catch (Exception e) {
                        System.err.println("Warning: Skipping malformed room line: " + line);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading rooms file: " + e.getMessage());
        }

        return rooms;
    }

    /**
     * Saves the current list of rooms to data/rooms.csv.
     */
    public static void saveRooms(List<Room> rooms) {
        ensureDataDirectoryExists();
        try (PrintWriter pw = new PrintWriter(new FileWriter(ROOMS_FILE))) {
            // Write CSV headers
            pw.println("roomNumber,roomType,price,isAvailable");
            for (Room room : rooms) {
                pw.printf("%d,%s,%d,%b\n", 
                        room.getRoomNumber(), 
                        room.getRoomType().name(), 
                        room.getPrice(), 
                        room.isAvailable());
            }
        } catch (IOException e) {
            System.err.println("Error writing rooms file: " + e.getMessage());
        }
    }

    /**
     * Loads the list of reservations from data/reservations.csv.
     */
    public static List<Reservation> loadReservations() {
        ensureDataDirectoryExists();
        List<Reservation> reservations = new ArrayList<>();
        File file = new File(RESERVATIONS_FILE);

        if (!file.exists()) {
            return reservations; // Return empty list if no bookings made yet
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                // Skip the CSV column headers (first line)
                if (isFirstLine && line.contains("reservationId")) {
                    isFirstLine = false;
                    continue;
                }
                isFirstLine = false;

                String[] parts = line.split(",");
                if (parts.length >= 10) {
                    try {
                        String reservationId = parts[0].trim();
                        String guestName = parts[1].trim();
                        String guestContact = parts[2].trim();
                        int roomNumber = Integer.parseInt(parts[3].trim());
                        LocalDate checkIn = LocalDate.parse(parts[4].trim());
                        LocalDate checkOut = LocalDate.parse(parts[5].trim());
                        double totalAmount = Double.parseDouble(parts[6].trim());
                        boolean isPaid = Boolean.parseBoolean(parts[7].trim());
                        String paymentTransactionId = parts[8].trim();
                        boolean isCancelled = Boolean.parseBoolean(parts[9].trim());

                        Reservation reservation = new Reservation(
                                reservationId, guestName, guestContact, roomNumber,
                                checkIn, checkOut, totalAmount, isPaid, paymentTransactionId, isCancelled
                        );
                        reservations.add(reservation);
                    } catch (Exception e) {
                        System.err.println("Warning: Skipping malformed reservation line: " + line);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading reservations file: " + e.getMessage());
        }

        return reservations;
    }

    /**
     * Saves the current list of reservations to data/reservations.csv.
     */
    public static void saveReservations(List<Reservation> reservations) {
        ensureDataDirectoryExists();
        try (PrintWriter pw = new PrintWriter(new FileWriter(RESERVATIONS_FILE))) {
            // Write CSV headers
            pw.println("reservationId,guestName,guestContact,roomNumber,checkInDate,checkOutDate,totalAmount,isPaid,paymentTransactionId,isCancelled");
            for (Reservation res : reservations) {
                pw.printf("%s,%s,%s,%d,%s,%s,%.2f,%b,%s,%b\n",
                        res.getReservationId(),
                        res.getGuestName().replace(",", ";"), // Replace commas to prevent breaking CSV cells
                        res.getGuestContact().replace(",", ";"),
                        res.getRoomNumber(),
                        res.getCheckIn().toString(),
                        res.getCheckOut().toString(),
                        res.getTotalAmount(),
                        res.isPaid(),
                        res.getPaymentTransactionId(),
                        res.isCancelled());
            }
        } catch (IOException e) {
            System.err.println("Error writing reservations file: " + e.getMessage());
        }
    }
}
