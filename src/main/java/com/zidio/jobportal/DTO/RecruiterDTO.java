package com.zidio.jobportal.DTO;


	import lombok.*;
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public class RecruiterDTO {

	    private Long userId; 
	    private String companyName; 
	    private String companyEmail;
	    private String companyDescription; 
	    private String websiteUrl; 
	    private String contactNumber; 
	}



