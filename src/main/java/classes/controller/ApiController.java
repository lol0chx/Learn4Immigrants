package classes.controller;

import classes.EducationalResource;
import classes.User;
import classes.service.ResourceService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {
    
    @Autowired
    private ResourceService resourceService;
    
    @PostMapping("/save-resource")
    public ResponseEntity<Map<String, String>> saveResource(
            @RequestParam int resourceIndex,
            @RequestParam String resourceType, // "recommended" or "eligible"
            HttpSession session) {
        
        User user = (User) session.getAttribute("user");
        if (user == null) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Not logged in");
            return ResponseEntity.badRequest().body(response);
        }
        
        Boolean isGuest = (Boolean) session.getAttribute("isGuest");
        if (isGuest != null && isGuest) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Guests cannot save resources");
            return ResponseEntity.badRequest().body(response);
        }
        
        List<EducationalResource> resources;
        if ("recommended".equals(resourceType)) {
            resources = resourceService.getRecommendedResources(user);
        } else {
            resources = resourceService.getAllEligibleResources(user);
        }
        
        if (resourceIndex > 0 && resourceIndex <= resources.size()) {
            user.saveResource(resources.get(resourceIndex - 1));
            Map<String, String> response = new HashMap<>();
            response.put("message", "Resource saved successfully!");
            return ResponseEntity.ok(response);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Invalid resource index");
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @GetMapping("/resources/recommended")
    public ResponseEntity<List<EducationalResource>> getRecommendedResources(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(resourceService.getRecommendedResources(user));
    }
    
    @GetMapping("/resources/eligible")
    public ResponseEntity<List<EducationalResource>> getEligibleResources(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(resourceService.getAllEligibleResources(user));
    }
    
    @GetMapping("/resources/saved")
    public ResponseEntity<List<EducationalResource>> getSavedResources(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(user.viewSavedResources());
    }
}

