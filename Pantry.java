import java.util.ArrayList;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


public class Pantry {
    private ArrayList<Food> foods;
    private String owner;
    private static ArrayList<Pantry> allPantries = new ArrayList<Pantry>();

    public Pantry(ArrayList<Food> foods, String owner) {
        this.foods = foods;
        this.owner = owner;
        allPantries.add(this);
    }
    public static Pantry findPantryByOwner(String owner) {
        for (Pantry pantry : allPantries) {
            if (pantry.getOwner().equalsIgnoreCase(owner)) {
                return pantry;
            }
        }
        return null; 
    }
    public ArrayList<Food> getFoods() {
        return foods;
    }
    public String getOwner() {
        return owner;
    }
    public void addFood(Food food) {
        foods.add(food);
    }
    public void removeFood(Food food) {
        foods.remove(food);
    }

    public void removeFood(String food) {
        for (int i = 0; i < foods.size(); i++) {
            if (foods.get(i).getName().equalsIgnoreCase(food)) {
                foods.remove(i);
                break;
            }
        }
    }
    public static void listAllOwners(){
        for (Pantry pantry : allPantries) {
            System.out.println("- " + pantry.getOwner());
        }
    } 

    public ArrayList<Food> getFoodList() {
        return foods;
    }
    public static ArrayList<Pantry> getAllPantries() {
        return allPantries;
    }
    public static void setAllPantries(ArrayList<Pantry> pantries) {
        allPantries = pantries;
    }

    // Delete by owner name. Returns true if deleted.
    public static boolean deletePantryByOwner(String owner) {
        Pantry p = findPantryByOwner(owner);
        if (p == null) return false;
        return deletePantry(p);
    }

    // Delete by Pantry instance. Returns true if deleted.
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

    // Instance convenience method to delete this pantry.
    public boolean delete() {
        return deletePantry(this);
    }

    // New method: print items with expiration date and days until expiration (uses system local time)
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
