package com.zidio.jobportal.entity;

import lombok.*;

import java.time.LocalDateTime;

import com.zidio.jobportal.Enum.PaymentStatus;
import com.zidio.jobportal.Enum.PaymentType;

import jakarta.persistence.*;

@Entity
@Table(name="payment")

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String userId;
	private Long planId;
	private Double amount;
	private String currency;
	
	@Enumerated(EnumType.STRING)
	private PaymentStatus paymentStatus;// // PENDING, COMPLETED, FAILED
	private String transactionId;// From Razorpay/Stripe/PayPal etc.
	
	 @Enumerated(EnumType.STRING)
	private PaymentType paymentType;
	private LocalDateTime timeStamp;
	
	 @PrePersist
	    public void onCreate() {
	        this.timeStamp = LocalDateTime.now();
	    }
	
	

	
}
