import java.util.ArrayList;
import java.time.LocalDate;
/**
 * The Pantry class represents a pantry owned by a user, containing a list of food items.
 * It provides methods to manage the food items and retrieve pantry information.
 * It also maintains a static list of all pantry instances for easy access and management.
 * New method added to print items with expiration date and days until expiration.
 */
public class Pantry {
    private ArrayList<Food> foods;
    private String owner;
    private static ArrayList<Pantry> allPantries = new ArrayList<Pantry>();

    /**
     * Constructs a Pantry object with the specified list of food items and owner.
     * @param foods the list of food items in the pantry
     * @param owner the owner of the pantry
     */
    public Pantry(ArrayList<Food> foods, String owner) {
        this.foods = foods;
        this.owner = owner;
        allPantries.add(this);
    }

    /**
     * Finds a pantry by its owner's name.
     * @param owner the owner of the pantry
     * @return the Pantry object if found, null otherwise
     */
    public static Pantry findPantryByOwner(String owner) {
        for (Pantry pantry : allPantries) {
            if (pantry.getOwner().equalsIgnoreCase(owner)) {
                return pantry;
            }
        }
        return null; 
    }

    /**
     * Gets the list of food items in the pantry.
     * @return the list of food items
     */
    public ArrayList<Food> getFoods() {
        return foods;
    }

    /**
     * Gets the owner of the pantry.
     * @return the owner of the pantry
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Adds a food item to the pantry.
     * @param food the food item to be added
     */
    public void addFood(Food food) {
        foods.add(food);
    }

    /**
     * Removes a food item from the pantry.
     * @param food the food item to be removed
     */
    public void removeFood(Food food) {
        foods.remove(food);
    }

    /**
     * Removes a food item from the pantry by its name.
     * @param food the name of the food item to be removed
     */
    public void removeFood(String food) {
        for (int i = 0; i < foods.size(); i++) {
            if (foods.get(i).getName().equalsIgnoreCase(food)) {
                foods.remove(i);
                break;
            }
        }
    }
    /**
     * Lists all pantry owners.
     */
    public static void listAllOwners(){
        for (Pantry pantry : allPantries) {
            System.out.println("- " + pantry.getOwner());
        }
    } 

    /**
     * Gets the list of food items in the pantry.
     * @return the list of food items
     */
    public ArrayList<Food> getFoodList() {
        return foods;
    }

    /**
     * Gets the list of all pantries.
     * @return the list of all pantries
     */
    public static ArrayList<Pantry> getAllPantries() {
        return allPantries;
    }

    /**
     * Sets the list of all pantries.
     * @param pantries the new list of all pantries
     */
    public static void setAllPantries(ArrayList<Pantry> pantries) {
        allPantries = pantries;
    }

    /**
     * Deletes the pantry owned by the specified owner from the list of all pantries.
     * @param owner the owner of the pantry to be deleted
     * @return true if the pantry was successfully deleted, false otherwise.
     */
    public static boolean deletePantryByOwner(String owner) {
        Pantry p = findPantryByOwner(owner);
        if (p == null) return false;
        return deletePantry(p);
    }

    /**
     * Deletes the specified pantry from the list of all pantries.
     * @param pantry the pantry to be deleted
     * @return true if the pantry was successfully deleted, false otherwise.
     */
    public static boolean deletePantry(Pantry pantry) {
        synchronized (allPantries) {
            boolean removed = allPantries.remove(pantry);
            if (removed) {
                if (pantry.foods != null) pantry.foods.clear();
                pantry.owner = null;
            }
            return removed;
        }
    }

    /**
     * Deletes this pantry from the list of all pantries.
     * @return true if the pantry was successfully deleted, false otherwise.
     */
    public boolean delete() {
        return deletePantry(this);
    }

    // New method: print items with expiration date and days until expiration (uses system local time)
    /**
     * Prints the food items in the pantry along with their expiration dates and status.
     * Items that are past their expiration date are marked accordingly.
     */
    public void printItemsWithExpiry() {
        LocalDate today = LocalDate.now();

        for (Food f : foods) {
            LocalDate exp = f.getExpirationDate();
            String name = f.getName();
            String type = f.getExpirationtype();
            String foodType; 
            if (f instanceof Perishable) {
                foodType = "Perishable";
            } else if (f instanceof NonPerishable){
                foodType = "Nonperishable";
            } else {
                foodType = "Food";
            }

            if (exp == null) {
                System.out.println(name + " (" + foodType + ") - expiration unknown");
                continue;
            }

            // Pick prefix based on type
            String prefix = switch (type) {
                case "USE_BY" -> "use by ";
                case "BEST_BY" -> "best by ";
                default -> "expires ";
            };

            boolean isPast = exp.isBefore(today);
            String marker = "";

            // Markers:
            // Past expiration => "!"
            // Past best-by or use-by => "*"
            if (isPast) {
                if (type == null || type.equals("EXPIRATION") || type.equals("EXPIRES")) {
                    marker = " (Expired)";   // throw away
                } else {
                    marker = " (Check before use)";   // caution
                }
            }

            // Print formatted
            System.out.println(name + " (" + foodType + ") - " + prefix + exp + marker);
        }
    }

}
