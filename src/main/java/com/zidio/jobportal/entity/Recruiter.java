package com.zidio.jobportal.entity;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "recruiters")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recruiter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Link to the User entity (one-to-one relationship)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false)
    private String companyName;

    @Column(unique = true, nullable = false)
    private String companyEmail;

    @Column(length = 500)
    private String companyDescription;

    @Column(length = 200)
    private String websiteUrl;

    @Column(length = 15)
    private String contactNumber;

   
}
