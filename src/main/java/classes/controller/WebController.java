package classes.controller;

import classes.ImmigrationCategory;
import classes.User;
import classes.service.ResourceService;
import classes.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ResourceService resourceService;
    
    @GetMapping("/")
    public String home() {
        return "index";
    }
    
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    
    @PostMapping("/login")
    public String login(@RequestParam String email, 
                       @RequestParam String password,
                       HttpSession session,
                       Model model) {
        User user = userService.loginUser(email, password);
        if (user != null) {
            session.setAttribute("user", user);
            return "redirect:/dashboard";
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }
    
    @GetMapping("/signup")
    public String signupPage(Model model) {
        model.addAttribute("categories", ImmigrationCategory.values());
        return "signup";
    }
    
    @PostMapping("/signup")
    public String signup(@RequestParam String name,
                        @RequestParam String email,
                        @RequestParam int age,
                        @RequestParam String immigrationCategory,
                        @RequestParam String location,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        
        if (userService.emailExists(email)) {
            model.addAttribute("error", "Email already exists");
            model.addAttribute("categories", ImmigrationCategory.values());
            return "signup";
        }
        
        ImmigrationCategory category = ImmigrationCategory.valueOf(immigrationCategory);
        User user = userService.createAccount(name, email, age, category, location, password);
        
        if (user != null) {
            session.setAttribute("user", user);
            return "redirect:/dashboard";
        } else {
            model.addAttribute("error", "Failed to create account");
            model.addAttribute("categories", ImmigrationCategory.values());
            return "signup";
        }
    }
    
    @GetMapping("/guest")
    public String guestPage(Model model) {
        model.addAttribute("categories", ImmigrationCategory.values());
        return "guest";
    }
    
    @PostMapping("/guest")
    public String guestLogin(@RequestParam String name,
                            @RequestParam String email,
                            @RequestParam int age,
                            @RequestParam String immigrationCategory,
                            @RequestParam String location,
                            HttpSession session) {
        ImmigrationCategory category = ImmigrationCategory.valueOf(immigrationCategory);
        User guest = userService.createGuestUser(name, email, age, category, location);
        session.setAttribute("user", guest);
        session.setAttribute("isGuest", true);
        return "redirect:/guest-dashboard";
    }
    
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        
        model.addAttribute("user", user);
        model.addAttribute("recommendedResources", 
                resourceService.getRecommendedResources(user));
        model.addAttribute("allEligibleResources", 
                resourceService.getAllEligibleResources(user));
        model.addAttribute("savedResources", user.viewSavedResources());
        
        return "dashboard";
    }
    
    @GetMapping("/guest-dashboard")
    public String guestDashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/guest";
        }
        
        model.addAttribute("user", user);
        model.addAttribute("recommendedResources", 
                resourceService.getRecommendedResources(user));
        model.addAttribute("allEligibleResources", 
                resourceService.getAllEligibleResources(user));
        
        return "guest-dashboard";
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}

