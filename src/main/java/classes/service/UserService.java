package classes.service;

import classes.ImmigrationCategory;
import classes.User;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class UserService {
    
    private static final String USERS_FILE = "Users";
    
    public User createGuestUser(String name, String email, int age, 
                               ImmigrationCategory category, String location) {
        return new User(name, email, age, category, location);
    }
    
    public User loginUser(String email, String password) {
        try {
            java.nio.file.Path usersPath = Paths.get(USERS_FILE);
            
            if (!Files.exists(usersPath)) {
                return null;
            }
            
            List<String> lines = Files.readAllLines(usersPath);
            
            for (String line : lines) {
                if (line.trim().isEmpty() || line.startsWith("ID|")) continue;
                
                String[] parts = line.split("\\|");
                if (parts.length < 7) continue;
                
                String fileEmail = parts[2].trim();
                String filePassword = parts[6].trim();
                
                if (fileEmail.equalsIgnoreCase(email) && filePassword.equals(password)) {
                    String name = parts[1];
                    int age = Integer.parseInt(parts[3]);
                    ImmigrationCategory immigrationCategory = ImmigrationCategory.valueOf(parts[4]);
                    String location = parts[5];
                    
                    return new User(name, email, age, immigrationCategory, location, password);
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading users file: " + e.getMessage());
        }
        
        return null;
    }
    
    public User createAccount(String name, String email, int age, 
                             ImmigrationCategory category, String location, String password) {
        try {
            java.nio.file.Path usersPath = Paths.get(USERS_FILE);
            int lastId = 0;
            
            if (Files.exists(usersPath)) {
                List<String> lines = Files.readAllLines(usersPath);
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
            FileWriter fileWriter = new FileWriter(USERS_FILE, true);
            BufferedWriter bufWriter = new BufferedWriter(fileWriter);
            
            bufWriter.write("\n" + newId + "|" + name + "|" + email + "|" +
                    age + "|" + category + "|" + location + "|" + password);
            bufWriter.close();
            
            return new User(name, email, age, category, location, password);
        } catch (Exception e) {
            System.err.println("Error saving user: " + e.getMessage());
            return null;
        }
    }
    
    public boolean emailExists(String email) {
        try {
            java.nio.file.Path usersPath = Paths.get(USERS_FILE);
            if (!Files.exists(usersPath)) {
                return false;
            }
            
            List<String> lines = Files.readAllLines(usersPath);
            for (String line : lines) {
                if (line.trim().isEmpty() || line.startsWith("ID|")) continue;
                String[] parts = line.split("\\|");
                if (parts.length >= 3) {
                    String fileEmail = parts[2].trim();
                    if (fileEmail.equalsIgnoreCase(email)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error checking email: " + e.getMessage());
        }
        return false;
    }
}

