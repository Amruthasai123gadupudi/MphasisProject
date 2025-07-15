
package com.realestate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realestate.model.User;
import com.realestate.model.UserNotification;
import com.realestate.repository.UserNotificationRepository;
import com.realestate.repository.UserRepository;

@Service
public class NotificationDispatchService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserNotificationRepository userNotificationRepository;

    public void notifyUsersByRole(User.Role targetRole, String message) {
        List<User> users = userRepository.findByRole(targetRole);

        for (User user : users) {
            UserNotification notification = new UserNotification();
            notification.setUser(user);
            notification.setMessage(message);
            notification.setActive(true);
            notification.setTargetRole(targetRole);
            userNotificationRepository.save(notification);
        }
    }
}
