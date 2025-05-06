package com.example.lowheadcount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@SpringBootApplication
public class LowHeadcountApplication {
    public static void main(String[] args) {
        SpringApplication.run(LowHeadcountApplication.class, args);
    }
}

// REST Controller for incoming API
@RestController
@RequestMapping("/api")
class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/headcount")
    public String receiveHeadcount(@RequestBody Map<String, Integer> headcountMap) {
        notificationService.processHeadcountNotifications(headcountMap);
        return "Headcount notifications generated successfully!";
    }

    @PostMapping("/learning")
    public String receiveLearningHours(@RequestBody Map<String, Double> learningMap) {
        notificationService.processLearningNotifications(learningMap);
        return "Learning hours notifications generated successfully!";
    }
}

// Web Controller for Thymeleaf
@Controller
class WebController {

    private final NotificationService notificationService;

    public WebController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("headcountNotifications", notificationService.getHeadcountNotifications());
        model.addAttribute("learningNotifications", notificationService.getLearningNotifications());
        return "index";
    }
}

// Service to manage notification logic
@Service
class NotificationService {

    private final List<String> headcountNotifications = new ArrayList<>();
    private final List<String> learningNotifications = new ArrayList<>();

    public void processHeadcountNotifications(Map<String, Integer> headcountMap) {
        Set<String> unique = new LinkedHashSet<>();
        for (Map.Entry<String, Integer> entry : headcountMap.entrySet()) {
            unique.add("Low headcount at " + entry.getKey() + ": " + entry.getValue());
        }
        synchronized (headcountNotifications) {
            headcountNotifications.clear();
            headcountNotifications.addAll(unique);
        }
    }

    public void processLearningNotifications(Map<String, Double> learningMap) {
        Set<String> unique = new LinkedHashSet<>();
        for (Map.Entry<String, Double> entry : learningMap.entrySet()) {
            unique.add("Low learning hours at " + entry.getKey() + ": " + entry.getValue());
        }
        synchronized (learningNotifications) {
            learningNotifications.clear();
            learningNotifications.addAll(unique);
        }
    }

    public List<String> getHeadcountNotifications() {
        synchronized (headcountNotifications) {
            return new ArrayList<>(headcountNotifications);
        }
    }

    public List<String> getLearningNotifications() {
        synchronized (learningNotifications) {
            return new ArrayList<>(learningNotifications);
        }
    }
}
