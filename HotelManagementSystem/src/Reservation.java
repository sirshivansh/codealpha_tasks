import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Reservation represents a booking made by a guest for a specific room and date range.
 */
public class Reservation {
    // Member variables (Private for safety encapsulation)
    private String reservationId;       // Unique ID like RES-1001
    private String guestName;
    private String guestContact;
    private int roomNumber;
    private LocalDate checkIn;          // Check-in date (Year, Month, Day)
    private LocalDate checkOut;         // Check-out date (Year, Month, Day)
    private double totalAmount;         // Total price in INR (₹)
    private boolean isPaid;             // Has payment been completed?
    private String paymentTransactionId;// Transaction ID from payment gateway
    private boolean isCancelled;        // Cancellation state

    /**
     * Constructor 1: Creates a Reservation object with all details.
     * This is useful when loading reservation history from the CSV file.
     */
    public Reservation(String reservationId, String guestName, String guestContact, int roomNumber, 
                       LocalDate checkIn, LocalDate checkOut, double totalAmount, 
                       boolean isPaid, String paymentTransactionId, boolean isCancelled) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.guestContact = guestContact;
        this.roomNumber = roomNumber;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalAmount = totalAmount;
        this.isPaid = isPaid;
        this.paymentTransactionId = paymentTransactionId;
        this.isCancelled = isCancelled;
    }

    /**
     * Constructor 2: Creates a brand-new reservation.
     * It automatically calculates the total cost based on the number of nights stayed.
     */
    public Reservation(String reservationId, String guestName, String guestContact, int roomNumber, 
                       LocalDate checkIn, LocalDate checkOut, double pricePerNight) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.guestContact = guestContact;
        this.roomNumber = roomNumber;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        // Automatically calculate total amount based on price per night
        this.totalAmount = calculateTotalAmount(pricePerNight);
        this.isPaid = false;
        this.paymentTransactionId = "PENDING";
        this.isCancelled = false;
    }

    /**
     * Calculates total amount = nights * rate.
     * Uses ChronoUnit.DAYS.between to find the number of nights.
     */
    private double calculateTotalAmount(double pricePerNight) {
        long days = ChronoUnit.DAYS.between(checkIn, checkOut);
        if (days <= 0) {
            days = 1; // Safeguard: if check-in and check-out are the same day, count as 1 night
        }
        return days * pricePerNight;
    }

    // --- GETTER AND SETTER METHODS ---

    public String getReservationId() { return reservationId; }
    public String getGuestName() { return guestName; }
    public String getGuestContact() { return guestContact; }
    public int getRoomNumber() { return roomNumber; }
    public LocalDate getCheckIn() { return checkIn; }
    public LocalDate getCheckOut() { return checkOut; }
    public double getTotalAmount() { return totalAmount; }
    public boolean isPaid() { return isPaid; }
    public String getPaymentTransactionId() { return paymentTransactionId; }
    public boolean isCancelled() { return isCancelled; }

    public void setPaid(boolean paid) { this.isPaid = paid; }
    public void setPaymentTransactionId(String paymentTransactionId) { this.paymentTransactionId = paymentTransactionId; }
    public void setCancelled(boolean cancelled) { this.isCancelled = cancelled; }

    /**
     * Check if this reservation's dates overlap with another check-in/check-out range.
     * 
     * How overlapping range logic works:
     * Two date ranges (A and B) overlap if:
     * B_start is before A_end AND A_start is before B_end.
     * If the reservation is cancelled, we ignore it.
     */
    public boolean overlaps(LocalDate otherCheckIn, LocalDate otherCheckOut) {
        if (this.isCancelled) {
            return false; // Cancelled reservation does not occupy the room
        }
        return this.checkIn.isBefore(otherCheckOut) && otherCheckIn.isBefore(this.checkOut);
    }

    /**
     * Formats details for printing. Uses the Indian Rupee symbol (₹) for pricing.
     */
    @Override
    public String toString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String status = isCancelled ? "CANCELLED" : (isPaid ? "PAID" : "UNPAID");
        return String.format("Res ID: %-8s | Room: %-3d | Guest: %-15s | Dates: %s to %s | Total: ₹%-7.2f | Status: %-9s | Txn: %s",
                reservationId, roomNumber, guestName, checkIn.format(dtf), checkOut.format(dtf), totalAmount, status, paymentTransactionId);
    }
}
