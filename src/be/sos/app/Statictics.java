package be.sos.app;

import be.sos.service.OrderService;
import be.sos.utils.I18n;
import be.sos.utils.ScannerUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Map;

public class Statictics {

    public static void menu() {
        int choice;
        do {
            SandwichOrderSystem.displayTitle();
            String title = I18n.getMessage("statistics.menu");
            System.out.println(title);
            System.out.println("-".repeat(title.length()));
            System.out.println("1. " + I18n.getMessage("calculate.price.per.month"));
            System.out.println("2. " + I18n.getMessage("show.popular.sandwiches"));
            System.out.println("3. " + I18n.getMessage("print.report"));
            System.out.println("0. " + I18n.getMessage("quit"));
            choice = ScannerUtils.readIntBetween(I18n.getMessage("label.choose.option"), 0, 3);
            switch (choice) {
                case 1:
                    calculatePricePerMonth();
                    break;
                case 2:
                    showPopulareSandwiches();
                    break;
                case 3:
                    printReport();
                    break;
            }
            System.out.println();
        } while (choice != 0);
    }

    public static void calculatePricePerMonth() {
        int year = ScannerUtils.readIntBetween(I18n.getMessage("label.enter.year"), 0, 9999);
        Map<Integer, Double> totalPricePerMonth = OrderService.getTotalPricePerMonth(year);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM", Locale.FRENCH);

        totalPricePerMonth.forEach((month, totalPrice) -> {
            String strMonth = LocalDate.of(year, month.intValue(), 1)
                    .getMonth()
                    .getDisplayName(TextStyle.FULL, Locale.FRENCH);
            System.out.println("  " + strMonth + ", " + I18n.getMessage("label.total.price") + totalPrice);
        });
    }


    public static void showPopulareSandwiches() {
        System.out.println(I18n.getMessage("list.sandwich.popular"));
        OrderService.getSandwichesStatistics().forEach(s -> System.out.println("  - " + s));
    }

    public static void printReport() {
        LocalDate reportDate = ScannerUtils.readDate(I18n.getMessage("label.enter.report.date"), true);
        OrderService.createReport(reportDate);
    }
}
