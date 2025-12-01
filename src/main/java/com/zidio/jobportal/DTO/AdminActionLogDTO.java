package com.zidio.jobportal.DTO;
import java.time.LocalDateTime;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminActionLogDTO {
	private Long id;
    private Long adminId;
    private Long targetUserId;
    private Long jobPostId;
    private Long recruiterId;
    private Long applicationId;
    private String action;
    private String description;
    private LocalDateTime timestamp; 

}
