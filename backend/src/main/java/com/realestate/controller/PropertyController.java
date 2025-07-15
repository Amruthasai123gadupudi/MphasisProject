package com.realestate.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.realestate.model.Property;
import com.realestate.model.PropertyType;
import com.realestate.model.User;
import com.realestate.service.PropertyService;
import com.realestate.service.PropertyTypeService;
import com.realestate.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {

	@Autowired
	private PropertyService propertyService;
	@Autowired
	UserService userService;
	@Autowired
	PropertyTypeService propertyTypeService;

	@GetMapping("/search")
	public ResponseEntity<List<Property>> searchProperties(
			@RequestParam(name = "location", required = false) String location,
			@RequestParam(name = "minPrice", required = false) Double minPrice,
			@RequestParam(name = "maxPrice", required = false) Double maxPrice,
			@RequestParam(name = "typeId", required = false) Integer typeId) {

		List<Property> results = propertyService.searchProperties(location, minPrice, maxPrice, typeId);
		return ResponseEntity.ok(results);
	}

	@GetMapping // API for get All Properties
	public ResponseEntity<List<Property>> getAllProperties() {
		return ResponseEntity.ok(propertyService.getAllProperties());
	}

//	@PostMapping
//	public ResponseEntity<?> addProperty(@Valid @RequestBody Property property) {
//	    try {
//	        Property saved = propertyService.addProperty(property);
//	        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
//	    } catch (Exception e) {
//	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//	    }
//	}

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> addProperty(@RequestParam("title") String title,
			@RequestParam("description") String description, @RequestParam("price") double price,
			@RequestParam("location") String location, @RequestParam("approved") boolean approved,
			@RequestParam("seller.id") int sellerId, @RequestParam("manager.id") int managerId,
			@RequestParam("type.id") int typeId, @RequestParam("imageFile") MultipartFile imageFile) {
		try {
			// 1. Save image to disk (or wherever)
			String imageUrl = propertyService.storeImage(imageFile);

			// 2. Load related entities by their IDs
			User seller = userService.getUserById(sellerId);

			User manager = userService.getUserById(managerId);
			PropertyType type = propertyTypeService.findById(typeId)
					.orElseThrow(() -> new RuntimeException("Property type not found"));

			// 3. Create Property entity
			Property property = new Property();
			property.setTitle(title);
			property.setDescription(description);
			property.setPrice(price);
			property.setLocation(location);
			property.setApproved(approved);
			property.setImageUrl(imageUrl);
			property.setSeller(seller);
			property.setManager(manager);
			property.setType(type);

			// 4. Save property
			Property saved = propertyService.addProperty(property);

			return ResponseEntity.status(HttpStatus.CREATED).body(saved);

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

//	@PutMapping("/{id}")
//	public ResponseEntity<?> updateProperty(@PathVariable Integer id, @Valid @RequestBody Property property) {
//	    try {
//	        Property updated = propertyService.updateProperty(id, property);
//	        return ResponseEntity.ok(updated);
//	    } catch (RuntimeException e) {
//	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
//	    } catch (Exception e) {
//	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Update failed");
//	    }
//	}
	@PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> updateProperty(
	    @PathVariable("id") Integer id,
	    @RequestParam("title") String title,
	    @RequestParam("description") String description,
	    @RequestParam("price") double price,
	    @RequestParam("location") String location,
	    @RequestParam("approved") boolean approved,
	    @RequestParam("sellerId") int sellerId,
	    @RequestParam("managerId") int managerId,
	    @RequestParam("typeId") int typeId,
	    @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {

	    try {
	        // 1. Fetch existing property by ID
	        Property existingProperty = propertyService.getPropertyById(id)
	            .orElseThrow(() -> new RuntimeException("Property not found with id: " + id));

	        // 2. Update basic fields
	        existingProperty.setTitle(title);
	        existingProperty.setDescription(description);
	        existingProperty.setPrice(price);
	        existingProperty.setLocation(location);
	        existingProperty.setApproved(approved);

	        // 3. Handle image update
	        if (imageFile != null && !imageFile.isEmpty()) {
	            String imageUrl = propertyService.storeImage(imageFile);
	            existingProperty.setImageUrl(imageUrl);
	        }

	        // 4. Load related entities
	        User seller = userService.getUserById(sellerId);
	        User manager = userService.getUserById(managerId);
	        PropertyType type = propertyTypeService.findById(typeId)
	            .orElseThrow(() -> new RuntimeException("Property type not found"));

	        existingProperty.setSeller(seller);
	        existingProperty.setManager(manager);
	        existingProperty.setType(type);

	        // 5. Persist updated property
	        Property updated = propertyService.updateProperty(id, existingProperty);
	        return ResponseEntity.ok(updated);

	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Update failed: " + e.getMessage());
	    }
	}


	// Delete property
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProperty(@PathVariable("id") Integer id,
			@RequestParam("sellerId") Integer sellerId) {
		try {
			propertyService.deleteProperty(id, sellerId);
			return ResponseEntity.noContent().build();
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@GetMapping("/approved") // API for Approved Properties Only By Manager
	public ResponseEntity<List<Property>> getProperties() {
		List<Property> results = propertyService.getApprovedProperties();
		return ResponseEntity.ok(results);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getPropertyById(@PathVariable("id") Integer id) {
		Optional<Property> propertyOpt = propertyService.getPropertyById(id);
		if (propertyOpt.isPresent()) {
			return ResponseEntity.ok(propertyOpt.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Property not found");
		}
	}

	@GetMapping("/seller/{sellerId}") // Get all properties for a seller
	public ResponseEntity<?> getPropertiesBySeller(@PathVariable("sellerId") Integer sellerId) {
		return ResponseEntity.ok(propertyService.getPropertiesBySeller(sellerId));
	}

	@GetMapping("/available")
	public ResponseEntity<List<Property>> getAvailableProperties() {
		List<Property> properties = propertyService.getPropertiesWithoutCompletedTransactions();
		return ResponseEntity.ok(properties);
	}

	@GetMapping("/approved/incomplete-transactions")
	public List<Property> getApprovedPropertiesWithIncompleteTransactions() {
		return propertyService.getApprovedPropertiesWithIncompleteTransactions();
	}

}
