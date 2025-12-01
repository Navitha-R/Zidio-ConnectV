package com.zidio.jobportal.DTO;

import com.zidio.jobportal.Enum.PaymentType;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequestDTO {
	private String userId;        // Who is making the payment
    private Long planId;          // Subscription / plan / course plan
    private Double amount;        // Amount to be charged
    private String currency;      // INR, USD, etc.
    private PaymentType paymentType; // UPI, CARD, NET_BANKING
    private InvoiceRequestDTO invoiceRequest; 
}
