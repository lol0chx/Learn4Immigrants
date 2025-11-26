package classes;

import java.util.Arrays;
import java.util.List;

public class EducationalResourceRepository {
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
            "http://englishcourse.com",
            ResourceCategory.LANGUAGE.name(),
            provider1,
            req1
        );

        EducationalResource res2 = new EducationalResource(
            "Vocational Training",
            "Get certified in a trade.",
            "http://vocationaltraining.com",
            ResourceCategory.VOCATIONAL.name(),
            provider2,
            req2
        );

        EducationalResource res3 = new EducationalResource(
            "University Admission Guide",
            "Step-by-step guide to applying for university programs.",
            "http://stateuni.edu/admissions",
            ResourceCategory.UNIVERSITY.name(),
            provider3,
            req3
        );

        EducationalResource res4 = new EducationalResource(
            "International Student Scholarship",
            "Apply for scholarships for international and immigrant students.",
            "http://scholarships.org/international",
            ResourceCategory.SCHOLARSHIP.name(),
            provider4,
            req4
        );

        EducationalResource res5 = new EducationalResource(
            "Spanish Language Course",
            "Learn Spanish for work and community.",
            "http://spanishcourse.com",
            ResourceCategory.LANGUAGE.name(),
            provider1,
            req2
        );

        EducationalResource res6 = new EducationalResource(
            "Tech Vocational Bootcamp",
            "Intensive bootcamp for IT and technology careers.",
            "http://techbootcamp.com",
            ResourceCategory.VOCATIONAL.name(),
            provider2,
            req1
        );

        EducationalResource res7 = new EducationalResource(
            "STEM University Prep",
            "Preparation resources for STEM university programs.",
            "http://stateuni.edu/stem-prep",
            ResourceCategory.UNIVERSITY.name(),
            provider3,
            req4
        );

        EducationalResource res8 = new EducationalResource(
            "Merit-Based Scholarship",
            "Scholarships based on academic merit for immigrants.",
            "http://scholarships.org/merit",
            ResourceCategory.SCHOLARSHIP.name(),
            provider4,
            req3
        );

        return Arrays.asList(res1, res2, res3, res4, res5, res6, res7, res8);
    }
}
