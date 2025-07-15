package com.realestate.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realestate.exceptions.UserConflictException;
import com.realestate.model.User;
import com.realestate.model.User.Role;
import com.realestate.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Override
	public User registerUser(User user) {
		if (userRepo.existsByEmail(user.getEmail())) {
			throw new UserConflictException("Email already registered");
		}
//        user.setPassword(PasswordUtil.hashPassword(user.getPassword()));
		user.setActive(true);
		return userRepo.save(user);
	}

	@Override
	public User loginUser(String email, String password) {
		Optional<User> optionalUser = userRepo.findByEmail(email);
		if (optionalUser.isEmpty()) {
			throw new RuntimeException("Invalid email or password");
		}
		User user = optionalUser.get();

		if (!user.getPassword().equals(password)) {
			throw new RuntimeException("Invalid email or password");
		}

		if (!user.isActive()) {
			throw new RuntimeException("User account is not active");
		}

		return user;
	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return userRepo.findAll();
	}

	@Override
	public User getUserById(int id) {
		return userRepo.findById(id).orElse(null);
	}

	@Override
	public User createUser(User user) {
		return userRepo.save(user);
	}

	@Override
	public User updateUserStatus(int id, boolean active) {
		User user = userRepo.findById(id).orElseThrow();
		user.setActive(active);
		return userRepo.save(user);
	}

	@Override
	public User updateUserRole(int id, Role role) {
		User user = userRepo.findById(id).orElseThrow();
		user.setRole(role);
		return userRepo.save(user);
	}

	@Override
	public void deleteUserById(int id) {
		if (!userRepo.existsById(id)) {
			throw new RuntimeException("User not found");
		}
		userRepo.deleteById(id);
	}

	@Override
	public List<User> getUsersByRole(User.Role role) {
		return userRepo.findByRole(role);
	}

	@Override
	public List<User> searchUsersByName(String name) {
		// TODO Auto-generated method stub
		return userRepo.findByNameContainingIgnoreCase(name);
	}

}