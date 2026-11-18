package be.sos.app;

import be.sos.enumeration.Language;
import be.sos.model.Ingredient;
import be.sos.model.Sandwich;
import be.sos.model.TypeSandwich;
import be.sos.parser.NamesParser;
import be.sos.service.SandwichService;
import be.sos.service.TypeSandwichService;
import be.sos.utils.I18n;
import be.sos.utils.ScannerUtils;
import be.sos.utils.TextFormatter;

import java.util.*;
import java.util.stream.Collectors;

public class ManageSandwiches {
    private static List<Sandwich> lstSandwich = new ArrayList<>();

    public static void menu() {
        int choice;
        String title = I18n.getMessage("manage.sandwich.list");
        do {
            loadSandwichMenu();
            System.out.println(title);
            System.out.println("-".repeat(title.length()));
            System.out.println("1. " + I18n.getMessage("add.sandwich.list"));
            System.out.println("2. " + I18n.getMessage("delete.sandwich.list"));
            System.out.println("3. " + I18n.getMessage("display.sandwich.list"));
            choice = ScannerUtils.readIntBetween(I18n.getMessage("label.choose.option"), 0, 3);
            switch (choice) {
                case 1: addSandwichScreen(); break;
                case 2: deleteSandwichScreen(); break;
                case 3: displaySandwichScreen(); break;
            }
        }
        while (choice != 0);
    }

    private static void addSandwichScreen() {
        System.out.println();
        NamesParser namesParser = new NamesParser();
        String title = I18n.getMessage( I18n.getMessage("add.sandwich.list"));
        System.out.println(title);
        System.out.println("-".repeat(title.length()));

        //Select the type of Sandwich
        List<TypeSandwich> lstTypeSandwiches = TypeSandwichService.getAllTypeSandwiches();
        // Si le liste de type de sandwiches est vide...
        if(lstTypeSandwiches.size() == 0) {
            System.out.println(I18n.getMessage("error.add.sandwich.no.sandwich.type"));
            return;
        }

        // On affiche la liste des types de sandwiches
        int nbrTypeSandwiches = 0;
        for(TypeSandwich ts : lstTypeSandwiches) {
            System.out.println((++nbrTypeSandwiches) + ". " + ts.getName(I18n.getCurrentLanguage()));
        }

        int idxTypeSandwich = ScannerUtils.readIntBetween( I18n.getMessage("select.sandwich.type"), 1, nbrTypeSandwiches) - 1;
        TypeSandwich typeSandwich = lstTypeSandwiches.get(idxTypeSandwich);

        boolean valid;
        StringJoiner sj = new StringJoiner("/");
        for (Language lang : Language.values())  sj.add(lang.name());
        String strLanguages = sj.toString();

        String strNames;
        do {
            valid = true;
            strNames = ScannerUtils.readNonEmptyText(I18n.getMessage("lalel.sandwiches.names.lang", strLanguages));
            if(strNames.split("/").length != Language.values().length) {
                System.out.println(I18n.getMessage("error.fill.all.language", String.valueOf(Language.values().length)));
                valid = false;
            }
        } while(!valid);

        Map<Language, String> names = namesParser.parse(strNames);


        double price = ScannerUtils.readDouble(I18n.getMessage("label.price"));
        boolean cruditiesOptionAvailable = ScannerUtils.readYesNo(I18n.getMessage("label.crudities.available"));
        String ingredient;
        int numIngredient = 0;


        List<Ingredient> lstIngredients = new ArrayList<>();
        do {
            do {
                valid = true;
                ingredient = ScannerUtils.readText(I18n.getMessage("label.ingredient.num", String.valueOf(++numIngredient)));
                if(!ingredient.isBlank() && ingredient.split("/").length != Language.values().length) {
                    System.out.println(I18n.getMessage("error.fill.all.language", String.valueOf(Language.values().length)));
                    valid = false;
                }
            } while(!valid);

            if(!ingredient.isBlank()) {
                try {
                    lstIngredients.add(new Ingredient(namesParser.parse(ingredient)));
                }
                catch (Exception e) {
                    System.out.println(I18n.getMessage("error.ingredient.pasring"));
                    numIngredient--;
                }
            }
        }
        while(!ingredient.isBlank());

        Sandwich newSandwich = new Sandwich(null, names, typeSandwich, price, cruditiesOptionAvailable, lstIngredients);

        System.out.println(I18n.getMessage("confirm.add"));
        sandwichDetail(newSandwich);
        if(ScannerUtils.readYesNo(I18n.getMessage("question.confirm.add.sandwich"))) {
            SandwichService.addSandwich(newSandwich);
            System.out.println(I18n.getMessage("add.successfull"));
        }
        else
        {
            System.out.println(I18n.getMessage("add.canceled"));
        }
        System.out.println();
    }

