package classes;

import java.util.ArrayList;
import java.util.List;

public class Requirements {

    private int minAge;
    private List<ImmigrationCategory> eligibleCategories;

    // Optional/future attributes
    private String languageProficiency; // e.g., "English", "Spanish", "None"
    private String priorEducation;      // e.g., "High School", "Bachelor's", "None"

    public Requirements(int minAge, List<ImmigrationCategory> eligibleCategories) {
        this.minAge = minAge;
        this.eligibleCategories = new ArrayList<>(eligibleCategories);
        this.languageProficiency = "None";
        this.priorEducation = "None";
    }

    public Requirements(int minAge, List<ImmigrationCategory> eligibleCategories,
                        String languageProficiency, String priorEducation) {
        this.minAge = minAge;
        this.eligibleCategories = new ArrayList<>(eligibleCategories);
        this.languageProficiency = languageProficiency;
        this.priorEducation = priorEducation;
    }

    public boolean isEligible(User user) {
        if (user.getAge() < minAge) {
            return false;
        }

        if (eligibleCategories.isEmpty()) {
            return true;
        }

        if (!eligibleCategories.contains(user.getImmigrationCategory())) {
            return false;
        }

        // Future: language, priorEducation checks

        return true;
    }

    public String getRequirementsDescription() {
        StringBuilder description = new StringBuilder();

        description.append("Minimum Age: ").append(minAge).append(" years\n");

        if (!eligibleCategories.isEmpty()) {
            description.append("Eligible Immigration Categories: ");
            for (int i = 0; i < eligibleCategories.size(); i++) {
                description.append(eligibleCategories.get(i));
                if (i < eligibleCategories.size() - 1) {
                    description.append(", ");
                }
            }
            description.append("\n");
        } else {
            description.append("Eligible Immigration Categories: All\n");
        }

        if (!languageProficiency.equals("None")) {
            description.append("Language Proficiency: ").append(languageProficiency).append("\n");
        }

        if (!priorEducation.equals("None")) {
            description.append("Prior Education: ").append(priorEducation).append("\n");
        }

        return description.toString();
    }

    public int getMinAge() {
        return minAge;
    }

    public List<ImmigrationCategory> getEligibleCategories() {
        return new ArrayList<>(eligibleCategories);
    }

    public String getLanguageProficiency() {
        return languageProficiency;
    }

    public String getPriorEducation() {
        return priorEducation;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public void setEligibleCategories(List<ImmigrationCategory> eligibleCategories) {
        this.eligibleCategories = new ArrayList<>(eligibleCategories);
    }

    public void setLanguageProficiency(String languageProficiency) {
        this.languageProficiency = languageProficiency;
    }

    public void setPriorEducation(String priorEducation) {
        this.priorEducation = priorEducation;
    }
}
