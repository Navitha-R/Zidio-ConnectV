package com.zidio.jobportal.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name="notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notifications {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id;
	
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "jobseeker_id")
	private Jobseeker jobseeker;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="recruiter_id")
    private Recruiter recruiter;
    
    @Column(nullable=false) 
    private String message;
    
    @Column(nullable=false)
    private boolean readStatus = false;
    
    private LocalDateTime createdAt;
   
    @PrePersist
    public void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
} 
    
	 
	 
	


