package com.zidio.jobportal.entity;
import jakarta.persistence.*;




import lombok.*;

@Entity
@Table(name = "jobseekers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Jobseeker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Foreign key to the user who created this profile
    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String jobseekerFullname;

    @Column(unique = true, nullable = false)
    private String jobseekerEmail;

    @Column(nullable = false)
    private String universityName;

    @Column(nullable = false)
    private String educationalStream;

    @Column(nullable = false)
    private String passedOutYear;

    @Column(length = 500)
    private String skills;

    private String resumeUrl;

    private String certificateJpg;
}
