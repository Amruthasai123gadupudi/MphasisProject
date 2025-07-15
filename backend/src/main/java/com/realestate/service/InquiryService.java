package com.realestate.service;

import java.time.LocalDateTime;
import java.util.List;

import com.realestate.model.Inquiry;
import com.realestate.model.Inquiry.TargetRole;

public interface InquiryService {
	Inquiry sendInquiry(int propertyId, int buyerId, String message, TargetRole targetRole);

	Inquiry respondToInquiry(int inquiryId, int responderId, String message, LocalDateTime scheduleDate);

	List<Inquiry> getInquiriesByBuyer(int buyerId);

	List<Inquiry> getInquiriesBySeller(int sellerId);

	List<Inquiry> getInquiriesByManager(int managerId);
}
