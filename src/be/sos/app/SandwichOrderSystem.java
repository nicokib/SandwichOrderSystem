package be.sos.app;

import be.sos.enumeration.PersonRole;
import be.sos.model.Person;
import be.sos.service.PersonService;
import be.sos.utils.CurrentUser;
import be.sos.utils.I18n;
import be.sos.utils.ScannerUtils;

import java.util.Optional;

public class SandwichOrderSystem {
    public static void main(String[] args) {

        displayTitle();
        encodeCredential();

        Person user = CurrentUser.getUser();
        switch (user.getRole()) {
            case PersonRole.STUDENT :
            case PersonRole.INSTRUCTOR :
                CurrentUser.initCurrentSession();
                if(CurrentUser.getActiveSessions().size() == 0) {
                    System.out.println(I18n.getMessage("session.active.notfound"));
                    System.out.println(I18n.getMessage("bye"));
                    System.exit(-1);
                }
                OrderSandwich.menu();
                break;
            case PersonRole.OFFICE_MANAGER :
                officeManagerMenu();
                break;
        }
    }


    public static void officeManagerMenu() {
        int choice;
        do {
            System.out.println("1. Statistics");
            System.out.println("2. Manage Sandwiches list");
            choice = ScannerUtils.readIntBetween(I18n.getMessage("label.choose.option"), 0, 2);
            switch (choice) {
                case 1:
                    Statictics.menu();
                    break;
                case 2:
                    ManageSandwiches.menu();
                    break;
            }
        }
        while (choice != 0);

    }

    public static void displayTitle() {
        int screenSize = 80;
        String title = I18n.getMessage("title");
        System.out.println("-".repeat(screenSize));
        System.out.printf("%" + ((screenSize - title.length()) / 2) + "s\n", title);
        System.out.println("-".repeat(screenSize));
    }

    public static void encodeCredential () {
        Optional<Person> optUser;
        int nbrConnectionTentative = 0;
        int maxConnectionTentative = 3;

        do {
            String email = ScannerUtils.readNonEmptyText("Email: ");
            String password = ScannerUtils.readPassword("Password: ");
            optUser = PersonService.validateCredentials(email, password);
            nbrConnectionTentative++;
        }
        while(optUser.isEmpty() && nbrConnectionTentative < maxConnectionTentative);
        if(optUser.isEmpty()) {
            System.out.println("Maximum number of login attempts reached. Terminate program.");
            System.exit(-1);
        }
        Person user = optUser.get();
        CurrentUser.setUser(user);
        I18n.setCurrentLanguage(user.getLanguage());
    }


}

