package com.zidio.jobportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zidio.jobportal.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {
	 // 1. Find all payments made by a user
    List<Payment> findByUserId(String userId);

    // 2. Find payment using gateway transaction ID
    Payment findByTransactionId(String transactionId);

    // 3. Check if transaction already exists (avoid duplicates)
    boolean existsByTransactionId(String transactionId);

}
