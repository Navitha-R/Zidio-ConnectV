package com.zidio.jobportal.DTO;

import com.zidio.jobportal.Enum.ApplicationStatus;

import lombok.Data;

@Data
public class ApplicationDTO {
	private Long jobId;
	private Long jobSeekerId;
	private Long recruiterId;
	private ApplicationStatus status;
	

}
