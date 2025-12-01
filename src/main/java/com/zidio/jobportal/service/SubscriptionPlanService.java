package com.zidio.jobportal.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.zidio.jobportal.DTO.SubscriptionPlanDTO;
import com.zidio.jobportal.entity.SubscriptionPlan;
import com.zidio.jobportal.repository.SubscriptionPlanRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubscriptionPlanService {
	 
	 private final SubscriptionPlanRepository subscriptionPlanRepository;

	    // Create new plan
	    public SubscriptionPlan createPlan(SubscriptionPlanDTO dto) {

	        if (subscriptionPlanRepository.existsByPlanName(dto.getPlanName())) {
	            throw new RuntimeException("Plan with name '" + dto.getPlanName() + "' already exists");
	        }

	        SubscriptionPlan plan = SubscriptionPlan.builder()
	                .planName(dto.getPlanName())
	                .price(dto.getPrice())
	                .currency(dto.getCurrency())
	                .duration(dto.getDuration())
	                .validityDays(dto.getValidityDays())
	                .isActive(true)
	                .features(dto.getFeatures())
	                .description(dto.getDescription())
	                .build();

	        return subscriptionPlanRepository.save(plan);
	    }

	    // Fetch all plans
	    public List<SubscriptionPlan> getAllPlans() {
	        return subscriptionPlanRepository.findAll();
	    }

	    // Fetch Active Plans
	    public List<SubscriptionPlan> getActivePlans() {
	        return subscriptionPlanRepository.findByIsActiveTrue();
	    }

	    // Fetch plan by ID
	    public SubscriptionPlan getPlanById(Long id) {
	        Optional<SubscriptionPlan> planOpt = subscriptionPlanRepository.findById(id);

	        if (planOpt.isEmpty()) {
	            throw new RuntimeException("Plan with ID " + id + " not found");
	        }
	        return planOpt.get();
	    }

	    // Update plan
	    public SubscriptionPlan updatePlan(Long id, SubscriptionPlanDTO dto) {

	        SubscriptionPlan plan = getPlanById(id);

	        plan.setPlanName(dto.getPlanName());
	        plan.setPrice(dto.getPrice());
	        plan.setCurrency(dto.getCurrency());
	        plan.setDuration(dto.getDuration());
	        plan.setValidityDays(dto.getValidityDays());
	        plan.setIsActive(dto.getIsActive());
	        plan.setFeatures(dto.getFeatures());
	        plan.setDescription(dto.getDescription());

	        return subscriptionPlanRepository.save(plan);
	    }

	    // Soft delete
	    public void deletePlan(Long id) {
	        SubscriptionPlan plan = getPlanById(id);
	        plan.setIsActive(false);
	        subscriptionPlanRepository.save(plan);
	    }

}
