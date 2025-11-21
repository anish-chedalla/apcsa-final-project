import java.util.ArrayList;

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
}
