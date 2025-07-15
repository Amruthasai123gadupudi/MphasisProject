package com.realestate.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realestate.model.Inquiry;
import com.realestate.model.Inquiry.TargetRole;
import com.realestate.model.Property;
import com.realestate.model.User;
import com.realestate.repository.InquiryRepository;
import com.realestate.repository.PropertyRepository;
import com.realestate.repository.UserRepository;

@Service
public class InquiryServiceImpl implements InquiryService {

	@Autowired
	private InquiryRepository inquiryRepo;

	@Autowired
	private PropertyRepository propertyRepo;

	@Autowired
	private UserRepository userRepo;

	public Inquiry sendInquiry(int propertyId, int buyerId, String message, TargetRole targetRole) {
	    Property property = propertyRepo.findById(propertyId).orElseThrow();
	    User buyer = userRepo.findById(buyerId).orElseThrow();

	    Inquiry inquiry = new Inquiry();
	    inquiry.setProperty(property);
	    inquiry.setBuyer(buyer);
	    inquiry.setMessage(message);
	    inquiry.setTargetRole(targetRole);
	    inquiry.setCreatedAt(LocalDateTime.now());

	    // 🧭 Set targetUser based on role
	    if (targetRole == TargetRole.SELLER) {
	        inquiry.setTargetUser(property.getSeller());
	    } else if (targetRole == TargetRole.MANAGER) {
	        inquiry.setTargetUser(property.getManager());
	    }

	    return inquiryRepo.save(inquiry);
	}

	@Override
	public List<Inquiry> getInquiriesByManager(int managerId) {
		return inquiryRepo.findInquiriesByTargetUserId(managerId);
	}

	@Override
	public List<Inquiry> getInquiriesBySeller(int sellerId) {
	    return inquiryRepo.findInquiriesByTargetUserId(sellerId);
	}



	@Override
	public List<Inquiry> getInquiriesByBuyer(int buyerId) {
		return inquiryRepo.findByBuyer_Id(buyerId);
	}
	
	
	//respond To Inquiry
    @Override
    public Inquiry respondToInquiry(int inquiryId, int responderId, String message, LocalDateTime scheduleDate) {
        Inquiry inquiry = inquiryRepo.findById(inquiryId)
                .orElseThrow(() -> new RuntimeException("Inquiry not found"));

        User responder = userRepo.findById(responderId)
                .orElseThrow(() -> new RuntimeException("Responder not found"));

        inquiry.setResponder(responder);
        inquiry.setRespondedAt(LocalDateTime.now());

        String fullResponseMessage = message;
        if (scheduleDate != null) {
            // Append schedule date nicely formatted to the message
            String formattedDate = scheduleDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            fullResponseMessage += "\nScheduled Date: " + formattedDate;
            inquiry.setScheduleDate(scheduleDate);
        }

        inquiry.setResponseMessage(fullResponseMessage);

        return inquiryRepo.save(inquiry);
    }

}
