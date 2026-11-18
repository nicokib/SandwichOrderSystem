package be.sos.utils;

import be.sos.enumeration.Language;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class I18n {
    private static final String BASE_NAME = "be.sos.resources.messages";

    private static Language currentLanguage = Language.FR;
    private static ResourceBundle currentBundle = loadBundle(currentLanguage);

    public static Language getCurrentLanguage() {
        return currentLanguage;
    }

    public static void setCurrentLanguage(Language language) {
        if (language != null && language != currentLanguage) {
            currentLanguage = language;
            currentBundle = loadBundle(language);
        }
    }

    private static ResourceBundle loadBundle(Language language) {
        Locale locale = (language == Language.NL) ? Locale.forLanguageTag("nl") : Locale.FRENCH;
        return ResourceBundle.getBundle(BASE_NAME, locale);
    }

    private static Locale getCurrentLocale() {
        return (currentLanguage == Language.NL) ? Locale.forLanguageTag("nl") : Locale.FRENCH;
    }

    public static String getMessage(String key) {
        try {
            return currentBundle.getString(key);
        } catch (MissingResourceException e) {
            return "[" + key + "]";
        }
    }

    public static String getMessage(String key, Object... args) {
        if (args == null || args.length == 0) {
            return getMessage(key);
        }

        String pattern = getMessage(key);
        if (pattern.startsWith("[") && pattern.endsWith("]")) {
            return pattern;
        }

        MessageFormat formatter = new MessageFormat(pattern, getCurrentLocale());
        return formatter.format(args);
    }
}