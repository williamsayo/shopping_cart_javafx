package org.example.java_shopping_cart.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Locale;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LocalizationServiceTest {

    @BeforeEach
    void setUp() {
        // Reset locale to default before each test
        LocalizationService.changeLocale(Locale.US);
    }

    @Test
    void testGetLocaleDefault() {
        Locale locale = LocalizationService.getLocale();
        assertNotNull(locale);
        assertEquals(Locale.US, locale);
    }

    @Test
    void testChangeLocale() {
        Locale desiredLocale = Locale.forLanguageTag("fi-FI");
        LocalizationService.changeLocale(desiredLocale);
        Locale result = LocalizationService.getLocale();

        assertEquals(desiredLocale, result);
    }

    @Test
    void testChangeLocaleMultipleTimes() {
        final String swedish = "sv-SE";
        final String japanese = "ja-JP";
        final String arabic = "jar-AE";
        LocalizationService.changeLocale(Locale.forLanguageTag(swedish));
        assertEquals(Locale.forLanguageTag(swedish), LocalizationService.getLocale());

        LocalizationService.changeLocale(Locale.forLanguageTag(japanese));
        assertEquals(Locale.forLanguageTag(japanese), LocalizationService.getLocale());

        LocalizationService.changeLocale(Locale.forLanguageTag(arabic));
        assertEquals(Locale.forLanguageTag(arabic), LocalizationService.getLocale());
    }

    @Test
    void testGetLocalizedStringsWithFallback() {
        // Act - This will use fallback strings since database connection might fail
        Map<String, String> strings = LocalizationService.getLocalizedStrings(Locale.US);

        // Assert - Should contain at least the fallback strings
        assertNotNull(strings);
        assertFalse(strings.isEmpty());
        
        // Verify fallback strings are present
        assertTrue(strings.containsKey("welcomeMessage") || strings.isEmpty());
    }

    @Test
    void testBindFunctionality() {
        // Arrange
        LocalizationService.changeLocale(Locale.US);

        // Act
        var binding = LocalizationService.bind("welcomeMessage");

        // Assert
        assertNotNull(binding);
    }

    @Test
    void testMultipleLocalesSequence() {
        // Test that changing between locales works correctly
        Locale[] locales = {
            Locale.US,
            Locale.forLanguageTag("fi-FI"),
            Locale.forLanguageTag("sv-SE"),
            Locale.forLanguageTag("ja-JP"),
            Locale.forLanguageTag("ar-AE")
        };

        for (Locale locale : locales) {
            // Act
            LocalizationService.changeLocale(locale);
            Locale current = LocalizationService.getLocale();

            // Assert
            assertEquals(locale, current);
        }
    }

    @Test
    void testLocalizationServiceInitialization() {
        // Assert that the service is properly initialized
        assertNotNull(LocalizationService.getLocale());
        assertNotNull(LocalizationService.getLocalizedStrings(Locale.US));
    }

    @ParameterizedTest
    @ValueSource(strings = {"en-US", "fi-FI", "sv-SE", "ja-JP", "ar-AE"})
    void testGetLocalizedStringsMultipleLanguages(String lang) {

        Map<String, String> strings =
            LocalizationService.getLocalizedStrings(Locale.forLanguageTag(lang));

        assertNotNull(strings);
    }
}
