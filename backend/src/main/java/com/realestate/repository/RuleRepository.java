package com.realestate.repository;

import com.realestate.model.Rule;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleRepository extends JpaRepository<Rule, Long> {

	List<Rule> findAllByTargetRole(Rule.Role role);
    // Add custom queries if needed
}