package be.sos.parser;

import be.sos.enumeration.Language;
import be.sos.model.Ingredient;
import be.sos.model.Sandwich;
import be.sos.model.TypeSandwich;
import be.sos.repository.*;

import java.util.*;
import java.util.stream.Collectors;

public class SandwichParser implements Parser<Sandwich> {

    // csv format : id;names;typeSandwich;price;cruditiesOptionAvailable;visibleInList;ingredients

    private static TypeSandwichRepository typeSandwichFileRepository = TypeSandwichFileRepository.getInstance();
    private static NamesParser namesParser = new NamesParser();



    public static List<Ingredient> parseIngredients(String input) {
        List<Ingredient> ingredients = new ArrayList<>();
        if (input == null || input.isBlank()) return ingredients;

        Language[] langues = Language.values(); // Prend l'ordre exact de l'enum

        for (String item : input.split(",")) {
            /*String[] parts = item.split("/");
            Map<Language, String> names = new EnumMap<>(Language.class);

            int count = Math.min(parts.length, langues.length);
            for (int i = 0; i < count; i++) {
                String translation = parts[i].trim();
                if (!translation.isEmpty()) {
                    names.put(langues[i], translation);
                }
            }*/
            Map<Language, String> names = namesParser.parse(item);
            ingredients.add(new Ingredient(names));
        }
        return ingredients;
    }


    @Override
    public Sandwich parse(String line)  {
        int idxCol = 0;
        String [] parts = line.split(";", -1);

        if (parts.length < 6) {
            throw new IllegalArgumentException("Format de ligne invalide pour Sandwich : " + line);
        }

        Integer id = Integer.parseInt(parts[idxCol++].trim());

        /*Map<Language, String> names = new EnumMap<>(Language.class);
        for (Language lang : Language.values()) {
            names.put(lang, parts[idxCol++].trim());
        }*/

        Map<Language, String> names = namesParser.parse(parts[idxCol++].trim());

        int typeSandwichId = Integer.parseInt(parts[idxCol++].trim());
        TypeSandwich typeSandwich = typeSandwichFileRepository.findById(typeSandwichId)
                .orElseThrow(() -> new IllegalArgumentException("Type de sandwich introuvable pour l'ID : " + typeSandwichId));

        double price = Double.parseDouble(parts[idxCol++].trim());
        boolean cruditiesOptionAvailable = Boolean.parseBoolean(parts[idxCol++].trim());
        List<Ingredient> ingredients = null;
        String lstIngredients = parts[idxCol++].trim();
        if (!lstIngredients.isEmpty()) {
            ingredients = parseIngredients(lstIngredients);
        }
        return new Sandwich(id, names, typeSandwich, price, cruditiesOptionAvailable, ingredients);
    }

    @Override
    public String format(Sandwich s) {
        StringJoiner sj = new StringJoiner(";");

        sj.add(String.valueOf(s.getId()));

        /*for (Language lang : Language.values()) {
            sj.add(s.getName(lang));
        }*/

        /*StringJoiner sjNames = new StringJoiner("/");
        for (Language lang : Language.values()) {
            sjNames.add(s.getName(lang));
        }
        sj.add(sjNames.toString());   */

        sj.add(namesParser.format(s.getNames()));
        sj.add(String.valueOf(s.getTypeSandwich().getId()));
        sj.add(String.valueOf(s.getPrice()));
        sj.add(String.valueOf(s.isCruditiesOptionAvailable()));

        String ingredientsList;
        if(s.getIngredients() != null) {
            StringJoiner sjIngredients = new StringJoiner(",");

            for(Ingredient ingredient : s.getIngredients()) {
                StringJoiner sjIngredient = new StringJoiner("/");
                for (Language lang : Language.values()) {
                    sjIngredient.add(ingredient.getName(lang));
                }
                sjIngredients.add(sjIngredient.toString());
            }
            ingredientsList = sjIngredients.toString();
        }
        else ingredientsList = "";
        sj.add(ingredientsList);

        return sj.toString();
    }
}
