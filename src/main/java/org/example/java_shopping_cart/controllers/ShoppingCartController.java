package org.example.java_shopping_cart.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.example.java_shopping_cart.model.ShoppingCart;
import org.example.java_shopping_cart.services.LocalizationService;

import java.text.NumberFormat;
import java.util.*;

public class ShoppingCartController {

    @FXML private ComboBox<String> languageComboBox;
    @FXML private Label totalCost;
    @FXML private Button cartSizeBtn;
    @FXML private Button calculateBtn;
    @FXML private VBox rootView;
    @FXML private VBox itemsVBox;
    @FXML private Label appTitle;
    @FXML private Label welcomeMessage;
    @FXML private TextField cartSize;
    @FXML private Label totalCostLabel;

    private Map<String, String> localizedStrings;

    private final List<TextField> priceFields = new ArrayList<>();
    private final List<TextField> qtyFields   = new ArrayList<>();
    private ShoppingCart shoppingCart = new ShoppingCart();

    @FXML
    public void initialize(){
        setLanguage(LocalizationService.getLocale());
    }

    /**
     * Set the application language
     */
    @FXML
    private void buildItemRows() {
        int count;
        try {
            count = Integer.parseInt(cartSize.getText().trim());
            if (count < 1 || count > 50) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter a whole number between 1 and 50.");
            alert.showAndWait();
            return;
        }

        // Clear previous state
        itemsVBox.getChildren().clear();
        priceFields.clear();
        qtyFields.clear();
        totalCost.setText("");

        for (int index = 1; index <= count; index++) {
            itemsVBox.getChildren().add(buildRow());
        }
    }

    private HBox buildRow() {
        VBox priceWrapper = new VBox(5);
        Label priceLabel = new Label();
        priceLabel.textProperty().bind(LocalizationService.bind("itemPrice"));
        TextField priceField = new TextField();
        priceField.promptTextProperty().bind(LocalizationService.bind("itemPrice"));
        priceField.setPrefWidth(150);
        priceWrapper.getChildren().addAll(priceLabel, priceField);
        HBox.setHgrow(priceWrapper, Priority.ALWAYS);

        VBox qtyWrapper = new VBox(5);
        Label qtyLabel = new Label();
        qtyLabel.textProperty().bind(LocalizationService.bind("itemQuantity"));
        TextField qtyField = new TextField("1");
        qtyField.setPrefWidth(150);
        HBox.setHgrow(qtyWrapper, Priority.ALWAYS);

        qtyWrapper.getChildren().addAll(qtyLabel, qtyField);

        priceFields.add(priceField);
        qtyFields.add(qtyField);

        HBox row = new HBox(15, priceWrapper,qtyWrapper);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(4, 8, 4, 8));
        return row;
    }

    @FXML
    private void calculateTotal() {
        priceFields.forEach(f -> f.setStyle(""));
        qtyFields.forEach(f   -> f.setStyle(""));

        for (int index = 0; index < priceFields.size(); index++) {
            TextField pricefield = priceFields.get(index);
            TextField qtyfield = qtyFields.get(index);
            try {
                double price = Double.parseDouble(pricefield.getText().trim());
                int qty   = Integer.parseInt(qtyfield.getText().trim());
                if (price < 0 || qty < 1) throw new NumberFormatException();
                double itemCost = shoppingCart.calculateItemCost(qty,price);
                shoppingCart.addToCart(itemCost);
            } catch (NumberFormatException e) {
                pricefield.setStyle("-fx-border-color: red;");
                qtyfield.setStyle("-fx-border-color: red;");
                shoppingCart.clearCart();
                return;
            }
        }
        totalCost.setText(shoppingCart.formatTotalPrice(LocalizationService.getLocale()));
        shoppingCart.clearCart();
    }

    public void changeLanguage(ActionEvent actionEvent) {
        switch (languageComboBox.getValue()){
            case "English" -> setLanguage(Locale.US);
            case "suomalainen" -> setLanguage(new Locale("fi", "FI"));
            case "Svenska" ->setLanguage(new Locale("sv", "SE"));
            case "日本語" ->  setLanguage(new Locale("ja", "JP"));
            case "العربية" -> setLanguage(new Locale("ar", "AE"));
        }
    }

    private void setLanguage(Locale locale) {
        LocalizationService.changeLocale(locale);

        // Load localized strings
        localizedStrings = LocalizationService.getLocalizedStrings(locale);

        // Update all UI text
        welcomeMessage.setText(localizedStrings.get("welcomeMessage"));
        totalCostLabel.setText(localizedStrings.get("total"));
        calculateBtn.setText(localizedStrings.get("calculateButton"));
        cartSizeBtn.setText(localizedStrings.get("enterItemsButton"));

        setOrientation(locale);
    }

    private void setOrientation(Locale locale) {

        String lang = locale.getLanguage();
        boolean isRTL = lang.equals("ar");  // Arabic

        // Step 2: Wrap UI changes in Platform.runLater() for thread safety
        Platform.runLater(() -> {
            // Step 3: Set NodeOrientation on the root VBox
            if (rootView != null) {
                rootView.setNodeOrientation(
                        isRTL ? NodeOrientation.RIGHT_TO_LEFT
                                : NodeOrientation.LEFT_TO_RIGHT
                );
            }

            // Step 4: Align text inside TextFields
            String alignment = isRTL ? "-fx-text-alignment: right; -fx-alignment: center-right;"
                    : "-fx-text-alignment: left; -fx-alignment: center-left;";
            cartSize.setStyle(alignment);
        });
    }

}
