package classes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EducationalResourceRepository {

    // Update if your DB settings differ
    private static final String URL =
            "jdbc:postgresql://localhost:5432/learn4immigrants";
    private static final String USER = "ermi";
    private static final String PASSWORD = "";  // if you set a password, put it here

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
                Provider provider = new Provider(
                        rs.getString("provider_name"),
                        rs.getString("provider_contact"),
                        rs.getString("provider_location")
                );

                int minAge = rs.getInt("min_age");
                String eligibleCategoriesStr = rs.getString("eligible_categories");
                List<ImmigrationCategory> eligibleCategories =
                        parseEligibleCategories(eligibleCategoriesStr);
                Requirements requirements = new Requirements(minAge, eligibleCategories);

                EducationalResource resource = new EducationalResource(
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("url"),
                        rs.getString("category"),
                        provider,
                        requirements
                );

                resources.add(resource);
            }

        } catch (SQLException e) {
            System.out.println("Error loading resources from DB: " + e.getMessage());
        }

        return resources;
    }

    private static List<ImmigrationCategory> parseEligibleCategories(String value) {
        List<ImmigrationCategory> result = new ArrayList<>();

        if (value == null || value.isBlank()) {
            return result; // empty list = everyone eligible
        }

        String[] parts = value.split(",");
        for (String p : parts) {
            String trimmed = p.trim();
            if (!trimmed.isEmpty()) {
                try {
                    result.add(ImmigrationCategory.valueOf(trimmed));
                } catch (IllegalArgumentException ex) {
                    System.out.println("Unknown immigration category in DB: " + trimmed);
                }
            }
        }

        return result;
    }
}
