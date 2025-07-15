package com.realestate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.realestate.model.Rule.Role;
import com.realestate.model.User;
import com.realestate.model.UserNotification;

public interface UserNotificationRepository extends JpaRepository<UserNotification, Long> {
    List<UserNotification> findByUserId(Long userId);
    
    List<UserNotification> findByUserRole(User.Role enumRole);
	List<UserNotification> findByTargetRole(User.Role role);

}