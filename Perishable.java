import java.time.LocalDate;

public class Perishable extends Food {
	private double storageTemperatureC; // preferred storage temp in Celsius
	private boolean refrigerated; // whether item should be refrigerated

	public Perishable(String name, String barcode, String brand, LocalDate expirationDate,
					  double storageTemperatureC, boolean refrigerated) {
		super(name, barcode, brand, expirationDate);
		this.storageTemperatureC = storageTemperatureC;
		this.refrigerated = refrigerated;
	}

	public double getStorageTemperatureC() {
		return storageTemperatureC;
	}

	public boolean isRefrigerated() {
		return refrigerated;
	}

	public boolean isExpired() {
		LocalDate exp = getExpirationDate();
		return exp != null && exp.isBefore(LocalDate.now());
	}

	public String storageAdvice() {
		if (refrigerated) return "Keep refrigerated at around " + storageTemperatureC + "°C";
		return "Store at room temperature (approx " + storageTemperatureC + "°C)";
	}

	@Override
	public String toString() {
		return "Perishable{" + super.toString() + ", storageTemperatureC=" + storageTemperatureC + 
			   ", refrigerated=" + refrigerated + "}";
	}
}
