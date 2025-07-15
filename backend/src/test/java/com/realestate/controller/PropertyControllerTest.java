package com.realestate.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import com.realestate.model.Property;
import com.realestate.model.PropertyType;
import com.realestate.model.User;
import com.realestate.service.PropertyService;
import com.realestate.service.PropertyTypeService;
import com.realestate.service.UserService;

class PropertyControllerTest {

    @InjectMocks
    private PropertyController propertyController;

    @Mock
    private PropertyService propertyService;

    @Mock
    private UserService userService;

    @Mock
    private PropertyTypeService propertyTypeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProperties() {
        Property prop1 = new Property();
        Property prop2 = new Property();

        when(propertyService.getAllProperties()).thenReturn(Arrays.asList(prop1, prop2));

        ResponseEntity<List<Property>> response = propertyController.getAllProperties();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(propertyService, times(1)).getAllProperties();
    }

    @Test
    void testGetPropertyById_found() {
        Property prop = new Property();
        prop.setId(1);

        when(propertyService.getPropertyById(1)).thenReturn(Optional.of(prop));

        ResponseEntity<?> response = propertyController.getPropertyById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(prop, response.getBody());
    }

    @Test
    void testGetPropertyById_notFound() {
        when(propertyService.getPropertyById(1)).thenReturn(Optional.empty());

        ResponseEntity<?> response = propertyController.getPropertyById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Property not found", response.getBody());
    }

    @Test
    void testAddProperty_success() throws Exception {
        // Prepare inputs
        String title = "Title";
        String description = "Desc";
        double price = 100.0;
        String location = "Loc";
        boolean approved = true;
        int sellerId = 1;
        int managerId = 2;
        int typeId = 3;

        MockMultipartFile file = new MockMultipartFile("imageFile", "image.jpg", "image/jpeg", "dummy content".getBytes());

        User seller = new User();
        seller.setId(sellerId);

        User manager = new User();
        manager.setId(managerId);

        PropertyType type = new PropertyType();
        type.setId(typeId);

        when(propertyService.storeImage(file)).thenReturn("url-to-image");
        when(userService.getUserById(sellerId)).thenReturn(seller);
        when(userService.getUserById(managerId)).thenReturn(manager);
        when(propertyTypeService.findById(typeId)).thenReturn(Optional.of(type));

        Property savedProperty = new Property();
        savedProperty.setTitle(title);
        savedProperty.setDescription(description);
        savedProperty.setPrice(price);
        savedProperty.setLocation(location);
        savedProperty.setApproved(approved);
        savedProperty.setImageUrl("url-to-image");
        savedProperty.setSeller(seller);
        savedProperty.setManager(manager);
        savedProperty.setType(type);

        when(propertyService.addProperty(any(Property.class))).thenReturn(savedProperty);

        ResponseEntity<?> response = propertyController.addProperty(title, description, price, location, approved, sellerId, managerId, typeId, file);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Property result = (Property) response.getBody();
        assertEquals(title, result.getTitle());
        assertEquals("url-to-image", result.getImageUrl());

        verify(propertyService, times(1)).storeImage(file);
        verify(userService, times(1)).getUserById(sellerId);
        verify(userService, times(1)).getUserById(managerId);
        verify(propertyTypeService, times(1)).findById(typeId);
        verify(propertyService, times(1)).addProperty(any(Property.class));
    }

    @Test
    void testDeleteProperty_success() {
        Integer id = 1;
        Integer sellerId = 10;

        // no exception expected
        doNothing().when(propertyService).deleteProperty(id, sellerId);

        ResponseEntity<?> response = propertyController.deleteProperty(id, sellerId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(propertyService, times(1)).deleteProperty(id, sellerId);
    }

    @Test
    void testDeleteProperty_notFound() {
        Integer id = 1;
        Integer sellerId = 10;

        doThrow(new RuntimeException("Property not found")).when(propertyService).deleteProperty(id, sellerId);

        ResponseEntity<?> response = propertyController.deleteProperty(id, sellerId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Property not found", response.getBody());
    }

    // You can add more tests for updateProperty, getPropertiesBySeller, getAvailableProperties similarly
}
