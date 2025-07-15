package com.realestate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


import com.realestate.model.Inquiry;
import com.realestate.model.Inquiry.TargetRole;
import com.realestate.service.InquiryService;

@RestController
@RequestMapping("/api/inquiries")
public class InquiryController {

	@Autowired
	private InquiryService inquiryService;

	
	@PostMapping("/send")//Buyer submits inquiry targeted to a seller or manager
	public ResponseEntity<Inquiry> sendInquiry(
	        @RequestParam("propertyId") int propertyId,
	        @RequestParam("buyerId") int buyerId,
	        @RequestParam("message") String message,
	        @RequestParam("targetRole") TargetRole targetRole) {

	    Inquiry inquiry = inquiryService.sendInquiry(propertyId, buyerId, message, targetRole);
	    return ResponseEntity.ok(inquiry);
	}
	

	@GetMapping("/buyer/{buyerId}")//Buyer views their inquiries
	public ResponseEntity<List<Inquiry>> getInquiriesByBuyer(@PathVariable("buyerId") int buyerId) {
		return ResponseEntity.ok(inquiryService.getInquiriesByBuyer(buyerId));
	}
	
	
	 // 👤 Seller responds to an inquiry
	@PutMapping("/respond/seller")
	public ResponseEntity<Inquiry> sellerRespond(
	        @RequestParam int inquiryId,
	        @RequestParam int sellerId,
	        @RequestParam String message,
	        @RequestParam(required = false) String scheduleDate) {

	    LocalDateTime parsedScheduleDate = null;
	    if (scheduleDate != null && !scheduleDate.isEmpty()) {
	        try {
	            parsedScheduleDate = LocalDateTime.parse(scheduleDate);
	        } catch (DateTimeParseException e) {
	            return ResponseEntity.badRequest().build();
	        }
	    }

	    Inquiry updated = inquiryService.respondToInquiry(inquiryId, sellerId, message, parsedScheduleDate);
	    return ResponseEntity.ok(updated);
	}

   
	@PutMapping("/respond/manager")
	public ResponseEntity<Inquiry> managerRespond(
	        @RequestParam("inquiryId") int inquiryId,
	        @RequestParam("managerId") int managerId,
	        @RequestParam("message") String message,
	        @RequestParam(name="scheduleDate",required = false) String scheduleDate) {

	    LocalDateTime parsedScheduleDate = null;
	    if (scheduleDate != null && !scheduleDate.isEmpty()) {
	        try {
	            parsedScheduleDate = LocalDateTime.parse(scheduleDate);
	        } catch (DateTimeParseException e) {
	            return ResponseEntity.badRequest().build();
	        }
	    }

	    Inquiry updated = inquiryService.respondToInquiry(inquiryId, managerId, message, parsedScheduleDate);
	    return ResponseEntity.ok(updated);
	}


	
	@GetMapping("/manager/{managerId}")//Manager sees inquiries for properties they manage
	public ResponseEntity<List<Inquiry>> getInquiriesForManager(@PathVariable("managerId") int managerId) {
		List<Inquiry> inquiries = inquiryService.getInquiriesByManager(managerId);
		return ResponseEntity.ok(inquiries);
	}

	@GetMapping("/seller/{sellerId}")//Seller sees inquiries for properties they own
	public ResponseEntity<List<Inquiry>> getInquiriesBySeller(@PathVariable("sellerId") int sellerId) {
		return ResponseEntity.ok(inquiryService.getInquiriesBySeller(sellerId));
	}
	

}