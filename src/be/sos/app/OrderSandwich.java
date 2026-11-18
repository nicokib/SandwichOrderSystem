package be.sos.app;

import be.sos.StringUtils;
import be.sos.enumeration.BreadType;
import be.sos.model.*;
import be.sos.service.OrderService;
import be.sos.service.SandwichService;
import be.sos.utils.CurrentUser;
import be.sos.utils.I18n;
import be.sos.utils.ScannerUtils;
import be.sos.utils.TextFormatter;

import javax.crypto.spec.PSource;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class OrderSandwich {
    private static List<Sandwich>lstSandwich = new ArrayList<>();
    private static List<SandwichWithOptions> sandwichesOrder = new ArrayList<>();


    public static void menu() {
        Person user = CurrentUser.getUser();
        Session currentSession;
        // Si une seule session on prend celle la
        if(CurrentUser.getActiveSessions().size() == 1) {
            currentSession = selectCurrentSession(CurrentUser.getActiveSessions());
        }
        else
        {
            currentSession = CurrentUser.getActiveSessions().get(0);
        }


        int choiceSandwich;
        String remarks = "";

        List<Order> existingOrder = OrderService.findAllOrderByOrdererAndDate(user, LocalDate.now());
        if(!existingOrder.isEmpty()) {
            System.out.println(I18n.getMessage("msg.order.already.exists"));
            System.out.println();
            displayOrderInfos(existingOrder.get(0));

            if(!ScannerUtils.readYesNo(I18n.getMessage("question.delete.order"))) {
                OrderService.deleteOrder(existingOrder.get(0));
            }
        }

        do {
            String selectSandwichTitle = I18n.getMessage("title.select.sandwich");
            System.out.println(selectSandwichTitle);
            System.out.println("-".repeat(selectSandwichTitle.length()));

            loadSandwichMenu();
            System.out.println(I18n.getMessage("label.name") + user.getFirstName() + " " + user.getLastName());
            System.out.println(I18n.getMessage("label.training") + currentSession.getCourse().getTitle());
            displaySandwichMenu();

            choiceSandwich = ScannerUtils.readIntBetween(I18n.getMessage("label.choose.option"), 0, lstSandwich.size());
            if(choiceSandwich != 0) {
                boolean crudities = false;
                BreadType breadType = BreadType.WHITE;
                Sandwich selectSandwich =  lstSandwich.get(choiceSandwich-1);
                if (selectSandwich.isCruditiesOptionAvailable()) {
                    crudities = ScannerUtils.readYesNo(I18n.getMessage("label.crudities"));
                }
                else {
                    System.out.println(I18n.getMessage("crudities.not.available"));
                }

                System.out.println(I18n.getMessage("label.breadType"));
                BreadType[] breadTypes = BreadType.values();

                for (int i = 0; i < breadTypes.length; i++) {
                    System.out.println((i + 1) + ". " + breadTypes[i].getDisplayName(I18n.getCurrentLanguage()));
                }

                int choiceBreadType = ScannerUtils.readIntBetween(I18n.getMessage("select.breadType"), 1, breadTypes.length);
                breadType = breadTypes[choiceBreadType - 1];
                sandwichesOrder.add(new SandwichWithOptions(selectSandwich, crudities, breadType));
            }

        } while (choiceSandwich != 0 && sandwichesOrder.size() < 2);

        if(sandwichesOrder.size() ==0) {
            System.out.println(I18n.getMessage("no.order"));
            return;
        }
        remarks = ScannerUtils.readText(I18n.getMessage("label.remarks"));


        // Confirm order ?
        Order newOrder = new Order(null, LocalDate.now(), user, currentSession, sandwichesOrder, remarks);

        System.out.println();
        String labelConfirmOrder = I18n.getMessage("title.confirm.order");
        System.out.println(labelConfirmOrder);
        System.out.println("-".repeat(labelConfirmOrder.length()));

        displayOrderInfos(newOrder);

        boolean orderConfirmed = ScannerUtils.readYesNo(I18n.getMessage("confirm.order"));

        if(orderConfirmed) {

            if(OrderService.treatOrder(newOrder)){
                System.out.println(I18n.getMessage("successfull.order") );
            }

        }
    }


    private static Session selectCurrentSession(List<Session> lstSession){
        System.out.println(I18n.getMessage("msg.different.course.same.time"));
        System.out.println(I18n.getMessage("question.select.session.today"));
        int nbrsession = 0;
        for(Session session : lstSession) {
            System.out.println(++nbrsession + session.getCourse().getTitle());
        }
        int numSession = ScannerUtils.readIntBetween(I18n.getMessage("label.select.session"), 1, lstSession.size());
        return lstSession.get(numSession - 1);
    }


    private static void displayOrderInfos(Order order) {
        System.out.println(I18n.getMessage("label.name") + order.getOrderer().getFirstName() + " " + order.getOrderer().getLastName());
        System.out.println(I18n.getMessage("label.training") + order.getSession().getCourse().getTitle());
        System.out.println(I18n.getMessage("label.nbrSandwiches") + order.getSandwiches().size());
        int numSandwiches = 0;
        for(SandwichWithOptions sandwich : order.getSandwiches()) {
            System.out.println(I18n.getMessage("sandwich") + (++numSandwiches) + " : " + sandwich.getName(I18n.getCurrentLanguage()) + " "
                    +  (sandwich.isCrudities() ? I18n.getMessage("with.crudities") : I18n.getMessage("without.crudities")) + " "
                    + sandwich.getBreadType().getDisplayName(I18n.getCurrentLanguage()) + " "
                    + I18n.getMessage("label.price") + sandwich.getPrice() + " €");
        }
        System.out.println(I18n.getMessage("label.remarks") + order.getRemarks());
        System.out.println(I18n.getMessage("label.total.price") + order.getTotalPrice());
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
                    System.out.printf("%-65s %6.2f €%n", StringUtils.capitalize(nameLines.get(i)), sandwich.getPrice());
                } else {
                    System.out.println(nameLines.get(i));
                }
            }
        }
        System.out.println("0. " + I18n.getMessage("terminate.order"));
    }
}
