package com.zidio.jobportal.entity;
import java.time.LocalDateTime;

import com.zidio.jobportal.Enum.ApplicationStatus;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name="applications")

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Application {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;
  
  //linking to jobseeker,jobposts and recruiter ID's
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "jobseeker_id", nullable = false)
  private Jobseeker jobSeeker;
  //link to jobpost
  @ManyToOne(fetch= FetchType.LAZY)
  @JoinColumn(name="job_id", nullable=false)
  private JobPosts job;
  
  
  
  //link to recruiter(who owns the job)
  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="recruiter_id", nullable=false)
  private Recruiter recruiter;
  
  @Enumerated(EnumType.STRING)
  private ApplicationStatus status;
  
  private LocalDateTime appliedDate;
  
  @PrePersist
  public void prePersist() {
	  if(appliedDate==null) {
		  appliedDate=LocalDateTime.now();
	  }
	  if(status==null) {
		  status=ApplicationStatus.APPLIED;
	  }
  }

}
