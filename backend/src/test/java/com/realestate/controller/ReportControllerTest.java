package com.realestate.controller;

import com.realestate.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class ReportControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ReportService reportService;

    @InjectMocks
    private ReportController reportController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(reportController).build();
    }

    @Test
    void testGetUserRoleReport() throws Exception {
        List<Map<String, Object>> mockReport = new ArrayList<>();
        Map<String, Object> data = new HashMap<>();
        data.put("role", "ADMIN");
        data.put("count", 5);
        mockReport.add(data);

        when(reportService.getUserRoleReport()).thenReturn(mockReport);

        mockMvc.perform(get("/api/admin/reports/user-roles"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].role").value("ADMIN"))
                .andExpect(jsonPath("$[0].count").value(5));

        verify(reportService, times(1)).getUserRoleReport();
    }

    @Test
    void testGetPropertyApprovalReport() throws Exception {
        List<Map<String, Object>> mockReport = new ArrayList<>();
        Map<String, Object> data = new HashMap<>();
        data.put("status", "APPROVED");
        data.put("count", 10);
        mockReport.add(data);

        when(reportService.getPropertyApprovalReport()).thenReturn(mockReport);

        mockMvc.perform(get("/api/admin/reports/property-approval"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].status").value("APPROVED"))
                .andExpect(jsonPath("$[0].count").value(10));

        verify(reportService, times(1)).getPropertyApprovalReport();
    }

    @Test
    void testGetTransactionStatusReport() throws Exception {
        List<Map<String, Object>> mockReport = new ArrayList<>();
        Map<String, Object> data = new HashMap<>();
        data.put("status", "COMPLETED");
        data.put("count", 7);
        mockReport.add(data);

        when(reportService.getTransactionStatusReport()).thenReturn(mockReport);

        mockMvc.perform(get("/api/admin/reports/transaction-status"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].status").value("COMPLETED"))
                .andExpect(jsonPath("$[0].count").value(7));

        verify(reportService, times(1)).getTransactionStatusReport();
    }

    @Test
    void testGetPlatformPerformanceReport() throws Exception {
        Map<String, Object> mockReport = new HashMap<>();
        mockReport.put("uptime", 99.9);
        mockReport.put("responseTime", 120);

        when(reportService.generatePlatformPerformanceReport()).thenReturn(mockReport);

        mockMvc.perform(get("/api/admin/reports/platform-performance"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.uptime").value(99.9))
                .andExpect(jsonPath("$.responseTime").value(120));

        verify(reportService, times(1)).generatePlatformPerformanceReport();
    }

    @Test
    void testGetSalesPerformanceReport() throws Exception {
        Map<String, Object> mockReport = new HashMap<>();
        mockReport.put("totalSales", 50000);
        mockReport.put("salesGrowth", 5.5);

        when(reportService.generateSalesPerformanceReport()).thenReturn(mockReport);

        mockMvc.perform(get("/api/admin/reports/sales-performance"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalSales").value(50000))
                .andExpect(jsonPath("$.salesGrowth").value(5.5));

        verify(reportService, times(1)).generateSalesPerformanceReport();
    }
}
