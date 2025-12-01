package com.zidio.jobportal.DTO;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class JobStatusDTO {
	private long totalJobs;
	private  long internships;
	private  long fullTime;
	private  long contractual;
	private  long partTime;
	private  long freelance;
	private  long activeJobs;
	private  long closedJobs;
	private  long archievedJobs;
	
}
