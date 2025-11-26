package classes;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Load all resources from PostgreSQL via repository
        List<EducationalResource> allResources = EducationalResourceRepository.getAllResources();

        System.out.println("Welcome to Learn4Immigrants!");
        System.out.println("1. Guest User");
        System.out.println("2. Login");
        System.out.println("3. Create an Account");
        System.out.print("Choose an option: ");

        int option = Integer.parseInt(scanner.nextLine());

        if (option == 1) {
            // ----- GUEST USER FLOW -----
            User guest = createGuestUser(scanner);
            showGuestMenu(guest, allResources, scanner);

        } else if (option == 2) {
            // ----- LOGIN FLOW -----
            User loggedIn = loginUser(scanner);
            if (loggedIn != null) {
                showUserMenu(loggedIn, allResources, scanner);
            }

        } else if (option == 3) {
            createAccount(scanner);
        }

        scanner.close();
    }

    // ---------------- HELPER METHODS ----------------

    private static User createGuestUser(Scanner scanner) {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        System.out.print("Enter your age: ");
        int age = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter your location (e.g. 'North Seattle, WA'): ");
        String location = scanner.nextLine();

        // Immigration category selection
        ImmigrationCategory[] categories = ImmigrationCategory.values();
        System.out.println("Choose your immigration category:");
        for (int i = 0; i < categories.length; i++) {
            System.out.println((i + 1) + ". " + categories[i]);
        }
        System.out.print("Enter number: ");
        int catChoice = Integer.parseInt(scanner.nextLine());
        ImmigrationCategory chosenCategory =
                categories[Math.max(0, Math.min(catChoice - 1, categories.length - 1))];

        // guest has no password
        return new User(name, email, age, chosenCategory, location);
    }

    private static User loginUser(Scanner scanner) {
        System.out.println("Enter your email: ");
        String inputEmail = scanner.nextLine().trim();

        System.out.println("Enter your password: ");
        String inputPassword = scanner.nextLine().trim();

        String name = null;
        String emailC = null;
        int age = 0;
        String immigrationCategory = null;
        String location = null;
        String passwordC = null;

        boolean found = false;

        try {
            java.nio.file.Path usersPath = java.nio.file.Paths.get("Users");

            if (!java.nio.file.Files.exists(usersPath)) {
                System.out.println("No users registered yet. Please create an account first.");
                return null;
            }

            List<String> lines = java.nio.file.Files.readAllLines(usersPath);

            for (String line : lines) {
                if (line.trim().isEmpty() || line.startsWith("ID|")) continue;

                String[] parts = line.split("\\|");
                if (parts.length < 7) continue;

                String fileEmail = parts[2].trim();
                String filePassword = parts[6].trim();

                if (fileEmail.equalsIgnoreCase(inputEmail) && filePassword.equals(inputPassword)) {
                    name = parts[1];
                    emailC = fileEmail;
                    age = Integer.parseInt(parts[3]);
                    immigrationCategory = parts[4];
                    location = parts[5];
                    passwordC = filePassword;
                    found = true;
                    break;
                }
            }

        } catch (Exception e) {
            System.out.println("Error reading users file: " + e.getMessage());
        }

        if (!found) {
            System.out.println("Invalid email or password, or user not found.");
            return null;
        }

        System.out.println("\nWelcome back, " + name + "!");
        return new User(
                name,
                emailC,
                age,
                ImmigrationCategory.valueOf(immigrationCategory),
                location,
                passwordC
        );
    }

    private static void createAccount(Scanner scanner) {
        System.out.println("Enter your name: ");
        String name = scanner.nextLine();

        System.out.println("Enter your email: ");
        String email = scanner.nextLine();

        System.out.println("Enter your age: ");
        int age = Integer.parseInt(scanner.nextLine());

        ImmigrationCategory[] categories = ImmigrationCategory.values();
        System.out.println("Choose your immigration category:");
        for (int i = 0; i < categories.length; i++) {
            System.out.println((i + 1) + ". " + categories[i]);
        }
        System.out.print("Enter number: ");
        int catChoice = Integer.parseInt(scanner.nextLine());
        ImmigrationCategory chosenCategory =
                categories[Math.max(0, Math.min(catChoice - 1, categories.length - 1))];

        System.out.println("Enter your location: ");
        String location = scanner.nextLine();

        System.out.println("Set up a password: ");
        String password = scanner.nextLine();

        User newUser = new User(name, email, age, chosenCategory, location, password);

        System.out.println("Account created successfully");

        // Save user to Users file with unique auto-incremented ID
        try {
            java.nio.file.Path usersPath = java.nio.file.Paths.get("Users");
            int lastId = 0;

            if (java.nio.file.Files.exists(usersPath)) {
                List<String> lines = java.nio.file.Files.readAllLines(usersPath);
                for (String line : lines) {
                    if (line.trim().isEmpty() || line.startsWith("ID|")) continue;
                    String[] parts = line.split("\\|");
                    if (parts.length > 0) {
                        try {
                            int id = Integer.parseInt(parts[0]);
                            if (id > lastId) lastId = id;
                        } catch (NumberFormatException ignored) {}
                    }
                }
            }

            int newId = lastId + 1;
            FileWriter fileWriter = new FileWriter("Users", true);
            BufferedWriter bufWriter = new BufferedWriter(fileWriter);

            bufWriter.write("\n" + newId + "|" + name + "|" + email + "|" +
                    age + "|" + chosenCategory + "|" + location + "|" + password);
            bufWriter.close();
            System.out.println("User info saved! Your ID is: " + newId);

        } catch (Exception e) {
            System.out.println("Error saving user: " + e.getMessage());
        }
    }

    // -------- LOGGED-IN MENU --------

    private static void showUserMenu(User user, List<EducationalResource> allResources, Scanner scanner) {
        boolean running = true;

        while (running) {
            System.out.println("\n=== Main Menu ===");
            System.out.println("Logged in as: " + user.getName() + " (" + user.getLocation() + ")");
            System.out.println("1. View recommended resources near you");
            System.out.println("2. View all eligible resources");
            System.out.println("3. View saved resources");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");

            String choiceStr = scanner.nextLine();
            int choice;
            try {
                choice = Integer.parseInt(choiceStr);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }

            switch (choice) {
                case 1:
                    showRecommendedResources(user, allResources, scanner);
                    break;
                case 2:
                    showAllEligibleResources(user, allResources, scanner);
                    break;
                case 3:
                    showSavedResources(user);
                    break;
                case 4:
                    System.out.println("Logging out...");
                    running = false;
                    break;
                default:
                    System.out.println("Unknown option, please try again.");
            }
        }
    }

    // -------- GUEST MENU --------

    private static void showGuestMenu(User guest, List<EducationalResource> allResources, Scanner scanner) {
        boolean running = true;

        while (running) {
            System.out.println("\n=== Guest Mode ===");
            System.out.println("Browsing as: " + guest.getName() + " (" + guest.getLocation() + ")");
            System.out.println("1. View recommended resources near you");
            System.out.println("2. View all eligible resources");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            String choiceStr = scanner.nextLine();
            int choice;
            try {
                choice = Integer.parseInt(choiceStr);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }

            switch (choice) {
                case 1:
                    showGuestRecommendedResources(guest, allResources);
                    break;
                case 2:
                    showGuestAllEligibleResources(guest, allResources);
                    break;
                case 3:
                    System.out.println("Exiting guest mode...");
                    running = false;
                    break;
                default:
                    System.out.println("Unknown option, please try again.");
            }
        }
    }

    // -------- RESOURCE VIEWS --------

    private static void showGuestAllEligibleResources(User user, List<EducationalResource> allResources) {
        List<EducationalResource> eligible =
                EducationalResource.filterByEligibility(allResources, user);

        if (eligible.isEmpty()) {
            System.out.println("No resources available for your profile.");
            return;
        }

        System.out.println("\nAll eligible resources (guest view):");
        for (EducationalResource r : eligible) {
            System.out.println("-----------------------------------------");
            System.out.println(r.getSummary());
        }
    }

    private static void showGuestRecommendedResources(User user, List<EducationalResource> allResources) {
        List<EducationalResource> eligible =
                EducationalResource.filterByEligibility(allResources, user);

        List<EducationalResource> nearby =
                EducationalResource.filterByLocation(eligible, user.getLocation());

        List<EducationalResource> toShow = nearby.isEmpty() ? eligible : nearby;

        if (toShow.isEmpty()) {
            System.out.println("No resources available for your profile.");
            return;
        }

        System.out.println("\nRecommended resources near you (guest view):");
        for (EducationalResource r : toShow) {
            System.out.println("-----------------------------------------");
            System.out.println(r.getSummary());
        }
    }

    private static void showRecommendedResources(User user, List<EducationalResource> allResources, Scanner scanner) {
        List<EducationalResource> eligible =
                EducationalResource.filterByEligibility(allResources, user);

        List<EducationalResource> nearby =
                EducationalResource.filterByLocation(eligible, user.getLocation());

        List<EducationalResource> toShow = nearby.isEmpty() ? eligible : nearby;

        if (toShow.isEmpty()) {
            System.out.println("No resources available for your profile.");
            return;
        }

        System.out.println("\nRecommended resources near you:");
        for (int i = 0; i < toShow.size(); i++) {
            System.out.println("[" + (i + 1) + "]");
            System.out.println(toShow.get(i).getSummary());
        }

        System.out.println("Enter the number of a resource to save it, or 0 to go back:");
        String input = scanner.nextLine();
        try {
            int idx = Integer.parseInt(input);
            if (idx > 0 && idx <= toShow.size()) {
                user.saveResource(toShow.get(idx - 1));
                System.out.println("Resource saved!");
            }
        } catch (NumberFormatException ignored) {}
    }

    private static void showAllEligibleResources(User user, List<EducationalResource> allResources, Scanner scanner) {
        List<EducationalResource> eligible =
                EducationalResource.filterByEligibility(allResources, user);

        if (eligible.isEmpty()) {
            System.out.println("No resources available for your profile.");
            return;
        }

        System.out.println("\nAll eligible resources:");
        for (int i = 0; i < eligible.size(); i++) {
            System.out.println("[" + (i + 1) + "]");
            System.out.println(eligible.get(i).getSummary());
        }

        System.out.println("Enter the number of a resource to save it, or 0 to go back:");
        String input = scanner.nextLine();
        try {
            int idx = Integer.parseInt(input);
            if (idx > 0 && idx <= eligible.size()) {
                user.saveResource(eligible.get(idx - 1));
                System.out.println("Resource saved!");
            }
        } catch (NumberFormatException ignored) {}
    }

    private static void showSavedResources(User user) {
        List<EducationalResource> saved = user.viewSavedResources();
        if (saved.isEmpty()) {
            System.out.println("You haven't saved any resources yet.");
            return;
        }

        System.out.println("\nYour saved resources:");
        for (EducationalResource r : saved) {
            System.out.println("-----------------------------------------");
            System.out.println(r.getSummary());
        }
    }
}
