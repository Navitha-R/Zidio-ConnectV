package com.zidio.jobportal.DTO;

import java.util.List;

import com.zidio.jobportal.Enum.SubscriptionDuration;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionPlanDTO {
	 private Long id;
	    private String planName;
	    private Double price;
	    private String currency;
	    private SubscriptionDuration duration;
	    private Integer validityDays;
	    private Boolean isActive;
	    private List<String> features;
	    private String description;

}
