package com.realestate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realestate.model.ViewedProperty;
import com.realestate.service.ViewPropertyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/buyer")
@RequiredArgsConstructor
public class ViewedPropertyController {
	 // 👁️ Log Viewed Property
	 @Autowired
	    private ViewPropertyService viewedPropertyService;
    @PostMapping("/viewed")
    public ResponseEntity<ViewedProperty> logViewed(@RequestBody ViewedProperty view) {
        return ResponseEntity.status(HttpStatus.CREATED).body(viewedPropertyService.logView(view));
    }

    // 👁️ Get Viewed Properties
    @GetMapping("/viewed/{buyerId}")
    public ResponseEntity<List<ViewedProperty>> getViewed(@PathVariable("buyerId") Integer buyerId) {
        List<ViewedProperty> viewedProperties = viewedPropertyService.getViewedByBuyer(buyerId);
        return ResponseEntity.ok(viewedProperties);
    }
}
