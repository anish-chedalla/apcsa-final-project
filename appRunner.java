import java.util.Scanner;
public class appRunner {
    public static void main(String[] args) {

    Scanner input = new Scanner(System.in);
    System.out.println("Enter a barcode to look up:");
    String barcode = input.nextLine();

    System.out.println("App Runner3");

    FoodAPI.ProductInfo info = FoodAPI.getProductInfo(barcode, false);
    if (info != null) {
        String name = info.getName();
        String brand = info.getBrand();

        System.out.println("Product Name: " + name);
        System.out.println("Brand: " + brand);
    }

    }
}
