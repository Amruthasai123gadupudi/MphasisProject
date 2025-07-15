package com.realestate.service;

import com.realestate.model.Rule;
import com.realestate.repository.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleService {

    @Autowired
    private RuleRepository ruleRepository;

    public Rule saveRule(Rule rule) {
        return ruleRepository.save(rule);
    }

    public List<Rule> getAllRules() {
        return ruleRepository.findAll();
    }

    public void deleteRule(Long id) {
        ruleRepository.deleteById(id);
    }

    public Rule getRuleById(Long id) {
        return ruleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rule not found with id: " + id));
    }

	public List<Rule> getAllRules(Rule.Role role) {
		// TODO Auto-generated method stub
		return ruleRepository.findAllByTargetRole(role);
	}
}