package com.zidio.jobportal.DTO;
import java.time.LocalDateTime;

import lombok.*;


@Data 
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseDTO {

	 private Long id;
	 private String courseName;

	  //  private String level; 
	    // BEGINNER, INTERMEDIATE, ADVANCED

	    private String category;
	    // Programming, AI, Cloud, etc.
	    private double price;
	    private int durationHours;
	    private String CourseDescription;

	    private boolean certificateAvailable;

	    private String courseUrl; 
	    // Video link / LMS / YouTube

	    private String adminId;
	    private boolean active;

}
