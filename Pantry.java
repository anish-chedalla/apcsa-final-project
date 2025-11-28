import java.util.ArrayList;
import java.util.Date;
import java.lang.reflect.Method;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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

            if (exp == null) {
                System.out.println(name + " - expiration unknown");
                continue;
            }

            long days = ChronoUnit.DAYS.between(today, exp);

            if (days < 0) {
                System.out.println(name + " - expired " + (-days) + " days ago");
            } else if (days == 0) {
                System.out.println(name + " - expires today");
            } else if (days == 1) {
                System.out.println(name + " - expires in 1 day");
            } else {
                System.out.println(name + " - expires in " + days + " days");
            }
        }
        }
    }
