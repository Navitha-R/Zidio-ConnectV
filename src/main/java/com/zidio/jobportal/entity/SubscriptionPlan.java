package com.zidio.jobportal.entity;
import java.time.LocalDateTime;
import java.util.List;

import com.zidio.jobportal.Enum.SubscriptionDuration;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="subscription_plan")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionPlan {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable = false, unique = true)
	    private String planName;  
	    // Example: "Premium", "Pro", "Gold", "Resume Booster"

	    @Column(nullable = false)
	    private Double price;  
	    // Final price after tax (optional — you can store tax also)

	    @Column(nullable = false)
	    private String currency;  
	    // "INR", "USD"

	    @Enumerated(EnumType.STRING)
	    private SubscriptionDuration duration; 
	    // MONTHLY, YEARLY, QUARTERLY

	    private Integer validityDays;  
	    // Ex: 30, 90, 365 → real world job portals use this

	    private Boolean isActive;  // Admin can enable/disable plan without deleting
	    
	    @ElementCollection
	    @CollectionTable(
	        name = "subscription_features",
	        joinColumns = @JoinColumn(name = "plan_id")
	    )
	    @Column(name = "feature")
	    private List<String> features; // Stored like,premium,profile boost,direct apply,unlimitedJobApplication,priority support.
	    private String description;
	    private LocalDateTime createdAt;

	    @PrePersist
	    public void onCreate() {
	        this.createdAt = LocalDateTime.now();
	    }
}
