package com.realestate.service;
 
import java.util.Optional;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.realestate.model.PropertyType;
import com.realestate.repository.PropertyTypeRepository;
 
@Service
public class PropertyTypeService {
 
    @Autowired
    private PropertyTypeRepository propertyTypeRepository;
 
    public Optional<PropertyType> findById(int id) {
        return propertyTypeRepository.findById(id);
    }
}