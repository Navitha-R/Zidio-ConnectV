package com.zidio.jobportal.DTO;
import java.time.LocalDateTime;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseStatusDTO {
	 private Long id;
	    private String courseName;
	    private String category;
	    private double price;
	    private int durationHours;
	    private String courseDescription;

	    private boolean certificateAvailable; 
	    private boolean active; // ACTIVE / INACTIVE (soft delete)
	    private boolean inActive;
	    
	    private String courseUrl;             // LMS / video link
	    private String adminId;               // Uploaded by which admin

	    private LocalDateTime createdAt;    
}
