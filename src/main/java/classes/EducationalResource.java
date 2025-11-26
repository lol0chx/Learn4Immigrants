package classes;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    // Static methods for filtering, sorting, grouping, mapping, and counting using Streams

    public static List<EducationalResource> filterByEligibility(List<EducationalResource> resources, User user) {
        if (resources == null || user == null) {
            throw new IllegalArgumentException("Resources and User must not be null");
        }
        return resources.stream()
                .filter(r -> r.checkEligibility(user))
                .collect(Collectors.toList());
    }

    public static List<EducationalResource> sortByCategory(List<EducationalResource> resources) {
        if (resources == null) {
            throw new IllegalArgumentException("Resources must not be null");
        }
        return resources.stream()
                .sorted((r1, r2) -> r1.getResourceCategory().compareToIgnoreCase(r2.getResourceCategory()))
                .collect(Collectors.toList());
    }

    public static Map<Provider, List<EducationalResource>> groupByProvider(List<EducationalResource> resources) {
        if (resources == null) {
            throw new IllegalArgumentException("Resources must not be null");
        }
        return resources.stream()
                .collect(Collectors.groupingBy(EducationalResource::getProvider));
    }

    public static Map<String, List<EducationalResource>> groupByResourceCategory(List<EducationalResource> resources) {
        if (resources == null) {
            throw new IllegalArgumentException("Resources must not be null");
        }
        return resources.stream()
                .collect(Collectors.groupingBy(EducationalResource::getResourceCategory));
    }

    public static List<String> mapToSummaries(List<EducationalResource> resources) {
        if (resources == null) {
            throw new IllegalArgumentException("Resources must not be null");
        }
        return resources.stream()
                .map(EducationalResource::getSummary)
                .collect(Collectors.toList());
    }

    public static Map<String, Long> countByCategory(List<EducationalResource> resources) {
        if (resources == null) {
            throw new IllegalArgumentException("Resources must not be null");
        }
        return resources.stream()
                .collect(Collectors.groupingBy(EducationalResource::getResourceCategory, Collectors.counting()));
    }

    // Location-based filter using provider.location
    public static List<EducationalResource> filterByLocation(
            List<EducationalResource> resources,
            String locationKeyword
    ) {
        if (resources == null) {
            throw new IllegalArgumentException("Resources must not be null");
        }
        if (locationKeyword == null || locationKeyword.isBlank()) {
            return resources;
        }

        String keywordLower = locationKeyword.toLowerCase();

        return resources.stream()
                .filter(r -> r.getProvider() != null
                        && r.getProvider().getLocation() != null
                        && r.getProvider().getLocation().toLowerCase().contains(keywordLower))
                .collect(Collectors.toList());
    }

    public String getResourceCategory() {
        return resourceCategory;
    }

    public Provider getProvider() {
        return provider;
    }
}
