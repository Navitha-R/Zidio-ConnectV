package com.zidio.jobportal.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zidio.jobportal.DTO.ApplicationStatusDTO;
import com.zidio.jobportal.DTO.CourseStatusDTO;
import com.zidio.jobportal.DTO.JobStatusDTO;
import com.zidio.jobportal.DTO.SubscriptionStatusDTO;
import com.zidio.jobportal.DTO.UserStatusDTO;
import com.zidio.jobportal.service.AnalyticsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/analytics")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    // ------------------- USERS -------------------
    @GetMapping("/users")
    public ResponseEntity<UserStatusDTO> getUserStatistics() {
        return ResponseEntity.ok(analyticsService.getUserStatus());
    }

    // ------------------- JOBS -------------------
    @GetMapping("/jobs")
    public ResponseEntity<JobStatusDTO> getJobStatistics() {
        return ResponseEntity.ok(analyticsService.getJobStatus());
    }

    // ------------------- APPLICATIONS -------------------
    @GetMapping("/applications")
    public ResponseEntity<ApplicationStatusDTO> getApplicationStatistics() {
        return ResponseEntity.ok(analyticsService.getApplicationStatus());
    }

    // ------------------- SUBSCRIPTIONS -------------------
    @GetMapping("/subscriptions")
    public ResponseEntity<SubscriptionStatusDTO> getSubscriptionStatistics() {
        return ResponseEntity.ok(analyticsService.getSubscriptionStatus());
    }

    // ------------------- COURSES -------------------
    @GetMapping("/courses")
    public ResponseEntity<CourseStatusDTO> getCourseStatistics() {
        return ResponseEntity.ok(analyticsService.getCourseStatus());
    }

}
