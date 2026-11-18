package be.sos.service;

import be.sos.enumeration.Language;
import be.sos.model.Order;
import be.sos.model.Person;
import be.sos.model.SandwichWithOptions;
import be.sos.repository.OrderFileRepository;
import be.sos.repository.OrderRepository;
import be.sos.repository.StatSandwich;
import be.sos.utils.CommonData;
import be.sos.utils.CurrentUser;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class OrderService {
    private static final OrderRepository orderRepository = OrderFileRepository.getInstance();
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    public static boolean treatOrder(Order order) {
        Order createOrder = orderRepository.add(order);
        return createOrder.getOrderer() != null;
    }

    public static void deleteOrder(Order order) {
        if(order.getDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Delete order impossible: You cannot remove a order in the past...");
        }
        if(!CurrentUser.getUser().equals(order.getOrderer())) {
            throw new IllegalArgumentException("Delete order impossible: You can only remove you order...");
        }
        orderRepository.delete(order.getId());
    }


    public static Map<Integer, Double> getTotalPricePerMonth(int year) {
        return orderRepository.findAll().stream()
                .filter(o -> o.getDate().getYear() == year)
                .collect(Collectors.groupingBy(
                        o -> o.getDate().getMonthValue(),
                        Collectors.summingDouble(o ->
                                o.getSandwiches().stream()
                                        .mapToDouble(SandwichWithOptions::getPrice)
                                        .sum())
                ));
    }



    public static List<StatSandwich> getSandwichesStatistics() {
        // Count the number of occurences for each sandwich
        Map<SandwichWithOptions, Long> counting = orderRepository.findAll().stream()
                .flatMap(order -> order.getSandwiches().stream())
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));

        // Calculate the total number of sandwiches orderer
        long totalSandwiches = counting.values().stream()
                .mapToLong(Long::longValue)
                .sum();


        // Create the statistics and sort by popularity
        return counting.entrySet().stream()
                .sorted(Map.Entry.<SandwichWithOptions, Long>comparingByValue().reversed())
                .map(entry -> {

                    SandwichWithOptions sandwich = entry.getKey();
                    long quantity = entry.getValue();

                    double percentage = (quantity * 100.0) / totalSandwiches;

                    return new StatSandwich(sandwich, quantity, percentage);
                })
                .collect(Collectors.toList());
    }

    public static void createReport(LocalDate reportDate) {
        Language reportLanguage = Language.NL;
        DateTimeFormatter dtfReportFile = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try(BufferedWriter writer = Files.newBufferedWriter(
                Path.of(CommonData.getPathData() + "/report " + reportDate.format(dtfReportFile) + ".txt"))) {
            // Print title
            String title = "SANDWICH REPORT (Date : "+ reportDate.format(dtf) + ")";
            writer.write(title);
            writer.newLine();
            writer.write("-".repeat(title.length()));
            writer.newLine();

            int nbrSandwhiches = 0;
            double totalPrice = 0.0;
            int nbrSession = 0;

            long previousSessionId = -1;
            double sessionPrice = 0.0;

            for(Order order : orderRepository.findAll().stream()
                    .filter(o -> o.getDate().equals(reportDate))
                    .sorted(Comparator.comparing(o -> o.getSession().getId()))
                    .collect(Collectors.toList())) {

                long currentSessionId = order.getSession().getId();

                // Add a break when the session changes
                if (currentSessionId != previousSessionId) {
                    if (previousSessionId != -1) {
                        writer.newLine();
                        writer.write(String.format("Total session price : %.2f", sessionPrice));

                        writer.newLine();
                        writer.newLine();
                    }

                    // Reset the totla for the new session
                    sessionPrice = 0.0;

                    String titleSession = "SESSION" + ++nbrSession + "-" + order.getSession().getCourse().getTitle();
                    writer.write(titleSession);

                    writer.newLine();
                    writer.write("-".repeat(titleSession.length()));
                    writer.newLine();

                    previousSessionId = currentSessionId;

                    String line = String.format("%-25s  %-20s  %-20s  %-8s  %-10s  %-10s",
                            "Name", "Broodjes", "Soorten sandwiches", "Groenten", "Bruin/Wit", "Price");

                    writer.write(line);
                    writer.newLine();

                    line = String.format("%-25s  %-20s  %-20s  %-8s  %-10s  %-10s",
                            "----", "--------", "------------------", "--------", "---------", "-----");
                    writer.write(line);
                    writer.newLine();
                }

                for (SandwichWithOptions sandwich : order.getSandwiches()) {
                    String reportLine = String.format("%-25s  %-20s  %-20s  %-8s  %-10s  %-10s",
                            (order.getOrderer().getFirstName() + " " + order.getOrderer().getLastName()).substring(0,25),
                            sandwich.getName(reportLanguage).substring(0,20),
                            sandwich.getTypeSandwich().getName(reportLanguage).substring(0,20),
                            sandwich.isCrudities() ? "Jaa" : "Nee",
                            sandwich.getBreadType().getDisplayName(reportLanguage).substring(0,10),
                            sandwich.getPrice());

                    writer.write(reportLine);
                    writer.newLine();

                    nbrSandwhiches++;

                    // Add the price to both totals
                    sessionPrice += sandwich.getPrice();
                    totalPrice += sandwich.getPrice();
                }
            }

            // Display the total price of the last session...
            if (previousSessionId != -1) {
                writer.newLine();
                writer.write(String.format("Total session price : %.2f", sessionPrice));
                writer.newLine();
            }

            writer.newLine();
            writer.write(String.format("-".repeat(80)));
            writer.newLine();
            // Footer
            writer.write("Aantal bestelde sandwiches : " + nbrSandwhiches);
            writer.newLine();

            writer.write(String.format("Total prijs : %.2f", totalPrice));
            writer.newLine();

        } catch (Exception e) {
            throw new RuntimeException("Unable to write report", e);
        }
    }

    public static List<Order> findAllOrderByOrdererAndDate(Person orderer, LocalDate date) {
        return orderRepository.findAll().stream()
                .filter(order -> order.getOrderer().equals(orderer) && order.getDate().equals(date))
                .collect(Collectors.toList());
    }

}
