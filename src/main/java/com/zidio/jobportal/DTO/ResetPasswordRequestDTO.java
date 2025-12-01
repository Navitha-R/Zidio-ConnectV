package com.zidio.jobportal.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public class ResetPasswordRequestDTO {

	    @NotBlank(message = "Email is required")
	    @Email(message = "Invalid email format")
	    private String email;

	    @NotBlank(message = "OTP is required")
	    private String otp;
	    
	    @NotBlank(message = "Reset token is required")
	    private String token;


	    @NotBlank(message = "New password is required")
	    @Size(min = 6, message = "Password must be at least 6 characters long")
	    private String newPassword;

		public String getToken() {
			// TODO Auto-generated method stub
			return null;
		}
	}



