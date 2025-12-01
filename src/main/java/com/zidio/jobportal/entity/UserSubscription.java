package com.zidio.jobportal.entity;
import java.time.LocalDateTime;

import com.zidio.jobportal.Enum.SubscriptionDuration;
import com.zidio.jobportal.Enum.UserType;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_subscription",
       indexes = {@Index(name="idx_user_active", columnList = "userId, active")})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSubscription {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String planName;      // e.g. FREE, PREMIUM, STARTER

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private String currency;      // INR, USD

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubscriptionDuration duration;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType forUserType; // JOBSEEKER / RECRUITER

    // Recruiter features
    private Integer allowedJobPosts;     // allowed posts per duration
    private Integer allowedResumeViews;  // allowed resume downloads/views per duration

    // Job seeker features
    private Boolean profileBoost;        // true if profile is boosted
    private Integer inMailCredits;       // message credits

    @Column(columnDefinition = "TEXT")
    private String features;             // human readable feature summary (or JSON)

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
