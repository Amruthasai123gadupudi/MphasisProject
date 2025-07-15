package com.realestate.service;

import java.util.List;

import com.realestate.model.ViewedProperty;

public interface ViewPropertyService {
	abstract ViewedProperty logView(ViewedProperty view);
	abstract List<ViewedProperty> getViewedByBuyer(Integer buyerId);

}
