package com.zidio.jobportal.DTO;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserStatusDTO {
	private long totalJobseeker;
	private long totalRecruiter;
	private long  blockedUsers;
	private long verifiedUsers;
    private long unverifiedUsers;

}
