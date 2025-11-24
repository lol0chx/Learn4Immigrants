package classes;

public class Requirements {
    private int minAge;
    private int maxAge;
    private String requiredStatus;
    private String requiredLocation;

    public Requirements(int minAge, int maxAge, String requiredStatus, String requiredLocation) {
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.requiredStatus = requiredStatus;
        this.requiredLocation = requiredLocation;
    }

    // Main eligibility logic
    public boolean isEligible(User user) {
        boolean statusOK = requiredStatus == null ||
                requiredStatus.equalsIgnoreCase(user.getImmigrationCategory().name());

        boolean locationOK = requiredLocation == null ||
                requiredLocation.equalsIgnoreCase(user.getLocation());

        boolean ageOK = user.getAge() >= minAge &&
                (maxAge == 0 || user.getAge() <= maxAge);

        return statusOK && locationOK && ageOK;
    }

}

