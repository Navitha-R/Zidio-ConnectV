package com.zidio.jobportal.controller;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.zidio.jobportal.DTO.JobPostsDTO;
import com.zidio.jobportal.Enum.JobType;
import com.zidio.jobportal.entity.JobPosts;
import com.zidio.jobportal.service.JobPostsService;

@RestController
@RequestMapping("/api/jobposts")
public class JobPostsController {

    private final JobPostsService jobPostsService;
    // Constructor injection (best practice)
    public JobPostsController(JobPostsService jobPostsService) {
        this.jobPostsService = jobPostsService;
    }
    //Create a new job post for a recruiter
    //POST → /api/jobposts/create/{recruiterId}
    @PostMapping("/create/{recruiterId}")
    public ResponseEntity<JobPosts> createJobPost(@PathVariable Long recruiterId,
                                                  @RequestBody JobPostsDTO dto) {
        JobPosts job = jobPostsService.createJobPost(recruiterId, dto);
        return ResponseEntity.ok(job);
    }
    //Get all active jobs
    // GET → /api/jobposts/all
    @GetMapping("/all")
    public ResponseEntity<List<JobPosts>> getAllActiveJobs() {
        return ResponseEntity.ok(jobPostsService.getActiveJobs());
    }
    //Get jobs by recruiter
    //GET → /api/jobposts/recruiter/{recruiterId}
    @GetMapping("/recruiter/{recruiterId}")
    public ResponseEntity<List<JobPosts>> getJobsByRecruiter(@PathVariable Long recruiterId) {
        return ResponseEntity.ok(jobPostsService.getJobsByRecruiter(recruiterId));
    }
    //Search jobs by title
    // GET → /api/jobposts/search/title/{keyword}
    @GetMapping("/search/title/{keyword}")
    public ResponseEntity<List<JobPosts>> searchJobsByTitle(@PathVariable String keyword) {
        return ResponseEntity.ok(jobPostsService.searchByTitle(keyword));
    }
    //Search jobs by location
    // GET → /api/jobposts/search/location/{location}
    @GetMapping("/search/location/{location}")
    public ResponseEntity<List<JobPosts>> searchJobsByLocation(@PathVariable String location) {
        return ResponseEntity.ok(jobPostsService.searchByLocation(location));
    }
    //Search jobs by company
    //GET → /api/jobposts/search/company/{companyName}
    @GetMapping("/search/company/{companyName}")
    public ResponseEntity<List<JobPosts>> searchJobsByCompany(@PathVariable String companyName) {
        return ResponseEntity.ok(jobPostsService.searchByCompanyName(companyName));
    }
    //Filter by job type
    // GET → /api/jobposts/filter/{jobType}
    @GetMapping("/filter/{jobType}")
    public ResponseEntity<List<JobPosts>> filterJobsByType(@PathVariable JobType jobType) {
        return ResponseEntity.ok(jobPostsService.filterByJobType(jobType));
    }
    //Deactivate job post
    // PUT → /api/jobposts/deactivate/{jobId}
    @PutMapping("/deactivate/{jobId}")
    public ResponseEntity<String> deactivateJob(@PathVariable Long jobId) {
        jobPostsService.deactivateJob(jobId);
        return ResponseEntity.ok("Job post deactivated successfully.");
    }
    //Get job by ID
    //GET → /api/jobposts/{id}
    @GetMapping("/{id}")
    public ResponseEntity<JobPosts> getJobById(@PathVariable Long id) {
        return ResponseEntity.ok(jobPostsService.getJobById(id));
    }
}


	


