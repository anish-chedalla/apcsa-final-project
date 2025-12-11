import java.time.LocalDate;

/**
 * The Food class represents a food item with its details such as name, barcode, brand, expiration date, and expiration type.
 * It provides constructors to create Food objects and getter methods to access their attributes.
 */
public class Food {
    private String name;
    private String barcode;
    private String brand;
    private LocalDate expirationDate;
    private String expirationtype;

    /**
     * Constructs a Food object with the specified details.
     * 
     * @param name           the name of the food item
     * @param barcode        the barcode of the food item
     * @param brand          the brand of the food item
     * @param expirationDate the expiration date of the food item
     */
    public Food(String name, String barcode, String brand, LocalDate expirationDate) {
        this.name = name;
        this.barcode = barcode;
        this.brand = brand;
        this.expirationDate = expirationDate;
        this.expirationtype = "expiration";
    }

    /**
     * Constructs a Food object with the specified details and expiration type.
     * 
     * @param name           the name of the food item
     * @param barcode        the barcode of the food item
     * @param brand          the brand of the food item
     * @param expirationDate the expiration date of the food item
     * @param expirationtype the type of expiration (e.g., "sell-by", "use-by")
     */
    public Food(String name, String barcode, String brand, LocalDate expirationDate, String expirationtype) {
        this(name, barcode, brand, expirationDate);
        if (expirationtype != null) {
            this.expirationtype = expirationtype;
        }
    }

    /**
     * Gets the expiration type of the food item.
     * 
     * @return the expiration type
     */
    public String getExpirationtype() {
        return expirationtype;
    }

    /**
     * Gets the name of the food item.
     * 
     * @return the name of the food item
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the barcode of the food item.
     * 
     * @return the barcode of the food item
     */
    public String getBarcode() {
        return barcode;
    }

    /**
     * Gets the brand of the food item.
     * 
     * @return the brand of the food item
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Gets the expiration date of the food item.
     * 
     * @return the expiration date
     */
    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    /**
     * Returns a string representation of the Food object.
     * 
     * @return string representation of the Food
     */
    @Override
    public String toString() {
        return "Food{name='" + name + "', barcode='" + barcode + "', brand='" + brand + "', expirationDate=" + expirationDate + "}";
    }
}
