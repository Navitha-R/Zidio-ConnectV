package com.zidio.jobportal.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "loggedOut")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlockListedToken {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	 @Column(nullable = false, unique = true, length = 500)
	 private String token;        // JWT token that is logged out

	 @Column(nullable = false)
	 private LocalDateTime expiryAt;   // Token expiry time

	
	 @Column(nullable = false)
	 private LocalDateTime loggedOutAt; // When token was logged out

	 @PrePersist
	 public void onLogout() {
	   this.loggedOutAt = LocalDateTime.now();
	    }

}
