package com.realestate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realestate.model.PropertyType;
import com.realestate.model.Rule;
import com.realestate.model.User;
import com.realestate.model.UserNotification;
import com.realestate.repository.UserNotificationRepository;
import com.realestate.repository.UserRepository;
import com.realestate.service.NotificationDispatchService;
import com.realestate.service.PlatformConfigService;
import com.realestate.service.RuleService;

@RestController
@RequestMapping("/api/admin/config")
public class PlatformConfigController {

	@Autowired
	private PlatformConfigService configService;
	@Autowired
	private UserNotificationRepository userNotificationRepository;

	@Autowired
	private NotificationDispatchService notificationDispatchService;

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RuleService ruleService;

	public PlatformConfigController(UserNotificationRepository userNotificationRepository) {
		this.userNotificationRepository = userNotificationRepository;
	}

	@PostMapping("/categories")
	public ResponseEntity<?> addCategory(@RequestBody PropertyType type) {
		try {
			PropertyType saved = configService.createCategory(type);
			return new ResponseEntity<>(saved, HttpStatus.CREATED);
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\":\"" + e.getMessage() + "\"}");
		}
	}

	@PutMapping("/categories/{id}")
	public ResponseEntity<?> editCategory(@PathVariable("id") Integer id, @RequestBody PropertyType type) {
		try {
			PropertyType updated = configService.updateCategory(id, type);
			return ResponseEntity.ok(updated);
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\":\"" + e.getMessage() + "\"}");
		}
	}

	@DeleteMapping("/categories/{id}")
	public ResponseEntity<?> deleteCategory(@PathVariable("id") Integer id) {
		try {
			configService.deleteCategory(id);
			return ResponseEntity.ok("{\"message\":\"Category deleted successfully\"}");
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"message\":\"" + e.getMessage() + "\"}");
		}
	}

	@GetMapping("/categories")
	public ResponseEntity<List<PropertyType>> getCategories() {
		return ResponseEntity.ok(configService.getAllCategories());
	}

	@PostMapping("/notifications")
	public ResponseEntity<?> setNotification(@RequestBody UserNotification notification) {
		try {
			if (notification.getUser() == null || notification.getUser().getId() == 0) {
				return ResponseEntity.badRequest().body("{\"message\":\"User ID is required\"}");

			}

			User managedUser = userRepository.findById(notification.getUser().getId())
					.orElseThrow(() -> new RuntimeException("User not found"));

			notification.setUser(managedUser);
			UserNotification saved = userNotificationRepository.save(notification);

			return ResponseEntity.ok("Notification saved for user ID: " + saved.getUser().getId());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("{\"message\":\"Failed to save notification: " + e.getMessage() + "\"}");
		}
	}

	@GetMapping("/notifications/user/{userId}")
	public ResponseEntity<List<UserNotification>> getNotificationsByUser(@PathVariable("userId") Long userId) {
		List<UserNotification> notifications = userNotificationRepository.findByUserId(userId);
		return ResponseEntity.ok(notifications);
	}

	@GetMapping("/notifications/role/{role}")
	public ResponseEntity<List<UserNotification>> getNotificationsByRole(@PathVariable("role") String role) {
		try {
			User.Role enumRole = User.Role.valueOf(role.toUpperCase()); // Converts string to enum
			List<UserNotification> notifications = userNotificationRepository.findByUserRole(enumRole);
			return ResponseEntity.ok(notifications);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(null); // Invalid role passed
		}
	}

	@PostMapping("/rules")
	public ResponseEntity<?> addRule(@RequestBody Rule rule) {
		try {
			Rule savedRule = ruleService.saveRule(rule);
			return new ResponseEntity<>(savedRule, HttpStatus.CREATED);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\":\"" + e.getMessage() + "\"}");
		}
	}

	@GetMapping("/rules")
	public ResponseEntity<List<Rule>> getAllRules() {
		return ResponseEntity.ok(ruleService.getAllRules());
	}
	@GetMapping("/rules/{role}")
	public ResponseEntity<List<Rule>> getAllRulesRoles(@PathVariable("role") Rule.Role role) {
		return ResponseEntity.ok(ruleService.getAllRules(role));
	}
	@DeleteMapping("/rules/{id}")
	public ResponseEntity<?> deleteRule(@PathVariable("id") Long id) {
		try {
			ruleService.deleteRule(id);
			return ResponseEntity.ok("{\"message\":\"Rule deleted successfully\"}");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"message\":\"" + e.getMessage() + "\"}");
		}
	}
}
