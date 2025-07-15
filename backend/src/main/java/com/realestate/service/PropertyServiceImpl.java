package com.realestate.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.realestate.model.Property;
import com.realestate.model.PropertyType;
import com.realestate.model.User;
import com.realestate.repository.PropertyRepository;
import com.realestate.repository.PropertyTypeRepository;
import com.realestate.repository.UserRepository;

@Service
public class PropertyServiceImpl implements PropertyService {

	@Autowired
	private PropertyRepository propertyRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PropertyTypeRepository propertyTypeRepository;
	
	//Filesupload service
	private final String UPLOAD_DIR = "uploads/";
	public String storeImage(MultipartFile file) throws IOException {
    // You can customize the folder path and filename
    String uploadsDir = "uploads/";
 
    // Make sure folder exists
    Path uploadPath = Paths.get(uploadsDir);
    if (!Files.exists(uploadPath)) {
        Files.createDirectories(uploadPath);
    }
 
    // Create unique file name
    String originalFilename = file.getOriginalFilename();
    String fileExtension = "";
 
    if (originalFilename != null && originalFilename.contains(".")) {
        fileExtension = originalFilename.substring(originalFilename.lastIndexOf('.'));
    }
 
    String uniqueFilename = System.currentTimeMillis() + fileExtension;
 
    // Save file to uploads folder
    Path filePath = uploadPath.resolve(uniqueFilename);
    Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
 
    // Return URL path — you can serve static files from /uploads via Spring config
    // Example: http://localhost:8082/uploads/{filename}
    return "/uploads/" + uniqueFilename;
}
	
	@Override
	public List<Property> searchProperties(String location, Double minPrice, Double maxPrice, Integer typeId) {
		return propertyRepository.searchProperties(location, minPrice, maxPrice, typeId);
	}

	@Override
	public List<Property> getApprovedProperties() {
		return propertyRepository.findByApprovedTrue();
	}

	public List<Property> getPropertiesBySeller(Integer sellerId) {

		User seller = userRepository.findById(sellerId).orElseThrow(() -> new RuntimeException("Seller not found"));

		return propertyRepository.findBySeller(seller);
	}

	public Optional<Property> getPropertyById(Integer id) {
		return propertyRepository.findById(id);
	}

	public List<Property> getAllProperties() {
		return propertyRepository.findAll();
	}

	@Override
	public List<Property> getPropertiesWithoutCompletedTransactions() {
		return propertyRepository.findPropertiesWithoutCompletedTransactions();
	}

	@Override
	public Property addProperty(Property property) {
		Integer sellerId = property.getSeller().getId();
		Integer typeId = property.getType().getId();

		User seller = userRepository.findById(sellerId).orElseThrow(() -> new RuntimeException("Seller not found"));
		PropertyType type = propertyTypeRepository.findById(typeId)
				.orElseThrow(() -> new RuntimeException("Invalid property type"));

		property.setSeller(seller);
		property.setType(type);
		property.setApproved(false);

		return propertyRepository.save(property);
	}

	@Override
	public Property updateProperty(Integer propertyId, Property updatedProperty) {
		Integer sellerId = updatedProperty.getSeller().getId();
		Integer managerId = updatedProperty.getManager() != null ? updatedProperty.getManager().getId() : null;
		Integer typeId = updatedProperty.getType().getId();

		Property existing = propertyRepository.findById(propertyId)
				.orElseThrow(() -> new RuntimeException("Property not found"));

		if (!sellerId.equals(existing.getSeller().getId())) {
			throw new RuntimeException("Unauthorized to update this property");
		}

		PropertyType type = propertyTypeRepository.findById(typeId)
				.orElseThrow(() -> new RuntimeException("Invalid property type"));
		
		User manager = null;
	    if (managerId != null) {
	        manager = userRepository.findById(managerId)
	            .orElseThrow(() -> new RuntimeException("Manager not found"));
	    }
	    
		existing.setTitle(updatedProperty.getTitle());
		existing.setDescription(updatedProperty.getDescription());
		existing.setPrice(updatedProperty.getPrice());
		existing.setLocation(updatedProperty.getLocation());
		existing.setImageUrl(updatedProperty.getImageUrl());
		existing.setType(type);
		existing.setApproved(false);
		existing.setManager(manager);


		return propertyRepository.save(existing);
	}

	public void deleteProperty(Integer propertyId, Integer sellerId) {
		Property property = propertyRepository.findById(propertyId)
				.orElseThrow(() -> new RuntimeException("Property not found"));
		if (!sellerId.equals(property.getSeller().getId())) {
			throw new RuntimeException("Unauthorized to delete this property");
		}
		propertyRepository.delete(property);
	}
	
	 public List<Property> getApprovedPropertiesWithIncompleteTransactions() {
	        return propertyRepository.findApprovedPropertiesWithIncompleteTransactions();
	    }
}
