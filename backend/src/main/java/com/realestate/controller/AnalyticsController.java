package com.realestate.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realestate.service.AnalyticsService;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping("/top-sellers")
    public ResponseEntity<?> getTopSellers() {
        List<Map<String, Object>> topSellers = analyticsService.getTop3SellersWithUser();

        if (topSellers.isEmpty()) {
        	Map<String, String> message = new HashMap<>();
            message.put("message", "No data found");
            return ResponseEntity.ok(message);
        }

        return ResponseEntity.ok(topSellers);
    }
}
