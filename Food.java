import java.time.LocalDate;

public class Food {
    private String name;
    private String barcode;
    private String brand;
    private LocalDate expirationDate;
    private String expirationtype;

    public Food(String name, String barcode, String brand, LocalDate expirationDate) {
        this.name = name;
        this.barcode = barcode;
        this.brand = brand;
        this.expirationDate = expirationDate;
        this.expirationtype = "expiration";
    }

    public Food(String name, String barcode, String brand, LocalDate expirationDate, String expirationtype) {
        this(name, barcode, brand, expirationDate);
        if (expirationtype != null) {
            this.expirationtype = expirationtype;
        }
    }

    public String getExpirationtype() {
        return expirationtype;
    }

    public String getName() {
        return name;
    }
    public String getBarcode() {
        return barcode;
    }
    public String getBrand() {
        return brand;
    }
    public LocalDate getExpirationDate() {
        return expirationDate;
    }
    @Override
    public String toString() {
        return "Food{name='" + name + "', barcode='" + barcode + "', brand='" + brand + "', expirationDate=" + expirationDate + "}";
    }
}
