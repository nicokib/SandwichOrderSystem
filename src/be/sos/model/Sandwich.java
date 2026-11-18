package be.sos.model;

import be.sos.enumeration.Language;
import be.sos.repository.Identifiable;

import java.lang.reflect.Type;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Sandwich implements Identifiable<Integer> {
    private Integer id;
    private Map<Language, String> names;
    private TypeSandwich typeSandwich;
    private double price;
    private boolean cruditiesOptionAvailable;
    private List<Ingredient> ingredients;

    public Sandwich(Integer id, Map<Language, String> names, TypeSandwich typeSandwich, double price, boolean cruditiesOptionAvailable, List<Ingredient> ingredients) {
        this.id = id;
        this.names = names;
        this.typeSandwich = typeSandwich;
        this.price = price;
        this.cruditiesOptionAvailable = cruditiesOptionAvailable;
        this.ingredients = ingredients;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName(Language language) {
        return names.get(language);
    }

    public void setName(Language language, String name) {
        this.names.put(language, name.replace(",", " ").replace(";", " ").replace("/", " "));
    }

    public TypeSandwich getTypeSandwich() {
        return typeSandwich;
    }

    public void setTypeSandwich(TypeSandwich typeSandwich) {
        this.typeSandwich = typeSandwich;
    }

    public double getPrice() {
        return price;
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

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isCruditiesOptionAvailable() {
        return cruditiesOptionAvailable;
    }

    public void setCruditiesOptionAvailable(boolean cruditiesOptionAvailable) {
        this.cruditiesOptionAvailable = cruditiesOptionAvailable;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Sandwich sandwich)) return false;
        return Objects.equals(names, sandwich.names);
    }

    @Override
    public int hashCode() {
        return Objects.hash(names);
    }

    @Override
    public String toString() {
        return "Sandwich{" +
                "id=" + id +
                ", names=" + names +
                ", typeSandwich=" + typeSandwich +
                ", price=" + price +
                ", cruditiesOptionAvailable=" + cruditiesOptionAvailable +
                ", ingredients=" + ingredients +
                '}';
    }
}
