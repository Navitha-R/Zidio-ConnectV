package com.zidio.jobportal.entity;
import java.time.LocalDateTime;

import com.zidio.jobportal.Enum.JobType;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="job_posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class JobPosts {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="recruiter_id", nullable= false)
	private Recruiter recruiter;
	
	@Column(nullable = false)
	private String jobTitle; 
	
	@Column(nullable=false)
	private String jobDescription;
	
	@Column(nullable=false)
	private String companyName;
	
	@Column(nullable=false)
	private String jobLocation;
	
	@Enumerated(EnumType.STRING)
	private JobType jobType;
	private String remote;
	private Double salary;
	private String experienceLevel;
	private String skillsRequired;
	@Builder.Default
	private boolean active=true;
	private LocalDateTime postedDate;
	private LocalDateTime deadlineDate;
	@PrePersist
    public void prePersist() {
        if (postedDate == null) {
            postedDate = LocalDateTime.now();
        }
        if (deadlineDate == null) {
            deadlineDate = postedDate.plusDays(30); // default deadline 30 days later
        }
    }
	
	
}

