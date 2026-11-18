package be.sos.service;

import be.sos.StringUtils;
import be.sos.model.Ingredient;
import be.sos.model.Sandwich;
import be.sos.repository.SandwichFileRepository;
import be.sos.repository.SandwichRepository;
import be.sos.utils.I18n;

import java.util.List;

public class SandwichService {
    private static final SandwichRepository sandwichRepository = SandwichFileRepository.getInstance();

    public static List<Sandwich> getAllSandwiches() {
        return sandwichRepository.findAll();
    }

    public static String getCompleteSandwichName(Sandwich sandwich) {
        String completeName = StringUtils.capitalize(sandwich.getName(I18n.getCurrentLanguage()));

        if(sandwich.getIngredients() != null && sandwich.getIngredients().size() > 0) {
            completeName += " (";

            completeName += StringUtils.capitalize(sandwich.getIngredients().get(0).getName(I18n.getCurrentLanguage()));
            for(int i = 1; i < sandwich.getIngredients().size(); i++) {
                completeName += ", " + sandwich.getIngredients().get(i).getName(I18n.getCurrentLanguage());
            }
            /*for(Ingredient ingredient : sandwich.getIngredients()) {
                completeName += (firstIngredient ? "" : ", ") + ingredient.getName(I18n.getCurrentLanguage());
                firstIngredient = false;
            }*/
            completeName += ")";
        }

        return completeName;
    }

    public static Sandwich addSandwich(Sandwich newSandwich) {
        return sandwichRepository.add(newSandwich);
    }

    public static void deleteSandwich(Sandwich sandwich) {
        sandwichRepository.delete(sandwich.getId());
    }
}
