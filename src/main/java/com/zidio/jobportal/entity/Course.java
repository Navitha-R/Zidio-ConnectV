package com.zidio.jobportal.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class Course {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String courseName;

    @Column(nullable = false)
    private String category;  
    // Programming, Soft Skills, AI, Cloud, etc.

    @Column(nullable = false)
    private boolean certificateAvailable = true;
    // You can keep or remove — most courses have certificate

    private String courseUrl; 
    // Optional → video link / LMS link / YouTube

    @Column(nullable = false)
    private String adminId; 
    // Who uploaded the course (Admin user id / email)
    @Column(nullable=false)
    private String  courseDescription;
    
    @Column(nullable=false)
    private int durationHours;
    
    @Column(nullable=false)
    private double price;

    @Column(nullable = false)
    private boolean active = true;

    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

}
	
	


