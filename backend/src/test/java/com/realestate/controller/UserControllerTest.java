package com.realestate.controller;

import com.realestate.model.User;
import com.realestate.model.User.Role;
import com.realestate.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.springframework.http.ResponseEntity;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user1 = new User();
        user1.setId(1);
        user1.setName("John Doe");
        user1.setEmail("john@example.com");
        user1.setRole(Role.BUYER);
        user1.setActive(true);

        user2 = new User();
        user2.setId(2);
        user2.setName("Jane Smith");
        user2.setEmail("jane@example.com");
        user2.setRole(Role.SELLER);
        user2.setActive(false);
    }

    @Test
    void testGetAllUsers() {
        List<User> mockUsers = Arrays.asList(user1, user2);
        when(userService.getAllUsers()).thenReturn(mockUsers);

        List<User> result = userController.getAllUsers();

        assertEquals(2, result.size());
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void testCreateUser() {
        when(userService.createUser(any(User.class))).thenReturn(user1);

        User result = userController.createUser(user1);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(userService, times(1)).createUser(user1);
    }

    @Test
    void testGetUserById() {
        when(userService.getUserById(1)).thenReturn(user1);

        ResponseEntity<User> response = userController.getUserById(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(user1, response.getBody());
        verify(userService, times(1)).getUserById(1);
    }

    @Test
    void testUpdateStatus() {
        when(userService.updateUserStatus(1, false)).thenReturn(user1);  // Correctly mock returned user

        ResponseEntity<?> response = userController.updateStatus(1, false);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User status updated", response.getBody());
        verify(userService, times(1)).updateUserStatus(1, false);
    }

    @Test
    void testUpdateRole() {
        when(userService.updateUserRole(1, Role.ADMIN)).thenReturn(user1);  // Correctly mock returned user

        ResponseEntity<?> response = userController.updateRole(1, Role.ADMIN);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User role updated", response.getBody());
        verify(userService, times(1)).updateUserRole(1, Role.ADMIN);
    }

    @Test
    void testDeleteUserSuccess() {
        doNothing().when(userService).deleteUserById(1);

        ResponseEntity<?> response = userController.deleteUser(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User deleted successfully", response.getBody());
        verify(userService, times(1)).deleteUserById(1);
    }

    @Test
    void testDeleteUserNotFound() {
        doThrow(new RuntimeException("User not found")).when(userService).deleteUserById(1);

        ResponseEntity<?> response = userController.deleteUser(1);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("User not found", response.getBody());
        verify(userService, times(1)).deleteUserById(1);
    }

    @Test
    void testGetUsersByRole() {
        List<User> buyers = Arrays.asList(user1);
        when(userService.getUsersByRole(Role.BUYER)).thenReturn(buyers);

        ResponseEntity<List<User>> response = userController.getUsersByRole(Role.BUYER);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals(Role.BUYER, response.getBody().get(0).getRole());
        verify(userService, times(1)).getUsersByRole(Role.BUYER);
    }
}
