package com.realestate.service;

import com.realestate.repository.PropertyRepository;
import com.realestate.repository.TransactionRepository;
import com.realestate.repository.UserRepository;
import com.realestate.service.ReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private PropertyRepository propertyRepo;
	@Autowired
	private TransactionRepository transactionRepo;

	@Override
	public List<Map<String, Object>> getUserRoleReport() {
		List<Object[]> raw = userRepo.countUsersByRole();
		return convertToMap(raw, "role", "count");
	}

	@Override
	public List<Map<String, Object>> getPropertyApprovalReport() {
		List<Object[]> raw = propertyRepo.countPropertiesByApproval();
		return convertToMap(raw, "approved", "count");
	}

	@Override
	public List<Map<String, Object>> getTransactionStatusReport() {
		List<Object[]> raw = transactionRepo.countTransactionsByStatus();
		return convertToMap(raw, "status", "count");
	}

	public Map<String, Object> generatePlatformPerformanceReport() {
		long totalUsers = userRepo.countTotalUsers();
		long totalProperties = propertyRepo.countTotalProperties();
		long totalSellers = userRepo.countSellers();
		long totalBuyers = userRepo.countBuyers();
		long totalActiveProperties = propertyRepo.countApprovedProperties();

		Map<String, Object> report = new HashMap<>();
		report.put("totalUsers", totalUsers);
		report.put("totalProperties", totalProperties);
		report.put("totalSellers", totalSellers);
		report.put("totalBuyers", totalBuyers);
		report.put("totalActiveProperties", totalActiveProperties);

		return report;
	}

	// Sales Performance Report (New)
	public Map<String, Object> generateSalesPerformanceReport() {
		long totalPropertiesSold = transactionRepo.countTotalSales();
		double totalSalesAmount = transactionRepo.countTotalSalesAmount();
		long totalSalesByManager = transactionRepo.countTotalSalesByManager();

		Map<String, Object> report = new HashMap<>();
		report.put("totalSalesAmount", totalSalesAmount);
		report.put("totalPropertiesSold", totalPropertiesSold);
		report.put("totalSalesByManager", totalSalesByManager);

		return report;
	}

	private List<Map<String, Object>> convertToMap(List<Object[]> raw, String keyLabel, String valueLabel) {
		List<Map<String, Object>> result = new ArrayList<>();
		for (Object[] row : raw) {
			Map<String, Object> map = new HashMap<>();
			map.put(keyLabel, row[0]);
			map.put(valueLabel, row[1]);
			result.add(map);
		}
		return result;
	}
}