    private static void deleteSandwichScreen() {
        System.out.println();
        String title = I18n.getMessage("delete.sandwich.list");
        System.out.println(title);
        System.out.println("-".repeat(title.length()));
        loadSandwichMenu();
        displaySandwichMenu();
        int numSandwich = ScannerUtils.readIntBetween(I18n.getMessage("label.select.sandwich.delete"), 1, lstSandwich.size());
        System.out.println(I18n.getMessage("confirm.deletion"));
        sandwichDetail(lstSandwich.get(numSandwich - 1));
        if(ScannerUtils.readYesNo(I18n.getMessage("question.confirm.deletion.sandwich"))) {
            SandwichService.deleteSandwich(lstSandwich.get(numSandwich - 1));
            System.out.println(I18n.getMessage("deletion.successfull"));
        }
        else
        {
            System.out.println(I18n.getMessage("deletion.canceled"));
        }
    }

    private static void displaySandwichScreen() {
        System.out.println();
        String title = I18n.getMessage("display.sandwich.list");
        System.out.println(title);
        System.out.println("-".repeat(title.length()));
        displaySandwichMenu();
    }

    public static void loadSandwichMenu() {
        lstSandwich = SandwichService.getAllSandwiches().stream()
                .sorted(Comparator.comparingInt(s ->  s.getTypeSandwich().getId()))
                .collect(Collectors.toList());
    }

    public static void displaySandwichMenu() {
        int numSandwich = 0;
        for(Sandwich sandwich : lstSandwich)
        {
            String sandwichEntry = (++numSandwich) + ". " + SandwichService.getCompleteSandwichName(sandwich);
            List<String> nameLines = TextFormatter.wrapText(sandwichEntry,60);
            for (int i = 0; i < nameLines.size(); i++) {
                if (i == 0) {
                    System.out.printf("%-65s %6.2f €%n", nameLines.get(i), sandwich.getPrice());
                } else {
                    System.out.println(nameLines.get(i));
                }
            }
        }
        System.out.println("0. " + I18n.getMessage("terminate.order"));
    }

    public static void sandwichDetail(Sandwich sandwich) {
        System.out.println(I18n.getMessage("label.sandwich.type") + sandwich.getTypeSandwich().getName(I18n.getCurrentLanguage()));
        for(Language lang : Language.values()) {
            System.out.println(I18n.getMessage("lalel.sandwich.name.lang", lang.name()) + sandwich.getName(lang));
        }
        System.out.println(I18n.getMessage("label.price") + sandwich.getPrice());
        System.out.println(I18n.getMessage("label.crudities.available") + sandwich.isCruditiesOptionAvailable());
        int numIngredient = 0;
        for(Ingredient i : sandwich.getIngredients()) {
            String strIngredient = "";
            StringJoiner sjIngredient = new StringJoiner("/");
            for(Language lang : Language.values())
                sjIngredient.add(i.getName(lang));
            System.out.println(I18n.getMessage("label.ingredient.num", String.valueOf (++numIngredient)) + sjIngredient.toString());
        }
    }
}


