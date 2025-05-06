package com.example.btpservice;

import org.springframework.web.bind.annotation.*;
import java.util.*;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.OPTIONS})
@RestController
@RequestMapping("/api")
public class LocationController {

    private final List<Map<String, Object>> headcountData = new ArrayList<>();
    private final List<Map<String, Object>> learningData = new ArrayList<>();

    @PostMapping("/headcount")
    public String saveHeadcount(@RequestBody Map<String, Integer> data) {
        data.forEach((location, count) -> {
            Map<String, Object> entry = new HashMap<>();
            entry.put("Location", location);
            entry.put("headcount", count);
            headcountData.add(entry);
        });
        return "Headcount data saved.";
    }

    @PostMapping("/learning")
    public String saveLearning(@RequestBody Map<String, Integer> data) {
        data.forEach((location, hours) -> {
            Map<String, Object> entry = new HashMap<>();
            entry.put("Location", location);
            entry.put("learningHours", hours);
            learningData.add(entry);
        });
        return "Learning data saved.";
    }

    @GetMapping("/headcount")
    public Map<String, Object> getHeadcount() {
        return Collections.singletonMap("list", headcountData);
    }

    @GetMapping("/learning")
    public Map<String, Object> getLearning() {
        return Collections.singletonMap("list", learningData);
    }
}

