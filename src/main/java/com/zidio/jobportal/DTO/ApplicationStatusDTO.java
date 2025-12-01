package com.zidio.jobportal.DTO;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ApplicationStatusDTO {
	private long totalJobApplication;
    private long appliedCount;
    private long underReviewCount;
    private long shortlistedCount;
    private long rejectedCount;
    private long hiredCount;
	

}
