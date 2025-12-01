package com.zidio.jobportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.zidio.jobportal.DTO.JobseekerDTO;
import com.zidio.jobportal.entity.Jobseeker;
import com.zidio.jobportal.service.JobseekerService;

@RestController
@RequestMapping("/api/jobseekers")
public class JobseekerController {

    @Autowired
    private JobseekerService jobseekerService;

    // ✅ Create new jobseeker
    @PostMapping("/create")
    public ResponseEntity<Jobseeker> createJobseeker(@RequestBody JobseekerDTO dto) {
        Jobseeker savedJobseeker = jobseekerService.createProfile(dto);
        return ResponseEntity.ok(savedJobseeker);
    }

    // ✅ Get jobseeker profile by user ID
    @GetMapping("/{userId}")
    public ResponseEntity<Jobseeker> getProfileByUserId(@PathVariable Long userId) {
        Jobseeker jobseeker = jobseekerService.getJobseekerProfile(userId);
        return ResponseEntity.ok(jobseeker);
    }
}
