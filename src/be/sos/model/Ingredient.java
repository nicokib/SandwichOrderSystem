package be.sos.model;

import be.sos.enumeration.Language;
import be.sos.repository.Identifiable;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

public class Ingredient {
    private Map<Language, String> names;

    public Ingredient( Map<Language, String> names) {
        this.names = names;
    }

    public Ingredient(String nameFr, String nameNl) {
        names = new EnumMap<>(Language.class);
        this.names.put(Language.FR, nameFr);
        this.names.put(Language.NL, nameNl);
    }

    public String getName(Language language) {
        return names.get(language);
    }

    public void setName(Language language, String name) {
        this.names.put(language, name.replace(",", " ").replace(";", " ").replace("/", " "));
    }


    public Map<Language, String> getNames() {
        return names;
    }

    public void setNames(Map<Language, String> names) {
        this.names.clear();
        if (names != null) {
            names.forEach(this::setName);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Ingredient that)) return false;
        return Objects.equals(names, that.names);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(names);
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                ", names=" + names +
                '}';
    }
}
