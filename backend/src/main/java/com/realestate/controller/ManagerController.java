package com.realestate.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realestate.model.Property;
import com.realestate.service.ManagerService;


@RestController
@RequestMapping("/api/manager")
public class ManagerController {
	
	@Autowired
    private ManagerService managerService;

    @PutMapping("/approve/{propertyId}")
    public ResponseEntity<Property> approveProperty(@PathVariable("propertyId") int propertyId) {
        return ResponseEntity.ok(managerService.approveProperty(propertyId));
    }

    @PutMapping("/edit/{propertyId}")
    public ResponseEntity<Property> editProperty(
            @PathVariable("propertyId") int propertyId,
            @RequestBody Property updatedData) {
        return ResponseEntity.ok(managerService.editProperty(propertyId, updatedData));
    }

//    @PutMapping("/remove/{propertyId}")
//    public ResponseEntity<Property> removeProperty(@PathVariable int propertyId) {
//        return ResponseEntity.ok(managerService.removeProperty(propertyId));
//    }

    @DeleteMapping("/delete/{propertyId}")
    public ResponseEntity<String> deleteProperty(@PathVariable("propertyId") int propertyId) {
        managerService.deleteProperty(propertyId);
        return ResponseEntity.ok("Deleted successfully");
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Property>> getPendingProperties() {
        return ResponseEntity.ok(managerService.getPendingProperties());
    }
    
    @GetMapping("/approved")
    public ResponseEntity<List<Property>> getApprovedProperties() {
        return ResponseEntity.ok(managerService.getApprovedProperties());
    }
    
    @GetMapping("/combined")
    public ResponseEntity<Map<String, List<Property>>> getCombinedProperties() {
        Map<String, List<Property>> response = new HashMap<>();
        response.put("approved", managerService.getApprovedProperties());
        response.put("pending", managerService.getPendingProperties());
        return ResponseEntity.ok(response);
    }

}
