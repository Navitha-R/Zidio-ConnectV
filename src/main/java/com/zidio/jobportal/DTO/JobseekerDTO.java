package com.zidio.jobportal.DTO;

  import lombok.*;

	/**
	 * DTO for creating or updating a Jobseeker profile.
	 */
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public class JobseekerDTO {
		private Long id;

	    private Long userId; // linked to user table

	    
	    private String jobseekerFullname;  
	    private String jobseekerEmail;  
	    private String universityName;  
	    private String educationalStream;
	    private String passedOutYear;
	    private String skills;
	    private String resumeUrl;
	    private String certificateJpg;
	}


