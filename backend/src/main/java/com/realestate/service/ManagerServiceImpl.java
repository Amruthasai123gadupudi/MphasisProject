package com.realestate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realestate.model.Property;
import com.realestate.repository.PropertyRepository;
import com.realestate.service.ManagerService;

@Service
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private PropertyRepository propertyRepository;

    @Override
    public Property approveProperty(int propertyId) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));
        property.setApproved(true);
        return propertyRepository.save(property);
    }

    @Override
    public Property editProperty(int propertyId, Property updatedData) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        property.setTitle(updatedData.getTitle());
        property.setDescription(updatedData.getDescription());
        property.setPrice(updatedData.getPrice());
        property.setLocation(updatedData.getLocation());
        property.setImageUrl(updatedData.getImageUrl());
        property.setType(updatedData.getType());
        property.setApproved(updatedData.isApproved());
        return propertyRepository.save(property);
    }
//
//    @Override
//    public Property removeProperty(int propertyId) {
//        Property property = propertyRepository.findById(propertyId)
//                .orElseThrow(() -> new RuntimeException("Property not found"));
//
//        property.setApproved(false);
//        property.setTitle("[REMOVED]");
//        property.setDescription("This listing has been removed.");
//
//        return propertyRepository.save(property);
//    }

    @Override
    public void deleteProperty(int propertyId) {
        propertyRepository.deleteById(propertyId);
    }

    @Override
    public List<Property> getPendingProperties() {
        return propertyRepository.findByApprovedFalse();
    }

	@Override
	public List<Property> getApprovedProperties() {
		return propertyRepository.findByApprovedTrue();
		
	}
}
