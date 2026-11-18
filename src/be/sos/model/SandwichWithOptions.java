package be.sos.model;

import be.sos.enumeration.BreadType;
import be.sos.enumeration.Language;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SandwichWithOptions extends Sandwich {
    static double cruditiesPrice = 1.0;
    private boolean crudities;
    private BreadType breadType;

    public SandwichWithOptions(Integer id, Map<Language, String> names, TypeSandwich typeSandwich, double price, boolean cruditiesOptionAvailable, List<Ingredient> ingredients, boolean crudities, BreadType breadType) {
        super(id, names, typeSandwich, price, cruditiesOptionAvailable, ingredients);
        this.crudities = crudities;
        this.breadType = breadType;
    }

    public SandwichWithOptions(Sandwich sandwich, boolean crudities, BreadType breadType) {
        super(sandwich.getId(), sandwich.getNames(), sandwich.getTypeSandwich(), sandwich.getPrice(),
                sandwich.isCruditiesOptionAvailable(), sandwich.getIngredients());
        this.crudities = crudities;
        this.breadType = breadType;
    }

    public static double getCruditiesPrice() {
        return cruditiesPrice;
    }

    public static void setCruditiesPrice(double cruditiesPrice) {
        SandwichWithOptions.cruditiesPrice = cruditiesPrice;
    }

    public boolean isCrudities() {
        return crudities;
    }

    public void setCrudities(boolean crudities) {
        this.crudities = crudities;
    }

    public BreadType getBreadType() {
        return breadType;
    }

    public void setBreadType(BreadType breadType) {
        this.breadType = breadType;
    }

    public double getPrice() {
        return super.getPrice() + (crudities ? cruditiesPrice : 0);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SandwichWithOptions sandwich)) return false;

        return Objects.equals(getNames(), sandwich.getNames())
                && crudities == sandwich.crudities
                && breadType == sandwich.breadType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNames(), crudities, breadType);
    }

    @Override
    public String toString() {
        return "SandwichWithOptions{" +
                "crudities=" + crudities +
                ", breadType=" + breadType +
                '}';
    }
}
