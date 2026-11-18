package be.sos.enumeration;

import java.util.EnumMap;
import java.util.Map;

public enum PersonRole {
    STUDENT (Map.of(
            Language.FR, "étudiant",
            Language.NL, "student")),
    INSTRUCTOR(Map.of(
            Language.FR, "instructeur",
            Language.NL, "instructeur")),
    OFFICE_MANAGER(Map.of(
            Language.FR, "office manager",
            Language.NL, "office manager"));

    private Map<Language, String> displayNames = new EnumMap<>(Language.class);


    PersonRole(Map<Language, String> displayNames) {
        this.displayNames = displayNames;
    }

    public String getDisplayName(Language Language) {
        return displayNames.get(Language);
    }
}
