package com.realestate.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realestate.model.PropertyType;
import com.realestate.repository.PropertyTypeRepository;

@Service
public class PlatformConfigServiceImpl implements PlatformConfigService {
	@Autowired
    private PropertyTypeRepository repo;

    @Override
    public PropertyType createCategory(PropertyType type) {
        if (repo.existsByName(type.getName())) {
            throw new RuntimeException("Category already exists");
        }
        return repo.save(type);
    }

    @Override
    public PropertyType updateCategory(Integer id, PropertyType type) {
        PropertyType existing = repo.findById(id)
            .orElseThrow(() -> new RuntimeException("Category not found"));
        if (!existing.getName().equals(type.getName())
            && repo.existsByName(type.getName())) {
            throw new RuntimeException("Category name already used");
        }
        existing.setName(type.getName());
        return repo.save(existing);
    }

    @Override
    public void deleteCategory(Integer id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Category not found");
        }
        repo.deleteById(id);
    }

    @Override
    public List<PropertyType> getAllCategories() {
        return repo.findAll();
    }
    private final List<String> rules = new ArrayList();
	@Override
	public List<String> getRules() {
		// TODO Auto-generated method stub
		return new ArrayList<>(rules);
	}
}