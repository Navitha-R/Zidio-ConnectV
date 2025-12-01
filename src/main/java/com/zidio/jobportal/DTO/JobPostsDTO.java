package com.zidio.jobportal.DTO;

import java.time.LocalDateTime;

import com.zidio.jobportal.Enum.JobType;
import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class JobPostsDTO {

	
	private String jobTitle;
	private String jobDescription;
	private String companyName;
	private String jobLocation;
	private String remote;
	private double salary;
	private String experienceLevel;
	private String skillsRequired;
	private JobType jobtype;
	private LocalDateTime postedDate;
	private LocalDateTime deadlineDate;
	
	
}
