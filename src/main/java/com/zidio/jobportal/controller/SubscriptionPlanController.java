package com.zidio.jobportal.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zidio.jobportal.DTO.SubscriptionPlanDTO;
import com.zidio.jobportal.entity.SubscriptionPlan;
import com.zidio.jobportal.service.SubscriptionPlanService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/subscription")
@RequiredArgsConstructor
@CrossOrigin("*")
public class SubscriptionPlanController {
	 private final SubscriptionPlanService subscriptionPlanService;

	    @PostMapping("/create")
	    public ResponseEntity<SubscriptionPlan> createPlan(@RequestBody SubscriptionPlanDTO dto) {
	        return ResponseEntity.ok(subscriptionPlanService.createPlan(dto));
	    }

	    @GetMapping
	    public ResponseEntity<List<SubscriptionPlan>> getAllPlans() {
	        return ResponseEntity.ok(subscriptionPlanService.getAllPlans());
	    }

	    @GetMapping("/active")
	    public ResponseEntity<List<SubscriptionPlan>> getActivePlans() {
	        return ResponseEntity.ok(subscriptionPlanService.getActivePlans());
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<SubscriptionPlan> getPlanById(@PathVariable Long id) {
	        return ResponseEntity.ok(subscriptionPlanService.getPlanById(id));
	    }

	    @PutMapping("/update/{id}")
	    public ResponseEntity<SubscriptionPlan> updatePlan(
	            @PathVariable Long id,
	            @RequestBody SubscriptionPlanDTO dto) {
	        return ResponseEntity.ok(subscriptionPlanService.updatePlan(id, dto));
	    }

	    @DeleteMapping("/delete/{id}")
	    public ResponseEntity<String> deletePlan(@PathVariable Long id) {
	        subscriptionPlanService.deletePlan(id);
	        return ResponseEntity.ok("Subscription plan deactivated successfully.");
	    }
}
