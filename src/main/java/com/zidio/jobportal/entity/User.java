package com.zidio.jobportal.entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import com.zidio.jobportal.Enum.Role;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userName;

    @Column(unique = true, nullable = false)
    private String userEmail;

    @Column(nullable = false)
    private String password;
    
    private String resetToken;
    private LocalDateTime resetTokenExpire;
    
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.USER; 
    @Column(nullable = false)
    private boolean active = true; 
    @Column(nullable = false)
    private boolean verified = false; 
    @OneToOne
    @JoinColumn(name = "subscription_id")
    private SubscriptionPlan subscription;

    private boolean premiumUser = false;
}
   