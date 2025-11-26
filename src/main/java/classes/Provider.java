package classes;

public class Provider {
    private String name;
    private String contactInfo;
    private String location;

    public Provider(String name, String contactInfo, String location) {
        this.name = name;
        this.contactInfo = contactInfo;
        this.location = location;
    }

    // optional 2-arg constructor if needed elsewhere
    public Provider(String name, String contactInfo) {
        this(name, contactInfo, null);
    }

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

    public String getInfo() {
        return "Provider: " + name +
                " | Contact: " + contactInfo +
                (location != null ? " | Location: " + location : "");
    }
}
