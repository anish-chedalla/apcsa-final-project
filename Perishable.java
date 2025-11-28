import java.time.LocalDate;

public class Perishable extends Food {
	private String foodType;

	public Perishable(String name, String barcode, String brand, LocalDate expirationDate) {
		super(name, barcode, brand, expirationDate);
		this.foodType = "Perishable";
	}

	public String getFoodType() {
		return foodType;
	}
}
