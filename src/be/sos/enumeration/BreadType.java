package be.sos.enumeration;

import java.util.EnumMap;
import java.util.Map;

public enum BreadType {
    WHITE(Map.of(
            Language.FR, "pain blanc",
            Language.NL, "witbrood")),
    GRAY(Map.of(
            Language.FR, "pain gris",
            Language.NL, "bruinbrood"));

    private Map<Language, String> displayNames = new EnumMap<>(Language.class);


    BreadType(Map<Language, String> displayNames) {
        this.displayNames = displayNames;
    }

    public String getDisplayName(Language Language) {
        return displayNames.get(Language);
    }
}
