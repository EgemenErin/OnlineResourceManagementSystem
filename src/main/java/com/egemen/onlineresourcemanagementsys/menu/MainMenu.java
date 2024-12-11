package com.egemen.onlineresourcemanagementsys.menu;

import com.egemen.onlineresourcemanagementsys.logic.AuthLogic;
import com.egemen.onlineresourcemanagementsys.logic.ResourceLogic;
import com.egemen.onlineresourcemanagementsys.logic.UserLogic;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class MainMenu {
    private final AuthLogic authLogic;
    private final UserLogic userLogic;
    private final ResourceLogic resourceLogic;
    private final Scanner scanner = new Scanner(System.in);

    // Constructor for dependency injection
    public MainMenu(AuthLogic authLogic, UserLogic userLogic, ResourceLogic resourceLogic) {
        this.authLogic = authLogic;
        this.userLogic = userLogic;
        this.resourceLogic = resourceLogic;
    }

    /**
     * Displays the main menu and handles user input.
     */
    public void display() {
        while (true) {
            printMenuHeader();
            printMenuOptions();
            System.out.print("👉 Select an option: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> login();
                    case 2 -> register();
                    case 3 -> {
                        System.out.println("👋 Quitting the application. Goodbye!");
                        System.exit(0);
                        return;
                    }
                    default -> System.out.println("❌ Invalid option. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input. Please enter a number between 1 and 3.");
            }
        }
    }

    /**
     * Handles the user login process.
     */
    private void login() {
        System.out.println("\n🔐 --- User Login --- 🔐");
        System.out.print("👤 Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("🔑 Password: ");
        String password = scanner.nextLine().trim();

        var user = authLogic.loginUser(username, password);
        if (user != null) {
            System.out.println("🎉 Access granted! Welcome, " + username + "!");
            // Navigate to the user dashboard
            new Dashboard(resourceLogic, userLogic, user).display();
        } else {
            System.out.println("⚠️ Login failed. Please check your credentials and try again.");
        }
    }

    /**
     * Handles the user registration process.
     */
    private void register() {
        System.out.println("\n📝 --- User Registration --- 📝");
        System.out.print("👤 Choose a Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("🔑 Choose a Password: ");
        String password = scanner.nextLine().trim();

        try {
            userLogic.registerUser(username, password);
            System.out.println("✅ Registration successful! You can now log in with your credentials.");
        } catch (Exception e) {
            System.out.println("⚠️ Registration failed. " + e.getMessage());
        }
    }

    /**
     * Prints the main menu header with decorative separators and emojis.
     */
    private void printMenuHeader() {
        System.out.println("\n✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨");
        System.out.println("         🚀 Welcome to the Online Resource Manager 🚀");
        System.out.println("✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨");
    }

    /**
     * Prints the main menu options with emojis.
     */
    private void printMenuOptions() {
        System.out.println("1️⃣. Login");
        System.out.println("2️⃣. Register");
        System.out.println("3️⃣. Quit");
    }
}
