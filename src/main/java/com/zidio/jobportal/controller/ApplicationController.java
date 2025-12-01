package com.zidio.jobportal.controller;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.zidio.jobportal.DTO.ApplicationDTO;
import com.zidio.jobportal.Enum.ApplicationStatus;
import com.zidio.jobportal.entity.Application;
import com.zidio.jobportal.service.ApplicationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping("/apply")
    public ResponseEntity<Application> applyForJob(@RequestBody ApplicationDTO dto) {
        return ResponseEntity.ok(applicationService.applyForJob(dto));
    }

    @GetMapping("/jobseeker/{id}")
    public ResponseEntity<List<Application>> getApplicationsByJobSeeker(@PathVariable Long id) {
        return ResponseEntity.ok(applicationService.getApplicationByJobseeker(id));
    }

    @GetMapping("/recruiter/{id}")
    public ResponseEntity<List<Application>> getApplicationsByRecruiter(@PathVariable Long id) {
        return ResponseEntity.ok(applicationService.getApplicationByRecruiter(id));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(
            @PathVariable Long id,
            @RequestParam ApplicationStatus status) {
        applicationService.updateStatus(id, status);
        return ResponseEntity.ok("Status updated successfully to " + status);
    }
}



