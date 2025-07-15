package com.realestate.service;

import com.realestate.model.Property;
import java.util.List;

public interface ManagerService {
    abstract Property approveProperty(int propertyId);
    abstract Property editProperty(int propertyId, Property updatedData);
//    abstract Property removeProperty(int propertyId);
    void deleteProperty(int propertyId);
    List<Property> getPendingProperties();
    List<Property> getApprovedProperties();
}
