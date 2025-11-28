import java.time.LocalDate;

public class NonPerishable extends Food {
	private String foodType;

	public NonPerishable(String name, String barcode, String brand, LocalDate expirationDate) {
		super(name, barcode, brand, expirationDate);
		this.foodType = "NonPerishable";
	}
	public NonPerishable(String name, String barcode, String brand, LocalDate expirationDate, String expirationType) {
		super(name, barcode, brand, expirationDate, expirationType);
		this.foodType = "NonPerishable";
	}
	public String getFoodType() {
		return foodType;
	}
}
