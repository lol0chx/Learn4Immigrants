package classes;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private String email;
    private int age;
    private ImmigrationCategory immigrationCategory;
    private List<EducationalResource> savedResources;
    private String location;

    public User(String name, String email, int age, ImmigrationCategory immigrationCategory) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.immigrationCategory = immigrationCategory;
        this.savedResources = new ArrayList<>();
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public ImmigrationCategory getImmigrationCategory() {
        return immigrationCategory;
    }

    public void setImmigrationCategory(ImmigrationCategory immigrationCategory) {
        this.immigrationCategory = immigrationCategory;
    }

    public List<EducationalResource> getSavedResources() {
        return savedResources;
    }

    public void setSavedResources(List<EducationalResource> savedResources) {
        this.savedResources = savedResources;
    }

    public List<EducationalResource> viewAvailableResources() {
        // This method would typically fetch resources from a repository or service
        // For now, returning an empty list as placeholder
        return new ArrayList<>();
    }

    public void saveResource(EducationalResource r) {
        if (r != null && !savedResources.contains(r)) {
            savedResources.add(r);
        }
    }

    public List<EducationalResource> viewSavedResources() {
        return new ArrayList<>(savedResources);
    }
}

