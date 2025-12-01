package com.zidio.jobportal.Enum;

public enum PaymentStatus {
	    PENDING,    // Payment has been initiated but not completed
	    SUCCESS,  // Payment was successful
	    FAILED,     // Payment failed due to some error
	    CANCELLED,  // Payment was cancelled by user or system
	    REFUNDED    // Payment has been refunded

}
