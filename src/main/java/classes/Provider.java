package classes;

public class Provider {
    private String name;
    private String contactInfo;
    private String location;   // NEW

    public Provider(String name, String contactInfo, String location) {
        this.name = name;
        this.contactInfo = contactInfo;
        this.location = location;
    }

    // If you still need the old 2-arg constructor, keep it too:
    public Provider(String name, String contactInfo) {
        this(name, contactInfo, null);
    }

    // Getters & setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    // method
    public String getInfo() {
        return "Provider: " + name + " | Contact: " + contactInfo +
                (location != null ? " | Location: " + location : "");
    }
}
