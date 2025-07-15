package com.realestate.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realestate.service.ReportService;

@RestController
@RequestMapping("/api/admin/reports")
public class ReportController {

	@Autowired
	private ReportService reportService;

	@GetMapping("/user-roles")
	public List<Map<String, Object>> getUserRoleReport() {
		return reportService.getUserRoleReport();
	}

	@GetMapping("/property-approval")
	public List<Map<String, Object>> getPropertyApprovalReport() {
		return reportService.getPropertyApprovalReport();
	}

	@GetMapping("/transaction-status")
	public List<Map<String, Object>> getTransactionStatusReport() {
		return reportService.getTransactionStatusReport();
	}

	@GetMapping("/platform-performance")
    public Map<String, Object> getPlatformPerformanceReport() {
        return reportService.generatePlatformPerformanceReport();
    }

    @GetMapping("/sales-performance")
    public Map<String, Object> getSalesPerformanceReport() {
        return reportService.generateSalesPerformanceReport();
    }
}