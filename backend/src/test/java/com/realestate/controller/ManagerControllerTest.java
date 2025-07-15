package com.realestate.controller;
 
import com.realestate.model.Property;
import com.realestate.model.PropertyType;
import com.realestate.model.User;
import com.realestate.service.ManagerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
 
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
 
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
 
@WebMvcTest(ManagerController.class)
public class ManagerControllerTest {
 
    @Autowired
    private MockMvc mockMvc;
 
    @MockBean
    private ManagerService managerService;
 
    private Property sampleProperty;
 
    @BeforeEach
    public void setup() {
        User seller = new User();
        seller.setId(1);
        seller.setName("John Doe");
 
        PropertyType type = new PropertyType();
        type.setId(1);
        type.setName("Apartment");
 
        sampleProperty = new Property();
        sampleProperty.setId(1);
        sampleProperty.setTitle("Test Property");
        sampleProperty.setDescription("A beautiful test property.");
        sampleProperty.setPrice(100000.0);
        sampleProperty.setLocation("Test City");
        sampleProperty.setImageUrl("http://example.com/image.jpg");
        sampleProperty.setApproved(false);
        sampleProperty.setCreatedAt(LocalDateTime.now());
        sampleProperty.setSeller(seller);
        sampleProperty.setType(type);
        sampleProperty.setManager(null); // Optional
    }
 
    @Test
    public void testApproveProperty() throws Exception {
        Mockito.when(managerService.approveProperty(1)).thenReturn(sampleProperty);
 
        mockMvc.perform(put("/api/manager/approve/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Property"))
                .andExpect(jsonPath("$.approved").value(false));
    }
 
    @Test
    public void testEditProperty() throws Exception {
        Mockito.when(managerService.editProperty(anyInt(), any(Property.class))).thenReturn(sampleProperty);
 
        String json = """
            {
              "title": "Test Property",
              "description": "A beautiful test property.",
              "price": 100000.0,
              "location": "Test City",
              "imageUrl": "http://example.com/image.jpg",
              "approved": false
            }
        """;
 
        mockMvc.perform(put("/api/manager/edit/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Property"));
    }
 
    @Test
    public void testDeleteProperty() throws Exception {
        mockMvc.perform(delete("/api/manager/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted successfully"));
    }
 
    @Test
    public void testGetPendingProperties() throws Exception {
        List<Property> pending = Arrays.asList(sampleProperty);
        Mockito.when(managerService.getPendingProperties()).thenReturn(pending);
 
        mockMvc.perform(get("/api/manager/pending"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }
 
    @Test
    public void testGetApprovedProperties() throws Exception {
        List<Property> approved = Arrays.asList(sampleProperty);
        Mockito.when(managerService.getApprovedProperties()).thenReturn(approved);
 
        mockMvc.perform(get("/api/manager/approved"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }
 
    @Test
    public void testGetCombinedProperties() throws Exception {
        List<Property> approved = Arrays.asList(sampleProperty);
        List<Property> pending = Arrays.asList(sampleProperty);
 
        Mockito.when(managerService.getApprovedProperties()).thenReturn(approved);
        Mockito.when(managerService.getPendingProperties()).thenReturn(pending);
 
        mockMvc.perform(get("/api/manager/combined"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.approved", hasSize(1)))
                .andExpect(jsonPath("$.pending", hasSize(1)));
    }
}