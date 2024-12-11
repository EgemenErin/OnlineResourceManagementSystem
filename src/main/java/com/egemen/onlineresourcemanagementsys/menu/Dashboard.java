package com.egemen.onlineresourcemanagementsys.menu;

import com.egemen.onlineresourcemanagementsys.entities.GameAccount;
import com.egemen.onlineresourcemanagementsys.entities.Resource;
import com.egemen.onlineresourcemanagementsys.entities.Subscription;
import com.egemen.onlineresourcemanagementsys.entities.User;
import com.egemen.onlineresourcemanagementsys.logic.ResourceLogic;
import com.egemen.onlineresourcemanagementsys.logic.UserLogic;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Dashboard {
    // Dependencies for ResourceLogic, UserLogic, and the current User
    private final ResourceLogic resourceLogic;
    private final UserLogic userLogic;
    private final User user;
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Constructor for injecting dependencies and the current user.
     *
     * @param resourceLogic Logic handling resource-related operations.
     * @param userLogic     Logic handling user-related operations.
     * @param user          The currently logged-in user.
     */
    public Dashboard(ResourceLogic resourceLogic, UserLogic userLogic, User user) {
        this.resourceLogic = resourceLogic;
        this.userLogic = userLogic;
        this.user = user;
    }

    /**
     * Displays the main dashboard menu and handles user interactions.
     */
    public void display() {
        while (true) {
            printDashboardHeader();
            printDashboardOptions();
            System.out.print("👉 Select an option: ");

            try {
                // Reading the user's choice
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> viewResources();   // View all resources
                    case 2 -> addResource();     // Add a new resource
                    case 3 -> editResource();    // Edit an existing resource
                    case 4 -> removeResource();  // Remove a resource
                    case 5 -> {
                        System.out.println("👋 You have been logged out successfully.");
                        return; // Exit the dashboard loop to log out
                    }
                    default -> System.out.println("❌ Invalid option. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input. Please enter a valid number.");
            }
        }
    }

    /**
     * Prints the dashboard header with decorative separators and emojis.
     */
    private void printDashboardHeader() {
        System.out.println("\n✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨");
        System.out.println("          🎮 Welcome to Your User Dashboard, " + user.getUsername() + " 🎮");
        System.out.println("✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨");
    }

    /**
     * Prints the dashboard menu options with emojis.
     */
    private void printDashboardOptions() {
        System.out.println("1️⃣. View Resources");
        System.out.println("2️⃣. Add Resource");
        System.out.println("3️⃣. Edit Resource");
        System.out.println("4️⃣. Remove Resource");
        System.out.println("5️⃣. Logout");
    }

    /**
     * Fetches and displays all resources associated with the current user.
     */
    private void viewResources() {
        // Grouping resources by their type (e.g., GameAccount, Subscription)
        Map<String, List<Resource>> groupedResources = resourceLogic.groupResourcesByType(user.getId());

        if (groupedResources.isEmpty()) {
            // Informing the user if no resources are found
            System.out.println("📂 No resources found for user: " + user.getUsername());
        } else {
            // Displaying all grouped resources
            System.out.println("\n📁 Resources for " + user.getUsername() + ":");
            groupedResources.forEach((type, resources) -> {
                System.out.println("\n🔹 --- " + type + " --- 🔹");
                resources.forEach(Resource::displayInfo);
            });
        }
    }

    /**
     * Displays the 'Add Resource' submenu and handles user input for adding resources.
     */
    private void addResource() {
        System.out.println("\n🆕 Adding a resource for " + user.getUsername());
        System.out.println("\n--- Add Resource Menu ---");
        System.out.println("1️⃣. Add a Game Account");
        System.out.println("2️⃣. Add a Subscription");
        System.out.print("👉 Select an option: ");

        try {
            // Reading the user's choice for resource type
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> addGameAccount();   // Add a new GameAccount
                case 2 -> addSubscription();  // Add a new Subscription
                default -> System.out.println("❌ Invalid option. Please try again.");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid input. Please enter a valid number.");
        }
    }

    /**
     * Handles the editing of an existing resource.
     */
    private void editResource() {
        System.out.println("\n✏️ --- Edit Resource ---");

        // Prompting the user to find the resource to edit
        Resource resource = findResource();
        if (resource == null) return;

        System.out.println("\n🔄 Editing Resource: " + resource.getName());

        if (resource instanceof GameAccount gameAccount) {
            // Updating attributes specific to a GameAccount
            updateResourceAttributes(gameAccount);
            gameAccount.setGamePlatform(promptInput("🎮 Enter new Platform (current: " + gameAccount.getGamePlatform() + "): ", gameAccount.getGamePlatform()));
        } else if (resource instanceof Subscription subscription) {
            // Updating attributes specific to a Subscription
            updateResourceAttributes(subscription);
            subscription.setSubscriptionType(promptInput("📰 Enter new Subscription Type (current: " + subscription.getSubscriptionType() + "): ", subscription.getSubscriptionType()));
        }

        // Saving the updated resource
        resourceLogic.saveResource(resource);
        System.out.println("✅ Resource updated successfully.");
    }

    /**
     * Handles the removal of an existing resource.
     */
    private void removeResource() {
        System.out.println("\n🗑️ --- Remove Resource ---");

        // Prompting the user to find the resource to remove
        Resource resource = findResource();
        if (resource == null) return;

        // Deleting the resource via the service
        resourceLogic.deleteResource(resource.getId());
        System.out.println("🗑️ Resource removed successfully.");
    }

    /**
     * Adds a new GameAccount resource based on user input.
     */
    private void addGameAccount() {
        System.out.println("\n🆕 --- Add Game Account ---");

        // Creating a new GameAccount object with user-provided details
        GameAccount gameAccount = new GameAccount(
                promptInput("🎮 Enter Game Name: "),
                promptInput("👤 Enter Username: "),
                promptInput("🔑 Enter Password: "),
                promptInput("🖥️ Enter Platform (e.g., PC, Xbox, PlayStation): ")
        );

        // Saving the resource and associating it with the current user
        resourceLogic.addResource(gameAccount, user);
        System.out.println("🎉 Game account added successfully!");
    }

    /**
     * Adds a new Subscription resource based on user input.
     */
    private void addSubscription() {
        System.out.println("\n🆕 --- Add Subscription ---");

        // Creating a new Subscription object with user-provided details
        Subscription subscription = new Subscription(
                promptInput("📺 Enter Subscription Name (e.g., Netflix, Spotify): "),
                promptInput("👤 Enter Username: "),
                promptInput("🔑 Enter Password: "),
                promptInput("📚 Enter Subscription Type (e.g., Streaming, Magazine): ")
        );

        // Saving the resource and associating it with the current user
        resourceLogic.addResource(subscription, user);
        System.out.println("🎉 Subscription added successfully!");
    }

    /**
     * Prompts the user to enter the name of a resource and attempts to find it.
     *
     * @return The found Resource object or null if not found.
     */
    private Resource findResource() {
        System.out.print("\n🔍 Enter the name of the resource: ");
        String resourceName = scanner.nextLine();
        Resource resource = resourceLogic.findResourceByNameAndUser(resourceName, user);
        if (resource == null) {
            System.out.println("⚠️ No resource found with the name: " + resourceName);
        }
        return resource;
    }

    /**
     * Updates the common attributes of a resource based on user input.
     *
     * @param resource The resource to be updated.
     */
    private void updateResourceAttributes(Resource resource) {
        resource.setName(promptInput("✏️ Enter new Name (current: " + resource.getName() + "): ", resource.getName()));
        resource.setUsername(promptInput("👤 Enter new Username (current: " + resource.getUsername() + "): ", resource.getUsername()));
        resource.setPassword(promptInput("🔑 Enter new Password (current: " + resource.getPassword() + "): ", resource.getPassword()));
    }

    /**
     * Prompts the user for input with a custom message.
     *
     * @param message The prompt message.
     * @return The user's input as a String.
     */
    private String promptInput(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    /**
     * Prompts the user for input with a custom message and a default value.
     * If the user provides no input, the current value is retained.
     *
     * @param message      The prompt message.
     * @param currentValue The current value of the attribute.
     * @return The new input or the current value if input is empty.
     */
    private String promptInput(String message, String currentValue) {
        System.out.print(message);
        String input = scanner.nextLine();
        return input.isEmpty() ? currentValue : input; // Retain current value if input is empty
    }
}
