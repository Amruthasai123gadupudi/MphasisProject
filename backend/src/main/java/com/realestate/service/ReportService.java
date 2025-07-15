package com.realestate.service;

import java.util.List;
import java.util.Map;

public interface ReportService {
    List<Map<String, Object>> getUserRoleReport();
    List<Map<String, Object>> getPropertyApprovalReport();
    List<Map<String, Object>> getTransactionStatusReport();
    Map<String, Object> generatePlatformPerformanceReport();
    Map<String, Object> generateSalesPerformanceReport();
}