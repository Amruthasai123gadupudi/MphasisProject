package com.realestate.service;

import java.util.List;

import com.realestate.model.PropertyType;

public interface PlatformConfigService {
	PropertyType createCategory(PropertyType type);
	 
	PropertyType updateCategory(Integer id, PropertyType type);
 
	void deleteCategory(Integer id);
 
	List<PropertyType> getAllCategories();

    List<String> getRules();
	//Object getRules();

}