package com.zidio.jobportal.DTO;
import lombok.*;
import com.zidio.jobportal.Enum.PaymentStatus;
import com.zidio.jobportal.Enum.PaymentType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponseDTO {
	 private Long paymentId;
	    private String userId;
	    private Long planId;
	    private Double amount;
	    private String currency;

	    private PaymentStatus paymentStatus;   // PENDING / COMPLETED / FAILED
	    private PaymentType paymentType;       // UPI / CARD / NET_BANKING

	    private String transactionId;          // Razorpay/Stripe transaction reference
	    private String timeStamp;              // Time of transaction
}
