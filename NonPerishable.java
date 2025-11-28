import java.time.LocalDate;

public class NonPerishable extends Food {
	private int shelfLifeMonths; // approximate shelf life in months
	private String recommendedStorage; // e.g., "pantry", "cool dry place"

	public NonPerishable(String name, String barcode, String brand, LocalDate expirationDate,
						int shelfLifeMonths, String recommendedStorage) {
		super(name, barcode, brand, expirationDate);
		this.shelfLifeMonths = shelfLifeMonths;
		this.recommendedStorage = recommendedStorage;
	}

	public int getShelfLifeMonths() {
		return shelfLifeMonths;
	}

	public String getRecommendedStorage() {
		return recommendedStorage;
	}

	public boolean isLongShelfLife() {
		return shelfLifeMonths >= 12;
	}

	@Override
	public String toString() {
		return "NonPerishable{" + super.toString() + ", shelfLifeMonths=" + shelfLifeMonths + 
			   ", recommendedStorage='" + recommendedStorage + "'}";
	}
}
