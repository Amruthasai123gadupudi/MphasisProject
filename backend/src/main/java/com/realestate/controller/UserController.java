 package com.realestate.controller;

import com.realestate.model.User;
import com.realestate.model.User.Role;
import com.realestate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") int id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }


    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable("id") int id, @RequestParam("active") boolean active) {
        userService.updateUserStatus(id, active);
        return ResponseEntity.ok("User status updated");
    }

    @PutMapping("/{id}/role")
    public ResponseEntity<?> updateRole(@PathVariable("id") int id, @RequestParam("role") Role role) {
        userService.updateUserRole(id, role);
        return ResponseEntity.ok("User role updated");
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") int id) {
        try {
            userService.deleteUserById(id);
            return ResponseEntity.ok("User deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("User not found");
        }
    }
   
    @GetMapping("/role/{role}")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable("role") Role role) {
        return ResponseEntity.ok(userService.getUsersByRole(role));
    }

}