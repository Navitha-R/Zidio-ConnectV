package com.zidio.jobportal.DTO;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class InvoiceItem {
	    private String description;
	    private int quantity;
	    private BigDecimal unitPrice;

	    public BigDecimal getLineTotal() {
	        if (unitPrice == null) return BigDecimal.ZERO;
	        return unitPrice.multiply(BigDecimal.valueOf(quantity));
	    }
	}


