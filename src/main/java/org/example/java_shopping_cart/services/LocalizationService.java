package org.example.java_shopping_cart.services;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class LocalizationService {
    private static Locale currentLocale = Locale.US;
    private static Map<String, String> strings = new HashMap<>();
    private static final LongProperty localeChangeUpdate =
            new SimpleLongProperty(0);

    public static Map<String, String> getLocalizedStrings(Locale locale) {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(
                    "i18n.MessageBundles",
                    locale
            );

            // Extract all keys
            for (String key : bundle.keySet()) {
                strings.put(key, bundle.getString(key));
            }
        } catch (Exception e) {
            System.err.println("Failed to load resource bundle for locale: " + locale);
            // Fallback to English
            try {
                ResourceBundle fallback = ResourceBundle.getBundle(
                        "i18n.MessagesBundle",
                        Locale.US
                );
                for (String key : fallback.keySet()) {
                    strings.put(key, fallback.getString(key));
                }
            } catch (Exception ex) {
                // Use hardcoded defaults as last resort
                strings.put("welcomeMessage", "Enter number of items:");
                strings.put("itemPrice", "Enter price for item:");
                strings.put("itemQuantity", "Enter quantity for item:");
                strings.put("total", "Total cost:");
            }
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
