package be.sos.utils;

import be.sos.enumeration.Language;
import be.sos.enumeration.YesNo;

import java.io.Console;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Scanner;

public class ScannerUtils {

    // Single shared Scanner for the entire program
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Reads a string of characters (accepts spaces).
     */
    public static String readText(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    /**
     * Reads a non-empty text (loops if the user just presses Enter).
     */
    public static String readNonEmptyText(String prompt) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Error: input cannot be empty.");
        }
    }

    /**
     * Reads an integer (int) and loops until the input is valid.
     */
    public static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                int value = scanner.nextInt();
                scanner.nextLine(); // Clears the newline character from memory
                return value;
            } else {
                System.out.println("Error: please enter a valid integer.");
                scanner.nextLine(); // Discards the invalid input
            }
        }
    }

    /**
     * Reads an integer between a minimum and a maximum value.
     */
    public static int readIntBetween(String prompt, int min, int max) {
        while (true) {
            int value = readInt(prompt);
            if (value >= min && value <= max) {
                return value;
            }
            System.out.println("Error: the number must be between " + min + " and " + max + ".");
        }
    }

    /**
     * Reads a floating-point number (double) and loops until the input is valid.
     */
    public static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextDouble()) {
                double value = scanner.nextDouble();
                scanner.nextLine(); // Clears the newline character from memory
                return value;
            } else {
                System.out.println("Error: please enter a valid decimal number.");
                scanner.nextLine(); // Discards the invalid input
            }
        }
    }

    /**
     * Reads a Yes/No (Y/N) answer and returns a boolean (true/false).
     */
    public static boolean readYesNo(String prompt) {
        while (true) {
            System.out.print(prompt + " (Y/N): ");
            String answer = scanner.nextLine().trim().toLowerCase();
            if (answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("yes")) {
                return true;
            } else if (answer.equalsIgnoreCase("n") || answer.equalsIgnoreCase("no")) {
                return false;
            }
            System.out.println("Error: please answer with Y (Yes) or N (No).");
        }
    }

    /**
     * Reads a password without echoing characters if a system console is available.
     * Falls back to standard Scanner input when running inside an IDE.
     */
    public static String readPassword(String prompt) {
        Console console = System.console();
        String password;

        if (console == null) {
            System.out.println("Attention la console n'est pas disponible votre mot de passe s'affichera à l'écran...");
        }

        while (true) {
            if (console != null) {
                // Hides user input in a real terminal
                char[] passwordChars = console.readPassword("%s", prompt);
                password = (passwordChars != null) ? new String(passwordChars) : "";
            } else {
                // Fallback for IDEs (IntelliJ, Eclipse, VS Code) where System.console() is null
                System.out.print(prompt);
                password = scanner.nextLine();
            }

            if (!password.trim().isEmpty()) {
                return password;
            }
            System.out.println("Error: password cannot be empty.");
        }
    }


    /**
     * Reads a valid date in the format dd/MM/yyyy and loops until the input is valid.
     */
    public static LocalDate readDate(String prompt) {
        return readDate(prompt, false);
    }


    public static LocalDate readDate(String prompt, boolean emptyIsToday) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu")
                .withResolverStyle(ResolverStyle.STRICT);

        while (true) {
            System.out.print(prompt + " (dd/MM/yyyy): ");
            String input = scanner.nextLine().trim();
            if(emptyIsToday && input.isEmpty()) return LocalDate.now();
            try {
                return LocalDate.parse(input, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Error: please enter a valid date in dd/MM/yyyy format (e.g., 25/12/2026).");
            }
        }
    }
}