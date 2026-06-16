import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Hotel manages all rooms and reservation transactions.
 * It serves as the primary controller for business logic.
 */
public class Hotel {
    // Member lists containing our data collections
    private List<Room> rooms;
    private List<Reservation> reservations;

    /**
     * Constructor loads rooms and reservations from files via FileManager.
     */
    public Hotel() {
        this.rooms = FileManager.loadRooms();
        this.reservations = FileManager.loadReservations();
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    /**
     * Linear search to find a room by its number.
     */
    public Room getRoomByNumber(int roomNumber) {
        for (Room r : rooms) {
            if (r.getRoomNumber() == roomNumber) {
                return r; // Found
            }
        }
        return null; // Not found
    }

    /**
     * Linear search to find a reservation by its ID (case-insensitive).
     */
    public Reservation getReservationById(String reservationId) {
        for (Reservation r : reservations) {
            if (r.getReservationId().equalsIgnoreCase(reservationId)) {
                return r; // Found
            }
        }
        return null; // Not found
    }

    /**
     * Checks if a room is free for the specified date range.
     * 1. Check if the room itself is in service (not in maintenance).
     * 2. Loop through all reservations to verify there are no overlaps.
     */
    public boolean isRoomAvailableForDates(int roomNumber, LocalDate checkIn, LocalDate checkOut) {
        Room room = getRoomByNumber(roomNumber);
        if (room == null || !room.isAvailable()) {
            return false; // Room is either invalid or under maintenance
        }

        for (Reservation res : reservations) {
            // Check if reservation is for the same room and overlaps with target dates
            if (res.getRoomNumber() == roomNumber && res.overlaps(checkIn, checkOut)) {
                return false; // Found an overlapping booking!
            }
        }
        return true; // No overlaps found; room is free
    }

    /**
     * Searches for all available rooms of a specific type (Standard/Deluxe/Suite)
     * during a given date range.
     */
    public List<Room> searchAvailableRooms(RoomType type, LocalDate checkIn, LocalDate checkOut) {
        List<Room> availableRooms = new ArrayList<>();
        for (Room r : rooms) {
            if (r.getRoomType() == type) {
                if (isRoomAvailableForDates(r.getRoomNumber(), checkIn, checkOut)) {
                    availableRooms.add(r);
                }
            }
        }
        return availableRooms;
    }

    /**
     * Books a room and saves the reservation record.
     * Throws an IllegalArgumentException if the room is invalid or already booked.
     */
    public Reservation bookRoom(String guestName, String guestContact, int roomNumber, LocalDate checkIn, LocalDate checkOut) {
        Room room = getRoomByNumber(roomNumber);
        if (room == null) {
            throw new IllegalArgumentException("Room " + roomNumber + " does not exist.");
        }
        if (!isRoomAvailableForDates(roomNumber, checkIn, checkOut)) {
            throw new IllegalArgumentException("Room " + roomNumber + " is not available for the selected dates.");
        }

        // Generate next ID like RES-1001, RES-1002, etc.
        String resId = generateNextReservationId();
        
        // Create the reservation instance
        Reservation newRes = new Reservation(resId, guestName, guestContact, roomNumber, checkIn, checkOut, room.getPrice());
        reservations.add(newRes);
        
        // Save the updated list of reservations to database file
        FileManager.saveReservations(reservations);
        return newRes;
    }

    /**
     * Cancels a reservation based on ID, releasing the room for the dates.
     */
    public boolean cancelReservation(String reservationId) {
        Reservation res = getReservationById(reservationId);
        if (res != null && !res.isCancelled()) {
            res.setCancelled(true);
            FileManager.saveReservations(reservations); // Save cancel state
            return true;
        }
        return false;
    }

    /**
     * Updates payment status of a booking after a successful simulation.
     */
    public void updateReservationPayment(String reservationId, String transactionId) {
        Reservation res = getReservationById(reservationId);
        if (res != null) {
            res.setPaid(true);
            res.setPaymentTransactionId(transactionId);
            FileManager.saveReservations(reservations); // Save payment confirmation
        }
    }

    /**
     * Generates a unique, incremental reservation ID.
     * It scans existing reservations, extracts their numerical ID suffix,
     * finds the maximum numeric value, and increments it by 1.
     */
    private String generateNextReservationId() {
        int maxId = 1000;
        for (Reservation res : reservations) {
            String id = res.getReservationId();
            if (id.startsWith("RES-")) {
                try {
                    int num = Integer.parseInt(id.substring(4));
                    if (num > maxId) {
                        maxId = num;
                    }
                } catch (NumberFormatException ignored) {
                    // Ignore malformed IDs
                }
            }
        }
        return "RES-" + (maxId + 1);
    }
}
