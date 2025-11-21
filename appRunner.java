import java.util.ArrayList;
import java.util.Scanner;
public class appRunner {
    public static void main(String[] args) {

    // Scanner input = new Scanner(System.in);
    // System.out.println("Enter a barcode to look up:");
    // String barcode = input.nextLine();

    // System.out.println("App Runner3");

    // FoodAPI.ProductInfo info = FoodAPI.getProductInfo(barcode, false);
    // if (info != null) {
    //     String name = info.getName();
    //     String brand = info.getBrand();

    //     System.out.println("Product Name: " + name);
    //     System.out.println("Brand: " + brand);
    // }
    boolean running = true;
    System.out.println("Welcome to the Food Tracker App");
    while (running) {
        System.out.println("Choose an option:\n1. Create Pantry\n2. Edit Existing Pantry\n3. Read About FoodAPI\n4. Exit");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        scanner.nextLine(); 
        switch (choice) {
            case 1:
                System.out.println("Enter pantry owner name:");
                String owner = scanner.nextLine();
                ArrayList<Food> foods = new ArrayList<Food>();
                Pantry pantry = new Pantry(foods, owner);
                System.out.println("Pantry created for " + pantry.getOwner());
                break;
            case 2:
                System.out.println("Feature to edit existing pantry is under development.");
                break;
            case 3:
                appRunner.aboutFoodAPI();
                break;
            case 4:
                System.out.println("Exiting the application. Goodbye!");
                running = false;
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    }

    public static void aboutFoodAPI() {
        System.out.println("The FoodAPI allows you to look up food products by their barcode using the Open Food Facts database.");
        System.out.println("You can retrieve information such as product name and brand.");
        System.out.println("To use the API, provide a barcode and specify whether you want a 'long' or 'short' response.");
    }
}
