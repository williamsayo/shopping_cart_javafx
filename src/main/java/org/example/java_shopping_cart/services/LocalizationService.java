package org.example.java_shopping_cart.services;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import org.example.java_shopping_cart.db.DataBaseConnection;
import org.example.java_shopping_cart.db.ShoppingCartDAO;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class LocalizationService {
    private static Locale currentLocale = Locale.US;
    private static Map<String, String> strings = new HashMap<>();
    private static final LongProperty localeChangeUpdate =
            new SimpleLongProperty(0);

    private LocalizationService () {}

    public static Map<String, String> getLocalizedStrings(Locale locale) {
        try{
            ShoppingCartDAO shoppingCartDAO = new ShoppingCartDAO(DataBaseConnection.getConnection());
            strings = shoppingCartDAO.getAllLocaleStrings(locale.getLanguage());
        }catch(Exception e){
            strings.put("welcomeMessage", "Enter number of items:");
            strings.put("itemPrice", "Enter price for item:");
            strings.put("itemQuantity", "Enter quantity for item:");
            strings.put("total", "Total cost:");
            strings.put("calculateButton", "Calculate Total");
            strings.put("enterItemsButton", "Enter items");
        }
        return strings;
    }

    public static void changeLocale(Locale locale) {
        getLocalizedStrings(locale);
        localeChangeUpdate.set(localeChangeUpdate.get() + 1);
        currentLocale = locale;
    }

    public static Locale getLocale() {
        return currentLocale;
    }

    public static StringBinding bind(String key) {
        return Bindings.createStringBinding(
                () -> strings.get(key),
                localeChangeUpdate
        );
    }
}
