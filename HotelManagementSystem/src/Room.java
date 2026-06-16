/**
 * Room represents a single hotel room.
 * This class uses OOP encapsulation (private fields and public getter/setter methods).
 */
public class Room {
    // Private variables (encapsulation prevents direct modification from outside)
    private int roomNumber;
    private RoomType roomType;
    private int price;             // Price per night in Indian Rupees (Rs)
    private boolean isAvailable;   // Availability state (true = in service, false = maintenance)

    /**
     * Constructor 1: Fully customizes the room with a custom price.
     */
    public Room(int roomNumber, RoomType roomType, int price){
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.price = price;
        this.isAvailable = true; // By default, new rooms are available (in service)
    }

    /**
     * Constructor 2: Overloaded constructor that uses the default price for that RoomType.
     * This is called constructor chaining.
     */
    public Room(int roomNumber, RoomType roomType){
        this(roomNumber, roomType, roomType.getPrice());
    }

    // --- GETTER AND SETTER METHODS ---
    // These allow other classes to safely access private variables

    public int getRoomNumber() {
        return roomNumber;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public int getPrice() {
        return price;
    }

    public boolean isAvailable(){
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable){
        this.isAvailable = isAvailable;
    }

    /**
     * toString() is overridden to print Room details cleanly in lists.
     * Use Indian Rupee symbol (Rs) for pricing.
     */
    @Override
    public String toString() {
        return String.format("Room %-3d | %-8s | Rs %d/night | %s", 
            roomNumber, roomType.getDisplayName(), price, (isAvailable ? "Available" : "Occupied"));
    }
}
