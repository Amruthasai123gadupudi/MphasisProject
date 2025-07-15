package com.realestate.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.realestate.model.User;
import com.realestate.model.User.Role;

public interface UserRepository extends JpaRepository<User, Integer> {

	List<User> findByActive(boolean active);

	List<User> findByRole(Role role);

	boolean existsByEmail(String email);

	Optional<User> findByEmail(String email);

	Optional<User> findById(Integer id);

	@Query("SELECT u.role, COUNT(u) FROM User u GROUP BY u.role")
	List<Object[]> countUsersByRole();

	@Query("SELECT COUNT(u) FROM User u")
	long countTotalUsers();

	@Query("SELECT COUNT(u) FROM User u WHERE u.role = 'SELLER'")
	long countSellers();

	@Query("SELECT COUNT(u) FROM User u WHERE u.role = 'BUYER'")
	long countBuyers();
	
	List<User> findByNameContainingIgnoreCase(String name);

}
