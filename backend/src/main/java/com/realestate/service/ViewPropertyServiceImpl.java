package com.realestate.service;



import java.util.List;

import org.springframework.stereotype.Service;

import com.realestate.model.ViewedProperty;
import com.realestate.repository.ViewedPropertyRepository;
import com.realestate.service.ViewPropertyService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ViewPropertyServiceImpl implements ViewPropertyService {

    private final ViewedPropertyRepository viewedPropertyRepository;

    @Override
    public ViewedProperty logView(ViewedProperty view) {
        return viewedPropertyRepository.save(view);
    }

    @Override
    public List<ViewedProperty> getViewedByBuyer(Integer buyerId) {
        return viewedPropertyRepository.findByBuyerId(buyerId);
    }
}
