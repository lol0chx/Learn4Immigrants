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
    }
}
