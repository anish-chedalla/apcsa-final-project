package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

public class PantryGUI extends Application {
    private final VBox pantryBox = new VBox(10);
    private Pantry userPantry;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        TextInputDialog ownerDialog = new TextInputDialog();
        ownerDialog.setTitle("Pantry Owner");
        ownerDialog.setHeaderText("Enter your name to create/load a pantry:");
        Optional<String> result = ownerDialog.showAndWait();

        if (result.isEmpty() || result.get().trim().isEmpty()) {
            showAlert("Startup Error", "Pantry owner is required.");
            return;
        }

        String owner = result.get().trim();
        userPantry = Pantry.findPantryByOwner(owner);
        if (userPantry == null) {
            userPantry = new Pantry(new ArrayList<>(), owner);
        }

        // Layout setup
        VBox root = new VBox(15);
        root.setPadding(new Insets(15));

        HBox barcodeBox = new HBox(10);
        TextField barcodeField = new TextField();
        barcodeField.setPromptText("Enter barcode");
        Button lookupBtn = new Button("Lookup & Add");
        barcodeBox.getChildren().addAll(barcodeField, lookupBtn);

        HBox manualAddBox = new HBox(10);
        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        TextField brandField = new TextField();
        brandField.setPromptText("Brand");
        DatePicker expDate = new DatePicker();
        Button addManualBtn = new Button("Add Manually");
        manualAddBox.getChildren().addAll(nameField, brandField, expDate, addManualBtn);

        Label pantryLabel = new Label("Pantry Contents:");

        Button refreshBtn = new Button("Refresh");
        refreshBtn.setOnAction(e -> refreshPantryList());

        root.getChildren().addAll(barcodeBox, manualAddBox, pantryLabel, pantryBox, refreshBtn);
        refreshPantryList();

        lookupBtn.setOnAction(e -> {
            String code = barcodeField.getText().trim();
            if (code.isEmpty()) return;
            FoodAPI.ProductInfo info = FoodAPI.getProductInfo(code, false);
            if (info == null) {
                showAlert("Lookup Failed", "Product not found.");
                return;
            }
            LocalDate date = LocalDate.now().plusDays(30);
            Food f = new Perishable(info.getName(), code, info.getBrand(), date, "EXPIRATION");
            userPantry.addFood(f);
            showAlert("Added", "Item added: " + f.getName());
            refreshPantryList();
        });

        addManualBtn.setOnAction(e -> {
            String name = nameField.getText().trim();
            String brand = brandField.getText().trim();
            LocalDate date = expDate.getValue();
            if (name.isEmpty() || brand.isEmpty() || date == null) {
                showAlert("Missing Fields", "Please fill all fields.");
                return;
            }
            Food f = new NonPerishable(name, null, brand, date, "BEST_BY");
            userPantry.addFood(f);
            showAlert("Added", "Item added: " + f.getName());
            refreshPantryList();
        });

        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Pantry Manager - JavaFX");
        stage.show();
    }

    private void refreshPantryList() {
        pantryBox.getChildren().clear();
        for (Food food : userPantry.getFoodList()) {
            Label label = new Label(food.getName() + " - " + food.getBrand() + " (" + food.getExpirationDate() + ")");
            Button removeBtn = new Button("Remove");
            removeBtn.setOnAction(e -> {
                userPantry.removeFood(food);
                refreshPantryList();
            });
            HBox itemBox = new HBox(10, label, removeBtn);
            pantryBox.getChildren().add(itemBox);
        }
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
