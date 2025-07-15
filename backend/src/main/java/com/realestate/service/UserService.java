package com.realestate.service;

import java.util.List;

import com.realestate.model.User;
import com.realestate.model.User.Role;

public interface UserService {
	User registerUser(User user);

	List<User> getAllUsers();

	User loginUser(String email, String password);

	User getUserById(int id);

	User createUser(User user);

	User updateUserStatus(int id, boolean active);

	User updateUserRole(int id, Role role);

	void deleteUserById(int id);

	List<User> getUsersByRole(User.Role role);

	 List<User> searchUsersByName(String name) ;

}
