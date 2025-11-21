import java.util.ArrayList;
import java.util.Scanner;
public class appRunner {
    public static void main(String[] args) {


    // FoodAPI.ProductInfo info = FoodAPI.getProductInfo(barcode, false);
    // if (info != null) {
    //     String name = info.getName();
    //     String brand = info.getBrand();

    // }
    boolean running = true;
    Scanner scanner = new Scanner(System.in);
    System.out.println("Welcome to the Food Tracker App");
    while (running) {
        System.out.println("\nChoose an option:\n1. Create Pantry\n2. Edit Existing Pantry\n3. Read About FoodAPI\n4. Exit");
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
                while (true) {
                    System.out.println("Select a Pantry or Quit:");
                    Pantry.listAllOwners();
                    String selectedOwner = scanner.nextLine().trim();
                    if (selectedOwner.equalsIgnoreCase("quit")) {
                        // go back to main menu
                        break;
                    }
                    Pantry selectedPantry = null;
                    try {
                        selectedPantry = Pantry.findPantryByOwner(selectedOwner);
                    } catch (Exception e) {
                        System.out.println("Pantry not found. Please try again.");
                        continue;
                    }
                    if (selectedPantry != null) {
                        pantryFunctions(selectedPantry);
                        break;
                    } else {
                        System.out.println("Pantry not found.");
                    }
                }
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
    scanner.close();
    }

    public static void aboutFoodAPI() {
        System.out.println("The FoodAPI allows you to look up food products by their barcode using the Open Food Facts database.");
        System.out.println("You can retrieve information such as product name and brand.");
        System.out.println("To use the API, provide a barcode and specify whether you want a 'long' or 'short' response.");
    }

    public static void pantryFunctions(Pantry selectedPantry) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nSelect a pantry function:\n1. Add Food\n2. Remove Food\n3. View Pantry Contents");
        int choice2 = scanner.nextInt();
        switch (choice2) {
            case 1:
                System.out.println("\n1. Search food by barcode\n2. Manually enter food details");
                int subChoice = scanner.nextInt();
                if (subChoice == 1) {
                    System.out.println("Enter a barcode to look up:");
                    String barcode = scanner.next();
                    FoodAPI.ProductInfo info = FoodAPI.getProductInfo(barcode, false);
                    if (info != null) {
                        String name = info.getName();
                        String brand = info.getBrand();
                        System.out.println("Product Name: " + name);
                        System.out.println("Brand: " + brand);
                    } else {
                        System.out.println("Product not found.");
                    }
                    Food newFood = new Food(info.getName(), barcode, info.getBrand(), null);
                    selectedPantry.addFood(newFood);
                    System.out.println("Food added to pantry.");


                } else if (subChoice == 2) {
                    System.out.println("Enter food name:");
                    String name = scanner.nextLine();
                    System.out.println("Enter food brand:");
                    String brand = scanner.nextLine();
                    System.out.println("Enter food barcode:");
                    String barcode = scanner.nextLine();
                    Food newFood = new Food(name, barcode, brand, null);
                    selectedPantry.addFood(newFood);
                    System.out.println("Food added to pantry.");
                } else {
                    System.out.println("Invalid choice.");
                }

                break;
            case 2:
                System.out.println("Enter the name of the food to remove:");
                String foodName = scanner.nextLine();
                selectedPantry.removeFood(foodName);
                System.out.println("Food removed from pantry.");
                break;
            case 3:
                ArrayList <Food> allFood = selectedPantry.getFoodList();
                 System.out.println("Pantry Contents:");
                 for (Food food : allFood) {
                     System.out.println("- " + food.getName() + " (" + food.getBrand() + ")");
                 }
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
        scanner.close();
    }
}
