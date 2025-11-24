package classes;


import java.util.ArrayList;
import java.util.List;

public class User {
    // Basic user attributes
    private String name;
    private int age;
    private ImmigrationCategory immigrationCategory;
    private List<String> savedResources; // Will store resource IDs or titles

    // Constructor
    public User(String name, int age, ImmigrationCategory immigrationCategory) {
        this.name = name;
        this.age = age;
        this.immigrationCategory = immigrationCategory;
        this.savedResources = new ArrayList<>();
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public ImmigrationCategory getImmigrationCategory() {
        return immigrationCategory;
    }

    public List<String> getSavedResources() {
        return new ArrayList<>(savedResources); // Return copy for encapsulation
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setImmigrationCategory(ImmigrationCategory immigrationCategory) {
        this.immigrationCategory = immigrationCategory;
    }

    // Methods for managing saved resources
    public void saveResource(String resourceId) {
        if (!savedResources.contains(resourceId)) {
            savedResources.add(resourceId);
        }
    }

    public void removeSavedResource(String resourceId) {
        savedResources.remove(resourceId);
    }

    // Display methods
    public void viewSavedResources() {
        System.out.println("Saved Resources for " + name + ":");
        if (savedResources.isEmpty()) {
            System.out.println("  No saved resources yet.");
        } else {
            for (String resource : savedResources) {
                System.out.println("  - " + resource);
            }
        }
    }


}