package be.sos.repository;

import be.sos.enumeration.Language;
import be.sos.model.SandwichWithOptions;

import java.util.Objects;
import be.sos.utils.I18n;

public class StatSandwich {

    private SandwichWithOptions sandwich;
    private long quantity;
    private double percentage;

    public StatSandwich(SandwichWithOptions sandwich, long quantity, double percentage) {
        this.sandwich = sandwich;
        this.quantity = quantity;
        this.percentage = percentage;
    }

    public SandwichWithOptions getSandwich() {
        return sandwich;
    }

    public void setSandwich(SandwichWithOptions sandwich) {
        this.sandwich = sandwich;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof StatSandwich that)) return false;
        return quantity == that.quantity && Double.compare(percentage, that.percentage) == 0 && Objects.equals(sandwich, that.sandwich);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(sandwich);
    }

    @Override
    public String toString() {
        return String.format("%-20.20s  %-15.15s  %-15.15s  %6.2f%%", sandwich.getName(I18n.getCurrentLanguage()),
                sandwich.getBreadType().getDisplayName(I18n.getCurrentLanguage()),
                sandwich.isCrudities() ? " with crudities" : " ".repeat(" with crudities".length()),
                percentage);
    }
}
