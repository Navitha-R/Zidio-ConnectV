package com.zidio.jobportal.DTO;

import java.time.LocalDateTime;
import java.util.List;

import com.zidio.jobportal.Enum.SubscriptionDuration;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionStatusDTO {
	 private Long id;
	    private String planName;
	    private Double price;
	    private String currency;

	    private SubscriptionDuration duration; 
	    private Integer validityDays;

	    private Boolean isActive;      // Active status
	    private Boolean isInactive;    // Derived field (not from DB)

	    private List<String> features;   
	    private String description;

	    private LocalDateTime createdAt;

}
