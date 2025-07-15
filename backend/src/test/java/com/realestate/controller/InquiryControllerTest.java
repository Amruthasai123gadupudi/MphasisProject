package com.realestate.controller;

import com.realestate.model.Inquiry;
import com.realestate.model.Inquiry.TargetRole;
import com.realestate.service.InquiryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InquiryControllerTest {

    @InjectMocks
    private InquiryController inquiryController;

    @Mock
    private InquiryService inquiryService;

    @Test
    void testSendInquiry_success() {
        Inquiry mockInquiry = new Inquiry();
        when(inquiryService.sendInquiry(1, 2, "Message", TargetRole.SELLER)).thenReturn(mockInquiry);

        ResponseEntity<Inquiry> response = inquiryController.sendInquiry(1, 2, "Message", TargetRole.SELLER);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockInquiry, response.getBody());
    }

    @Test
    void testGetInquiriesByBuyer_success() {
        List<Inquiry> mockList = Arrays.asList(new Inquiry());
        when(inquiryService.getInquiriesByBuyer(99)).thenReturn(mockList);

        ResponseEntity<List<Inquiry>> response = inquiryController.getInquiriesByBuyer(99);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockList, response.getBody());
    }

    @Test
    void testSellerRespond_success() {
        Inquiry mock = new Inquiry();
        LocalDateTime date = LocalDateTime.now();

        when(inquiryService.respondToInquiry(10, 20, "Thanks", date)).thenReturn(mock);

        ResponseEntity<Inquiry> response = inquiryController.sellerRespond(10, 20, "Thanks", date.toString());

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mock, response.getBody());
    }

    @Test
    void testSellerRespond_invalidDate_returnsBadRequest() {
        ResponseEntity<Inquiry> response = inquiryController.sellerRespond(10, 20, "Thanks", "invalid-date");
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void testManagerRespond_success() {
        Inquiry mock = new Inquiry();
        LocalDateTime date = LocalDateTime.now();

        when(inquiryService.respondToInquiry(11, 22, "OK", date)).thenReturn(mock);

        ResponseEntity<Inquiry> response = inquiryController.managerRespond(11, 22, "OK", date.toString());

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mock, response.getBody());
    }

    @Test
    void testManagerRespond_invalidDate_returnsBadRequest() {
        ResponseEntity<Inquiry> response = inquiryController.managerRespond(11, 22, "OK", "bad-date");
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void testGetInquiriesForManager_success() {
        List<Inquiry> mockList = Arrays.asList(new Inquiry());
        when(inquiryService.getInquiriesByManager(101)).thenReturn(mockList);

        ResponseEntity<List<Inquiry>> response = inquiryController.getInquiriesForManager(101);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockList, response.getBody());
    }

    @Test
    void testGetInquiriesBySeller_success() {
        List<Inquiry> mockList = Arrays.asList(new Inquiry());
        when(inquiryService.getInquiriesBySeller(88)).thenReturn(mockList);

        ResponseEntity<List<Inquiry>> response = inquiryController.getInquiriesBySeller(88);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockList, response.getBody());
    }
}
