package be.sos.enumeration;

import java.util.EnumMap;
import java.util.Map;

public enum YesNo {
    YES(Map.of(Language.FR, "oui",
          Language.NL, "ja"),
            Map.of(Language.FR, "O",
                    Language.NL, "N")),
    NO(Map.of(Language.FR, "Non",
              Language.NL, "Nee"),
            Map.of(Language.FR, "J",
                    Language.NL, "N"));

    private Map<Language, String> displayNames = new EnumMap<>(Language.class);
    private Map<Language, String> abbreviations = new EnumMap<>(Language.class);


    YesNo(Map<Language, String> displayNames,
          Map<Language, String> abbreviations) {
            this.displayNames = displayNames;
            this.abbreviations = abbreviations;

    }

    public String getDisplayName(Language Language) {
        return displayNames.get(Language);
    }

    public String getAbbreviation(Language Language) {
        return displayNames.get(Language);
    }
}
