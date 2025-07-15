package com.realestate.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.realestate.model.User;
import com.realestate.service.UserService;
import com.realestate.util.JwtUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    private User testUser;

    @BeforeEach
    public void setup() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("test@example.com");
        testUser.setPassword("password123");
        testUser.setName("Test User");
    }

    @Test
    public void testRegisterUser_Success() throws Exception {
        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setEmail(testUser.getEmail());
        savedUser.setName(testUser.getName());

        when(userService.registerUser(any(User.class))).thenReturn(savedUser);

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(savedUser.getId()))
                .andExpect(jsonPath("$.email").value(savedUser.getEmail()))
                .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test
    public void testRegisterUser_Conflict() throws Exception {
        when(userService.registerUser(any(User.class))).thenReturn(null);

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isConflict())
                .andExpect(content().string("user already exists"));
    }

    @Test
    public void testLoginUser_Success() throws Exception {
        when(userService.loginUser(testUser.getEmail(), testUser.getPassword())).thenReturn(testUser);
        when(jwtUtil.generateToken(testUser.getEmail())).thenReturn("mock-jwt-token");

        mockMvc.perform(post("/api/auth/login")
                .param("email", testUser.getEmail())
                .param("password", testUser.getPassword()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mock-jwt-token"))
                .andExpect(jsonPath("$.user.email").value(testUser.getEmail()));
    }

    @Test
    public void testLoginUser_Failure() throws Exception {
        when(userService.loginUser(testUser.getEmail(), testUser.getPassword())).thenReturn(null);

        mockMvc.perform(post("/api/auth/login")
                .param("email", testUser.getEmail())
                .param("password", testUser.getPassword()))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid email or password"));
    }

    @Test
    public void testGetAllUsers() throws Exception {
        List<User> users = List.of(testUser);
        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/api/auth/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value(testUser.getEmail()))
                .andExpect(jsonPath("$[0].name").value(testUser.getName()));
    }
}
