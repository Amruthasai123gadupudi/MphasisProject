package com.realestate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realestate.model.ViewedProperty;
@Repository
public interface ViewedPropertyRepository extends JpaRepository<ViewedProperty, Integer> {
    List<ViewedProperty> findByBuyerId(Integer buyerId);
}