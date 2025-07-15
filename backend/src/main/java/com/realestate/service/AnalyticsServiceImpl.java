package com.realestate.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realestate.model.User;
import com.realestate.repository.TransactionRepository;
import com.realestate.repository.UserRepository;
import com.realestate.service.AnalyticsService;
@Service
public class AnalyticsServiceImpl implements AnalyticsService{
	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private UserRepository userRepository;
	@Override
	public List<Map<String, Object>> getTop3SellersWithUser() {
		 List<Object[]> results = transactionRepository.findTop3SellerIdsWithCompletedCount();

		    return results.stream().map(row -> {
		        Integer sellerId = ((Number) row[0]).intValue();
		        Long count = ((Number) row[1]).longValue();

		        User seller = userRepository.findById(sellerId)
		                .orElseThrow(() -> new RuntimeException("Seller not found"));

		        Map<String, Object> map = new HashMap<>();
		        map.put("sellerId", seller.getId());
		        map.put("name", seller.getName());
		        map.put("email", seller.getEmail());
		        map.put("completedTransactions", count);
		        return map;
		    }).collect(Collectors.toList());
		
	}

}
