package classes;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get all resources from repository
        List<EducationalResource> allResources = EducationalResourceRepository.getAllResources();

        System.out.println("Welcome to Learn4Immigrants!");
        System.out.println("1. Guest User");
        System.out.println("2. Login ");
        System.out.println("3. Create an Account");
        System.out.print("Choose an option: ");
        int option = scanner.nextInt();
        scanner.nextLine(); // consume newline

        if (option == 1) {
            // New User flow
            System.out.print("Enter your name: ");
            String name = scanner.nextLine();
            System.out.print("Enter your email: ");
            String email = scanner.nextLine();
            System.out.print("Enter your age: ");
            int age = scanner.nextInt();
            scanner.nextLine(); // consume newline
            System.out.println("Enter your location: ");
            String location = scanner.nextLine();

            // Immigration category selection
            ImmigrationCategory[] categories = ImmigrationCategory.values();
            System.out.println("Choose your immigration category:");
            for (int i = 0; i < categories.length; i++) {
                System.out.println((i + 1) + ". " + categories[i]);
            }
            System.out.print("Enter number: ");
            int catChoice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            ImmigrationCategory chosenCategory = categories[Math.max(0, Math.min(catChoice - 1, categories.length - 1))];

            User newUser = new User(name, email, age, chosenCategory, location);

            // Show available resources for new user
            System.out.println("\nAvailable resources for you:");
            List<EducationalResource> available = EducationalResource.filterByEligibility(allResources, newUser);
            if (available.isEmpty()) {
                System.out.println("No resources available for your profile.");
            } else {
                for (EducationalResource r : available) {
                    System.out.println(r.getSummary());
                }
            }
        }
        else if(option == 2) {
            String emailC = "" ;
            String passwordC = "" ;
            String name ="";
            int age = 0;
            String immigrationCategory ="";
            String location ="";

            System.out.println("Enter your email: ");
            String email = scanner.nextLine();
            System.out.println("Enter you password: ");
            String password = scanner.nextLine();

            try {
                // Read all lines from Users file
                java.nio.file.Path usersPath = java.nio.file.Paths.get("Users");
                List<String> lines = java.nio.file.Files.readAllLines(usersPath);
                for (String line : lines) {
                    if (line.trim().isEmpty() || line.startsWith("ID|")) continue;
                    String[] parts = line.split("\\|");
                    if (parts.length > 0) {
                        try {
                             name = parts[1];
                             emailC  = parts[2];
                             age = Integer.parseInt(parts[3]);
                             immigrationCategory = parts[4];
                             location = parts[5];
                             passwordC = parts[6];
                        } catch (NumberFormatException ignored) {}
                    }
                }


            } catch (Exception e) {
                System.out.println( e.getMessage());
            }



            if (true) {
                User newUser = new User(name, emailC, age, ImmigrationCategory.valueOf(immigrationCategory), location, passwordC);
                System.out.println("\nAvailable resources for you:");
                List<EducationalResource> available = EducationalResource.filterByEligibility(allResources, newUser);
                if (available.isEmpty()) {
                    System.out.println("No resources available for your profile.");
                } else {
                    for (EducationalResource r : available) {
                        System.out.println(r.getSummary());
                    }
                }
            }


        }

        else if (option == 3) {
            
            System.out.println("Enter your name: ");
            String name = scanner.nextLine();

            System.out.println("Enter you email: ");
            String email = scanner.nextLine();
            System.out.println("Enter your age: ");
            int age = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Enter your immigration Status: ");
            ImmigrationCategory[] categories = ImmigrationCategory.values();
            System.out.println("Choose your immigration category:");

            for (int i = 0; i < categories.length; i++) {
                System.out.println((i + 1) + ". " + categories[i]);
            }
            System.out.print("Enter number: ");
            int catChoice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            System.out.println("Enter your location: ");
            String location = scanner.nextLine();

            System.out.println("Set up a password: ");
            String password = scanner.nextLine();

            ImmigrationCategory chosenCategory = categories[Math.max(0, Math.min(catChoice - 1, categories.length - 1))];

            User newUser = new User(name, email, age, chosenCategory, location, password);

            System.out.println("Account created successfully");

            // Save user to Users file with unique auto-incremented ID
            try {
                // Read all lines from Users file
                java.nio.file.Path usersPath = java.nio.file.Paths.get("Users");
                List<String> lines = java.nio.file.Files.readAllLines(usersPath);
                int lastId = 0;
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
                int newId = lastId + 1;
                FileWriter fileWriter = new FileWriter("Users", true);
                BufferedWriter bufWriter = new BufferedWriter(fileWriter);
                bufWriter.write("\n" + newId + "|" + name + "|" + email + "|" + age + "|" + chosenCategory + "|" + location + "|" + password);
                bufWriter.close();
                System.out.println("User info saved! Your ID is: " + newId);
            } catch (Exception e) {
                System.out.println("Error saving user: " + e.getMessage());
            }
        }

    }
}
