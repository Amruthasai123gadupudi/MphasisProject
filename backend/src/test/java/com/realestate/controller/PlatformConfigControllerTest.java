package com.realestate.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.realestate.model.PropertyType;
import com.realestate.model.User;
import com.realestate.model.UserNotification;
import com.realestate.service.NotificationDispatchService;
import com.realestate.service.PlatformConfigService;
import com.realestate.service.RuleService;

@WebMvcTest(PlatformConfigController.class)
public class PlatformConfigControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlatformConfigService configService;

    @MockBean
    private NotificationDispatchService notificationDispatchService;

    @MockBean
    private RuleService ruleService;

    @Autowired
    private ObjectMapper objectMapper;  // for JSON conversion

    private PropertyType testCategory;

    @BeforeEach
    public void setup() {
        testCategory = new PropertyType();
        testCategory.setId(1);
        testCategory.setName("Residential");
    }

    @Test
    public void testAddCategory_Success() throws Exception {
        when(configService.createCategory(any(PropertyType.class))).thenReturn(testCategory);

        mockMvc.perform(post("/api/admin/config/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testCategory)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(testCategory.getId()))
                .andExpect(jsonPath("$.name").value(testCategory.getName()));
    }

    @Test
    public void testAddCategory_Failure() throws Exception {
        when(configService.createCategory(any(PropertyType.class)))
                .thenThrow(new RuntimeException("Invalid category"));

        mockMvc.perform(post("/api/admin/config/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testCategory)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("{\"message\":\"Invalid category\"}"));
    }

    // Similarly, add more tests for other endpoints
    @Test
    public void testSetNotification_Success() throws Exception {
        UserNotification notification = new UserNotification();
        User user = new User();
        user.setId(123L);
        notification.setUser(user);
        notification.setMessage("Test message");

        when(configService.saveNotification(any(UserNotification.class)))
            .thenReturn("Notification saved for user ID: 123");

        mockMvc.perform(post("/api/admin/config/notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(notification)))
                .andExpect(status().isOk())
                .andExpect(content().string("Notification saved for user ID: 123"));
    }


    @Test
    public void testGetNotificationsByUser() throws Exception {
        // You can mock list of notifications and test GET here...
    }

    // ... add other test cases for GET, PUT, DELETE endpoints similarly
}
