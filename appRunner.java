import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.DateTimeException;
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
        System.out.println("\nChoose an option:\n1. Create Pantry\n2. Delete Pantry\n3. Edit Existing Pantry\n4. Read Purpose Statement\n5. Exit");
        int choice;
        try {
            choice = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a number between 1 and 5.");
            scanner.nextLine(); // clear the invalid input
            continue; // restart the loop
        }
        scanner.nextLine(); 
        switch (choice) {
            case 1:
            String owner;
            while (true) {
                System.out.println("\nEnter pantry owner name:");
                owner = scanner.nextLine().trim();
                if (owner.isEmpty()) {
                System.out.println("Owner name cannot be empty. Please enter a valid name.");
                continue;
                }
                if (owner.equalsIgnoreCase("quit")) {
                System.out.println("The name 'quit' is reserved. Please choose a different owner name.");
                continue;
                }
                if (Pantry.findPantryByOwner(owner) != null) {
                System.out.println("A pantry with that owner name already exists. Please select another name.");
                continue;
                }
                break;
            }
            ArrayList<Food> foods = new ArrayList<Food>();
            Pantry pantry = new Pantry(foods, owner);
            System.out.println("Pantry created for " + pantry.getOwner());
            break;
            case 2:
                // Delete a pantry by owner name
                if (Pantry.getAllPantries() == null || Pantry.getAllPantries().isEmpty()) {
                    System.out.println("No pantries exist. Nothing to delete.");
                    break;
                }
                System.out.println("\nExisting pantries:");
                Pantry.listAllOwners();
                System.out.println("Enter the owner name of the pantry to delete (or type 'cancel'):");
                String ownerToDelete = scanner.nextLine().trim();
                if (ownerToDelete.equalsIgnoreCase("cancel")) {
                    System.out.println("Delete cancelled.");
                    break;
                }
                boolean deleted = Pantry.deletePantryByOwner(ownerToDelete);
                if (deleted) {
                    System.out.println("Pantry for '" + ownerToDelete + "' deleted.");
                } else {
                    System.out.println("No pantry found for '" + ownerToDelete + "'.");
                }
                break;
            case 3:
                // Edit Existing Pantry (moved from option 2)
                // if there are no pantries, inform the user and return to main menu before entering selection loop
                if (Pantry.getAllPantries() == null || Pantry.getAllPantries().isEmpty()) {
                    System.out.println("No pantries exist. Please create a pantry first.");
                    break;
                }

                while (true) {
                    System.out.println("\nSelect a Pantry or Quit:");
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
                        System.out.println("\nPantry not found. Please try again.");
                        continue;
                    }
                    if (selectedPantry != null) {
                        pantryFunctions(selectedPantry, scanner);
                        break;
                    } else {
                        System.out.println("Pantry not found.");
                    }
                }
                break;
            case 4:
                appRunner.appPurpose();
                break;
            case 5:
                System.out.println("Exiting the application. Goodbye!");
                running = false;
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
    scanner.close();
    }

    public static void appPurpose() {
        System.out.println("This app is designed to help people keep track of perishable foods before they go bad.");
        System.out.println("Many foodborne illnesses come from eating food that was kept past a safe time.");
        System.out.println("At the same time, a lot of perfectly good food gets thrown away because date labels are confusing.");
        System.out.println("By logging what you have and when it expires, this app aims to lower the risk of food poisoning");
        System.out.println("and reduce food waste by giving clearer reminders about when food is likely no longer safe to eat.");
    }

    private static LocalDate promptForExpiration(Scanner scanner) {
        while (true) {
            try {
                System.out.println("Enter expiration year (e.g. 2025):");
                int year = Integer.parseInt(scanner.nextLine().trim());
                System.out.println("Enter expiration month (1-12):");
                int month = Integer.parseInt(scanner.nextLine().trim());
                System.out.println("Enter expiration day (1-31):");
                int day = Integer.parseInt(scanner.nextLine().trim());
                LocalDate date = LocalDate.of(year, month, day);
                return date;
            } catch (NumberFormatException e) {
                System.out.println("Please enter numeric values for year, month and day.");
            } catch (DateTimeException e) {
                System.out.println("Invalid date. Please try again (e.g. 2025 12 31).");
            }
        }
    }

    private static String promptForExpirationType(Scanner scanner) {
        while (true) {
            System.out.println("Enter expiration type (Expiration, Use By, or Best By):");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("Expiration") || input.equalsIgnoreCase("Use By") || input.equalsIgnoreCase("Best By")) {
                return input.toUpperCase().replace(" ", "_");
            }
            System.out.println("Invalid type. Please enter one of: Expiration, Use By, or Best By");
        }
    }

    private static String promptForFoodType(Scanner scanner) {
        while (true) {
            System.out.println("Enter food type (Perishable or Nonperishable):");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("Perishable") || input.equalsIgnoreCase("NonPerishable")) {
                return input;
            }
            System.out.println("Invalid type. Please enter one of: Perishable or Nonperishable");
        }
    }


    public static void pantryFunctions(Pantry selectedPantry, Scanner scanner) {
        boolean inPantry = true;
        while (inPantry) {
            System.out.println("\nSelect a pantry function:\n1. Add Food\n2. Remove Food\n3. View Pantry Contents\n4. See Expiry Dates\n5. Back to Main Menu");
            int choice2;
            try {
                choice2 = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number between 1 and 5.");
                scanner.nextLine();
                continue;
            }
            scanner.nextLine();
            switch (choice2) {
                case 1:
                    System.out.println("\n1. Search food by barcode\n2. Manually enter food details");
                    int subChoice;
                    try {
                        subChoice = scanner.nextInt();
                    } catch (Exception e) {
                        System.out.println("Invalid input. Please enter 1 or 2.");
                        scanner.nextLine();
                        break;
                    }
                    scanner.nextLine();
                    if (subChoice == 1) {
                        System.out.println("\nEnter a barcode to look up:");
                        String barcode = scanner.nextLine().trim();
                        FoodAPI.ProductInfo info = null;
                        try {
                            info = FoodAPI.getProductInfo(barcode, false);
                        } catch (Exception e) {
                            System.out.println("Lookup failed: " + e.getMessage());
                        }
                            if (info != null && !info.getName().equalsIgnoreCase("Unknown")) {
                                String name = info.getName();
                                String brand = info.getBrand();
                                System.out.println("Product Name: " + name);
                                System.out.println("Brand: " + brand);
                                String foodType = promptForFoodType(scanner);
                                LocalDate expDate = promptForExpiration(scanner);
                                String expType = promptForExpirationType(scanner);
                                Food newFood;
                                if (foodType.equalsIgnoreCase("Perishable")) {
                                    newFood = new Perishable(info.getName(), barcode, info.getBrand(), expDate, expType);
                                } else {
                                    newFood = new NonPerishable(info.getName(), barcode, info.getBrand(), expDate, expType);
                                }
                                selectedPantry.addFood(newFood);
                                System.out.println("Food added to pantry.");
                            } else {
                                System.out.println("Product not found.");
                            }
                    } else if (subChoice == 2) {
                        System.out.println("\nEnter food name:");
                        String name = scanner.nextLine();
                        System.out.println("Enter food brand:");
                        String brand = scanner.nextLine();

                        String foodType = promptForFoodType(scanner);
                        LocalDate expDate = promptForExpiration(scanner);
                        String expType = promptForExpirationType(scanner);
                        Food newFood;
                        if (foodType.equalsIgnoreCase("Perishable")) {
                            newFood = new Perishable(name, null, brand, expDate, expType);
                        } else {
                            newFood = new NonPerishable(name, null, brand, expDate, expType);
                        }
                        selectedPantry.addFood(newFood);
                        System.out.println("Food added to pantry.");
                    } else {
                        System.out.println("Invalid choice.");
                    }
                    break;
                case 2:
                    // show pantry contents first (similar to case 3) so the user can pick what to remove
                    ArrayList<Food> foodsToRemove = selectedPantry.getFoodList();
                    if (foodsToRemove.isEmpty()) {
                        System.out.println("Pantry is empty. Nothing to remove.");
                        break;
                    }
                    System.out.println("Pantry Contents:");
                    for (int i = 0; i < foodsToRemove.size(); i++) {
                        Food f = foodsToRemove.get(i);
                        System.out.println((i + 1) + ". " + f.getName() + " (" + f.getBrand() + ")");
                    }

                    System.out.println("Enter the number of the food to remove, or type the exact food name (or 'cancel'):");
                    String removeInput = scanner.nextLine().trim();
                    if (removeInput.equalsIgnoreCase("cancel")) {
                        System.out.println("Removal cancelled.");
                        break;
                    }

                    // remove by exact name entered by user
                    boolean found = false;
                    for (Food f : foodsToRemove) {
                        if (f.getName().equalsIgnoreCase(removeInput)) {
                            found = true;
                            break;
                        }
                    }
                    if (found) {
                        selectedPantry.removeFood(removeInput);
                        System.out.println("Removed: " + removeInput);
                    } else {
                        System.out.println("No food named '" + removeInput + "' found in pantry.");
                    }
                    break;
                case 3:
                    ArrayList <Food> allFood = selectedPantry.getFoodList();
                    System.out.println("Pantry Contents:");
                    if (allFood.isEmpty()) {
                        System.out.println("Pantry is empty.");
                    } else {
                        for (Food food : allFood) {
                            System.out.println("- " + food.getName() + " (" + food.getBrand() + ")");
                        }
                    }
                    break;
                case 4:
                    // Show expiry dates and days until expiration
                    selectedPantry.printItemsWithExpiry();
                    break;
                case 5:
                    System.out.println("Returning to main menu...");
                    inPantry = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
