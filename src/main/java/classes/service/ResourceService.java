package classes.service;

import classes.EducationalResource;
import classes.EducationalResourceRepository;
import classes.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceService {
    
    private List<EducationalResource> allResources;
    
    public ResourceService() {
        this.allResources = EducationalResourceRepository.getAllResources();
    }
    
    public List<EducationalResource> getRecommendedResources(User user) {
        List<EducationalResource> eligible = 
                EducationalResource.filterByEligibility(allResources, user);
        
        List<EducationalResource> nearby = 
                EducationalResource.filterByLocation(eligible, user.getLocation());
        
        return nearby.isEmpty() ? eligible : nearby;
    }
    
    public List<EducationalResource> getAllEligibleResources(User user) {
        return EducationalResource.filterByEligibility(allResources, user);
    }
    
    public List<EducationalResource> getAllResources() {
        return allResources;
    }
}

