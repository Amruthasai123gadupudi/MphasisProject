package com.realestate.repository;

import com.realestate.model.PropertyType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyTypeRepository extends JpaRepository<PropertyType, Integer> {
	boolean existsByName(String name);
}