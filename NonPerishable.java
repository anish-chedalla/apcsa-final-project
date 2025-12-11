import java.time.LocalDate;

/**
 * The NonPerishable class represents a non-perishable food item.
 * It extends the Food class and adds a foodType attribute to specify that it is non-perishable.
 */
public class NonPerishable extends Food {
	private String foodType;

	/**
	 * Constructs a NonPerishable food item with the specified details.
	 * 
	 * @param name           the name of the food item
	 * @param barcode        the barcode of the food item
	 * @param brand          the brand of the food item
	 * @param expirationDate the expiration date of the food item
	 */
	public NonPerishable(String name, String barcode, String brand, LocalDate expirationDate) {
		super(name, barcode, brand, expirationDate);
		this.foodType = "NonPerishable";
	}

	/**
	 * Constructs a NonPerishable food item with the specified details and expiration type.
	 * 
	 * @param name           the name of the food item
	 * @param barcode        the barcode of the food item
	 * @param brand          the brand of the food item
	 * @param expirationDate the expiration date of the food item
	 * @param expirationType the type of expiration (e.g., "sell-by", "use-by")
	 */
	public NonPerishable(String name, String barcode, String brand, LocalDate expirationDate, String expirationType) {
		super(name, barcode, brand, expirationDate, expirationType);
		this.foodType = "NonPerishable";
	}

	/**
	 * Gets the food type of the item.
	 * 
	 * @return the food type
	 */
	public String getFoodType() {
		return foodType;
	}
}
