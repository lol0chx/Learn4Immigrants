package classes;

public class EducationalResource {
    private String title;
    private String description;
    private String url;
    private String resourceCategory;
    private Provider provider;
    private Requirements requirements;


    public EducationalResource(String title, String description, String url,
                               String resourceCategory, Provider provider,
                               Requirements requirements) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.resourceCategory = resourceCategory;
        this.provider = provider;
        this.requirements = requirements;
    }


    public boolean checkEligibility(User user) {
        if (requirements == null) return true;
        return requirements.isEligible(user);
    }


    public String getSummary() {
        return "Title: " + title + "\n" +
                "Description: " + description + "\n" +
                "Category: " + resourceCategory + "\n" +
                "Provider: " + (provider != null ? provider.getName() : "N/A") + "\n" +
                "URL: " + url + "\n";
    }
}