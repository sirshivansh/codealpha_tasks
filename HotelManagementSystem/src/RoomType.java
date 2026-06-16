/**
 * RoomType is an Enum that represents the categories of rooms available.
 * Each category has a user-friendly display name and a price in Indian Rupees (Rs).
 */
public enum RoomType {
    // Categories with realistic Indian Rupee prices per night
    STANDARD("Standard", 1500),
    DELUXE("Deluxe", 3500),
    SUITE("Suite", 7000);

    private final String displayName;
    private final int price; // Price in INR (Rs)

    // Enum Constructor - called automatically when initializing constants above
    RoomType(String displayName, int price) {
        this.displayName = displayName;
        this.price = price;
    }

    // Getter method to retrieve the display name
    public String getDisplayName() {
        return displayName;
    }

    // Getter method to retrieve the price per night
    public int getPrice() {
        return price;
    }

    /**
     * Converts a string input (like "Standard" or "deluxe") to the matching RoomType enum.
     * This makes it easy to parse room types stored in CSV files.
     */
    public static RoomType fromString(String text) {
        for (RoomType type : RoomType.values()) {
            if (type.displayName.equalsIgnoreCase(text) || type.name().equalsIgnoreCase(text)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown Room Type: " + text);
    }
}
