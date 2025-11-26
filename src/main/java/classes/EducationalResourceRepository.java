package classes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EducationalResourceRepository {

    // 🔐 Update these if needed
    private static final String URL =
            "jdbc:postgresql://localhost:5432/learn4immigrants";
    private static final String USER = "ermi";      // your DB role
    private static final String PASSWORD = "";      // empty if you didn't set one

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static List<EducationalResource> getAllResources() {
        List<EducationalResource> resources = new ArrayList<>();

        String sql = """
                SELECT 
                    r.id,
                    r.title,
                    r.description,
                    r.url,
                    r.category,
                    r.min_age,
                    r.eligible_categories,
                    p.id AS provider_id,
                    p.name AS provider_name,
                    p.contact_info AS provider_contact,
                    p.location AS provider_location
                FROM educational_resources r
                JOIN providers p ON r.provider_id = p.id
                """;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                // Provider from DB
                Provider provider = new Provider(
                        rs.getString("provider_name"),
                        rs.getString("provider_contact"),
                        rs.getString("provider_location")
                );

                // Requirements from DB
                int minAge = rs.getInt("min_age");
                String eligibleCategoriesStr = rs.getString("eligible_categories");
                List<ImmigrationCategory> eligibleCategories =
                        parseEligibleCategories(eligibleCategoriesStr);
                Requirements requirements = new Requirements(minAge, eligibleCategories);

                // EducationalResource from DB
                EducationalResource resource = new EducationalResource(
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("url"),
                        rs.getString("category"),   // you used String in EducationalResource
                        provider,
                        requirements
                );

                resources.add(resource);
            }

        } catch (SQLException e) {
            System.out.println("Error loading resources from DB: " + e.getMessage());
            // optionally print stack trace for debugging
            // e.printStackTrace();
        }

        return resources;
    }

    /**
     * Converts a string like "ASYLUM_SEEKER,OTHER,GREEN_CARD_HOLDER"
     * into List<ImmigrationCategory>.
     */
    private static List<ImmigrationCategory> parseEligibleCategories(String value) {
        List<ImmigrationCategory> result = new ArrayList<>();

        if (value == null || value.isBlank()) {
            // empty list = "everyone eligible" in your Requirements logic
            return result;
        }

        String[] parts = value.split(",");
        for (String p : parts) {
            String trimmed = p.trim();
            if (!trimmed.isEmpty()) {
                try {
                    result.add(ImmigrationCategory.valueOf(trimmed));
                } catch (IllegalArgumentException ex) {
                    // In case of bad data, skip unknown values
                    System.out.println("Unknown immigration category in DB: " + trimmed);
                }
            }
        }

        return result;
        public static List<EducationalResource> getAllResources() {
        Provider provider1 = new Provider("Local College", "info@college.edu");
        Provider provider2 = new Provider("Community Center", "contact@community.org");
        Provider provider3 = new Provider("State University", "admissions@stateuni.edu");
        Provider provider4 = new Provider("Scholarship Foundation", "apply@scholarships.org");

        Requirements req1 = new Requirements(18, Arrays.asList(ImmigrationCategory.GREEN_CARD_HOLDER, ImmigrationCategory.ASYLUM_SEEKER));
        Requirements req2 = new Requirements(16, Arrays.asList(ImmigrationCategory.ASYLUM_SEEKER, ImmigrationCategory.OTHER));
        Requirements req3 = new Requirements(18, Arrays.asList(ImmigrationCategory.GREEN_CARD_HOLDER));
        Requirements req4 = new Requirements(17, Arrays.asList(ImmigrationCategory.ASYLUM_SEEKER, ImmigrationCategory.OTHER, ImmigrationCategory.GREEN_CARD_HOLDER));

        EducationalResource res1 = new EducationalResource(
            "English Language Course",
            "Learn English for daily life and work.",
            "https://www.kaplaninternational.com/language-schools/united-states",
            ResourceCategory.LANGUAGE.name(),
            provider1,
            req1
        );

        EducationalResource res2 = new EducationalResource(
            "Vocational Training",
            "Get certified in a trade.",
            "https://esd.wa.gov/jobs-and-training/job-training-support",
            ResourceCategory.VOCATIONAL.name(),
            provider2,
            req2
        );

        EducationalResource res3 = new EducationalResource(
            "University Admission Guide",
            "Step-by-step guide to applying for university programs.",
            "https://www.connectionsacademy.com/support/resources/article/online-school-to-college-application-guide/",
            ResourceCategory.UNIVERSITY.name(),
            provider3,
            req3
        );

        EducationalResource res4 = new EducationalResource(
            "International Student Scholarship",
            "Apply for scholarships for international and immigrant students.",
            "https://www.internationalstudent.com/scholarships/",
            ResourceCategory.SCHOLARSHIP.name(),
            provider4,
            req4
        );

        EducationalResource res5 = new EducationalResource(
            "Spanish Language Course",
            "Learn Spanish for work and community.",
            "https://www.onlinefreespanish.com/",
            ResourceCategory.LANGUAGE.name(),
            provider1,
            req2
        );

        EducationalResource res6 = new EducationalResource(
            "Tech Vocational Bootcamp",
            "Intensive bootcamp for IT and technology careers.",
            "https://www.yearup.org/",
            ResourceCategory.VOCATIONAL.name(),
            provider2,
            req1
        );

        EducationalResource res7 = new EducationalResource(
            "STEM University Prep",
            "Preparation resources for STEM university programs.",
            "https://www.zenithprepacademy.com/specialties/stem/",
            ResourceCategory.UNIVERSITY.name(),
            provider3,
            req4
        );

        EducationalResource res8 = new EducationalResource(
            "Merit-Based Scholarship",
            "Scholarships based on academic merit for immigrants.",
            "https://meritscholarships.org/",
            ResourceCategory.SCHOLARSHIP.name(),
            provider4,
            req3
        );

        return Arrays.asList(res1, res2, res3, res4, res5, res6, res7, res8);
    }
}
