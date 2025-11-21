import java.lang.reflect.Array;
import java.util.ArrayList;

public class Pantry {
    private ArrayList<Food> foods;
    private String owner;

    public Pantry(ArrayList<Food> foods, String owner) {
        this.foods = foods;
        this.owner = owner;
    }
    public ArrayList<Food> getFoods() {
        return foods;
    }
    public String getOwner() {
        return owner;
    }
    
}
