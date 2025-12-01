package com.zidio.jobportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.zidio.jobportal.entity.SubscriptionPlan;

public interface SubscriptionPlanRepository extends JpaRepository<SubscriptionPlan, Long> {

	 boolean existsByPlanName(String planName);

	    List<SubscriptionPlan> findByIsActiveTrue();
}
