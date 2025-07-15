package com.realestate.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

public interface AnalyticsService {
    List<Map<String, Object>> getTop3SellersWithUser();
}
