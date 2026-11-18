package be.sos;

public class StringUtils {
    public static String capitalize(String str) {
        if (str == null || str.isBlank()) {
            return str;
        }
        String trimmed = str.trim();
        return trimmed.substring(0, 1).toUpperCase() + trimmed.substring(1); //.toLowerCase();
    }
}
