package com.realestate.service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.realestate.model.Property;

public interface PropertyService {
	List<Property> searchProperties(String location, Double minPrice, Double maxPrice, Integer typeId);

	List<Property> getApprovedProperties();

	List<Property> getPropertiesBySeller(Integer sellerId);

	Optional<Property> getPropertyById(Integer id);

	List<Property> getAllProperties();

	void deleteProperty(Integer propertyId, Integer sellerId);

	Property addProperty(Property property);

	Property updateProperty(Integer propertyId, Property updatedProperty);

	List<Property> getPropertiesWithoutCompletedTransactions();

	List<Property> getApprovedPropertiesWithIncompleteTransactions();

	String storeImage(MultipartFile file) throws Exception;
}
