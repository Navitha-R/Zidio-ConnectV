package com.zidio.jobportal.DTO;

import lombok.Data;
import java.util.List;

@Data
public class InvoiceRequestDTO {

	    private String invoiceNumber;
	    private String currency;
	    private String paymentMethod;

	    private BillingInfo billingInfo;
	    private List<InvoiceItem> items;

	    private double taxPercent;
	    private double discountAmount;

	    private String notes;
}

	


