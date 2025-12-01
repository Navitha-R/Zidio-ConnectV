package com.zidio.jobportal.DTO;

import com.zidio.jobportal.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserDTO - Data Transfer Object for user-related data.
 * Used for registration, profile updates, or authentication requests.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    private String userName;
    private String userEmail;
    private String password;
    private Role role;

   
  
}
